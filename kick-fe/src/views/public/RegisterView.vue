<script setup>
import { ref, reactive } from 'vue'
import { RouterLink, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const auth = useAuthStore()

const fullName = ref('')
const email = ref('')
const phone = ref('')
const password = ref('')
const confirmPassword = ref('')
const acceptTerms = ref(true)
const isLoading = ref(false)

const errors = reactive({
  fullName: '',
  email: '',
  phone: '',
  password: '',
  confirmPassword: '',
  acceptTerms: '',
})

function validateForm() {
  let isValid = true
  // Reset errors
  errors.fullName = ''
  errors.email = ''
  errors.phone = ''
  errors.password = ''
  errors.confirmPassword = ''
  errors.acceptTerms = ''

  // Fullname check
  if (!fullName.value.trim()) {
    errors.fullName = 'Vui lòng nhập họ và tên.'
    isValid = false
  } else if (fullName.value.trim().length < 2) {
    errors.fullName = 'Họ và tên phải có ít nhất 2 ký tự.'
    isValid = false
  }

  // Email check
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!email.value.trim()) {
    errors.email = 'Vui lòng nhập địa chỉ email.'
    isValid = false
  } else if (!emailRegex.test(email.value.trim())) {
    errors.email = 'Địa chỉ email không đúng định dạng (VD: name@example.com).'
    isValid = false
  }

  // Phone check (optional or 10-digit check)
  if (phone.value.trim()) {
    const phoneRegex = /(03|05|07|08|09)+([0-9]{8})\b/
    if (!phoneRegex.test(phone.value.trim())) {
      errors.phone = 'Số điện thoại không hợp lệ (gồm 10 chữ số bắt đầu bằng 03, 05, 07, 08, 09).'
      isValid = false
    }
  }

  // Password check
  if (!password.value) {
    errors.password = 'Vui lòng nhập mật khẩu.'
    isValid = false
  } else if (password.value.length < 8) {
    errors.password = 'Mật khẩu phải có độ dài ít nhất 8 ký tự.'
    isValid = false
  }

  // Confirm password check
  if (!confirmPassword.value) {
    errors.confirmPassword = 'Vui lòng nhập lại mật khẩu.'
    isValid = false
  } else if (password.value !== confirmPassword.value) {
    errors.confirmPassword = 'Mật khẩu xác nhận không trùng khớp với mật khẩu đã nhập.'
    isValid = false
  }

  // Terms check
  if (!acceptTerms.value) {
    errors.acceptTerms = 'Bạn phải đồng ý với Điều khoản dịch vụ & Bảo mật để tạo tài khoản.'
    isValid = false
  }

  return isValid
}

function handleGoogleRegister() {
  isLoading.value = true
  setTimeout(() => {
    isLoading.value = false
    auth.login({
      fullName: 'Khách Hàng Mới (Google)',
      email: 'newuser@gmail.com',
      avatar: 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150&q=80',
    })
    router.push('/account')
  }, 600)
}

function handleRegisterSubmit() {
  if (!validateForm()) return

  isLoading.value = true
  setTimeout(() => {
    isLoading.value = false
    auth.login({
      fullName: fullName.value,
      email: email.value,
    })
    router.push('/account')
  }, 600)
}
</script>

