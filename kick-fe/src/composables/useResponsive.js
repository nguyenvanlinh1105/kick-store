import { computed, onMounted, onUnmounted, ref } from 'vue'

/** KickVerse layout breakpoint — mobile < 960px, desktop ≥ 960px */
export const KV_BREAKPOINT = 960

/**
 * Responsive helpers for KickVerse UI.
 * Desktop: width ≥ 960px · Mobile: width < 960px
 */
export function useResponsive() {
  const width = ref(typeof window !== 'undefined' ? window.innerWidth : KV_BREAKPOINT)

  function update() {
    width.value = window.innerWidth
  }

  onMounted(() => {
    update()
    window.addEventListener('resize', update, { passive: true })
  })

  onUnmounted(() => {
    window.removeEventListener('resize', update)
  })

  const isDesktop = computed(() => width.value >= KV_BREAKPOINT)
  const isMobile = computed(() => width.value < KV_BREAKPOINT)
  const breakpoint = computed(() => (isDesktop.value ? 'desktop' : 'mobile'))

  return {
    width,
    isDesktop,
    isMobile,
    breakpoint,
    KV_BREAKPOINT,
  }
}
