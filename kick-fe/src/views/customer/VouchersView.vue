<script setup>
import vouchersJson from '@/data/json/vouchers.json'
import customersJson from '@/data/json/customers.json'
import { formatVnd } from '@/data/demo'

const user = customersJson[0]
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <h1 class="text-2xl font-extrabold text-slate-900">Ví Voucher & Điểm Thưởng</h1>
      <span class="text-xs font-extrabold text-amber-700 bg-amber-500/10 border border-amber-500/30 px-3.5 py-1.5 rounded-full">
        ⭐ {{ user.points }} Điểm thưởng ({{ formatVnd(user.points * 1000) }})
      </span>
    </div>

    <!-- PURE WHITE LIGHT VOUCHER CARDS -->
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
      <div
        v-for="v in vouchersJson"
        :key="v.id"
        class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-3 shadow-sm hover:shadow-md transition-all text-slate-900"
      >
        <div class="flex items-center justify-between">
          <span class="text-xs font-extrabold text-amber-600 font-mono bg-amber-500/10 border border-amber-500/30 px-3 py-1 rounded-xl">
            {{ v.code }}
          </span>
          <span class="text-[11px] text-slate-500 font-bold">HSD: {{ v.expiry }}</span>
        </div>

        <div class="flex flex-col">
          <h3 class="text-sm font-extrabold text-slate-900">{{ v.title }}</h3>
          <p class="text-xs text-slate-500 font-medium mt-0.5">{{ v.desc }}</p>
        </div>

        <div class="flex items-center justify-between border-t border-slate-100 pt-3 text-xs">
          <span class="text-slate-600 font-medium">Đơn tối thiểu: <strong class="text-slate-900 font-extrabold">{{ formatVnd(v.minSpend) }}</strong></span>
          <button
            type="button"
            class="px-4 py-2 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 cursor-pointer shadow transition-colors"
            @click="$router.push('/cart')"
          >
            Dùng ngay
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
