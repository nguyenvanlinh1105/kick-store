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
      <h1 class="text-2xl font-extrabold text-white">Thông Báo Của Tôi</h1>
      <button
        type="button"
        class="text-xs text-primary font-bold hover:underline cursor-pointer"
        @click="markAllAsRead"
      >
        Đánh dấu tất cả đã đọc
      </button>
    </div>

    <div class="flex flex-col gap-3">
      <div
        v-for="n in list"
        :key="n.id"
        class="p-4 bg-neutral-900 border rounded-2xl flex flex-col gap-1 transition-all"
        :class="[n.read ? 'border-white/5 text-neutral-400' : 'border-primary/40 bg-primary/5 text-white']"
      >
        <div class="flex items-center justify-between">
          <span class="text-xs font-bold text-primary uppercase tracking-wider">{{ n.type }}</span>
          <span class="text-[10px] text-neutral-500">{{ n.createdAt }}</span>
        </div>
        <h3 class="text-sm font-bold text-white">{{ n.title }}</h3>
        <p class="text-xs leading-relaxed text-neutral-300">{{ n.body }}</p>
      </div>
    </div>
  </div>
</template>
