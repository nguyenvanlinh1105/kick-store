<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'

const props = defineProps({
  modelValue: {
    type: [String, Number],
    default: '',
  },
  options: {
    type: Array, // Array of { value, label } or strings
    default: () => [],
  },
  label: {
    type: String,
    default: '',
  },
  placeholder: {
    type: String,
    default: 'Chọn tùy chọn...',
  },
  error: {
    type: String,
    default: '',
  },
  disabled: {
    type: Boolean,
    default: false,
  },
  dark: {
    type: Boolean,
    default: false,
  },
})

const emit = defineEmits(['update:modelValue', 'change'])

const isOpen = ref(false)
const selectRef = ref(null)

const formattedOptions = computed(() => {
  return props.options.map((opt) => {
    if (typeof opt === 'object' && opt !== null) {
      return { value: opt.value, label: opt.label }
    }
    return { value: opt, label: opt }
  })
})

const selectedLabel = computed(() => {
  const found = formattedOptions.value.find((o) => o.value === props.modelValue)
  return found ? found.label : props.placeholder
})

function selectOption(val) {
  emit('update:modelValue', val)
  emit('change', val)
  isOpen.value = false
}

function handleClickOutside(e) {
  if (selectRef.value && !selectRef.value.contains(e.target)) {
    isOpen.value = false
  }
}

onMounted(() => window.addEventListener('click', handleClickOutside))
onUnmounted(() => window.removeEventListener('click', handleClickOutside))
</script>

<template>
  <div ref="selectRef" class="flex flex-col gap-1.5 w-full relative">
    <label v-if="label" class="text-xs font-bold" :class="[dark ? 'text-neutral-300' : 'text-slate-700']">
      {{ label }}
    </label>

    <div class="relative">
      <!-- Trigger Button -->
      <button
        type="button"
        :disabled="disabled"
        class="w-full h-10 px-3.5 text-xs font-bold rounded-xl border flex items-center justify-between transition-all cursor-pointer shadow-sm outline-none disabled:opacity-50 disabled:cursor-not-allowed"
        :class="[
          dark
            ? 'bg-neutral-900 border-white/15 text-white hover:border-amber-500'
            : 'bg-white border-slate-200 text-slate-900 hover:border-amber-600',
          isOpen && (dark ? 'border-amber-500 ring-2 ring-amber-500/20' : 'border-amber-600 ring-2 ring-amber-600/20'),
          error && 'border-red-500',
        ]"
        @click="isOpen = !isOpen"
      >
        <span class="truncate" :class="[!modelValue && 'text-slate-400 font-normal']">
          {{ selectedLabel }}
        </span>
        <svg
          class="w-4 h-4 transition-transform duration-200 shrink-0"
          :class="[isOpen && 'rotate-180', dark ? 'text-neutral-400' : 'text-slate-500']"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2.5"
          stroke-linecap="round"
          stroke-linejoin="round"
        >
          <polyline points="6 9 12 15 18 9" />
        </svg>
      </button>

      <!-- CUSTOM DROPDOWN POPOVER CARD WITH ROUNDED-2XL CORNERS & NO SCROLLBAR -->
      <Transition
        enter-active-class="transition duration-150 ease-out"
        enter-from-class="opacity-0 scale-95 -translate-y-1"
        enter-to-class="opacity-100 scale-100 translate-y-0"
        leave-active-class="transition duration-100 ease-in"
        leave-from-class="opacity-100 scale-100 translate-y-0"
        leave-to-class="opacity-0 scale-95 -translate-y-1"
      >
        <div
          v-if="isOpen"
          class="absolute top-full left-0 right-0 mt-1.5 z-50 rounded-2xl border shadow-2xl p-1.5 max-h-60 overflow-y-auto no-scrollbar"
          :class="[
            dark
              ? 'bg-neutral-900 border-white/15 text-white'
              : 'bg-white border-slate-200 text-slate-900'
          ]"
        >
          <div
            v-for="opt in formattedOptions"
            :key="opt.value"
            class="px-3.5 py-2.5 text-xs font-bold rounded-xl transition-all cursor-pointer flex items-center justify-between my-0.5"
            :class="[
              opt.value === modelValue
                ? (dark ? 'bg-amber-500/20 text-amber-400 font-extrabold' : 'bg-slate-900 text-white font-extrabold')
                : (dark ? 'hover:bg-white/10 text-neutral-200' : 'hover:bg-amber-500/10 hover:text-amber-700 text-slate-700')
            ]"
            @click="selectOption(opt.value)"
          >
            <span>{{ opt.label }}</span>
            <span v-if="opt.value === modelValue" class="text-xs">✓</span>
          </div>
        </div>
      </Transition>
    </div>

    <span v-if="error" class="text-[11px] text-red-500 font-medium">{{ error }}</span>
  </div>
</template>
