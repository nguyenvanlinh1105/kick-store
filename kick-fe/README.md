# KickVerse Frontend (`kick-fe`)

Vue 3 + Vite + Pinia + Vue Router + Tailwind CSS UI cho KickVerse, theo `DESIGN.md`.

## Chạy local

```bash
npm install
npm run dev
```

## Cấu trúc theo role

| Area | Path | Layout |
|------|------|--------|
| Public | `/`, `/shop`, `/login`, `/register`, `/support`, `/cart`, `/checkout` | `PublicLayout` |
| Customer | `/account/*`, `/wishlist`, `/notifications` | `CustomerLayout` |
| Admin / QL bán hàng | `/admin/*` | `AdminLayout` |
| Staff | `/staff/*` | `StaffLayout` |

## Responsive

Composable `useResponsive` — breakpoint **960px** (`KV_BREAKPOINT`). Tailwind variant: `kv:` (≥ 960px).

## Demo đăng nhập

Trang `/login` có nút demo nhanh: Customer / Admin / QL bán hàng / Staff.
