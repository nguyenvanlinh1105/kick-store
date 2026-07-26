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
        <h1 class="text-2xl font-extrabold text-white">Quản Lý Khách Hàng (CRM)</h1>
        <p class="text-xs text-neutral-400 mt-1">Hồ sơ khách hàng thành viên, tổng chi tiêu và phân hạng</p>
      </div>
    </div>

    <div class="bg-neutral-900 border border-white/10 rounded-2xl overflow-hidden">
      <table class="w-full text-left text-xs text-neutral-300">
        <thead class="bg-black text-white font-bold uppercase border-b border-white/10">
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
        <tbody class="divide-y divide-white/5">
          <tr v-for="c in customers" :key="c.id" class="hover:bg-white/5">
            <td class="p-4 flex items-center gap-3">
              <img :src="c.avatar" :alt="c.fullName" class="w-9 h-9 rounded-full border border-primary" />
              <span class="font-bold text-white">{{ c.fullName }}</span>
            </td>
            <td class="p-4">
              <span class="block text-white font-mono">{{ c.phone }}</span>
              <span class="text-[10px] text-neutral-400">{{ c.email }}</span>
            </td>
            <td class="p-4">
              <span class="px-2.5 py-1 text-[10px] font-extrabold uppercase rounded text-black bg-amber-400">
                Hạng {{ c.tier }}
              </span>
            </td>
            <td class="p-4 font-bold text-white">{{ c.orderCount }} đơn</td>
            <td class="p-4 font-extrabold text-primary">{{ formatVnd(c.totalSpent) }}</td>
            <td class="p-4">
              <span class="px-2 py-0.5 font-bold text-[10px] rounded" :class="[c.status === 'ACTIVE' ? 'bg-emerald-500/20 text-emerald-400' : 'bg-red-500/20 text-red-400']">
                {{ c.status === 'ACTIVE' ? 'Đang hoạt động' : 'Tài khoản bị khóa' }}
              </span>
            </td>
            <td class="p-4 text-right">
              <button
                @click="toggleLockAccount(c)"
                class="px-3 py-1.5 font-bold text-xs rounded-lg cursor-pointer transition-all"
                :class="[c.status === 'ACTIVE' ? 'bg-red-500/20 text-red-400 hover:bg-red-500/30' : 'bg-emerald-500/20 text-emerald-400 hover:bg-emerald-500/30']"
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
