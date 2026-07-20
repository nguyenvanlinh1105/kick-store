<script setup>
import { ref, computed } from 'vue'
import { useRouter, RouterLink } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import authService from '@/services/authService'

const router = useRouter()
const auth = useAuthStore()

const fullName = ref('')
const email = ref('')
const phone = ref('')
const password = ref('')
const confirm = ref('')

const fullNameError = ref('')
const emailError = ref('')
const phoneError = ref('')
const passwordError = ref('')
const confirmError = ref('')
const loading = ref(false)

// Focus states for input animations
const nameFocused = ref(false)
const emailFocused = ref(false)
const phoneFocused = ref(false)
const passFocused = ref(false)
const confirmFocused = ref(false)

const isEmailValid = computed(() => {
  if (!email.value) return false
  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  return re.test(email.value)
})

const isPhoneValid = computed(() => {
  if (!phone.value) return false
  const re = /^[0-9]{10}$/
  return re.test(phone.value)
})

function validateForm() {
  let isValid = true
  fullNameError.value = ''
  emailError.value = ''
  phoneError.value = ''
  passwordError.value = ''
  confirmError.value = ''

  if (!fullName.value.trim()) {
    fullNameError.value = 'Vui lòng nhập họ và tên.'
    isValid = false
  } else if (fullName.value.trim().length < 2) {
    fullNameError.value = 'Họ và tên phải dài từ 2 ký tự trở lên.'
    isValid = false
  }

  if (!email.value) {
    emailError.value = 'Vui lòng nhập địa chỉ email.'
    isValid = false
  } else if (!isEmailValid.value) {
    emailError.value = 'Địa chỉ email không hợp lệ.'
    isValid = false
  }

  if (!phone.value) {
    phoneError.value = 'Vui lòng nhập số điện thoại.'
    isValid = false
  } else if (!isPhoneValid.value) {
    phoneError.value = 'Số điện thoại phải chứa đúng 10 số.'
    isValid = false
  }

  if (!password.value) {
    passwordError.value = 'Vui lòng nhập mật khẩu.'
    isValid = false
  } else if (password.value.length < 8) {
    passwordError.value = 'Mật khẩu phải chứa ít nhất 8 ký tự.'
    isValid = false
  }

  if (!confirm.value) {
    confirmError.value = 'Vui lòng xác nhận mật khẩu.'
    isValid = false
  } else if (password.value !== confirm.value) {
    confirmError.value = 'Mật khẩu xác nhận không khớp.'
    isValid = false
  }

  return isValid
}

