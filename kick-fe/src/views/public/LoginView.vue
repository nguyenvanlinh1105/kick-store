<script setup>
import { ref } from 'vue'
import { RouterLink, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const auth = useAuthStore()

const accountInput = ref('nguyenvana@gmail.com')
const passwordInput = ref('12345678')
const rememberMe = ref(true)
const isLoading = ref(false)
const isGoogleLoading = ref(false)

function handleLogin() {
  if (!accountInput.value || !passwordInput.value) return
  isLoading.value = true
  setTimeout(() => {
    isLoading.value = false
    auth.login({
      fullName: 'Nguyễn Văn A',
      email: accountInput.value,
      avatar: 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150&q=80',
    })
    router.push('/account')
  }, 500)
}

function handleGoogleLogin() {
  isGoogleLoading.value = true
  setTimeout(() => {
    isGoogleLoading.value = false
    auth.login({
      fullName: 'Nguyễn Văn A (Google)',
      email: 'nguyenvana@gmail.com',
      avatar: 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150&q=80',
    })
    router.push('/account')
  }, 600)
}
</script>

<template>
  <div class="bg-slate-50 pt-8 pb-24 flex items-center justify-center text-slate-900 px-4">
    <div class="w-full max-w-md bg-white border border-slate-200 rounded-3xl p-8 md:p-10 shadow-xl flex flex-col gap-6">
      
      <!-- Brand Header -->
      <div class="text-center flex flex-col items-center">
        <RouterLink to="/" class="font-display text-3xl tracking-[3px] text-slate-900 no-underline mb-2">
          KICK<span class="text-amber-600 font-extrabold">VERSE</span>
        </RouterLink>
        <h1 class="text-xl font-extrabold text-slate-900">Đăng Nhập Tài Khoản</h1>
        <p class="text-xs text-slate-500 mt-1">Truy cập để quản lý đơn hàng & tích điểm thành viên</p>
      </div>

      <!-- PRIMARY: GOOGLE SINGLE SIGN-ON (1-CLICK GOOGLE LOGIN) -->
      <div class="flex flex-col gap-3">
        <button
          type="button"
          :disabled="isGoogleLoading"
          class="w-full h-13 bg-white border-2 border-slate-200 hover:border-amber-600 text-slate-800 font-extrabold text-xs rounded-2xl flex items-center justify-center gap-3 transition-all cursor-pointer shadow-sm hover:shadow-md active:scale-98 disabled:opacity-50"
          @click="handleGoogleLogin"
        >
          <svg class="w-5 h-5 shrink-0" viewBox="0 0 24 24">
            <path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
            <path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
            <path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.06H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.94l2.85-2.22.81-.63z"/>
            <path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.06l3.66 2.84c.87-2.6 3.3-4.52 6.16-4.52z"/>
          </svg>
          <span>{{ isGoogleLoading ? 'Đang xác thực Google...' : 'TIẾP TỤC VỚI GOOGLE (1-CLICK)' }}</span>
        </button>

      </div>

      <!-- Divider -->
      <div class="relative flex items-center justify-center my-1">
        <div class="absolute inset-0 flex items-center"><div class="w-full border-t border-slate-200"></div></div>
        <span class="relative bg-white px-3 text-[11px] text-slate-400 font-bold uppercase">Hoặc dùng Email</span>
      </div>

      <!-- Traditional Email Form -->
      <form @submit.prevent="handleLogin" class="flex flex-col gap-4">
        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-slate-700">Email hoặc Số điện thoại</label>
          <input
            v-model="accountInput"
            type="text"
            required
            placeholder="name@example.com"
            class="h-11 px-4 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 focus:outline-none focus:border-amber-600 font-medium"
          />
        </div>

        <div class="flex flex-col gap-1.5">
          <div class="flex items-center justify-between">
            <label class="text-xs font-bold text-slate-700">Mật khẩu</label>
            <RouterLink to="/forgot-password" class="text-xs text-amber-600 font-extrabold hover:underline no-underline">Quên mật khẩu?</RouterLink>
          </div>
          <input
            v-model="passwordInput"
            type="password"
            required
            placeholder="••••••••"
            class="h-11 px-4 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 focus:outline-none focus:border-amber-600 font-medium"
          />
        </div>

        <label class="flex items-center gap-2 text-xs text-slate-600 cursor-pointer">
          <input type="checkbox" v-model="rememberMe" class="accent-amber-600 w-4 h-4 rounded" />
          <span>Ghi nhớ đăng nhập</span>
        </label>

        <button
          type="submit"
          :disabled="isLoading"
          class="h-12 bg-slate-900 text-white font-extrabold text-xs uppercase tracking-wider rounded-xl hover:bg-amber-600 transition-all cursor-pointer shadow-md disabled:opacity-50"
        >
          {{ isLoading ? 'Đang xử lý...' : 'ĐĂNG NHẬP BẰNG EMAIL' }}
        </button>
      </form>

      <p class="text-center text-xs text-slate-500 border-t border-slate-100 pt-4">
        Chưa có tài khoản?
        <RouterLink to="/register" class="text-amber-600 font-extrabold hover:underline no-underline ml-1">Đăng ký ngay</RouterLink>
      </p>

    </div>
  </div>
</template>
