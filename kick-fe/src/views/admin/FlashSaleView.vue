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
        <h1 class="text-2xl font-extrabold text-white">Quản Lý Flash Sale & Chiến Dịch Combo</h1>
        <p class="text-xs text-neutral-400 mt-1">Cấu hình các đợt giảm giá chớp nhoáng đếm ngược trên trang chủ</p>
      </div>

      <button class="px-5 py-2.5 bg-primary text-black font-extrabold text-xs rounded-xl hover:bg-primary-hover cursor-pointer">
        + Tạo Đợt Flash Sale Mới
      </button>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
      <div v-for="c in campaigns" :key="c.id" class="bg-neutral-900 border border-white/10 rounded-2xl p-5 flex flex-col gap-3">
        <div class="flex items-center justify-between">
          <span class="text-xs font-bold text-primary uppercase">{{ c.status === 'ACTIVE' ? '🔴 Đang diễn ra' : '⏳ Sắp diễn ra' }}</span>
          <span class="text-xs text-neutral-400">{{ c.endIn }}</span>
        </div>
        <h3 class="text-base font-bold text-white">{{ c.name }}</h3>
        <span class="text-xs text-neutral-300">Mức giảm: <strong class="text-emerald-400">{{ c.discountPct }}</strong> ({{ c.itemCount }} sản phẩm áp dụng)</span>
      </div>
    </div>
  </div>
</template>
