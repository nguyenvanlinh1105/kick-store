<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import KvProductCard from '@/components/ui/KvProductCard.vue'
import KvPagination from '@/components/ui/KvPagination.vue'
import KvSelect from '@/components/ui/KvSelect.vue'
import productsJson from '@/data/json/products.json'
import { DEMO_BRANDS, formatVnd } from '@/data/demo'
import { useCartStore } from '@/stores/cart'

const route = useRoute()
const cart = useCartStore()

const displayMode = ref('grid')

const selectedCategory = ref(route.query.cat || 'all')
const selectedBrand = ref(route.query.brand || 'all')
const selectedSize = ref('')
const selectedColor = ref('')
const onlyInStock = ref(false)
const maxPrice = ref(5000000)
const searchQuery = ref('')

const sortBy = ref('newest')
const currentPage = ref(1)
const itemsPerPage = 6

const sortOptions = [
  { value: 'newest', label: 'Mới nhất' },
  { value: 'price-asc', label: 'Giá: Thấp đến Cao' },
  { value: 'price-desc', label: 'Giá: Cao đến Thấp' },
  { value: 'rating', label: 'Đánh giá cao nhất' },
]

const categoryOptions = [
  { value: 'all', label: 'Tất cả danh mục' },
  { value: 'running', label: 'Running Shoes' },
  { value: 'lifestyle', label: 'Lifestyle Streetwear' },
]

const brandOptions = computed(() => [
  { value: 'all', label: 'Tất cả thương hiệu' },
  ...DEMO_BRANDS.map((b) => ({ value: b.name, label: b.name })),
])

const availableSizes = [36, 37, 38, 39, 40, 41, 42, 43, 44]

const filteredProducts = computed(() => {
  return productsJson.filter((p) => {
    if (selectedCategory.value !== 'all' && p.category !== selectedCategory.value) return false
    if (selectedBrand.value !== 'all' && p.brand.toLowerCase() !== selectedBrand.value.toLowerCase()) return false
    if (selectedSize.value && !p.sizes.includes(Number(selectedSize.value))) return false
    if (selectedColor.value && !p.colors.includes(selectedColor.value)) return false
    if (onlyInStock.value && !p.inStock) return false
    if (p.price > maxPrice.value) return false
    if (searchQuery.value && !p.name.toLowerCase().includes(searchQuery.value.toLowerCase())) return false
    return true
  }).sort((a, b) => {
    if (sortBy.value === 'price-asc') return a.price - b.price
    if (sortBy.value === 'price-desc') return b.price - a.price
    if (sortBy.value === 'rating') return (b.rating || 0) - (a.rating || 0)
    return b.id - a.id
  })
})

const totalPages = computed(() => Math.ceil(filteredProducts.value.length / itemsPerPage) || 1)

const paginatedProducts = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  return filteredProducts.value.slice(start, start + itemsPerPage)
})

function resetFilters() {
  selectedCategory.value = 'all'
  selectedBrand.value = 'all'
  selectedSize.value = ''
  selectedColor.value = ''
  onlyInStock.value = false
  maxPrice.value = 5000000
  searchQuery.value = ''
  currentPage.value = 1
}

onMounted(() => {
  if (route.query.cat) selectedCategory.value = route.query.cat
  if (route.query.brand) selectedBrand.value = route.query.brand
})
</script>

