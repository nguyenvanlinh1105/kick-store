<script setup>
import { computed, ref } from 'vue'
import { useRoute } from 'vue-router'
import KvBadge from '@/components/ui/KvBadge.vue'
import { DEMO_PRODUCTS } from '@/data/demo'
import { useCartStore } from '@/stores/cart'
import { useWishlistStore } from '@/stores/wishlist'

const route = useRoute()
const cart = useCartStore()
const wishlist = useWishlistStore()

const product = computed(
  () => DEMO_PRODUCTS.find((p) => p.slug === route.params.slug) || DEMO_PRODUCTS[0],
)

const size = ref(null)
const color = ref(null)

function ensureDefaults() {
  if (product.value) {
    if (!size.value && product.value.sizes) size.value = product.value.sizes[0]
    if (!color.value && product.value.colors) color.value = product.value.colors[0]
  }
}
ensureDefaults()
</script>

<template>
  <div class="bg-surface-0 min-h-screen pt-10 pb-28 text-text-primary">
    <div class="kv-container">
      
      <!-- Back Link -->
      <div class="mb-8">
        <RouterLink to="/shop" class="inline-flex items-center gap-1.5 text-xs font-medium text-text-muted hover:text-primary transition-colors no-underline">
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <polyline points="15 18 9 12 15 6"/>
          </svg>
          Quay lại cửa hàng
        </RouterLink>
      </div>

      <!-- Main Layout Grid -->
      <div class="grid grid-cols-1 md:grid-cols-2 gap-10 md:gap-16">
        
        <!-- Left: Image Gallery -->
        <div class="w-full">
          <div class="relative w-full aspect-square bg-surface-1 border border-white/5 rounded-2xl overflow-hidden">
            <img :src="product.image" :alt="product.name" class="w-full h-full object-cover block animate-fade-in" />
            <div class="absolute inset-0 bg-gradient-to-t from-black/20 to-transparent"></div>
          </div>
        </div>

        <!-- Right: Info Panel -->
        <div class="flex flex-col">
          
          <!-- Category & Brand -->
          <div class="flex items-center gap-3 mb-4">
            <span class="text-[11px] font-bold tracking-wider uppercase text-primary px-2.5 py-1 bg-primary-dim border border-primary/20 rounded">{{ product.brand }}</span>
            <span class="text-[11px] font-semibold tracking-wide uppercase text-text-muted/65" v-if="product.category">{{ product.category }}</span>
          </div>

          <!-- Product Name -->
          <h1 class="text-3xl md:text-5xl font-extrabold tracking-tight leading-[1.1] text-white mb-5">{{ product.name }}</h1>

          <!-- Price Row -->
          <div class="flex items-baseline gap-4 mb-6">
            <span class="text-2xl md:text-3xl font-bold text-white">{{ product.priceLabel }}</span>
            <span v-if="product.compareAt" class="text-base text-text-muted/40 line-through">{{ product.compareAt }}</span>
            <KvBadge v-if="product.badge" :variant="product.badgeVariant || 'gold'" class="ml-2">
              {{ product.badge }}
            </KvBadge>
          </div>

          <!-- Description -->
          <p class="text-sm leading-relaxed text-text-secondary mb-9">
            Trải nghiệm phong cách đỉnh cao với dòng giày thể thao cao cấp. Chất liệu da/mesh bền bỉ, lưỡi gà êm ái cùng bộ đế đệm giảm chấn giúp bạn tự tin di chuyển suốt ngày dài.
          </p>

          <!-- Colors -->
          <div class="mb-7" v-if="product.colors && product.colors.length">
            <h3 class="text-xs font-bold tracking-wide uppercase text-text-muted/50 mb-3">Chọn màu</h3>
            <div class="flex flex-wrap gap-2.5">
              <button
                v-for="c in product.colors"
                :key="c"
                type="button"
                class="px-5 py-2.5 text-xs font-bold tracking-wide rounded-lg cursor-pointer transition-all duration-200 border"
                :class="color === c 
                  ? 'text-black bg-primary border-primary shadow-[0_4px_12px_rgb(200_169_110/0.2)]'
                  : 'text-text-secondary/70 bg-white/3 border-white/8 hover:text-white hover:border-white/25'"
                @click="color = c"
              >
                {{ c }}
              </button>
            </div>
          </div>

          <!-- Sizes -->
          <div class="mb-7" v-if="product.sizes && product.sizes.length">
            <h3 class="text-xs font-bold tracking-wide uppercase text-text-muted/50 mb-3">Chọn Size (US)</h3>
            <div class="grid grid-cols-4 sm:grid-cols-6 gap-2.5">
              <button
                v-for="s in product.sizes"
                :key="s"
                type="button"
                class="h-12 flex items-center justify-center text-xs font-bold rounded-lg cursor-pointer transition-all duration-200 border"
                :class="size === s
                  ? 'text-black bg-primary border-primary shadow-[0_4px_12px_rgb(200_169_110/0.2)]'
                  : 'text-text-secondary/70 bg-white/3 border-white/8 hover:text-white hover:border-white/25'"
                @click="size = s"
              >
                {{ s }}
              </button>
            </div>
          </div>

          <!-- Actions -->
          <div class="flex gap-3 mt-3 mb-10">
            <button
              type="button"
              class="flex-1 inline-flex items-center justify-center gap-2.5 h-13 text-xs font-extrabold tracking-widest uppercase text-black bg-gradient-to-r from-primary via-primary-hover to-primary-pressed rounded-lg shadow-lg hover:bg-right hover:-translate-y-0.5 hover:shadow-primary/35 transition-all duration-300 border-0 cursor-pointer"
              @click="cart.addItem(product, { size, color })"
            >
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
                <path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z"/><line x1="3" y1="6" x2="21" y2="6"/><path d="M16 10a4 4 0 0 1-8 0"/>
              </svg>
              Thêm vào giỏ hàng
            </button>
            
            <button
              type="button"
              class="w-13 h-13 flex items-center justify-center rounded-lg cursor-pointer transition-all duration-200 border"
              :class="wishlist.has(product.id)
                ? 'text-commerce border-commerce/20 bg-commerce/8 hover:border-commerce/30'
                : 'text-text-secondary/70 bg-white/4 border-white/8 hover:text-white hover:border-white/25'"
              @click="wishlist.toggle(product)"
            >
              <svg width="16" height="16" viewBox="0 0 24 24" :fill="wishlist.has(product.id) ? 'currentColor' : 'none'" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/>
              </svg>
            </button>
          </div>

          <!-- Brand Guarantees -->
          <div class="border-t border-white/5 pt-8 flex flex-col gap-5">
            <div class="flex gap-4">
              <span class="text-xl flex items-center justify-center w-10 h-10 bg-white/3 border border-white/6 rounded-full flex-shrink-0">⚡</span>
              <div>
                <p class="text-[13.5px] font-bold text-white mb-1">Giao Hàng Siêu Tốc 24H</p>
                <p class="text-[12.5px] text-text-muted/55 m-0">Nội thành TP.HCM & Hà Nội nhận hàng sớm nhất.</p>
              </div>
            </div>
            <div class="flex gap-4">
              <span class="text-xl flex items-center justify-center w-10 h-10 bg-white/3 border border-white/6 rounded-full flex-shrink-0">🔄</span>
              <div>
                <p class="text-[13.5px] font-bold text-white mb-1">Hỗ Trợ Đổi Size Miễn Phí</p>
                <p class="text-[12.5px] text-text-muted/55 m-0">Bảo hành 14 ngày, đổi size thuận tiện nhanh chóng nếu còn tem mác.</p>
              </div>
            </div>
          </div>

        </div>

      </div>

    </div>
  </div>
</template>
