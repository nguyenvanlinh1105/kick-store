<script setup>
import { computed, ref } from 'vue'
import { useRoute } from 'vue-router'
import KvFilterPill from '@/components/ui/KvFilterPill.vue'
import KvProductCard from '@/components/ui/KvProductCard.vue'
import { DEMO_PRODUCTS } from '@/data/demo'
import { useCartStore } from '@/stores/cart'
import { useResponsive } from '@/composables/useResponsive'

const route = useRoute()
const cart = useCartStore()
const { isMobile } = useResponsive()

const filters = ['Tất cả', 'Nike', 'Adidas', 'Jordan', 'New Balance', 'Puma', 'Converse']
const active = ref('Tất cả')

const products = computed(() => {
  let list = DEMO_PRODUCTS
  // Filter by query category if exists
  if (route.query.cat) {
    list = list.filter((p) => p.category?.toLowerCase() === route.query.cat.toLowerCase())
  }
  if (active.value === 'Tất cả') return list
  return list.filter((p) => p.brand === active.value)
})
</script>

<template>
  <div class="kv-shop">
    <!-- Header Banner -->
    <header class="kv-shop-header">
      <div class="kv-container">
        <div class="kv-shop-header__inner">
          <div class="kv-shop-header__text">
            <span class="kv-section-tag">Catalog</span>
            <h1 class="kv-shop-header__title">Cửa hàng</h1>
            <p class="kv-shop-header__desc">
              Khám phá {{ products.length }} sản phẩm tuyển chọn từ các thương hiệu hàng đầu.
            </p>
          </div>
          
          <!-- Brand filter row -->
          <div class="kv-shop-header__filters">
            <div class="kv-shop-header__filters-scroll">
              <KvFilterPill
                v-for="f in filters"
                :key="f"
                :active="active === f"
                @click="active = f"
              >
                {{ f }}
              </KvFilterPill>
            </div>
          </div>
        </div>
      </div>
    </header>

    <!-- Main Products Area -->
    <main class="kv-shop-main">
      <div class="kv-container">
        
        <!-- Empty State -->
        <div v-if="products.length === 0" class="kv-shop-empty">
          <p class="kv-shop-empty__title">Không tìm thấy sản phẩm</p>
          <p class="kv-shop-empty__desc">Vui lòng chọn bộ lọc hoặc phân loại khác.</p>
        </div>

        <!-- Product Grid -->
        <div v-else class="kv-shop-grid" :class="{ 'kv-shop-grid--mobile': isMobile }">
          <KvProductCard
            v-for="p in products"
            :key="p.id"
            :product="p"
            class="kv-animate-fade-up"
            @add-to-cart="cart.addItem"
          />
        </div>

      </div>
    </main>
  </div>
</template>

<style scoped>
.kv-shop {
  background: #0a0a0a;
  min-height: 100vh;
  color: #f5f4f0;
}

/* Header */
.kv-shop-header {
  padding-top: 60px;
  padding-bottom: 40px;
  background: linear-gradient(to bottom, #111111 0%, #0a0a0a 100%);
  border-bottom: 1px solid rgb(255 255 255 / 0.04);
}

.kv-shop-header__inner {
  display: flex;
  flex-direction: column;
  gap: 32px;
}

.kv-shop-header__title {
  font-size: clamp(40px, 6vw, 64px);
  font-weight: 800;
  letter-spacing: -1.5px;
  line-height: 1;
  color: #f5f4f0;
  margin: 12px 0 8px;
}

.kv-shop-header__desc {
  font-size: 15px;
  color: rgb(245 244 240 / 0.55);
  margin: 0;
}

.kv-shop-header__filters {
  position: relative;
  width: 100%;
}

.kv-shop-header__filters-scroll {
  display: flex;
  gap: 8px;
  overflow-x: auto;
  padding-bottom: 8px;
  -webkit-overflow-scrolling: touch;
}

/* Hide scrollbar */
.kv-shop-header__filters-scroll::-webkit-scrollbar {
  display: none;
}
.kv-shop-header__filters-scroll {
  -ms-overflow-style: none;
  scrollbar-width: none;
}

/* Grid Area */
.kv-shop-main {
  padding-top: 48px;
  padding-bottom: 120px;
}

.kv-shop-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

@media (max-width: 959px) {
  .kv-shop-grid {
    grid-template-columns: repeat(2, 1fr);
    gap: 16px;
  }
}

@media (max-width: 599px) {
  .kv-shop-grid {
    grid-template-columns: 1fr;
    gap: 16px;
  }
}

/* Empty state */
.kv-shop-empty {
  padding: 120px 24px;
  text-align: center;
  background: #111111;
  border: 1px solid rgb(255 255 255 / 0.04);
  border-radius: 12px;
}

.kv-shop-empty__title {
  font-size: 20px;
  font-weight: 700;
  color: #f5f4f0;
  margin: 0 0 8px;
}

.kv-shop-empty__desc {
  font-size: 14px;
  color: rgb(245 244 240 / 0.55);
  margin: 0;
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
