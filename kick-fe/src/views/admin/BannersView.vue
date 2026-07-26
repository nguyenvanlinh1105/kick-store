<script setup>
import { ref } from 'vue'
import { DEMO_BANNERS } from '@/data/demo'

const banners = ref([...DEMO_BANNERS])
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-extrabold text-slate-900">Quản Lý Banner & Carousel Trang Chủ</h1>
        <p class="text-xs text-slate-500 mt-1 font-medium">Cấu hình thứ tự hiển thị banner slider và liên kết đích</p>
      </div>

      <button class="px-5 py-2.5 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 transition-all cursor-pointer shadow">
        + Tải Banner Mới
      </button>
    </div>

    <!-- PURE WHITE LIGHT BANNERS CARDS -->
    <div class="flex flex-col gap-4">
      <div v-for="b in banners" :key="b.id" class="bg-white border border-slate-200 rounded-3xl p-5 flex flex-col md:flex-row items-center gap-6 shadow-sm hover:shadow-md transition-all">
        <img :src="b.image" :alt="b.title" class="w-full md:w-48 h-28 object-cover rounded-2xl shrink-0 border border-slate-200" />
        <div class="flex-1 flex flex-col gap-1.5">
          <span class="text-xs font-extrabold text-amber-600 uppercase">{{ b.subtitle }}</span>
          <h3 class="text-base font-extrabold text-slate-900">{{ b.title }}</h3>
          <span class="text-xs text-slate-500 font-medium">Liên kết đích: <strong class="text-slate-900 font-mono font-bold">{{ b.ctaLink }}</strong></span>
        </div>
        <button class="px-4 py-2 bg-red-50 text-red-600 text-xs font-extrabold rounded-xl border border-red-200 hover:bg-red-600 hover:text-white transition-colors cursor-pointer">
          Xóa
        </button>
      </div>
    </div>
  </div>
</template>
