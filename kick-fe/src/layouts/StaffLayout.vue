<script setup>
import { RouterView, RouterLink } from 'vue-router'
import DashboardTopBar from '@/components/layout/DashboardTopBar.vue'
import DashboardSidebar from '@/components/layout/DashboardSidebar.vue'

const links = [
  { to: '/staff/pos', label: '🛒 POS Bán Hàng Tại Quầy' },
  { to: '/staff/support', label: '💬 Chat Tư Vấn Khách Hàng' },
  { to: '/admin', label: '⚙️ Trang Quản Trị Admin' },
]
</script>

<template>
  <div class="min-h-screen bg-slate-50 text-slate-900">
    <DashboardTopBar brand="KICKVERSE STAFF" home-to="/staff/pos">
      <template #nav>
        <span class="rounded-full bg-amber-500/20 border border-amber-500/40 px-3 py-1 text-xs font-extrabold text-amber-400 uppercase">
          Cổng Nhân Viên POS & CSKH
        </span>
      </template>
      <template #actions>
        <RouterLink to="/" class="text-xs font-bold text-slate-300 hover:text-white no-underline">
          🌐 Xem Store Cửa Hàng
        </RouterLink>
      </template>
    </DashboardTopBar>

    <div class="flex flex-col md:flex-row">
      <DashboardSidebar title="Mô-đun Nhân Viên" :links="links" />
      <main class="flex-1 p-6">
        <div class="mx-auto max-w-7xl">
          <RouterView />
        </div>
      </main>
    </div>
  </div>
</template>
