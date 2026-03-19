# 🚩 작업 보고서: 로그인/로그아웃 시스템 구현

- **작업 일시**: 2026-03-19
- **진행 단계**: 완료

## 1. 🌊 전체 작업 흐름 (Workflow)

1. **DTO 확인**: `UserRequest.Login` 클래스 존재 확인 및 활용 결정.
2. **서비스 로직 추가**: `UserService`에 `login` 메서드 구현. `PasswordEncoder`를 사용하여 보안성 강화.
3. **컨트롤러 구현**: `UserController`에 로그인(`/login`), 로그아웃(`/logout`) 핸들러 추가. `HttpSession`을 통한 상태 유지.
4. **UI 구현**: Bootstrap 5.3 기반의 세련된 `login-form.mustache` 작성.
5. **레이아웃 연동**: `header.mustache`에 세션 상태(`sessionUser`)에 따른 동적 메뉴 분기 로직 적용.

## 2. 🧩 변경된 모든 코드 포함

### UserService.java
```java
// 로그인 비즈니스 로직: 아이디 확인 후 비밀번호 대조
public User login(UserRequest.Login loginDTO) {
    User user = userRepository.findByUsername(loginDTO.getUsername())
            .orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다."));

    if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
        throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }
    return user;
}
```

### UserController.java
```java
// 로그인 처리: 성공 시 세션 저장 및 홈 리다이렉트
@PostMapping("/login")
public String login(UserRequest.Login loginDTO) {
    User sessionUser = userService.login(loginDTO);
    session.setAttribute("sessionUser", sessionUser);
    return "redirect:/";
}

// 로그아웃 처리: 세션 무효화
@GetMapping("/logout")
public String logout() {
    session.invalidate();
    return "redirect:/";
}
```

### header.mustache
```html
<!-- 세션 유무에 따른 메뉴 분기 -->
{{#sessionUser}}
    <li class="nav-item"><a class="nav-link" href="/logout">로그아웃</a></li>
{{/sessionUser}}
{{^sessionUser}}
    <li class="nav-item"><a class="nav-link" href="/login-form">로그인</a></li>
{{/sessionUser}}
```

## 🍦 상세비유 쉬운 예시를 들어서 (Easy Analogy)

이번 작업은 **놀이공원 입장 시스템**을 만든 것과 같습니다.
- **회원가입**은 입장권을 미리 구매하고 정보를 등록하는 과정입니다.
- **로그인**은 입구에서 내가 등록한 이름과 비밀번호를 말하고 **'입장 팔찌(세션)'**를 받는 과정입니다.
- **로그아웃**은 팔찌를 반납하고 나가는 것이며, 이후에는 놀이기구(글쓰기 등)를 탈 수 없게 됩니다.

## 📚 기술 딥다이브 (Technical Deep-dive)

- **BCrypt (PasswordEncoder)**: 비밀번호를 평문으로 저장하지 않고 강력한 해시 알고리즘으로 암호화하여 저장하고 검증합니다.
- **HttpSession**: 서버 측 메모리에 사용자 상태를 저장하는 기술입니다. 브라우저가 종료되거나 유효 시간이 지나면 사라져 보안을 유지합니다.
- **Mustache Sections**: `{{#var}}`와 `{{^var}}`를 사용하여 간단한 조건부 렌더링을 수행합니다. 이는 SSR 방식에서 화면을 제어하는 핵심 도구입니다.
