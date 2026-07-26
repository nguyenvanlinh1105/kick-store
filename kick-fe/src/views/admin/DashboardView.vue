<script setup>
import { ref } from 'vue'
import { formatVnd } from '@/data/demo'
import productsJson from '@/data/json/products.json'
import ordersJson from '@/data/json/orders.json'

const selectedPeriod = ref('month')

const stats = ref([
  { title: 'Tổng Doanh Thu', value: '148.500.000đ', change: '+18.5%', isUp: true },
  { title: 'Đơn Hàng Mới', value: '1,240', change: '+12.3%', isUp: true },
  { title: 'Tỷ Lệ Hủy Đơn', value: '2.1%', change: '-0.8%', isUp: false },
  { title: 'Giá Trị Đơn Trung Bình (AOV)', value: '3.250.000đ', change: '+5.4%', isUp: true },
])

const lowStockItems = productsJson.filter((p) => p.stockCount <= 10)
</script>

<template>
  <div class="flex flex-col gap-8">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-extrabold text-white">Báo Cáo Tổng Quan Dashboard</h1>
        <p class="text-xs text-neutral-400 mt-1">Thống kê hiệu suất kinh doanh toàn hệ thống KickVerse</p>
      </div>

      <select v-model="selectedPeriod" class="h-10 px-4 text-xs bg-neutral-900 border border-white/15 rounded-xl text-white font-bold">
        <option value="today">Hôm nay</option>
        <option value="week">Tuần này</option>
        <option value="month">Tháng này</option>
        <option value="year">Năm 2026</option>
      </select>
    </div>

    <!-- METRIC CARDS GRID -->
    <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
      <div v-for="s in stats" :key="s.title" class="bg-neutral-900 border border-white/10 rounded-2xl p-5 flex flex-col gap-2">
        <span class="text-xs font-bold text-neutral-400">{{ s.title }}</span>
        <span class="text-2xl font-extrabold text-white">{{ s.value }}</span>
        <span class="text-[11px] font-bold" :class="[s.isUp ? 'text-emerald-400' : 'text-red-400']">
          {{ s.change }} so với kỳ trước
        </span>
      </div>
    </div>

    <!-- REVENUE CHART & LOW STOCK ALERT GRID -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
      <!-- Simulated Revenue Chart -->
      <div class="md:col-span-2 bg-neutral-900 border border-white/10 rounded-2xl p-6 flex flex-col gap-4">
        <div class="flex items-center justify-between border-b border-white/10 pb-3">
          <h2 class="text-sm font-bold text-white uppercase tracking-wider">Biểu Đồ Doanh Thu Theo Tháng</h2>
          <span class="text-xs text-primary font-bold">Đơn vị: Triệu VNĐ</span>
        </div>

        <div class="h-64 flex items-end justify-between gap-3 pt-6 px-4 border-b border-white/10">
          <div v-for="(val, idx) in [45, 68, 92, 110, 85, 130, 148]" :key="idx" class="flex-1 flex flex-col items-center gap-2 group">
            <span class="text-[10px] font-bold text-primary opacity-0 group-hover:opacity-100 transition-opacity">{{ val }}M</span>
            <div class="w-full bg-gradient-to-t from-primary/30 to-primary rounded-t-lg transition-all group-hover:bg-primary-hover" :style="{ height: `${val * 1.5}px` }"></div>
            <span class="text-[10px] text-neutral-400">T{{ idx + 1 }}</span>
          </div>
        </div>
      </div>

      <!-- Low Stock Warnings Widget -->
      <div class="bg-neutral-900 border border-white/10 rounded-2xl p-6 flex flex-col gap-4">
        <div class="flex items-center justify-between border-b border-white/10 pb-3">
          <h2 class="text-sm font-bold text-white uppercase tracking-wider flex items-center gap-1.5">
            <span class="w-2 h-2 rounded-full bg-red-500 animate-ping"></span>
            Cảnh Báo Sắp Hết Hàng
          </h2>
          <RouterLink to="/admin/inventory" class="text-xs text-primary font-bold hover:underline no-underline">Quản lý kho</RouterLink>
        </div>

        <div class="flex flex-col gap-3">
          <div v-for="item in lowStockItems" :key="item.id" class="flex items-center justify-between p-3 bg-black/40 border border-white/5 rounded-xl text-xs">
            <div class="flex items-center gap-3">
              <img :src="item.image" :alt="item.name" class="w-10 h-10 object-cover rounded-lg" />
              <div>
                <span class="font-bold text-white line-clamp-1 block">{{ item.name }}</span>
                <span class="text-[10px] text-neutral-400">SKU: {{ item.sku }}</span>
              </div>
            </div>
            <span class="px-2 py-1 bg-red-500/20 text-red-400 font-extrabold rounded-lg text-xs">
              Còn {{ item.stockCount }}
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
