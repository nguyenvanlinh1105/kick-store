import { defineStore } from 'pinia'
import { computed, ref } from 'vue'

export const useWishlistStore = defineStore('wishlist', () => {
  const ids = ref([])

  const count = computed(() => ids.value.length)

  function has(id) {
    return ids.value.includes(id)
  }

  function toggle(product) {
    if (has(product.id)) {
      ids.value = ids.value.filter((x) => x !== product.id)
    } else {
      ids.value.push(product.id)
    }
  }

  function remove(id) {
    ids.value = ids.value.filter((x) => x !== id)
  }

  return { ids, count, has, toggle, remove }
})
