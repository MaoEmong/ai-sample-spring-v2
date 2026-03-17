# 🚩 작업 보고서: 회원가입 아이디 중복 체크 (AJAX)

- **작업 일시**: 2026-03-17
- **진행 단계**: [완료]

## 1. 🌊 전체 작업 흐름 (Workflow)

1. **백엔드 API 구축**: `UserApiController`에 `/api/user/username-same-check` 엔드포인트를 생성하여 아이디 중복 여부를 반환하도록 구현하였습니다.
2. **서비스 로직 구현**: `UserService`에서 `UserRepository`를 통해 해당 아이디가 존재하는지 확인하는 기능을 추가하였습니다.
3. **회원가입 뷰 생성**: `join-form.mustache`를 생성하고 Bootstrap 5.3을 활용하여 UI를 구성하였습니다.
4. **비동기 통신 구현**: Fetch API를 사용하여 '중복체크' 버튼 클릭 시 백엔드와 통신하고, 결과를 실시간으로 화면에 반영하도록 스크립트를 작성하였습니다.
5. **유효성 처리**: 중복 체크가 완료되지 않으면 '가입하기' 버튼을 비활성화하고, 아이디 입력값이 변경되면 다시 체크하도록 제어 로직을 추가하였습니다.

## 2. 🧩 핵심 코드 (Core Logic)

### **백엔드 (UserApiController.java)**
```java
@GetMapping("/api/user/username-same-check")
public ResponseEntity<?> usernameSameCheck(String username) {
    // DB에서 아이디 중복 여부 확인
    boolean isSame = userService.checkUsername(username);
    // Resp 도구를 사용하여 표준화된 응답 반환
    return Resp.ok(isSame);
}
```

### **프론트엔드 (join-form.mustache)**
```javascript
async function checkUsername() {
    const username = document.querySelector("#username").value;
    // Fetch API를 통한 비동기 GET 요청
    const response = await fetch(`/api/user/username-same-check?username=${username}`);
    const result = await response.json();

    if (result.body === true) {
        // 중복 시 처리 로직
        isUsernameChecked = false;
    } else {
        // 사용 가능 시 처리 로직
        isUsernameChecked = true;
    }
}
```

## 3. 🍦 상세 비유 (Easy Analogy)

이번 작업은 **"도서관 회원증 발급 전, 이름표 확인"**과 같습니다.
마치 도서관에서 새로운 회원증을 만들기 전에 사서 선생님께 **"이 이름으로 이미 등록된 사람이 있나요?"**라고 물어보고, 사서 선생님이 **"네, 있어요"** 또는 **"아니요, 없어요"**라고 답해주는 것과 비슷해요! 사서 선생님의 대답을 듣기 전까지는 회원증 신청서를 제출(가입하기)할 수 없도록 막아둔 상태입니다.

## 4. 📚 기술 딥다이브 (Technical Deep-dive)

- **AJAX (Asynchronous JavaScript and XML)**: 페이지 전체를 새로고침하지 않고도 필요한 데이터만 서버와 주고받는 기술입니다. 여기서는 Fetch API를 사용하여 구현되었습니다.
- **Fetch API**: 현대 브라우저에서 제공하는 네트워크 통신 도구로, Promise 기반으로 동작하여 비동기 처리를 직관적으로 작성할 수 있게 해줍니다.
- **RestController**: JSON 데이터를 직접 반환하는 컨트롤러입니다. 뷰(HTML)가 아닌 순수 데이터만 응답할 때 사용합니다.
