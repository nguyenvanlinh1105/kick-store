<script setup>
import { ref } from 'vue'
import { formatVnd } from '@/data/demo'

const tiers = ref([
  { name: 'Đồng (Bronze)', minSpent: 0, perk: 'Tích điểm 1% trên mỗi đơn hàng' },
  { name: 'Bạc (Silver)', minSpent: 5000000, perk: 'Tích điểm 2% + Voucher sinh nhật 100k' },
  { name: 'Vàng (Gold)', minSpent: 15000000, perk: 'Tích điểm 3.5% + Freeship mọi đơn hàng' },
  { name: 'Kim Cương (Diamond)', minSpent: 30000000, perk: 'Tích điểm 5% + Quyền ưu tiên đặt drop sản phẩm Limited' },
])
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-extrabold text-slate-900">Cấu Hình Hạng Thành Viên & Quyền Lợi (Loyalty Tiers)</h1>
        <p class="text-xs text-slate-500 mt-1 font-medium">Quy định ngưỡng tổng chi tiêu và đặc quyền tích điểm thưởng cho từng cấp độ</p>
      </div>
    </div>

    <!-- PURE WHITE LIGHT LOYALTY CARDS -->
    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
      <div v-for="t in tiers" :key="t.name" class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-3 shadow-sm hover:shadow-md transition-all">
        <span class="text-lg font-extrabold text-amber-600">{{ t.name }}</span>
        <span class="text-xs text-slate-600 font-medium">Ngưỡng tích lũy chi tiêu: <strong class="text-slate-900 font-extrabold">{{ formatVnd(t.minSpent) }}</strong></span>
        <p class="text-xs text-slate-700 font-medium mt-1 bg-slate-50 p-3 rounded-2xl border border-slate-200">{{ t.perk }}</p>
      </div>
    </div>
  </div>
</template>
