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
  <div class="h-screen bg-slate-50 text-slate-900 flex flex-col overflow-hidden">
    <!-- POS HEADER -->
    <header class="h-14 bg-slate-900 text-white px-6 flex items-center justify-between shrink-0 shadow-md">
      <div class="flex items-center gap-3">
        <span class="text-xs font-extrabold px-3 py-1 bg-amber-600 text-white rounded-lg uppercase">KICKVERSE POS</span>
        <span class="text-xs text-slate-300 font-bold">Bán Hàng Tại Quầy - Quầy 01</span>
      </div>

      <div class="flex items-center gap-4 text-xs">
        <span class="text-slate-300">Thu ngân: <strong class="text-white font-extrabold">Hoàng Anh Tuấn</strong></span>
        <RouterLink to="/staff" class="text-amber-400 font-extrabold hover:underline no-underline">Thoát POS →</RouterLink>
      </div>
    </header>

    <!-- POS MAIN WORKSPACE -->
    <div class="flex flex-1 overflow-hidden">
      <!-- LEFT: PRODUCT SEARCH & CATALOG -->
      <div class="flex-1 p-6 flex flex-col gap-4 overflow-y-auto border-r border-slate-200 bg-white">
        <!-- Barcode / Search Input -->
        <form @submit.prevent="handleBarcodeScan" class="relative">
          <input
            v-model="searchSKU"
            type="text"
            placeholder="🔍 Quét mã vạch Barcode hoặc nhập mã SKU sản phẩm (Nhấn Enter)..."
            class="w-full h-12 px-4 pr-16 text-xs bg-slate-50 border-2 border-slate-200 focus:border-amber-600 rounded-2xl text-slate-900 placeholder-slate-400 focus:outline-none font-medium shadow-sm transition-all"
          />
          <button type="submit" class="absolute right-3 top-1/2 -translate-y-1/2 text-xs font-extrabold bg-slate-900 text-white px-4 py-1.5 rounded-xl cursor-pointer hover:bg-amber-600 shadow">
            Thêm
          </button>
        </form>

        <!-- Quick Select Products Grid -->
        <span class="text-xs font-extrabold text-slate-900 uppercase tracking-wider">Sản Phẩm Khả Dụng Trong Kho (Chạm để chọn)</span>
        <div class="grid grid-cols-2 md:grid-cols-3 gap-3">
          <button
            v-for="p in productsJson"
            :key="p.id"
            type="button"
            class="p-3 bg-slate-50 border border-slate-200 hover:border-amber-600 rounded-2xl flex items-center gap-3 text-left transition-all cursor-pointer shadow-sm hover:shadow-md"
            @click="searchSKU = p.sku; handleBarcodeScan()"
          >
            <img :src="p.image" :alt="p.name" class="w-12 h-12 object-cover rounded-xl shrink-0 border border-slate-200" />
            <div class="flex flex-col min-w-0">
              <span class="text-xs font-extrabold text-slate-900 truncate">{{ p.name }}</span>
              <span class="text-[10px] text-slate-400 font-mono font-bold">{{ p.sku }}</span>
              <span class="text-xs font-extrabold text-amber-600 mt-1">{{ formatVnd(p.price) }}</span>
            </div>
          </button>
        </div>
      </div>

      <!-- RIGHT: CURRENT RECEIPT CART & CHECKOUT -->
      <div class="w-96 bg-slate-100 p-6 flex flex-col justify-between shrink-0 border-l border-slate-200">
        <div class="flex flex-col gap-4">
          <!-- Customer Lookup -->
          <div class="p-4 bg-white border border-slate-200 rounded-2xl flex items-center justify-between text-xs shadow-sm">
            <div>
              <span class="text-[10px] text-slate-400 font-bold block">Khách hàng tích điểm:</span>
              <span class="font-extrabold text-slate-900">{{ selectedCustomer.fullName }} ({{ selectedCustomer.phone }})</span>
            </div>
            <span class="text-[10px] bg-amber-400 text-slate-900 font-extrabold px-2 py-0.5 rounded-full">Hạng {{ selectedCustomer.tier }}</span>
          </div>

          <!-- Items List -->
          <div class="flex flex-col gap-2 max-h-72 overflow-y-auto pr-1">
            <div v-for="(item, idx) in posCart" :key="idx" class="p-3 bg-white border border-slate-200 rounded-2xl flex items-center justify-between text-xs shadow-sm">
              <div class="flex flex-col gap-0.5 min-w-0 pr-2">
                <span class="font-extrabold text-slate-900 truncate">{{ item.name }}</span>
                <span class="text-[10px] text-slate-500 font-bold">Size {{ item.size }} | {{ formatVnd(item.price) }}</span>
              </div>
              <div class="flex items-center gap-2 shrink-0">
                <button @click="updateQty(idx, -1)" class="w-7 h-7 bg-slate-100 border border-slate-200 rounded-lg text-slate-900 font-extrabold cursor-pointer hover:bg-slate-200">-</button>
                <span class="font-extrabold text-slate-900">{{ item.qty }}</span>
                <button @click="updateQty(idx, 1)" class="w-7 h-7 bg-slate-100 border border-slate-200 rounded-lg text-slate-900 font-extrabold cursor-pointer hover:bg-slate-200">+</button>
              </div>
            </div>
          </div>
        </div>

        <!-- Checkout Controls -->
        <div class="flex flex-col gap-4 border-t border-slate-200 pt-4">
          <!-- Payment Method Radios -->
          <div class="grid grid-cols-3 gap-2 text-center text-xs">
            <button
              type="button"
              class="py-2.5 font-extrabold rounded-xl border cursor-pointer transition-all shadow-sm"
              :class="[selectedPayment === 'cash' ? 'bg-slate-900 text-white border-slate-900' : 'bg-white border-slate-200 text-slate-700 hover:border-slate-300']"
              @click="selectedPayment = 'cash'"
            >
              💵 Tiền mặt
            </button>
            <button
              type="button"
              class="py-2.5 font-extrabold rounded-xl border cursor-pointer transition-all shadow-sm"
              :class="[selectedPayment === 'vietqr' ? 'bg-slate-900 text-white border-slate-900' : 'bg-white border-slate-200 text-slate-700 hover:border-slate-300']"
              @click="selectedPayment = 'vietqr'"
            >
              🏦 VietQR
            </button>
            <button
              type="button"
              class="py-2.5 font-extrabold rounded-xl border cursor-pointer transition-all shadow-sm"
              :class="[selectedPayment === 'card' ? 'bg-slate-900 text-white border-slate-900' : 'bg-white border-slate-200 text-slate-700 hover:border-slate-300']"
              @click="selectedPayment = 'card'"
            >
              💳 Quẹt thẻ
            </button>
          </div>

          <div class="flex justify-between text-base font-extrabold text-slate-900">
            <span>Tổng thanh toán:</span>
            <span class="text-amber-600 text-xl">{{ formatVnd(subtotal) }}</span>
          </div>

          <button
            type="button"
            class="w-full h-14 bg-slate-900 text-white font-extrabold text-xs uppercase tracking-wider rounded-2xl hover:bg-amber-600 transition-all cursor-pointer shadow-lg"
            @click="completeCheckout"
          >
            🖨️ THANH TOÁN & IN BILL (80mm)
          </button>
        </div>
      </div>
    </div>

    <!-- Thermal Receipt Modal 80mm -->
    <KvModal v-model="showReceiptModal" title="Hóa Đơn Bán Lẻ Tại Quầy (Máy in 80mm)" :dark="false">
      <div class="flex flex-col gap-3 text-xs text-slate-900 font-mono p-4 bg-slate-50 border border-slate-200 rounded-2xl max-w-xs mx-auto">
        <div class="text-center border-b border-slate-300 pb-2">
          <h3 class="font-extrabold text-base text-slate-900">KICKVERSE SNEAKER</h3>
          <p class="text-[10px] text-slate-500 font-bold">Hóa đơn bán lẻ tại quầy</p>
        </div>
        <div v-for="i in posCart" :key="i.id" class="flex justify-between">
          <span>{{ i.name }} (x{{ i.qty }})</span>
          <span class="font-bold text-slate-900">{{ formatVnd(i.price * i.qty) }}</span>
        </div>
        <div class="border-t border-slate-300 pt-2 flex justify-between font-extrabold text-sm">
          <span>TỔNG TIỀN:</span>
          <span class="text-amber-600">{{ formatVnd(subtotal) }}</span>
        </div>
        <button @click="showReceiptModal = false; alert('Đã in bill 80mm thành công!')" class="h-11 bg-slate-900 text-white font-extrabold text-xs rounded-xl mt-2 cursor-pointer hover:bg-amber-600 shadow">
          🖨️ IN BILL NGAY
        </button>
      </div>
    </KvModal>
  </div>
</template>
