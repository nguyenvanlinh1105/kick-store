<script setup>
import { RouterLink } from 'vue-router'
import KvButton from '@/components/ui/KvButton.vue'
import KvProductCard from '@/components/ui/KvProductCard.vue'
import { DEMO_PRODUCTS } from '@/data/demo'
import { useCartStore } from '@/stores/cart'
import { useResponsive } from '@/composables/useResponsive'

const cart = useCartStore()
const { isMobile } = useResponsive()
const featured = DEMO_PRODUCTS.slice(0, 4)

const collections = [
  {
    id: 'running',
    label: 'Running',
    tag: 'Performance',
    img: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=800&q=85&auto=format&fit=crop',
    to: '/shop?cat=running',
  },
  {
    id: 'lifestyle',
    label: 'Lifestyle',
    tag: 'Streetwear',
    img: 'https://images.unsplash.com/photo-1600269452121-4f2416e55c28?w=800&q=85&auto=format&fit=crop',
    to: '/shop?cat=lifestyle',
  },
  {
    id: 'limited',
    label: 'Limited',
    tag: 'Exclusive Drop',
    img: 'https://images.unsplash.com/photo-1539185441755-769473a23570?w=800&q=85&auto=format&fit=crop',
    to: '/shop?cat=new',
  },
]

const stats = [
  { value: '10K+', label: 'Khách hàng' },
  { value: '500+', label: 'Sản phẩm' },
  { value: '98%', label: 'Đánh giá tốt' },
  { value: '24h', label: 'Giao hàng' },
]
</script>

