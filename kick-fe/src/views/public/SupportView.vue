<script setup>
import { ref } from 'vue'
import { useResponsive } from '@/composables/useResponsive'

const { isMobile } = useResponsive()
const query = ref('')

const topics = [
  { id: 'shipping', title: 'Vận chuyển & giao hàng', desc: 'Phí ship, thời gian giao hàng, theo dõi lộ trình đơn hàng' },
  { id: 'returns', title: 'Đổi trả & bảo hành', desc: 'Chính sách hoàn tiền 14 ngày, đổi size thuận tiện nhanh chóng' },
  { id: 'payment', title: 'Thanh toán & bảo mật', desc: 'Thanh toán COD khi nhận hàng, thanh toán qua cổng VNPay, ví điện tử MoMo' },
  { id: 'account', title: 'Tài khoản & thành viên', desc: 'Quản lý thông tin đăng nhập, bảo mật mật khẩu, hồ sơ cá nhân và quyền lợi tích điểm' },
]
</script>

<template>
  <div class="kv-support">
    <!-- Header Banner -->
    <section class="kv-support-hero">
      <div class="kv-container kv-support-hero__inner">
        <span class="kv-section-tag">Hỗ trợ</span>
        <h1 class="kv-support-hero__title">Chúng tôi có thể giúp gì cho bạn?</h1>
        <p class="kv-support-hero__desc">Tìm câu trả lời nhanh chóng cho các thắc mắc về đơn hàng, vận chuyển và chính sách thành viên.</p>
        
        <!-- Premium Search Bar -->
        <div class="kv-support-search">
          <span class="kv-support-search__icon" aria-hidden="true">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
              <circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/>
            </svg>
          </span>
          <input
            v-model="query"
            type="search"
            placeholder="Tìm kiếm nội dung hướng dẫn..."
            class="kv-support-search__input"
            aria-label="Tìm kiếm trợ giúp"
          />
        </div>
      </div>
    </section>

    <!-- Main Content Area -->
    <section class="kv-container kv-support-content">
      <div class="kv-support-layout" :class="{ 'kv-support-layout--mobile': isMobile }">
        
        <!-- Sidebar Navigation -->
        <aside class="kv-support-sidebar">
          <h2 class="kv-support-sidebar__title">Chủ đề phổ biến</h2>
          <nav class="kv-support-sidebar__nav" aria-label="Danh mục hỗ trợ">
            <a
              v-for="t in topics"
              :key="t.id"
              :href="`#${t.id}`"
              class="kv-support-sidebar__link"
            >
              <span>{{ t.title }}</span>
              <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
                <polyline points="9 18 15 12 9 6"/>
              </svg>
            </a>
          </nav>
        </aside>

        <!-- Topic Details -->
        <main class="kv-support-main">
          <article
            v-for="t in topics"
            :id="t.id"
            :key="t.id"
            class="kv-support-card"
          >
            <div class="kv-support-card__header">
              <span class="kv-support-card__icon">✦</span>
              <h3 class="kv-support-card__title">{{ t.title }}</h3>
            </div>
            <p class="kv-support-card__desc">{{ t.desc }}</p>
            <div class="kv-support-card__content">
              <p>Nội dung chi tiết sẽ được tự động đồng bộ qua hệ thống FAQ API của KickVerse. Nếu bạn có bất kỳ câu hỏi khẩn cấp nào khác, vui lòng liên hệ đội ngũ Chăm sóc khách hàng qua Hotline hoặc mục Chat trực tuyến của chúng tôi.</p>
            </div>
          </article>
        </main>

      </div>
    </section>
  </div>
</template>

<style scoped>
.kv-support {
  background: #0a0a0a;
  min-height: 100vh;
  color: #f5f4f0;
}

