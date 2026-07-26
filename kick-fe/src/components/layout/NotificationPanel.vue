<script setup>
import { watch, onUnmounted } from 'vue'
import { storeToRefs } from 'pinia'
import { RouterLink } from 'vue-router'
import { useNotificationStore } from '@/stores/notification'
import KvEmptyState from '@/components/ui/KvEmptyState.vue'

const store = useNotificationStore()
const { items, panelOpen, unreadCount } = storeToRefs(store)

// Scroll Lock Body
watch(panelOpen, (isOpen) => {
  if (isOpen) {
    document.body.style.overflow = 'hidden'
  } else {
    document.body.style.overflow = ''
  }
})

onUnmounted(() => {
  document.body.style.overflow = ''
})
</script>

<template>
  <Teleport to="body">
    <!-- Backdrop Overlay -->
    <Transition
      enter-active-class="transition duration-300 ease-out"
      enter-from-class="opacity-0"
      enter-to-class="opacity-100"
      leave-active-class="transition duration-200 ease-in"
      leave-from-class="opacity-100"
      leave-to-class="opacity-0"
    >
      <div
        v-if="panelOpen"
        class="fixed inset-0 z-50 bg-slate-950/60 backdrop-blur-sm"
        @click="store.closePanel()"
      ></div>
    </Transition>

    <!-- Slide-Over Drawer Panel -->
    <Transition
      enter-active-class="transition duration-300 ease-out transform"
      enter-from-class="translate-x-full"
      enter-to-class="translate-x-0"
      leave-active-class="transition duration-200 ease-in transform"
      leave-from-class="translate-x-0"
      leave-to-class="translate-x-full"
    >
      <div
        v-if="panelOpen"
        class="fixed inset-y-0 right-0 z-50 w-full max-w-md bg-white border-l border-slate-200 shadow-2xl flex flex-col text-slate-900"
      >
        <!-- Panel Header -->
        <div class="p-6 border-b border-slate-200 flex items-center justify-between bg-slate-50">
          <div class="flex items-center gap-3">
            <h2 class="text-lg font-extrabold text-slate-900">Thông Báo Hệ Thống</h2>
            <span v-if="unreadCount" class="px-2.5 py-0.5 bg-amber-500/10 text-amber-700 font-extrabold text-xs rounded-full">
              {{ unreadCount }} mới
            </span>
          </div>

          <button
            type="button"
            class="w-9 h-9 flex items-center justify-center rounded-xl bg-slate-200/60 hover:bg-slate-900 hover:text-white transition-colors cursor-pointer text-sm font-bold"
            @click="store.closePanel()"
          >
            ✕
          </button>
        </div>

        <!-- Toolbar -->
        <div class="px-6 py-3 border-b border-slate-100 flex items-center justify-between bg-white text-xs">
          <span class="text-slate-500 font-medium">{{ unreadCount }} thông báo chưa đọc</span>
          <button
            type="button"
            class="text-amber-600 font-extrabold hover:underline cursor-pointer"
            @click="store.markAllRead()"
          >
            Đánh dấu tất cả đã đọc
          </button>
        </div>

        <!-- Notifications Stream -->
        <div class="flex-1 overflow-y-auto p-6 flex flex-col gap-3">
          <div v-if="items.length === 0" class="py-12">
            <KvEmptyState title="Chưa có thông báo" description="Khi có cập nhật đơn hàng hoặc Flash Sale, thông báo sẽ xuất hiện ở đây." />
          </div>

          <div
            v-for="n in items"
            :key="n.id"
            class="p-4 rounded-2xl border transition-all cursor-pointer flex flex-col gap-1 text-xs"
            :class="[n.read ? 'bg-slate-50 border-slate-200 text-slate-600' : 'bg-amber-500/5 border-amber-600 text-slate-900 shadow-sm']"
            @click="store.markRead(n.id)"
          >
            <div class="flex items-center justify-between">
              <span class="font-extrabold text-amber-600 uppercase tracking-wider text-[10px]">Cập nhật đơn hàng</span>
              <span class="text-[10px] text-slate-400 font-bold">{{ n.createdAt }}</span>
            </div>
            <strong class="text-sm font-extrabold text-slate-900">{{ n.title }}</strong>
            <p class="text-xs text-slate-600 font-medium leading-relaxed">{{ n.body }}</p>
          </div>
        </div>

        <!-- Panel Footer -->
        <div class="p-6 border-t border-slate-200 bg-white">
          <RouterLink
            to="/notifications"
            class="w-full h-12 bg-slate-900 text-white font-extrabold text-xs uppercase tracking-wider rounded-xl flex items-center justify-center hover:bg-amber-600 transition-all no-underline shadow"
            @click="store.closePanel()"
          >
            XEM TẤT CẢ THÔNG BÁO →
          </RouterLink>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>
