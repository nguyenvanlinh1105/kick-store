<script setup>
import { RouterView, RouterLink, useRoute } from 'vue-router'
import staffJson from '@/data/json/staff.json'

const route = useRoute()
const currentStaff = staffJson[0] // Super Admin

const menuGroups = [
  {
    title: 'TỔNG QUAN',
    items: [
      { to: '/admin', label: '📊 Dashboard Thống Kê', exact: true },
    ],
  },
  {
    title: 'QUẢN LÝ BÁN HÀNG',
    items: [
      { to: '/admin/orders', label: '📦 Đơn Hàng Hệ Thống' },
      { to: '/admin/returns', label: '🔄 Yêu Cầu Đổi / Trả' },
      { to: '/admin/coupons', label: '🎟️ Mã Giảm Giá' },
      { to: '/admin/flash-sale', label: '⚡ Flash Sale & Combo' },
      { to: '/admin/banners', label: '🖼️ Banner & Popup' },
    ],
  },
  {
    title: 'SẢN PHẨM & KHO',
    items: [
      { to: '/admin/products', label: '👟 Danh Sách Sản Phẩm' },
      { to: '/admin/categories', label: '📂 Cây Danh Mục' },
      { to: '/admin/attributes', label: '🏷️ Thuộc Tính (Size/Màu)' },
      { to: '/admin/brands', label: '⭐ Thương Hiệu' },
      { to: '/admin/inventory', label: '🏭 Quản Lý Kho Hàng' },
    ],
  },
  {
    title: 'KHÁCH HÀNG & CSKH',
    items: [
      { to: '/admin/customers', label: '👥 Quản Lý Khách Hàng' },
      { to: '/admin/loyalty', label: '👑 Hạng Thành Viên' },
      { to: '/admin/reviews', label: '⭐ Duyệt Đánh Giá' },
    ],
  },
  {
    title: 'HỆ THỐNG & PHÂN QUYỀN',
    items: [
      { to: '/admin/users', label: '👮 Nhân Viên & RBAC' },
      { to: '/admin/settings', label: '⚙️ Cấu Hình Hệ Thống' },
    ],
  },
]
</script>

<template>
  <div class="min-h-screen bg-black text-text-primary flex flex-col">
    <!-- ADMIN TOP HEADER BAR -->
    <header class="h-16 bg-neutral-900 border-b border-white/10 px-6 flex items-center justify-between sticky top-0 z-40">
      <div class="flex items-center gap-4">
        <RouterLink to="/admin" class="font-display text-xl tracking-wider text-white no-underline flex items-center gap-2">
          <span class="w-3 h-3 rounded-full bg-primary animate-pulse"></span>
          KICKVERSE <span class="text-xs text-primary font-bold px-2 py-0.5 bg-primary/10 rounded border border-primary/20">ADMIN PORTAL</span>
        </RouterLink>
      </div>

      <div class="flex items-center gap-4 text-xs">
        <RouterLink to="/" target="_blank" class="text-neutral-400 hover:text-white no-underline flex items-center gap-1">
          <span>🌐 Xem Storefront</span>
        </RouterLink>

        <div class="h-4 w-px bg-white/10"></div>

        <div class="flex items-center gap-3">
          <div class="w-8 h-8 rounded-full bg-primary/20 border border-primary text-primary font-extrabold flex items-center justify-center">
            A
          </div>
          <div class="flex flex-col text-left">
            <span class="font-bold text-white leading-tight">{{ currentStaff.fullName }}</span>
            <span class="text-[10px] text-primary font-mono">{{ currentStaff.roleName }}</span>
          </div>
        </div>
      </div>
    </header>

    <!-- ADMIN LAYOUT BODY -->
    <div class="flex flex-1">
      <!-- SIDEBAR NAV -->
      <aside class="w-64 bg-neutral-950 border-r border-white/10 p-4 flex flex-col gap-6 shrink-0 min-h-[calc(100vh-4rem)]">
        <div v-for="group in menuGroups" :key="group.title" class="flex flex-col gap-1">
          <span class="text-[10px] font-extrabold tracking-widest text-neutral-500 uppercase px-3 py-1">
            {{ group.title }}
          </span>
          <RouterLink
            v-for="item in group.items"
            :key="item.to"
            :to="item.to"
            class="px-3 py-2 text-xs font-bold rounded-lg transition-all no-underline flex items-center justify-between"
            :class="[
              (item.exact ? route.path === item.to : route.path.startsWith(item.to))
                ? 'bg-primary text-black font-extrabold shadow-sm'
                : 'text-neutral-300 hover:bg-white/5 hover:text-white'
            ]"
          >
            <span>{{ item.label }}</span>
          </RouterLink>
        </div>
      </aside>

      <!-- CONTENT MAIN -->
      <main class="flex-1 p-8 bg-surface-0 overflow-y-auto">
        <RouterView />
      </main>
    </div>
  </div>
</template>
