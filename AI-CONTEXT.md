# Demo Project

## 목적
Spring Boot 4.0.3과 Java 21을 기반으로 한 Mustache, H2, JPA 기반의 게시판 프로젝트입니다.

## 주요 파일
| 파일명 | 설명 |
|--------|------|
| `build.gradle` | 프로젝트 의존성 및 빌드 설정 (Spring Boot 4.0.3, Java 21) |
| `AI-GUIDE.md` | AI 개발 가이드 및 스킬 정의 |
| `AGENTS.md` | 프로젝트 전문 에이전트 목록 및 정의 |
| `GEMINI.md` | Gemini CLI 동작을 위한 파운데이션 가이드 |
| `README.md` | 프로젝트 기본 설명 |

## 하위 디렉토리
- `src/main/java/com/example/demo/` - 핵심 도메인 로직 및 컨트롤러
- `src/main/resources/` - DB 설정, Mustache 템플릿, 정적 리소스
- `.ai/` - AI 관련 규칙 및 스킬 정의

## AI 작업 지침
- **HTTP 메서드**: GET, POST만 사용하여 구현한다. (PUT, DELETE 사용 금지)
- **Service 메서드**: `*Service.java`의 메서드명은 반드시 한글로 작성한다. (예: `회원가입()`, `글쓰기()`)
- **API 컨트롤러**: `*ApiController`는 Ajax 요청이 필요한 경우에만 제한적으로 사용한다.
- **요청 방식**: Form 태그와 `application/x-www-form-urlencoded` 방식을 기본으로 사용한다.
- **Java 21/Spring Boot 4.x**: 최신 자바 문법(var, 레코드 등)과 스프링 부트 기능을 적극 활용한다.
- **DTO 평탄화**: 응답 및 요청 DTO는 가능한 한 평탄한 구조(Flat DTO)를 유지한다.
- **한글 주석**: 모든 중요한 로직에는 친절한 한글 주석을 작성한다.

## 테스트
- `./gradlew test` 명령을 통해 전체 테스트를 수행할 수 있습니다.

## 의존성
- 내부: `board`, `reply`, `user`, `_core` 패키지 간 상호 작용
- 외부: Spring Boot Starter (Data JPA, Web, Mustache), H2 Database, Lombok
