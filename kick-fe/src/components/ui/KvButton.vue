<script setup>
import { RouterLink } from 'vue-router'
import { computed } from 'vue'

const props = defineProps({
  variant: {
    type: String,
    default: 'primary',
    validator: (v) =>
      ['primary', 'commerce', 'secondary', 'secondary-dark', 'ghost', 'danger'].includes(v),
  },
  size: {
    type: String,
    default: 'lg',
    validator: (v) => ['lg', 'md', 'sm'].includes(v),
  },
  type: { type: String, default: 'button' },
  disabled: Boolean,
  block: Boolean,
  to: { type: [String, Object], default: null },
})

const tag = computed(() => (props.to ? RouterLink : 'button'))
</script>

<template>
  <component
    :is="tag"
    :to="to || undefined"
    :type="to ? undefined : type"
    :disabled="disabled"
    class="inline-flex items-center justify-center gap-2 rounded-full text-button-lg transition-colors select-none disabled:pointer-events-none disabled:bg-surface-soft disabled:text-ash-light"
    :class="[
      block && 'w-full',
      size === 'lg' && 'h-12 px-7',
      size === 'md' && 'h-10 px-5 text-button-md',
      size === 'sm' && 'h-9 px-4 text-button-md',
      !disabled &&
        variant === 'primary' &&
        'bg-primary text-on-primary active:bg-primary-pressed',
      !disabled &&
        variant === 'commerce' &&
        'bg-commerce text-on-commerce active:bg-commerce-pressed',
      !disabled &&
        variant === 'secondary' &&
        'bg-transparent text-ink border border-ash-light active:bg-surface-soft',
      !disabled &&
        variant === 'secondary-dark' &&
        'bg-transparent text-on-dark border border-ash-dark active:bg-white/10',
      !disabled && variant === 'ghost' && 'bg-transparent text-ink active:bg-surface-soft',
      !disabled && variant === 'danger' && 'bg-warning text-on-primary active:opacity-90',
    ]"
  >
    <slot />
  </component>
</template>
