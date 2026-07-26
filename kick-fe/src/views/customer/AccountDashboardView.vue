<script setup>
import { RouterLink } from 'vue-router'
import customersJson from '@/data/json/customers.json'
import ordersJson from '@/data/json/orders.json'
import { formatVnd } from '@/data/demo'

const user = customersJson[0]
const recentOrders = ordersJson.slice(0, 2)
</script>

<template>
  <div class="flex flex-col gap-8">
    <div>
      <h1 class="text-2xl font-extrabold text-white">Xin chào, {{ user.fullName }}!</h1>
      <p class="text-xs text-neutral-400 mt-1">Chào mừng bạn trở lại với KickVerse Account Portal.</p>
    </div>

    <!-- TIER & REWARD STATS CARDS -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
      <div class="bg-neutral-900 border border-white/10 rounded-2xl p-5 flex flex-col gap-2 relative overflow-hidden">
        <span class="text-xs font-bold text-neutral-400">Hạng Thành Viên</span>
        <span class="text-2xl font-extrabold text-amber-400">HẠNG {{ user.tier }}</span>
        <span class="text-[11px] text-neutral-400">Tích lũy: {{ formatVnd(user.totalSpent) }}</span>
      </div>

      <div class="bg-neutral-900 border border-white/10 rounded-2xl p-5 flex flex-col gap-2">
        <span class="text-xs font-bold text-neutral-400">Điểm Tích Lũy</span>
        <span class="text-2xl font-extrabold text-primary">{{ user.points }} PTS</span>
        <span class="text-[11px] text-neutral-400">Quy đổi tương đương {{ formatVnd(user.points * 1000) }}</span>
      </div>

      <div class="bg-neutral-900 border border-white/10 rounded-2xl p-5 flex flex-col gap-2">
        <span class="text-xs font-bold text-neutral-400">Tổng Đơn Hàng</span>
        <span class="text-2xl font-extrabold text-white">{{ user.orderCount }} Đơn</span>
        <RouterLink to="/account/orders" class="text-[11px] text-primary font-bold hover:underline no-underline mt-auto">Xem tất cả →</RouterLink>
      </div>
    </div>

    <!-- RECENT ORDERS SECTION -->
    <div class="bg-neutral-900 border border-white/10 rounded-2xl p-6 flex flex-col gap-4">
      <div class="flex items-center justify-between border-b border-white/10 pb-3">
        <h2 class="text-base font-bold text-white">Đơn Hàng Gần Đây</h2>
        <RouterLink to="/account/orders" class="text-xs text-primary font-bold hover:underline no-underline">Xem tất cả</RouterLink>
      </div>

      <div class="flex flex-col gap-3">
        <div
          v-for="ord in recentOrders"
          :key="ord.id"
          class="flex flex-col sm:flex-row items-start sm:items-center justify-between p-4 bg-black/40 border border-white/5 rounded-xl gap-4"
        >
          <div class="flex flex-col gap-1">
            <div class="flex items-center gap-3">
              <span class="text-sm font-extrabold text-primary font-mono">{{ ord.id }}</span>
              <span class="text-[10px] font-bold px-2 py-0.5 rounded bg-blue-500/20 text-blue-400">{{ ord.statusLabel }}</span>
            </div>
            <span class="text-xs text-neutral-400">{{ ord.createdAt }} · {{ ord.items.length }} sản phẩm</span>
          </div>

          <div class="flex items-center gap-4 ml-auto sm:ml-0">
            <span class="text-sm font-extrabold text-white">{{ formatVnd(ord.total) }}</span>
            <RouterLink :to="`/account/orders/${ord.id}`" class="px-3 py-1.5 bg-neutral-800 text-xs font-bold text-white rounded-lg hover:bg-neutral-700 no-underline">
              Chi tiết
            </RouterLink>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
