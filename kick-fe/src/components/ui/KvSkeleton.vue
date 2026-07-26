<script setup>
defineProps({
  type: {
    type: String,
    default: 'text', // 'text', 'avatar', 'image', 'card', 'button'
  },
  width: {
    type: String,
    default: undefined,
  },
  height: {
    type: String,
    default: undefined,
  },
  count: {
    type: Number,
    default: 1,
  },
  dark: {
    type: Boolean,
    default: false,
  },
})
</script>

<template>
  <div class="flex flex-col gap-2.5 w-full">
    <div
      v-for="i in count"
      :key="i"
      class="animate-pulse rounded transition-colors"
      :class="[
        dark ? 'bg-neutral-800/80' : 'bg-neutral-200/80',
        type === 'text' && 'h-4 w-full rounded',
        type === 'avatar' && 'h-10 w-10 rounded-full shrink-0',
        type === 'image' && 'aspect-square w-full rounded-xl',
        type === 'button' && 'h-10 w-28 rounded-md',
        type === 'card' && 'h-64 w-full rounded-xl',
      ]"
      :style="{
        width: width ? width : undefined,
        height: height ? height : undefined,
      }"
    ></div>
  </div>
</template>
