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
    class="shrink-0 border-r border-hairline-light bg-canvas-light"
    :class="isMobile ? 'w-full border-b border-r-0' : 'w-64 min-h-[calc(100vh-3rem)]'"
  >
    <div class="px-lg py-lg">
      <p class="text-caption-sm uppercase tracking-wider text-mute-light">{{ title }}</p>
      <nav
        class="mt-md flex gap-sm"
        :class="isMobile ? 'overflow-x-auto pb-sm' : 'flex-col'"
      >
        <RouterLink
          v-for="link in links"
          :key="link.to"
          :to="link.to"
          class="whitespace-nowrap rounded-md px-md py-sm text-body-sm"
          :class="
            isActive(link.to)
              ? 'bg-surface-card text-ink font-semibold'
              : 'text-mute-light active:bg-surface-soft'
          "
        >
          {{ link.label }}
        </RouterLink>
      </nav>
    </div>
  </aside>
</template>
