<script setup>
import { ref } from 'vue'
import KvButton from '@/components/ui/KvButton.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'
import { useResponsive } from '@/composables/useResponsive'

const { isMobile } = useResponsive()

const conversations = [
  { id: 1, name: 'Nguyễn An', preview: 'Đơn KV-1042 giao khi nào?', unread: true },
  { id: 2, name: 'Trần Bảo', preview: 'Muốn đổi size 42 → 43', unread: false },
]

const active = ref(conversations[0])
const draft = ref('')
const messages = ref([
  { from: 'customer', text: 'Đơn KV-1042 giao khi nào ạ?' },
  { from: 'staff', text: 'Đơn đang shipping, dự kiến giao trong hôm nay.' },
])

function send() {
  if (!draft.value.trim()) return
  messages.value.push({ from: 'staff', text: draft.value.trim() })
  draft.value = ''
}
</script>

<template>
  <div>
    <KvPageHeader title="Chat hỗ trợ" description="Conversation OPEN / CLOSED" />

    <div
      class="overflow-hidden rounded-md border border-hairline-light bg-canvas-light"
      :class="isMobile ? 'flex flex-col' : 'grid grid-cols-[280px_1fr] min-h-[480px]'"
    >
      <aside class="border-b border-hairline-light kv:border-b-0 kv:border-r">
        <button
          v-for="c in conversations"
          :key="c.id"
          type="button"
          class="flex w-full flex-col border-b border-hairline-light px-md py-md text-left"
          :class="active?.id === c.id ? 'bg-surface-card' : 'active:bg-surface-soft'"
          @click="active = c"
        >
          <span class="flex items-center justify-between">
            <span class="text-heading-md">{{ c.name }}</span>
            <span v-if="c.unread" class="h-2 w-2 rounded-full bg-primary" />
          </span>
          <span class="mt-xxs truncate text-caption-md text-mute-light">{{ c.preview }}</span>
        </button>
      </aside>

      <div class="flex min-h-[360px] flex-col">
        <div class="border-b border-hairline-light px-lg py-md">
          <p class="text-heading-md">{{ active?.name }}</p>
        </div>
        <div class="flex flex-1 flex-col gap-sm overflow-y-auto p-lg">
          <div
            v-for="(m, i) in messages"
            :key="i"
            class="max-w-[80%] rounded-md px-md py-sm text-body-sm"
            :class="
              m.from === 'staff'
                ? 'ml-auto bg-primary text-on-primary'
                : 'bg-surface-soft text-ink'
            "
          >
            {{ m.text }}
          </div>
        </div>
        <form class="flex gap-sm border-t border-hairline-light p-md" @submit.prevent="send">
          <input
            v-model="draft"
            type="text"
            placeholder="Nhập tin nhắn..."
            class="h-12 flex-1 rounded-sm border border-ash-light px-md outline-none focus:border-2 focus:border-primary"
          />
          <KvButton type="submit" variant="primary">Gửi</KvButton>
        </form>
      </div>
    </div>
  </div>
</template>
