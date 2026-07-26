<script setup>
import { ref, computed } from 'vue'
import ordersJson from '@/data/json/orders.json'
import KvModal from '@/components/ui/KvModal.vue'
import KvSelect from '@/components/ui/KvSelect.vue'
import { formatVnd } from '@/data/demo'

const orders = ref([...ordersJson])
const statusFilter = ref('ALL')
const search = ref('')

const selectedOrderForInvoice = ref(null)
const showInvoiceModal = ref(false)

const statusOptions = [
  { value: 'ALL', label: 'Tất cả trạng thái' },
  { value: 'PENDING', label: 'Chờ xác nhận' },
  { value: 'DELIVERING', label: 'Đang giao hàng' },
  { value: 'COMPLETED', label: 'Đã hoàn thành' },
]

const filteredOrders = computed(() => {
  return orders.value.filter((o) => {
    if (statusFilter.value !== 'ALL' && o.status !== statusFilter.value) return false
    if (search.value && !o.id.toLowerCase().includes(search.value.toLowerCase()) && !o.customer.name.toLowerCase().includes(search.value.toLowerCase())) return false
    return true
  })
})

function updateOrderStatus(id, newStatus, newLabel) {
  const o = orders.value.find((x) => x.id === id)
  if (o) {
    o.status = newStatus
    o.statusLabel = newLabel
  }
}

function printInvoice(order) {
  selectedOrderForInvoice.value = order
  showInvoiceModal.value = true
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-extrabold text-slate-900">Quản Lý Đơn Hàng Hệ Thống</h1>
        <p class="text-xs text-slate-500 mt-1 font-medium">Xử lý tiến trình đơn hàng, duyệt đơn và in hóa đơn giao hàng</p>
      </div>
    </div>

    <!-- Filters & Search Toolbar -->
    <div class="bg-white border border-slate-200 rounded-3xl p-4 flex flex-col sm:flex-row gap-4 items-center justify-between shadow-sm">
      <input
        v-model="search"
        type="text"
        placeholder="Tìm theo Mã đơn (KV-1042), Tên khách..."
        class="w-full sm:w-80 h-10 px-4 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 focus:outline-none focus:border-amber-600 font-medium"
      />

      <div class="w-56">
        <KvSelect v-model="statusFilter" :options="statusOptions" :dark="false" />
      </div>
    </div>

    <!-- System Orders Table -->
    <div class="bg-white border border-slate-200 rounded-3xl overflow-hidden shadow-sm">
      <table class="w-full text-left text-xs text-slate-700">
        <thead class="bg-slate-900 text-white font-bold uppercase border-b border-slate-200">
          <tr>
            <th class="p-4">Mã Đơn Hàng</th>
            <th class="p-4">Khách Hàng</th>
            <th class="p-4">PT Thanh Toán</th>
            <th class="p-4">Tổng Tiền</th>
            <th class="p-4">Trạng Thái</th>
            <th class="p-4 text-right">Thao Tác Tiến Trình</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-slate-100">
          <tr v-for="o in filteredOrders" :key="o.id" class="hover:bg-slate-50 transition-colors">
            <td class="p-4 font-mono font-extrabold text-amber-600">{{ o.id }}</td>
            <td class="p-4">
              <span class="font-extrabold text-slate-900 block">{{ o.customer.name }}</span>
              <span class="text-[10px] text-slate-400 font-bold">{{ o.customer.phone }}</span>
            </td>
            <td class="p-4 font-semibold text-slate-700">{{ o.paymentMethod }}</td>
            <td class="p-4 font-extrabold text-slate-900">{{ formatVnd(o.total) }}</td>
            <td class="p-4">
              <span
                class="px-2.5 py-1 font-extrabold text-[10px] uppercase rounded-full"
                :class="[
                  o.status === 'COMPLETED' && 'bg-emerald-500/10 text-emerald-700',
                  o.status === 'DELIVERING' && 'bg-blue-500/10 text-blue-700',
                  o.status === 'PENDING' && 'bg-amber-500/10 text-amber-700',
                ]"
              >
                {{ o.statusLabel }}
              </span>
            </td>
            <td class="p-4 text-right">
              <div class="flex items-center justify-end gap-2">
                <button
                  v-if="o.status === 'PENDING'"
                  @click="updateOrderStatus(o.id, 'DELIVERING', 'Đang giao hàng')"
                  class="px-3 py-1.5 bg-blue-50 text-blue-700 font-extrabold rounded-xl border border-blue-200 hover:bg-blue-600 hover:text-white cursor-pointer transition-colors"
                >
                  ✓ Duyệt & Giao
                </button>
                <button
                  v-if="o.status === 'DELIVERING'"
                  @click="updateOrderStatus(o.id, 'COMPLETED', 'Đã hoàn thành')"
                  class="px-3 py-1.5 bg-emerald-50 text-emerald-700 font-extrabold rounded-xl border border-emerald-200 hover:bg-emerald-600 hover:text-white cursor-pointer transition-colors"
                >
                  ✓ Hoàn Thành
                </button>

                <button
                  @click="printInvoice(o)"
                  class="px-3 py-1.5 bg-slate-900 text-white font-extrabold rounded-xl hover:bg-amber-600 cursor-pointer shadow transition-colors"
                >
                  🖨️ In Hóa Đơn
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Printable Invoice Modal -->
    <KvModal v-model="showInvoiceModal" title="In Hóa Đơn / Phiếu Giao Hàng KickVerse" :dark="false">
      <div v-if="selectedOrderForInvoice" class="flex flex-col gap-4 text-xs text-slate-800 p-4 bg-slate-50 border border-slate-200 rounded-2xl">
        <div class="text-center border-b border-slate-200 pb-3">
          <h2 class="text-lg font-extrabold text-slate-900">KICKVERSE SNEAKER STORE</h2>
          <p class="text-[10px] text-slate-500 font-bold">Hóa đơn bán hàng & Phiếu đóng gói kiện hàng</p>
        </div>

        <div class="flex justify-between">
          <span>Mã đơn: <strong class="text-amber-600 font-mono">{{ selectedOrderForInvoice.id }}</strong></span>
          <span>Ngày: {{ selectedOrderForInvoice.createdAt }}</span>
        </div>

        <div class="flex flex-col gap-1 border-y border-slate-200 py-2">
          <span>Người nhận: <strong>{{ selectedOrderForInvoice.customer.name }}</strong> ({{ selectedOrderForInvoice.customer.phone }})</span>
          <span>Địa chỉ: {{ selectedOrderForInvoice.customer.address }}</span>
        </div>

        <div class="flex justify-between text-sm font-extrabold text-slate-900 border-t border-slate-200 pt-2">
          <span>Tổng thu tiền:</span>
          <span class="text-amber-600">{{ formatVnd(selectedOrderForInvoice.total) }}</span>
        </div>

        <button @click="showInvoiceModal = false; alert('Đã gửi lệnh in hóa đơn sang máy in!')" class="h-11 bg-slate-900 text-white font-extrabold text-xs rounded-xl mt-2 hover:bg-amber-600 shadow">
          🖨️ XÁC NHẬN IN PHIẾU
        </button>
      </div>
    </KvModal>
  </div>
</template>
