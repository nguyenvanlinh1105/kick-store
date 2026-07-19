<script setup>
import { storeToRefs } from 'pinia'
import { useCartStore } from '@/stores/cart'
import KvButton from '@/components/ui/KvButton.vue'
import KvModal from '@/components/ui/KvModal.vue'
import KvEmptyState from '@/components/ui/KvEmptyState.vue'

const cart = useCartStore()
const { items, drawerOpen, subtotalLabel } = storeToRefs(cart)
</script>

<template>
  <KvModal :open="drawerOpen" title="Giỏ hàng" size="md" @close="cart.closeDrawer()">
    <div v-if="items.length" class="flex flex-col gap-md">
      <article
        v-for="item in items"
        :key="item.key"
        class="flex gap-md border-b border-hairline-light pb-md"
      >
        <img :src="item.image" :alt="item.name" class="h-20 w-20 rounded-md object-cover" />
        <div class="flex flex-1 flex-col gap-xxs">
          <p class="text-caption-md text-mute-light">{{ item.brand }}</p>
          <p class="text-heading-md">{{ item.name }}</p>
          <p class="text-caption-md text-mute-light">
            Size {{ item.size }} · {{ item.color }}
          </p>
          <div class="mt-auto flex items-center justify-between">
            <div class="flex items-center gap-sm">
              <button
                type="button"
                class="flex h-8 w-8 items-center justify-center rounded-full border border-ash-light"
                @click="cart.updateQty(item.key, item.qty - 1)"
              >
                −
              </button>
              <span class="text-body-sm w-6 text-center">{{ item.qty }}</span>
              <button
                type="button"
                class="flex h-8 w-8 items-center justify-center rounded-full border border-ash-light"
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
          class="self-start text-mute-light active:text-warning"
          aria-label="Xóa"
          @click="cart.removeItem(item.key)"
        >
          ✕
        </button>
      </article>
    </div>

    <KvEmptyState
      v-else
      title="Giỏ trống"
      description="Thêm đôi giày yêu thích để bắt đầu checkout."
    >
      <template #action>
        <KvButton to="/shop" variant="primary" @click="cart.closeDrawer()">Mua sắm</KvButton>
      </template>
    </KvEmptyState>

    <template v-if="items.length" #footer>
      <div class="flex flex-col gap-md">
        <div class="flex items-center justify-between">
          <span class="text-body-sm text-body-light">Tạm tính</span>
          <span class="text-heading-md">{{ subtotalLabel }}</span>
        </div>
        <div class="flex flex-col gap-sm sm:flex-row">
          <KvButton to="/cart" variant="secondary" block @click="cart.closeDrawer()">
            Xem giỏ
          </KvButton>
          <KvButton to="/checkout" variant="commerce" block @click="cart.closeDrawer()">
            Thanh toán
          </KvButton>
        </div>
      </div>
    </template>
  </KvModal>
</template>
