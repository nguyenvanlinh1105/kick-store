<script setup>
import { RouterLink } from 'vue-router'
import KvProductCard from '@/components/ui/KvProductCard.vue'
import { DEMO_PRODUCTS } from '@/data/demo'
import { useCartStore } from '@/stores/cart'
import { useResponsive } from '@/composables/useResponsive'

const cart = useCartStore()
const { isMobile } = useResponsive()
const featured = DEMO_PRODUCTS.slice(0, 4)

const collections = [
  {
    id: 'running',
    label: 'Running',
    tag: 'Performance',
    img: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=800&q=85&auto=format&fit=crop',
    to: '/shop?cat=running',
  },
  {
    id: 'lifestyle',
    label: 'Lifestyle',
    tag: 'Streetwear',
    img: 'https://images.unsplash.com/photo-1600269452121-4f2416e55c28?w=800&q=85&auto=format&fit=crop',
    to: '/shop?cat=lifestyle',
  },
  {
    id: 'limited',
    label: 'Limited',
    tag: 'Exclusive Drop',
    img: 'https://images.unsplash.com/photo-1539185441755-769473a23570?w=800&q=85&auto=format&fit=crop',
    to: '/shop?cat=new',
  },
]

const stats = [
  { value: '10K+', label: 'Khách hàng' },
  { value: '500+', label: 'Sản phẩm' },
  { value: '98%', label: 'Đánh giá tốt' },
  { value: '24h', label: 'Giao hàng' },
]
</script>