<template>
  <div class="kv-home">

    <!-- ═══════════════════════════════════════
         HERO — Full-bleed cinematic
    ═══════════════════════════════════════ -->
    <section class="kv-hero" id="hero" aria-label="Hero section">
      <!-- BG Image Layer -->
      <div class="kv-hero__bg">
        <img
          src="https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=1920&q=85&auto=format&fit=crop"
          alt=""
          aria-hidden="true"
          class="kv-hero__bg-img"
        />
        <div class="kv-hero__bg-overlay"></div>
      </div>

      <!-- Noise Texture -->
      <div class="kv-hero__noise" aria-hidden="true"></div>

      <!-- Content -->
      <div class="kv-container kv-hero__content">
        <div class="kv-hero__eyebrow">
          <span class="kv-hero__eyebrow-dot"></span>
          KickVerse · SS 2026
        </div>

        <h1 class="kv-hero__title">
          <span class="kv-hero__title-line">Đôi giày</span>
          <span class="kv-hero__title-line kv-hero__title-line--accent">định nghĩa</span>
          <span class="kv-hero__title-line">phong cách.</span>
        </h1>

        <p class="kv-hero__desc">
          Curated sneakers từ các thương hiệu hàng đầu.<br v-if="!isMobile" />
          Từ limited drops đến everyday essentials.
        </p>

        <div class="kv-hero__actions">
          <RouterLink to="/shop" class="kv-btn-primary-hero" id="hero-shop-btn">
            Khám phá ngay
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
              <line x1="5" y1="12" x2="19" y2="12"/><polyline points="12 5 19 12 12 19"/>
            </svg>
          </RouterLink>
          <RouterLink to="/shop?cat=new" class="kv-btn-ghost-hero" id="hero-new-btn">
            Hàng mới về
          </RouterLink>
        </div>

        <!-- Stats Row -->
        <div class="kv-hero__stats">
          <div v-for="stat in stats" :key="stat.label" class="kv-hero__stat">
            <span class="kv-hero__stat-value">{{ stat.value }}</span>
            <span class="kv-hero__stat-label">{{ stat.label }}</span>
          </div>
        </div>
      </div>

      <!-- Scroll indicator -->
      <div class="kv-hero__scroll" aria-hidden="true">
        <div class="kv-hero__scroll-line"></div>
        <span>Scroll</span>
      </div>
    </section>

    <!-- ═══════════════════════════════════════
         COLLECTIONS — 3-col grid
    ═══════════════════════════════════════ -->
    <section class="kv-collections kv-section" id="collections" aria-label="Bộ sưu tập">
      <div class="kv-container">
        <div class="kv-section-header">
          <div class="kv-section-tag">Bộ sưu tập</div>
          <h2 class="kv-section-title">Chọn phong cách của bạn</h2>
        </div>

        <div class="kv-collections__grid" :class="{ 'kv-collections__grid--mobile': isMobile }">
          <RouterLink
            v-for="col in collections"
            :key="col.id"
            :to="col.to"
            class="kv-collection-card"
            :id="`collection-${col.id}`"
          >
            <div class="kv-collection-card__img-wrap">
              <img :src="col.img" :alt="col.label" class="kv-collection-card__img" loading="lazy" />
              <div class="kv-collection-card__overlay"></div>
            </div>
            <div class="kv-collection-card__info">
              <span class="kv-collection-card__tag">{{ col.tag }}</span>
              <h3 class="kv-collection-card__name">{{ col.label }}</h3>
              <span class="kv-collection-card__cta">
                Xem ngay
                <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
                  <line x1="5" y1="12" x2="19" y2="12"/><polyline points="12 5 19 12 12 19"/>
                </svg>
              </span>
            </div>
          </RouterLink>
        </div>
      </div>
    </section>

    <!-- ═══════════════════════════════════════
         FEATURED PRODUCTS
    ═══════════════════════════════════════ -->
    <section class="kv-featured kv-section" id="featured-products" aria-label="Sản phẩm nổi bật">
      <div class="kv-container">
        <div class="kv-section-header kv-section-header--split">
          <div>
            <div class="kv-section-tag">Hot này tuần</div>
            <h2 class="kv-section-title">Bán chạy nhất</h2>
          </div>
          <RouterLink to="/shop" class="kv-link-cta" id="view-all-products-link">
            Xem tất cả
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <line x1="5" y1="12" x2="19" y2="12"/><polyline points="12 5 19 12 12 19"/>
            </svg>
          </RouterLink>
        </div>
        <div class="kv-product-grid" :class="{ 'kv-product-grid--mobile': isMobile }">
          <KvProductCard
            v-for="p in featured"
            :key="p.id"
            :product="p"
            @add-to-cart="cart.addItem"
          />
        </div>
      </div>
    </section>

    <!-- ═══════════════════════════════════════
         EDITORIAL SPLIT — Brand story
    ═══════════════════════════════════════ -->
    <section class="kv-editorial kv-section" id="editorial" aria-label="Câu chuyện thương hiệu">
      <div class="kv-container">
        <div class="kv-editorial__grid" :class="{ 'kv-editorial__grid--mobile': isMobile }">
          <!-- Image -->
          <div class="kv-editorial__media">
            <img
              src="https://images.unsplash.com/photo-1549298916-b41d501d3772?w=1000&q=85&auto=format&fit=crop"
              alt="KickVerse — Crafted for the street"
              class="kv-editorial__img"
              loading="lazy"
            />
            <div class="kv-editorial__media-badge">
              <span>Since 2024</span>
            </div>
          </div>

          <!-- Text -->
          <div class="kv-editorial__text">
            <div class="kv-section-tag">Về chúng tôi</div>
            <h2 class="kv-editorial__title">
              Chất liệu.<br/>Form dáng.<br/>
              <span class="kv-text-gold">Cảm giác đi.</span>
            </h2>
            <p class="kv-editorial__desc">
              Mỗi đôi giày trên KickVerse được chọn lọc theo tiêu chí thực tế: đế êm, form chuẩn streetwear, và bảng size rõ ràng trước khi bạn thêm vào giỏ hàng.
            </p>
            <p class="kv-editorial__desc">
              Chúng tôi tin rằng sneaker không chỉ là thời trang — đó là cách bạn bước đi trong cuộc sống.
            </p>
            <RouterLink to="/support" class="kv-btn-outline" id="editorial-learn-more-btn">
              Tìm hiểu thêm
            </RouterLink>
          </div>
        </div>
      </div>
    </section>

    <!-- ═══════════════════════════════════════
         MEMBERSHIP CTA — Gold gradient
    ═══════════════════════════════════════ -->
    <section class="kv-membership" id="membership" aria-label="Thành viên KickVerse">
      <div class="kv-container kv-membership__inner" :class="{ 'kv-membership__inner--mobile': isMobile }">
        <div class="kv-membership__text">
          <div class="kv-section-tag kv-section-tag--dark">Thành viên</div>
          <h2 class="kv-membership__title">Trở thành KickVerse Member</h2>
          <p class="kv-membership__desc">
            Nhận thông báo drop mới sớm nhất, theo dõi đơn và lưu wishlist trên mọi thiết bị.
          </p>
        </div>
        <RouterLink to="/register" class="kv-btn-membership" id="membership-register-btn">
          Tạo tài khoản miễn phí
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
            <line x1="5" y1="12" x2="19" y2="12"/><polyline points="12 5 19 12 12 19"/>
          </svg>
        </RouterLink>
      </div>
    </section>

  </div>
</template>

