<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import KvSelect from '@/components/ui/KvSelect.vue'
import { formatVnd } from '@/data/demo'
import { useCartStore } from '@/stores/cart'

const router = useRouter()
const cart = useCartStore()

const addressForm = ref({
  fullName: 'Nguyễn Văn A',
  phone: '0987654321',
  email: 'customer@example.com',
  province: 'Hà Nội',
  district: 'Cầu Giấy',
  ward: 'Dịch Vọng',
  street: 'Số 10 Phạm Hùng',
  note: '',
})

const provinceOptions = ['Hà Nội', 'TP. Hồ Chí Minh', 'Đà Nẵng', 'Hải Phòng', 'Cần Thơ']
const districtOptions = ['Cầu Giấy', 'Ba Đình', 'Đống Đa', 'Hoàn Kiếm', 'Thanh Xuân']
const wardOptions = ['Dịch Vọng', 'Dịch Vọng Hậu', 'Mai Dịch', 'Yên Hòa']

const selectedShipping = ref('express')
const shippingMethods = [
  { id: 'standard', name: 'Giao Hàng Tiêu Chuẩn (2-3 ngày)', price: 35000 },
  { id: 'express', name: 'Giao Hàng Nhanh (1-2 ngày)', price: 50000 },
  { id: 'super', name: 'Giao Hỏa Tốc (Trong ngày - Hà Nội/TP.HCM)', price: 80000 },
]

const selectedPayment = ref('vietqr')
const paymentMethods = [
  { id: 'vietqr', name: 'Chuyển Khoản QR Bank (VietQR / Napas247)', icon: '🏦', desc: 'Quét mã QR qua ứng dụng ngân hàng tự động duyệt' },
  { id: 'cod', name: 'Thanh toán khi nhận hàng (COD)', icon: '💵', desc: 'Thanh toán tiền mặt cho shipper khi nhận kiện hàng' },
  { id: 'vnpay', name: 'Cổng VNPAY (ATM / QR Code)', icon: '💳', desc: 'Thanh toán bảo mật qua VNPAY Gateway' },
  { id: 'momo', name: 'Ví Điện Tử MoMo', icon: '📱', desc: 'Thanh toán tức thì qua ví MoMo' },
  { id: 'visa', name: 'Thẻ Quốc Tế (Visa / Mastercard)', icon: '🌐', desc: 'Thanh toán thẻ tín dụng/ghi nợ quốc tế' },
]

const isSubmitting = ref(false)

function handlePlaceOrder() {
  if (!addressForm.value.fullName || !addressForm.value.phone) {
    alert('Vui lòng điền đầy đủ Họ tên và Số điện thoại nhận hàng!')
    return
  }

  isSubmitting.value = true
  setTimeout(() => {
    isSubmitting.value = false
    const mockOrderCode = 'KV-' + Math.floor(100000 + Math.random() * 900000)
    router.push({
      path: '/order-success',
      query: { code: mockOrderCode, payment: selectedPayment.value, total: cart.total },
    })
    cart.clear()
  }, 1000)
}
</script>

