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
  <div class="min-h-screen bg-slate-50 text-slate-900 flex flex-col">
    <!-- ADMIN TOP HEADER BAR (PURE WHITE LIGHT THEME) -->
    <header class="h-16 bg-white border-b border-slate-200 px-6 flex items-center justify-between sticky top-0 z-40 shadow-sm">
      <div class="flex items-center gap-4">
        <RouterLink to="/admin" class="font-display text-xl tracking-wider text-slate-900 no-underline flex items-center gap-2">
          <span class="w-3 h-3 rounded-full bg-amber-600 animate-pulse"></span>
          KICKVERSE <span class="text-xs text-amber-700 font-extrabold px-2 py-0.5 bg-amber-500/10 rounded border border-amber-500/20">ADMIN PORTAL</span>
        </RouterLink>
      </div>

      <div class="flex items-center gap-4 text-xs">
        <RouterLink to="/" target="_blank" class="text-slate-600 hover:text-amber-600 font-bold no-underline flex items-center gap-1">
          <span>🌐 Xem Storefront</span>
        </RouterLink>

        <div class="h-4 w-px bg-slate-200"></div>

        <div class="flex items-center gap-3">
          <div class="w-8 h-8 rounded-full bg-slate-900 text-white font-extrabold flex items-center justify-center shadow">
            A
          </div>
          <div class="flex flex-col text-left">
            <span class="font-bold text-slate-900 leading-tight">{{ currentStaff.fullName }}</span>
            <span class="text-[10px] text-amber-600 font-mono font-extrabold">{{ currentStaff.roleName }}</span>
          </div>
        </div>
      </div>
    </header>

    <!-- ADMIN LAYOUT BODY -->
    <div class="flex flex-1">
      <!-- SIDEBAR NAV (PURE WHITE LIGHT THEME) -->
      <aside class="w-64 bg-white border-r border-slate-200 p-4 flex flex-col gap-6 shrink-0 min-h-[calc(100vh-4rem)]">
        <div v-for="group in menuGroups" :key="group.title" class="flex flex-col gap-1">
          <span class="text-[10px] font-extrabold tracking-widest text-amber-600 uppercase px-3 py-1">
            {{ group.title }}
          </span>
          <RouterLink
            v-for="item in group.items"
            :key="item.to"
            :to="item.to"
            class="px-3.5 py-2.5 text-xs font-bold rounded-xl transition-all no-underline flex items-center justify-between"
            :class="[
              (item.exact ? route.path === item.to : route.path.startsWith(item.to))
                ? 'bg-slate-900 text-white font-extrabold shadow-sm'
                : 'text-slate-700 hover:bg-slate-100 hover:text-slate-900'
            ]"
          >
            <span>{{ item.label }}</span>
          </RouterLink>
        </div>
      </aside>

      <!-- CONTENT MAIN (PURE WHITE CANVAS) -->
      <main class="flex-1 p-8 bg-slate-50 overflow-y-auto">
        <RouterView />
      </main>
    </div>
  </div>
</template>
