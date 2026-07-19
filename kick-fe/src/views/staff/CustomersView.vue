<script setup>
import KvPageHeader from '@/components/ui/KvPageHeader.vue'

const customers = [
  { name: 'Nguyễn Minh An', phone: '0901 234 567', lastOrder: 'KV-1042' },
  { name: 'Trần Bảo', phone: '0912 345 678', lastOrder: 'KV-1045' },
  { name: 'Lê Hà', phone: '0987 654 321', lastOrder: 'KV-1044' },
]
</script>

<template>
  <div>
    <KvPageHeader title="Khách hàng" description="Tra cứu nhanh khi xử lý đơn / hỗ trợ" />
    <div class="overflow-x-auto rounded-md bg-canvas-light">
      <table class="w-full min-w-[480px] text-left text-body-sm">
        <thead>
          <tr class="border-b border-hairline-light text-caption-md text-mute-light">
            <th class="px-lg py-md font-medium">Tên</th>
            <th class="px-lg py-md font-medium">SĐT</th>
            <th class="px-lg py-md font-medium">Đơn gần nhất</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="c in customers" :key="c.phone" class="border-b border-hairline-light">
            <td class="px-lg py-md">{{ c.name }}</td>
            <td class="px-lg py-md">{{ c.phone }}</td>
            <td class="px-lg py-md">{{ c.lastOrder }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
