<script setup>
import { RouterLink } from 'vue-router'
import { storeToRefs } from 'pinia'
import { ref, onMounted, onUnmounted } from 'vue'
import { useResponsive } from '@/composables/useResponsive'
import { useAuthStore } from '@/stores/auth'
import { useCartStore } from '@/stores/cart'
import { useWishlistStore } from '@/stores/wishlist'
import { useNotificationStore } from '@/stores/notification'
import KvAvatar from '@/components/ui/KvAvatar.vue'

const { isMobile } = useResponsive()
const auth = useAuthStore()
const cart = useCartStore()
const wishlist = useWishlistStore()
const notifications = useNotificationStore()
const { user, isAuthenticated } = storeToRefs(auth)
const { count: cartCount } = storeToRefs(cart)
const { count: wishCount } = storeToRefs(wishlist)
const { unreadCount } = storeToRefs(notifications)

const menuOpen = ref(false)
const scrolled = ref(false)

function onScroll() {
  scrolled.value = window.scrollY > 20
}

onMounted(() => window.addEventListener('scroll', onScroll, { passive: true }))
onUnmounted(() => window.removeEventListener('scroll', onScroll))

const links = [
  { to: '/shop', label: 'Cửa hàng' },
  { to: '/shop?cat=running', label: 'Running' },
  { to: '/shop?cat=lifestyle', label: 'Lifestyle' },
  { to: '/support', label: 'Hỗ trợ' },
]
</script>

