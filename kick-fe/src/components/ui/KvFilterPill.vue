<script setup>
defineProps({
  active: Boolean,
})

defineEmits(['click'])
</script>

<template>
  <button
    type="button"
    class="inline-flex items-center h-[38px] px-5 text-xs font-bold tracking-wider uppercase rounded-full cursor-pointer transition-all duration-200 border"
    :class="active 
      ? 'text-black bg-primary border-primary shadow-[0_4px_12px_rgb(200_169_110/0.25)]' 
      : 'text-text-secondary/70 bg-white/4 border-white/8 hover:text-white hover:border-white/20 hover:bg-white/8'"
    @click="$emit('click')"
  >
    <slot />
  </button>
</template>