<template>
  <div class="bg-slate-50 text-slate-900 min-h-screen py-10">
    <div class="kv-container">
      <!-- Page Header -->
      <div class="flex flex-col md:flex-row md:items-center justify-between gap-4 mb-8">
        <div>
          <h1 class="text-3xl font-extrabold text-slate-900">Danh Mục Sản Phẩm Sneaker</h1>
          <p class="text-xs text-slate-500 mt-1">Tìm thấy {{ filteredProducts.length }} sản phẩm phù hợp với tiêu chí của bạn</p>
        </div>

        <div class="flex items-center gap-3">
          <div class="w-52">
            <KvSelect v-model="sortBy" :options="sortOptions" :dark="false" />
          </div>

          <div class="flex items-center bg-white border border-slate-200 rounded-xl p-1 shadow-sm">
            <button
              type="button"
              class="w-8 h-8 flex items-center justify-center rounded-lg text-xs transition-colors cursor-pointer"
              :class="[displayMode === 'grid' ? 'bg-slate-900 text-white font-bold' : 'text-slate-500']"
              @click="displayMode = 'grid'"
            >
              田
            </button>
            <button
              type="button"
              class="w-8 h-8 flex items-center justify-center rounded-lg text-xs transition-colors cursor-pointer"
              :class="[displayMode === 'list' ? 'bg-slate-900 text-white font-bold' : 'text-slate-500']"
              @click="displayMode = 'list'"
            >
              ☰
            </button>
          </div>
        </div>
      </div>

      <!-- Main Grid: Sidebar + Products -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-8">
        <!-- Sidebar Filter -->
        <aside class="md:col-span-1 bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-6 h-fit shadow-sm">
          <div class="flex items-center justify-between border-b border-slate-100 pb-3">
            <h3 class="text-xs font-extrabold text-slate-900 uppercase tracking-wider">Bộ Lọc Tìm Kiếm</h3>
            <button type="button" @click="resetFilters" class="text-xs text-amber-600 font-extrabold hover:underline cursor-pointer">
              Xóa bộ lọc
            </button>
          </div>

          <!-- Category Select -->
          <KvSelect
            v-model="selectedCategory"
            label="Danh Mục"
            :options="categoryOptions"
            :dark="false"
          />

          <!-- Brand Select -->
          <KvSelect
            v-model="selectedBrand"
            label="Thương Hiệu"
            :options="brandOptions"
            :dark="false"
          />

          <!-- Price Range Slider -->
          <div class="flex flex-col gap-2">
            <div class="flex justify-between text-xs">
              <span class="font-bold text-slate-700">Khoảng giá tối đa</span>
              <span class="text-amber-600 font-extrabold">{{ formatVnd(maxPrice) }}</span>
            </div>
            <input type="range" v-model.number="maxPrice" min="1000000" max="5000000" step="200000" class="w-full accent-amber-600 cursor-pointer" />
          </div>

          <!-- Size Options Grid -->
          <div class="flex flex-col gap-2">
            <span class="text-xs font-bold text-slate-700">Size Giày (EU)</span>
            <div class="flex flex-wrap gap-1.5">
              <button
                v-for="sz in availableSizes"
                :key="sz"
                type="button"
                class="w-8 h-8 text-xs font-bold rounded-lg border transition-all cursor-pointer"
                :class="[selectedSize == sz ? 'bg-slate-900 text-white border-slate-900' : 'bg-slate-50 text-slate-700 border-slate-200 hover:border-slate-400']"
                @click="selectedSize = selectedSize == sz ? '' : sz"
              >
                {{ sz }}
              </button>
            </div>
          </div>

          <label class="flex items-center gap-2.5 text-xs font-semibold text-slate-700 cursor-pointer pt-2 border-t border-slate-100">
            <input type="checkbox" v-model="onlyInStock" class="accent-amber-600 w-4 h-4 rounded" />
            <span>Chỉ hiển thị hàng có sẵn</span>
          </label>
        </aside>

        <!-- Product Listing Grid: 1 PRODUCT PER ROW ON MOBILE (grid-cols-1 sm:grid-cols-2 md:grid-cols-3) -->
        <main class="md:col-span-3">
          <div v-if="filteredProducts.length === 0" class="py-16 text-center bg-white border border-slate-200 rounded-3xl p-8 shadow-sm">
            <p class="text-slate-600 font-bold mb-4">Không tìm thấy sản phẩm phù hợp với bộ lọc.</p>
            <button @click="resetFilters" class="px-6 py-2.5 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 transition-all cursor-pointer">
              Xóa Bộ Lọc
            </button>
          </div>

          <div v-else>
            <div v-if="displayMode === 'grid'" class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
              <KvProductCard
                v-for="p in paginatedProducts"
                :key="p.id"
                :product="p"
                :dark="false"
                @add-to-cart="cart.addItem"
              />
            </div>

            <div v-else class="flex flex-col gap-4">
              <div
                v-for="p in paginatedProducts"
                :key="p.id"
                class="flex flex-col sm:flex-row items-center gap-6 bg-white border border-slate-200 rounded-2xl p-4 shadow-sm hover:shadow-md transition-all"
              >
                <img :src="p.image" :alt="p.name" class="w-full sm:w-36 h-48 sm:h-36 object-cover rounded-xl shrink-0" />
                <div class="flex-1 flex flex-col gap-1.5 w-full">
                  <span class="text-xs font-extrabold text-amber-600 uppercase">{{ p.brand }}</span>
                  <h3 class="text-base font-extrabold text-slate-900">{{ p.name }}</h3>
                  <p class="text-xs text-slate-500 line-clamp-2">{{ p.description }}</p>
                  <div class="flex items-center gap-3 mt-2">
                    <span class="text-lg font-extrabold text-slate-900">{{ p.priceLabel }}</span>
                    <span v-if="p.compareAt" class="text-xs line-through text-slate-400">{{ p.compareAt }}</span>
                  </div>
                </div>
                <button
                  type="button"
                  class="w-full sm:w-auto px-6 py-3 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 shrink-0 cursor-pointer shadow"
                  @click="cart.addItem(p)"
                >
                  + MUA NGAY
                </button>
              </div>
            </div>

            <div class="mt-10">
              <KvPagination
                v-model:currentPage="currentPage"
                :total-pages="totalPages"
                :dark="false"
              />
            </div>
          </div>
        </main>
      </div>
    </div>
  </div>
</template>
