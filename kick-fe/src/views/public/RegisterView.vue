<script setup>
import { ref } from 'vue'
import { useRouter, RouterLink } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useResponsive } from '@/composables/useResponsive'

const router = useRouter()
const auth = useAuthStore()
const { isMobile } = useResponsive()

const fullName = ref('')
const email = ref('')
const phone = ref('')
const password = ref('')
const confirm = ref('')
const error = ref('')
const loading = ref(false)

async function submit() {
  error.value = ''
  if (!fullName.value || !email.value || !password.value) {
    error.value = 'Vui lòng điền đầy đủ thông tin bắt buộc.'
    return
  }
  if (password.value !== confirm.value) {
    error.value = 'Mật khẩu xác nhận không khớp.'
    return
  }
  
  loading.value = true
  try {
    auth.login({ email: email.value, fullName: fullName.value, roles: ['CUSTOMER'] })
    router.push('/account')
  } catch {
    error.value = 'Đã có lỗi xảy ra. Vui lòng thử lại.'
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="grid grid-cols-1 md:grid-cols-[1fr_480px] min-h-[100svh] bg-surface-0 text-text-primary">
    <!-- Left Panel: Graphic & Content -->
    <div v-if="!isMobile" class="relative overflow-hidden" aria-hidden="true">
      <img
        src="https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=1200&q=85&auto=format&fit=crop"
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
          Trở thành một phần<br/>của văn hóa Sneaker.
        </p>
        <div class="flex flex-col gap-3 mb-10">
          <div v-for="f in ['Tích lũy điểm Member', 'Ưu tiên pre-order drops', 'Quà sinh nhật độc quyền']" :key="f" class="flex items-center gap-2.5 text-xs font-semibold text-text-secondary">
            <span class="w-1 h-1 rounded-full bg-primary flex-shrink-0"></span>
            {{ f }}
          </div>
        </div>
      </div>

      <!-- Decorative card -->
      <div class="absolute top-10 right-8 p-4 px-5.5 bg-white/5 backdrop-blur-md border border-primary/20 rounded-xl">
        <div class="text-[10px] font-bold tracking-widest uppercase text-primary mb-1.5">Đăng ký tham gia</div>
        <div class="text-lg font-bold text-white">KickVerse Member</div>
      </div>
    </div>

    <!-- Right Panel: Form -->
    <div class="flex items-center justify-center p-12 md:p-10 bg-surface-1 border-l border-white/5">
      <div class="w-full max-w-[380px]">
        <!-- Header -->
        <div class="mb-6">
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

        <h1 class="text-3xl font-extrabold tracking-tight leading-[1.1] text-white mb-3">Tạo tài khoản.</h1>
        <p class="text-sm text-text-muted mb-7">
          Đã có tài khoản?
          <RouterLink to="/login" class="text-primary font-semibold no-underline hover:opacity-80 transition-opacity">Đăng nhập</RouterLink>
        </p>

        <!-- Form -->
        <form class="flex flex-col gap-4" @submit.prevent="submit" novalidate>
          <div class="flex flex-col gap-2">
            <label class="text-[11px] font-bold tracking-wider uppercase text-text-muted/80" for="reg-name">Họ và tên</label>
            <input
              id="reg-name"
              v-model="fullName"
              type="text"
              class="w-full px-4 py-3 text-sm text-white bg-white/3 border border-white/10 rounded-lg outline-none focus:border-primary/50 focus:bg-white/5 focus:shadow-[0_0_0_3px_rgb(200_169_110/0.08)] transition-all duration-200"
              placeholder="Nguyễn Văn A"
              required
            />
          </div>

          <div class="flex flex-col gap-2">
            <label class="text-[11px] font-bold tracking-wider uppercase text-text-muted/80" for="reg-email">Email</label>
            <input
              id="reg-email"
              v-model="email"
              type="email"
              class="w-full px-4 py-3 text-sm text-white bg-white/3 border border-white/10 rounded-lg outline-none focus:border-primary/50 focus:bg-white/5 focus:shadow-[0_0_0_3px_rgb(200_169_110/0.08)] transition-all duration-200"
              placeholder="ban@email.com"
              autocomplete="email"
              required
            />
          </div>

          <div class="flex flex-col gap-2">
            <label class="text-[11px] font-bold tracking-wider uppercase text-text-muted/80" for="reg-phone">Số điện thoại</label>
            <input
              id="reg-phone"
              v-model="phone"
              type="tel"
              class="w-full px-4 py-3 text-sm text-white bg-white/3 border border-white/10 rounded-lg outline-none focus:border-primary/50 focus:bg-white/5 focus:shadow-[0_0_0_3px_rgb(200_169_110/0.08)] transition-all duration-200"
              placeholder="09xx xxx xxx"
            />
          </div>

          <div class="flex flex-col gap-2">
            <label class="text-[11px] font-bold tracking-wider uppercase text-text-muted/80" for="reg-password">Mật khẩu</label>
            <input
              id="reg-password"
              v-model="password"
              type="password"
              class="w-full px-4 py-3 text-sm text-white bg-white/3 border border-white/10 rounded-lg outline-none focus:border-primary/50 focus:bg-white/5 focus:shadow-[0_0_0_3px_rgb(200_169_110/0.08)] transition-all duration-200"
              placeholder="Tối thiểu 8 ký tự"
              autocomplete="new-password"
              required
            />
          </div>

          <div class="flex flex-col gap-2">
            <label class="text-[11px] font-bold tracking-wider uppercase text-text-muted/80" for="reg-confirm">Xác nhận mật khẩu</label>
            <input
              id="reg-confirm"
              v-model="confirm"
              type="password"
              class="w-full px-4 py-3 text-sm text-white bg-white/3 border border-white/10 rounded-lg outline-none focus:border-primary/50 focus:bg-white/5 focus:shadow-[0_0_0_3px_rgb(200_169_110/0.08)] transition-all duration-200"
              :class="{ 'border-commerce': error }"
              placeholder="Nhập lại mật khẩu"
              autocomplete="new-password"
              required
            />
            <p v-if="error" class="text-xs text-commerce m-0">{{ error }}</p>
          </div>

          <button
            type="submit"
            class="w-full mt-2 py-3.5 text-xs font-bold tracking-widest uppercase text-black bg-gradient-to-r from-primary via-primary-hover to-primary-pressed rounded-lg shadow-lg hover:bg-right hover:-translate-y-0.5 hover:shadow-primary/30 transition-all duration-300 disabled:opacity-65 disabled:cursor-not-allowed disabled:transform-none flex items-center justify-center min-h-[44px] border-0 cursor-pointer"
            :disabled="loading"
          >
            <span v-if="!loading">Đăng ký tài khoản</span>
            <span v-else class="inline-block w-4 h-4 border-2 border-black/30 border-t-black rounded-full animate-spin"></span>
          </button>
        </form>
      </div>
    </div>
  </div>
</template>