/* Hero Section */
.kv-support-hero {
  padding-top: 80px;
  padding-bottom: 60px;
  background: linear-gradient(to bottom, #111111 0%, #0a0a0a 100%);
  border-bottom: 1px solid rgb(255 255 255 / 0.04);
}

.kv-support-hero__inner {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  max-width: 800px;
}

.kv-support-hero__title {
  font-size: clamp(32px, 5vw, 54px);
  font-weight: 800;
  letter-spacing: -1.5px;
  line-height: 1.1;
  color: #f5f4f0;
  margin: 16px 0 12px;
}

.kv-support-hero__desc {
  font-size: 15px;
  line-height: 1.7;
  color: rgb(245 244 240 / 0.6);
  margin: 0 0 32px;
}

/* Search Box */
.kv-support-search {
  position: relative;
  width: 100%;
}

.kv-support-search__icon {
  position: absolute;
  left: 20px;
  top: 50%;
  transform: translateY(-50%);
  color: rgb(245 244 240 / 0.3);
  display: flex;
  align-items: center;
}

.kv-support-search__input {
  width: 100%;
  height: 56px;
  padding-left: 56px;
  padding-right: 24px;
  font-size: 15px;
  color: #f5f4f0;
  background: rgb(255 255 255 / 0.04);
  border: 1px solid rgb(255 255 255 / 0.1);
  border-radius: 9999px;
  outline: none;
  transition: all 0.3s cubic-bezier(0.16, 1, 0.3, 1);
}

.kv-support-search__input::placeholder { color: rgb(245 244 240 / 0.25); }

.kv-support-search__input:focus {
  border-color: rgb(200 169 110 / 0.5);
  background: rgb(255 255 255 / 0.06);
  box-shadow: 0 0 0 3px rgb(200 169 110 / 0.08);
}

/* Layout */
.kv-support-content {
  padding-top: 64px;
  padding-bottom: 120px;
}

.kv-support-layout {
  display: grid;
  grid-template-columns: 280px 1fr;
  gap: 60px;
  align-items: start;
}

.kv-support-layout--mobile {
  grid-template-columns: 1fr;
  gap: 40px;
}

/* Sidebar */
.kv-support-sidebar {
  position: sticky;
  top: 100px;
}

.kv-support-sidebar__title {
  font-size: 14px;
  font-weight: 700;
  letter-spacing: 1.5px;
  text-transform: uppercase;
  color: #c8a96e;
  margin: 0 0 20px;
}

.kv-support-sidebar__nav {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.kv-support-sidebar__link {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 14px 16px;
  font-size: 13.5px;
  font-weight: 600;
  color: rgb(245 244 240 / 0.6);
  text-decoration: none;
  background: transparent;
  border-radius: 8px;
  transition: all 0.2s ease;
}

.kv-support-sidebar__link:hover {
  color: #f5f4f0;
  background: rgb(255 255 255 / 0.04);
}

.kv-support-sidebar__link svg {
  opacity: 0;
  transform: translateX(-4px);
  transition: all 0.2s ease;
}

.kv-support-sidebar__link:hover svg {
  opacity: 1;
  transform: translateX(0);
  color: #c8a96e;
}

/* Cards */
.kv-support-main {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.kv-support-card {
  padding: 32px;
  background: #111111;
  border: 1px solid rgb(255 255 255 / 0.04);
  border-radius: 16px;
  transition: border-color 0.3s ease;
}

.kv-support-card:hover {
  border-color: rgb(255 255 255 / 0.08);
}

.kv-support-card__header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.kv-support-card__icon {
  font-size: 16px;
  color: #c8a96e;
}

.kv-support-card__title {
  font-size: 18px;
  font-weight: 700;
  color: #f5f4f0;
  margin: 0;
}

.kv-support-card__desc {
  font-size: 14px;
  line-height: 1.6;
  color: rgb(245 244 240 / 0.75);
  margin: 0 0 16px;
  font-weight: 500;
}

.kv-support-card__content {
  font-size: 13px;
  line-height: 1.7;
  color: rgb(245 244 240 / 0.45);
  border-top: 1px solid rgb(255 255 255 / 0.05);
  padding-top: 16px;
}

/* Tag */
.kv-section-tag {
  display: inline-block;
  font-size: 10px;
  font-weight: 700;
  letter-spacing: 2.5px;
  text-transform: uppercase;
  color: #c8a96e;
  padding: 4px 10px;
  background: rgb(200 169 110 / 0.1);
  border: 1px solid rgb(200 169 110 / 0.2);
  border-radius: 4px;
}
</style>
