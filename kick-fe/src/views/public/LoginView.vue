<script setup>
import { ref } from 'vue'
import { useRouter, RouterLink } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useResponsive } from '@/composables/useResponsive'

const router = useRouter()
const auth = useAuthStore()
const { isMobile } = useResponsive()

const email = ref('')
const password = ref('')
const error = ref('')
const loading = ref(false)

async function submit() {
  error.value = ''
  if (!email.value || !password.value) {
    error.value = 'Vui lòng nhập email và mật khẩu.'
    return
  }
  loading.value = true
  try {
    auth.login({ email: email.value, roles: ['CUSTOMER'] })
    router.push('/account')
  } catch {
    error.value = 'Email hoặc mật khẩu không chính xác.'
  } finally {
    loading.value = false
  }
}

function quick(role) {
  auth.demoAs(role)
  if (role === 'admin' || role === 'manager') router.push('/admin')
  else if (role === 'staff') router.push('/staff')
  else router.push('/account')
}

const quickRoles = [
  { key: 'customer', label: 'Customer' },
  { key: 'admin', label: 'Admin' },
  { key: 'manager', label: 'Quản lý' },
  { key: 'staff', label: 'Staff' },
]
</script>

<template>
  <div class="grid grid-cols-1 md:grid-cols-[1fr_480px] min-h-[100svh] bg-surface-0 text-text-primary">
    <!-- Left: Visual Panel -->
    <div v-if="!isMobile" class="relative overflow-hidden" aria-hidden="true">
      <img
        src="https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=1200&q=85&auto=format&fit=crop"
        alt=""
        class="w-full h-full object-cover object-center"
      />
      <div class="absolute inset-0 bg-gradient-to-tr from-neutral-950/85 via-neutral-950/40 to-neutral-950/70"></div>

      <!-- Floating brand text -->
      <div class="absolute bottom-0 inset-x-0 p-12">
        <div class="font-display text-4xl tracking-[4px] text-white mb-5">
          KICK<span class="bg-gradient-to-r from-primary via-primary-hover to-primary-pressed bg-clip-text text-transparent">VERSE</span>
        </div>
        <p class="text-4xl font-extrabold tracking-tight leading-[1.1] text-white mb-8">
          Nơi style<br/>bắt đầu từ đôi chân.
        </p>
        <div class="flex flex-col gap-3 mb-10">
          <div v-for="f in ['Drop độc quyền', 'Giao hàng 24h', 'Hoàn đổi 30 ngày']" :key="f" class="flex items-center gap-2.5 text-xs font-semibold text-text-secondary">
            <span class="w-1 h-1 rounded-full bg-primary flex-shrink-0"></span>
            {{ f }}
          </div>
        </div>
      </div>

      <!-- Decorative card -->
      <div class="absolute top-10 right-8 p-4 px-5.5 bg-white/5 backdrop-blur-md border border-primary/20 rounded-xl">
        <div class="text-[10px] font-bold tracking-widest uppercase text-primary mb-1.5">Thành viên Premium</div>
        <div class="text-lg font-bold text-white">10,000+ Members</div>
      </div>
    </div>

    <!-- Right: Form Panel -->
    <div class="flex items-center justify-center p-12 md:p-10 bg-surface-1 border-l border-white/5">
      <div class="w-full max-w-[380px]">
        <!-- Header -->
        <div class="mb-9">
          <RouterLink to="/" class="inline-flex items-center gap-1.5 text-xs font-medium text-text-muted hover:text-text-secondary transition-colors no-underline">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <polyline points="15 18 9 12 15 6"/>
            </svg>
            Về trang chủ
          </RouterLink>
        </div>

        <div class="font-display text-3xl tracking-[3px] text-white mb-6" v-if="isMobile">
          KICK<span class="bg-gradient-to-r from-primary via-primary-hover to-primary-pressed bg-clip-text text-transparent">VERSE</span>
        </div>

        <h1 class="text-3xl font-extrabold tracking-tight leading-[1.1] text-white mb-3">Chào mừng<br/>trở lại.</h1>
        <p class="text-sm text-text-muted mb-9">
          Chưa có tài khoản?
          <RouterLink to="/register" class="text-primary font-semibold no-underline hover:opacity-80 transition-opacity">Đăng ký ngay</RouterLink>
        </p>

        <!-- Form -->
        <form class="flex flex-col gap-4" @submit.prevent="submit" novalidate>
          <div class="flex flex-col gap-2">
            <label class="text-[11px] font-bold tracking-wider uppercase text-text-muted/80" for="login-email">Email</label>
            <input
              id="login-email"
              v-model="email"
              type="email"
              class="w-full px-4 py-3 text-sm text-white bg-white/3 border border-white/10 rounded-lg outline-none focus:border-primary/50 focus:bg-white/5 focus:shadow-[0_0_0_3px_rgb(200_169_110/0.08)] transition-all duration-200"
              placeholder="ban@email.com"
              autocomplete="email"
              required
            />
          </div>

          <div class="flex flex-col gap-2">
            <div class="flex justify-between items-center">
              <label class="text-[11px] font-bold tracking-wider uppercase text-text-muted/80" for="login-password">Mật khẩu</label>
              <a href="#" class="text-xs text-text-muted/60 hover:text-primary transition-colors no-underline">Quên mật khẩu?</a>
            </div>
            <input
              id="login-password"
              v-model="password"
              type="password"
              class="w-full px-4 py-3 text-sm text-white bg-white/3 border border-white/10 rounded-lg outline-none focus:border-primary/50 focus:bg-white/5 focus:shadow-[0_0_0_3px_rgb(200_169_110/0.08)] transition-all duration-200"
              :class="{ 'border-commerce focus:border-commerce focus:shadow-[0_0_0_3px_rgb(239_68_68/0.1)]': error }"
              placeholder="••••••••"
              autocomplete="current-password"
              required
            />
            <p v-if="error" class="text-xs text-commerce m-0">{{ error }}</p>
          </div>

          <button
            type="submit"
            class="w-full mt-2 py-3.5 text-xs font-bold tracking-widest uppercase text-black bg-gradient-to-r from-primary via-primary-hover to-primary-pressed rounded-lg shadow-lg hover:bg-right hover:-translate-y-0.5 hover:shadow-primary/30 transition-all duration-300 disabled:opacity-65 disabled:cursor-not-allowed disabled:transform-none flex items-center justify-center min-h-[44px] border-0 cursor-pointer"
            :disabled="loading"
          >
            <span v-if="!loading">Đăng nhập</span>
            <span v-else class="inline-block w-4 h-4 border-2 border-black/30 border-t-black rounded-full animate-spin"></span>
          </button>
        </form>

        <!-- Divider -->
        <div class="relative text-center my-7 flex items-center justify-center gap-4">
          <div class="flex-1 h-px bg-white/8"></div>
          <span class="text-[10px] font-bold tracking-widest text-text-muted/40 uppercase">hoặc thử nhanh theo role</span>
          <div class="flex-1 h-px bg-white/8"></div>
        </div>

        <!-- Quick Demo -->
        <div class="grid grid-cols-4 gap-2">
          <button
            v-for="r in quickRoles"
            :key="r.key"
            type="button"
            class="py-2.5 text-[11px] font-bold tracking-wider uppercase text-text-muted/65 bg-white/3 border border-white/10 rounded-md hover:text-primary hover:border-primary/30 hover:bg-primary/5 transition-all duration-200 cursor-pointer"
            @click="quick(r.key)"
          >
            {{ r.label }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