<template>
  <header
    class="sticky top-0 z-50 transition-all duration-300 border-b"
    :class="[
      scrolled 
        ? 'bg-white/95 backdrop-blur-2xl shadow-sm border-black/10' 
        : 'bg-white/80 backdrop-blur-xl border-black/5',
      menuOpen ? 'bg-white' : ''
    ]"
  >
    <div class="kv-container flex items-center justify-between h-16 gap-8">
      <!-- Logo & Mobile menu button -->
      <div class="flex items-center gap-3">
        <button
          v-if="isMobile"
          type="button"
          class="flex flex-col justify-center items-center gap-1.5 w-10 h-10 p-2 bg-transparent border-0 cursor-pointer"
          aria-label="Menu"
          @click="menuOpen = !menuOpen"
        >
          <span 
            class="block w-6 h-0.5 bg-black rounded transition-all duration-300"
            :class="{ 'transform translate-y-2 rotate-45': menuOpen }"
          ></span>
          <span 
            class="block w-6 h-0.5 bg-black rounded transition-all duration-300"
            :class="{ 'opacity-0 scale-x-0': menuOpen }"
          ></span>
          <span 
            class="block w-6 h-0.5 bg-black rounded transition-all duration-300"
            :class="{ 'transform -translate-y-2 -rotate-45': menuOpen }"
          ></span>
        </button>
        
        <RouterLink to="/" class="flex items-center no-underline group">
          <span class="font-display text-2xl tracking-[3px] text-black transition-colors duration-200 group-hover:text-primary">
            KICK<span class="bg-gradient-to-r from-primary via-primary-hover to-primary-pressed bg-clip-text text-transparent">VERSE</span>
          </span>
        </RouterLink>
      </div>

      <!-- Desktop Navigation links -->
      <nav v-if="!isMobile" class="hidden md:flex flex-1 justify-center items-center gap-1" role="navigation" aria-label="Main Navigation">
        <RouterLink
          v-for="link in links"
          :key="link.to"
          :to="link.to"
          class="relative px-3.5 py-1.5 text-xs font-bold tracking-wider uppercase text-black/65 hover:text-black hover:bg-black/5 rounded-md transition-all duration-200 no-underline"
          active-class="!text-primary"
        >
          {{ link.label }}
        </RouterLink>
      </nav>

      <!-- Action Buttons -->
      <div class="flex items-center gap-1">
        <!-- Search icon -->
        <RouterLink to="/shop" class="relative w-10 h-10 flex items-center justify-center rounded-lg text-black/70 hover:text-black hover:bg-black/5 transition-all duration-200 no-underline" aria-label="Tìm kiếm">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/>
          </svg>
        </RouterLink>

        <!-- Notification Panel Trigger -->
        <button type="button" class="relative w-10 h-10 flex items-center justify-center rounded-lg text-black/70 hover:text-black hover:bg-black/5 transition-all duration-200 border-0 bg-transparent cursor-pointer" aria-label="Thông báo" @click="notifications.openPanel()">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.73 21a2 2 0 0 1-3.46 0"/>
          </svg>
          <span v-if="unreadCount" class="absolute top-1 right-1 min-w-4 h-4 px-1 rounded-full bg-commerce text-white text-[10px] font-bold flex items-center justify-center leading-none shadow-sm">{{ unreadCount }}</span>
        </button>

        <!-- Wishlist -->
        <RouterLink to="/wishlist" class="relative w-10 h-10 flex items-center justify-center rounded-lg text-black/70 hover:text-black hover:bg-black/5 transition-all duration-200 no-underline" aria-label="Yêu thích">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/>
          </svg>
          <span v-if="wishCount" class="absolute top-1 right-1 min-w-4 h-4 px-1 rounded-full bg-primary text-black text-[10px] font-bold flex items-center justify-center leading-none shadow-sm">{{ wishCount }}</span>
        </RouterLink>

        <!-- Cart drawer trigger -->
        <button type="button" class="relative w-10 h-10 flex items-center justify-center rounded-lg text-black/70 hover:text-black hover:bg-black/5 transition-all duration-200 border-0 bg-transparent cursor-pointer" aria-label="Giỏ hàng" @click="cart.openDrawer()">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z"/><line x1="3" y1="6" x2="21" y2="6"/><path d="M16 10a4 4 0 0 1-8 0"/>
          </svg>
          <span v-if="cartCount" class="absolute top-1 right-1 min-w-4 h-4 px-1 rounded-full bg-commerce text-white text-[10px] font-bold flex items-center justify-center leading-none shadow-sm">{{ cartCount }}</span>
        </button>

        <!-- User avatar / Login link -->
        <RouterLink v-if="isAuthenticated" to="/account" class="w-10 h-10 flex items-center justify-center rounded-lg hover:bg-black/5 transition-all duration-200 ml-1 no-underline" aria-label="Tài khoản">
          <KvAvatar :name="user.fullName" size="sm" />
        </RouterLink>
        <RouterLink v-else to="/login" class="inline-flex items-center px-4 py-2 ml-1 text-xs font-bold tracking-wider uppercase text-white bg-black hover:bg-neutral-800 rounded-md transition-all duration-200 active:scale-95 no-underline" aria-label="Đăng nhập">
          Đăng nhập
        </RouterLink>
      </div>
    </div>

    <!-- Mobile Drawer menu -->
    <Transition
      enter-active-class="transition duration-200 ease-out"
      enter-from-class="opacity-0 -translate-y-2"
      enter-to-class="opacity-100 translate-y-0"
      leave-active-class="transition duration-150 ease-in"
      leave-from-class="opacity-100 translate-y-0"
      leave-to-class="opacity-0 -translate-y-2"
    >
      <div v-if="isMobile && menuOpen" class="w-full bg-white border-t border-black/10 py-3 px-6 shadow-lg" role="navigation" aria-label="Mobile Navigation">
        <div class="flex flex-col">
          <RouterLink
            v-for="link in links"
            :key="link.to"
            :to="link.to"
            class="flex items-center justify-between py-4 border-b border-black/5 text-sm font-bold tracking-wider uppercase text-black/70 hover:text-primary hover:bg-primary/5 transition-all duration-200 no-underline"
            @click="menuOpen = false"
          >
            <span>{{ link.label }}</span>
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
              <polyline points="9 18 15 12 9 6"/>
            </svg>
          </RouterLink>
          
          <RouterLink 
            to="/account" 
            class="flex items-center justify-between py-4 border-b border-black/5 text-sm font-bold tracking-wider uppercase text-black/70 hover:text-primary hover:bg-primary/5 transition-all duration-200 no-underline" 
            @click="menuOpen = false"
          >
            <span>Tài khoản</span>
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
              <polyline points="9 18 15 12 9 6"/>
            </svg>
          </RouterLink>
        </div>
        <div class="mt-6">
          <RouterLink v-if="!isAuthenticated" to="/login" class="block w-full py-4 text-center text-xs font-bold tracking-wider uppercase text-white bg-black hover:bg-neutral-800 rounded-lg no-underline transition-all duration-200" @click="menuOpen = false">
            Đăng nhập / Đăng ký
          </RouterLink>
        </div>
      </div>
    </Transition>
  </header>
</template>
