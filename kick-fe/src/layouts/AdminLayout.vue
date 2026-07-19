<script setup>
import { RouterView, RouterLink } from 'vue-router'
import DashboardTopBar from '@/components/layout/DashboardTopBar.vue'
import DashboardSidebar from '@/components/layout/DashboardSidebar.vue'
import { useAuthStore } from '@/stores/auth'
import { computed } from 'vue'

const auth = useAuthStore()

const links = computed(() => {
  const base = [
    { to: '/admin', label: 'Dashboard' },
    { to: '/admin/orders', label: 'Đơn hàng' },
    { to: '/admin/products', label: 'Sản phẩm' },
    { to: '/admin/inventory', label: 'Kho' },
    { to: '/admin/customers', label: 'Khách hàng' },
    { to: '/admin/reviews', label: 'Đánh giá' },
    { to: '/admin/coupons', label: 'Mã giảm giá' },
  ]
  if (auth.roles.includes('ADMIN')) {
    base.push(
      { to: '/admin/brands', label: 'Thương hiệu' },
      { to: '/admin/categories', label: 'Danh mục' },
      { to: '/admin/users', label: 'Nhân sự' },
    )
  }
  return base
})

const roleLabel = computed(() =>
  auth.roles.includes('ADMIN') ? 'Admin' : 'Quản lý bán hàng',
)
</script>

<template>
  <div class="min-h-screen bg-surface-soft">
    <DashboardTopBar brand="KickVerse Admin" home-to="/admin">
      <template #nav>
        <span class="rounded-full bg-primary/20 px-3 py-1 text-caption-sm text-link-dark">
          {{ roleLabel }}
        </span>
      </template>
      <template #actions>
        <RouterLink to="/" class="text-caption-md text-on-dark-mute active:text-on-dark">
          Xem store
        </RouterLink>
      </template>
    </DashboardTopBar>

    <div class="flex flex-col kv:flex-row">
      <DashboardSidebar title="Quản trị" :links="links" />
      <main class="flex-1 px-lg py-xl">
        <div class="mx-auto max-w-6xl">
          <RouterView />
        </div>
      </main>
    </div>
  </div>
</template>
