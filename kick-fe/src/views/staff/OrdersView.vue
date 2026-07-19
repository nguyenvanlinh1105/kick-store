<script setup>
import { ref } from 'vue'
import KvBadge from '@/components/ui/KvBadge.vue'
import KvButton from '@/components/ui/KvButton.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'

const orders = ref([
  { id: 'KV-1045', customer: 'Trần Bảo', status: 'PENDING', items: 2 },
  { id: 'KV-1044', customer: 'Lê Hà', status: 'CONFIRMED', items: 1 },
  { id: 'KV-1043', customer: 'Phạm Huy', status: 'PREPARING', items: 3 },
])

const nextStatus = {
  PENDING: 'CONFIRMED',
  CONFIRMED: 'PREPARING',
  PREPARING: 'SHIPPING',
  SHIPPING: 'DELIVERED',
}

function advance(o) {
  const next = nextStatus[o.status]
  if (next) o.status = next
}
</script>

<template>
  <div>
    <KvPageHeader title="Xử lý đơn" description="Cập nhật trạng thái theo pipeline kho" />

    <div class="flex flex-col gap-md">
      <article
        v-for="o in orders"
        :key="o.id"
        class="flex flex-wrap items-center justify-between gap-md rounded-md bg-canvas-light p-lg"
      >
        <div>
          <p class="text-heading-md">{{ o.id }}</p>
          <p class="text-caption-md text-mute-light">
            {{ o.customer }} · {{ o.items }} SP
          </p>
        </div>
        <div class="flex items-center gap-sm">
          <KvBadge variant="info">{{ o.status }}</KvBadge>
          <KvButton
            v-if="nextStatus[o.status]"
            size="md"
            variant="commerce"
            @click="advance(o)"
          >
            → {{ nextStatus[o.status] }}
          </KvButton>
        </div>
      </article>
    </div>
  </div>
</template>
