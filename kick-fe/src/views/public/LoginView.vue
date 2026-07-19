<script setup>
import { ref, computed } from 'vue'
import { useRouter, RouterLink } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import authService from '@/services/authService'

const router = useRouter()
const auth = useAuthStore()

const email = ref('')
const password = ref('')
const emailError = ref('')
const passwordError = ref('')
const generalError = ref('')
const loading = ref(false)

// Smooth interactive states
const emailFocused = ref(false)
const passwordFocused = ref(false)

const isEmailValid = computed(() => {
  if (!email.value) return false
  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  return re.test(email.value)
})

function validateForm() {
  let isValid = true
  emailError.value = ''
  passwordError.value = ''
  generalError.value = ''

  if (!email.value) {
    emailError.value = 'Vui lòng nhập địa chỉ email.'
    isValid = false
  } else if (!isEmailValid.value) {
    emailError.value = 'Địa chỉ email không hợp lệ.'
    isValid = false
  }

  if (!password.value) {
    passwordError.value = 'Vui lòng nhập mật khẩu.'
    isValid = false
  } else if (password.value.length < 8) {
    passwordError.value = 'Mật khẩu phải chứa ít nhất 8 ký tự.'
    isValid = false
  }

  return isValid
}

async function submit() {
  if (!validateForm()) return

  loading.value = true
  generalError.value = ''
  try {
    const res = await authService.login({
      email: email.value,
      password: password.value,
    })
    
    // res is ApiResponse<LoginResponse>, data contains user/tokens
    auth.login(res.data)
    
    // Redirect logic based on role
    if (auth.isAdmin) {
      router.push('/admin')
    } else if (auth.isStaff) {
      router.push('/staff')
    } else {
      const redirectQuery = router.currentRoute.value.query.redirect
      if (redirectQuery) {
        router.push(decodeURIComponent(redirectQuery))
      } else {
        router.push('/account')
      }
    }
  } catch (error) {
    console.error('Login error:', error)
    generalError.value = error.response?.data?.message || 'Tên đăng nhập hoặc mật khẩu không khớp.'
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div
    class="relative flex items-center justify-center bg-canvas-light pt-6 pb-10 px-6 overflow-hidden"
  >
    <!-- Subtle neutral background accents for modern clean aesthetic -->
    <div
      class="absolute top-1/4 left-1/4 w-96 h-96 bg-neutral-200/20 rounded-full blur-[100px] pointer-events-none"
      aria-hidden="true"
    ></div>
    <div
      class="absolute bottom-1/4 right-1/4 w-[400px] h-[400px] bg-neutral-100/30 rounded-full blur-[120px] pointer-events-none"
      aria-hidden="true"
    ></div>

    <div class="w-full max-w-2xl relative z-10 animate-fade-in-up">
      <!-- Main Frosted Glass Card -->
      <div
        class="bg-white/80 backdrop-blur-xl border border-black/5 rounded-2xl px-6 py-10 shadow-[0_12px_40px_rgba(0,0,0,0.03)]"
      >
        <!-- Brand Signature -->
        <div class="text-center mb-8 flex flex-col items-center">
          <img src="/logo.png?v=2" alt="KickVerse Logo" class="h-24 w-auto object-contain" />
          <h1 class="text-2xl font-bold tracking-tight text-neutral-900 mt-4 mb-2">
            Chào mừng trở lại
          </h1>
          <p class="text-sm text-neutral-500">Đăng nhập tài khoản để tiếp tục trải nghiệm</p>
        </div>

        <!-- General Error Alert -->
        <div
          v-if="generalError"
          class="mb-6 p-4 bg-commerce/5 border border-commerce/20 rounded-xl flex items-center gap-3 text-xs font-medium text-commerce"
        >
          <svg
            width="16"
            height="16"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2.5"
          >
            <circle cx="12" cy="12" r="10" />
            <line x1="12" y1="8" x2="12" y2="12" />
            <line x1="12" y1="16" x2="12.01" y2="16" />
          </svg>
          {{ generalError }}
        </div>

        <!-- Form fields -->
        <form class="flex flex-col gap-5" @submit.prevent="submit" novalidate>
          <!-- Email Field -->
          <div class="flex flex-col gap-1.5 relative">
            <div class="flex justify-between items-center px-1">
              <label
                class="text-[11px] font-bold tracking-wider uppercase text-neutral-500"
                for="login-email"
                >Email</label
              >
              <span
                v-if="isEmailValid"
                class="text-[10px] font-bold text-neutral-800 flex items-center gap-1"
              >
                <svg
                  width="12"
                  height="12"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="3"
                >
                  <polyline points="20 6 9 17 4 12" />
                </svg>
                Hợp lệ
              </span>
            </div>

            <div class="relative">
              <input
                id="login-email"
                v-model="email"
                type="email"
                class="w-full h-12 px-4 text-[14.5px] text-black bg-neutral-50/50 border rounded-xl outline-none transition-all duration-200"
                :class="[
                  emailError
                    ? 'border-commerce focus:shadow-[0_0_0_3px_rgba(239,68,68,0.08)]'
                    : 'border-neutral-200/80 focus:border-black focus:shadow-[0_0_0_3px_rgba(0,0,0,0.04)]',
                  emailFocused ? 'bg-white' : '',
                ]"
                placeholder="tenban@email.com"
                autocomplete="email"
                required
                @focus="emailFocused = true"
                @blur="emailFocused = false"
              />
            </div>
            <p
              v-if="emailError"
              class="text-xs text-commerce px-1 mt-1 font-medium flex items-center gap-1.5"
            >
              <svg
                width="12"
                height="12"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="2.5"
              >
                <circle cx="12" cy="12" r="10" />
                <line x1="12" y1="8" x2="12" y2="12" />
                <line x1="12" y1="16" x2="12.01" y2="16" />
              </svg>
              {{ emailError }}
            </p>
          </div>

          <!-- Password Field -->
          <div class="flex flex-col gap-1.5">
            <div class="flex justify-between items-center px-1">
              <label
                class="text-[11px] font-bold tracking-wider uppercase text-neutral-500"
                for="login-password"
                >Mật khẩu</label
              >
              <a
                href="#"
                class="text-xs font-semibold text-neutral-400 hover:text-black transition-colors no-underline"
                >Quên mật khẩu?</a
              >
            </div>

            <div class="relative">
              <input
                id="login-password"
                v-model="password"
                type="password"
                class="w-full h-12 px-4 text-[14.5px] text-black bg-neutral-50/50 border rounded-xl outline-none transition-all duration-200"
                :class="[
                  passwordError
                    ? 'border-commerce focus:shadow-[0_0_0_3px_rgba(239,68,68,0.08)]'
                    : 'border-neutral-200/80 focus:border-black focus:shadow-[0_0_0_3px_rgba(0,0,0,0.04)]',
                  passwordFocused ? 'bg-white' : '',
                ]"
                placeholder="Nhập tối thiểu 8 ký tự"
                autocomplete="current-password"
                required
                @focus="passwordFocused = true"
                @blur="passwordFocused = false"
              />
            </div>
            <p
              v-if="passwordError"
              class="text-xs text-commerce px-1 mt-1 font-medium flex items-center gap-1.5"
            >
              <svg
                width="12"
                height="12"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="2.5"
              >
                <circle cx="12" cy="12" r="10" />
                <line x1="12" y1="8" x2="12" y2="12" />
                <line x1="12" y1="16" x2="12.01" y2="16" />
              </svg>
              {{ passwordError }}
            </p>
          </div>

          <!-- Submit Button -->
          <button
            type="submit"
            class="w-full mt-4 h-12 text-xs font-bold tracking-widest uppercase text-white bg-black hover:bg-neutral-800 active:scale-[0.98] rounded-xl shadow-md transition-all duration-200 flex items-center justify-center border-0 cursor-pointer"
            :disabled="loading"
          >
            <span v-if="!loading" class="flex items-center gap-2">
              Đăng nhập
              <svg
                width="14"
                height="14"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="2.5"
              >
                <line x1="5" y1="12" x2="19" y2="12" />
                <polyline points="12 5 19 12 12 19" />
              </svg>
            </span>
            <span
              v-else
              class="inline-block w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin"
            ></span>
          </button>
        </form>

        <!-- Redirect to register -->
        <p class="text-center text-sm text-neutral-500 mt-8 mb-0">
          Chưa có tài khoản?
          <RouterLink to="/register" class="text-black font-bold no-underline hover:underline"
            >Đăng ký thành viên</RouterLink
          >
        </p>
      </div>
    </div>
  </div>
</template>
