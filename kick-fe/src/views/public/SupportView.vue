<script setup>
import { ref } from 'vue'
import { useResponsive } from '@/composables/useResponsive'

const { isMobile } = useResponsive()
const query = ref('')

const topics = [
  { id: 'shipping', title: 'Vận chuyển & giao hàng', desc: 'Phí ship, thời gian giao hàng, theo dõi lộ trình đơn hàng' },
  { id: 'returns', title: 'Đổi trả & bảo hành', desc: 'Chính sách hoàn tiền 14 ngày, đổi size thuận tiện nhanh chóng' },
  { id: 'payment', title: 'Thanh toán & bảo mật', desc: 'Thanh toán COD khi nhận hàng, thanh toán qua cổng VNPay, ví điện tử MoMo' },
  { id: 'account', title: 'Tài khoản & thành viên', desc: 'Quản lý thông tin đăng nhập, bảo mật mật khẩu, hồ sơ cá nhân và quyền lợi tích điểm' },
]
</script>

<template>
  <div class="bg-surface-0 min-h-screen text-text-primary">
    <!-- Header Banner -->
    <section class="pt-20 pb-15 bg-gradient-to-b from-surface-1 to-surface-0 border-b border-white/5">
      <div class="kv-container flex flex-col items-start max-w-3xl">
        <span class="inline-block text-[10px] font-bold tracking-[2.5px] uppercase text-primary mb-3 px-2.5 py-1 bg-primary-dim border border-primary/20 rounded">Hỗ trợ</span>
        <h1 class="text-3xl md:text-5xl font-extrabold tracking-tight leading-[1.1] text-white mt-4 mb-3">Chúng tôi có thể giúp gì cho bạn?</h1>
        <p class="text-sm leading-relaxed text-text-secondary mb-8">Tìm câu trả lời nhanh chóng cho các thắc mắc về đơn hàng, vận chuyển và chính sách thành viên.</p>
        
        <!-- Search Bar -->
        <div class="relative w-full">
          <span class="absolute left-5 top-1/2 -translate-y-1/2 text-text-muted/40 flex items-center" aria-hidden="true">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
              <circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/>
            </svg>
          </span>
          <input
            v-model="query"
            type="search"
            placeholder="Tìm kiếm nội dung hướng dẫn..."
            class="w-full h-14 pl-14 pr-6 text-sm text-white bg-white/4 border border-white/10 rounded-full outline-none focus:border-primary/50 focus:bg-white/6 focus:shadow-[0_0_0_3px_rgb(200_169_110/0.08)] transition-all duration-300"
            aria-label="Tìm kiếm trợ giúp"
          />
        </div>
      </div>
    </section>

    <!-- Main Content Area -->
    <section class="kv-container pt-16 pb-28">
      <div class="grid gap-10 md:gap-16 items-start" :class="isMobile ? 'grid-cols-1' : 'grid-cols-[280px_1fr]'">
        
        <!-- Sidebar Navigation -->
        <aside class="sticky top-24" v-if="!isMobile">
          <h2 class="text-[11px] font-bold tracking-widest uppercase text-primary mb-5">Chủ đề phổ biến</h2>
          <nav class="flex flex-col gap-1" aria-label="Danh mục hỗ trợ">
            <a
              v-for="t in topics"
              :key="t.id"
              :href="`#${t.id}`"
              class="group flex items-center justify-between p-3.5 px-4 text-sm font-semibold text-text-secondary/70 no-underline rounded-lg hover:text-white hover:bg-white/4 transition-all duration-200"
            >
              <span>{{ t.title }}</span>
              <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round" class="opacity-0 -translate-x-1 group-hover:opacity-100 group-hover:translate-x-0 group-hover:text-primary transition-all duration-200">
                <polyline points="9 18 15 12 9 6"/>
              </svg>
            </a>
          </nav>
        </aside>

        <!-- Topic Details -->
        <main class="flex flex-col gap-6">
          <article
            v-for="t in topics"
            :id="t.id"
            :key="t.id"
            class="p-8 bg-surface-1 border border-white/4 rounded-2xl hover:border-white/8 transition-all duration-300"
          >
            <div class="flex items-center gap-3 mb-3">
              <span class="text-base text-primary">✦</span>
              <h3 class="text-lg font-bold text-white m-0">{{ t.title }}</h3>
            </div>
            <p class="text-sm leading-relaxed text-text-secondary m-0 font-medium mb-4">{{ t.desc }}</p>
            <div class="text-[13px] leading-relaxed text-text-muted/50 border-t border-white/5 pt-4">
              <p>Nội dung chi tiết sẽ được tự động đồng bộ qua hệ thống FAQ API của KickVerse. Nếu bạn có bất kỳ câu hỏi khẩn cấp nào khác, vui lòng liên hệ đội ngũ Chăm sóc khách hàng qua Hotline hoặc mục Chat trực tuyến của chúng tôi.</p>
            </div>
          </article>
        </main>

      </div>
    </section>
  </div>
</template>