async function submit() {
  if (!validateForm()) return
  
  loading.value = true
  try {
    // 1. Call registration API
    await authService.register({
      email: email.value,
      password: password.value,
      fullName: fullName.value,
      phone: phone.value,
    })

    // 2. Automatically log in on success
    const loginRes = await authService.login({
      email: email.value,
      password: password.value,
    })

    auth.login(loginRes.data)
    router.push('/account')
  } catch (error) {
    console.error('Registration error:', error)
    const serverMessage = error.response?.data?.message || ''
    
    if (serverMessage.toLowerCase().includes('email')) {
      emailError.value = serverMessage
    } else if (serverMessage.toLowerCase().includes('phone') || serverMessage.toLowerCase().includes('sđt') || serverMessage.toLowerCase().includes('số điện thoại')) {
      phoneError.value = serverMessage
    } else {
      emailError.value = serverMessage || 'Đăng ký không thành công. Vui lòng kiểm tra lại thông tin.'
    }
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="relative w-full flex flex-col items-center justify-center bg-canvas-light py-6 px-4 sm:px-6 overflow-hidden">
    
    <!-- Subtle neutral background accents for modern clean aesthetic -->
    <div class="absolute top-1/4 right-1/4 w-96 h-96 bg-neutral-200/20 rounded-full blur-[100px] pointer-events-none" aria-hidden="true"></div>
    <div class="absolute bottom-1/4 left-1/4 w-[400px] h-[400px] bg-neutral-100/30 rounded-full blur-[120px] pointer-events-none" aria-hidden="true"></div>

    <div class="w-full max-w-[450px] relative z-10 kv-animate-fade-up">
      <!-- Main Frosted Glass Card -->
      <div class="w-full bg-white/80 backdrop-blur-xl border border-black/5 rounded-2xl p-6 sm:p-10 shadow-[0_12px_40px_rgba(0,0,0,0.03)]">
        
        <!-- Brand Signature -->
        <div class="text-center mb-8 flex flex-col items-center">
          <img src="/logo.png?v=2" alt="KickVerse Logo" class="h-24 w-auto object-contain" />
          <h1 class="text-2xl font-bold tracking-tight text-neutral-900 mt-4 mb-2">Đăng ký thành viên</h1>
          <p class="text-sm text-neutral-500">Gia nhập cộng đồng sneaker streetwear cao cấp</p>
        </div>

        <!-- Form fields -->
        <form class="flex flex-col gap-4.5" @submit.prevent="submit" novalidate>
          
          <!-- Họ và tên -->
          <div class="flex flex-col gap-1.5">
            <label class="text-[11px] font-bold tracking-wider uppercase text-neutral-500 px-1" for="reg-name">Họ và tên *</label>
            <input
              id="reg-name"
              v-model="fullName"
              type="text"
              class="w-full h-12 px-4 text-[14.5px] text-black bg-neutral-50/50 border rounded-xl outline-none transition-all duration-200"
              :class="[
                fullNameError ? 'border-commerce focus:shadow-[0_0_0_3px_rgba(239,68,68,0.08)]' : 'border-neutral-200/80 focus:border-black focus:shadow-[0_0_0_3px_rgba(0,0,0,0.04)]',
                nameFocused ? 'bg-white' : ''
              ]"
              placeholder="Nguyễn Văn A"
              required
              @focus="nameFocused = true"
              @blur="nameFocused = false"
            />
            <p v-if="fullNameError" class="text-xs text-commerce px-1 mt-1 font-medium flex items-center gap-1.5">
              <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
                <circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/>
              </svg>
              {{ fullNameError }}
            </p>
          </div>

          <!-- Email -->
          <div class="flex flex-col gap-1.5">
            <div class="flex justify-between items-center px-1">
              <label class="text-[11px] font-bold tracking-wider uppercase text-neutral-500" for="reg-email">Email *</label>
              <span v-if="isEmailValid" class="text-[10px] font-bold text-neutral-800 flex items-center gap-1">
                <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3">
                  <polyline points="20 6 9 17 4 12"/>
                </svg> Hợp lệ
              </span>
            </div>
            <input
              id="reg-email"
              v-model="email"
              type="email"
              class="w-full h-12 px-4 text-[14.5px] text-black bg-neutral-50/50 border rounded-xl outline-none transition-all duration-200"
              :class="[
                emailError ? 'border-commerce focus:shadow-[0_0_0_3px_rgba(239,68,68,0.08)]' : 'border-neutral-200/80 focus:border-black focus:shadow-[0_0_0_3px_rgba(0,0,0,0.04)]',
                emailFocused ? 'bg-white' : ''
              ]"
              placeholder="tenban@email.com"
              autocomplete="email"
              required
              @focus="emailFocused = true"
              @blur="emailFocused = false"
            />
            <p v-if="emailError" class="text-xs text-commerce px-1 mt-1 font-medium flex items-center gap-1.5">
              <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
                <circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/>
              </svg>
              {{ emailError }}
            </p>
          </div>

          <!-- Số điện thoại -->
          <div class="flex flex-col gap-1.5">
            <label class="text-[11px] font-bold tracking-wider uppercase text-neutral-500 px-1" for="reg-phone">Số điện thoại *</label>
            <input
              id="reg-phone"
              v-model="phone"
              type="tel"
              class="w-full h-12 px-4 text-[14.5px] text-black bg-neutral-50/50 border rounded-xl outline-none transition-all duration-200"
              :class="[
                phoneError ? 'border-commerce focus:shadow-[0_0_0_3px_rgba(239,68,68,0.08)]' : 'border-neutral-200/80 focus:border-black focus:shadow-[0_0_0_3px_rgba(0,0,0,0.04)]',
                phoneFocused ? 'bg-white' : ''
              ]"
              placeholder="09xx xxx xxx"
              @focus="phoneFocused = true"
              @blur="phoneFocused = false"
            />
            <p v-if="phoneError" class="text-xs text-commerce px-1 mt-1 font-medium flex items-center gap-1.5">
              <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
                <circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/>
              </svg>
              {{ phoneError }}
            </p>
          </div>

          <!-- Mật khẩu -->
          <div class="flex flex-col gap-1.5">
            <label class="text-[11px] font-bold tracking-wider uppercase text-neutral-500 px-1" for="reg-password">Mật khẩu *</label>
            <input
              id="reg-password"
              v-model="password"
              type="password"
              class="w-full h-12 px-4 text-[14.5px] text-black bg-neutral-50/50 border rounded-xl outline-none transition-all duration-200"
              :class="[
                passwordError ? 'border-commerce focus:shadow-[0_0_0_3px_rgba(239,68,68,0.08)]' : 'border-neutral-200/80 focus:border-black focus:shadow-[0_0_0_3px_rgba(0,0,0,0.04)]',
                passFocused ? 'bg-white' : ''
              ]"
              placeholder="Tối thiểu 8 ký tự"
              autocomplete="new-password"
              required
              @focus="passFocused = true"
              @blur="passFocused = false"
            />
            <p v-if="passwordError" class="text-xs text-commerce px-1 mt-1 font-medium flex items-center gap-1.5">
              <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
                <circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/>
              </svg>
              {{ passwordError }}
            </p>
          </div>

          <!-- Xác nhận mật khẩu -->
          <div class="flex flex-col gap-1.5">
            <label class="text-[11px] font-bold tracking-wider uppercase text-neutral-500 px-1" for="reg-confirm">Xác nhận mật khẩu *</label>
            <input
              id="reg-confirm"
              v-model="confirm"
              type="password"
              class="w-full h-12 px-4 text-[14.5px] text-black bg-neutral-50/50 border rounded-xl outline-none transition-all duration-200"
              :class="[
                confirmError ? 'border-commerce focus:shadow-[0_0_0_3px_rgba(239,68,68,0.08)]' : 'border-neutral-200/80 focus:border-black focus:shadow-[0_0_0_3px_rgba(0,0,0,0.04)]',
                confirmFocused ? 'bg-white' : ''
              ]"
              placeholder="Nhập lại mật khẩu"
              autocomplete="new-password"
              required
              @focus="confirmFocused = true"
              @blur="confirmFocused = false"
            />
            <p v-if="confirmError" class="text-xs text-commerce px-1 mt-1 font-medium flex items-center gap-1.5">
              <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
                <circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/>
              </svg>
              {{ confirmError }}
            </p>
          </div>

          <!-- Submit Button -->
          <button
            type="submit"
            class="w-full mt-4 h-12 text-xs font-bold tracking-widest uppercase text-white bg-black hover:bg-neutral-800 active:scale-[0.98] rounded-xl shadow-md transition-all duration-200 flex items-center justify-center border-0 cursor-pointer"
            :disabled="loading"
          >
            <span v-if="!loading" class="flex items-center gap-2">
              Đăng ký tài khoản
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
                <line x1="5" y1="12" x2="19" y2="12"/><polyline points="12 5 19 12 12 19"/>
              </svg>
            </span>
            <span v-else class="inline-block w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin"></span>
          </button>
        </form>

        <!-- Redirect to login -->
        <p class="text-center text-sm text-neutral-500 mt-8 mb-0">
          Đã có tài khoản?
          <RouterLink to="/login" class="text-black font-bold no-underline hover:underline">Đăng nhập</RouterLink>
        </p>

      </div>
    </div>
  </div>
</template>
