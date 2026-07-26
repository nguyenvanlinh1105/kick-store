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
    default: false, // Default to clean light card
  },
})

defineEmits(['add-to-cart'])
</script>

<template>
  <article 
    class="flex flex-col border rounded-2xl overflow-hidden hover:-translate-y-1.5 transition-all duration-300 relative group shadow-sm hover:shadow-xl"
    :class="[
      dark 
        ? 'bg-neutral-900 border-white/10 hover:border-amber-500/30 text-white' 
        : 'bg-white border-slate-200 hover:border-amber-500/40 text-slate-900'
    ]"
  >
    <!-- Image Area -->
    <RouterLink 
      :to="`/shop/${product.slug}`" 
      class="relative w-full aspect-square overflow-hidden block no-underline"
      :class="[dark ? 'bg-neutral-950' : 'bg-slate-50']"
    >
      <img
        :src="product.image"
        :alt="product.name"
        class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-108"
        loading="lazy"
      />
      <div 
        class="absolute inset-0 pointer-events-none"
        :class="[dark ? 'bg-gradient-to-t from-black/30 to-transparent' : 'bg-black/5']"
      ></div>
      
      <!-- Badges -->
      <div class="absolute top-3 left-3 z-10 flex flex-wrap gap-1.5" v-if="product.badge">
        <KvBadge :variant="product.badgeVariant || 'gold'">
          {{ product.badge }}
        </KvBadge>
      </div>
    </RouterLink>

    <!-- Info Area -->
    <div class="p-5 flex flex-col flex-1 gap-3">
      <div class="flex flex-col gap-1">
        <span class="text-[11px] font-extrabold tracking-widest uppercase text-amber-600">{{ product.brand }}</span>
        <RouterLink 
          :to="`/shop/${product.slug}`" 
          class="text-sm font-extrabold no-underline transition-colors duration-200 line-clamp-2 leading-snug"
          :class="[
            dark 
              ? 'text-white hover:text-amber-400' 
              : 'text-slate-900 hover:text-amber-600'
          ]"
        >
          {{ product.name }}
        </RouterLink>
      </div>

      <div class="flex items-center justify-between mt-auto gap-2 pt-2 border-t" :class="[dark ? 'border-white/10' : 'border-slate-100']">
        <div class="flex flex-col">
          <span 
            class="text-base font-extrabold"
            :class="[dark ? 'text-white' : 'text-slate-900']"
          >
            {{ product.priceLabel }}
          </span>
          <span 
            v-if="product.compareAt" 
            class="text-xs line-through"
            :class="[dark ? 'text-neutral-500' : 'text-slate-400']"
          >
            {{ product.compareAt }}
          </span>
        </div>
        
        <button
          type="button"
          class="inline-flex items-center gap-1.5 h-9 px-3.5 text-[11px] font-extrabold tracking-wider text-white bg-slate-900 border-0 rounded-xl cursor-pointer hover:bg-amber-600 hover:-translate-y-0.5 transition-all duration-200 shadow-md"
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
