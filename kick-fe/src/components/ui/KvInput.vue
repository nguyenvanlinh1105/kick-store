<script setup>
defineProps({
  modelValue: { type: [String, Number], default: '' },
  type: { type: String, default: 'text' },
  label: { type: String, default: '' },
  placeholder: { type: String, default: '' },
  error: { type: String, default: '' },
  hint: { type: String, default: '' },
  disabled: Boolean,
  required: Boolean,
  id: { type: String, default: undefined },
  autocomplete: { type: String, default: undefined },
})

const emit = defineEmits(['update:modelValue'])
</script>

<template>
  <label class="flex flex-col gap-xs w-full">
    <span v-if="label" class="text-caption-md text-mute-light">
      {{ label }}
      <span v-if="required" class="text-warning">*</span>
    </span>
    <input
      :id="id"
      :type="type"
      :value="modelValue"
      :placeholder="placeholder"
      :disabled="disabled"
      :required="required"
      :autocomplete="autocomplete"
      class="h-12 w-full rounded-sm border bg-canvas-light px-md text-body-md text-ink outline-none placeholder:text-ash-light disabled:bg-surface-soft disabled:text-ash-light"
      :class="
        error
          ? 'border-warning focus:border-warning focus:border-2'
          : 'border-ash-light focus:border-2 focus:border-primary'
      "
      @input="emit('update:modelValue', $event.target.value)"
    />
    <span v-if="error" class="text-caption-sm text-warning">{{ error }}</span>
    <span v-else-if="hint" class="text-caption-sm text-mute-light">{{ hint }}</span>
  </label>
</template>
