import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import { formatVnd } from '@/data/demo'

export const useCartStore = defineStore('cart', () => {
  const items = ref([])
  const drawerOpen = ref(false)

  const count = computed(() => items.value.reduce((n, i) => n + i.qty, 0))
  const subtotal = computed(() => items.value.reduce((n, i) => n + i.price * i.qty, 0))
  const subtotalLabel = computed(() => formatVnd(subtotal.value))

  function openDrawer() {
    drawerOpen.value = true
  }

  function closeDrawer() {
    drawerOpen.value = false
  }

  function addItem(product, { size = 41, color = product.colors?.[0], qty = 1 } = {}) {
    const key = `${product.id}-${size}-${color}`
    const existing = items.value.find((i) => i.key === key)
    if (existing) {
      existing.qty += qty
    } else {
      items.value.push({
        key,
        productId: product.id,
        slug: product.slug,
        name: product.name,
        brand: product.brand,
        image: product.image,
        price: product.price,
        priceLabel: product.priceLabel,
        size,
        color,
        qty,
      })
    }
    drawerOpen.value = true
  }

  function updateQty(key, qty) {
    const item = items.value.find((i) => i.key === key)
    if (!item) return
    item.qty = Math.max(1, qty)
  }

  function removeItem(key) {
    items.value = items.value.filter((i) => i.key !== key)
  }

  function clear() {
    items.value = []
  }

  return {
    items,
    drawerOpen,
    count,
    subtotal,
    subtotalLabel,
    openDrawer,
    closeDrawer,
    addItem,
    updateQty,
    removeItem,
    clear,
  }
})
