# Design System Tokens

## 💎 Global Design Tokens (:root)

```css
:root {
  /* Colors */
  --color-primary: #197fe6;       /* 브랜드 메인 컬러 */
  --color-secondary: #6c757d;     /* 보조 컬러 */
  --color-success: #28a745;
  --color-danger: #dc3545;
  --color-warning: #ffc107;
  --color-info: #17a2b8;
  --color-light: #f8f9fa;
  --color-dark: #343a40;
  --color-bg: #ffffff;
  --color-text: #212529;

  /* Typography */
  --font-family: 'Pretendard', -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
  --font-size-base: 16px;
  --font-size-sm: 0.875rem;
  --font-size-lg: 1.25rem;

  /* Shape & Shadow */
  --border-radius-sm: 8px;
  --border-radius: 12px;
  --border-radius-lg: 16px;
  --box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  --box-shadow-hover: 0 8px 30px rgba(0, 0, 0, 0.08);

  /* Spacing */
  --spacing-xs: 0.25rem;
  --spacing-sm: 0.5rem;
  --spacing-md: 1rem;
  --spacing-lg: 1.5rem;
  --spacing-xl: 3rem;
}
```

## 📐 UI Guidelines

1. **Buttons**: 반드시 `var(--border-radius)`를 사용하며, 호버 시 `var(--box-shadow-hover)`를 적용한다.
2. **Cards**: 화이트 배경에 `var(--box-shadow)`를 기본으로 사용한다.
3. **Typography**: 가독성을 위해 본문 텍스트는 `var(--color-text)`와 `var(--font-family)`를 엄격히 준수한다.
