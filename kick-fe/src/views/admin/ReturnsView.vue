<script setup>
import { ref } from 'vue'

const returnRequests = ref([
  { id: 'RET-101', orderId: 'KV-1042', customer: 'Nguyễn Văn A', reason: 'Không vừa size (muốn đổi từ 41 lên 42)', status: 'PENDING', date: '26/07/2026' },
  { id: 'RET-102', orderId: 'KV-1039', customer: 'Lê Hoàng M', reason: 'Sản phẩm trầy xước đế', status: 'APPROVED', date: '24/07/2026' },
])

function updateStatus(id, newStatus) {
  const req = returnRequests.value.find((r) => r.id === id)
  if (req) req.status = newStatus
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-extrabold text-white">Quản Lý Đổi / Trả Hàng & Hoàn Tiền</h1>
        <p class="text-xs text-neutral-400 mt-1">Xử lý phê duyệt các yêu cầu đổi size hoặc hoàn tiền từ khách hàng</p>
      </div>
    </div>

    <div class="bg-neutral-900 border border-white/10 rounded-2xl overflow-hidden">
      <table class="w-full text-left text-xs text-neutral-300">
        <thead class="bg-black text-white font-bold uppercase border-b border-white/10">
          <tr>
            <th class="p-4">Mã Yêu Cầu</th>
            <th class="p-4">Mã Đơn Hàng</th>
            <th class="p-4">Khách Hàng</th>
            <th class="p-4">Lý Do Đổi Trả</th>
            <th class="p-4">Trạng Thái</th>
            <th class="p-4 text-right">Phê Duyệt</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-white/5">
          <tr v-for="r in returnRequests" :key="r.id" class="hover:bg-white/5">
            <td class="p-4 font-mono font-extrabold text-primary">{{ r.id }}</td>
            <td class="p-4 font-mono text-white">{{ r.orderId }}</td>
            <td class="p-4 font-bold text-white">{{ r.customer }}</td>
            <td class="p-4 text-neutral-300 max-w-xs">{{ r.reason }}</td>
            <td class="p-4">
              <span
                class="px-2.5 py-1 text-[10px] font-bold rounded-full uppercase"
                :class="[
                  r.status === 'APPROVED' && 'bg-emerald-500/20 text-emerald-400',
                  r.status === 'REJECTED' && 'bg-red-500/20 text-red-400',
                  r.status === 'PENDING' && 'bg-amber-500/20 text-amber-400',
                ]"
              >
                {{ r.status === 'APPROVED' ? 'Đã Chấp Nhận' : r.status === 'REJECTED' ? 'Đã Từ Chối' : 'Chờ Phê Duyệt' }}
              </span>
            </td>
            <td class="p-4 text-right">
              <div v-if="r.status === 'PENDING'" class="flex items-center justify-end gap-2">
                <button @click="updateStatus(r.id, 'APPROVED')" class="px-3 py-1.5 bg-emerald-500/20 text-emerald-400 font-bold rounded-lg hover:bg-emerald-500/30 cursor-pointer">
                  ✓ Duyệt
                </button>
                <button @click="updateStatus(r.id, 'REJECTED')" class="px-3 py-1.5 bg-red-500/20 text-red-400 font-bold rounded-lg hover:bg-red-500/30 cursor-pointer">
                  ✕ Từ Chối
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
