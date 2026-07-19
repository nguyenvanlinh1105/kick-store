<script setup>
import KvBadge from '@/components/ui/KvBadge.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'

const customers = [
  { name: 'Nguyễn Minh An', email: 'an@email.com', orders: 5, spent: '12,4tr', status: 'ACTIVE' },
  { name: 'Trần Bảo', email: 'bao@email.com', orders: 2, spent: '5,1tr', status: 'ACTIVE' },
  { name: 'Lê Hà', email: 'ha@email.com', orders: 1, spent: '2,9tr', status: 'BLOCKED' },
]
</script>

<template>
  <div>
    <KvPageHeader title="Khách hàng" description="Danh sách tài khoản CUSTOMER" />
    <div class="overflow-x-auto rounded-md bg-canvas-light">
      <table class="w-full min-w-[640px] text-left text-body-sm">
        <thead>
          <tr class="border-b border-hairline-light text-caption-md text-mute-light">
            <th class="px-lg py-md font-medium">Tên</th>
            <th class="px-lg py-md font-medium">Email</th>
            <th class="px-lg py-md font-medium">Đơn</th>
            <th class="px-lg py-md font-medium">Chi tiêu</th>
            <th class="px-lg py-md font-medium">Status</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="c in customers" :key="c.email" class="border-b border-hairline-light">
            <td class="px-lg py-md">{{ c.name }}</td>
            <td class="px-lg py-md">{{ c.email }}</td>
            <td class="px-lg py-md">{{ c.orders }}</td>
            <td class="px-lg py-md">{{ c.spent }}</td>
            <td class="px-lg py-md">
              <KvBadge :variant="c.status === 'ACTIVE' ? 'info' : 'commerce'">{{
                c.status
              }}</KvBadge>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
