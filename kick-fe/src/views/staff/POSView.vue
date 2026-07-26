<script setup>
import { ref, computed } from 'vue'
import productsJson from '@/data/json/products.json'
import customersJson from '@/data/json/customers.json'
import KvModal from '@/components/ui/KvModal.vue'
import { formatVnd } from '@/data/demo'

const searchSKU = ref('')
const posCart = ref([
  { id: 1, name: productsJson[0].name, size: 41, color: 'Trắng', price: productsJson[0].price, qty: 1, image: productsJson[0].image, sku: productsJson[0].sku },
])

const selectedCustomer = ref(customersJson[0])
const selectedPayment = ref('cash') // 'cash' | 'vietqr' | 'card'
const cashGiven = ref(3000000)

const subtotal = computed(() => posCart.value.reduce((s, i) => s + i.price * i.qty, 0))
const changeAmount = computed(() => Math.max(0, cashGiven.value - subtotal.value))

const showReceiptModal = ref(false)

function handleBarcodeScan() {
  if (!searchSKU.value) return
  const p = productsJson.find((x) => x.sku.toLowerCase().includes(searchSKU.value.toLowerCase()) || x.name.toLowerCase().includes(searchSKU.value.toLowerCase())) || productsJson[0]
  
  const existing = posCart.value.find((i) => i.id === p.id)
  if (existing) {
    existing.qty++
  } else {
    posCart.value.push({
      id: p.id,
      name: p.name,
      size: 41,
      color: 'Default',
      price: p.price,
      qty: 1,
      image: p.image,
      sku: p.sku,
    })
  }
  searchSKU.value = ''
}

function updateQty(idx, delta) {
  posCart.value[idx].qty += delta
  if (posCart.value[idx].qty <= 0) {
    posCart.value.splice(idx, 1)
  }
}

function completeCheckout() {
  if (posCart.value.length === 0) return
  showReceiptModal.value = true
}
</script>

