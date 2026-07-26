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

// View State Management (loading, empty, error, success)
const viewState = ref('success')
const errorMessage = ref('Lỗi kết nối khi tìm kiếm. Vui lòng thử lại!')

// Search Input & Debounce
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

// Filter matching products
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
  <div class="bg-surface-0 text-text-primary min-h-screen py-10">
    <div class="kv-container">
      <!-- Demo State Controller -->
      <div class="bg-neutral-900 border border-white/10 p-3 rounded-xl mb-6 flex items-center justify-between text-xs">
        <span class="font-bold text-primary">Search View State Demo:</span>
        <div class="flex gap-2">
          <button @click="viewState = 'success'" class="px-2 py-1 rounded text-[11px]" :class="[viewState === 'success' ? 'bg-primary text-black font-bold' : 'bg-white/5']">Success</button>
          <button @click="viewState = 'loading'" class="px-2 py-1 rounded text-[11px]" :class="[viewState === 'loading' ? 'bg-primary text-black font-bold' : 'bg-white/5']">Loading</button>
          <button @click="viewState = 'empty'" class="px-2 py-1 rounded text-[11px]" :class="[viewState === 'empty' ? 'bg-primary text-black font-bold' : 'bg-white/5']">Empty</button>
          <button @click="viewState = 'error'" class="px-2 py-1 rounded text-[11px]" :class="[viewState === 'error' ? 'bg-primary text-black font-bold' : 'bg-white/5']">Error</button>
        </div>
      </div>

      <!-- Search Header & AutoSuggest Input -->
      <div class="max-w-2xl mx-auto text-center mb-10 flex flex-col gap-4">
        <h1 class="text-3xl font-extrabold text-white">Tìm Kiếm Sản Phẩm</h1>
        
        <div class="relative w-full">
          <input
            v-model="searchInput"
            type="text"
            placeholder="Nhập tên giày, thương hiệu (Nike, Adidas, Jordan...)"
            class="w-full h-12 px-5 pr-12 text-sm bg-neutral-900 border-2 border-white/15 rounded-xl text-white placeholder-neutral-500 focus:outline-none focus:border-primary transition-all"
            @input="onSearchInput"
          />
          <span class="absolute right-4 top-1/2 -translate-y-1/2 text-neutral-400">🔍</span>
        </div>

        <p v-if="searchQuery" class="text-xs text-neutral-400">
          Kết quả tìm kiếm cho từ khóa: <strong class="text-primary font-bold">"{{ searchQuery }}"</strong> ({{ searchResults.length }} kết quả)
        </p>
      </div>

      <!-- 1. LOADING STATE -->
      <div v-if="viewState === 'loading'" class="grid grid-cols-2 md:grid-cols-4 gap-4">
        <KvSkeleton v-for="i in 4" :key="i" type="card" height="300px" :dark="true" />
      </div>

      <!-- 2. ERROR STATE -->
      <div v-else-if="viewState === 'error'" class="py-16 text-center bg-neutral-900 border border-white/10 rounded-2xl p-8 max-w-md mx-auto">
        <p class="text-red-400 font-bold mb-4">{{ errorMessage }}</p>
        <button @click="viewState = 'success'" class="px-6 py-2.5 bg-primary text-black font-bold text-xs rounded-lg">Thử lại</button>
      </div>

      <!-- 3. EMPTY STATE -->
      <div v-else-if="viewState === 'empty' || searchResults.length === 0" class="py-12">
        <KvEmptyState
          :title="`Không tìm thấy kết quả cho '${searchQuery}'`"
          description="Hãy thử kiểm tra lại lỗi chính tả hoặc tìm kiếm bằng từ khóa ngắn hơn."
          action-text="Xem tất cả sản phẩm"
          @action="$router.push('/shop')"
        />
      </div>

      <!-- 4. SUCCESS SEARCH RESULTS GRID -->
      <div v-else class="grid grid-cols-2 md:grid-cols-4 gap-4">
        <KvProductCard
          v-for="p in searchResults"
          :key="p.id"
          :product="p"
          @add-to-cart="cart.addItem"
        />
      </div>
    </div>
  </div>
</template>
