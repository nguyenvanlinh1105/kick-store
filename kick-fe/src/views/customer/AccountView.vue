<script setup>
import { storeToRefs } from 'pinia'
import { RouterLink } from 'vue-router'
import KvButton from '@/components/ui/KvButton.vue'
import KvCard from '@/components/ui/KvCard.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'
import KvAvatar from '@/components/ui/KvAvatar.vue'
import { useAuthStore } from '@/stores/auth'
import { useCartStore } from '@/stores/cart'
import { useWishlistStore } from '@/stores/wishlist'
import { useNotificationStore } from '@/stores/notification'

const auth = useAuthStore()
const cart = useCartStore()
const wishlist = useWishlistStore()
const notifications = useNotificationStore()
const { user } = storeToRefs(auth)
const { count: cartCount } = storeToRefs(cart)
const { count: wishCount } = storeToRefs(wishlist)
const { unreadCount } = storeToRefs(notifications)

const tiles = [
  { to: '/account/orders', label: 'Đơn hàng', value: '3', hint: '1 đang giao' },
  { to: '/cart', label: 'Giỏ hàng', value: String(cartCount), hint: 'sản phẩm' },
  { to: '/wishlist', label: 'Wishlist', value: String(wishCount), hint: 'đã lưu' },
  { to: '/notifications', label: 'Thông báo', value: String(unreadCount), hint: 'chưa đọc' },
]
</script>

<template>
  <div>
    <KvPageHeader title="Tài khoản" description="Quản lý đơn, hồ sơ và ưu đãi của bạn" />

    <div v-if="user" class="mb-xl flex items-center gap-md rounded-md bg-surface-card p-lg">
      <KvAvatar :name="user.fullName" size="lg" />
      <div>
        <p class="text-heading-md">{{ user.fullName }}</p>
        <p class="text-body-sm text-mute-light">{{ user.email }}</p>
      </div>
      <KvButton to="/account/profile" size="md" variant="secondary" class="ml-auto">
        Chỉnh sửa
      </KvButton>
    </div>

    <div v-else class="mb-xl rounded-md bg-surface-card p-lg">
      <p class="text-body-md">Bạn chưa đăng nhập.</p>
      <div class="mt-md flex gap-sm">
        <KvButton to="/login" variant="primary">Đăng nhập</KvButton>
        <KvButton to="/register" variant="secondary">Đăng ký</KvButton>
      </div>
    </div>

    <div class="grid gap-md sm:grid-cols-2">
      <RouterLink v-for="t in tiles" :key="t.to" :to="t.to">
        <KvCard class="h-full active:opacity-90">
          <p class="text-caption-md text-mute-light">{{ t.label }}</p>
          <p class="mt-sm text-display-md">{{ t.value }}</p>
          <p class="mt-xxs text-caption-md text-body-light">{{ t.hint }}</p>
        </KvCard>
      </RouterLink>
    </div>
  </div>
</template>
