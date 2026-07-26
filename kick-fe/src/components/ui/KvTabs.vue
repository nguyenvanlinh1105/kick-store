<script setup>
defineProps({
  modelValue: {
    type: [String, Number],
    required: true,
  },
  tabs: {
    type: Array, // [{ key, label, count }]
    required: true,
  },
  dark: {
    type: Boolean,
    default: false,
  },
})

defineEmits(['update:modelValue', 'change'])
</script>

<template>
  <div class="flex items-center gap-1 border-b overflow-x-auto no-scrollbar" :class="[dark ? 'border-white/10' : 'border-neutral-200']">
    <button
      v-for="tab in tabs"
      :key="tab.key"
      type="button"
      class="inline-flex items-center gap-2 px-4 py-3 text-xs font-bold whitespace-nowrap transition-all border-b-2 -mb-px cursor-pointer"
      :class="[
        modelValue === tab.key
          ? 'border-primary text-primary font-extrabold'
          : dark
            ? 'border-transparent text-neutral-400 hover:text-white'
            : 'border-transparent text-neutral-600 hover:text-neutral-900'
      ]"
      @click="$emit('update:modelValue', tab.key); $emit('change', tab.key)"
    >
      {{ tab.label }}
      <span
        v-if="tab.count !== undefined"
        class="px-1.5 py-0.5 text-[10px] rounded-full font-bold"
        :class="[
          modelValue === tab.key
            ? 'bg-primary/20 text-primary'
            : dark
              ? 'bg-neutral-800 text-neutral-400'
              : 'bg-neutral-100 text-neutral-600'
        ]"
      >
        {{ tab.count }}
      </span>
    </button>
  </div>
</template>
