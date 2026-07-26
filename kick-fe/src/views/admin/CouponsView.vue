<script setup>
import { ref } from 'vue'
import vouchersJson from '@/data/json/vouchers.json'
import KvModal from '@/components/ui/KvModal.vue'
import { formatVnd } from '@/data/demo'

const coupons = ref([...vouchersJson])
const showModal = ref(false)

const form = ref({
  code: '',
  title: '',
  desc: '',
  discount: 100000,
  minSpend: 1000000,
  expiry: '31/12/2026',
  usageLimit: 500,
})

function addCoupon() {
  if (!form.value.code) return
  coupons.value.push({
    id: Date.now(),
    ...form.value,
    usedCount: 0,
    status: 'ACTIVE',
  })
  showModal.value = false
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-extrabold text-white">Quản Lý Mã Giảm Giá (Coupons)</h1>
        <p class="text-xs text-neutral-400 mt-1">Tạo và cấu hình voucher khuyến mãi toàn sàn</p>
      </div>

      <button @click="showModal = true" class="px-5 py-2.5 bg-primary text-black font-extrabold text-xs rounded-xl hover:bg-primary-hover cursor-pointer">
        + Tạo Mã Giảm Giá Mới
      </button>
    </div>

    <!-- Coupons Table -->
    <div class="bg-neutral-900 border border-white/10 rounded-2xl overflow-hidden">
      <table class="w-full text-left text-xs text-neutral-300">
        <thead class="bg-black text-white font-bold uppercase border-b border-white/10">
          <tr>
            <th class="p-4">Mã Voucher</th>
            <th class="p-4">Tiêu Đề / Mô Tả</th>
            <th class="p-4">Mức Giảm</th>
            <th class="p-4">Đơn Tối Thiểu</th>
            <th class="p-4">Lượt Dùng</th>
            <th class="p-4">Hạn Sử Dụng</th>
            <th class="p-4 text-right">Thao Tác</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-white/5">
          <tr v-for="c in coupons" :key="c.id" class="hover:bg-white/5">
            <td class="p-4 font-mono font-extrabold text-primary">{{ c.code }}</td>
            <td class="p-4 font-bold text-white">{{ c.title }}</td>
            <td class="p-4 font-bold text-emerald-400">{{ formatVnd(c.discount || 0) }}</td>
            <td class="p-4 font-bold text-white">{{ formatVnd(c.minSpend) }}</td>
            <td class="p-4 text-neutral-300">{{ c.usedCount }} / {{ c.usageLimit }}</td>
            <td class="p-4 text-neutral-400">{{ c.expiry }}</td>
            <td class="p-4 text-right">
              <button @click="coupons = coupons.filter(x => x.id !== c.id)" class="text-red-400 font-bold hover:underline cursor-pointer">Xóa</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <KvModal v-model="showModal" title="Tạo Mã Giảm Giá Mới" :dark="true">
      <form @submit.prevent="addCoupon" class="flex flex-col gap-3">
        <input v-model="form.code" placeholder="Mã Code (VD: SUMMER2026)" required class="h-10 px-3 bg-black border border-white/15 rounded-lg text-white font-mono uppercase text-xs" />
        <input v-model="form.title" placeholder="Tiêu đề hiển thị..." required class="h-10 px-3 bg-black border border-white/15 rounded-lg text-white text-xs" />
        <div class="grid grid-cols-2 gap-3">
          <input v-model.number="form.discount" type="number" placeholder="Số tiền giảm (VNĐ)..." required class="h-10 px-3 bg-black border border-white/15 rounded-lg text-white text-xs" />
          <input v-model.number="form.minSpend" type="number" placeholder="Đơn tối thiểu..." required class="h-10 px-3 bg-black border border-white/15 rounded-lg text-white text-xs" />
        </div>
        <button type="submit" class="h-11 bg-primary text-black font-extrabold text-xs rounded-xl mt-2">LƯU VOUCHER</button>
      </form>
    </KvModal>
  </div>
</template>
