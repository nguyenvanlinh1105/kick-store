<script setup>
import { ref } from 'vue'
import staffJson from '@/data/json/staff.json'

const staffList = ref([...staffJson])

const availableModules = [
  { key: 'PRODUCTS', label: 'Sản phẩm & Danh mục' },
  { key: 'ORDERS', label: 'Xử lý Đơn hàng' },
  { key: 'INVENTORY', label: 'Nhập kho & Quản lý tồn' },
  { key: 'PROMOTIONS', label: 'Khuyến mãi & Voucher' },
  { key: 'CUSTOMERS', label: 'Khách hàng (CRM)' },
  { key: 'STAFF', label: 'Nhân sự & Phân quyền' },
  { key: 'SETTINGS', label: 'Cấu hình Hệ thống' },
]

function togglePermission(staff, moduleKey) {
  if (staff.role === 'SUPER_ADMIN') return
  const idx = staff.permissions.indexOf(moduleKey)
  if (idx > -1) {
    staff.permissions.splice(idx, 1)
  } else {
    staff.permissions.push(moduleKey)
  }
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-extrabold text-slate-900">Quản Lý Nhân Viên & Ma Trận Phân Quyền RBAC</h1>
        <p class="text-xs text-slate-500 mt-1 font-medium">Thiết lập quyền truy cập theo từng mô-đun chức năng cho tài khoản nội bộ</p>
      </div>

      <button class="px-5 py-2.5 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 transition-all cursor-pointer shadow">
        + Thêm Nhân Viên Mới
      </button>
    </div>

    <!-- PURE WHITE LIGHT RBAC PERMISSIONS TABLE -->
    <div class="bg-white border border-slate-200 rounded-3xl overflow-hidden shadow-sm">
      <table class="w-full text-left text-xs text-slate-700">
        <thead class="bg-slate-900 text-white font-bold uppercase border-b border-slate-200">
          <tr>
            <th class="p-4">Nhân Viên</th>
            <th class="p-4">Chức Danh / Vai Trò</th>
            <th v-for="m in availableModules" :key="m.key" class="p-4 text-center">{{ m.label }}</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-slate-100">
          <tr v-for="st in staffList" :key="st.id" class="hover:bg-slate-50 transition-colors">
            <td class="p-4">
              <span class="font-extrabold text-slate-900 block">{{ st.fullName }}</span>
              <span class="text-[10px] text-slate-400 font-mono font-bold">{{ st.email }}</span>
            </td>
            <td class="p-4 font-extrabold text-amber-600">{{ st.roleName }}</td>
            <td v-for="m in availableModules" :key="m.key" class="p-4 text-center">
              <input
                type="checkbox"
                :checked="st.permissions.includes(m.key)"
                :disabled="st.role === 'SUPER_ADMIN'"
                @change="togglePermission(st, m.key)"
                class="accent-amber-600 w-4 h-4 rounded cursor-pointer disabled:opacity-50"
              />
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
