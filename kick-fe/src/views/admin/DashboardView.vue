<script setup>
import KvCard from '@/components/ui/KvCard.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'
import KvBadge from '@/components/ui/KvBadge.vue'
import { useAuthStore } from '@/stores/auth'
import { computed } from 'vue'

const auth = useAuthStore()
const isFullAdmin = computed(() => auth.roles.includes('ADMIN'))

const stats = [
  { label: 'Doanh thu hôm nay', value: '48,2tr', hint: '+12% so với hôm qua' },
  { label: 'Đơn mới', value: '36', hint: '8 cần xử lý' },
  { label: 'Tồn kho thấp', value: '14', hint: 'SKU < 5' },
  { label: 'Khách mới', value: '22', hint: '7 ngày qua' },
]

const recent = [
  { id: 'KV-1045', customer: 'Trần Bảo', total: '3.590.000đ', status: 'PENDING' },
  { id: 'KV-1044', customer: 'Lê Hà', total: '2.890.000đ', status: 'CONFIRMED' },
  { id: 'KV-1043', customer: 'Phạm Huy', total: '4.290.000đ', status: 'SHIPPING' },
]
</script>

<template>
  <div>
    <KvPageHeader
      :title="isFullAdmin ? 'Admin Dashboard' : 'Dashboard bán hàng'"
      description="Tổng quan vận hành KickVerse"
    />

    <div class="grid gap-md sm:grid-cols-2 lg:grid-cols-4">
      <KvCard v-for="s in stats" :key="s.label">
        <p class="text-caption-md text-mute-light">{{ s.label }}</p>
        <p class="mt-sm text-display-md">{{ s.value }}</p>
        <p class="mt-xxs text-caption-sm text-body-light">{{ s.hint }}</p>
      </KvCard>
    </div>

    <section class="mt-xl rounded-md bg-canvas-light p-lg">
      <h2 class="mb-md text-heading-md">Đơn gần đây</h2>
      <div class="overflow-x-auto">
        <table class="w-full min-w-[560px] text-left text-body-sm">
          <thead>
            <tr class="border-b border-hairline-light text-caption-md text-mute-light">
              <th class="pb-sm font-medium">Mã</th>
              <th class="pb-sm font-medium">Khách</th>
              <th class="pb-sm font-medium">Tổng</th>
              <th class="pb-sm font-medium">Trạng thái</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="o in recent"
              :key="o.id"
              class="border-b border-hairline-light"
            >
              <td class="py-md">{{ o.id }}</td>
              <td class="py-md">{{ o.customer }}</td>
              <td class="py-md">{{ o.total }}</td>
              <td class="py-md">
                <KvBadge variant="info">{{ o.status }}</KvBadge>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
  </div>
</template>
