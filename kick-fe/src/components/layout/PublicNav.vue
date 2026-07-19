<script setup>
import { RouterLink, useLink } from 'vue-router'
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
    class="kv-nav"
    :class="{ 'kv-nav--scrolled': scrolled, 'kv-nav--open': menuOpen }"
  >
    <div class="kv-container kv-nav__inner">
      <!-- Logo -->
      <div class="kv-nav__logo-wrap">
        <button
          v-if="isMobile"
          type="button"
          class="kv-nav__hamburger"
          aria-label="Menu"
          @click="menuOpen = !menuOpen"
        >
          <span class="kv-hamburger-bar" :class="{ 'kv-hamburger-bar--open': menuOpen }"></span>
          <span class="kv-hamburger-bar" :class="{ 'kv-hamburger-bar--open': menuOpen }"></span>
          <span class="kv-hamburger-bar" :class="{ 'kv-hamburger-bar--open': menuOpen }"></span>
        </button>
        <RouterLink to="/" class="kv-nav__logo" aria-label="KickVerse Home">
          <span class="kv-nav__logo-text">KICK<span class="kv-nav__logo-accent">VERSE</span></span>
        </RouterLink>
      </div>

      <!-- Desktop Navigation -->
      <nav v-if="!isMobile" class="kv-nav__links" role="navigation" aria-label="Điều hướng chính">
        <RouterLink
          v-for="link in links"
          :key="link.to"
          :to="link.to"
          class="kv-nav__link"
          active-class="kv-nav__link--active"
        >
          {{ link.label }}
        </RouterLink>
      </nav>

      <!-- Actions -->
      <div class="kv-nav__actions">
        <!-- Search -->
        <RouterLink to="/shop" class="kv-nav__action-btn" aria-label="Tìm kiếm">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/>
          </svg>
        </RouterLink>

        <!-- Notifications -->
        <button type="button" class="kv-nav__action-btn kv-nav__action-btn--relative" aria-label="Thông báo" @click="notifications.openPanel()">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.73 21a2 2 0 0 1-3.46 0"/>
          </svg>
          <span v-if="unreadCount" class="kv-nav__badge">{{ unreadCount }}</span>
        </button>

        <!-- Wishlist -->
        <RouterLink to="/wishlist" class="kv-nav__action-btn kv-nav__action-btn--relative" aria-label="Yêu thích">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/>
          </svg>
          <span v-if="wishCount" class="kv-nav__badge kv-nav__badge--gold">{{ wishCount }}</span>
        </RouterLink>

        <!-- Cart -->
        <button type="button" class="kv-nav__action-btn kv-nav__action-btn--relative" aria-label="Giỏ hàng" @click="cart.openDrawer()">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z"/><line x1="3" y1="6" x2="21" y2="6"/><path d="M16 10a4 4 0 0 1-8 0"/>
          </svg>
          <span v-if="cartCount" class="kv-nav__badge">{{ cartCount }}</span>
        </button>

        <!-- User -->
        <RouterLink v-if="isAuthenticated" to="/account" class="kv-nav__action-btn" aria-label="Tài khoản">
          <KvAvatar :name="user.fullName" size="sm" />
        </RouterLink>
        <RouterLink v-else to="/login" class="kv-nav__login-btn" aria-label="Đăng nhập">
          Đăng nhập
        </RouterLink>
      </div>
    </div>

    <!-- Mobile Menu Drawer -->
    <Transition name="kv-drawer">
      <div v-if="isMobile && menuOpen" class="kv-nav__mobile-menu" role="navigation" aria-label="Điều hướng di động">
        <nav class="kv-nav__mobile-links">
          <RouterLink
            v-for="link in links"
            :key="link.to"
            :to="link.to"
            class="kv-nav__mobile-link"
            @click="menuOpen = false"
          >
            <span>{{ link.label }}</span>
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <polyline points="9 18 15 12 9 6"/>
            </svg>
          </RouterLink>
          <RouterLink to="/account" class="kv-nav__mobile-link" @click="menuOpen = false">
            <span>Tài khoản</span>
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <polyline points="9 18 15 12 9 6"/>
            </svg>
          </RouterLink>
        </nav>
        <div class="kv-nav__mobile-footer">
          <RouterLink v-if="!isAuthenticated" to="/login" class="kv-nav__mobile-cta" @click="menuOpen = false">
            Đăng nhập / Đăng ký
          </RouterLink>
        </div>
      </div>
    </Transition>
  </header>
</template>

<style scoped>
/* ── Nav Shell ── */
.kv-nav {
  position: sticky;
  top: 0;
  z-index: 200;
  background: rgb(10 10 10 / 0.85);
  backdrop-filter: blur(24px) saturate(180%);
  -webkit-backdrop-filter: blur(24px) saturate(180%);
  border-bottom: 1px solid rgb(255 255 255 / 0.06);
  transition: background 0.3s ease, box-shadow 0.3s ease, border-color 0.3s ease;
}

.kv-nav--scrolled {
  background: rgb(10 10 10 / 0.96);
  box-shadow: 0 8px 32px rgb(0 0 0 / 0.5);
  border-bottom-color: rgb(200 169 110 / 0.12);
}

.kv-nav__inner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64px;
  gap: 32px;
}

/* ── Logo ── */
.kv-nav__logo-wrap {
  display: flex;
  align-items: center;
  gap: 12px;
  flex-shrink: 0;
}

.kv-nav__logo {
  display: flex;
  align-items: center;
  text-decoration: none;
}

