<!-- Parent: ../../../../../../AI-CONTEXT.md -->

# demo

## 목적
Spring Boot 애플리케이션의 엔트리 포인트이자 전체 도메인 로직이 시작되는 루트 패키지입니다.

## 주요 파일
| 파일명 | 설명 |
|--------|------|
| `DemoApplication.java` | Spring Boot 애플리케이션의 실행을 위한 메인 엔트리 클래스 |

## 하위 디렉토리
- `_core/` - 공통 유틸리티 및 설정
- `board/` - 게시판 도메인 로직 및 API
- `reply/` - 댓글 도메인 로직 및 API
- `user/` - 사용자 관리 및 인증 로직

## AI 작업 지침
- **HTTP 메서드**: GET, POST만 사용한다. (PUT, DELETE 사용 금지)
- **Service 메서드**: `*Service.java`의 메서드명은 반드시 한글로 작성한다.
- **Modern Java**: Java 21의 `var` 키워드를 적극적으로 사용한다.
- **DTO 설계**: 계층 간 데이터 전송을 위해 평탄한 구조(Flat DTO)를 유지한다.
- **주석**: 모든 로직에 친절하고 상세한 한글 주석을 작성한다.
- **청결한 코드**: 사용하지 않는 임포트와 중복 코드를 즉시 제거한다.

## 테스트
- `./gradlew test`를 사용하여 전체 테스트를 실행합니다.

## 의존성
- 내부: `board`, `reply`, `user`, `_core` 패키지 참조
- 외부: Spring Boot Starter Web, JPA
