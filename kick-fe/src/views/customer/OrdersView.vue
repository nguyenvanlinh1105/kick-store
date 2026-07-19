<script setup>
import { RouterLink } from 'vue-router'
import KvBadge from '@/components/ui/KvBadge.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'

const orders = [
  {
    id: 'KV-1042',
    status: 'SHIPPING',
    statusLabel: 'Đang giao',
    date: '18/07/2026',
    total: '5.780.000đ',
    items: 2,
  },
  {
    id: 'KV-1038',
    status: 'DELIVERED',
    statusLabel: 'Đã giao',
    date: '10/07/2026',
    total: '2.490.000đ',
    items: 1,
  },
  {
    id: 'KV-1021',
    status: 'CANCELLED',
    statusLabel: 'Đã hủy',
    date: '02/07/2026',
    total: '1.890.000đ',
    items: 1,
  },
]

const badgeVariant = {
  SHIPPING: 'info',
  DELIVERED: 'soft',
  CANCELLED: 'commerce',
  PENDING: 'dark',
}
</script>

<template>
  <div>
    <KvPageHeader title="Đơn hàng" description="Lịch sử mua hàng của bạn" />

    <div class="flex flex-col gap-md">
      <RouterLink
        v-for="o in orders"
        :key="o.id"
        :to="`/account/orders/${o.id}`"
        class="flex flex-wrap items-center justify-between gap-md rounded-md bg-surface-card p-lg active:opacity-90"
      >
        <div>
          <p class="text-heading-md">#{{ o.id }}</p>
          <p class="mt-xxs text-caption-md text-mute-light">
            {{ o.date }} · {{ o.items }} sản phẩm
          </p>
        </div>
        <div class="flex items-center gap-md">
          <KvBadge :variant="badgeVariant[o.status] || 'soft'">{{ o.statusLabel }}</KvBadge>
          <p class="text-body-strong">{{ o.total }}</p>
        </div>
      </RouterLink>
    </div>
  </div>
</template>
