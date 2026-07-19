<script setup>
import { computed, ref } from 'vue'
import { useRoute } from 'vue-router'
import KvBadge from '@/components/ui/KvBadge.vue'
import { DEMO_PRODUCTS } from '@/data/demo'
import { useCartStore } from '@/stores/cart'
import { useWishlistStore } from '@/stores/wishlist'
import { useResponsive } from '@/composables/useResponsive'

const route = useRoute()
const cart = useCartStore()
const wishlist = useWishlistStore()
const { isMobile } = useResponsive()

const product = computed(
  () => DEMO_PRODUCTS.find((p) => p.slug === route.params.slug) || DEMO_PRODUCTS[0],
)

const size = ref(null)
const color = ref(null)

function ensureDefaults() {
  if (product.value) {
    if (!size.value && product.value.sizes) size.value = product.value.sizes[0]
    if (!color.value && product.value.colors) color.value = product.value.colors[0]
  }
}
ensureDefaults()
</script>

<template>
  <div class="kv-detail">
    <div class="kv-container">
      
      <!-- Back Link -->
      <div class="kv-detail__header">
        <RouterLink to="/shop" class="kv-detail__back">
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <polyline points="15 18 9 12 15 6"/>
          </svg>
          Quay lại cửa hàng
        </RouterLink>
      </div>

      <!-- Main Layout Grid -->
      <div class="kv-detail__grid" :class="{ 'kv-detail__grid--mobile': isMobile }">
        
        <!-- Left: Image Gallery -->
        <div class="kv-detail__media-panel">
          <div class="kv-detail__image-box">
            <img :src="product.image" :alt="product.name" class="kv-detail__img animate-fade-in" />
            <div class="kv-detail__image-overlay"></div>
          </div>
        </div>

        <!-- Right: Info Panel -->
        <div class="kv-detail__info-panel">
          
          <!-- Category & Brand -->
          <div class="kv-detail__meta">
            <span class="kv-detail__brand">{{ product.brand }}</span>
            <span class="kv-detail__cat" v-if="product.category">{{ product.category }}</span>
          </div>

          <!-- Product Name -->
          <h1 class="kv-detail__title">{{ product.name }}</h1>

          <!-- Price Row -->
          <div class="kv-detail__price-row">
            <span class="kv-detail__price">{{ product.priceLabel }}</span>
            <span v-if="product.compareAt" class="kv-detail__compare">{{ product.compareAt }}</span>
            <KvBadge v-if="product.badge" :variant="product.badgeVariant || 'gold'" class="kv-detail__badge">
              {{ product.badge }}
            </KvBadge>
          </div>

          <!-- Description -->
          <p class="kv-detail__desc">
            Trải nghiệm phong cách đỉnh cao với dòng giày thể thao cao cấp. Chất liệu da/mesh bền bỉ, lưỡi gà êm ái cùng bộ đế đệm giảm chấn giúp bạn tự tin di chuyển suốt ngày dài.
          </p>

          <!-- Colors -->
          <div class="kv-detail__options" v-if="product.colors && product.colors.length">
            <h3 class="kv-detail__option-title">Chọn màu</h3>
            <div class="kv-detail__option-list">
              <button
                v-for="c in product.colors"
                :key="c"
                type="button"
                class="kv-detail__color-btn"
                :class="{ 'kv-detail__color-btn--active': color === c }"
                @click="color = c"
              >
                {{ c }}
              </button>
            </div>
          </div>

          <!-- Sizes -->
          <div class="kv-detail__options" v-if="product.sizes && product.sizes.length">
            <h3 class="kv-detail__option-title">Chọn Size (US)</h3>
            <div class="kv-detail__option-list kv-detail__option-list--grid">
              <button
                v-for="s in product.sizes"
                :key="s"
                type="button"
                class="kv-detail__size-btn"
                :class="{ 'kv-detail__size-btn--active': size === s }"
                @click="size = s"
              >
                {{ s }}
              </button>
            </div>
          </div>

          <!-- Actions -->
          <div class="kv-detail__actions">
            <button
              type="button"
              class="kv-detail__add-btn"
              @click="cart.addItem(product, { size, color })"
            >
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
                <path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z"/><line x1="3" y1="6" x2="21" y2="6"/><path d="M16 10a4 4 0 0 1-8 0"/>
              </svg>
              Thêm vào giỏ hàng
            </button>
            
            <button
              type="button"
              class="kv-detail__wish-btn"
              :class="{ 'kv-detail__wish-btn--active': wishlist.has(product.id) }"
              @click="wishlist.toggle(product)"
            >
              <svg width="16" height="16" viewBox="0 0 24 24" :fill="wishlist.has(product.id) ? 'currentColor' : 'none'" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/>
              </svg>
            </button>
          </div>

          <!-- Brand Guarantees -->
          <div class="kv-detail__guarantees">
            <div class="kv-detail__guarantee">
              <span class="kv-detail__guarantee-icon">⚡</span>
              <div>
                <p class="kv-detail__guarantee-title">Giao Hàng Siêu Tốc 24H</p>
                <p class="kv-detail__guarantee-desc">Nội thành TP.HCM & Hà Nội nhận hàng sớm nhất.</p>
              </div>
            </div>
            <div class="kv-detail__guarantee">
              <span class="kv-detail__guarantee-icon">🔄</span>
              <div>
                <p class="kv-detail__guarantee-title">Hỗ Trợ Đổi Size Miễn Phí</p>
                <p class="kv-detail__guarantee-desc">Bảo hành 14 ngày, đổi size thuận tiện nếu còn tem mác.</p>
              </div>
            </div>
          </div>

        </div>

      </div>

    </div>
  </div>
