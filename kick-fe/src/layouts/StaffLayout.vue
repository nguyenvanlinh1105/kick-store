<script setup>
import { RouterView, RouterLink } from 'vue-router'
import DashboardTopBar from '@/components/layout/DashboardTopBar.vue'
import DashboardSidebar from '@/components/layout/DashboardSidebar.vue'

const links = [
  { to: '/staff', label: 'Dashboard' },
  { to: '/staff/orders', label: 'Xử lý đơn' },
  { to: '/staff/inventory', label: 'Kiểm kho' },
  { to: '/staff/customers', label: 'Khách hàng' },
  { to: '/staff/support', label: 'Chat hỗ trợ' },
]
</script>

<template>
  <div class="min-h-screen bg-surface-soft">
    <DashboardTopBar brand="KickVerse Staff" home-to="/staff">
      <template #nav>
        <span class="rounded-full bg-commerce/30 px-3 py-1 text-caption-sm text-on-dark">
          Nhân viên
        </span>
      </template>
      <template #actions>
        <RouterLink to="/" class="text-caption-md text-on-dark-mute active:text-on-dark">
          Xem store
        </RouterLink>
      </template>
    </DashboardTopBar>

    <div class="flex flex-col kv:flex-row">
      <DashboardSidebar title="Vận hành" :links="links" />
      <main class="flex-1 px-lg py-xl">
        <div class="mx-auto max-w-6xl">
          <RouterView />
        </div>
      </main>
    </div>
  </div>
</template>
