<script setup>
import { ref } from 'vue'
import notificationsJson from '@/data/json/notifications.json'

const list = ref([...notificationsJson])

function markAllAsRead() {
  list.value.forEach((n) => (n.read = true))
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <h1 class="text-2xl font-extrabold text-slate-900">Thông Báo Của Tôi</h1>
      <button
        type="button"
        class="text-xs text-amber-600 font-extrabold hover:underline cursor-pointer"
        @click="markAllAsRead"
      >
        Đánh dấu tất cả đã đọc
      </button>
    </div>

    <!-- PURE WHITE LIGHT NOTIFICATION CARDS -->
    <div class="flex flex-col gap-3">
      <div
        v-for="n in list"
        :key="n.id"
        class="p-5 bg-white border rounded-3xl flex flex-col gap-1.5 transition-all shadow-sm text-slate-900"
        :class="[n.read ? 'border-slate-200 opacity-80' : 'border-amber-600 bg-amber-500/5']"
      >
        <div class="flex items-center justify-between">
          <span class="text-xs font-extrabold text-amber-600 uppercase tracking-wider">{{ n.type }}</span>
          <span class="text-[10px] text-slate-400 font-bold">{{ n.createdAt }}</span>
        </div>
        <h3 class="text-sm font-extrabold text-slate-900">{{ n.title }}</h3>
        <p class="text-xs leading-relaxed text-slate-600 font-medium">{{ n.body }}</p>
      </div>
    </div>
  </div>
</template>
