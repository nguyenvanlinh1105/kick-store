<script setup>
import { RouterLink } from 'vue-router'

const columns = [
  {
    title: 'Cửa hàng',
    links: [
      { to: '/shop', label: 'Tất cả sản phẩm' },
      { to: '/shop?cat=running', label: 'Running' },
      { to: '/shop?cat=lifestyle', label: 'Lifestyle' },
    ],
  },
  {
    title: 'Tài khoản',
    links: [
      { to: '/login', label: 'Đăng nhập' },
      { to: '/register', label: 'Đăng ký' },
      { to: '/account/orders', label: 'Đơn hàng' },
      { to: '/wishlist', label: 'Yêu thích' },
    ],
  },
  {
    title: 'Hỗ trợ',
    links: [
      { to: '/support', label: 'Trung tâm hỗ trợ' },
      { to: '/support#shipping', label: 'Vận chuyển' },
      { to: '/support#returns', label: 'Đổi trả' },
    ],
  },
]
</script>

<template>
  <footer class="bg-surface-0 border-t border-white/5 text-text-secondary pt-20 pb-10 relative z-10">
    <div class="kv-container flex flex-col gap-16">
      
      <!-- Top Section -->
      <div class="grid grid-cols-1 md:grid-cols-[1fr_2fr] gap-12">
        <div class="flex flex-col items-start">
          <RouterLink to="/" class="font-display text-3xl tracking-[3px] text-white no-underline mb-4 inline-block">
            KICK<span class="bg-gradient-to-r from-primary via-primary-hover to-primary-pressed bg-clip-text text-transparent">VERSE</span>
          </RouterLink>
          <p class="text-sm leading-relaxed text-text-muted mb-6">
            Định nghĩa lại phong cách sneaker streetwear cao cấp.
          </p>
          <div class="flex gap-4">
            <a 
              v-for="s in ['IG', 'FB', 'TW', 'YT']" 
              :key="s" 
              href="#" 
              :aria-label="s"
              class="w-9 h-9 rounded-full border border-white/10 text-xs font-bold text-text-muted flex items-center justify-center transition-all duration-200 hover:bg-primary hover:text-black hover:border-primary hover:-translate-y-0.5 hover:shadow-lg no-underline"
            >
              {{ s }}
            </a>
          </div>
        </div>

        <!-- Links Columns -->
        <div class="grid grid-cols-1 sm:grid-cols-3 gap-10">
          <div v-for="col in columns" :key="col.title" class="flex flex-col">
            <h3 class="text-xs font-bold tracking-widest uppercase text-primary mb-5">{{ col.title }}</h3>
            <ul class="list-none p-0 m-0 flex flex-col gap-3">
              <li v-for="link in col.links" :key="link.to">
                <RouterLink 
                  :to="link.to" 
                  class="text-[13.5px] text-text-secondary/70 no-underline transition-all duration-200 hover:text-white hover:pl-1 inline-block"
                >
                  {{ link.label }}
                </RouterLink>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <!-- Bottom Section -->
      <div class="flex flex-col sm:flex-row items-center justify-between gap-5 border-t border-white/5 pt-8">
        <p class="text-xs text-text-muted/80 m-0">© 2026 KickVerse. Crafted with luxury aesthetics.</p>
        <div class="flex items-center gap-3">
          <a href="#" class="text-xs text-text-muted/80 no-underline transition-colors duration-200 hover:text-primary">Điều khoản dịch vụ</a>
          <span class="text-[10px] text-white/10">|</span>
          <a href="#" class="text-xs text-text-muted/80 no-underline transition-colors duration-200 hover:text-primary">Chính sách bảo mật</a>
        </div>
      </div>

    </div>
  </footer>
</template>
