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
  <div class="bg-surface-0 min-h-screen text-text-primary">
    <!-- Header Banner -->
    <header class="pt-16 pb-10 bg-gradient-to-b from-surface-1 to-surface-0 border-b border-white/5">
      <div class="kv-container">
        <div class="flex flex-col gap-8">
          <div class="flex flex-col">
            <span class="inline-block text-[10px] font-bold tracking-[2.5px] uppercase text-primary mb-3 px-2.5 py-1 bg-primary-dim border border-primary/20 rounded self-start">Catalog</span>
            <h1 class="text-4xl md:text-6xl font-extrabold tracking-tight leading-none text-white mt-3 mb-2">Cửa hàng</h1>
            <p class="text-sm text-text-muted m-0">
              Khám phá {{ products.length }} sản phẩm tuyển chọn từ các thương hiệu hàng đầu.
            </p>
          </div>
          
          <!-- Brand filter row -->
          <div class="relative w-full">
            <div class="flex gap-2 overflow-x-auto pb-2 [scrollbar-width:none] [-ms-overflow-style:none] [&::-webkit-scrollbar]:hidden">
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
    <main class="pt-12 pb-28">
      <div class="kv-container">
        
        <!-- Empty State -->
        <div v-if="products.length === 0" class="py-28 px-6 text-center bg-surface-1 border border-white/5 rounded-xl">
          <p class="text-xl font-bold text-white mb-2">Không tìm thấy sản phẩm</p>
          <p class="text-sm text-text-muted m-0">Vui lòng chọn bộ lọc hoặc phân loại khác.</p>
        </div>

        <!-- Product Grid -->
        <div v-else class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-5">
          <KvProductCard
            v-for="p in products"
            :key="p.id"
            :product="p"
            class="animate-fade-in-up"
            @add-to-cart="cart.addItem"
          />
        </div>

      </div>
    </main>
  </div>
</template>
