<script setup>
import { ref } from 'vue'
import { RouterLink, useRouter } from 'vue-router'
import { formatVnd } from '@/data/demo'
import { useCartStore } from '@/stores/cart'

const router = useRouter()
const cart = useCartStore()

const voucherCodeInput = ref('')
const voucherFeedback = ref({ message: '', error: false })

function handleApplyVoucher() {
  if (!voucherCodeInput.value) return
  const res = cart.applyVoucher(voucherCodeInput.value)
  voucherFeedback.value = {
    message: res.message,
    error: !res.success,
  }
}
</script>

<template>
  <div class="bg-slate-50 text-slate-900 min-h-screen py-10">
    <div class="kv-container">
      <h1 class="text-3xl font-extrabold text-slate-900 mb-8">Giỏ Hàng Của Bạn</h1>

      <div v-if="cart.items.length === 0" class="py-16 text-center bg-white border border-slate-200 rounded-3xl p-8 shadow-sm">
        <p class="text-slate-600 font-bold mb-4">Giỏ hàng của bạn đang trống.</p>
        <button @click="$router.push('/shop')" class="px-6 py-3 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 cursor-pointer shadow">
          Khám Phá Cửa Hàng
        </button>
      </div>

      <div v-else class="grid grid-cols-1 md:grid-cols-3 gap-8 items-start">
        <!-- Cart Items List -->
        <div class="md:col-span-2 flex flex-col gap-4">
          <div class="flex items-center justify-between p-4 bg-white border border-slate-200 rounded-2xl text-xs shadow-sm">
            <label class="flex items-center gap-2 font-bold text-slate-900 cursor-pointer">
              <input
                type="checkbox"
                :checked="cart.items.every((i) => i.selected)"
                @change="cart.toggleSelectAll($event.target.checked)"
                class="accent-amber-600 w-4 h-4 rounded"
              />
              <span>Chọn tất cả ({{ cart.count }} sản phẩm)</span>
            </label>
            <button
              type="button"
              class="text-red-500 font-extrabold hover:underline cursor-pointer"
              @click="cart.removeSelected"
            >
              Xóa mục đã chọn
            </button>
          </div>

          <div
            v-for="item in cart.items"
            :key="item.key"
            class="flex flex-col sm:flex-row items-center gap-4 p-4 bg-white border border-slate-200 rounded-2xl shadow-sm hover:shadow-md transition-all"
          >
            <input
              type="checkbox"
              :checked="item.selected"
              @change="cart.toggleSelect(item.key)"
              class="accent-amber-600 w-4 h-4 rounded"
            />

            <img :src="item.image" :alt="item.name" class="w-20 h-20 object-cover rounded-xl shrink-0" />

            <div class="flex-1 flex flex-col gap-1 w-full">
              <span class="text-[10px] font-extrabold text-amber-600 uppercase">{{ item.brand }}</span>
              <RouterLink :to="`/shop/${item.slug}`" class="text-sm font-extrabold text-slate-900 no-underline hover:text-amber-600">
                {{ item.name }}
              </RouterLink>
              <span class="text-xs text-slate-500">Size: <strong>{{ item.size }}</strong> | Màu: <strong>{{ item.color }}</strong></span>
              <span class="text-sm font-extrabold text-slate-900 mt-1">{{ formatVnd(item.price) }}</span>
            </div>

            <div class="flex items-center gap-4 shrink-0">
              <div class="flex items-center bg-slate-100 border border-slate-200 rounded-xl">
                <button @click="cart.updateQty(item.key, item.qty - 1)" class="w-8 h-8 text-slate-900 font-bold cursor-pointer">-</button>
                <span class="w-8 text-center text-xs font-extrabold text-slate-900">{{ item.qty }}</span>
                <button @click="cart.updateQty(item.key, item.qty + 1)" class="w-8 h-8 text-slate-900 font-bold cursor-pointer">+</button>
              </div>

              <button
                type="button"
                class="w-8 h-8 flex items-center justify-center text-slate-400 hover:text-red-500 text-sm cursor-pointer"
                @click="cart.removeItem(item.key)"
                title="Xóa"
              >
                ✕
              </button>
            </div>
          </div>
        </div>

        <!-- Order Summary Sidebar -->
        <div class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-6 shadow-sm sticky top-20">
          <h2 class="text-base font-extrabold text-slate-900 border-b border-slate-100 pb-3">Tóm Tắt Đơn Hàng</h2>

          <div class="flex flex-col gap-2">
            <span class="text-xs font-bold text-slate-700">Mã giảm giá (Coupon):</span>
            <div class="flex gap-2">
              <input
                v-model="voucherCodeInput"
                type="text"
                placeholder="Nhập mã (VD: KICK100)"
                class="flex-1 h-10 px-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-mono uppercase focus:outline-none focus:border-amber-600"
              />
              <button
                type="button"
                class="px-4 py-2 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 cursor-pointer"
                @click="handleApplyVoucher"
              >
                Áp dụng
              </button>
            </div>
            <p v-if="voucherFeedback.message" class="text-[11px] font-bold" :class="[voucherFeedback.error ? 'text-red-500' : 'text-emerald-600']">
              {{ voucherFeedback.message }}
            </p>
          </div>

          <div class="flex flex-col gap-2.5 text-xs text-slate-600 border-t border-slate-100 pt-4">
            <div class="flex justify-between">
              <span>Tạm tính ({{ cart.selectedCount }} sp):</span>
              <span class="font-bold text-slate-900">{{ cart.subtotalLabel }}</span>
            </div>
            <div class="flex justify-between">
              <span>Phí vận chuyển:</span>
              <span class="font-bold text-slate-900">{{ cart.shippingFeeLabel }}</span>
            </div>
            <div v-if="cart.voucherDiscount > 0" class="flex justify-between text-emerald-600 font-bold">
              <span>Giảm giá Voucher:</span>
              <span>-{{ formatVnd(cart.voucherDiscount) }}</span>
            </div>
            <div class="flex justify-between text-base font-extrabold text-slate-900 border-t border-slate-100 pt-3">
              <span>Tổng thanh toán:</span>
              <span class="text-amber-600 text-xl">{{ cart.totalLabel }}</span>
            </div>
          </div>

          <button
            type="button"
            class="w-full h-12 bg-slate-900 text-white font-extrabold text-xs uppercase tracking-wider rounded-xl hover:bg-amber-600 transition-all cursor-pointer shadow-lg"
            @click="router.push('/checkout')"
          >
            Tiến Hành Thanh Toán →
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
