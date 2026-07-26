<script setup>
import { ref } from 'vue'
import KvProductCard from '@/components/ui/KvProductCard.vue'
import KvEmptyState from '@/components/ui/KvEmptyState.vue'
import productsJson from '@/data/json/products.json'
import { useCartStore } from '@/stores/cart'

const cart = useCartStore()
const wishlistItems = ref([...productsJson.slice(0, 3)])

function removeFromWishlist(id) {
  wishlistItems.value = wishlistItems.value.filter((p) => p.id !== id)
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between border-b border-slate-200 pb-4">
      <h1 class="text-2xl font-extrabold text-slate-900">Danh Sách Yêu Thích</h1>
      <span class="text-xs text-slate-500 font-bold">{{ wishlistItems.length }} sản phẩm đã lưu</span>
    </div>

    <div v-if="wishlistItems.length === 0" class="py-12">
      <KvEmptyState
        title="Danh sách yêu thích trống"
        description="Hãy thả tim các mẫu giày bạn yêu thích khi lướt xem sản phẩm để lưu tại đây."
        action-text="Khám phá ngay"
        @action="$router.push('/shop')"
      />
    </div>

    <!-- 1 PRODUCT PER ROW ON MOBILE (grid-cols-1 sm:grid-cols-2 md:grid-cols-3) -->
    <div v-else class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
      <div v-for="p in wishlistItems" :key="p.id" class="relative group">
        <KvProductCard :product="p" :dark="false" @add-to-cart="cart.addItem" />
        <button
          type="button"
          class="absolute top-3 right-3 z-20 w-8 h-8 rounded-full bg-white/90 border border-slate-200 text-red-500 text-xs flex items-center justify-center cursor-pointer hover:scale-110 shadow-md transition-transform"
          @click="removeFromWishlist(p.id)"
          title="Xóa khỏi Wishlist"
        >
          ✕
        </button>
      </div>
    </div>
  </div>
</template>
