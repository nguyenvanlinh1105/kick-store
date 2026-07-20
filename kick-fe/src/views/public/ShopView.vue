<script setup>
import { computed, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import KvProductCard from '@/components/ui/KvProductCard.vue'
import { DEMO_PRODUCTS } from '@/data/demo'
import { useCartStore } from '@/stores/cart'

const route = useRoute()
const router = useRouter()
const cart = useCartStore()

// State
const searchQuery = ref('')
const selectedCategory = ref(route.query.cat || 'all')
const selectedBrand = ref('all')
const selectedPriceRange = ref('all')
const showMobileFilters = ref(false)

// Brands & Categories metadata
const brands = ['Nike', 'Adidas', 'Jordan', 'New Balance', 'Puma', 'Converse']
const categories = [
  { value: 'all', label: 'Tất cả' },
  { value: 'running', label: 'Running' },
  { value: 'lifestyle', label: 'Lifestyle' }
]
const priceRanges = [
  { value: 'all', label: 'Tất cả giá' },
  { value: 'under2', label: 'Dưới 2.000.000đ' },
  { value: '2to3', label: '2.000.000đ - 3.000.000đ' },
  { value: 'over3', label: 'Trên 3.000.000đ' }
]

// Sync route changes with category filter
watch(
  () => route.query.cat,
  (newCat) => {
    selectedCategory.value = newCat ? newCat.toLowerCase() : 'all'
  }
)

// Set category and update query params
function setCategory(cat) {
  selectedCategory.value = cat
  if (cat === 'all') {
    router.push('/shop')
  } else {
    router.push(`/shop?cat=${cat}`)
  }
}

// Reset all filters
function resetFilters() {
  searchQuery.value = ''
  selectedBrand.value = 'all'
  selectedPriceRange.value = 'all'
  setCategory('all')
}

// Check if any filter is active (to show reset button)
const hasActiveFilters = computed(() => {
  return (
    searchQuery.value !== '' ||
    selectedCategory.value !== 'all' ||
    selectedBrand.value !== 'all' ||
    selectedPriceRange.value !== 'all'
  )
})

// Count active filters
const activeFiltersCount = computed(() => {
  let count = 0
  if (searchQuery.value) count++
  if (selectedCategory.value !== 'all') count++
  if (selectedBrand.value !== 'all') count++
  if (selectedPriceRange.value !== 'all') count++
  return count
})

// Filtering logic
const products = computed(() => {
  return DEMO_PRODUCTS.filter((product) => {
    // 1. Search Query filter (matches name or brand)
    if (searchQuery.value) {
      const q = searchQuery.value.toLowerCase().trim()
      const matchName = product.name.toLowerCase().includes(q)
      const matchBrand = product.brand.toLowerCase().includes(q)
      if (!matchName && !matchBrand) return false
    }

    // 2. Category filter
    if (selectedCategory.value !== 'all') {
      if (product.category?.toLowerCase() !== selectedCategory.value.toLowerCase()) {
        return false
      }
    }

    // 3. Brand filter
    if (selectedBrand.value !== 'all') {
      if (product.brand?.toLowerCase() !== selectedBrand.value.toLowerCase()) {
        return false
      }
    }

    // 4. Price filter
    if (selectedPriceRange.value !== 'all') {
      const price = product.price
      if (selectedPriceRange.value === 'under2') {
        if (price >= 2000000) return false
      } else if (selectedPriceRange.value === '2to3') {
        if (price < 2000000 || price > 3000000) return false
      } else if (selectedPriceRange.value === 'over3') {
        if (price <= 3000000) return false
      }
    }

    return true
  })
})
</script>

<template>
  <div class="bg-neutral-50 min-h-screen text-neutral-900">
    <!-- Header Banner - Lightened & Refined -->
    <header class="pt-12 pb-8 bg-white border-b border-neutral-200">
      <div class="kv-container">
        <div class="flex flex-col gap-2">
          <div class="flex items-center gap-2 text-xs font-semibold text-neutral-400 uppercase tracking-widest">
            <span class="text-primary font-bold">KickVerse</span>
            <span>/</span>
            <span>Cửa hàng</span>
          </div>
          <h1 class="text-3xl md:text-4xl font-black tracking-tight text-neutral-900 mt-2 mb-1">
            Bộ Sưu Tập Giày
          </h1>
          <p class="text-sm text-neutral-500">
            Khám phá các mẫu giày sneaker và streetwear độc đáo từ các thương hiệu hàng đầu thế giới với mức giá tốt nhất.
          </p>
        </div>
      </div>
    </header>

    <!-- Main Section -->
    <section class="py-8">
      <div class="kv-container">
        <!-- Layout grid: filter panel + product area -->
        <div class="flex flex-col lg:flex-row gap-8">
          
          <!-- LEFT SIDEBAR: FILTERS (DESKTOP) -->
          <aside class="hidden lg:flex flex-col w-64 shrink-0 gap-6">
            <!-- Search Widget -->
            <div class="bg-white border border-neutral-200 rounded-2xl p-5 shadow-sm">
              <h3 class="text-xs font-bold uppercase tracking-wider text-neutral-400 mb-3">Tìm kiếm</h3>
              <div class="relative flex items-center bg-neutral-50 border border-neutral-200 rounded-xl px-3 py-2 focus-within:border-black focus-within:bg-white transition-all duration-200">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" class="text-neutral-400 mr-2 shrink-0">
                  <circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/>
                </svg>
                <input
                  v-model="searchQuery"
                  type="text"
                  placeholder="Tìm tên, thương hiệu..."
                  class="bg-transparent border-0 outline-none text-sm text-neutral-900 placeholder-neutral-400 w-full"
                />
                <button
                  v-if="searchQuery"
                  type="button"
                  class="text-neutral-400 hover:text-black border-0 bg-transparent cursor-pointer p-0.5"
                  @click="searchQuery = ''"
                >
                  <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3">
                    <line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/>
                  </svg>
                </button>
              </div>
            </div>

            <!-- Categories Widget -->
            <div class="bg-white border border-neutral-200 rounded-2xl p-5 shadow-sm">
              <h3 class="text-xs font-bold uppercase tracking-wider text-neutral-400 mb-3">Phân loại</h3>
              <div class="flex flex-col gap-1">
                <button
                  v-for="cat in categories"
                  :key="cat.value"
                  type="button"
                  class="w-full text-left text-sm py-2 px-3 rounded-xl border-0 cursor-pointer transition-all duration-200 flex justify-between items-center"
                  :class="[
                    selectedCategory === cat.value
                      ? 'bg-neutral-900 text-white font-semibold'
                      : 'bg-transparent text-neutral-600 hover:bg-neutral-100 hover:text-black'
                  ]"
                  @click="setCategory(cat.value)"
                >
                  <span>{{ cat.label }}</span>
                  <svg
                    v-if="selectedCategory === cat.value"
                    width="12"
                    height="12"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    stroke-width="3"
                  >
                    <polyline points="20 6 9 17 4 12"/>
                  </svg>
                </button>
              </div>
            </div>

            <!-- Brands Widget -->
            <div class="bg-white border border-neutral-200 rounded-2xl p-5 shadow-sm">
              <h3 class="text-xs font-bold uppercase tracking-wider text-neutral-400 mb-3">Thương hiệu</h3>
              <div class="flex flex-col gap-1">
                <button
                  type="button"
                  class="w-full text-left text-sm py-2 px-3 rounded-xl border-0 cursor-pointer transition-all duration-200 flex justify-between items-center"
                  :class="[
                    selectedBrand === 'all'
                      ? 'bg-neutral-900 text-white font-semibold'
                      : 'bg-transparent text-neutral-600 hover:bg-neutral-100 hover:text-black'
                  ]"
                  @click="selectedBrand = 'all'"
                >
                  <span>Tất cả</span>
                  <svg v-if="selectedBrand === 'all'" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3">
                    <polyline points="20 6 9 17 4 12"/>
                  </svg>
                </button>
                <button
                  v-for="brand in brands"
                  :key="brand"
                  type="button"
                  class="w-full text-left text-sm py-2 px-3 rounded-xl border-0 cursor-pointer transition-all duration-200 flex justify-between items-center"
                  :class="[
                    selectedBrand.toLowerCase() === brand.toLowerCase()
                      ? 'bg-neutral-900 text-white font-semibold'
                      : 'bg-transparent text-neutral-600 hover:bg-neutral-100 hover:text-black'
                  ]"
                  @click="selectedBrand = brand"
                >
                  <span>{{ brand }}</span>
                  <svg v-if="selectedBrand.toLowerCase() === brand.toLowerCase()" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3">
                    <polyline points="20 6 9 17 4 12"/>
                  </svg>
                </button>
              </div>
            </div>

            <!-- Price Filter Widget -->
            <div class="bg-white border border-neutral-200 rounded-2xl p-5 shadow-sm">
              <h3 class="text-xs font-bold uppercase tracking-wider text-neutral-400 mb-3">Khoảng giá</h3>
              <div class="flex flex-col gap-1">
                <button
                  v-for="range in priceRanges"
                  :key="range.value"
                  type="button"
                  class="w-full text-left text-sm py-2 px-3 rounded-xl border-0 cursor-pointer transition-all duration-200 flex justify-between items-center"
                  :class="[
                    selectedPriceRange === range.value
                      ? 'bg-neutral-900 text-white font-semibold'
                      : 'bg-transparent text-neutral-600 hover:bg-neutral-100 hover:text-black'
                  ]"
                  @click="selectedPriceRange = range.value"
                >
                  <span>{{ range.label }}</span>
                  <svg v-if="selectedPriceRange === range.value" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3">
                    <polyline points="20 6 9 17 4 12"/>
                  </svg>
                </button>
              </div>
            </div>
          </aside>

          <!-- MOBILE FILTERS & ACTIONS BAR (lg:hidden) -->
          <div class="lg:hidden w-full flex flex-col gap-3">
            <div class="flex gap-2">
              <!-- Search bar -->
              <div class="flex-1 relative flex items-center bg-white border border-neutral-200 rounded-xl px-3 py-2.5 shadow-sm focus-within:border-black transition-all">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" class="text-neutral-400 mr-2 shrink-0">
                  <circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/>
                </svg>
                <input
                  v-model="searchQuery"
                  type="text"
                  placeholder="Tìm kiếm giày..."
                  class="bg-transparent border-0 outline-none text-sm text-neutral-900 placeholder-neutral-400 w-full"
                />
              </div>

              <!-- Filter Toggle Button -->
              <button
                type="button"
                class="flex items-center gap-2 px-4 bg-white border border-neutral-200 rounded-xl text-sm font-semibold shadow-sm cursor-pointer hover:bg-neutral-50 transition-colors"
                @click="showMobileFilters = !showMobileFilters"
              >
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
                  <line x1="4" y1="21" x2="4" y2="14"/><line x1="4" y1="10" x2="4" y2="3"/>
                  <line x1="12" y1="21" x2="12" y2="12"/><line x1="12" y1="8" x2="12" y2="3"/>
                  <line x1="20" y1="21" x2="20" y2="16"/><line x1="20" y1="12" x2="20" y2="3"/>
                  <line x1="1" y1="14" x2="7" y2="14"/><line x1="9" y1="8" x2="15" y2="8"/><line x1="17" y1="16" x2="23" y2="16"/>
                </svg>
                <span>Bộ lọc</span>
                <span v-if="activeFiltersCount" class="w-5 h-5 rounded-full bg-black text-white text-[11px] flex items-center justify-center font-bold">
                  {{ activeFiltersCount }}
                </span>
              </button>
            </div>

            <!-- Expandable Mobile Filters Content -->
            <transition name="slide-fade">
              <div v-if="showMobileFilters" class="bg-white border border-neutral-200 rounded-2xl p-5 shadow-md flex flex-col gap-5 mt-1">
                <!-- Phân loại -->
                <div>
                  <h4 class="text-xs font-bold uppercase tracking-wider text-neutral-400 mb-2">Phân loại</h4>
                  <div class="flex flex-wrap gap-1.5">
                    <button
                      v-for="cat in categories"
                      :key="cat.value"
                      type="button"
                      class="text-xs py-1.5 px-3 rounded-full border cursor-pointer transition-colors"
                      :class="[
                        selectedCategory === cat.value
                          ? 'bg-black border-black text-white font-semibold'
                          : 'bg-neutral-50 border-neutral-200 text-neutral-600 hover:bg-neutral-100'
                      ]"
                      @click="setCategory(cat.value)"
                    >
                      {{ cat.label }}
                    </button>
                  </div>
                </div>

                <!-- Thương hiệu -->
                <div>
                  <h4 class="text-xs font-bold uppercase tracking-wider text-neutral-400 mb-2">Thương hiệu</h4>
                  <div class="flex flex-wrap gap-1.5">
                    <button
                      type="button"
                      class="text-xs py-1.5 px-3 rounded-full border cursor-pointer transition-colors"
                      :class="[
                        selectedBrand === 'all'
                          ? 'bg-black border-black text-white font-semibold'
                          : 'bg-neutral-50 border-neutral-200 text-neutral-600 hover:bg-neutral-100'
                      ]"
                      @click="selectedBrand = 'all'"
                    >
                      Tất cả
                    </button>
                    <button
                      v-for="brand in brands"
                      :key="brand"
                      type="button"
                      class="text-xs py-1.5 px-3 rounded-full border cursor-pointer transition-colors"
                      :class="[
                        selectedBrand.toLowerCase() === brand.toLowerCase()
                          ? 'bg-black border-black text-white font-semibold'
                          : 'bg-neutral-50 border-neutral-200 text-neutral-600 hover:bg-neutral-100'
                      ]"
                      @click="selectedBrand = brand"
                    >
                      {{ brand }}
                    </button>
                  </div>
                </div>

                <!-- Khoảng giá -->
                <div>
                  <h4 class="text-xs font-bold uppercase tracking-wider text-neutral-400 mb-2">Khoảng giá</h4>
                  <div class="flex flex-col gap-1.5">
                    <button
                      v-for="range in priceRanges"
                      :key="range.value"
                      type="button"
                      class="w-full text-left text-xs py-2 px-3 border rounded-xl cursor-pointer transition-colors"
                      :class="[
                        selectedPriceRange === range.value
                          ? 'bg-black border-black text-white font-semibold'
                          : 'bg-neutral-50 border-neutral-200 text-neutral-600 hover:bg-neutral-100'
                      ]"
                      @click="selectedPriceRange = range.value"
                    >
                      {{ range.label }}
                    </button>
                  </div>
                </div>

                <!-- Clear button -->
                <button
                  v-if="hasActiveFilters"
                  type="button"
                  class="w-full py-2 bg-neutral-100 hover:bg-neutral-200 text-neutral-700 font-semibold rounded-xl text-xs border-0 cursor-pointer transition-colors"
                  @click="resetFilters"
                >
                  Xóa tất cả bộ lọc
                </button>
              </div>
            </transition>
          </div>

          <!-- RIGHT AREA: PRODUCTS & SEARCH META -->
          <div class="flex-1 flex flex-col gap-6">
            <!-- Filter Toolbar / Status Row -->
            <div class="flex items-center justify-between bg-white border border-neutral-200 rounded-2xl px-5 py-4 shadow-sm">
              <div class="text-sm text-neutral-500">
                Tìm thấy <span class="font-bold text-neutral-900">{{ products.length }}</span> sản phẩm
              </div>

              <!-- Reset Filters (Desktop view) -->
              <button
                v-if="hasActiveFilters"
                type="button"
                class="hidden lg:flex items-center gap-1 text-xs font-bold text-neutral-500 hover:text-black border-0 bg-transparent cursor-pointer"
                @click="resetFilters"
              >
                <span>Xóa bộ lọc</span>
                <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
                  <line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/>
                </svg>
              </button>
            </div>

            <!-- Products Grid -->
            <div v-if="products.length > 0" class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
              <KvProductCard
                v-for="p in products"
                :key="p.id"
                :product="p"
                :dark="false"
                class="animate-fade-in-up"
                @add-to-cart="cart.addItem"
              />
            </div>

            <!-- Empty State -->
            <div v-else class="py-24 px-6 text-center bg-white border border-neutral-200 rounded-3xl shadow-sm flex flex-col items-center justify-center gap-4">
              <div class="w-16 h-16 rounded-full bg-neutral-100 flex items-center justify-center text-neutral-400">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/>
                </svg>
              </div>
              <div>
                <h3 class="text-lg font-bold text-neutral-900 mb-1">Không tìm thấy sản phẩm nào</h3>
                <p class="text-sm text-neutral-400 max-w-sm m-0">
                  Không tìm thấy kết quả phù hợp với các tiêu chí tìm kiếm hoặc bộ lọc hiện tại của bạn.
                </p>
              </div>
              <button
                type="button"
                class="mt-2 px-5 py-2.5 bg-neutral-900 hover:bg-neutral-800 text-white text-xs font-bold uppercase tracking-wider rounded-xl border-0 cursor-pointer transition-all duration-200 shadow-sm"
                @click="resetFilters"
              >
                Đặt lại bộ lọc
              </button>
            </div>
          </div>
          
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>
/* Transition animations for mobile filter panel */
.slide-fade-enter-active,
.slide-fade-leave-active {
  transition: all 0.3s ease-out;
}
.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateY(-10px);
  opacity: 0;
}
</style>
