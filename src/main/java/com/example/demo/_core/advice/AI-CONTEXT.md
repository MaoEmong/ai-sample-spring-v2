<!-- Parent: ../AI-CONTEXT.md -->

# advice

## 목적
애플리케이션 전반에서 발생하는 예외 처리 및 공통적인 검증 로직을 AOP로 처리하는 패키지입니다.

## 주요 파일
| 파일명 | 설명 |
|--------|------|
| `CustomValidationAdvice.java` | 컨트롤러의 BindingResult를 체크하여 유효성 검사 실패 시 예외를 던지는 AOP 클래스 |
| `GlobalExceptionHandler.java` | 애플리케이션 전역에서 발생하는 예외를 포착하여 공통된 응답 형식을 반환하는 클래스 |

## 하위 디렉토리
- 없음

## AI 작업 지침
- **예외 처리**: `GlobalExceptionHandler`를 통해 일관된 에러 응답 형식을 유지한다.
- **AOP 활용**: 반복적인 유효성 검사 로직은 `CustomValidationAdvice`와 같은 AOP로 분리하여 코드 중복을 최소화한다.
- **한글 주석**: 각 Advice 클래스의 역할과 적용 범위를 상세히 한글로 설명한다.

## 테스트
- 컨트롤러 테스트 시 유효하지 않은 데이터를 전송하여 에러 응답이 정상적으로 반환되는지 확인한다.

## 의존성
- 내부: `com.example.demo._core.utils.Resp` 사용
- 외부: Spring AOP, `@ControllerAdvice`
