<script setup>
import { ref } from 'vue'

const toasts = ref([])

function addToast({ message, type = 'info', duration = 3000 }) {
  const id = Date.now() + Math.random()
  toasts.value.push({ id, message, type })
  if (duration > 0) {
    setTimeout(() => {
      removeToast(id)
    }, duration)
  }
}

function removeToast(id) {
  toasts.value = toasts.value.filter((t) => t.id !== id)
}

defineExpose({ addToast, removeToast })
</script>

<template>
  <div class="fixed top-5 right-5 z-50 flex flex-col gap-2.5 max-w-sm w-full pointer-events-none px-4">
    <TransitionGroup name="toast">
      <div
        v-for="t in toasts"
        :key="t.id"
        class="pointer-events-auto p-4 rounded-xl shadow-xl border flex items-center justify-between gap-3 text-xs font-medium text-white transition-all duration-300"
        :class="[
          t.type === 'success' && 'bg-emerald-950 border-emerald-500/40 text-emerald-200',
          t.type === 'error' && 'bg-red-950 border-red-500/40 text-red-200',
          t.type === 'warning' && 'bg-amber-950 border-amber-500/40 text-amber-200',
          t.type === 'info' && 'bg-neutral-900 border-neutral-700 text-neutral-200',
        ]"
      >
        <span>{{ t.message }}</span>
        <button
          type="button"
          class="text-neutral-400 hover:text-white cursor-pointer text-sm"
          @click="removeToast(t.id)"
        >
          ✕
        </button>
      </div>
    </TransitionGroup>
  </div>
</template>

<style scoped>
.toast-enter-active,
.toast-leave-active {
  transition: all 0.3s ease;
}
.toast-enter-from {
  opacity: 0;
  transform: translateY(-20px) scale(0.95);
}
.toast-leave-to {
  opacity: 0;
  transform: translateX(100px);
}
</style>
