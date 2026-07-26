<script setup>
import { computed } from 'vue'

const props = defineProps({
  currentPage: {
    type: Number,
    default: 1,
  },
  totalPages: {
    type: Number,
    default: 1,
  },
  dark: {
    type: Boolean,
    default: false,
  },
})

const emit = defineEmits(['update:currentPage', 'page-change'])

const pages = computed(() => {
  const range = []
  const total = props.totalPages
  const current = props.currentPage
  
  if (total <= 7) {
    for (let i = 1; i <= total; i++) range.push(i)
  } else {
    if (current <= 4) {
      for (let i = 1; i <= 5; i++) range.push(i)
      range.push('...')
      range.push(total)
    } else if (current >= total - 3) {
      range.push(1)
      range.push('...')
      for (let i = total - 4; i <= total; i++) range.push(i)
    } else {
      range.push(1)
      range.push('...')
      for (let i = current - 1; i <= current + 1; i++) range.push(i)
      range.push('...')
      range.push(total)
    }
  }
  return range
})

function changePage(page) {
  if (typeof page !== 'number' || page === props.currentPage || page < 1 || page > props.totalPages) return
  emit('update:currentPage', page)
  emit('page-change', page)
}
</script>

<template>
  <nav v-if="totalPages > 1" class="flex items-center justify-center gap-1.5 py-4" aria-label="Pagination">
    <!-- Previous Button -->
    <button
      type="button"
      :disabled="currentPage === 1"
      class="inline-flex items-center justify-center w-9 h-9 text-xs font-semibold rounded-lg transition-all duration-200 disabled:opacity-40 disabled:cursor-not-allowed"
      :class="[
        dark
          ? 'bg-neutral-900 border border-white/10 text-white hover:bg-neutral-800'
          : 'bg-white border border-neutral-200 text-neutral-700 hover:bg-neutral-100'
      ]"
      @click="changePage(currentPage - 1)"
      aria-label="Trang trước"
    >
      ‹
    </button>

    <!-- Page Numbers -->
    <template v-for="(p, idx) in pages" :key="idx">
      <span
        v-if="p === '...'"
        class="inline-flex items-center justify-center w-9 h-9 text-xs"
        :class="[dark ? 'text-neutral-500' : 'text-neutral-400']"
      >
        ...
      </span>
      <button
        v-else
        type="button"
        class="inline-flex items-center justify-center w-9 h-9 text-xs font-bold rounded-lg transition-all duration-200"
        :class="[
          currentPage === p
            ? 'bg-primary text-black font-extrabold shadow-sm'
            : dark
              ? 'bg-neutral-900 border border-white/10 text-neutral-300 hover:bg-neutral-800'
              : 'bg-white border border-neutral-200 text-neutral-700 hover:bg-neutral-100'
        ]"
        @click="changePage(p)"
      >
        {{ p }}
      </button>
    </template>

    <!-- Next Button -->
    <button
      type="button"
      :disabled="currentPage === totalPages"
      class="inline-flex items-center justify-center w-9 h-9 text-xs font-semibold rounded-lg transition-all duration-200 disabled:opacity-40 disabled:cursor-not-allowed"
      :class="[
        dark
          ? 'bg-neutral-900 border border-white/10 text-white hover:bg-neutral-800'
          : 'bg-white border border-neutral-200 text-neutral-700 hover:bg-neutral-100'
      ]"
      @click="changePage(currentPage + 1)"
      aria-label="Trang sau"
    >
      ›
    </button>
  </nav>
</template>
