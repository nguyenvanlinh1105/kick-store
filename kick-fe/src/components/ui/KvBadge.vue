<script setup>
defineProps({
  variant: {
    type: String,
    default: 'info',
    validator: (v) => ['info', 'commerce', 'dark', 'soft', 'gold'].includes(v),
  },
})
</script>

<template>
  <span
    class="inline-flex items-center h-[22px] px-2.5 text-[10px] font-bold tracking-widest uppercase border rounded"
    :class="{
      'bg-electric/15 text-electric border-electric/30': variant === 'info',
      'bg-commerce/15 text-commerce border-commerce/30': variant === 'commerce',
      'bg-white/5 text-white border-white/10': variant === 'dark',
      'bg-white/3 text-text-secondary/50 border-white/5': variant === 'soft',
      'bg-primary/15 text-primary border-primary/30': variant === 'gold',
    }"
  >
    <slot />
  </span>
</template>
