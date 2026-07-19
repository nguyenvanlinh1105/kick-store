<script setup>
import KvBadge from '@/components/ui/KvBadge.vue'
import KvButton from '@/components/ui/KvButton.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'

const users = [
  { name: 'Admin KickVerse', email: 'admin@kickverse.vn', role: 'ADMIN', status: 'ACTIVE' },
  {
    name: 'Quản lý bán hàng',
    email: 'manager@kickverse.vn',
    role: 'SALES_MANAGER',
    status: 'ACTIVE',
  },
  { name: 'Nhân viên kho', email: 'staff@kickverse.vn', role: 'STAFF', status: 'ACTIVE' },
]
</script>

<template>
  <div>
    <KvPageHeader title="Nhân sự" description="Phân quyền ADMIN / SALES_MANAGER / STAFF">
      <KvButton size="md" variant="primary">Mời thành viên</KvButton>
    </KvPageHeader>
    <div class="overflow-x-auto rounded-md bg-canvas-light">
      <table class="w-full min-w-[560px] text-left text-body-sm">
        <thead>
          <tr class="border-b border-hairline-light text-caption-md text-mute-light">
            <th class="px-lg py-md font-medium">Tên</th>
            <th class="px-lg py-md font-medium">Email</th>
            <th class="px-lg py-md font-medium">Role</th>
            <th class="px-lg py-md font-medium">Status</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="u in users" :key="u.email" class="border-b border-hairline-light">
            <td class="px-lg py-md">{{ u.name }}</td>
            <td class="px-lg py-md">{{ u.email }}</td>
            <td class="px-lg py-md"><KvBadge variant="info">{{ u.role }}</KvBadge></td>
            <td class="px-lg py-md">{{ u.status }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
