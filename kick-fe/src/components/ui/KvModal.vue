<script setup>
import { onMounted, onUnmounted, watch } from 'vue'

const props = defineProps({
  open: Boolean,
  title: { type: String, default: '' },
  size: {
    type: String,
    default: 'md',
    validator: (v) => ['sm', 'md', 'lg'].includes(v),
  },
})

const emit = defineEmits(['close'])

function onKey(e) {
  if (e.key === 'Escape') emit('close')
}

watch(
  () => props.open,
  (v) => {
    document.body.style.overflow = v ? 'hidden' : ''
  },
)

onMounted(() => window.addEventListener('keydown', onKey))
onUnmounted(() => {
  window.removeEventListener('keydown', onKey)
  document.body.style.overflow = ''
})
</script>

<template>
  <Teleport to="body">
    <div
      v-if="open"
      class="fixed inset-0 z-50 flex items-end justify-center bg-black/50 kv:items-center kv:p-lg"
      @click.self="emit('close')"
    >
      <div
        class="max-h-[90vh] w-full max-w-2xl overflow-y-auto rounded-t-lg bg-canvas-light kv:rounded-lg"
        role="dialog"
        aria-modal="true"
      >
        <header
          v-if="title || $slots.header"
          class="flex items-center justify-between border-b border-hairline-light px-lg py-md"
        >
          <slot name="header">
            <h2 class="text-heading-lg">{{ title }}</h2>
          </slot>
          <button
            type="button"
            class="flex h-11 w-11 items-center justify-center rounded-full text-mute-light active:bg-surface-soft"
            aria-label="Đóng"
            @click="emit('close')"
          >
            ✕
          </button>
        </header>
        <div class="p-lg">
          <slot />
        </div>
        <footer v-if="$slots.footer" class="border-t border-hairline-light px-lg py-md">
          <slot name="footer" />
        </footer>
      </div>
    </div>
  </Teleport>
</template>
