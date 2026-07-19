<script setup>
import { computed, ref } from 'vue'
import { storeToRefs } from 'pinia'
import { RouterLink, useRouter } from 'vue-router'
import KvButton from '@/components/ui/KvButton.vue'
import KvInput from '@/components/ui/KvInput.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'
import { useCartStore } from '@/stores/cart'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const cart = useCartStore()
const auth = useAuthStore()
const { items, subtotalLabel } = storeToRefs(cart)

const method = ref('COD')
const address = ref({
  fullName: auth.user?.fullName || '',
  phone: '',
  line: '',
  city: 'TP. Hồ Chí Minh',
})

const canSubmit = computed(() => items.value.length && address.value.line && address.value.phone)

function placeOrder() {
  if (!canSubmit.value) return
  cart.clear()
  router.push('/account/orders')
}
</script>

<template>
  <div class="kv-container py-xl">
    <KvPageHeader title="Thanh toán" description="Xác nhận địa chỉ và phương thức thanh toán" />

    <div v-if="items.length" class="grid gap-xl kv:grid-cols-[1fr_360px]">
      <div class="flex flex-col gap-lg">
        <section class="rounded-md bg-surface-card p-lg">
          <h2 class="text-heading-md">Địa chỉ giao hàng</h2>
          <div class="mt-md grid gap-md sm:grid-cols-2">
            <KvInput v-model="address.fullName" label="Họ tên" required />
            <KvInput v-model="address.phone" label="Số điện thoại" required />
            <div class="sm:col-span-2">
              <KvInput v-model="address.line" label="Địa chỉ" required />
            </div>
            <KvInput v-model="address.city" label="Thành phố" />
          </div>
        </section>

        <section class="rounded-md bg-surface-card p-lg">
          <h2 class="text-heading-md">Phương thức thanh toán</h2>
          <div class="mt-md flex flex-col gap-sm">
            <label
              v-for="m in ['COD', 'VNPAY', 'MOMO']"
              :key="m"
              class="flex cursor-pointer items-center gap-md rounded-md border px-md py-md"
              :class="method === m ? 'border-primary bg-canvas-light' : 'border-hairline-light'"
            >
              <input v-model="method" type="radio" :value="m" class="accent-primary" />
              <span class="text-body-sm">{{ m }}</span>
            </label>
          </div>
        </section>
      </div>

      <aside class="h-fit rounded-md bg-surface-card p-lg">
        <h2 class="text-heading-md">Đơn hàng ({{ items.length }})</h2>
        <ul class="mt-md flex flex-col gap-sm">
          <li v-for="item in items" :key="item.key" class="flex justify-between text-body-sm">
            <span class="text-body-light">{{ item.name }} × {{ item.qty }}</span>
            <span>{{ item.priceLabel }}</span>
          </li>
        </ul>
        <div class="mt-md flex justify-between border-t border-hairline-light pt-md">
          <span class="text-heading-md">Tổng</span>
          <span class="text-heading-md">{{ subtotalLabel }}</span>
        </div>
        <KvButton
          variant="commerce"
          block
          class="mt-lg"
          :disabled="!canSubmit"
          @click="placeOrder"
        >
          Đặt hàng
        </KvButton>
      </aside>
    </div>

    <p v-else class="text-body-md text-body-light">
      Giỏ trống.
      <RouterLink to="/shop" class="text-link-light">Quay lại cửa hàng</RouterLink>
    </p>
  </div>
</template>
