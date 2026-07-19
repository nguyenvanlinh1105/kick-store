<script setup>
defineProps({
  variant: {
    type: String,
    default: 'light',
    validator: (v) => ['light', 'dark', 'feature', 'plain'].includes(v),
  },
  padding: {
    type: String,
    default: 'lg',
  },
})
</script>

<template>
  <div
    class="rounded-md"
    :class="[
      variant === 'light' && 'bg-surface-card text-ink',
      variant === 'dark' && 'bg-surface-dark-card text-on-dark',
      variant === 'feature' && 'bg-canvas-light text-ink',
      variant === 'plain' && 'bg-transparent',
      padding === 'lg' && 'p-lg',
      padding === 'xl' && 'p-xl',
      padding === 'md' && 'p-md',
      padding === 'none' && 'p-0',
    ]"
  >
    <slot />
  </div>
</template>
