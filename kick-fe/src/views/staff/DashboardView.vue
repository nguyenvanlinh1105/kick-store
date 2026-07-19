<script setup>
import KvCard from '@/components/ui/KvCard.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'
import KvBadge from '@/components/ui/KvBadge.vue'

const stats = [
  { label: 'Đơn chờ xử lý', value: '8' },
  { label: 'Đang chuẩn bị', value: '5' },
  { label: 'Cần xuất kho', value: '3' },
  { label: 'Chat mở', value: '2' },
]

const queue = [
  { id: 'KV-1045', task: 'Xác nhận đơn', priority: 'High' },
  { id: 'KV-1044', task: 'In phiếu giao', priority: 'Medium' },
  { id: 'KV-1041', task: 'Điều chỉnh tồn', priority: 'Low' },
]
</script>

<template>
  <div>
    <KvPageHeader title="Staff Dashboard" description="Hàng đợi vận hành trong ngày" />

    <div class="grid gap-md sm:grid-cols-2 lg:grid-cols-4">
      <KvCard v-for="s in stats" :key="s.label">
        <p class="text-caption-md text-mute-light">{{ s.label }}</p>
        <p class="mt-sm text-display-md">{{ s.value }}</p>
      </KvCard>
    </div>

    <section class="mt-xl rounded-md bg-canvas-light p-lg">
      <h2 class="mb-md text-heading-md">Việc cần làm</h2>
      <ul class="flex flex-col gap-sm">
        <li
          v-for="q in queue"
          :key="q.id"
          class="flex items-center justify-between border-b border-hairline-light py-md"
        >
          <div>
            <p class="text-body-sm font-medium">{{ q.id }}</p>
            <p class="text-caption-md text-mute-light">{{ q.task }}</p>
          </div>
          <KvBadge :variant="q.priority === 'High' ? 'commerce' : 'soft'">{{
            q.priority
          }}</KvBadge>
        </li>
      </ul>
    </section>
  </div>
</template>
