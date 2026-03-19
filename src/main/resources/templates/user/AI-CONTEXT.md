<!-- Parent: ../AI-CONTEXT.md -->

# user

## 목적
사용자 관련(회원가입, 로그인 등) 기능을 제공하는 Mustache 템플릿 파일들을 포함합니다.

## 주요 파일
| 파일명 | 설명 |
|--------|------|
| `join-form.mustache` | 회원가입 요청을 처리하기 위한 입력 폼 템플릿 |
| `login-form.mustache` | 로그인 요청을 처리하기 위한 입력 폼 템플릿 |

## 하위 디렉토리
- 없음

## AI 작업 지침
- **입력 폼 설계**: 사용자의 데이터를 안전하게 전달하기 위해 `form` 태그와 적절한 HTTP POST 메서드를 사용한다.
- **유효성 검사**: 프론트엔드와 백엔드 모두에서 필수 입력 항목에 대한 검증을 수행한다.
- **레이아웃 결합**: `header.mustache`와 `footer.mustache`를 적절히 삽입하여 일관된 사용자 경험을 제공한다.

## 테스트
- 실제 폼 데이터를 전송하여 정상적으로 회원가입 및 로그인이 완료되는지 확인한다.

## 의존성
- 내부: `src/main/java/com/example/demo/user/UserController`에서 렌더링 호출
- 외부: Mustache Template Engine, Bootstrap
