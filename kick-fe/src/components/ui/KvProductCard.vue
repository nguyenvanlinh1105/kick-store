<script setup>
import { RouterLink } from 'vue-router'
import KvBadge from './KvBadge.vue'

defineProps({
  product: {
    type: Object,
    required: true,
  },
  dark: {
    type: Boolean,
    default: true,
  },
})

defineEmits(['add-to-cart'])
</script>

<template>
  <article class="kv-card-premium">
    <!-- Image Area -->
    <RouterLink :to="`/shop/${product.slug}`" class="kv-card-premium__image-wrap">
      <img
        :src="product.image"
        :alt="product.name"
        class="kv-card-premium__img"
        loading="lazy"
      />
      <div class="kv-card-premium__overlay"></div>
      
      <!-- Badges -->
      <div class="kv-card-premium__badges" v-if="product.badge">
        <KvBadge :variant="product.badgeVariant || 'gold'">
          {{ product.badge }}
        </KvBadge>
      </div>
    </RouterLink>

    <!-- Info Area -->
    <div class="kv-card-premium__info">
      <div class="kv-card-premium__header">
        <span class="kv-card-premium__brand">{{ product.brand }}</span>
        <RouterLink :to="`/shop/${product.slug}`" class="kv-card-premium__title">
          {{ product.name }}
        </RouterLink>
      </div>

      <div class="kv-card-premium__footer">
        <div class="kv-card-premium__price-box">
          <span class="kv-card-premium__price">{{ product.priceLabel }}</span>
          <span v-if="product.compareAt" class="kv-card-premium__compare">{{ product.compareAt }}</span>
        </div>
        
        <button
          type="button"
          class="kv-card-premium__buy-btn"
          aria-label="Thêm vào giỏ hàng"
          @click.stop.prevent="$emit('add-to-cart', product)"
        >
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
            <line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/>
          </svg>
          MUA
        </button>
      </div>
    </div>
  </article>
</template>

<style scoped>
.kv-card-premium {
  display: flex;
  flex-direction: column;
  background: #111111;
  border: 1px solid rgb(255 255 255 / 0.04);
  border-radius: 12px;
  overflow: hidden;
  transition: all 0.3s cubic-bezier(0.16, 1, 0.3, 1);
  position: relative;
}

.kv-card-premium:hover {
  transform: translateY(-4px);
  border-color: rgb(200 169 110 / 0.15);
  box-shadow: 0 12px 32px rgb(0 0 0 / 0.6);
}

.kv-card-premium__image-wrap {
  position: relative;
  width: 100%;
  aspect-ratio: 1 / 1;
  overflow: hidden;
  display: block;
  background: #181818;
}

.kv-card-premium__img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.6s cubic-bezier(0.16, 1, 0.3, 1);
}

.kv-card-premium:hover .kv-card-premium__img {
  transform: scale(1.05);
}

.kv-card-premium__overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(to top, rgb(10 10 10 / 0.2) 0%, transparent 100%);
  pointer-events: none;
}

.kv-card-premium__badges {
  position: absolute;
  top: 12px;
  left: 12px;
  z-index: 2;
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}

/* Info */
.kv-card-premium__info {
  padding: 20px;
  display: flex;
  flex-direction: column;
  flex: 1;
  gap: 16px;
}

.kv-card-premium__header {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.kv-card-premium__brand {
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 1.5px;
  text-transform: uppercase;
  color: #c8a96e;
}

.kv-card-premium__title {
  font-size: 16px;
  font-weight: 600;
  color: #f5f4f0;
  line-height: 1.4;
  text-decoration: none;
  transition: color 0.2s ease;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.kv-card-premium__title:hover {
  color: #c8a96e;
}

/* Footer */
.kv-card-premium__footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: auto;
  gap: 12px;
}

.kv-card-premium__price-box {
  display: flex;
  flex-direction: column;
}

.kv-card-premium__price {
  font-size: 16px;
  font-weight: 700;
  color: #f5f4f0;
}

.kv-card-premium__compare {
  font-size: 12px;
  color: rgb(245 244 240 / 0.4);
  text-decoration: line-through;
}

.kv-card-premium__buy-btn {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  height: 36px;
  padding: 0 16px;
  font-size: 11px;
  font-weight: 800;
  letter-spacing: 1px;
  color: #0a0a0a;
  background: #c8a96e;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.kv-card-premium__buy-btn:hover {
  background: #d4ba80;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgb(200 169 110 / 0.25);
}
</style>
