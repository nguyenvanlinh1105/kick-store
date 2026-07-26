<script setup>
import { ref } from 'vue'
import { DEMO_BANNERS } from '@/data/demo'

const banners = ref([...DEMO_BANNERS])
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-extrabold text-white">Quản Lý Banner & Carousel Trang Chủ</h1>
        <p class="text-xs text-neutral-400 mt-1">Cấu hình thứ tự hiển thị banner slider và liên kết đích</p>
      </div>

      <button class="px-5 py-2.5 bg-primary text-black font-extrabold text-xs rounded-xl hover:bg-primary-hover cursor-pointer">
        + Tải Banner Mới
      </button>
    </div>

    <div class="flex flex-col gap-4">
      <div v-for="b in banners" :key="b.id" class="bg-neutral-900 border border-white/10 rounded-2xl p-4 flex flex-col md:flex-row items-center gap-6">
        <img :src="b.image" :alt="b.title" class="w-full md:w-48 h-28 object-cover rounded-xl shrink-0" />
        <div class="flex-1 flex flex-col gap-1">
          <span class="text-[10px] font-bold text-primary uppercase">{{ b.subtitle }}</span>
          <h3 class="text-base font-bold text-white">{{ b.title }}</h3>
          <span class="text-xs text-neutral-400">Liên kết đích: <strong class="text-white font-mono">{{ b.ctaLink }}</strong></span>
        </div>
        <button class="px-3 py-1.5 bg-red-500/20 text-red-400 text-xs font-bold rounded-lg hover:bg-red-500/30 cursor-pointer">
          Xóa
        </button>
      </div>
    </div>
  </div>
</template>
