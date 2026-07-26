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
        <h1 class="text-2xl font-extrabold text-slate-900">Quản Lý Đổi / Trả Hàng & Hoàn Tiền</h1>
        <p class="text-xs text-slate-500 mt-1 font-medium">Xử lý phê duyệt các yêu cầu đổi size hoặc hoàn tiền từ khách hàng</p>
      </div>
    </div>

    <!-- PURE WHITE LIGHT RETURNS TABLE -->
    <div class="bg-white border border-slate-200 rounded-3xl overflow-hidden shadow-sm">
      <table class="w-full text-left text-xs text-slate-700">
        <thead class="bg-slate-900 text-white font-bold uppercase border-b border-slate-200">
          <tr>
            <th class="p-4">Mã Yêu Cầu</th>
            <th class="p-4">Mã Đơn Hàng</th>
            <th class="p-4">Khách Hàng</th>
            <th class="p-4">Lý Do Đổi Trả</th>
            <th class="p-4">Trạng Thái</th>
            <th class="p-4 text-right">Phê Duyệt</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-slate-100">
          <tr v-for="r in returnRequests" :key="r.id" class="hover:bg-slate-50 transition-colors">
            <td class="p-4 font-mono font-extrabold text-amber-600">{{ r.id }}</td>
            <td class="p-4 font-mono font-bold text-slate-900">{{ r.orderId }}</td>
            <td class="p-4 font-extrabold text-slate-900">{{ r.customer }}</td>
            <td class="p-4 text-slate-600 font-medium max-w-xs">{{ r.reason }}</td>
            <td class="p-4">
              <span
                class="px-2.5 py-1 text-[10px] font-extrabold rounded-full uppercase"
                :class="[
                  r.status === 'APPROVED' && 'bg-emerald-500/10 text-emerald-700',
                  r.status === 'REJECTED' && 'bg-red-500/10 text-red-600',
                  r.status === 'PENDING' && 'bg-amber-500/10 text-amber-700',
                ]"
              >
                {{ r.status === 'APPROVED' ? 'Đã Chấp Nhận' : r.status === 'REJECTED' ? 'Đã Từ Chối' : 'Chờ Phê Duyệt' }}
              </span>
            </td>
            <td class="p-4 text-right">
              <div v-if="r.status === 'PENDING'" class="flex items-center justify-end gap-2">
                <button @click="updateStatus(r.id, 'APPROVED')" class="px-3 py-1.5 bg-emerald-50 text-emerald-700 font-extrabold rounded-xl border border-emerald-200 hover:bg-emerald-600 hover:text-white cursor-pointer transition-colors">
                  ✓ Duyệt
                </button>
                <button @click="updateStatus(r.id, 'REJECTED')" class="px-3 py-1.5 bg-red-50 text-red-600 font-extrabold rounded-xl border border-red-200 hover:bg-red-600 hover:text-white cursor-pointer transition-colors">
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
