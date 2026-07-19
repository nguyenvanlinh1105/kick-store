import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import { DEMO_NOTIFICATIONS } from '@/data/demo'

export const useNotificationStore = defineStore('notification', () => {
  const items = ref([...DEMO_NOTIFICATIONS])
  const panelOpen = ref(false)

  const unreadCount = computed(() => items.value.filter((n) => !n.read).length)

  function openPanel() {
    panelOpen.value = true
  }

  function closePanel() {
    panelOpen.value = false
  }

  function markRead(id) {
    const n = items.value.find((x) => x.id === id)
    if (n) n.read = true
  }

  function markAllRead() {
    items.value.forEach((n) => {
      n.read = true
    })
  }

  function remove(id) {
    items.value = items.value.filter((n) => n.id !== id)
  }

  return {
    items,
    panelOpen,
    unreadCount,
    openPanel,
    closePanel,
    markRead,
    markAllRead,
    remove,
  }
})
