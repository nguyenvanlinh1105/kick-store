<script setup>
import { watch, onMounted, onUnmounted } from 'vue'
import { storeToRefs } from 'pinia'
import { RouterLink, useRouter } from 'vue-router'
import { useCartStore } from '@/stores/cart'
import KvEmptyState from '@/components/ui/KvEmptyState.vue'
import { formatVnd } from '@/data/demo'

const router = useRouter()
const cart = useCartStore()
const { items, drawerOpen, count, subtotalLabel } = storeToRefs(cart)

// Lock body scrolling when drawer is open
watch(
  drawerOpen,
  (isOpen) => {
    if (isOpen) {
      document.body.style.overflow = 'hidden'
    } else {
      document.body.style.overflow = ''
    }
  },
  { immediate: true },
)

function onKeydown(e) {
  if (e.key === 'Escape' && drawerOpen.value) {
    cart.closeDrawer()
  }
}

onMounted(() => window.addEventListener('keydown', onKeydown))
onUnmounted(() => {
  window.removeEventListener('keydown', onKeydown)
  document.body.style.overflow = ''
})

function goToCheckout() {
  cart.closeDrawer()
  router.push('/checkout')
}

function goToCart() {
  cart.closeDrawer()
  router.push('/cart')
}
</script>

<template>
  <Teleport to="body">
    <!-- Backdrop Overlay -->
    <Transition
      enter-active-class="transition-opacity duration-300 ease-out"
      enter-from-class="opacity-0"
      enter-to-class="opacity-100"
      leave-active-class="transition-opacity duration-200 ease-in"
      leave-from-class="opacity-100"
      leave-to-class="opacity-0"
    >
      <div
        v-if="drawerOpen"
        class="fixed inset-0 z-50 bg-slate-950/60 backdrop-blur-sm"
        @click="cart.closeDrawer()"
      ></div>
    </Transition>

    <!-- Slide-Over Right Drawer Panel -->
    <Transition
      enter-active-class="transition-transform duration-300 ease-out"
      enter-from-class="translate-x-full"
      enter-to-class="translate-x-0"
      leave-active-class="transition-transform duration-200 ease-in"
      leave-from-class="translate-x-0"
      leave-to-class="translate-x-full"
    >
      <aside
        v-if="drawerOpen"
        class="fixed inset-y-0 right-0 z-50 w-full max-w-md bg-white shadow-2xl flex flex-col border-l border-slate-200 text-slate-900"
        role="dialog"
        aria-modal="true"
        aria-label="Giỏ hàng xem nhanh"
      >
        <!-- Header -->
        <div class="h-16 px-6 bg-white border-b border-slate-100 flex items-center justify-between shrink-0">
          <div class="flex items-center gap-2">
            <span class="text-base font-extrabold text-slate-900">Giỏ Hàng Xem Nhanh</span>
            <span v-if="count" class="px-2.5 py-0.5 bg-amber-500/10 text-amber-700 font-extrabold text-xs rounded-full">
              {{ count }} sản phẩm
            </span>
          </div>

          <button
            type="button"
            class="w-9 h-9 flex items-center justify-center text-slate-400 hover:text-slate-900 hover:bg-slate-100 rounded-full transition-all cursor-pointer text-lg"
            @click="cart.closeDrawer()"
            aria-label="Đóng giỏ hàng"
          >
            ✕
          </button>
        </div>

        <!-- Cart Content Items Body -->
        <div class="flex-1 overflow-y-auto p-6 flex flex-col gap-4">
          <div v-if="items.length" class="flex flex-col gap-4">
            <div
              v-for="item in items"
              :key="item.key"
              class="flex items-center gap-4 p-3 bg-slate-50 border border-slate-200 rounded-2xl relative group"
            >
              <img :src="item.image" :alt="item.name" class="w-20 h-20 object-cover rounded-xl shrink-0" />

              <div class="flex-1 flex flex-col gap-1 pr-6">
                <span class="text-[10px] font-extrabold text-amber-600 uppercase tracking-wider">{{ item.brand }}</span>
                <RouterLink
                  :to="`/shop/${item.slug}`"
                  class="text-xs font-extrabold text-slate-900 line-clamp-1 no-underline hover:text-amber-600"
                  @click="cart.closeDrawer()"
                >
                  {{ item.name }}
                </RouterLink>
                <span class="text-[11px] text-slate-500 font-medium">Size: <strong>{{ item.size }}</strong> · Màu: <strong>{{ item.color }}</strong></span>

                <div class="flex items-center justify-between mt-2">
                  <!-- Quantity adjuster -->
                  <div class="flex items-center bg-white border border-slate-200 rounded-lg">
                    <button
                      type="button"
                      class="w-7 h-7 flex items-center justify-center text-slate-700 font-bold text-xs cursor-pointer hover:bg-slate-100 rounded-l-lg"
                      @click="cart.updateQty(item.key, item.qty - 1)"
                    >
                      −
                    </button>
                    <span class="w-8 text-center text-xs font-extrabold text-slate-900">{{ item.qty }}</span>
                    <button
                      type="button"
                      class="w-7 h-7 flex items-center justify-center text-slate-700 font-bold text-xs cursor-pointer hover:bg-slate-100 rounded-r-lg"
                      @click="cart.updateQty(item.key, item.qty + 1)"
                    >
                      +
                    </button>
                  </div>

                  <span class="text-xs font-extrabold text-slate-900">{{ formatVnd(item.price * item.qty) }}</span>
                </div>
              </div>

              <!-- Delete item icon -->
              <button
                type="button"
                class="absolute top-3 right-3 text-slate-400 hover:text-red-500 text-sm cursor-pointer"
                @click="cart.removeItem(item.key)"
                title="Xóa sản phẩm"
              >
                ✕
              </button>
            </div>
          </div>

          <!-- Empty Cart State -->
          <KvEmptyState
            v-else
            title="Giỏ hàng của bạn đang trống"
            description="Hãy chọn sản phẩm sneaker yêu thích để thêm vào giỏ hàng."
            action-text="Khám phá cửa hàng"
            @action="goToCart"
          />
        </div>

        <!-- Footer Checkout CTA -->
        <div v-if="items.length" class="p-6 bg-white border-t border-slate-100 flex flex-col gap-4 shrink-0 shadow-lg">
          <!-- Free shipping progress indicator -->
          <div class="flex flex-col gap-1 text-[11px] text-slate-600 bg-amber-500/10 p-2.5 rounded-xl border border-amber-500/20">
            <span class="font-bold text-amber-700">🚚 Miễn phí vận chuyển cho đơn từ 3.000.000đ</span>
          </div>

          <div class="flex items-center justify-between text-xs text-slate-600">
            <span>Tạm tính giỏ hàng:</span>
            <span class="text-lg font-extrabold text-amber-600">{{ subtotalLabel }}</span>
          </div>

          <div class="grid grid-cols-2 gap-3">
            <button
              type="button"
              class="h-12 bg-white border-2 border-slate-900 text-slate-900 font-extrabold text-xs rounded-xl hover:bg-slate-900 hover:text-white transition-all cursor-pointer uppercase tracking-wider"
              @click="goToCart"
            >
              Xem Giỏ Hàng
            </button>

            <button
              type="button"
              class="h-12 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 transition-all cursor-pointer uppercase tracking-wider shadow-lg"
              @click="goToCheckout"
            >
              ⚡ THANH TOÁN NGAY
            </button>
          </div>
        </div>
      </aside>
    </Transition>
  </Teleport>
</template>
