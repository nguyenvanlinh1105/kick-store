<script setup>
import { ref } from 'vue'
import { DEMO_REVIEWS } from '@/data/demo'

const reviews = ref([...DEMO_REVIEWS.map(r => ({ ...r, status: 'PUBLISHED' }))])

function toggleStatus(rev) {
  rev.status = rev.status === 'PUBLISHED' ? 'HIDDEN' : 'PUBLISHED'
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-extrabold text-white">Quản Lý Đánh Giá & Bình Luận</h1>
        <p class="text-xs text-neutral-400 mt-1">Duyệt hoặc ẩn đánh giá vi phạm quy chuẩn cộng đồng</p>
      </div>
    </div>

    <div class="flex flex-col gap-4">
      <div v-for="rev in reviews" :key="rev.id" class="bg-neutral-900 border border-white/10 rounded-2xl p-5 flex flex-col gap-3">
        <div class="flex items-center justify-between">
          <div class="flex items-center gap-3">
            <img :src="rev.avatar" :alt="rev.user" class="w-8 h-8 rounded-full" />
            <div>
              <span class="text-xs font-bold text-white block">{{ rev.user }}</span>
              <span class="text-[10px] text-neutral-400">{{ rev.date }} · {{ rev.variant }}</span>
            </div>
          </div>

          <div class="flex items-center gap-4">
            <span class="text-xs font-bold text-primary">★ {{ rev.rating }}.0</span>
            <button
              @click="toggleStatus(rev)"
              class="px-3 py-1 text-xs font-bold rounded-lg cursor-pointer transition-all"
              :class="[rev.status === 'PUBLISHED' ? 'bg-red-500/20 text-red-400' : 'bg-emerald-500/20 text-emerald-400']"
            >
              {{ rev.status === 'PUBLISHED' ? 'Ẩn Đánh Giá' : 'Hiện Đánh Giá' }}
            </button>
          </div>
        </div>

        <p class="text-xs text-neutral-300 leading-relaxed bg-black/40 p-3 rounded-xl border border-white/5">{{ rev.content }}</p>
      </div>
    </div>
  </div>
</template>
