<script setup>
import { ref, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import KvProductCard from '@/components/ui/KvProductCard.vue'
import KvSkeleton from '@/components/ui/KvSkeleton.vue'
import KvEmptyState from '@/components/ui/KvEmptyState.vue'
import { DEMO_PRODUCTS } from '@/data/demo'
import { useCartStore } from '@/stores/cart'

const route = useRoute()
const router = useRouter()
const cart = useCartStore()

const viewState = ref('success')

const searchQuery = ref(route.query.q || '')
const searchInput = ref(searchQuery.value)
let debounceTimer = null

function onSearchInput() {
  clearTimeout(debounceTimer)
  debounceTimer = setTimeout(() => {
    searchQuery.value = searchInput.value
    router.replace({ query: { ...route.query, q: searchInput.value } })
  }, 300)
}

const searchResults = computed(() => {
  const q = searchQuery.value.trim().toLowerCase()
  if (!q) return DEMO_PRODUCTS
  return DEMO_PRODUCTS.filter(
    (p) => p.name.toLowerCase().includes(q) || p.brand.toLowerCase().includes(q) || p.category.toLowerCase().includes(q)
  )
})

watch(
  () => route.query.q,
  (newQ) => {
    if (newQ !== undefined) {
      searchQuery.value = newQ
      searchInput.value = newQ
    }
  }
)
</script>

<template>
  <div class="bg-slate-50 text-slate-900 min-h-screen py-10">
    <div class="kv-container">
      <!-- Search Header & AutoSuggest Input -->
      <div class="max-w-2xl mx-auto text-center mb-10 flex flex-col gap-4">
        <h1 class="text-3xl font-extrabold text-slate-900">Tìm Kiếm Sản Phẩm</h1>
        
        <div class="relative w-full">
          <input
            v-model="searchInput"
            type="text"
            placeholder="Nhập tên giày, thương hiệu (Nike, Adidas, Jordan...)"
            class="w-full h-12 px-5 pr-12 text-sm bg-white border-2 border-slate-200 rounded-2xl text-slate-900 placeholder-slate-400 focus:outline-none focus:border-amber-600 shadow-sm transition-all"
            @input="onSearchInput"
          />
          <span class="absolute right-4 top-1/2 -translate-y-1/2 text-slate-400">🔍</span>
        </div>

        <p v-if="searchQuery" class="text-xs text-slate-500">
          Kết quả tìm kiếm cho từ khóa: <strong class="text-amber-600 font-bold">"{{ searchQuery }}"</strong> ({{ searchResults.length }} kết quả)
        </p>
      </div>

      <!-- 1. LOADING STATE -->
      <div v-if="viewState === 'loading'" class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-6">
        <KvSkeleton v-for="i in 4" :key="i" type="card" height="300px" :dark="false" />
      </div>

      <!-- 2. EMPTY STATE -->
      <div v-else-if="searchResults.length === 0" class="py-12">
        <KvEmptyState
          :title="`Không tìm thấy kết quả cho '${searchQuery}'`"
          description="Hãy thử kiểm tra lại lỗi chính tả hoặc tìm kiếm bằng từ khóa ngắn hơn."
          action-text="Xem tất cả sản phẩm"
          @action="$router.push('/shop')"
        />
      </div>

      <!-- 3. SUCCESS SEARCH RESULTS GRID: 1 PRODUCT PER ROW ON MOBILE (grid-cols-1 sm:grid-cols-2 md:grid-cols-4) -->
      <div v-else class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-6">
        <KvProductCard
          v-for="p in searchResults"
          :key="p.id"
          :product="p"
          :dark="false"
          @add-to-cart="cart.addItem"
        />
      </div>
    </div>
  </div>
</template>