</template>

<style scoped>
.kv-detail {
  background: #0a0a0a;
  min-height: 100vh;
  padding-top: 40px;
  padding-bottom: 120px;
  color: #f5f4f0;
}

.kv-detail__header {
  margin-bottom: 32px;
}

.kv-detail__back {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  font-weight: 500;
  color: rgb(245 244 240 / 0.45);
  text-decoration: none;
  transition: color 0.2s;
}

.kv-detail__back:hover { color: #c8a96e; }

.kv-detail__grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 60px;
}

.kv-detail__grid--mobile {
  grid-template-columns: 1fr;
  gap: 40px;
}

/* Image gallery panel */
.kv-detail__media-panel {
  width: 100%;
}

.kv-detail__image-box {
  position: relative;
  width: 100%;
  aspect-ratio: 1 / 1;
  background: #111111;
  border: 1px solid rgb(255 255 255 / 0.05);
  border-radius: 16px;
  overflow: hidden;
}

.kv-detail__img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

.kv-detail__image-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(to top, rgb(10 10 10 / 0.2) 0%, transparent 100%);
}

/* Info Panel */
.kv-detail__info-panel {
  display: flex;
  flex-direction: column;
}

.kv-detail__meta {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 16px;
}

.kv-detail__brand {
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 2px;
  text-transform: uppercase;
  color: #c8a96e;
  padding: 4px 10px;
  background: rgb(200 169 110 / 0.1);
  border: 1px solid rgb(200 169 110 / 0.2);
  border-radius: 4px;
}

.kv-detail__cat {
  font-size: 11px;
  font-weight: 600;
  letter-spacing: 1px;
  text-transform: uppercase;
  color: rgb(245 244 240 / 0.4);
}

.kv-detail__title {
  font-size: clamp(32px, 4vw, 48px);
  font-weight: 800;
  letter-spacing: -1.5px;
  line-height: 1.1;
  color: #f5f4f0;
  margin: 0 0 20px;
}

.kv-detail__price-row {
  display: flex;
  align-items: baseline;
  gap: 16px;
  margin-bottom: 24px;
}

.kv-detail__price {
  font-size: clamp(24px, 3vw, 32px);
  font-weight: 700;
  color: #f5f4f0;
}