<template>
  <div class="bg-surface-0 text-text-primary">
    <!-- HERO Section -->
    <section
      class="relative min-h-[100svh] flex items-center overflow-hidden"
      id="hero"
      aria-label="Hero section"
    >
      <!-- Background Image -->
      <div class="absolute inset-0 z-0">
        <img
          src="https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=1920&q=85&auto=format&fit=crop"
          alt=""
          aria-hidden="true"
          class="w-full h-full object-cover object-[center_30%] saturate-[0.7]"
        />
        <div
          class="absolute inset-0 bg-gradient-to-r from-neutral-950/95 via-neutral-950/80 to-transparent"
        ></div>
      </div>

      <!-- Noise Overlay -->
      <div
        class="absolute inset-0 z-10 opacity-3 pointer-events-none"
        style="
          background-image: url(&quot;data:image/svg+xml,%3Csvg viewBox='0 0 256 256' xmlns='http://www.w3.org/2000/svg'%3E%3Cfilter id='noise'%3E%3CfeTurbulence type='fractalNoise' baseFrequency='0.9' numOctaves='4' stitchTiles='stitch'/%3E%3C/filter%3E%3Crect width='100%25' height='100%25' filter='url(%23noise)' opacity='1'/%3E%3C/svg%3E&quot;);
          background-size: 200px;
        "
        aria-hidden="true"
      ></div>

      <!-- Content -->
      <div class="relative z-20 kv-container pt-20 pb-28">
        <div
          class="flex items-center gap-2.5 text-[11px] font-bold tracking-[3px] uppercase text-primary mb-7 animate-pulse"
        >
          <span class="w-1.5 h-1.5 rounded-full bg-primary"></span>
          KickVerse · SS 2026
        </div>

        <h1
          class="flex flex-col font-display text-[64px] sm:text-[96px] md:text-[120px] leading-[0.92] tracking-[-1px] text-white mb-8"
        >
          <span class="block">Đôi giày</span>
          <span
            class="block bg-gradient-to-r from-primary via-primary-hover to-primary-pressed bg-clip-text text-transparent"
            >định nghĩa</span
          >
          <span class="block">phong cách.</span>
        </h1>

        <p class="text-base leading-relaxed text-text-secondary/95 mb-12">
          Curated sneakers từ các thương hiệu hàng đầu.<br v-if="!isMobile" />
          Từ limited drops đến everyday essentials.
        </p>

        <div class="flex flex-wrap gap-3.5 mb-16">
          <RouterLink
            to="/shop"
            class="inline-flex items-center gap-2.5 px-7 py-3.5 text-xs font-bold tracking-widest uppercase text-black bg-primary rounded-md hover:bg-primary-hover hover:-translate-y-0.5 hover:shadow-[0_12px_32px_rgb(200_169_110/0.3)] transition-all duration-200 no-underline"
            id="hero-shop-btn"
          >
            Khám phá ngay
            <svg
              width="16"
              height="16"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2.5"
              stroke-linecap="round"
              stroke-linejoin="round"
            >
              <line x1="5" y1="12" x2="19" y2="12" />
              <polyline points="12 5 19 12 12 19" />
            </svg>
          </RouterLink>
          <RouterLink
            to="/shop?cat=new"
            class="inline-flex items-center px-7 py-3.5 text-xs font-bold tracking-widest uppercase text-white bg-transparent border border-white/20 rounded-md hover:border-primary hover:text-primary hover:bg-primary/5 transition-all duration-200 no-underline"
            id="hero-new-btn"
          >
            Hàng mới về
          </RouterLink>
        </div>

        <!-- Stats Row -->
        <div class="flex flex-wrap gap-10">
          <div v-for="stat in stats" :key="stat.label" class="flex flex-col gap-1">
            <span class="font-display text-4xl tracking-wider text-primary leading-none">{{
              stat.value
            }}</span>
            <span class="text-[11px] font-semibold tracking-widest uppercase text-text-muted/85">{{
              stat.label
            }}</span>
          </div>
        </div>
      </div>

      <!-- Scroll indicator -->
      <div
        class="absolute bottom-10 left-1/2 -translate-x-1/2 z-20 flex flex-col items-center gap-2"
        aria-hidden="true"
      >
        <div class="w-px h-12 bg-gradient-to-b from-primary to-transparent animate-bounce"></div>
        <span class="text-[9px] font-bold tracking-[3px] uppercase text-text-muted/65">Scroll</span>
      </div>
    </section>

    <!-- COLLECTIONS Section -->
    <section class="bg-white py-12" id="collections" aria-label="Bộ sưu tập">
      <div class="kv-container">
        <div class="mb-12">
          <div
            class="inline-block text-[10px] font-bold tracking-[2.5px] uppercase text-primary mb-3 px-2.5 py-1 bg-primary/8 border border-primary/15 rounded"
          >
            Bộ sưu tập
          </div>
          <h2
            class="text-3xl md:text-5xl font-extrabold tracking-tight leading-[1.05] text-black m-0"
          >
            Chọn phong cách của bạn
          </h2>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
          <RouterLink
            v-for="col in collections"
            :key="col.id"
            :to="col.to"
            class="relative block rounded-xl overflow-hidden aspect-[3/4] group no-underline"
            :id="`collection-${col.id}`"
          >
            <div class="absolute inset-0">
              <img
                :src="col.img"
                :alt="col.label"
                class="w-full h-full object-cover saturate-[0.8] group-hover:scale-105 group-hover:saturate-100 transition-all duration-500"
                loading="lazy"
              />
              <div
                class="absolute inset-0 bg-gradient-to-t from-black/90 via-black/10 to-transparent group-hover:from-black/95 group-hover:via-black/30 transition-all duration-300"
              ></div>
            </div>
            <div class="absolute bottom-0 inset-x-0 p-7 flex flex-col gap-1.5 z-10">
              <span class="text-[10px] font-bold tracking-widest uppercase text-primary">{{
                col.tag
              }}</span>
              <h3 class="font-display text-4xl tracking-wider text-white m-0 leading-none">
                {{ col.label }}
              </h3>
              <span
                class="inline-flex items-center gap-2 text-xs font-bold tracking-wider uppercase text-white/50 group-hover:text-primary group-hover:gap-3 transition-all duration-200 mt-1"
              >
                Xem ngay
                <svg
                  width="12"
                  height="12"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="2.5"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                >
                  <line x1="5" y1="12" x2="19" y2="12" />
                  <polyline points="12 5 19 12 12 19" />
                </svg>
              </span>
            </div>
          </RouterLink>
        </div>
      </div>
    </section>

    <!-- FEATURED PRODUCTS -->
    <section class="bg-white py-12" id="featured-products" aria-label="Sản phẩm nổi bật">
      <div class="kv-container">
        <div class="flex items-end justify-between gap-6 mb-12">
          <div>
            <div
              class="inline-block text-[10px] font-bold tracking-[2.5px] uppercase text-primary mb-3 px-2.5 py-1 bg-primary/5 border border-primary/15 rounded"
            >
              Hot tuần này
            </div>
            <h2
              class="text-3xl md:text-5xl font-extrabold tracking-tight leading-[1.05] text-black m-0"
            >
              Bán chạy nhất
            </h2>
          </div>
          <RouterLink
            to="/shop"
            class="inline-flex items-center gap-2 text-xs font-bold tracking-wide text-primary transition-all duration-200 hover:gap-3 hover:opacity-80 pb-1 no-underline"
            id="view-all-products-link"
          >
            Xem tất cả
            <svg
              width="14"
              height="14"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
            >
              <line x1="5" y1="12" x2="19" y2="12" />
              <polyline points="12 5 19 12 12 19" />
            </svg>
          </RouterLink>
        </div>

        <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
          <KvProductCard
            v-for="p in featured"
            :key="p.id"
            :product="p"
            @add-to-cart="cart.addItem"
          />
        </div>
      </div>
    </section>

    <!-- EDITORIAL SPLIT -->
    <section
      class="bg-white py-12 border-t border-black/5"
      id="editorial"
      aria-label="Câu chuyện thương hiệu"
    >
      <div class="kv-container">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-16 md:gap-20 items-center">
          <!-- Image -->
          <div class="relative rounded-2xl overflow-hidden aspect-[4/5] group">
            <img
              src="https://images.unsplash.com/photo-1549298916-b41d501d3772?w=1000&q=85&auto=format&fit=crop"
              alt="KickVerse — Crafted for the street"
              class="w-full h-full object-cover group-hover:scale-[1.02] transition-all duration-500"
              loading="lazy"
            />
            <div
              class="absolute bottom-6 right-6 px-4.5 py-2.5 bg-primary/90 backdrop-blur-md rounded-md text-[12px] font-bold tracking-wider uppercase text-black"
            >
              <span>Since 2024</span>
            </div>
          </div>

          <!-- Text -->
          <div class="flex flex-col">
            <div
              class="inline-block text-[10px] font-bold tracking-[2.5px] uppercase text-primary mb-3 px-2.5 py-1 bg-primary/5 border border-primary/15 rounded self-start"
            >
              Về chúng tôi
            </div>
            <h2
              class="text-3xl md:text-5xl font-extrabold tracking-tight leading-[1.1] text-black mt-4 mb-7"
            >
              Chất liệu.<br />Form dáng.<br />
              <span
                class="bg-gradient-to-r from-primary via-primary-hover to-primary-pressed bg-clip-text text-transparent"
                >Cảm giác đi.</span
              >
            </h2>
            <p class="text-sm leading-relaxed text-neutral-600 mb-4 font-medium">
              Mỗi đôi giày trên KickVerse được chọn lọc theo tiêu chí thực tế: đế êm, form chuẩn
              streetwear, và bảng size rõ ràng trước khi bạn thêm vào giỏ hàng.
            </p>
            <p class="text-sm leading-relaxed text-neutral-500 mb-8">
              Chúng tôi tin rằng sneaker không chỉ là thời trang — đó là cách bạn bước đi trong cuộc
              sống.
            </p>
            <RouterLink
              to="/support"
              class="inline-flex items-center self-start px-6 py-3 text-[11px] font-bold tracking-widest uppercase text-black border border-black/15 rounded-md hover:border-primary hover:text-primary hover:bg-primary/5 transition-all duration-200 no-underline bg-blue"
              id="editorial-learn-more-btn"
            >
              Tìm hiểu thêm
            </RouterLink>
          </div>
        </div>
      </div>
    </section>

    <!-- MEMBERSHIP CTA -->
    <section
      class="relative overflow-hidden bg-gradient-to-br from-yellow-950/20 via-surface-0 to-neutral-900/40 border-y border-primary/10 py-12"
      id="membership"
      aria-label="Thành viên KickVerse"
    >
      <!-- Watermark Background -->
      <div
        class="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 font-display text-[80px] md:text-[200px] tracking-[8px] text-primary/8 select-none pointer-events-none whitespace-nowrap"
        aria-hidden="true"
      >
        KICKVERSE
      </div>

      <div
        class="relative z-10 kv-container flex flex-col md:flex-row items-start md:items-center justify-between gap-12"
      >
        <div class="flex flex-col">
          <div
            class="inline-block text-[10px] font-bold tracking-[2.5px] uppercase text-primary mb-3 px-2.5 py-1 bg-primary-dim border border-primary/20 rounded self-start"
          >
            Thành viên
          </div>
          <h2
            class="text-3xl md:text-5xl font-extrabold tracking-tight leading-[1.1] text-white mt-4 mb-4"
          >
            Trở thành KickVerse Member
          </h2>
          <p class="text-sm leading-relaxed text-text-secondary">
            Nhận thông báo drop mới sớm nhất, theo dõi đơn và lưu wishlist trên mọi thiết bị.
          </p>
        </div>
        <RouterLink
          to="/register"
          class="inline-flex items-center gap-3 px-8 py-4 text-xs font-bold tracking-widest uppercase text-black bg-gradient-to-r from-primary via-primary-hover to-primary-pressed rounded-lg shadow-lg hover:bg-right hover:scale-[1.02] hover:shadow-primary-hover/35 transition-all duration-200 no-underline"
          id="membership-register-btn"
        >
          Tạo tài khoản miễn phí
          <svg
            width="16"
            height="16"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2.5"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <line x1="5" y1="12" x2="19" y2="12" />
            <polyline points="12 5 19 12 12 19" />
          </svg>
        </RouterLink>
      </div>
    </section>
  </div>
</template>
