<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import KvModal from '@/components/ui/KvModal.vue'
import KvBadge from '@/components/ui/KvBadge.vue'
import productsJson from '@/data/json/products.json'
import { DEMO_REVIEWS, formatVnd } from '@/data/demo'
import { useCartStore } from '@/stores/cart'

const route = useRoute()
const router = useRouter()
const cart = useCartStore()

const product = computed(() => {
  const slug = route.params.slug || 'air-force-kinetic'
  return productsJson.find((p) => p.slug === slug) || productsJson[0]
})

const activeImageIndex = ref(0)
const is360Mode = ref(false)

const selectedSize = ref(product.value?.sizes?.[2] || 41)
const selectedColor = ref(product.value?.colors?.[0] || 'Trắng')
const quantity = ref(1)
const isWishlisted = ref(false)
const showSizeChart = ref(false)

const reviewStarFilter = ref(0)
const filteredReviews = computed(() => {
  if (reviewStarFilter.value === 0) return DEMO_REVIEWS
  return DEMO_REVIEWS.filter((r) => r.rating === reviewStarFilter.value)
})

function handleBuyNow() {
  cart.addItem(product.value, { size: selectedSize.value, color: selectedColor.value, qty: quantity.value })
  router.push('/checkout')
}

function handleAddToCart() {
  cart.addItem(product.value, { size: selectedSize.value, color: selectedColor.value, qty: quantity.value })
}
</script>

