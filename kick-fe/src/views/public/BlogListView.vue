<script setup>
import { ref, computed } from 'vue'
import { RouterLink } from 'vue-router'
import { DEMO_BLOGS } from '@/data/demo'

const activeCategory = ref('ALL')

const filteredBlogs = computed(() => {
  if (activeCategory.value === 'ALL') return DEMO_BLOGS
  return DEMO_BLOGS.filter((b) => b.category === activeCategory.value)
})
</script>

<template>
  <div class="bg-surface-0 min-h-screen py-12 text-white">
    <div class="kv-container">
      <div class="text-center max-w-xl mx-auto mb-10">
        <span class="text-xs font-bold text-primary uppercase tracking-widest">KickVerse Magazine</span>
        <h1 class="text-3xl font-extrabold mt-1">Tin Tức Sneaker & Mẹo Phối Đồ</h1>
      </div>

      <!-- Categories Filter -->
      <div class="flex items-center justify-center gap-2 mb-10">
        <button
          v-for="cat in ['ALL', 'Tư vấn phối đồ', 'Mẹo bảo quản', 'Tin tức']"
          :key="cat"
          type="button"
          class="px-4 py-2 text-xs font-bold rounded-xl border transition-all cursor-pointer"
          :class="[activeCategory === cat ? 'bg-primary text-black border-primary font-extrabold' : 'bg-neutral-900 border-white/10 text-neutral-300 hover:border-white/30']"
          @click="activeCategory = cat"
        >
          {{ cat === 'ALL' ? 'Tất cả bài viết' : cat }}
        </button>
      </div>

      <!-- Blog Grid -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
        <article
          v-for="b in filteredBlogs"
          :key="b.id"
          class="bg-neutral-900 border border-white/10 rounded-2xl overflow-hidden flex flex-col hover:border-primary/40 transition-all"
        >
          <img :src="b.image" :alt="b.title" class="w-full h-52 object-cover" />
          <div class="p-6 flex flex-col flex-1">
            <div class="flex items-center justify-between text-[11px] text-neutral-400 mb-2">
              <span class="text-primary font-bold">{{ b.category }}</span>
              <span>{{ b.date }}</span>
            </div>
            <h2 class="text-base font-bold text-white mb-2 leading-snug">{{ b.title }}</h2>
            <p class="text-xs text-neutral-400 leading-relaxed line-clamp-3 mb-6">{{ b.summary }}</p>
            <RouterLink :to="`/blog/${b.slug}`" class="mt-auto text-xs font-bold text-primary no-underline hover:underline">
              Đọc chi tiết bài viết →
            </RouterLink>
          </div>
        </article>
      </div>
    </div>
  </div>
</template>
