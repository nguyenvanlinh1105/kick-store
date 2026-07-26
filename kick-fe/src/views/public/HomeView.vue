<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { RouterLink } from 'vue-router'
import KvProductCard from '@/components/ui/KvProductCard.vue'
import productsJson from '@/data/json/products.json'
import { DEMO_BANNERS, DEMO_BRANDS, DEMO_BLOGS } from '@/data/demo'
import { useCartStore } from '@/stores/cart'

const cart = useCartStore()

const currentBannerIndex = ref(0)
let bannerInterval = null

function nextBanner() {
  currentBannerIndex.value = (currentBannerIndex.value + 1) % DEMO_BANNERS.length
}

// Flash Sale Countdown Timer
const countdown = ref({ hours: 5, minutes: 24, seconds: 40 })
let countdownTimer = null

function startCountdown() {
  countdownTimer = setInterval(() => {
    if (countdown.value.seconds > 0) {
      countdown.value.seconds--
    } else {
      countdown.value.seconds = 59
      if (countdown.value.minutes > 0) {
        countdown.value.minutes--
      } else {
        countdown.value.minutes = 59
        if (countdown.value.hours > 0) {
          countdown.value.hours--
        }
      }
    }
  }, 1000)
}

const newsletterEmail = ref('')
const newsletterSubmitted = ref(false)

function handleNewsletterSubmit() {
  if (!newsletterEmail.value) return
  newsletterSubmitted.value = true
  setTimeout(() => {
    newsletterSubmitted.value = false
    newsletterEmail.value = ''
  }, 3000)
}

onMounted(() => {
  bannerInterval = setInterval(nextBanner, 6000)
  startCountdown()
})

onUnmounted(() => {
  if (bannerInterval) clearInterval(bannerInterval)
  if (countdownTimer) clearInterval(countdownTimer)
})

const featuredProducts = productsJson.slice(0, 4)
const flashSaleProducts = productsJson.filter((p) => p.badge === 'Flash Sale' || p.compareAt)
</script>

