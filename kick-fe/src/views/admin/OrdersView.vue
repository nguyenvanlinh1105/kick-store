<script setup>
import { ref } from 'vue'
import KvBadge from '@/components/ui/KvBadge.vue'
import KvButton from '@/components/ui/KvButton.vue'
import KvFilterPill from '@/components/ui/KvFilterPill.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'

const status = ref('ALL')
const filters = ['ALL', 'PENDING', 'CONFIRMED', 'SHIPPING', 'DELIVERED', 'CANCELLED']

const orders = [
  { id: 'KV-1045', customer: 'Trần Bảo', total: '3.590.000đ', status: 'PENDING', date: '19/07' },
  { id: 'KV-1044', customer: 'Lê Hà', total: '2.890.000đ', status: 'CONFIRMED', date: '19/07' },
  { id: 'KV-1043', customer: 'Phạm Huy', total: '4.290.000đ', status: 'SHIPPING', date: '18/07' },
  { id: 'KV-1042', customer: 'Nguyễn An', total: '5.780.000đ', status: 'SHIPPING', date: '18/07' },
  { id: 'KV-1038', customer: 'Võ Mai', total: '2.490.000đ', status: 'DELIVERED', date: '10/07' },
]
</script>

<template>
  <div>
    <KvPageHeader title="Đơn hàng" description="Theo dõi và cập nhật trạng thái đơn" />

    <div class="mb-lg flex flex-wrap gap-sm">
      <KvFilterPill
        v-for="f in filters"
        :key="f"
        :active="status === f"
        @click="status = f"
      >
        {{ f }}
      </KvFilterPill>
    </div>

    <div class="overflow-x-auto rounded-md bg-canvas-light">
      <table class="w-full min-w-[680px] text-left text-body-sm">
        <thead>
          <tr class="border-b border-hairline-light text-caption-md text-mute-light">
            <th class="px-lg py-md font-medium">Mã</th>
            <th class="px-lg py-md font-medium">Khách</th>
            <th class="px-lg py-md font-medium">Ngày</th>
            <th class="px-lg py-md font-medium">Tổng</th>
            <th class="px-lg py-md font-medium">Trạng thái</th>
            <th class="px-lg py-md font-medium" />
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="o in orders.filter((x) => status === 'ALL' || x.status === status)"
            :key="o.id"
            class="border-b border-hairline-light"
          >
            <td class="px-lg py-md font-medium">{{ o.id }}</td>
            <td class="px-lg py-md">{{ o.customer }}</td>
            <td class="px-lg py-md">{{ o.date }}</td>
            <td class="px-lg py-md">{{ o.total }}</td>
            <td class="px-lg py-md">
              <KvBadge variant="info">{{ o.status }}</KvBadge>
            </td>
            <td class="px-lg py-md text-right">
              <KvButton size="sm" variant="secondary">Chi tiết</KvButton>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
