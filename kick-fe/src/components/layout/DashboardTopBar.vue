<script setup>
import { RouterLink } from 'vue-router'
import { storeToRefs } from 'pinia'
import { useAuthStore } from '@/stores/auth'
import KvAvatar from '@/components/ui/KvAvatar.vue'

defineProps({
  brand: { type: String, default: 'KickVerse' },
  homeTo: { type: String, default: '/' },
})

const auth = useAuthStore()
const { user } = storeToRefs(auth)
</script>

<template>
  <header class="sticky top-0 z-40 flex h-14 items-center justify-between border-b border-slate-800 bg-slate-900 px-6 text-white shadow-md">
    <div class="flex items-center gap-4">
      <RouterLink :to="homeTo" class="font-display text-xl tracking-wider text-white no-underline hover:text-amber-400">
        {{ brand }}
      </RouterLink>
      <slot name="nav" />
    </div>

    <div class="flex items-center gap-4 text-xs">
      <slot name="actions" />
      <div v-if="user" class="flex items-center gap-3">
        <KvAvatar :name="user.fullName" size="sm" />
        <span class="hidden font-bold text-slate-200 md:inline">{{ user.fullName }}</span>
        <button
          type="button"
          class="px-3 py-1.5 bg-white/10 hover:bg-red-600 border border-white/15 text-white font-bold text-xs rounded-lg transition-colors cursor-pointer"
          @click="auth.logout()"
        >
          Đăng xuất
        </button>
      </div>
    </div>
  </header>
</template>
