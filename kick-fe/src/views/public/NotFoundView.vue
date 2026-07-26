<script setup>
import { RouterLink } from 'vue-router'
</script>

<template>
  <div class="min-h-screen bg-black text-white flex flex-col items-center justify-center text-center p-6">
    <span class="text-8xl font-extrabold text-primary font-display tracking-widest animate-pulse mb-4">404</span>
    <h1 class="text-3xl font-extrabold mb-2">Trang Không Tồn Tại</h1>
    <p class="text-xs text-neutral-400 max-w-md mb-8">
      Đường dẫn bạn truy cập có thể đã bị xóa, đổi tên hoặc tạm thời không khả dụng trên hệ thống KickVerse.
    </p>
    <RouterLink
      to="/"
      class="px-8 py-3.5 bg-primary text-black font-extrabold text-xs uppercase tracking-wider rounded-xl hover:bg-primary-hover no-underline shadow-lg"
    >
      🏠 Quay Về Trang Chủ
    </RouterLink>
  </div>
</template>
