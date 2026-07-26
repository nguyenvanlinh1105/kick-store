<script setup>
import vouchersJson from '@/data/json/vouchers.json'
import customersJson from '@/data/json/customers.json'
import { formatVnd } from '@/data/demo'

const user = customersJson[0]
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <h1 class="text-2xl font-extrabold text-white">Ví Voucher & Điểm Thưởng</h1>
      <span class="text-xs font-bold text-primary bg-primary/10 border border-primary/20 px-3 py-1 rounded-full">
        ⭐ {{ user.points }} Điểm thưởng ({{ formatVnd(user.points * 1000) }})
      </span>
    </div>

    <!-- Voucher Cards Grid -->
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
      <div
        v-for="v in vouchersJson"
        :key="v.id"
        class="bg-neutral-900 border border-white/10 rounded-2xl p-5 flex flex-col gap-3 relative overflow-hidden"
      >
        <div class="flex items-center justify-between">
          <span class="text-sm font-extrabold text-primary font-mono bg-primary/10 border border-primary/30 px-2.5 py-1 rounded-lg">
            {{ v.code }}
          </span>
          <span class="text-[11px] text-neutral-400">HSD: {{ v.expiry }}</span>
        </div>

        <div class="flex flex-col">
          <h3 class="text-sm font-bold text-white">{{ v.title }}</h3>
          <p class="text-xs text-neutral-400 mt-0.5">{{ v.desc }}</p>
        </div>

        <div class="flex items-center justify-between border-t border-white/10 pt-3 text-xs">
          <span class="text-neutral-400">Đơn tối thiểu: <strong>{{ formatVnd(v.minSpend) }}</strong></span>
          <button
            type="button"
            class="px-3 py-1 bg-primary text-black font-extrabold text-[11px] rounded-lg hover:bg-primary-hover cursor-pointer"
            @click="$router.push('/cart')"
          >
            Dùng ngay
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
