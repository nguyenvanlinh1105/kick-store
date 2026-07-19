<script setup>
defineProps({
  title: { type: String, required: true },
  description: { type: String, default: '' },
})
</script>

<template>
  <div class="flex flex-col items-center justify-center gap-md py-xxl text-center">
    <div class="flex h-16 w-16 items-center justify-center rounded-full bg-surface-soft text-mute-light">
      <slot name="icon" />
    </div>
    <h2 class="text-heading-xl text-ink">{{ title }}</h2>
    <p v-if="description" class="max-w-md text-body-sm text-body-light">{{ description }}</p>
    <div v-if="$slots.action" class="pt-sm">
      <slot name="action" />
    </div>
  </div>
</template>
