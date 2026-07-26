<script setup>
import { ref } from 'vue'
import KvTabs from '@/components/ui/KvTabs.vue'

const activeTab = ref('GENERAL')

const tabs = [
  { key: 'GENERAL', label: 'Thông tin Website' },
  { key: 'SHIPPING', label: 'Đơn vị Vận chuyển (GHN / GHTK)' },
  { key: 'PAYMENT', label: 'Cổng Thanh toán (VNPAY / VietQR)' },
  { key: 'EMAIL', label: 'Mẫu Email / SMS Auto' },
]

const settings = ref({
  storeName: 'KickVerse Store',
  hotline: '1900 6789',
  email: 'cskh@kickverse.vn',
  address: 'Số 10 Phạm Hùng, Cầu Giấy, Hà Nội',
  ghnToken: 'GHN-API-TOKEN-2026-KEY',
  vnpayTmnCode: 'VNPAY-TMN-CODE-8888',
})

function saveSettings() {
  alert('Đã lưu cấu hình hệ thống thành công!')
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-extrabold text-slate-900">Cấu Hình Tích Hợp Hệ Thống</h1>
        <p class="text-xs text-slate-500 mt-1 font-medium">Cấu hình kết nối API đơn vị vận chuyển, cổng thanh toán và thông tin chung</p>
      </div>

      <button @click="saveSettings" class="px-5 py-2.5 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 cursor-pointer shadow transition-all">
        💾 LƯU CẤU HÌNH
      </button>
    </div>

    <KvTabs v-model="activeTab" :tabs="tabs" :dark="false" />

    <!-- TAB 1: GENERAL STORE INFO -->
    <div v-if="activeTab === 'GENERAL'" class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-4 shadow-sm">
      <div class="grid grid-cols-2 gap-4">
        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-slate-700">Tên Cửa Hàng / Thương Hiệu</label>
          <input v-model="settings.storeName" class="h-10 px-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-medium" />
        </div>
        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-slate-700">Hotline CSKH</label>
          <input v-model="settings.hotline" class="h-10 px-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-medium" />
        </div>
      </div>
      <div class="grid grid-cols-2 gap-4">
        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-slate-700">Email Liên Hệ</label>
          <input v-model="settings.email" class="h-10 px-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-medium" />
        </div>
        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-slate-700">Địa Chỉ Cửa Hàng Trụ Sở</label>
          <input v-model="settings.address" class="h-10 px-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-medium" />
        </div>
      </div>
    </div>

    <!-- TAB 2: SHIPPING CARRIERS -->
    <div v-else-if="activeTab === 'SHIPPING'" class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-4 shadow-sm">
      <div class="flex flex-col gap-3">
        <div class="p-4 bg-slate-50 border border-slate-200 rounded-2xl flex items-center justify-between">
          <div class="flex flex-col">
            <span class="text-sm font-extrabold text-slate-900">Giao Hàng Nhanh (GHN Express)</span>
            <span class="text-xs text-slate-500 font-medium">Tích hợp tạo đơn tự động & lấy vận đơn tự động</span>
          </div>
          <span class="px-3 py-1 bg-emerald-500/10 text-emerald-700 font-extrabold text-xs rounded-full">Đã kết nối</span>
        </div>

        <div class="p-4 bg-slate-50 border border-slate-200 rounded-2xl flex items-center justify-between">
          <div class="flex flex-col">
            <span class="text-sm font-extrabold text-slate-900">Giao Hàng Tiết Kiệm (GHTK)</span>
            <span class="text-xs text-slate-500 font-medium">Đồng bộ mã tracking đơn hàng</span>
          </div>
          <span class="px-3 py-1 bg-emerald-500/10 text-emerald-700 font-extrabold text-xs rounded-full">Đã kết nối</span>
        </div>
      </div>
    </div>

    <!-- TAB 3: PAYMENT GATEWAYS -->
    <div v-else-if="activeTab === 'PAYMENT'" class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-4 shadow-sm">
      <div class="flex flex-col gap-3">
        <div class="p-4 bg-slate-50 border border-slate-200 rounded-2xl flex items-center justify-between">
          <div class="flex flex-col">
            <span class="text-sm font-extrabold text-slate-900">VietQR Bank Transfer (Napas247)</span>
            <span class="text-xs text-slate-500 font-medium">Tự động tạo mã QR chính xác số tiền & nội dung đơn</span>
          </div>
          <span class="px-3 py-1 bg-emerald-500/10 text-emerald-700 font-extrabold text-xs rounded-full">Hoạt động</span>
        </div>

        <div class="p-4 bg-slate-50 border border-slate-200 rounded-2xl flex items-center justify-between">
          <div class="flex flex-col">
            <span class="text-sm font-extrabold text-slate-900">Cổng Thanh Toán VNPAY Sandbox / Production</span>
            <span class="text-xs text-slate-500 font-medium">Thanh toán qua thẻ ATM / QR Bank</span>
          </div>
          <span class="px-3 py-1 bg-emerald-500/10 text-emerald-700 font-extrabold text-xs rounded-full">Hoạt động</span>
        </div>
      </div>
    </div>

    <!-- TAB 4: EMAIL / SMS -->
    <div v-else class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-4 shadow-sm">
      <p class="text-xs text-slate-600 font-medium">Cấu hình mẫu Email gửi cho khách khi Đặt hàng thành công, Reset password, OTP xác thực.</p>
    </div>
  </div>
</template>
