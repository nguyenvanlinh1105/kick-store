<script setup>
import { RouterLink } from 'vue-router'

const columns = [
  {
    title: 'Cửa hàng',
    links: [
      { to: '/shop', label: 'Tất cả sản phẩm' },
      { to: '/shop?cat=running', label: 'Running' },
      { to: '/shop?cat=lifestyle', label: 'Lifestyle' },
    ],
  },
  {
    title: 'Tài khoản',
    links: [
      { to: '/login', label: 'Đăng nhập' },
      { to: '/register', label: 'Đăng ký' },
      { to: '/account/orders', label: 'Đơn hàng' },
      { to: '/wishlist', label: 'Yêu thích' },
    ],
  },
  {
    title: 'Hỗ trợ',
    links: [
      { to: '/support', label: 'Trung tâm hỗ trợ' },
      { to: '/support#shipping', label: 'Vận chuyển' },
      { to: '/support#returns', label: 'Đổi trả' },
    ],
  },
]
</script>

<template>
  <footer class="kv-footer">
    <div class="kv-container kv-footer__inner">
      
      <!-- Top Section -->
      <div class="kv-footer__top">
        <div class="kv-footer__brand">
          <RouterLink to="/" class="kv-footer__logo">
            KICK<span>VERSE</span>
          </RouterLink>
          <p class="kv-footer__tagline">
            Định nghĩa lại phong cách sneaker streetwear cao cấp.
          </p>
          <div class="kv-footer__socials">
            <a href="#" aria-label="Instagram" class="kv-footer__social-link">IG</a>
            <a href="#" aria-label="Facebook" class="kv-footer__social-link">FB</a>
            <a href="#" aria-label="Twitter" class="kv-footer__social-link">TW</a>
            <a href="#" aria-label="Youtube" class="kv-footer__social-link">YT</a>
          </div>
        </div>

        <!-- Links Columns -->
        <div class="kv-footer__grid">
          <div v-for="col in columns" :key="col.title" class="kv-footer__col">
            <h3 class="kv-footer__col-title">{{ col.title }}</h3>
            <ul class="kv-footer__col-links">
              <li v-for="link in col.links" :key="link.to">
                <RouterLink :to="link.to" class="kv-footer__link">
                  {{ link.label }}
                </RouterLink>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <!-- Bottom Section -->
      <div class="kv-footer__bottom">
        <p class="kv-footer__copy">© 2026 KickVerse. Crafted with luxury aesthetics.</p>
        <div class="kv-footer__legal">
          <a href="#" class="kv-footer__legal-link">Điều khoản dịch vụ</a>
          <span class="kv-footer__divider">|</span>
          <a href="#" class="kv-footer__legal-link">Chính sách bảo mật</a>
        </div>
      </div>

    </div>
  </footer>
</template>

<style scoped>
.kv-footer {
  background: #0a0a0a;
  border-top: 1px solid rgb(255 255 255 / 0.05);
  color: rgb(245 244 240 / 0.75);
  padding-top: 80px;
  padding-bottom: 40px;
  position: relative;
  z-index: 10;
}

.kv-footer__inner {
  display: flex;
  flex-direction: column;
  gap: 60px;
}

.kv-footer__top {
  display: grid;
  grid-template-columns: 1fr 2fr;
  gap: 60px;
}

@media (max-width: 959px) {
  .kv-footer__top {
    grid-template-columns: 1fr;
    gap: 40px;
  }
}

.kv-footer__brand {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.kv-footer__logo {
  font-family: 'Bebas Neue', sans-serif;
  font-size: 32px;
  letter-spacing: 3px;
  color: #f5f4f0;
  text-decoration: none;
  margin-bottom: 16px;
  display: inline-block;
}

.kv-footer__logo span {
  background: linear-gradient(135deg, #c8a96e, #e8c97e, #c8a96e);
  background-clip: text;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.kv-footer__tagline {
  font-size: 14px;
  line-height: 1.6;
  color: rgb(245 244 240 / 0.5);
  max-width: 320px;
  margin: 0 0 24px;
}

.kv-footer__socials {
  display: flex;
  gap: 16px;
}

.kv-footer__social-link {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  border: 1px solid rgb(255 255 255 / 0.08);
  font-size: 11px;
  font-weight: 700;
  color: rgb(245 244 240 / 0.5);
  text-decoration: none;
  transition: all 0.2s ease;
}

.kv-footer__social-link:hover {
  color: #0a0a0a;
  background: #c8a96e;
  border-color: #c8a96e;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgb(200 169 110 / 0.2);
}

/* Links Columns */
.kv-footer__grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 40px;
}

@media (max-width: 639px) {
  .kv-footer__grid {
    grid-template-columns: 1fr;
    gap: 32px;
  }
}

.kv-footer__col {
  display: flex;
  flex-direction: column;
}

.kv-footer__col-title {
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 1.5px;
  text-transform: uppercase;
  color: #c8a96e;
  margin: 0 0 20px;
}

.kv-footer__col-links {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.kv-footer__link {
  font-size: 13.5px;
  color: rgb(245 244 240 / 0.55);
  text-decoration: none;
  transition: color 0.2s ease, padding-left 0.2s ease;
  display: inline-block;
}

.kv-footer__link:hover {
  color: #f5f4f0;
  padding-left: 4px;
}

/* Bottom Section */
.kv-footer__bottom {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
  border-top: 1px solid rgb(255 255 255 / 0.05);
  padding-top: 32px;
}

@media (max-width: 639px) {
  .kv-footer__bottom {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }
}

.kv-footer__copy {
  font-size: 12px;
  color: rgb(245 244 240 / 0.4);
  margin: 0;
}

.kv-footer__legal {
  display: flex;
  align-items: center;
  gap: 12px;
}

.kv-footer__legal-link {
  font-size: 12px;
  color: rgb(245 244 240 / 0.4);
  text-decoration: none;
  transition: color 0.2s ease;
}

.kv-footer__legal-link:hover {
  color: #c8a96e;
}

.kv-footer__divider {
  font-size: 10px;
  color: rgb(245 244 240 / 0.2);
}
</style>
