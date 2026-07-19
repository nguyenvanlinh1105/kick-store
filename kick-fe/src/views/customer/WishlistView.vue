<script setup>
import { computed } from 'vue'
import { storeToRefs } from 'pinia'
import KvButton from '@/components/ui/KvButton.vue'
import KvEmptyState from '@/components/ui/KvEmptyState.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'
import KvProductCard from '@/components/ui/KvProductCard.vue'
import { DEMO_PRODUCTS } from '@/data/demo'
import { useWishlistStore } from '@/stores/wishlist'
import { useCartStore } from '@/stores/cart'

const wishlist = useWishlistStore()
const cart = useCartStore()
const { ids } = storeToRefs(wishlist)

const products = computed(() => DEMO_PRODUCTS.filter((p) => ids.value.includes(p.id)))
</script>

<template>
  <div>
    <KvPageHeader
      title="Wishlist"
      :description="products.length ? `${products.length} sản phẩm đã lưu` : 'Chưa có sản phẩm'"
    />

    <div v-if="products.length" class="grid gap-lg sm:grid-cols-2">
      <div v-for="p in products" :key="p.id" class="relative">
        <KvProductCard :product="p" @add-to-cart="cart.addItem" />
        <button
          type="button"
          class="absolute right-3 top-3 z-10 flex h-10 w-10 items-center justify-center rounded-full bg-canvas-light text-mute-light shadow-[0_4px_12px_rgba(0,0,0,0.16)]"
          aria-label="Bỏ khỏi wishlist"
          @click="wishlist.remove(p.id)"
        >
          ♥
        </button>
      </div>
    </div>

    <KvEmptyState
      v-else
      title="Wishlist trống"
      description="Nhấn lưu trên trang sản phẩm để theo dõi những đôi bạn thích."
    >
      <template #action>
        <KvButton to="/shop" variant="primary">Khám phá</KvButton>
      </template>
    </KvEmptyState>
  </div>
</template>
