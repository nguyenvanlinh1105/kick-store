<script setup>
import { RouterLink } from 'vue-router'
import { storeToRefs } from 'pinia'
import { useAuthStore } from '@/stores/auth'
import KvAvatar from '@/components/ui/KvAvatar.vue'
import KvButton from '@/components/ui/KvButton.vue'

defineProps({
  brand: { type: String, default: 'KickVerse' },
  homeTo: { type: String, default: '/' },
})

const auth = useAuthStore()
const { user } = storeToRefs(auth)
</script>

<template>
  <header class="sticky top-0 z-30 flex h-12 items-center justify-between border-b border-hairline-light bg-canvas-dark px-lg text-on-dark">
    <div class="flex items-center gap-md">
      <RouterLink :to="homeTo" class="text-body-strong">{{ brand }}</RouterLink>
      <slot name="nav" />
    </div>
    <div class="flex items-center gap-sm">
      <slot name="actions" />
      <div v-if="user" class="flex items-center gap-sm">
        <KvAvatar :name="user.fullName" size="sm" />
        <span class="hidden text-caption-md kv:inline">{{ user.fullName }}</span>
        <KvButton size="sm" variant="secondary-dark" @click="auth.logout()">Đăng xuất</KvButton>
      </div>
    </div>
  </header>
</template>
