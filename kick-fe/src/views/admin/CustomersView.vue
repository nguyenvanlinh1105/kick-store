<script setup>
import { ref } from 'vue'
import customersJson from '@/data/json/customers.json'
import { formatVnd } from '@/data/demo'

const customers = ref([...customersJson])

function toggleLockAccount(cust) {
  cust.status = cust.status === 'ACTIVE' ? 'LOCKED' : 'ACTIVE'
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-extrabold text-slate-900">Quản Lý Khách Hàng (CRM)</h1>
        <p class="text-xs text-slate-500 mt-1 font-medium">Hồ sơ khách hàng thành viên, tổng chi tiêu và phân hạng</p>
      </div>
    </div>

    <!-- PURE WHITE LIGHT CRM TABLE CARD -->
    <div class="bg-white border border-slate-200 rounded-3xl overflow-hidden shadow-sm">
      <table class="w-full text-left text-xs text-slate-700">
        <thead class="bg-slate-900 text-white font-bold uppercase border-b border-slate-200">
          <tr>
            <th class="p-4">Khách Hàng</th>
            <th class="p-4">Số Điện Thoại / Email</th>
            <th class="p-4">Hạng Thành Viên</th>
            <th class="p-4">Số Đơn</th>
            <th class="p-4">Tổng Chi Tiêu</th>
            <th class="p-4">Trạng Thái</th>
            <th class="p-4 text-right">Khóa / Mở</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-slate-100">
          <tr v-for="c in customers" :key="c.id" class="hover:bg-slate-50 transition-colors">
            <td class="p-4 flex items-center gap-3">
              <img :src="c.avatar" :alt="c.fullName" class="w-9 h-9 rounded-full border border-slate-200" />
              <span class="font-extrabold text-slate-900">{{ c.fullName }}</span>
            </td>
            <td class="p-4">
              <span class="block text-slate-900 font-bold font-mono">{{ c.phone }}</span>
              <span class="text-[10px] text-slate-400 font-medium">{{ c.email }}</span>
            </td>
            <td class="p-4">
              <span class="px-2.5 py-1 text-[10px] font-extrabold uppercase rounded-full text-slate-900 bg-amber-400">
                Hạng {{ c.tier }}
              </span>
            </td>
            <td class="p-4 font-bold text-slate-900">{{ c.orderCount }} đơn</td>
            <td class="p-4 font-extrabold text-amber-600">{{ formatVnd(c.totalSpent) }}</td>
            <td class="p-4">
              <span class="px-2.5 py-1 font-extrabold text-[10px] rounded-full" :class="[c.status === 'ACTIVE' ? 'bg-emerald-500/10 text-emerald-700' : 'bg-red-500/10 text-red-600']">
                {{ c.status === 'ACTIVE' ? 'Đang hoạt động' : 'Tài khoản bị khóa' }}
              </span>
            </td>
            <td class="p-4 text-right">
              <button
                @click="toggleLockAccount(c)"
                class="px-3.5 py-1.5 font-extrabold text-xs rounded-xl cursor-pointer transition-all border"
                :class="[c.status === 'ACTIVE' ? 'bg-red-50 text-red-600 border-red-200 hover:bg-red-600 hover:text-white' : 'bg-emerald-50 text-emerald-700 border-emerald-200 hover:bg-emerald-600 hover:text-white']"
              >
                {{ c.status === 'ACTIVE' ? '🔒 Khóa Tài Khoản' : '🔓 Mở Khóa' }}
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
