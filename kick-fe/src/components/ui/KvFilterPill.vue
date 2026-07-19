<script setup>
defineProps({
  active: Boolean,
})

defineEmits(['click'])
</script>

<template>
  <button
    type="button"
    class="kv-filter-pill"
    :class="{ 'kv-filter-pill--active': active }"
    @click="$emit('click')"
  >
    <slot />
  </button>
</template>

<style scoped>
.kv-filter-pill {
  display: inline-flex;
  align-items: center;
  height: 38px;
  padding: 0 20px;
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 1px;
  text-transform: uppercase;
  color: rgb(245 244 240 / 0.6);
  background: rgb(255 255 255 / 0.04);
  border: 1px solid rgb(255 255 255 / 0.08);
  border-radius: 9999px;
  cursor: pointer;
  transition: all 0.25s cubic-bezier(0.16, 1, 0.3, 1);
}

.kv-filter-pill:hover {
  color: #f5f4f0;
  border-color: rgb(255 255 255 / 0.2);
  background: rgb(255 255 255 / 0.08);
}

.kv-filter-pill--active {
  color: #0a0a0a;
  background: #c8a96e;
  border-color: #c8a96e;
  box-shadow: 0 4px 12px rgb(200 169 110 / 0.25);
}
</style>
