<script setup>
import { RouterView, RouterLink, useRoute } from 'vue-router'
import PublicNav from '@/components/layout/PublicNav.vue'
import AppFooter from '@/components/layout/AppFooter.vue'
import CartDrawer from '@/components/layout/CartDrawer.vue'
import NotificationPanel from '@/components/layout/NotificationPanel.vue'
import customersJson from '@/data/json/customers.json'

const route = useRoute()
const currentUser = customersJson[0] // Mock logged-in user

const menuLinks = [
  { to: '/account', label: '📊 Tổng Quan Tài Khoản', exact: true },
  { to: '/account/orders', label: '📦 Lịch Sử Đơn Hàng' },
  { to: '/account/profile', label: '👤 Hồ Sơ Cá Nhân' },
  { to: '/account/addresses', label: '📍 Sổ Địa Chỉ' },
  { to: '/wishlist', label: '❤️ Danh Sách Yêu Thích' },
  { to: '/account/vouchers', label: '🎟️ Ví Voucher & Điểm' },
  { to: '/notifications', label: '🔔 Thông Báo' },
]
</script>

<template>
  <div class="flex min-h-screen flex-col bg-slate-50 text-slate-900">
    <PublicNav />

    <div class="kv-container py-10 flex-1">
      <div class="grid grid-cols-1 md:grid-cols-4 gap-8 items-start">
        <!-- ACCOUNT SIDEBAR -->
        <aside class="md:col-span-1 bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-6 shadow-sm">
          <!-- User Profile Brief -->
          <div class="flex items-center gap-4 border-b border-slate-100 pb-5">
            <img :src="currentUser.avatar" :alt="currentUser.fullName" class="w-14 h-14 rounded-full border-2 border-amber-600" />
            <div class="flex flex-col gap-1">
              <span class="text-sm font-extrabold text-slate-900">{{ currentUser.fullName }}</span>
              <div class="flex items-center gap-2">
                <span class="text-[10px] font-extrabold uppercase px-2 py-0.5 rounded text-white bg-amber-600">
                  Hạng {{ currentUser.tier }}
                </span>
                <span class="text-[11px] text-slate-500 font-bold">{{ currentUser.points }} điểm</span>
              </div>
            </div>
          </div>

          <!-- Navigation Links -->
          <nav class="flex flex-col gap-1">
            <RouterLink
              v-for="link in menuLinks"
              :key="link.to"
              :to="link.to"
              class="px-4 py-3 text-xs font-bold rounded-xl transition-all no-underline flex items-center justify-between"
              :class="[
                (link.exact ? route.path === link.to : route.path.startsWith(link.to))
                  ? 'bg-slate-900 text-white font-extrabold shadow-md'
                  : 'text-slate-700 hover:bg-slate-100 hover:text-slate-900'
              ]"
            >
              <span>{{ link.label }}</span>
              <span v-if="link.to === '/notifications'" class="w-2 h-2 rounded-full bg-red-500"></span>
            </RouterLink>

            <RouterLink
              to="/login"
              class="px-4 py-3 text-xs font-bold text-red-500 hover:bg-red-50 rounded-xl transition-all no-underline mt-4 border-t border-slate-100"
            >
              🚪 Đăng xuất tài khoản
            </RouterLink>
          </nav>
        </aside>

        <!-- MAIN ACCOUNT CONTENT AREA -->
        <main class="md:col-span-3">
          <RouterView />
        </main>
      </div>
    </div>

    <AppFooter />
    <CartDrawer />
    <NotificationPanel />
  </div>
</template>
