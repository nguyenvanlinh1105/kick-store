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
    class="kv-badge"
    :class="{
      'kv-badge--info': variant === 'info',
      'kv-badge--commerce': variant === 'commerce',
      'kv-badge--dark': variant === 'dark',
      'kv-badge--soft': variant === 'soft',
      'kv-badge--gold': variant === 'gold',
    }"
  >
    <slot />
  </span>
</template>

<style scoped>
.kv-badge {
  display: inline-flex;
  align-items: center;
  height: 22px;
  padding: 0 10px;
  font-size: 10px;
  font-weight: 700;
  letter-spacing: 1px;
  text-transform: uppercase;
  border-radius: 4px;
}

.kv-badge--info {
  background: rgb(108 99 255 / 0.15);
  color: #6c63ff;
  border: 1px solid rgb(108 99 255 / 0.3);
}

.kv-badge--commerce {
  background: rgb(239 68 68 / 0.15);
  color: #ef4444;
  border: 1px solid rgb(239 68 68 / 0.3);
}

.kv-badge--dark {
  background: rgb(255 255 255 / 0.05);
  color: #f5f4f0;
  border: 1px solid rgb(255 255 255 / 0.1);
}

.kv-badge--soft {
  background: rgb(255 255 255 / 0.03);
  color: rgb(245 244 240 / 0.5);
  border: 1px solid rgb(255 255 255 / 0.05);
}

.kv-badge--gold {
  background: rgb(200 169 110 / 0.15);
  color: #c8a96e;
  border: 1px solid rgb(200 169 110 / 0.3);
}
</style>