<style scoped>
/* ── HOME WRAPPER ── */
.kv-home { background: #0a0a0a; color: #f5f4f0; }

/* ══════════════════════════════════════
   HERO
══════════════════════════════════════ */
.kv-hero {
  position: relative;
  min-height: 100svh;
  display: flex;
  align-items: center;
  overflow: hidden;
}

.kv-hero__bg {
  position: absolute;
  inset: 0;
  z-index: 0;
}

.kv-hero__bg-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center 30%;
  filter: saturate(0.7);
}

.kv-hero__bg-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(
    105deg,
    rgb(10 10 10 / 0.95) 0%,
    rgb(10 10 10 / 0.8) 45%,
    rgb(10 10 10 / 0.3) 100%
  );
}

/* Grain noise texture */
.kv-hero__noise {
  position: absolute;
  inset: 0;
  z-index: 1;
  opacity: 0.03;
  background-image: url("data:image/svg+xml,%3Csvg viewBox='0 0 256 256' xmlns='http://www.w3.org/2000/svg'%3E%3Cfilter id='noise'%3E%3CfeTurbulence type='fractalNoise' baseFrequency='0.9' numOctaves='4' stitchTiles='stitch'/%3E%3C/filter%3E%3Crect width='100%25' height='100%25' filter='url(%23noise)' opacity='1'/%3E%3C/svg%3E");
  background-size: 200px;
  pointer-events: none;
}

.kv-hero__content {
  position: relative;
  z-index: 2;
  padding-top: 80px;
  padding-bottom: 120px;
}

/* Eyebrow */
.kv-hero__eyebrow {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 3px;
  text-transform: uppercase;
  color: #c8a96e;
  margin-bottom: 28px;
  animation: kv-fade-in-up 0.6s 0.1s both;
}

.kv-hero__eyebrow-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: #c8a96e;
  animation: kv-pulse-gold 2s ease infinite;
  flex-shrink: 0;
}

/* Title */
.kv-hero__title {
  display: flex;
  flex-direction: column;
  font-family: 'Bebas Neue', sans-serif;
  font-size: clamp(64px, 10vw, 120px);
  line-height: 0.92;
  letter-spacing: -1px;
  color: #f5f4f0;
  margin: 0 0 32px;
}

.kv-hero__title-line { display: block; }

