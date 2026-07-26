<script setup>
import { RouterLink } from 'vue-router'

const columns = [
  {
    title: 'Cửa hàng',
    links: [
      { to: '/shop', label: 'Tất cả sản phẩm' },
      { to: '/shop?cat=running', label: 'Giày Running' },
      { to: '/shop?cat=lifestyle', label: 'Giày Lifestyle' },
      { to: '/shop?cat=new', label: 'Hàng mới về' },
    ],
  },
  {
    title: 'Tài khoản',
    links: [
      { to: '/login', label: 'Đăng nhập' },
      { to: '/register', label: 'Đăng ký' },
      { to: '/account/orders', label: 'Lịch sử đơn hàng' },
      { to: '/wishlist', label: 'Danh sách yêu thích' },
    ],
  },
  {
    title: 'Hỗ trợ & Chính sách',
    links: [
      { to: '/about', label: 'Giới thiệu KickVerse' },
      { to: '/contact', label: 'Liên hệ & Bản đồ' },
      { to: '/return-policy', label: 'Chính sách đổi trả' },
      { to: '/shipping-policy', label: 'Chính sách giao hàng' },
      { to: '/faq', label: 'Câu hỏi thường gặp' },
    ],
  },
]
</script>

<template>
  <footer class="bg-white border-t border-slate-200 text-slate-600 pt-16 pb-10 relative z-10">
    <div class="kv-container flex flex-col gap-12">
      
      <!-- Top Section -->
      <div class="grid grid-cols-1 md:grid-cols-[1fr_2fr] gap-12">
        <div class="flex flex-col items-start">
          <RouterLink to="/" class="font-display text-3xl tracking-[3px] text-slate-900 no-underline mb-3 inline-block">
            KICK<span class="text-amber-600 font-extrabold">VERSE</span>
          </RouterLink>
          <p class="text-xs leading-relaxed text-slate-500 mb-6 max-w-sm">
            Định nghĩa lại phong cách sneaker streetwear cao cấp. Cam kết 100% chính hãng.
          </p>
          <div class="flex gap-3">
            <a 
              v-for="s in ['IG', 'FB', 'TW', 'YT']" 
              :key="s" 
              href="#" 
              :aria-label="s"
              class="w-9 h-9 rounded-full border border-slate-200 text-xs font-extrabold text-slate-600 flex items-center justify-center transition-all duration-200 hover:bg-slate-900 hover:text-white hover:border-slate-900 hover:-translate-y-0.5 shadow-sm no-underline"
            >
              {{ s }}
            </a>
          </div>
        </div>

        <!-- Links Columns -->
        <div class="grid grid-cols-1 sm:grid-cols-3 gap-10">
          <div v-for="col in columns" :key="col.title" class="flex flex-col">
            <h3 class="text-xs font-extrabold tracking-widest uppercase text-amber-600 mb-4">{{ col.title }}</h3>
            <ul class="list-none p-0 m-0 flex flex-col gap-2.5">
              <li v-for="link in col.links" :key="link.to">
                <RouterLink 
                  :to="link.to" 
                  class="text-xs font-medium text-slate-600 no-underline transition-all duration-200 hover:text-amber-600 hover:pl-1 inline-block"
                >
                  {{ link.label }}
                </RouterLink>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <!-- Bottom Section -->
      <div class="flex flex-col sm:flex-row items-center justify-between gap-4 border-t border-slate-100 pt-8 text-xs text-slate-500">
        <p class="m-0">© 2026 KickVerse Sneaker Store. All rights reserved.</p>
        <div class="flex items-center gap-4">
          <RouterLink to="/terms" class="no-underline transition-colors hover:text-amber-600">Điều khoản dịch vụ</RouterLink>
          <span class="text-slate-300">|</span>
          <RouterLink to="/privacy" class="no-underline transition-colors hover:text-amber-600">Chính sách bảo mật</RouterLink>
        </div>
      </div>

    </div>
  </footer>
</template>