<template>
  <div class="bg-slate-50 text-slate-900 min-h-screen py-10">
    <div class="kv-container">
      <div v-if="!product" class="py-16 text-center">
        <p class="text-slate-500 font-bold mb-4">Sản phẩm không tồn tại.</p>
        <button @click="$router.push('/shop')" class="px-6 py-2.5 bg-slate-900 text-white font-bold text-xs rounded-xl">Quay lại cửa hàng</button>
      </div>

      <div v-else class="flex flex-col gap-12">
        <!-- Gallery & Product Details Grid -->
        <div class="grid grid-cols-1 md:grid-cols-2 gap-10">
          <!-- Gallery Area -->
          <div class="flex flex-col gap-4">
            <div class="relative w-full aspect-square bg-white rounded-3xl border border-slate-200 shadow-sm overflow-hidden group">
              <template v-if="!is360Mode">
                <img
                  :src="product.images?.[activeImageIndex] || product.image"
                  :alt="product.name"
                  class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105"
                />
              </template>

              <template v-else>
                <div class="w-full h-full flex flex-col items-center justify-center bg-slate-900 text-center p-6 text-white">
                  <span class="text-4xl animate-spin mb-3">🔄</span>
                  <span class="text-xs font-bold text-amber-400 uppercase tracking-widest">Chế độ Xem 360°</span>
                  <p class="text-[11px] text-neutral-400 mt-1">Kéo chuột hoặc vuốt để xoay toàn bộ góc nhìn sản phẩm</p>
                </div>
              </template>

              <button
                type="button"
                class="absolute top-4 right-4 px-3.5 py-2 bg-slate-900/90 text-white text-xs font-extrabold rounded-xl hover:bg-amber-600 transition-all cursor-pointer z-10 shadow-md"
                @click="is360Mode = !is360Mode"
              >
                {{ is360Mode ? '✕ Thoát 360°' : '🌀 Xem 360°' }}
              </button>

              <KvBadge v-if="product.badge" variant="gold" class="absolute top-4 left-4 z-10">
                {{ product.badge }}
              </KvBadge>
            </div>

            <!-- Thumbnails Slider -->
            <div class="flex items-center gap-3 overflow-x-auto pb-2">
              <button
                v-for="(img, idx) in product.images || [product.image]"
                :key="idx"
                type="button"
                class="w-20 h-20 rounded-2xl overflow-hidden border-2 transition-all shrink-0 cursor-pointer shadow-sm"
                :class="[activeImageIndex === idx && !is360Mode ? 'border-amber-600 scale-105' : 'border-slate-200 hover:border-slate-400']"
                @click="activeImageIndex = idx; is360Mode = false"
              >
                <img :src="img" :alt="product.name" class="w-full h-full object-cover" />
              </button>
            </div>
          </div>

          <!-- Product Buy Info -->
          <div class="flex flex-col gap-6 bg-white p-8 border border-slate-200 rounded-3xl shadow-sm">
            <div>
              <span class="text-xs font-extrabold text-amber-600 uppercase tracking-widest block mb-1">{{ product.brand }}</span>
              <h1 class="text-3xl font-extrabold text-slate-900 leading-snug">{{ product.name }}</h1>
              <span class="text-xs text-slate-400 font-mono">Mã SKU: {{ product.sku }}</span>
            </div>

            <!-- Price & Stock -->
            <div class="flex items-center gap-4 p-4 bg-slate-50 border border-slate-200 rounded-2xl">
              <span class="text-3xl font-extrabold text-slate-900">{{ formatVnd(product.price) }}</span>
              <span v-if="product.compareAt" class="text-sm line-through text-slate-400">{{ product.compareAt }}</span>
              <div class="ml-auto text-xs text-emerald-600 font-extrabold flex items-center gap-1.5 bg-emerald-50 px-3 py-1 rounded-full border border-emerald-200">
                <span class="w-2 h-2 rounded-full bg-emerald-500 animate-ping"></span>
                <span>Còn {{ product.stockCount || 10 }} đôi</span>
              </div>
            </div>

            <!-- Color Options -->
            <div class="flex flex-col gap-2">
              <span class="text-xs font-bold text-slate-700">Màu Sắc: <strong class="text-slate-900">{{ selectedColor }}</strong></span>
              <div class="flex flex-wrap gap-2">
                <button
                  v-for="clr in product.colors"
                  :key="clr"
                  type="button"
                  class="px-4 py-2 text-xs font-bold rounded-xl border transition-all cursor-pointer"
                  :class="[selectedColor === clr ? 'bg-slate-900 text-white border-slate-900' : 'bg-slate-50 text-slate-700 border-slate-200 hover:border-slate-400']"
                  @click="selectedColor = clr"
                >
                  {{ clr }}
                </button>
              </div>
            </div>

            <!-- Size Options -->
            <div class="flex flex-col gap-2">
              <div class="flex items-center justify-between">
                <span class="text-xs font-bold text-slate-700">Size (EU): <strong class="text-slate-900">{{ selectedSize }}</strong></span>
                <button type="button" @click="showSizeChart = true" class="text-xs text-amber-600 font-extrabold hover:underline cursor-pointer">
                  📏 Bảng hướng dẫn chọn size
                </button>
              </div>
              <div class="grid grid-cols-5 gap-2">
                <button
                  v-for="sz in product.sizes"
                  :key="sz"
                  type="button"
                  class="h-10 text-xs font-extrabold rounded-xl border transition-all cursor-pointer"
                  :class="[selectedSize === sz ? 'bg-amber-600 text-white border-amber-600 shadow' : 'bg-slate-50 text-slate-700 border-slate-200 hover:border-slate-400']"
                  @click="selectedSize = sz"
                >
                  {{ sz }}
                </button>
              </div>
            </div>

            <!-- Actions -->
            <div class="flex flex-col gap-3 pt-4 border-t border-slate-100">
              <div class="flex items-center gap-4">
                <div class="flex items-center bg-slate-100 border border-slate-200 rounded-xl">
                  <button @click="quantity = Math.max(1, quantity - 1)" class="w-10 h-10 text-slate-900 font-bold cursor-pointer text-sm">-</button>
                  <span class="w-10 text-center text-xs font-extrabold text-slate-900">{{ quantity }}</span>
                  <button @click="quantity++" class="w-10 h-10 text-slate-900 font-bold cursor-pointer text-sm">+</button>
                </div>

                <button
                  type="button"
                  class="h-10 px-4 bg-slate-100 border border-slate-200 rounded-xl text-xs font-bold text-slate-700 hover:border-amber-600 hover:text-amber-600 transition-all cursor-pointer"
                  @click="isWishlisted = !isWishlisted"
                >
                  {{ isWishlisted ? '❤️ Đã yêu thích' : '🤍 Thêm Wishlist' }}
                </button>
              </div>

              <div class="grid grid-cols-2 gap-3 mt-2">
                <button
                  type="button"
                  class="h-12 bg-white border-2 border-slate-900 text-slate-900 font-extrabold text-xs rounded-xl hover:bg-slate-900 hover:text-white transition-all cursor-pointer uppercase tracking-wider shadow-sm"
                  @click="handleAddToCart"
                >
                  🛒 Thêm vào giỏ hàng
                </button>

                <button
                  type="button"
                  class="h-12 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 transition-all cursor-pointer uppercase tracking-wider shadow-lg"
                  @click="handleBuyNow"
                >
                  ⚡ Mua ngay
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Description & Specs -->
        <div class="bg-white border border-slate-200 rounded-3xl p-8 shadow-sm flex flex-col gap-6">
          <h3 class="text-base font-extrabold text-slate-900 border-b border-slate-100 pb-3">Chi Tiết Sản Phẩm & Thông Số</h3>
          <p class="text-xs text-slate-600 leading-relaxed font-medium">{{ product.description }}</p>

          <div v-if="product.specs" class="overflow-x-auto">
            <table class="w-full text-xs text-left border-collapse">
              <tbody>
                <tr v-for="(spec, idx) in product.specs" :key="idx" class="border-b border-slate-100">
                  <td class="py-3 px-4 font-bold text-slate-500 w-1/3 bg-slate-50">{{ spec.label }}</td>
                  <td class="py-3 px-4 text-slate-900 font-semibold">{{ spec.value }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- Size Chart Modal -->
        <KvModal v-model="showSizeChart" title="Bảng Hướng Dẫn Chọn Size Giày KickVerse" :dark="false">
          <div class="flex flex-col gap-4 text-xs text-slate-700">
            <p>Đo chiều dài bàn chân từ gót chân đến đầu ngón chân dài nhất để chọn size chính xác:</p>
            <table class="w-full text-center border border-slate-200">
              <thead class="bg-slate-900 text-white font-bold">
                <tr>
                  <th class="p-2.5 border border-slate-200">Size EU</th>
                  <th class="p-2.5 border border-slate-200">Chiều dài chân (cm)</th>
                  <th class="p-2.5 border border-slate-200">Size US</th>
                </tr>
              </thead>
              <tbody>
                <tr class="border-b border-slate-200"><td>39</td><td>24.5 cm</td><td>6.5</td></tr>
                <tr class="border-b border-slate-200"><td>40</td><td>25.0 cm</td><td>7.0</td></tr>
                <tr class="border-b border-slate-200"><td>41</td><td>25.5 cm</td><td>8.0</td></tr>
                <tr class="border-b border-slate-200"><td>42</td><td>26.0 cm</td><td>8.5</td></tr>
                <tr class="border-b border-slate-200"><td>43</td><td>27.0 cm</td><td>9.5</td></tr>
              </tbody>
            </table>
          </div>
        </KvModal>
      </div>
    </div>
  </div>
</template>
