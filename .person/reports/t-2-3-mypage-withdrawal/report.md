# 🚩 작업 보고서: T-2.3 회원 탈퇴 및 마이페이지 화면 구현 (최종)

- **작업 일시**: 2026-03-19
- **진행 단계**: 완료

## 1. 🌊 전체 작업 흐름 (Workflow)

1. **마이페이지 화면 구현**: 회원 정보를 수정하고 회원 탈퇴를 할 수 있는 `update-form.mustache` 화면을 생성했습니다. (회원가입과의 일관성을 위해 주소 기능은 제외했습니다.)
2. **DTO 추가**: 정보 수정에 필요한 필드(비밀번호, 이메일)를 담는 `UserRequest.Update` 클래스를 생성했습니다.
3. **리포지토리 수정**: 회원이 탈퇴할 때 작성한 게시글과 댓글을 함께 삭제하기 위해 `BoardRepository`와 `ReplyRepository`에 `deleteByUserId` 메서드를 추가했습니다.
4. **서비스 로직 구현**: `UserService`에 회원 정보를 업데이트하는 `회원수정` 메서드와 관련된 모든 데이터(댓글, 게시글, 유저)를 일괄 삭제하는 `회원탈퇴` 메서드를 구현했습니다.
5. **컨트롤러 연동**: `UserController`에 정보 수정 화면 요청(`GET`), 정보 수정 처리(`POST`), 회원 탈퇴 처리(`POST`)를 담당하는 엔드포인트를 매핑했습니다.

## 2. 🧩 변경된 모든 코드 포함

### 1) 클라이언트 요청 (프론트엔드)

`src/main/resources/templates/user/update-form.mustache` (일부 생략)
```html
<form action="/user/update" method="post" id="update-form">
    <!-- 아이디 (읽기 전용) -->
    <input type="text" name="username" value="{{sessionUser.username}}" readonly>
    <!-- 새 비밀번호, 이메일 입력 -->
    <input type="password" name="password" placeholder="새 비밀번호" required>
    <input type="email" name="email" value="{{sessionUser.email}}" required>
    
    <button type="submit" id="btn-submit" disabled>수정하기</button>
    <button type="button" onclick="deleteAccount()">회원탈퇴</button>
</form>

<form action="/user/delete" method="post" id="delete-form" style="display: none;"></form>

<script>
    // 비밀번호/이메일 유효성 검사 및 회원 탈퇴 확인 로직
    function deleteAccount() {
        if (confirm("정말로 회원 탈퇴를 진행하시겠습니까?")) {
            document.getElementById('delete-form').submit();
        }
    }
</script>
```

### 2) DTO 처리

`src/main/java/com/example/demo/user/UserRequest.java`
```java
// 정보 수정 요청 시 전달받을 데이터 (비밀번호, 이메일)
@Data
public static class Update {
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 4, max = 20, message = "비밀번호는 4자에서 20자 사이여야 합니다.")
    private String password;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
}
```

### 3) 컨트롤러 라우팅

`src/main/java/com/example/demo/user/UserController.java`
```java
// 회원 정보 수정 처리
@PostMapping("/user/update")
public String update(@Valid UserRequest.Update updateDTO, BindingResult bindingResult) {
    User sessionUser = (User) session.getAttribute("sessionUser");
    if (sessionUser == null) return "redirect:/login-form";
    
    // 주소 정보 제외하고 비밀번호와 이메일만 업데이트
    User updatedUser = userService.회원수정(sessionUser.getId(), updateDTO);
    session.setAttribute("sessionUser", updatedUser); // 갱신된 정보로 세션 업데이트
    return "redirect:/";
}

// 회원 탈퇴 처리
@PostMapping("/user/delete")
public String delete() {
    User sessionUser = (User) session.getAttribute("sessionUser");
    if (sessionUser == null) return "redirect:/login-form";
    
    userService.회원탈퇴(sessionUser.getId());
    session.invalidate(); // 탈퇴 후 세션 파기
    return "redirect:/";
}
```

### 4) 비즈니스 로직 및 DB 삭제

`src/main/java/com/example/demo/user/UserService.java`
```java
@Transactional
public User 회원수정(int id, UserRequest.Update updateDTO) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

    // 주소 필드 없이 비밀번호와 이메일만 수정
    user.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
    user.setEmail(updateDTO.getEmail());

    return user;
}

@Transactional
public void 회원탈퇴(int id) {
    // 1. 자식 테이블인 댓글부터 삭제
    replyRepository.deleteByUserId(id);
    // 2. 부모 테이블인 게시글 삭제
    boardRepository.deleteByUserId(id);
    // 3. 마지막으로 최상위 부모인 유저 정보 삭제
    userRepository.deleteById(id);
}
```

## 3. 🍦 상세비유 쉬운 예시를 들어서 (Easy Analogy)

- 이번 작업은 **"아파트 이사 가기"**와 같습니다.
- **마이페이지 수정**: 집에 있는 낡은 가구(비밀번호)를 교체하고, 연락처(이메일)를 새로 적어두는 거예요. 회원가입 때 없었던 복잡한 공사(주소 입력)는 하지 않기로 했어요.
- **회원 탈퇴 처리 (Cascade Delete)**: 이사를 완전히 나가려면 내가 베란다에 둔 화분(댓글)도 치워야 하고, 창고에 쌓아둔 짐(게시글)도 다 버린 뒤에 마지막으로 내 명패(유저 정보)를 떼어야 하죠. 그래야 집이 깨끗하게 비워지니까요!

## 4. 📚 기술 딥다이브 (Technical Deep-dive)

- **일관성 있는 기능 설계**: 기능을 추가할 때는 **사용자의 첫 경험(회원가입)**과 **이후의 관리 경험(정보 수정)**이 일치해야 합니다. 회원가입 시 받지 않은 주소 정보를 수정 페이지에서 갑자기 요구하는 것은 사용자에게 혼란을 줄 수 있으므로, 이번 수정에서는 주소 관련 로직을 모두 제거하여 일관성을 높였습니다.

- **Cascade Delete 전략 (애플리케이션 레벨)**: 데이터베이스 제약 조건(`ON DELETE CASCADE`)을 직접 걸 수도 있지만, 서비스 로직에서 명시적으로 삭제 순서를 지정하면 비즈니스 로직의 흐름을 파악하기 더 쉽고, 예기치 못한 대량 삭제 사고를 방지할 수 있는 장점이 있습니다.

```java
@Modifying
@Query("delete from Board b where b.user.id = :userId")
void deleteByUserId(@Param("userId") Integer userId);
```
- `@Modifying`: 단순 조회가 아닌 **데이터 변경(DELETE)**이 일어나는 쿼리임을 JPA에게 알려줍니다. 이 어노테이션이 있어야 데이터베이스에 직접적인 영향을 주는 명령을 안전하게 수행할 수 있습니다.