<template>
  <div class="bg-slate-50 text-slate-900 min-h-screen py-10">
    <div class="kv-container">
      <h1 class="text-3xl font-extrabold text-slate-900 mb-8">Thanh Toán Đơn Hàng</h1>

      <div v-if="cart.items.length === 0" class="py-16 text-center bg-white border border-slate-200 rounded-3xl p-8 shadow-sm">
        <p class="text-slate-600 font-bold mb-4">Không có sản phẩm nào để thanh toán.</p>
        <button @click="$router.push('/shop')" class="px-6 py-3 bg-slate-900 text-white font-extrabold text-xs rounded-xl shadow">
          Quay Lại Cửa Hàng
        </button>
      </div>

      <div v-else class="grid grid-cols-1 md:grid-cols-3 gap-8 items-start">
        <div class="md:col-span-2 flex flex-col gap-8">
          <!-- 1. ADDRESS FORM -->
          <div class="bg-white border border-slate-200 rounded-3xl p-6 shadow-sm flex flex-col gap-4">
            <h2 class="text-sm font-extrabold text-slate-900 uppercase tracking-wider flex items-center gap-2 border-b border-slate-100 pb-3">
              <span>📍 1. Thông Tin Nhận Hàng</span>
            </h2>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div class="flex flex-col gap-1.5">
                <label class="text-xs font-bold text-slate-700">Họ và tên người nhận *</label>
                <input v-model="addressForm.fullName" type="text" class="h-10 px-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900" />
              </div>

              <div class="flex flex-col gap-1.5">
                <label class="text-xs font-bold text-slate-700">Số điện thoại *</label>
                <input v-model="addressForm.phone" type="text" class="h-10 px-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900" />
              </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-3 gap-3">
              <KvSelect v-model="addressForm.province" label="Tỉnh / Thành phố" :options="provinceOptions" :dark="false" />
              <KvSelect v-model="addressForm.district" label="Quận / Huyện" :options="districtOptions" :dark="false" />
              <KvSelect v-model="addressForm.ward" label="Phường / Xã" :options="wardOptions" :dark="false" />
            </div>

            <div class="flex flex-col gap-1.5">
              <label class="text-xs font-bold text-slate-700">Địa chỉ cụ thể (Số nhà, tên đường)</label>
              <input v-model="addressForm.street" type="text" class="h-10 px-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900" />
            </div>

            <div class="flex flex-col gap-1.5">
              <label class="text-xs font-bold text-slate-700">Ghi chú cho đơn hàng (Tuỳ chọn)</label>
              <textarea v-model="addressForm.note" rows="2" placeholder="VD: Giao giờ hành chính..." class="p-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900"></textarea>
            </div>
          </div>

          <!-- 2. SHIPPING METHODS -->
          <div class="bg-white border border-slate-200 rounded-3xl p-6 shadow-sm flex flex-col gap-4">
            <h2 class="text-sm font-extrabold text-slate-900 uppercase tracking-wider border-b border-slate-100 pb-3">
              🚚 2. Phương Thức Vận Chuyển
            </h2>

            <div class="flex flex-col gap-3">
              <label
                v-for="sm in shippingMethods"
                :key="sm.id"
                class="flex items-center justify-between p-4 bg-slate-50 border rounded-2xl cursor-pointer transition-all"
                :class="[selectedShipping === sm.id ? 'border-amber-600 bg-amber-500/5' : 'border-slate-200 hover:border-slate-300']"
              >
                <div class="flex items-center gap-3">
                  <input type="radio" v-model="selectedShipping" :value="sm.id" class="accent-amber-600 w-4 h-4" />
                  <span class="text-xs font-bold text-slate-900">{{ sm.name }}</span>
                </div>
                <span class="text-xs font-extrabold text-amber-600">{{ formatVnd(sm.price) }}</span>
              </label>
            </div>
          </div>

          <!-- 3. PAYMENT METHODS -->
          <div class="bg-white border border-slate-200 rounded-3xl p-6 shadow-sm flex flex-col gap-4">
            <h2 class="text-sm font-extrabold text-slate-900 uppercase tracking-wider border-b border-slate-100 pb-3">
              💳 3. Phương Thức Thanh Toán
            </h2>

            <div class="flex flex-col gap-3">
              <label
                v-for="pm in paymentMethods"
                :key="pm.id"
                class="flex flex-col p-4 bg-slate-50 border rounded-2xl cursor-pointer transition-all gap-1"
                :class="[selectedPayment === pm.id ? 'border-amber-600 bg-amber-500/5' : 'border-slate-200 hover:border-slate-300']"
              >
                <div class="flex items-center justify-between">
                  <div class="flex items-center gap-3">
                    <input type="radio" v-model="selectedPayment" :value="pm.id" class="accent-amber-600 w-4 h-4" />
                    <span class="text-sm">{{ pm.icon }}</span>
                    <span class="text-xs font-bold text-slate-900">{{ pm.name }}</span>
                  </div>
                  <span v-if="selectedPayment === pm.id" class="text-[10px] bg-slate-900 text-white font-bold px-2 py-0.5 rounded">Đã chọn</span>
                </div>
                <p class="text-[11px] text-slate-500 pl-7">{{ pm.desc }}</p>
              </label>
            </div>
          </div>
        </div>

        <!-- Sticky Order Summary -->
        <div class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-6 shadow-sm sticky top-20">
          <h2 class="text-base font-extrabold text-slate-900 border-b border-slate-100 pb-3">Tóm Tắt Đơn Hàng</h2>

          <div class="flex flex-col gap-3 max-h-60 overflow-y-auto pr-1">
            <div v-for="item in cart.items" :key="item.key" class="flex items-center justify-between text-xs">
              <div class="flex items-center gap-2.5">
                <img :src="item.image" :alt="item.name" class="w-10 h-10 object-cover rounded-lg" />
                <div>
                  <span class="font-bold text-slate-900 line-clamp-1 block">{{ item.name }}</span>
                  <span class="text-[10px] text-slate-500">x{{ item.qty }} (Size {{ item.size }})</span>
                </div>
              </div>
              <span class="font-bold text-slate-900 shrink-0">{{ formatVnd(item.price * item.qty) }}</span>
            </div>
          </div>

          <div class="flex flex-col gap-2 text-xs border-t border-slate-100 pt-4 text-slate-600">
            <div class="flex justify-between">
              <span>Tạm tính sản phẩm:</span>
              <span class="font-bold text-slate-900">{{ cart.subtotalLabel }}</span>
            </div>
            <div class="flex justify-between">
              <span>Vận chuyển:</span>
              <span class="font-bold text-slate-900">{{ cart.shippingFeeLabel }}</span>
            </div>
            <div v-if="cart.voucherDiscount > 0" class="flex justify-between text-emerald-600 font-bold">
              <span>Giảm giá Voucher:</span>
              <span>-{{ formatVnd(cart.voucherDiscount) }}</span>
            </div>
            <div class="flex justify-between text-lg font-extrabold text-slate-900 border-t border-slate-100 pt-3">
              <span>Tổng thanh toán:</span>
              <span class="text-amber-600 text-xl">{{ cart.totalLabel }}</span>
            </div>
          </div>

          <button
            type="button"
            :disabled="isSubmitting"
            class="w-full h-12 bg-slate-900 text-white font-extrabold text-xs uppercase tracking-wider rounded-xl hover:bg-amber-600 transition-all cursor-pointer shadow-lg disabled:opacity-50"
            @click="handlePlaceOrder"
          >
            {{ isSubmitting ? 'Đang khởi tạo đơn hàng...' : '🔒 ĐẶT HÀNG NGAY' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
