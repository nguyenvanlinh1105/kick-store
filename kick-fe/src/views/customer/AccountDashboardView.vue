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
      <h1 class="text-2xl font-extrabold text-slate-900">Xin chào, {{ user.fullName }}!</h1>
      <p class="text-xs text-slate-500 mt-1 font-medium">Chào mừng bạn trở lại với KickVerse Account Portal.</p>
    </div>

    <!-- TIER & REWARD STATS CARDS (PURE WHITE LIGHT THEME) -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-5">
      <div class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-2 relative overflow-hidden shadow-sm">
        <span class="text-xs font-bold text-slate-500">Hạng Thành Viên</span>
        <span class="text-2xl font-extrabold text-amber-600">HẠNG {{ user.tier }}</span>
        <span class="text-[11px] text-slate-500 font-medium">Tích lũy: {{ formatVnd(user.totalSpent) }}</span>
      </div>

      <div class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-2 shadow-sm">
        <span class="text-xs font-bold text-slate-500">Điểm Tích Lũy</span>
        <span class="text-2xl font-extrabold text-slate-900">{{ user.points }} PTS</span>
        <span class="text-[11px] text-slate-500 font-medium">Quy đổi tương đương {{ formatVnd(user.points * 1000) }}</span>
      </div>

      <div class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-2 shadow-sm">
        <span class="text-xs font-bold text-slate-500">Tổng Đơn Hàng</span>
        <span class="text-2xl font-extrabold text-slate-900">{{ user.orderCount }} Đơn</span>
        <RouterLink to="/account/orders" class="text-xs text-amber-600 font-extrabold hover:underline no-underline mt-auto">Xem tất cả →</RouterLink>
      </div>
    </div>

    <!-- RECENT ORDERS SECTION (PURE WHITE LIGHT THEME) -->
    <div class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-4 shadow-sm">
      <div class="flex items-center justify-between border-b border-slate-100 pb-3">
        <h2 class="text-base font-extrabold text-slate-900">Đơn Hàng Gần Đây</h2>
        <RouterLink to="/account/orders" class="text-xs text-amber-600 font-extrabold hover:underline no-underline">Xem tất cả</RouterLink>
      </div>

      <div class="flex flex-col gap-3">
        <div
          v-for="ord in recentOrders"
          :key="ord.id"
          class="flex flex-col sm:flex-row items-start sm:items-center justify-between p-4 bg-slate-50 border border-slate-200 rounded-2xl gap-4"
        >
          <div class="flex flex-col gap-1">
            <div class="flex items-center gap-3">
              <span class="text-sm font-extrabold text-amber-600 font-mono">{{ ord.id }}</span>
              <span class="text-[10px] font-extrabold px-2.5 py-0.5 rounded-full bg-blue-500/10 text-blue-700">{{ ord.statusLabel }}</span>
            </div>
            <span class="text-xs text-slate-500 font-medium">{{ ord.createdAt }} · {{ ord.items.length }} sản phẩm</span>
          </div>

          <div class="flex items-center gap-4 ml-auto sm:ml-0">
            <span class="text-sm font-extrabold text-slate-900">{{ formatVnd(ord.total) }}</span>
            <RouterLink :to="`/account/orders/${ord.id}`" class="px-3.5 py-1.5 bg-slate-900 text-xs font-extrabold text-white rounded-xl hover:bg-amber-600 no-underline transition-colors shadow">
              Chi tiết
            </RouterLink>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
