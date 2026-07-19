<script setup>
import { RouterLink } from 'vue-router'
import KvBadge from './KvBadge.vue'

defineProps({
  product: {
    type: Object,
    required: true,
  },
  dark: {
    type: Boolean,
    default: true,
  },
})

defineEmits(['add-to-cart'])
</script>

<template>
  <article class="flex flex-col bg-surface-dark-card border border-white/5 rounded-xl overflow-hidden hover:-translate-y-1 hover:border-primary/15 hover:shadow-[0_12px_32px_rgba(0,0,0,0.6)] transition-all duration-300 relative group">
    <!-- Image Area -->
    <RouterLink :to="`/shop/${product.slug}`" class="relative w-full aspect-square overflow-hidden block bg-neutral-900 no-underline">
      <img
        :src="product.image"
        :alt="product.name"
        class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105"
        loading="lazy"
      />
      <div class="absolute inset-0 bg-gradient-to-t from-black/20 to-transparent pointer-events-none"></div>
      
      <!-- Badges -->
      <div class="absolute top-3 left-3 z-10 flex flex-wrap gap-1.5" v-if="product.badge">
        <KvBadge :variant="product.badgeVariant || 'gold'">
          {{ product.badge }}
        </KvBadge>
      </div>
    </RouterLink>

    <!-- Info Area -->
    <div class="p-5 flex flex-col flex-1 gap-4">
      <div class="flex flex-col gap-1">
        <span class="text-[11px] font-bold tracking-widest uppercase text-primary">{{ product.brand }}</span>
        <RouterLink :to="`/shop/${product.slug}`" class="text-base font-semibold text-white no-underline hover:text-primary transition-colors duration-200 line-clamp-2">
          {{ product.name }}
        </RouterLink>
      </div>

      <div class="flex items-center justify-between mt-auto gap-3">
        <div class="flex flex-col">
          <span class="text-base font-bold text-white">{{ product.priceLabel }}</span>
          <span v-if="product.compareAt" class="text-xs text-text-muted/40 line-through">{{ product.compareAt }}</span>
        </div>
        
        <button
          type="button"
          class="inline-flex items-center gap-1.5 h-9 px-4 text-[11px] font-extrabold tracking-wider text-black bg-primary border-0 rounded-md cursor-pointer hover:bg-primary-hover hover:-translate-y-0.5 hover:shadow-[0_4px_12px_rgba(200,169,110,0.25)] transition-all duration-200"
          aria-label="Thêm vào giỏ hàng"
          @click.stop.prevent="$emit('add-to-cart', product)"
        >
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
            <line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/>
          </svg>
          MUA
        </button>
      </div>
    </div>
  </article>
</template>
