<script setup>
import { RouterLink } from 'vue-router'
</script>

<template>
  <div class="min-h-screen bg-black text-white flex flex-col items-center justify-center text-center p-6">
    <span class="text-8xl font-extrabold text-red-500 font-display tracking-widest mb-4">500</span>
    <h1 class="text-3xl font-extrabold mb-2">Lỗi Máy Chủ Nội Bộ</h1>
    <p class="text-xs text-neutral-400 max-w-md mb-8">
      Máy chủ gặp sự cố ngoài dự kiến. Kỹ thuật viên của KickVerse đang tiến hành xử lý khắc phục.
    </p>
    <button
      @click="$router.go(0)"
      class="px-8 py-3.5 bg-primary text-black font-extrabold text-xs uppercase tracking-wider rounded-xl hover:bg-primary-hover shadow-lg cursor-pointer"
    >
      🔄 Tải Lại Trang
    </button>
  </div>
</template>