.kv-hero__title-line--accent {
  background: linear-gradient(135deg, #c8a96e 0%, #e8c97e 50%, #b8944e 100%);
  background-clip: text;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.kv-hero__title-line:nth-child(1) { animation: kv-fade-in-up 0.6s 0.2s both; }
.kv-hero__title-line:nth-child(2) { animation: kv-fade-in-up 0.6s 0.35s both; }
.kv-hero__title-line:nth-child(3) { animation: kv-fade-in-up 0.6s 0.5s both; }

.kv-hero__desc {
  max-width: 480px;
  font-size: 16px;
  line-height: 1.7;
  color: rgb(245 244 240 / 0.65);
  margin-bottom: 48px;
  animation: kv-fade-in-up 0.6s 0.6s both;
}

/* Hero Buttons */
.kv-hero__actions {
  display: flex;
  flex-wrap: wrap;
  gap: 14px;
  margin-bottom: 64px;
  animation: kv-fade-in-up 0.6s 0.7s both;
}

.kv-btn-primary-hero {
  display: inline-flex;
  align-items: center;
  gap: 10px;
  padding: 14px 28px;
  font-size: 13px;
  font-weight: 700;
  letter-spacing: 1.5px;
  text-transform: uppercase;
  color: #0a0a0a;
  background: #c8a96e;
  border-radius: 6px;
  text-decoration: none;
  transition: background 0.2s, transform 0.2s, box-shadow 0.2s;
}

.kv-btn-primary-hero:hover {
  background: #d4ba80;
  transform: translateY(-2px);
  box-shadow: 0 12px 32px rgb(200 169 110 / 0.3);
}

.kv-btn-ghost-hero {
  display: inline-flex;
  align-items: center;
  padding: 14px 28px;
  font-size: 13px;
  font-weight: 700;
  letter-spacing: 1.5px;
  text-transform: uppercase;
  color: #f5f4f0;
  background: transparent;
  border: 1px solid rgb(255 255 255 / 0.2);
  border-radius: 6px;
  text-decoration: none;
  transition: border-color 0.2s, background 0.2s, color 0.2s;
}

.kv-btn-ghost-hero:hover {
  border-color: #c8a96e;
  color: #c8a96e;
  background: rgb(200 169 110 / 0.05);
}

/* Stats */
.kv-hero__stats {
  display: flex;
  flex-wrap: wrap;
  gap: 40px;
  animation: kv-fade-in-up 0.6s 0.85s both;
}

.kv-hero__stat {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.kv-hero__stat-value {
  font-family: 'Bebas Neue', sans-serif;
  font-size: 36px;
  letter-spacing: 1px;
  color: #c8a96e;
  line-height: 1;
}

.kv-hero__stat-label {
  font-size: 11px;
  font-weight: 600;
  letter-spacing: 1.5px;
  text-transform: uppercase;
  color: rgb(245 244 240 / 0.45);
}

/* Scroll Indicator */
.kv-hero__scroll {
  position: absolute;
  bottom: 40px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 2;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  animation: kv-fade-in-up 0.6s 1.2s both;
}

.kv-hero__scroll-line {
  width: 1px;
  height: 48px;
  background: linear-gradient(to bottom, #c8a96e, transparent);
  animation: kv-float 2s ease-in-out infinite;
}

.kv-hero__scroll span {
  font-size: 9px;
  font-weight: 700;
  letter-spacing: 3px;
  text-transform: uppercase;
  color: rgb(245 244 240 / 0.35);
}

/* ══════════════════════════════════════
   SECTION COMMON
══════════════════════════════════════ */
.kv-section-header {
  margin-bottom: 48px;
}

.kv-section-header--split {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  gap: 24px;
}

.kv-section-tag {
  display: inline-block;
  font-size: 10px;
  font-weight: 700;
  letter-spacing: 2.5px;
  text-transform: uppercase;
  color: #c8a96e;
  margin-bottom: 12px;
  padding: 4px 10px;
  background: rgb(200 169 110 / 0.1);
  border: 1px solid rgb(200 169 110 / 0.2);
  border-radius: 4px;
}

.kv-section-tag--dark {
  color: rgb(10 10 10 / 0.7);
  background: rgb(10 10 10 / 0.06);
  border-color: rgb(10 10 10 / 0.12);
}

.kv-section-title {
  font-size: clamp(32px, 4vw, 52px);
  font-weight: 800;
  letter-spacing: -1px;
  line-height: 1.05;
  color: #f5f4f0;
  margin: 0;
}

.kv-link-cta {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  font-size: 13px;
  font-weight: 700;
  letter-spacing: 0.5px;
  color: #c8a96e;
  text-decoration: none;
  white-space: nowrap;
  transition: gap 0.2s, opacity 0.2s;
  flex-shrink: 0;
  padding-bottom: 4px;
}

.kv-link-cta:hover { gap: 12px; opacity: 0.8; }

/* ══════════════════════════════════════
   COLLECTIONS
══════════════════════════════════════ */
.kv-collections {
  background: #0a0a0a;
}

.kv-collections__grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 16px;
}

.kv-collections__grid--mobile {
  grid-template-columns: 1fr;
}

.kv-collection-card {
  position: relative;
  display: block;
  border-radius: 12px;
  overflow: hidden;
  aspect-ratio: 3 / 4;
  text-decoration: none;
  group: true;
}

.kv-collection-card__img-wrap {
  position: absolute;
  inset: 0;
}

.kv-collection-card__img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.6s cubic-bezier(0.25, 0.46, 0.45, 0.94), filter 0.4s;
  filter: saturate(0.8);
}

.kv-collection-card:hover .kv-collection-card__img {
  transform: scale(1.06);
  filter: saturate(1);
}

.kv-collection-card__overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(to top, rgb(10 10 10 / 0.9) 0%, rgb(10 10 10 / 0.1) 60%, transparent 100%);
  transition: opacity 0.3s;
}

.kv-collection-card:hover .kv-collection-card__overlay {
  background: linear-gradient(to top, rgb(10 10 10 / 0.95) 0%, rgb(10 10 10 / 0.3) 60%, transparent 100%);
}

.kv-collection-card__info {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 28px 24px;
  display: flex;
  flex-direction: column;
  gap: 6px;
  z-index: 1;
}

.kv-collection-card__tag {
  font-size: 10px;
  font-weight: 700;
  letter-spacing: 2px;
  text-transform: uppercase;
  color: #c8a96e;
}

.kv-collection-card__name {
  font-family: 'Bebas Neue', sans-serif;
  font-size: 40px;
  letter-spacing: 1px;
  line-height: 1;
  color: #f5f4f0;
  margin: 0;
}

.kv-collection-card__cta {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 1px;
  text-transform: uppercase;
  color: rgb(245 244 240 / 0.5);
  transition: color 0.2s, gap 0.2s;
  margin-top: 4px;
}

.kv-collection-card:hover .kv-collection-card__cta {
  color: #c8a96e;
  gap: 12px;
}

/* ══════════════════════════════════════
   FEATURED PRODUCTS
══════════════════════════════════════ */
.kv-featured {
  background: #111111;
}

.kv-product-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
}

