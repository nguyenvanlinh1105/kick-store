<script setup>
import { storeToRefs } from 'pinia'
import KvButton from '@/components/ui/KvButton.vue'
import KvEmptyState from '@/components/ui/KvEmptyState.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'
import KvBadge from '@/components/ui/KvBadge.vue'
import { useNotificationStore } from '@/stores/notification'

const store = useNotificationStore()
const { items, unreadCount } = storeToRefs(store)

const typeLabel = {
  ORDER_STATUS: 'Đơn hàng',
  PROMOTION: 'Khuyến mãi',
  SYSTEM: 'Hệ thống',
}
</script>

<template>
  <div>
    <KvPageHeader title="Thông báo" :description="`${unreadCount} chưa đọc`">
      <KvButton size="md" variant="secondary" @click="store.markAllRead()">
        Đánh dấu đã đọc
      </KvButton>
    </KvPageHeader>

    <div v-if="items.length" class="flex flex-col rounded-md bg-canvas-light">
      <article
        v-for="n in items"
        :key="n.id"
        class="flex gap-md border-b border-hairline-light py-md"
        :class="!n.read && 'bg-surface-card/50'"
      >
        <button type="button" class="flex flex-1 gap-md text-left" @click="store.markRead(n.id)">
          <span
            class="mt-2 h-2.5 w-2.5 shrink-0 rounded-full"
            :class="n.read ? 'bg-ash-light' : 'bg-primary'"
          />
          <span>
            <span class="mb-xxs flex flex-wrap items-center gap-sm">
              <KvBadge variant="soft">{{ typeLabel[n.type] || n.type }}</KvBadge>
              <span class="text-caption-sm text-mute-light">{{ n.createdAt }}</span>
            </span>
            <span class="block text-heading-md">{{ n.title }}</span>
            <span class="mt-xxs block text-body-sm text-body-light">{{ n.body }}</span>
          </span>
        </button>
        <button
          type="button"
          class="self-start text-caption-md text-mute-light"
          @click="store.remove(n.id)"
        >
          Xóa
        </button>
      </article>
    </div>

    <KvEmptyState v-else title="Không có thông báo" description="Bạn đã xem hết mọi cập nhật." />
  </div>
</template>
