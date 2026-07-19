<script setup>
defineProps({
  title: { type: String, required: true },
  description: { type: String, default: '' },
  actions: Boolean,
})
</script>

<template>
  <div class="mb-lg flex flex-wrap items-end justify-between gap-md">
    <div>
      <h1 class="text-display-md text-ink">{{ title }}</h1>
      <p v-if="description" class="mt-xs max-w-xl text-body-sm text-body-light">
        {{ description }}
      </p>
    </div>
    <div v-if="$slots.default" class="flex flex-wrap items-center gap-sm">
      <slot />
    </div>
  </div>
</template>
