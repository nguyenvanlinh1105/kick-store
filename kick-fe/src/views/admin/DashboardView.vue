<script setup>
import { ref } from 'vue'
import { RouterLink } from 'vue-router'
import productsJson from '@/data/json/products.json'
import KvSelect from '@/components/ui/KvSelect.vue'

const selectedPeriod = ref('month')

const periodOptions = [
  { value: 'today', label: 'Hôm nay' },
  { value: 'week', label: 'Tuần này' },
  { value: 'month', label: 'Tháng này' },
  { value: 'year', label: 'Năm 2026' },
]

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
    <div class="flex flex-col md:flex-row md:items-center justify-between gap-4">
      <div>
        <h1 class="text-2xl font-extrabold text-slate-900">Báo Cáo Tổng Quan Dashboard</h1>
        <p class="text-xs text-slate-500 mt-1 font-medium">Thống kê hiệu suất kinh doanh toàn hệ thống KickVerse</p>
      </div>

      <div class="w-48">
        <KvSelect v-model="selectedPeriod" :options="periodOptions" :dark="false" />
      </div>
    </div>

    <!-- METRIC CARDS GRID -->
    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-5">
      <div v-for="s in stats" :key="s.title" class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-2 shadow-sm hover:shadow-md transition-all">
        <span class="text-xs font-bold text-slate-500">{{ s.title }}</span>
        <span class="text-2xl font-extrabold text-slate-900">{{ s.value }}</span>
        <span class="text-xs font-extrabold" :class="[s.isUp ? 'text-emerald-600' : 'text-red-500']">
          {{ s.change }} so với kỳ trước
        </span>
      </div>
    </div>

    <!-- REVENUE CHART & LOW STOCK ALERT GRID -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
      <!-- Simulated Revenue Chart -->
      <div class="md:col-span-2 bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-4 shadow-sm">
        <div class="flex items-center justify-between border-b border-slate-100 pb-3">
          <h2 class="text-xs font-extrabold text-slate-900 uppercase tracking-wider">Biểu Đồ Doanh Thu Theo Tháng</h2>
          <span class="text-xs text-amber-600 font-extrabold">Đơn vị: Triệu VNĐ</span>
        </div>

        <div class="h-64 flex items-end justify-between gap-3 pt-6 px-4 border-b border-slate-100">
          <div v-for="(val, idx) in [45, 68, 92, 110, 85, 130, 148]" :key="idx" class="flex-1 flex flex-col items-center gap-2 group">
            <span class="text-[10px] font-extrabold text-amber-600 opacity-0 group-hover:opacity-100 transition-opacity">{{ val }}M</span>
            <div class="w-full bg-gradient-to-t from-slate-800 to-slate-900 rounded-t-xl transition-all group-hover:bg-amber-600" :style="{ height: `${val * 1.5}px` }"></div>
            <span class="text-[10px] text-slate-500 font-bold">T{{ idx + 1 }}</span>
          </div>
        </div>
      </div>

      <!-- Low Stock Warnings Widget -->
      <div class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-4 shadow-sm">
        <div class="flex items-center justify-between border-b border-slate-100 pb-3">
          <h2 class="text-xs font-extrabold text-slate-900 uppercase tracking-wider flex items-center gap-1.5">
            <span class="w-2.5 h-2.5 rounded-full bg-red-500 animate-ping"></span>
            Cảnh Báo Sắp Hết Hàng
          </h2>
          <RouterLink to="/admin/inventory" class="text-xs text-amber-600 font-extrabold hover:underline no-underline">Quản lý kho</RouterLink>
        </div>

        <div class="flex flex-col gap-3">
          <div v-for="item in lowStockItems" :key="item.id" class="flex items-center justify-between p-3 bg-slate-50 border border-slate-200 rounded-2xl text-xs">
            <div class="flex items-center gap-3">
              <img :src="item.image" :alt="item.name" class="w-10 h-10 object-cover rounded-xl border border-slate-200" />
              <div>
                <span class="font-extrabold text-slate-900 line-clamp-1 block">{{ item.name }}</span>
                <span class="text-[10px] text-slate-400 font-bold">SKU: {{ item.sku }}</span>
              </div>
            </div>
            <span class="px-2.5 py-1 bg-red-500/10 text-red-600 font-extrabold rounded-lg text-xs">
              Còn {{ item.stockCount }}
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
