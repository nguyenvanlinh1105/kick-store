import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import { DEMO_PRODUCTS, DEMO_VOUCHERS, formatVnd } from '@/data/demo'

export const useCartStore = defineStore('cart', () => {
  // Initialize with mock cart items for immediate rendering & testing
  const items = ref([
    {
      key: '1-41-Trắng',
      productId: 1,
      slug: DEMO_PRODUCTS[0].slug,
      name: DEMO_PRODUCTS[0].name,
      brand: DEMO_PRODUCTS[0].brand,
      image: DEMO_PRODUCTS[0].image,
      price: DEMO_PRODUCTS[0].price,
      priceLabel: DEMO_PRODUCTS[0].priceLabel,
      size: 41,
      color: 'Trắng',
      qty: 1,
      selected: true,
    },
    {
      key: '2-42-Navy',
      productId: 2,
      slug: DEMO_PRODUCTS[1].slug,
      name: DEMO_PRODUCTS[1].name,
      brand: DEMO_PRODUCTS[1].brand,
      image: DEMO_PRODUCTS[1].image,
      price: DEMO_PRODUCTS[1].price,
      priceLabel: DEMO_PRODUCTS[1].priceLabel,
      size: 42,
      color: 'Navy',
      qty: 1,
      selected: true,
    },
  ])

  const drawerOpen = ref(false)
  const appliedVoucherCode = ref('')
  const usedPoints = ref(0) // Loyalty points redeem (1 point = 1,000 VND)

  const count = computed(() => items.value.reduce((n, i) => n + i.qty, 0))
  const selectedCount = computed(() => items.value.filter((i) => i.selected).reduce((n, i) => n + i.qty, 0))
  
  const subtotal = computed(() =>
    items.value
      .filter((i) => i.selected)
      .reduce((sum, i) => sum + i.price * i.qty, 0)
  )

  const shippingFee = computed(() => (subtotal.value > 3000000 || subtotal.value === 0 ? 0 : 35000))

  const voucherDiscount = computed(() => {
    if (!appliedVoucherCode.value) return 0
    const v = DEMO_VOUCHERS.find((x) => x.code === appliedVoucherCode.value)
    if (!v) return 0
    if (subtotal.value < v.minSpend) return 0
    if (v.discount) return v.discount
    if (v.discountPercent) {
      const calc = (subtotal.value * v.discountPercent) / 100
      return v.maxDiscount ? Math.min(calc, v.maxDiscount) : calc
    }
    return 0
  })

  const pointsDiscount = computed(() => usedPoints.value * 1000)

  const total = computed(() => {
    const val = subtotal.value + shippingFee.value - voucherDiscount.value - pointsDiscount.value
    return Math.max(0, val)
  })

  const subtotalLabel = computed(() => formatVnd(subtotal.value))
  const shippingFeeLabel = computed(() => (shippingFee.value === 0 ? 'Miễn phí' : formatVnd(shippingFee.value)))
  const discountLabel = computed(() => formatVnd(voucherDiscount.value + pointsDiscount.value))
  const totalLabel = computed(() => formatVnd(total.value))

  function openDrawer() {
    drawerOpen.value = true
  }

  function closeDrawer() {
    drawerOpen.value = false
  }

  function addItem(product, { size = 41, color = product.colors?.[0] || 'Default', qty = 1 } = {}) {
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
        selected: true,
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

  function toggleSelect(key) {
    const item = items.value.find((i) => i.key === key)
    if (item) item.selected = !item.selected
  }

  function toggleSelectAll(val) {
    items.value.forEach((i) => (i.selected = val))
  }

  function removeSelected() {
    items.value = items.value.filter((i) => !i.selected)
  }

  function applyVoucher(code) {
    const found = DEMO_VOUCHERS.find((v) => v.code === code.trim().toUpperCase())
    if (!found) {
      return { success: false, message: 'Mã giảm giá không hợp lệ' }
    }
    if (subtotal.value < found.minSpend) {
      return { success: false, message: `Đơn hàng tối thiểu ${formatVnd(found.minSpend)} để áp dụng mã này` }
    }
    appliedVoucherCode.value = found.code
    return { success: true, message: `Áp dụng thành công mã ${found.code}` }
  }

  function removeVoucher() {
    appliedVoucherCode.value = ''
  }

  function clear() {
    items.value = []
    appliedVoucherCode.value = ''
    usedPoints.value = 0
  }

  return {
    items,
    drawerOpen,
    appliedVoucherCode,
    usedPoints,
    count,
    selectedCount,
    subtotal,
    shippingFee,
    voucherDiscount,
    pointsDiscount,
    total,
    subtotalLabel,
    shippingFeeLabel,
    discountLabel,
    totalLabel,
    openDrawer,
    closeDrawer,
    addItem,
    updateQty,
    removeItem,
    toggleSelect,
    toggleSelectAll,
    removeSelected,
    applyVoucher,
    removeVoucher,
    clear,
  }
})
