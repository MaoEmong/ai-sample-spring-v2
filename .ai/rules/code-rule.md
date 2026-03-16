# Code Rules

## 1. 파일 명명 규칙 (Naming Conventions)

### Mustache 템플릿
- **규칙**: 모든 `.mustache` 파일은 소문자와 하이픈(`-`)만을 사용하여 작성한다 (kebab-case).
- **예시**:
  - `join-form.mustache` (O)
  - `joinForm.mustache` (X)
  - `board-detail.mustache` (O)
  - `boardDetail.mustache` (X)

## 2. Java 코딩 컨벤션
- 최신 Java 21 문법(var, record 등)을 적극 활용한다.
- 모든 주요 로직에는 한글 주석을 작성한다.
- 불필요한 임포트와 중복 코드를 엄격히 제거한다.
