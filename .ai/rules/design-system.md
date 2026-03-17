# Design System Tokens

## 💎 Global Design Tokens (:root)

기존 Bootstrap 5.3.0 기반의 화면(`home.mustache`, `header.mustache`, `footer.mustache`)에서 추출한 디자인 토큰입니다.

```css
:root {
  /* Colors (Bootstrap 5.3 기반) */
  --color-primary: #0d6efd;       /* Bootstrap Primary */
  --color-secondary: #6c757d;     
  --color-dark: #212529;          /* navbar-dark, bg-dark 활용 */
  --color-light: #f8f9fa;         
  --color-bg: #ffffff;            /* 기본 배경 */
  --color-text: #212529;          /* 기본 텍스트 */
  --color-text-inverse: #ffffff;  /* text-white 활용 */
  
  --color-success: #198754;
  --color-danger: #dc3545;
  --color-warning: #ffc107;
  --color-info: #0dcaf0;

  /* Typography */
  --font-family: 'Pretendard', system-ui, -apple-system, "Segoe UI", Roboto, "Helvetica Neue", "Noto Sans", "Liberation Sans", Arial, sans-serif;
  --font-size-base: 16px;
  --font-size-h1: 2.5rem;         /* <h1> 메인 페이지 타이틀용 */
  --font-size-p: 1rem;            /* 기본 <p> 태그용 */

  /* Spacing (Bootstrap 유틸리티 클래스 매핑) */
  --spacing-xs: 0.25rem;          /* 1 */
  --spacing-sm: 0.5rem;           /* 2 */
  --spacing-md: 1rem;             /* mt-3 매핑 */
  --spacing-lg: 1.5rem;           /* p-4 매핑 */
  --spacing-xl: 3rem;             /* mt-5 매핑 */

  /* Shape & Shadow (Base System 확장) */
  --border-radius-sm: 0.25rem;
  --border-radius: 0.375rem;      
  --border-radius-lg: 0.5rem;
  --box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  --box-shadow-hover: 0 8px 30px rgba(0, 0, 0, 0.08);
}
```

## 📐 UI Guidelines

1. **Buttons**: 반드시 `var(--border-radius)`를 사용하며, 호버 시 `var(--box-shadow-hover)`를 적용한다.
2. **Cards**: 화이트 배경에 `var(--box-shadow)`를 기본으로 사용한다.
3. **Typography**: 가독성을 위해 본문 텍스트는 `var(--color-text)`와 `var(--font-family)`를 엄격히 준수한다.
4. **Layout**: Header와 Footer는 `var(--color-dark)` 배경과 `var(--color-text-inverse)` 텍스트를 사용하여 명확히 구분 짓는다.
