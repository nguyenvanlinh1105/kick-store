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
        <h1 class="text-2xl font-extrabold text-white">Cấu Hình Hạng Thành Viên & Quyền Lợi (Loyalty Tiers)</h1>
        <p class="text-xs text-neutral-400 mt-1">Quy định ngưỡng tổng chi tiêu và đặc quyền tích điểm thưởng cho từng cấp độ</p>
      </div>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
      <div v-for="t in tiers" :key="t.name" class="bg-neutral-900 border border-white/10 rounded-2xl p-5 flex flex-col gap-2">
        <span class="text-base font-extrabold text-primary">{{ t.name }}</span>
        <span class="text-xs text-neutral-300">Ngưỡng tích lũy: <strong class="text-white font-bold">{{ formatVnd(t.minSpent) }}</strong></span>
        <p class="text-xs text-neutral-400 mt-1 bg-black/40 p-2.5 rounded-lg border border-white/5">{{ t.perk }}</p>
      </div>
    </div>
  </div>
</template>