.kv-detail__compare {
  font-size: 16px;
  color: rgb(245 244 240 / 0.4);
  text-decoration: line-through;
}

.kv-detail__desc {
  font-size: 15px;
  line-height: 1.7;
  color: rgb(245 244 240 / 0.65);
  margin: 0 0 36px;
}

/* Options */
.kv-detail__options {
  margin-bottom: 28px;
}

.kv-detail__option-title {
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 1px;
  text-transform: uppercase;
  color: rgb(245 244 240 / 0.5);
  margin: 0 0 12px;
}

.kv-detail__option-list {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.kv-detail__option-list--grid {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
}

@media (max-width: 639px) {
  .kv-detail__option-list--grid {
    grid-template-columns: repeat(4, 1fr);
  }
}

/* Color & Size buttons */
.kv-detail__color-btn {
  padding: 10px 20px;
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 0.5px;
  color: rgb(245 244 240 / 0.7);
  background: rgb(255 255 255 / 0.03);
  border: 1px solid rgb(255 255 255 / 0.08);
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.kv-detail__color-btn:hover {
  color: #f5f4f0;
  border-color: rgb(255 255 255 / 0.2);
}

.kv-detail__color-btn--active {
  color: #0a0a0a;
  background: #c8a96e;
  border-color: #c8a96e;
  box-shadow: 0 4px 12px rgb(200 169 110 / 0.2);
}

.kv-detail__size-btn {
  height: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 13px;
  font-weight: 700;
  color: rgb(245 244 240 / 0.7);
  background: rgb(255 255 255 / 0.03);
  border: 1px solid rgb(255 255 255 / 0.08);
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.kv-detail__size-btn:hover {
  color: #f5f4f0;
  border-color: rgb(255 255 255 / 0.2);
}

.kv-detail__size-btn--active {
  color: #0a0a0a;
  background: #c8a96e;
  border-color: #c8a96e;
  box-shadow: 0 4px 12px rgb(200 169 110 / 0.2);
}

/* Actions Row */
.kv-detail__actions {
  display: flex;
  gap: 12px;
  margin-top: 12px;
  margin-bottom: 40px;
}

.kv-detail__add-btn {
  flex: 1;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  height: 52px;
  font-size: 13px;
  font-weight: 800;
  letter-spacing: 1.5px;
  text-transform: uppercase;
  color: #0a0a0a;
  background: linear-gradient(135deg, #c8a96e 0%, #e8c97e 50%, #c8a96e 100%);
  background-size: 200% auto;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 8px 24px rgb(200 169 110 / 0.2);
}

.kv-detail__add-btn:hover {
  background-position: right center;
  transform: translateY(-1px);
  box-shadow: 0 12px 32px rgb(200 169 110 / 0.35);
}

.kv-detail__wish-btn {
  width: 52px;
  height: 52px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: rgb(245 244 240 / 0.7);
  background: rgb(255 255 255 / 0.04);
  border: 1px solid rgb(255 255 255 / 0.08);
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.kv-detail__wish-btn:hover {
  color: #f5f4f0;
  border-color: rgb(255 255 255 / 0.2);
}

.kv-detail__wish-btn--active {
  color: #ef4444;
  border-color: rgb(239 68 68 / 0.2);
  background: rgb(239 68 68 / 0.08);
}

/* Guarantees */
.kv-detail__guarantees {
  border-top: 1px solid rgb(255 255 255 / 0.05);
  padding-top: 32px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.kv-detail__guarantee {
  display: flex;
  gap: 16px;
}

.kv-detail__guarantee-icon {
  font-size: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  background: rgb(255 255 255 / 0.03);
  border: 1px solid rgb(255 255 255 / 0.06);
  border-radius: 50%;
  flex-shrink: 0;
}

.kv-detail__guarantee-title {
  font-size: 13.5px;
  font-weight: 700;
  color: #f5f4f0;
  margin: 0 0 4px;
}

.kv-detail__guarantee-desc {
  font-size: 12.5px;
  color: rgb(245 244 240 / 0.5);
  margin: 0;
}
</style>