.kv-product-grid--mobile {
  grid-template-columns: repeat(2, 1fr);
}

/* ══════════════════════════════════════
   EDITORIAL
══════════════════════════════════════ */
.kv-editorial {
  background: #0a0a0a;
}

.kv-editorial__grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 80px;
  align-items: center;
}

.kv-editorial__grid--mobile {
  grid-template-columns: 1fr;
  gap: 40px;
}

.kv-editorial__media {
  position: relative;
  border-radius: 16px;
  overflow: hidden;
  aspect-ratio: 4 / 5;
}

.kv-editorial__img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.8s cubic-bezier(0.25, 0.46, 0.45, 0.94);
}

.kv-editorial__media:hover .kv-editorial__img {
  transform: scale(1.04);
}

.kv-editorial__media-badge {
  position: absolute;
  bottom: 24px;
  right: 24px;
  padding: 10px 18px;
  background: rgb(200 169 110 / 0.9);
  backdrop-filter: blur(12px);
  border-radius: 6px;
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 1px;
  text-transform: uppercase;
  color: #0a0a0a;
}

.kv-editorial__title {
  font-size: clamp(36px, 4vw, 52px);
  font-weight: 800;
  letter-spacing: -1px;
  line-height: 1.1;
  color: #f5f4f0;
  margin: 16px 0 28px;
}

.kv-text-gold {
  background: linear-gradient(135deg, #c8a96e 0%, #e8c97e 50%, #b8944e 100%);
  background-clip: text;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.kv-editorial__desc {
  font-size: 15px;
  line-height: 1.8;
  color: rgb(245 244 240 / 0.6);
  margin-bottom: 16px;
}

.kv-btn-outline {
  display: inline-flex;
  align-items: center;
  margin-top: 12px;
  padding: 12px 24px;
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 1.5px;
  text-transform: uppercase;
  color: #f5f4f0;
  border: 1px solid rgb(255 255 255 / 0.2);
  border-radius: 6px;
  text-decoration: none;
  transition: border-color 0.2s, color 0.2s, background 0.2s;
}

.kv-btn-outline:hover {
  border-color: #c8a96e;
  color: #c8a96e;
  background: rgb(200 169 110 / 0.05);
}

/* ══════════════════════════════════════
   MEMBERSHIP CTA
══════════════════════════════════════ */
.kv-membership {
  background: linear-gradient(135deg, #1a1408 0%, #0a0a0a 40%, #0d0d0d 100%);
  border-top: 1px solid rgb(200 169 110 / 0.1);
  border-bottom: 1px solid rgb(200 169 110 / 0.1);
  padding-block: 96px;
  position: relative;
  overflow: hidden;
}

.kv-membership::before {
  content: 'KICKVERSE';
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-family: 'Bebas Neue', sans-serif;
  font-size: clamp(80px, 15vw, 200px);
  letter-spacing: 8px;
  color: rgb(200 169 110 / 0.03);
  white-space: nowrap;
  pointer-events: none;
  user-select: none;
}

.kv-membership__inner {
  position: relative;
  z-index: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 48px;
}

.kv-membership__inner--mobile {
  flex-direction: column;
  align-items: flex-start;
}

.kv-membership__title {
  font-size: clamp(28px, 3.5vw, 44px);
  font-weight: 800;
  letter-spacing: -1px;
  line-height: 1.1;
  color: #f5f4f0;
  margin: 12px 0 16px;
}

.kv-membership__desc {
  font-size: 15px;
  line-height: 1.7;
  color: rgb(245 244 240 / 0.55);
  max-width: 440px;
  margin: 0;
}

.kv-btn-membership {
  display: inline-flex;
  align-items: center;
  gap: 12px;
  padding: 16px 32px;
  font-size: 13px;
  font-weight: 700;
  letter-spacing: 1.5px;
  text-transform: uppercase;
  color: #0a0a0a;
  background: linear-gradient(135deg, #c8a96e, #e8c97e, #c8a96e);
  background-size: 200% auto;
  border-radius: 8px;
  text-decoration: none;
  white-space: nowrap;
  flex-shrink: 0;
  transition: background-position 0.4s, transform 0.2s, box-shadow 0.2s;
  box-shadow: 0 8px 24px rgb(200 169 110 / 0.2);
}

.kv-btn-membership:hover {
  background-position: right center;
  transform: translateY(-2px);
  box-shadow: 0 16px 40px rgb(200 169 110 / 0.35);
}
</style>
