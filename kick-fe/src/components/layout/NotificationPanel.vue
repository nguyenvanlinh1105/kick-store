<script setup>
import { storeToRefs } from 'pinia'
import { useNotificationStore } from '@/stores/notification'
import KvModal from '@/components/ui/KvModal.vue'
import KvButton from '@/components/ui/KvButton.vue'
import KvEmptyState from '@/components/ui/KvEmptyState.vue'

const store = useNotificationStore()
const { items, panelOpen, unreadCount } = storeToRefs(store)
</script>

<template>
  <KvModal :open="panelOpen" title="Thông báo" size="md" @close="store.closePanel()">
    <div class="mb-md flex items-center justify-between">
      <p class="text-caption-md text-mute-light">{{ unreadCount }} chưa đọc</p>
      <button
        type="button"
        class="text-caption-md text-link-light"
        @click="store.markAllRead()"
      >
        Đánh dấu đã đọc
      </button>
    </div>

    <div v-if="items.length" class="flex flex-col">
      <button
        v-for="n in items"
        :key="n.id"
        type="button"
        class="flex gap-md border-b border-hairline-light py-md text-left active:bg-surface-soft"
        @click="store.markRead(n.id)"
      >
        <span
          class="mt-1 h-2.5 w-2.5 shrink-0 rounded-full"
          :class="n.read ? 'bg-ash-light' : 'bg-primary'"
        />
        <span class="flex-1">
          <span class="block text-heading-md">{{ n.title }}</span>
          <span class="mt-xxs block text-body-sm text-body-light">{{ n.body }}</span>
          <span class="mt-xs block text-caption-sm text-mute-light">{{ n.createdAt }}</span>
        </span>
      </button>
    </div>

    <KvEmptyState v-else title="Chưa có thông báo" description="Khi có cập nhật đơn hàng, bạn sẽ thấy ở đây." />

    <template #footer>
      <KvButton to="/notifications" variant="secondary" block @click="store.closePanel()">
        Xem tất cả
      </KvButton>
    </template>
  </KvModal>
</template>
