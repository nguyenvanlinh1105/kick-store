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
  if (staff.role === 'SUPER_ADMIN') return // Super Admin has all
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
        <h1 class="text-2xl font-extrabold text-white">Quản Lý Nhân Viên & Ma Trận Phân Quyền RBAC</h1>
        <p class="text-xs text-neutral-400 mt-1">Thiết lập quyền truy cập theo từng mô-đun chức năng cho tài khoản nội bộ</p>
      </div>

      <button class="px-5 py-2.5 bg-primary text-black font-extrabold text-xs rounded-xl hover:bg-primary-hover cursor-pointer">
        + Thêm Nhân Viên Mới
      </button>
    </div>

    <!-- RBAC Permissions Matrix Table -->
    <div class="bg-neutral-900 border border-white/10 rounded-2xl overflow-hidden">
      <table class="w-full text-left text-xs text-neutral-300">
        <thead class="bg-black text-white font-bold uppercase border-b border-white/10">
          <tr>
            <th class="p-4">Nhân Viên</th>
            <th class="p-4">Chức Danh / Vai Trò</th>
            <th v-for="m in availableModules" :key="m.key" class="p-4 text-center">{{ m.label }}</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-white/5">
          <tr v-for="st in staffList" :key="st.id" class="hover:bg-white/5">
            <td class="p-4">
              <span class="font-bold text-white block">{{ st.fullName }}</span>
              <span class="text-[10px] text-neutral-400 font-mono">{{ st.email }}</span>
            </td>
            <td class="p-4 font-bold text-primary">{{ st.roleName }}</td>
            <td v-for="m in availableModules" :key="m.key" class="p-4 text-center">
              <input
                type="checkbox"
                :checked="st.permissions.includes(m.key)"
                :disabled="st.role === 'SUPER_ADMIN'"
                @change="togglePermission(st, m.key)"
                class="accent-primary w-4 h-4 rounded cursor-pointer disabled:opacity-50"
              />
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
