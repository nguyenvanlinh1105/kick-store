<script setup>
import { ref, computed } from 'vue'
import ordersJson from '@/data/json/orders.json'
import KvModal from '@/components/ui/KvModal.vue'
import { formatVnd } from '@/data/demo'

const orders = ref([...ordersJson])
const statusFilter = ref('ALL')
const search = ref('')

const selectedOrderForInvoice = ref(null)
const showInvoiceModal = ref(false)

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
        <h1 class="text-2xl font-extrabold text-white">Quản Lý Đơn Hàng Hệ Thống</h1>
        <p class="text-xs text-neutral-400 mt-1">Xử lý tiến trình đơn hàng, duyệt đơn và in hóa đơn giao hàng</p>
      </div>
    </div>

    <!-- Filters & Search Toolbar -->
    <div class="bg-neutral-900 border border-white/10 rounded-2xl p-4 flex flex-col sm:flex-row gap-4 items-center justify-between">
      <input
        v-model="search"
        type="text"
        placeholder="Tìm theo Mã đơn (KV-1042), Tên khách..."
        class="w-full sm:w-72 h-10 px-4 text-xs bg-black border border-white/15 rounded-xl text-white focus:outline-none focus:border-primary"
      />

      <select v-model="statusFilter" class="h-10 px-4 text-xs bg-black border border-white/15 rounded-xl text-white">
        <option value="ALL">Tất cả trạng thái</option>
        <option value="PENDING">Chờ xác nhận</option>
        <option value="DELIVERING">Đang giao hàng</option>
        <option value="COMPLETED">Đã hoàn thành</option>
      </select>
    </div>

    <!-- System Orders Table -->
    <div class="bg-neutral-900 border border-white/10 rounded-2xl overflow-hidden">
      <table class="w-full text-left text-xs text-neutral-300">
        <thead class="bg-black text-white font-bold uppercase border-b border-white/10">
          <tr>
            <th class="p-4">Mã Đơn Hàng</th>
            <th class="p-4">Khách Hàng</th>
            <th class="p-4">PT Thanh Toán</th>
            <th class="p-4">Tổng Tiền</th>
            <th class="p-4">Trạng Thái</th>
            <th class="p-4 text-right">Thao Tác Tiến Trình</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-white/5">
          <tr v-for="o in filteredOrders" :key="o.id" class="hover:bg-white/5">
            <td class="p-4 font-mono font-extrabold text-primary">{{ o.id }}</td>
            <td class="p-4">
              <span class="font-bold text-white block">{{ o.customer.name }}</span>
              <span class="text-[10px] text-neutral-400">{{ o.customer.phone }}</span>
            </td>
            <td class="p-4 font-medium text-neutral-300">{{ o.paymentMethod }}</td>
            <td class="p-4 font-extrabold text-white">{{ formatVnd(o.total) }}</td>
            <td class="p-4">
              <span
                class="px-2.5 py-1 font-bold text-[10px] uppercase rounded-full"
                :class="[
                  o.status === 'COMPLETED' && 'bg-emerald-500/20 text-emerald-400',
                  o.status === 'DELIVERING' && 'bg-blue-500/20 text-blue-400',
                  o.status === 'PENDING' && 'bg-amber-500/20 text-amber-400',
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
                  class="px-3 py-1.5 bg-blue-500/20 text-blue-400 font-bold rounded-lg hover:bg-blue-500/30 cursor-pointer"
                >
                  ✓ Duyệt & Giao
                </button>
                <button
                  v-if="o.status === 'DELIVERING'"
                  @click="updateOrderStatus(o.id, 'COMPLETED', 'Đã hoàn thành')"
                  class="px-3 py-1.5 bg-emerald-500/20 text-emerald-400 font-bold rounded-lg hover:bg-emerald-500/30 cursor-pointer"
                >
                  ✓ Hoàn Thành
                </button>

                <button
                  @click="printInvoice(o)"
                  class="px-3 py-1.5 bg-neutral-800 text-white font-bold rounded-lg hover:bg-neutral-700 cursor-pointer"
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
    <KvModal v-model="showInvoiceModal" title="In Hóa Đơn / Phiếu Giao Hàng KickVerse" :dark="true">
      <div v-if="selectedOrderForInvoice" class="flex flex-col gap-4 text-xs text-neutral-300 p-2 bg-black border border-white/10 rounded-xl">
        <div class="text-center border-b border-white/10 pb-3">
          <h2 class="text-lg font-extrabold text-white">KICKVERSE SNEAKER STORE</h2>
          <p class="text-[10px] text-neutral-400">Hóa đơn bán hàng & Phiếu đóng gói kiện hàng</p>
        </div>

        <div class="flex justify-between">
          <span>Mã đơn: <strong class="text-primary font-mono">{{ selectedOrderForInvoice.id }}</strong></span>
          <span>Ngày: {{ selectedOrderForInvoice.createdAt }}</span>
        </div>

        <div class="flex flex-col gap-1 border-y border-white/10 py-2">
          <span>Người nhận: <strong>{{ selectedOrderForInvoice.customer.name }}</strong> ({{ selectedOrderForInvoice.customer.phone }})</span>
          <span>Địa chỉ: {{ selectedOrderForInvoice.customer.address }}</span>
        </div>

        <div class="flex justify-between text-sm font-extrabold text-white border-t border-white/10 pt-2">
          <span>Tổng thu tiền:</span>
          <span class="text-primary">{{ formatVnd(selectedOrderForInvoice.total) }}</span>
        </div>

        <button @click="showInvoiceModal = false; alert('Đã gửi lệnh in hóa đơn sang máy in!')" class="h-10 bg-primary text-black font-extrabold text-xs rounded-lg mt-2">
          🖨️ XÁC NHẬN IN PHIẾU
        </button>
      </div>
    </KvModal>
  </div>
</template>
