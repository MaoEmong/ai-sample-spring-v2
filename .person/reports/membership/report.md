# 🚩 작업 보고서: 회원 인증 시스템 (Membership System) 구현

- **작업 일시**: 2026-03-18
- **진행 단계**: 완료

## 1. 🌊 전체 작업 흐름 (Workflow)

1. **유효성 검사 설계**: `JoinDTO`에 입력 값 검증 규칙을 설정하고, 이를 전역적으로 감시할 AOP(`CustomValidationAdvice`)를 만들었습니다.
2. **예외 처리 구축**: 검증 실패 시 사용자에게 알림창(`alert`)을 띄워주는 `GlobalExceptionHandler`를 구성했습니다.
3. **보안 강화**: `Spring Security`를 연동하고 `BCrypt` 암호화 엔진을 등록하여 비밀번호 노출 위험을 차단했습니다.
4. **회원가입 로직 업데이트**: `UserService`에서 실제 저장 전 비밀번호를 해싱(암호화)하도록 로직을 변경했습니다.
5. **화면 디자인 및 연동**: 기존 AJAX 방식을 표준 `form` 전송 방식으로 전환하고, 실시간 유효성 피드백이 가능한 세련된 UI를 `join-form.mustache`에 적용했습니다.

## 2. 🧩 변경된 모든 코드

### 1) 유효성 검사 및 에러 처리 (AOP & Exception)

```java
// CustomValidationAdvice.java
// POST/PUT 요청 시 입력 데이터의 유효성을 자동으로 체크하는 '감시자'입니다.
@Aspect
@Component
public class CustomValidationAdvice {
    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void validationAdvice(JoinPoint jp) {
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult bindingResult) {
                if (bindingResult.hasErrors()) {
                    // 첫 번째 에러 메시지를 가져와서 예외를 던집니다.
                    String message = bindingResult.getFieldErrors().get(0).getDefaultMessage();
                    throw new RuntimeException(message);
                }
            }
        }
    }
}
```

```java
// GlobalExceptionHandler.java
// 발생한 예외를 가로채서 사용자에게 브라우저 알림창으로 보여줍니다.
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public String ex(RuntimeException e) {
        return """
                <script>
                    alert("%s");
                    history.back();
                </script>
                """.formatted(e.getMessage());
    }
}
```

### 2) 보안 설정 및 서비스 로직 (Security & Service)

```java
// SecurityConfig.java
// 비밀번호 암호화 도구(BCrypt)를 등록하고 모든 페이지 접근을 허용합니다.
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호를 안전하게 으깨주는 도구
    }
}
```

```java
// UserService.java
// 회원가입 시 비밀번호를 암호화하여 DB에 저장합니다.
@Transactional
public void join(UserRequest.Join joinDTO) {
    User user = User.builder()
            .username(joinDTO.getUsername())
            .password(passwordEncoder.encode(joinDTO.getPassword())) // 암호화 수행!
            .email(joinDTO.getEmail())
            .build();
    userRepository.save(user);
}
```

## 3. 🍦 상세비유 (Easy Analogy)

"이번 작업은 **'최첨단 보안 출입국 심사'**와 같습니다. 

1. **유효성 검사**: 비자가 유효한지(형식이 맞는지) 미리 확인하는 **사전 심사대**입니다. 서류가 미비하면 바로 돌려보냅니다.
2. **BCrypt 암호화**: 여권 번호를 그대로 적지 않고, 아무도 알아볼 수 없게 **특수 암호 코드로 변환**하여 장부에 적는 것과 같습니다. 혹시 장부를 도둑맞아도 암호를 풀 수 없어 안전합니다.
3. **GlobalExceptionHandler**: 심사 탈락자에게 친절하게 "서류를 다시 확인해 주세요"라고 안내하며 **이전 줄로 돌려보내는 안내원** 역할을 합니다."

## 4. 📚 기술 딥다이브 (Technical Deep-dive)

- **AOP (Aspect Oriented Programming)**: 비즈니스 로직(회원가입)과 공통 로직(유효성 검사)을 분리하는 기술입니다. 모든 컨트롤러 메서드에 에러 처리 코드를 일일이 적지 않아도, AOP가 공통적으로 감시하고 처리해 줍니다.
- **BCrypt**: 단방향 해시 알고리즘입니다. 암호화는 가능하지만 복호화(원래 비번 찾기)는 불가능하게 설계되어 있어, 서버 관리자조차 사용자의 원래 비밀번호를 알 수 없습니다.
- **Validation (@Valid)**: Jakarta Bean Validation 표준을 사용하여 객체의 상태를 선언적으로 검증합니다. `@NotBlank`, `@Size` 등의 어노테이션만으로 복잡한 if문 없이 데이터 검증이 가능합니다.