<template>
  <div class="bg-slate-50 pt-8 pb-24 flex items-center justify-center text-slate-900 px-4">
    <!-- WIDE BALANCED 2-COLUMN REGISTER CARD -->
    <div class="w-full max-w-4xl bg-white border border-slate-200 rounded-3xl p-8 md:p-10 shadow-2xl grid grid-cols-1 md:grid-cols-2 gap-8 items-center">
      
      <!-- LEFT COLUMN: BRAND PERKS & GOOGLE 1-CLICK -->
      <div class="flex flex-col gap-6 border-b md:border-b-0 md:border-r border-slate-100 pb-8 md:pb-0 md:pr-8">
        <div>
          <RouterLink to="/" class="font-display text-3xl tracking-[3px] text-slate-900 no-underline mb-2 block">
            KICK<span class="text-amber-600 font-extrabold">VERSE</span>
          </RouterLink>
          <h1 class="text-2xl font-extrabold text-slate-900">Gia Nhập KickVerse</h1>
          <p class="text-xs text-slate-500 mt-1 leading-relaxed">
            Tạo tài khoản thành viên để tận hưởng đặc quyền mua sắm sản phẩm sneaker cao cấp độc quyền.
          </p>
        </div>

        <!-- MEMBER PERKS -->
        <div class="bg-slate-50 border border-slate-200 rounded-2xl p-5 flex flex-col gap-3">
          <span class="text-xs font-extrabold text-amber-600 uppercase tracking-wider">Đặc quyền thành viên VIP:</span>
          
          <div class="flex items-center gap-2.5 text-xs text-slate-700 font-medium">
            <span class="text-base">🎁</span>
            <span>Tặng ngay <strong>Voucher 100.000đ</strong> vào ví cá nhân</span>
          </div>

          <div class="flex items-center gap-2.5 text-xs text-slate-700 font-medium">
            <span class="text-base">⭐</span>
            <span>Tích điểm nâng hạng Bạc / Vàng / Kim Cương</span>
          </div>

          <div class="flex items-center gap-2.5 text-xs text-slate-700 font-medium">
            <span class="text-base">🚚</span>
            <span>Theo dõi hành trình đơn hàng chi tiết 24/7</span>
          </div>

          <div class="flex items-center gap-2.5 text-xs text-slate-700 font-medium">
            <span class="text-base">🔄</span>
            <span>Đổi trả size giày miễn phí tận nhà trong 14 ngày</span>
          </div>
        </div>

        <!-- GOOGLE 1-CLICK REGISTRATION -->
        <div class="flex flex-col gap-2">
          <button
            type="button"
            :disabled="isLoading"
            class="w-full h-13 bg-white border-2 border-slate-200 hover:border-amber-600 text-slate-800 font-extrabold text-xs rounded-2xl flex items-center justify-center gap-3 transition-all cursor-pointer shadow-sm hover:shadow-md active:scale-98 disabled:opacity-50"
            @click="handleGoogleRegister"
          >
            <svg class="w-5 h-5 shrink-0" viewBox="0 0 24 24">
              <path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
              <path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
              <path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.06H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.94l2.85-2.22.81-.63z"/>
              <path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.06l3.66 2.84c.87-2.6 3.3-4.52 6.16-4.52z"/>
            </svg>
            <span>ĐĂNG KÝ NHANH GOOGLE (1-CLICK)</span>
          </button>
          <span class="text-[11px] text-slate-400 text-center font-medium">⚡ Đăng ký tự động 3 giây bằng Google Account</span>
        </div>
      </div>

      <!-- RIGHT COLUMN: MULTI-COLUMN FORM WITH VALIDATION -->
      <div class="flex flex-col gap-5">
        <h2 class="text-base font-extrabold text-slate-900 border-b border-slate-100 pb-2">Đăng Ký Tài Khoản Mới</h2>

        <form @submit.prevent="handleRegisterSubmit" novalidate class="flex flex-col gap-4">
          <!-- Row 1: Full Name & Phone -->
          <div class="grid grid-cols-1 sm:grid-cols-2 gap-3">
            <div class="flex flex-col gap-1">
              <label class="text-xs font-bold text-slate-700">Họ và tên *</label>
              <input
                v-model="fullName"
                type="text"
                placeholder="Nguyễn Văn A"
                class="h-10 px-3 text-xs bg-slate-50 border rounded-xl text-slate-900 focus:outline-none font-medium transition-colors"
                :class="[errors.fullName ? 'border-red-500 bg-red-50/20' : 'border-slate-200 focus:border-amber-600']"
              />
              <span v-if="errors.fullName" class="text-[11px] text-red-500 font-bold mt-0.5">{{ errors.fullName }}</span>
            </div>

            <div class="flex flex-col gap-1">
              <label class="text-xs font-bold text-slate-700">Số điện thoại</label>
              <input
                v-model="phone"
                type="text"
                placeholder="0987654321"
                class="h-10 px-3 text-xs bg-slate-50 border rounded-xl text-slate-900 focus:outline-none font-medium transition-colors"
                :class="[errors.phone ? 'border-red-500 bg-red-50/20' : 'border-slate-200 focus:border-amber-600']"
              />
              <span v-if="errors.phone" class="text-[11px] text-red-500 font-bold mt-0.5">{{ errors.phone }}</span>
            </div>
          </div>

          <!-- Row 2: Email -->
          <div class="flex flex-col gap-1">
            <label class="text-xs font-bold text-slate-700">Địa chỉ Email *</label>
            <input
              v-model="email"
              type="email"
              placeholder="name@example.com"
              class="h-10 px-3 text-xs bg-slate-50 border rounded-xl text-slate-900 focus:outline-none font-medium transition-colors"
              :class="[errors.email ? 'border-red-500 bg-red-50/20' : 'border-slate-200 focus:border-amber-600']"
            />
            <span v-if="errors.email" class="text-[11px] text-red-500 font-bold mt-0.5">{{ errors.email }}</span>
          </div>

          <!-- Row 3: Password & Confirm Password -->
          <div class="grid grid-cols-1 sm:grid-cols-2 gap-3">
            <div class="flex flex-col gap-1">
              <label class="text-xs font-bold text-slate-700">Mật khẩu *</label>
              <input
                v-model="password"
                type="password"
                placeholder="••••••••"
                class="h-10 px-3 text-xs bg-slate-50 border rounded-xl text-slate-900 focus:outline-none font-medium transition-colors"
                :class="[errors.password ? 'border-red-500 bg-red-50/20' : 'border-slate-200 focus:border-amber-600']"
              />
              <span v-if="errors.password" class="text-[11px] text-red-500 font-bold mt-0.5">{{ errors.password }}</span>
            </div>

            <div class="flex flex-col gap-1">
              <label class="text-xs font-bold text-slate-700">Nhập lại mật khẩu *</label>
              <input
                v-model="confirmPassword"
                type="password"
                placeholder="••••••••"
                class="h-10 px-3 text-xs bg-slate-50 border rounded-xl text-slate-900 focus:outline-none font-medium transition-colors"
                :class="[errors.confirmPassword ? 'border-red-500 bg-red-50/20' : 'border-slate-200 focus:border-amber-600']"
              />
              <span v-if="errors.confirmPassword" class="text-[11px] text-red-500 font-bold mt-0.5">{{ errors.confirmPassword }}</span>
            </div>
          </div>

          <!-- Terms Checkbox -->
          <div class="flex flex-col gap-1 pt-1">
            <label class="flex items-center gap-2 text-xs text-slate-600 cursor-pointer">
              <input type="checkbox" v-model="acceptTerms" class="accent-amber-600 w-4 h-4 rounded" />
              <span>Tôi đồng ý với <RouterLink to="/terms" class="text-amber-600 font-bold hover:underline">Điều khoản dịch vụ</RouterLink> và <RouterLink to="/privacy" class="text-amber-600 font-bold hover:underline">Bảo mật</RouterLink></span>
            </label>
            <span v-if="errors.acceptTerms" class="text-[11px] text-red-500 font-bold">{{ errors.acceptTerms }}</span>
          </div>

          <!-- Submit Button -->
          <button
            type="submit"
            :disabled="isLoading"
            class="h-12 bg-slate-900 text-white font-extrabold text-xs uppercase tracking-wider rounded-xl hover:bg-amber-600 transition-all cursor-pointer shadow-md mt-1 disabled:opacity-50"
          >
            {{ isLoading ? 'Đang tạo tài khoản...' : 'TẠO TÀI KHOẢN MỚI →' }}
          </button>
        </form>

        <p class="text-center text-xs text-slate-500 border-t border-slate-100 pt-3">
          Đã có tài khoản thành viên?
          <RouterLink to="/login" class="text-amber-600 font-extrabold hover:underline no-underline ml-1">Đăng nhập ngay</RouterLink>
        </p>
      </div>

    </div>
  </div>
</template>
