<script setup>
import { RouterLink, useRoute } from 'vue-router'
import { useResponsive } from '@/composables/useResponsive'

defineProps({
  title: { type: String, required: true },
  links: {
    type: Array,
    required: true,
  },
})

const route = useRoute()
const { isMobile } = useResponsive()

function isActive(to) {
  return route.path === to || route.path.startsWith(`${to}/`)
}
</script>

<template>
  <aside
    class="shrink-0 border-r border-slate-200 bg-white"
    :class="isMobile ? 'w-full border-b border-r-0' : 'w-64 min-h-[calc(100vh-3.5rem)]'"
  >
    <div class="p-6">
      <p class="text-[11px] font-extrabold uppercase tracking-widest text-amber-600 mb-4">{{ title }}</p>
      <nav
        class="flex gap-1.5"
        :class="isMobile ? 'overflow-x-auto pb-2' : 'flex-col'"
      >
        <RouterLink
          v-for="link in links"
          :key="link.to"
          :to="link.to"
          class="whitespace-nowrap rounded-xl px-4 py-3 text-xs font-bold transition-all no-underline flex items-center justify-between"
          :class="[
            isActive(link.to)
              ? 'bg-slate-900 text-white font-extrabold shadow-md'
              : 'text-slate-700 hover:bg-slate-100 hover:text-slate-900'
          ]"
        >
          <span>{{ link.label }}</span>
        </RouterLink>
      </nav>
    </div>
  </aside>
</template>
