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
        <h1 class="text-2xl font-extrabold text-slate-900">Quản Lý Đánh Giá & Bình Luận</h1>
        <p class="text-xs text-slate-500 mt-1 font-medium">Duyệt hoặc ẩn đánh giá vi phạm quy chuẩn cộng đồng</p>
      </div>
    </div>

    <!-- PURE WHITE LIGHT REVIEWS CARDS -->
    <div class="flex flex-col gap-4">
      <div v-for="rev in reviews" :key="rev.id" class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-3 shadow-sm hover:shadow-md transition-all">
        <div class="flex items-center justify-between">
          <div class="flex items-center gap-3">
            <img :src="rev.avatar" :alt="rev.user" class="w-9 h-9 rounded-full border border-slate-200" />
            <div>
              <span class="text-xs font-extrabold text-slate-900 block">{{ rev.user }}</span>
              <span class="text-[10px] text-slate-500 font-bold">{{ rev.date }} · {{ rev.variant }}</span>
            </div>
          </div>

          <div class="flex items-center gap-4">
            <span class="text-xs font-extrabold text-amber-600">★ {{ rev.rating }}.0</span>
            <button
              @click="toggleStatus(rev)"
              class="px-3.5 py-1.5 text-xs font-extrabold rounded-xl cursor-pointer transition-all border"
              :class="[rev.status === 'PUBLISHED' ? 'bg-red-50 text-red-600 border-red-200 hover:bg-red-600 hover:text-white' : 'bg-emerald-50 text-emerald-700 border-emerald-200 hover:bg-emerald-600 hover:text-white']"
            >
              {{ rev.status === 'PUBLISHED' ? 'Ẩn Đánh Giá' : 'Hiện Đánh Giá' }}
            </button>
          </div>
        </div>

        <p class="text-xs text-slate-700 leading-relaxed bg-slate-50 p-4 rounded-2xl border border-slate-200 font-medium">{{ rev.content }}</p>
      </div>
    </div>
  </div>
</template>