<template>
  <div class="bg-slate-50 text-slate-900 min-h-screen">
    <!-- HERO CAROUSEL -->
    <section
      class="relative min-h-[560px] md:min-h-[640px] flex items-center overflow-hidden bg-slate-100 border-b border-slate-200"
    >
      <div
        v-for="(banner, idx) in DEMO_BANNERS"
        :key="banner.id"
        class="absolute inset-0 transition-opacity duration-700 ease-in-out flex items-center"
        :class="[
          idx === currentBannerIndex ? 'opacity-100 z-10' : 'opacity-0 pointer-events-none z-0',
        ]"
      >
        <!-- Background Banner Image -->
        <img
          :src="banner.image"
          :alt="banner.title"
          class="absolute inset-0 w-full h-full object-cover object-right saturate-[1.1]"
        />

        <!-- Light Gradient Overlay so Text is 100% Readable -->
        <div
          class="absolute inset-0 bg-gradient-to-r from-white via-white/90 to-transparent md:w-3/4"
        ></div>

        <!-- Banner Content Overlay -->
        <div class="relative z-20 kv-container w-full py-12">
          <div class="max-w-xl flex flex-col gap-4">
            <span
              class="inline-flex items-center gap-2 px-3.5 py-1.5 bg-amber-500/10 border border-amber-500/30 text-amber-700 font-extrabold text-xs tracking-wider uppercase rounded-full self-start"
            >
              <span>🔥</span> {{ banner.subtitle }}
            </span>

            <h1
              class="font-display text-5xl sm:text-6xl md:text-7xl font-extrabold leading-[0.95] text-slate-900 uppercase tracking-tight"
            >
              {{ banner.title }}
            </h1>

            <p class="text-sm md:text-base text-slate-600 font-medium leading-relaxed">
              {{ banner.desc }}
            </p>

            <div class="flex items-center gap-3 py-1">
              <span class="text-xs font-bold text-slate-500">Ưu đãi độc quyền:</span>
              <span class="text-lg font-extrabold text-amber-600">Giá chỉ từ 1.890.000đ</span>
            </div>

            <div class="flex flex-wrap items-center gap-3.5 mt-2">
              <RouterLink
                :to="banner.ctaLink"
                class="inline-flex items-center gap-2.5 px-8 py-4 text-xs font-extrabold tracking-widest uppercase text-white bg-slate-900 rounded-xl hover:bg-amber-600 hover:-translate-y-0.5 transition-all no-underline shadow-lg hover:shadow-amber-500/25"
              >
                {{ banner.ctaText }}
                <span>→</span>
              </RouterLink>

              <RouterLink
                to="/shop"
                class="inline-flex items-center gap-2 px-7 py-4 text-xs font-extrabold tracking-widest uppercase text-slate-900 bg-white border-2 border-slate-300 rounded-xl hover:border-slate-900 transition-all no-underline shadow-sm"
              >
                Khám Phá Cửa Hàng
              </RouterLink>
            </div>
          </div>
        </div>
      </div>

      <!-- Carousel Indicators (Dots only) -->
      <div class="absolute bottom-6 left-1/2 -translate-x-1/2 z-30 flex items-center gap-2">
        <button
          v-for="(b, idx) in DEMO_BANNERS"
          :key="b.id"
          type="button"
          class="h-2.5 rounded-full transition-all cursor-pointer"
          :class="[
            idx === currentBannerIndex
              ? 'w-10 bg-amber-600'
              : 'w-3 bg-slate-300 hover:bg-slate-400',
          ]"
          @click="currentBannerIndex = idx"
        />
      </div>
    </section>

    <!-- FLASH SALE SECTION WITH COUNTDOWN -->
    <section
      class="bg-gradient-to-r from-amber-500/10 via-amber-500/5 to-amber-500/10 py-12 border-b border-amber-500/20"
    >
      <div class="kv-container">
        <div class="flex flex-col md:flex-row md:items-center justify-between gap-4 mb-8">
          <div class="flex items-center gap-4">
            <div
              class="px-3.5 py-1.5 bg-red-600 text-white text-xs font-extrabold uppercase tracking-wider rounded-full flex items-center gap-2 shadow-md"
            >
              <span class="w-2 h-2 rounded-full bg-white animate-ping"></span>
              ⚡ Flash Sale Hôm Nay
            </div>
            <h2 class="text-2xl md:text-3xl font-extrabold text-slate-900">
              Săn Deal Giảm Giá Khủng
            </h2>
          </div>

          <!-- Countdown Clock -->
          <div class="flex items-center gap-2 text-xs font-bold text-slate-700">
            <span>Thời gian còn lại:</span>
            <div class="flex items-center gap-1.5">
              <span
                class="px-3 py-1.5 bg-slate-900 text-amber-400 font-extrabold text-sm rounded-lg shadow"
                >{{ String(countdown.hours).padStart(2, '0') }}</span
              >:
              <span
                class="px-3 py-1.5 bg-slate-900 text-amber-400 font-extrabold text-sm rounded-lg shadow"
                >{{ String(countdown.minutes).padStart(2, '0') }}</span
              >:
              <span
                class="px-3 py-1.5 bg-slate-900 text-amber-400 font-extrabold text-sm rounded-lg shadow"
                >{{ String(countdown.seconds).padStart(2, '0') }}</span
              >
            </div>
          </div>
        </div>

        <div class="grid grid-cols-2 md:grid-cols-4 gap-5">
          <KvProductCard
            v-for="p in flashSaleProducts"
            :key="p.id"
            :product="p"
            :dark="false"
            @add-to-cart="cart.addItem"
          />
        </div>
      </div>
    </section>

    <!-- FEATURED CATEGORY GRID -->
    <section class="py-16 bg-white border-b border-slate-200">
      <div class="kv-container">
        <div class="text-center max-w-md mx-auto mb-12">
          <span class="text-xs font-extrabold text-amber-600 uppercase tracking-widest block mb-1"
            >Phong Cách Sneaker</span
          >
          <h2 class="text-3xl font-extrabold text-slate-900">Danh Mục Nổi Bật 2026</h2>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
          <RouterLink
            to="/shop?cat=running"
            class="group relative h-84 rounded-3xl overflow-hidden no-underline block border border-slate-200 shadow-md hover:shadow-2xl transition-all"
          >
            <img
              src="https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=800&q=85&auto=format&fit=crop"
              alt="Running"
              class="w-full h-full object-cover group-hover:scale-108 transition-transform duration-700"
            />
            <div
              class="absolute inset-0 bg-gradient-to-t from-slate-950/85 via-slate-950/30 to-transparent"
            ></div>
            <div class="absolute bottom-6 left-6 right-6 flex flex-col gap-1">
              <span class="text-xs font-extrabold text-amber-400 uppercase tracking-widest"
                >Performance</span
              >
              <h3 class="text-2xl font-extrabold text-white">Running Shoes</h3>
              <span
                class="text-xs font-bold text-slate-200 group-hover:text-amber-400 transition-colors mt-1"
                >Khám phá ngay →</span
              >
            </div>
          </RouterLink>

          <RouterLink
            to="/shop?cat=lifestyle"
            class="group relative h-84 rounded-3xl overflow-hidden no-underline block border border-slate-200 shadow-md hover:shadow-2xl transition-all"
          >
            <img
              src="https://images.unsplash.com/photo-1600269452121-4f2416e55c28?w=800&q=85&auto=format&fit=crop"
              alt="Lifestyle"
              class="w-full h-full object-cover group-hover:scale-108 transition-transform duration-700"
            />
            <div
              class="absolute inset-0 bg-gradient-to-t from-slate-950/85 via-slate-950/30 to-transparent"
            ></div>
            <div class="absolute bottom-6 left-6 right-6 flex flex-col gap-1">
              <span class="text-xs font-extrabold text-amber-400 uppercase tracking-widest"
                >Streetwear</span
              >
              <h3 class="text-2xl font-extrabold text-white">Lifestyle Essentials</h3>
              <span
                class="text-xs font-bold text-slate-200 group-hover:text-amber-400 transition-colors mt-1"
                >Khám phá ngay →</span
              >
            </div>
          </RouterLink>

          <RouterLink
            to="/shop?cat=new"
            class="group relative h-84 rounded-3xl overflow-hidden no-underline block border border-slate-200 shadow-md hover:shadow-2xl transition-all"
          >
            <img
              src="https://images.unsplash.com/photo-1539185441755-769473a23570?w=800&q=85&auto=format&fit=crop"
              alt="Limited"
              class="w-full h-full object-cover group-hover:scale-108 transition-transform duration-700"
            />
            <div
              class="absolute inset-0 bg-gradient-to-t from-slate-950/85 via-slate-950/30 to-transparent"
            ></div>
            <div class="absolute bottom-6 left-6 right-6 flex flex-col gap-1">
              <span class="text-xs font-extrabold text-amber-400 uppercase tracking-widest"
                >Exclusive Drop</span
              >
              <h3 class="text-2xl font-extrabold text-white">Limited Editions</h3>
              <span
                class="text-xs font-bold text-slate-200 group-hover:text-amber-400 transition-colors mt-1"
                >Khám phá ngay →</span
              >
            </div>
          </RouterLink>
        </div>
      </div>
    </section>

    <!-- ALL PRODUCTS SECTION -->
    <section class="py-16 bg-slate-50 border-b border-slate-200">
      <div class="kv-container">
        <div class="flex items-end justify-between gap-4 mb-10">
          <div>
            <span class="text-xs font-extrabold text-amber-600 uppercase tracking-widest block mb-1"
              >Gợi Ý Mua Sắm</span
            >
            <h2 class="text-3xl font-extrabold text-slate-900">Sản Phẩm Bán Chạy Nhất</h2>
          </div>
          <RouterLink
            to="/shop"
            class="text-xs font-extrabold text-amber-600 hover:underline no-underline"
          >
            Xem tất cả sản phẩm →
          </RouterLink>
        </div>

        <div class="grid grid-cols-2 md:grid-cols-4 gap-5">
          <KvProductCard
            v-for="p in featuredProducts"
            :key="p.id"
            :product="p"
            :dark="false"
            @add-to-cart="cart.addItem"
          />
        </div>
      </div>
    </section>

    <!-- BRAND PARTNERS -->
    <section class="py-12 bg-white border-b border-slate-200">
      <div class="kv-container">
        <p class="text-center text-xs font-extrabold text-slate-400 uppercase tracking-widest mb-6">
          Thương hiệu chính hãng đối tác
        </p>
        <div class="flex flex-wrap items-center justify-center gap-6 md:gap-12">
          <RouterLink
            v-for="brand in DEMO_BRANDS"
            :key="brand.id"
            :to="`/shop?brand=${brand.name}`"
            class="flex items-center gap-2.5 text-sm font-extrabold text-slate-800 hover:text-amber-600 transition-all no-underline px-4 py-2 rounded-xl bg-slate-50 border border-slate-200 hover:border-amber-500/40 shadow-sm"
          >
            <span class="text-lg">{{ brand.logo }}</span>
            <span>{{ brand.name }}</span>
          </RouterLink>
        </div>
      </div>
    </section>

    <!-- NEWSLETTER FORM -->
    <section class="py-16 bg-gradient-to-br from-amber-500/10 via-white to-amber-500/5">
      <div class="kv-container max-w-2xl text-center">
        <span class="text-xs font-extrabold text-amber-600 uppercase tracking-widest block mb-1"
          >Đăng Ký Nhận Bản Tin</span
        >
        <h2 class="text-3xl font-extrabold text-slate-900 mb-3">Nhận Mã Giảm Giá 15% Độc Quyền</h2>
        <p class="text-xs text-slate-600 mb-8 leading-relaxed">
          Nhập email của bạn để không bỏ lỡ các đợt phát hành sản phẩm giới hạn (Limited Drop) và
          thông tin khuyến mãi mới nhất.
        </p>

        <p
          v-if="newsletterSubmitted"
          class="text-xs text-emerald-600 font-extrabold mt-4 animate-fade-in"
        >
          ✓ Cảm ơn bạn! Mã giảm giá 15% đã được gửi tới email của bạn.
        </p>
      </div>
    </section>
  </div>
</template>
