<script setup>
import { storeToRefs } from 'pinia'
import { RouterLink } from 'vue-router'
import KvButton from '@/components/ui/KvButton.vue'
import KvEmptyState from '@/components/ui/KvEmptyState.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'
import { useCartStore } from '@/stores/cart'

const cart = useCartStore()
const { items, subtotalLabel } = storeToRefs(cart)
</script>

<template>
  <div class="kv-container py-xl">
    <KvPageHeader title="Giỏ hàng" :description="`${items.length} sản phẩm`" />

    <div v-if="items.length" class="grid gap-xl kv:grid-cols-[1fr_320px]">
      <div class="flex flex-col gap-md">
        <article
          v-for="item in items"
          :key="item.key"
          class="flex gap-md rounded-md bg-surface-card p-lg"
        >
          <img :src="item.image" :alt="item.name" class="h-28 w-28 rounded-md object-cover" />
          <div class="flex flex-1 flex-col">
            <p class="text-caption-md text-mute-light">{{ item.brand }}</p>
            <RouterLink :to="`/shop/${item.slug}`" class="text-heading-md">
              {{ item.name }}
            </RouterLink>
            <p class="text-caption-md text-mute-light">Size {{ item.size }} · {{ item.color }}</p>
            <div class="mt-auto flex items-center justify-between pt-sm">
              <div class="flex items-center gap-sm">
                <button
                  type="button"
                  class="flex h-9 w-9 items-center justify-center rounded-full border border-ash-light"
                  @click="cart.updateQty(item.key, item.qty - 1)"
                >
                  −
                </button>
                <span class="w-6 text-center text-body-sm">{{ item.qty }}</span>
                <button
                  type="button"
                  class="flex h-9 w-9 items-center justify-center rounded-full border border-ash-light"
                  @click="cart.updateQty(item.key, item.qty + 1)"
                >
                  +
                </button>
              </div>
              <p class="text-body-strong">{{ item.priceLabel }}</p>
            </div>
          </div>
          <button
            type="button"
            class="self-start text-mute-light"
            @click="cart.removeItem(item.key)"
          >
            Xóa
          </button>
        </article>
      </div>

      <aside class="h-fit rounded-md bg-surface-card p-lg">
        <h2 class="text-heading-md">Tóm tắt</h2>
        <div class="mt-md flex justify-between text-body-sm">
          <span class="text-body-light">Tạm tính</span>
          <span>{{ subtotalLabel }}</span>
        </div>
        <div class="mt-sm flex justify-between text-body-sm">
          <span class="text-body-light">Vận chuyển</span>
          <span>Tính ở bước sau</span>
        </div>
        <div class="mt-md flex justify-between border-t border-hairline-light pt-md">
          <span class="text-heading-md">Tổng</span>
          <span class="text-heading-md">{{ subtotalLabel }}</span>
        </div>
        <KvButton to="/checkout" variant="commerce" block class="mt-lg">Thanh toán</KvButton>
        <KvButton to="/shop" variant="secondary" block class="mt-sm">Tiếp tục mua</KvButton>
      </aside>
    </div>

    <KvEmptyState
      v-else
      title="Giỏ hàng trống"
      description="Duyệt cửa hàng và thêm sản phẩm vào giỏ."
    >
      <template #action>
        <KvButton to="/shop" variant="primary">Đến cửa hàng</KvButton>
      </template>
    </KvEmptyState>
  </div>
</template>
