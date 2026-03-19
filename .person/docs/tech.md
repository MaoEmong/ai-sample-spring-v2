# 기술 명세서 (Tech Spec)

본 프로젝트의 기술 스택 및 아키텍처, 환경 설정을 정의한 문서입니다.

## 1. 기술 스택 (Tech Stack)

### 1.1 Backend

- **Language**: Java 21
- **Framework**: Spring Boot 4.0.3
- **Web**: Spring Web (Spring MVC)
- **ORM / Database Access**: Spring Data JPA / Hibernate
- **Database**: H2 Database (In-Memory, 개발/테스트용)
- **Template Engine**: Mustache (Server-Side Rendering)
- **Security**: 단방향 암호화(BCrypt) 적용 (Spring Security 도입 또는 자체 구현)

### 1.2 Build Tool & Utilities

- **Build Tool**: Gradle
- **Utility**: Lombok (보일러플레이트 코드 제거)
- **Development Tools**: Spring Boot DevTools

## 2. 시스템 아키텍처

- **Monolithic Architecture**: 프론트엔드(Mustache 템플릿)와 백엔드가 하나의 Spring Boot 애플리케이션으로 구동되는 모놀리식 구조입니다.
- **Layered Architecture**:
  - **Controller Layer**: HTTP 요청/응답 처리 및 뷰(View) 라우팅
  - **Service Layer**: 비즈니스 로직 및 트랜잭션 처리 (`@Transactional` 기반)
  - **Repository Layer**: JPA를 이용한 데이터베이스 접근
  - **Entity Layer**: DB 테이블과 1:1 매핑되는 도메인 객체

## 3. 주요 설정 내용 (application.properties 기반)

- **Server Port**: 8080 (`server.port=8080`)
- **Encoding**: UTF-8 강제 적용
- **Mustache**: 세션/리퀘스트 속성 노출 활성화 (`spring.mustache.servlet.expose-session-attributes=true`)
- **Database (H2)**:
  - 접속 URL: `jdbc:h2:mem:test`
  - H2 Console 활성화 (`spring.h2.console.enabled=true`)
- **JPA & SQL**:
  - `data.sql` 을 통한 초기 데이터 적재 (`spring.sql.init.data-locations=classpath:db/data.sql`)
  - SQL 쿼리 로깅 및 포맷팅 활성화 (`spring.jpa.show-sql=true`, `hibernate.format_sql=true`)
  - OSIV(Open Session In View) 비활성화 (`spring.jpa.open-in-view=false`)로 성능 및 설계 구조 개선
  - Batch Fetch Size 설정 (`hibernate.default_batch_fetch_size=10`)을 통한 N+1 문제 완화

## 4. 데이터베이스 및 엔티티 연관관계

- **회원 (User)**: ID, 로그인 정보(Bcrypt 암호화), 가입일, 탈퇴 여부(is_deleted)
- **게시글 (Board)**: ID, 작성자 참조, 제목, 본문, 작성/수정일, 삭제 여부(is_deleted)
- **댓글 (Reply)**: ID, 작성자 참조, 대상 게시글 참조, 본문, 작성일, 삭제 여부(is_deleted)
- **관계**: User(1) : Board(N), User(1) : Reply(N), Board(1) : Reply(N)

_(자세한 DB 스키마는 `erd.md` 참고)_
