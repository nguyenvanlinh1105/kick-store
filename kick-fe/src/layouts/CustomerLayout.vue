<script setup>
import { RouterView } from 'vue-router'
import PublicNav from '@/components/layout/PublicNav.vue'
import AppFooter from '@/components/layout/AppFooter.vue'
import CartDrawer from '@/components/layout/CartDrawer.vue'
import NotificationPanel from '@/components/layout/NotificationPanel.vue'
import DashboardSidebar from '@/components/layout/DashboardSidebar.vue'
import { useResponsive } from '@/composables/useResponsive'

const { isMobile } = useResponsive()

const links = [
  { to: '/account', label: 'Tổng quan' },
  { to: '/account/orders', label: 'Đơn hàng' },
  { to: '/account/profile', label: 'Hồ sơ' },
  { to: '/account/addresses', label: 'Địa chỉ' },
  { to: '/wishlist', label: 'Wishlist' },
  { to: '/notifications', label: 'Thông báo' },
]
</script>

<template>
  <div class="flex min-h-screen flex-col bg-canvas-light">
    <PublicNav />
    <div class="flex flex-1 flex-col kv:flex-row">
      <DashboardSidebar title="Tài khoản" :links="links" />
      <main class="flex-1 px-lg py-xl">
        <div class="mx-auto max-w-4xl">
          <RouterView />
        </div>
      </main>
    </div>
    <AppFooter v-if="!isMobile" />
    <CartDrawer />
    <NotificationPanel />
  </div>
</template>
