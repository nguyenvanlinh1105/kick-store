<script setup>
import { computed } from 'vue'
import { useRoute, RouterLink } from 'vue-router'
import { formatVnd } from '@/data/demo'

const route = useRoute()

const orderCode = computed(() => route.query.code || 'KV-982415')
const paymentMethod = computed(() => route.query.payment || 'vietqr')
const totalAmount = computed(() => Number(route.query.total) || 6480000)

const qrBankUrl = computed(() => {
  return `https://img.vietqr.io/image/MB-0382948291-compact2.png?amount=${totalAmount.value}&addInfo=THANH%20TOAN%20DON%20HANG%20${orderCode.value}&accountName=KICKVERSE%20STORE`
})
</script>

<template>
  <div class="bg-slate-50 text-slate-900 min-h-screen py-12">
    <div class="kv-container max-w-2xl mx-auto">
      <div class="flex flex-col items-center text-center bg-white border border-slate-200 rounded-3xl p-8 md:p-12 shadow-xl">
        <div class="w-20 h-20 rounded-full bg-emerald-100 border-2 border-emerald-500 flex items-center justify-center text-emerald-600 text-4xl mb-4 animate-bounce">
          ✓
        </div>

        <h1 class="text-3xl font-extrabold text-slate-900">Đặt Hàng Thành Công!</h1>
        <p class="text-xs text-slate-500 mt-2">Cảm ơn bạn đã tin tưởng mua sắm tại KickVerse.</p>

        <div class="my-6 p-5 bg-slate-50 border border-slate-200 rounded-2xl w-full flex flex-col gap-2 shadow-inner">
          <span class="text-xs text-slate-500">Mã đơn hàng của bạn:</span>
          <span class="text-2xl font-extrabold text-amber-600 font-mono tracking-wider">{{ orderCode }}</span>
          <span class="text-xs text-slate-700 mt-1">Tổng thanh toán: <strong class="text-slate-900 font-bold">{{ formatVnd(totalAmount) }}</strong></span>
        </div>

        <!-- CONDITIONAL BANK TRANSFER QR (VietQR) -->
        <div v-if="paymentMethod === 'vietqr'" class="w-full bg-slate-900 text-white border border-slate-800 rounded-2xl p-6 mb-8 flex flex-col items-center gap-3 shadow-lg">
          <span class="text-xs font-extrabold text-amber-400 uppercase tracking-wider">Quét Mã QR Chuyển Khoản Ngân Hàng (VietQR)</span>
          <p class="text-[11px] text-neutral-300 max-w-sm">Mở ứng dụng Ngân hàng hoặc Ví điện tử bất kỳ để quét mã bên dưới. Nội dung chuyển khoản đã được tự động điền.</p>
          
          <div class="p-3 bg-white rounded-2xl shadow-md my-2">
            <img :src="qrBankUrl" alt="VietQR Bank Transfer" class="w-56 h-56 object-contain" />
          </div>

          <div class="text-[11px] text-neutral-300 flex flex-col gap-1 w-full max-w-xs text-left bg-neutral-950 p-3 rounded-xl border border-white/10">
            <div>Chủ tài khoản: <strong class="text-white">KICKVERSE STORE</strong></div>
            <div>Số tài khoản: <strong class="text-white font-mono">0382948291</strong> (MB Bank)</div>
            <div>Nội dung CK: <strong class="text-amber-400 font-mono">THANH TOAN DON HANG {{ orderCode }}</strong></div>
          </div>
        </div>

        <div class="flex flex-col sm:flex-row gap-4 w-full">
          <RouterLink
            to="/account/orders"
            class="flex-1 h-12 bg-slate-100 border border-slate-200 text-slate-900 font-bold text-xs rounded-xl flex items-center justify-center hover:bg-slate-200 no-underline transition-all"
          >
            📋 Theo dõi đơn hàng
          </RouterLink>

          <RouterLink
            to="/shop"
            class="flex-1 h-12 bg-slate-900 text-white font-extrabold text-xs rounded-xl flex items-center justify-center hover:bg-amber-600 no-underline transition-all shadow-lg"
          >
            🛍️ Tiếp tục mua sắm
          </RouterLink>
        </div>
      </div>
    </div>
  </div>
</template>