.kv-nav__logo-text {
  font-family: 'Bebas Neue', sans-serif;
  font-size: 24px;
  letter-spacing: 3px;
  color: #f5f4f0;
  transition: color 0.2s;
}

.kv-nav__logo-accent {
  color: #c8a96e;
  background: linear-gradient(135deg, #c8a96e, #e8c97e, #c8a96e);
  background-clip: text;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.kv-nav__logo:hover .kv-nav__logo-text { color: #c8a96e; }

/* ── Desktop Links ── */
.kv-nav__links {
  display: flex;
  align-items: center;
  gap: 4px;
  flex: 1;
  justify-content: center;
}

.kv-nav__link {
  position: relative;
  padding: 6px 14px;
  font-size: 13px;
  font-weight: 600;
  letter-spacing: 0.8px;
  text-transform: uppercase;
  color: rgb(245 244 240 / 0.65);
  border-radius: 6px;
  transition: color 0.2s, background 0.2s;
  text-decoration: none;
}

.kv-nav__link::after {
  content: '';
  position: absolute;
  bottom: -2px;
  left: 14px;
  right: 14px;
  height: 1px;
  background: #c8a96e;
  transform: scaleX(0);
  transform-origin: center;
  transition: transform 0.25s cubic-bezier(0.25, 0.46, 0.45, 0.94);
}

.kv-nav__link:hover {
  color: #f5f4f0;
  background: rgb(255 255 255 / 0.05);
}

.kv-nav__link--active {
  color: #c8a96e !important;
}

.kv-nav__link--active::after {
  transform: scaleX(1);
}

/* ── Action Buttons ── */
.kv-nav__actions {
  display: flex;
  align-items: center;
  gap: 4px;
  flex-shrink: 0;
}

.kv-nav__action-btn {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: 8px;
  background: transparent;
  border: none;
  cursor: pointer;
  color: rgb(245 244 240 / 0.7);
  transition: color 0.2s, background 0.2s;
  text-decoration: none;
}

.kv-nav__action-btn--relative { position: relative; }

.kv-nav__action-btn:hover {
  color: #f5f4f0;
  background: rgb(255 255 255 / 0.07);
}

/* Badge */
.kv-nav__badge {
  position: absolute;
  top: 4px;
  right: 4px;
  min-width: 16px;
  height: 16px;
  padding: 0 4px;
  border-radius: 8px;
  background: #ef4444;
  color: #fff;
  font-size: 10px;
  font-weight: 700;
  line-height: 16px;
  text-align: center;
}

.kv-nav__badge--gold {
  background: #c8a96e;
  color: #0a0a0a;
}

/* Login Button */
.kv-nav__login-btn {
  display: flex;
  align-items: center;
  padding: 8px 18px;
  margin-left: 4px;
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 1px;
  text-transform: uppercase;
  color: #0a0a0a;
  background: #c8a96e;
  border-radius: 6px;
  text-decoration: none;
  transition: background 0.2s, transform 0.15s;
}

.kv-nav__login-btn:hover {
  background: #d4ba80;
  transform: translateY(-1px);
}

/* ── Hamburger ── */
.kv-nav__hamburger {
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 5px;
  width: 40px;
  height: 40px;
  padding: 10px 8px;
  background: transparent;
  border: none;
  cursor: pointer;
}

.kv-hamburger-bar {
  display: block;
  width: 100%;
  height: 1.5px;
  background: #f5f4f0;
  border-radius: 1px;
  transition: transform 0.25s, opacity 0.25s;
  transform-origin: center;
}

.kv-hamburger-bar--open:nth-child(1) { transform: translateY(6.5px) rotate(45deg); }
.kv-hamburger-bar--open:nth-child(2) { opacity: 0; transform: scaleX(0); }
.kv-hamburger-bar--open:nth-child(3) { transform: translateY(-6.5px) rotate(-45deg); }

/* ── Mobile Menu ── */
.kv-nav__mobile-menu {
  background: rgb(10 10 10 / 0.97);
  backdrop-filter: blur(24px);
  border-top: 1px solid rgb(255 255 255 / 0.06);
  padding: 12px 0 24px;
}

.kv-nav__mobile-links {
  display: flex;
  flex-direction: column;
}

.kv-nav__mobile-link {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 24px;
  font-size: 14px;
  font-weight: 600;
  letter-spacing: 0.5px;
  text-transform: uppercase;
  color: rgb(245 244 240 / 0.7);
  border-bottom: 1px solid rgb(255 255 255 / 0.04);
  transition: color 0.2s, background 0.2s;
  text-decoration: none;
}

.kv-nav__mobile-link:hover {
  color: #c8a96e;
  background: rgb(200 169 110 / 0.05);
}

.kv-nav__mobile-footer {
  padding: 20px 24px 0;
}

.kv-nav__mobile-cta {
  display: block;
  width: 100%;
  padding: 14px;
  text-align: center;
  font-size: 13px;
  font-weight: 700;
  letter-spacing: 1px;
  text-transform: uppercase;
  color: #0a0a0a;
  background: #c8a96e;
  border-radius: 8px;
  text-decoration: none;
  transition: background 0.2s;
}

.kv-nav__mobile-cta:hover { background: #d4ba80; }

/* ── Transitions ── */
.kv-drawer-enter-active,
.kv-drawer-leave-active {
  transition: opacity 0.2s ease, transform 0.25s cubic-bezier(0.16, 1, 0.3, 1);
}
.kv-drawer-enter-from,
.kv-drawer-leave-to {
  opacity: 0;
  transform: translateY(-8px);
}
</style>
