<script setup>
defineProps({
  name: { type: String, default: '' },
  src: { type: String, default: '' },
  size: { type: String, default: 'md' },
})

const sizeClass = {
  sm: 'h-8 w-8 text-caption-sm',
  md: 'h-10 w-10 text-caption-md',
  lg: 'h-12 w-12 text-body-sm',
}
</script>

<template>
  <div
    class="inline-flex shrink-0 items-center justify-center overflow-hidden rounded-full bg-primary text-on-primary font-semibold"
    :class="sizeClass[size] || sizeClass.md"
  >
    <img v-if="src" :src="src" :alt="name" class="h-full w-full object-cover" />
    <span v-else>{{ (name || '?').charAt(0).toUpperCase() }}</span>
  </div>
</template>
