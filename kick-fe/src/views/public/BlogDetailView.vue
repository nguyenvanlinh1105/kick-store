<script setup>
import { computed } from 'vue'
import { useRoute, RouterLink } from 'vue-router'
import { DEMO_BLOGS } from '@/data/demo'

const route = useRoute()
const blog = computed(() => DEMO_BLOGS.find((b) => b.slug === route.params.slug) || DEMO_BLOGS[0])
</script>

<template>
  <div class="bg-surface-0 min-h-screen py-12 text-white">
    <div class="kv-container max-w-3xl">
      <RouterLink to="/blog" class="text-xs text-primary font-bold hover:underline no-underline mb-4 block">← Quay lại danh sách blog</RouterLink>
      
      <span class="text-xs font-bold text-primary uppercase tracking-widest">{{ blog.category }} · {{ blog.date }}</span>
      <h1 class="text-3xl font-extrabold text-white mt-2 mb-4 leading-tight">{{ blog.title }}</h1>
      <p class="text-xs text-neutral-400 mb-6">Tác giả: <strong class="text-white">{{ blog.author }}</strong></p>

      <img :src="blog.image" :alt="blog.title" class="w-full h-80 object-cover rounded-2xl mb-8 border border-white/10" />

      <div class="bg-neutral-900 border border-white/10 rounded-2xl p-8 text-xs text-neutral-300 leading-relaxed flex flex-col gap-4">
        <p class="text-sm font-semibold text-white leading-relaxed">{{ blog.summary }}</p>
        <p>Sneaker đã từ lâu trở thành mảnh ghép không thể thiếu của tín đồ thời trang đường phố. Dù bạn theo đuổi phong cách tối giản (Minimalism) hay cá tính phá cách (Cyberpunk/Techwear), việc sở hữu những đôi sneaker chuẩn form sẽ giúp bạn nâng tầm trang phục tức thì.</p>
        <h3 class="text-base font-bold text-white mt-2">Lưu ý khi phối màu sắc với Sneaker</h3>
        <p>Nguyên tắc phối đồ kinh điển 60-30-10 luôn mang lại sự hài hòa thị giác. Hãy chọn đôi giày làm điểm nhấn chính (accent color) nếu trang phục của bạn có tone màu trung tính.</p>
      </div>
    </div>
  </div>
</template>
