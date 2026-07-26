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
    <div class="flex items-center justify-between">
      <h1 class="text-2xl font-extrabold text-white">Danh Sách Yêu Thích</h1>
      <span class="text-xs text-neutral-400">{{ wishlistItems.length }} sản phẩm</span>
    </div>

    <div v-if="wishlistItems.length === 0" class="py-12">
      <KvEmptyState
        title="Danh sách yêu thích trống"
        description="Hãy thả tim các mẫu giày bạn yêu thích khi lướt xem sản phẩm để lưu tại đây."
        action-text="Khám phá ngay"
        @action="$router.push('/shop')"
      />
    </div>

    <div v-else class="grid grid-cols-2 md:grid-cols-3 gap-4">
      <div v-for="p in wishlistItems" :key="p.id" class="relative group">
        <KvProductCard :product="p" @add-to-cart="cart.addItem" />
        <button
          type="button"
          class="absolute top-3 right-3 z-20 w-8 h-8 rounded-full bg-black/80 border border-white/20 text-red-400 text-xs flex items-center justify-center cursor-pointer hover:scale-110 transition-transform"
          @click="removeFromWishlist(p.id)"
          title="Xóa khỏi Wishlist"
        >
          ✕
        </button>
      </div>
    </div>
  </div>
</template>
