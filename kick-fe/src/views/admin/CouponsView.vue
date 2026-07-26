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
        <h1 class="text-2xl font-extrabold text-slate-900">Quản Lý Mã Giảm Giá (Coupons)</h1>
        <p class="text-xs text-slate-500 mt-1 font-medium">Tạo và cấu hình voucher khuyến mãi toàn sàn</p>
      </div>

      <button @click="showModal = true" class="px-5 py-2.5 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 cursor-pointer shadow transition-all">
        + Tạo Mã Giảm Giá Mới
      </button>
    </div>

    <!-- PURE WHITE LIGHT COUPONS TABLE -->
    <div class="bg-white border border-slate-200 rounded-3xl overflow-hidden shadow-sm">
      <table class="w-full text-left text-xs text-slate-700">
        <thead class="bg-slate-900 text-white font-bold uppercase border-b border-slate-200">
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
        <tbody class="divide-y divide-slate-100">
          <tr v-for="c in coupons" :key="c.id" class="hover:bg-slate-50 transition-colors">
            <td class="p-4 font-mono font-extrabold text-amber-600">{{ c.code }}</td>
            <td class="p-4 font-extrabold text-slate-900">{{ c.title }}</td>
            <td class="p-4 font-extrabold text-emerald-600">{{ formatVnd(c.discount || 0) }}</td>
            <td class="p-4 font-extrabold text-slate-900">{{ formatVnd(c.minSpend) }}</td>
            <td class="p-4 text-slate-600 font-bold">{{ c.usedCount }} / {{ c.usageLimit }}</td>
            <td class="p-4 text-slate-500 font-bold">{{ c.expiry }}</td>
            <td class="p-4 text-right">
              <button @click="coupons = coupons.filter(x => x.id !== c.id)" class="text-red-500 font-extrabold hover:underline cursor-pointer">Xóa</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <KvModal v-model="showModal" title="Tạo Mã Giảm Giá Mới" :dark="false">
      <form @submit.prevent="addCoupon" class="flex flex-col gap-3">
        <input v-model="form.code" placeholder="Mã Code (VD: SUMMER2026)" required class="h-10 px-3 bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-mono uppercase text-xs font-bold" />
        <input v-model="form.title" placeholder="Tiêu đề hiển thị..." required class="h-10 px-3 bg-slate-50 border border-slate-200 rounded-xl text-slate-900 text-xs font-medium" />
        <div class="grid grid-cols-2 gap-3">
          <input v-model.number="form.discount" type="number" placeholder="Số tiền giảm (VNĐ)..." required class="h-10 px-3 bg-slate-50 border border-slate-200 rounded-xl text-slate-900 text-xs font-medium" />
          <input v-model.number="form.minSpend" type="number" placeholder="Đơn tối thiểu..." required class="h-10 px-3 bg-slate-50 border border-slate-200 rounded-xl text-slate-900 text-xs font-medium" />
        </div>
        <button type="submit" class="h-11 bg-slate-900 text-white font-extrabold text-xs rounded-xl mt-2 hover:bg-amber-600 shadow">LƯU VOUCHER</button>
      </form>
    </KvModal>
  </div>
</template>
