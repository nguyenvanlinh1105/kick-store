<script setup>
import { ref } from 'vue'

const campaigns = ref([
  { id: 1, name: 'Flash Sale Cuối Tuần Sôi Động', status: 'ACTIVE', discountPct: '20% - 40%', itemCount: 12, endIn: '05 giờ 24 phút' },
  { id: 2, name: 'Combo Mua 1 Tặng 1 Vớ Thể Thao', status: 'UPCOMING', discountPct: 'Combo Special', itemCount: 6, endIn: 'Bắt đầu từ 01/08' },
])
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-extrabold text-slate-900">Quản Lý Flash Sale & Chiến Dịch Combo</h1>
        <p class="text-xs text-slate-500 mt-1 font-medium">Cấu hình các đợt giảm giá chớp nhoáng đếm ngược trên trang chủ</p>
      </div>

      <button class="px-5 py-2.5 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 transition-all cursor-pointer shadow">
        + Tạo Đợt Flash Sale Mới
      </button>
    </div>

    <!-- PURE WHITE LIGHT FLASH SALE CARDS -->
    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
      <div v-for="c in campaigns" :key="c.id" class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-3 shadow-sm hover:shadow-md transition-all">
        <div class="flex items-center justify-between">
          <span class="text-xs font-extrabold uppercase" :class="[c.status === 'ACTIVE' ? 'text-red-600' : 'text-amber-600']">
            {{ c.status === 'ACTIVE' ? '🔴 Đang diễn ra' : '⏳ Sắp diễn ra' }}
          </span>
          <span class="text-xs text-slate-500 font-bold">{{ c.endIn }}</span>
        </div>
        <h3 class="text-base font-extrabold text-slate-900">{{ c.name }}</h3>
        <span class="text-xs text-slate-600 font-medium">Mức giảm: <strong class="text-emerald-600 font-extrabold">{{ c.discountPct }}</strong> ({{ c.itemCount }} sản phẩm áp dụng)</span>
      </div>
    </div>
  </div>
</template>