<template>
  <div class="h-screen bg-black text-white flex flex-col overflow-hidden">
    <!-- POS HEADER -->
    <header class="h-14 bg-neutral-900 border-b border-white/10 px-6 flex items-center justify-between shrink-0">
      <div class="flex items-center gap-3">
        <span class="text-xs font-extrabold px-2.5 py-1 bg-primary text-black rounded uppercase">KICKVERSE POS</span>
        <span class="text-xs text-neutral-300">Bán Hàng Tại Quầy - Quầy 01</span>
      </div>

      <div class="flex items-center gap-4 text-xs">
        <span class="text-neutral-400">Thu ngân: <strong class="text-white">Hoàng Anh Tuấn</strong></span>
        <RouterLink to="/staff" class="text-primary font-bold hover:underline no-underline">Thoát POS →</RouterLink>
      </div>
    </header>

    <!-- POS MAIN WORKSPACE -->
    <div class="flex flex-1 overflow-hidden">
      <!-- LEFT: PRODUCT SEARCH & CATALOG -->
      <div class="flex-1 p-6 flex flex-col gap-4 overflow-y-auto border-r border-white/10">
        <!-- Barcode / Search Input -->
        <form @submit.prevent="handleBarcodeScan" class="relative">
          <input
            v-model="searchSKU"
            type="text"
            placeholder="🔍 Quét mã vạch Barcode hoặc nhập mã SKU sản phẩm (Nhấn Enter)..."
            class="w-full h-12 px-4 pr-12 text-sm bg-neutral-900 border-2 border-primary/50 rounded-xl text-white placeholder-neutral-500 focus:outline-none focus:border-primary"
          />
          <button type="submit" class="absolute right-3 top-1/2 -translate-y-1/2 text-xs font-bold bg-primary text-black px-3 py-1.5 rounded-lg cursor-pointer">
            Thêm
          </button>
        </form>

        <!-- Quick Select Products Grid -->
        <span class="text-xs font-bold text-neutral-400 uppercase tracking-wider">Sản Phẩm Khả Dụng Trong Kho</span>
        <div class="grid grid-cols-2 md:grid-cols-3 gap-3">
          <button
            v-for="p in productsJson"
            :key="p.id"
            type="button"
            class="p-3 bg-neutral-900 border border-white/10 hover:border-primary rounded-xl flex items-center gap-3 text-left transition-all cursor-pointer"
            @click="searchSKU = p.sku; handleBarcodeScan()"
          >
            <img :src="p.image" :alt="p.name" class="w-12 h-12 object-cover rounded-lg shrink-0" />
            <div class="flex flex-col">
              <span class="text-xs font-bold text-white line-clamp-1">{{ p.name }}</span>
              <span class="text-[10px] text-neutral-400 font-mono">{{ p.sku }}</span>
              <span class="text-xs font-extrabold text-primary mt-1">{{ formatVnd(p.price) }}</span>
            </div>
          </button>
        </div>
      </div>

      <!-- RIGHT: CURRENT RECEIPT CART & CHECKOUT -->
      <div class="w-96 bg-neutral-950 p-6 flex flex-col justify-between shrink-0">
        <div class="flex flex-col gap-4">
          <!-- Customer Lookup -->
          <div class="p-3 bg-neutral-900 border border-white/10 rounded-xl flex items-center justify-between text-xs">
            <div>
              <span class="text-[10px] text-neutral-400 block">Khách hàng tích điểm:</span>
              <span class="font-bold text-white">{{ selectedCustomer.fullName }} ({{ selectedCustomer.phone }})</span>
            </div>
            <span class="text-[10px] bg-amber-400 text-black font-extrabold px-1.5 py-0.5 rounded">Hạng {{ selectedCustomer.tier }}</span>
          </div>

          <!-- Items List -->
          <div class="flex flex-col gap-2 max-h-72 overflow-y-auto pr-1">
            <div v-for="(item, idx) in posCart" :key="idx" class="p-3 bg-neutral-900 border border-white/5 rounded-xl flex items-center justify-between text-xs">
              <div class="flex flex-col gap-0.5">
                <span class="font-bold text-white line-clamp-1">{{ item.name }}</span>
                <span class="text-[10px] text-neutral-400">Size {{ item.size }} | {{ formatVnd(item.price) }}</span>
              </div>
              <div class="flex items-center gap-2">
                <button @click="updateQty(idx, -1)" class="w-6 h-6 bg-black rounded text-white font-bold cursor-pointer">-</button>
                <span class="font-bold text-white">{{ item.qty }}</span>
                <button @click="updateQty(idx, 1)" class="w-6 h-6 bg-black rounded text-white font-bold cursor-pointer">+</button>
              </div>
            </div>
          </div>
        </div>

        <!-- Checkout Controls -->
        <div class="flex flex-col gap-4 border-t border-white/10 pt-4">
          <!-- Payment Method Radios -->
          <div class="grid grid-cols-3 gap-2 text-center text-xs">
            <button
              type="button"
              class="py-2 font-bold rounded-lg border cursor-pointer transition-all"
              :class="[selectedPayment === 'cash' ? 'bg-primary text-black border-primary' : 'bg-neutral-900 border-white/10 text-neutral-300']"
              @click="selectedPayment = 'cash'"
            >
              💵 Tiền mặt
            </button>
            <button
              type="button"
              class="py-2 font-bold rounded-lg border cursor-pointer transition-all"
              :class="[selectedPayment === 'vietqr' ? 'bg-primary text-black border-primary' : 'bg-neutral-900 border-white/10 text-neutral-300']"
              @click="selectedPayment = 'vietqr'"
            >
              🏦 VietQR
            </button>
            <button
              type="button"
              class="py-2 font-bold rounded-lg border cursor-pointer transition-all"
              :class="[selectedPayment === 'card' ? 'bg-primary text-black border-primary' : 'bg-neutral-900 border-white/10 text-neutral-300']"
              @click="selectedPayment = 'card'"
            >
              💳 Quẹt thẻ
            </button>
          </div>

          <div class="flex justify-between text-base font-extrabold text-white">
            <span>Tổng thanh toán:</span>
            <span class="text-primary text-xl">{{ formatVnd(subtotal) }}</span>
          </div>

          <button
            type="button"
            class="w-full h-14 bg-primary text-black font-extrabold text-sm uppercase tracking-wider rounded-xl hover:bg-primary-hover transition-all cursor-pointer shadow-lg"
            @click="completeCheckout"
          >
            🖨️ THANH TOÁN & IN BILL (80mm)
          </button>
        </div>
      </div>
    </div>

    <!-- Thermal Receipt Modal 80mm -->
    <KvModal v-model="showReceiptModal" title="Hóa Đơn Bán Lẻ Tại Quầy (Máy in 80mm)" :dark="true">
      <div class="flex flex-col gap-3 text-xs text-neutral-300 font-mono p-4 bg-white text-black rounded-xl max-w-xs mx-auto">
        <div class="text-center border-b border-black pb-2">
          <h3 class="font-extrabold text-base">KICKVERSE SNEAKER</h3>
          <p class="text-[10px]">Hóa đơn bán lẻ tại quầy</p>
        </div>
        <div v-for="i in posCart" :key="i.id" class="flex justify-between">
          <span>{{ i.name }} (x{{ i.qty }})</span>
          <span class="font-bold">{{ formatVnd(i.price * i.qty) }}</span>
        </div>
        <div class="border-t border-black pt-2 flex justify-between font-extrabold text-sm">
          <span>TỔNG TIỀN:</span>
          <span>{{ formatVnd(subtotal) }}</span>
        </div>
        <button @click="showReceiptModal = false; alert('Đã in bill 80mm thành công!')" class="h-10 bg-black text-white font-bold text-xs rounded-lg mt-2 cursor-pointer">
          🖨️ IN BILL NGAY
        </button>
      </div>
    </KvModal>
  </div>
</template>
