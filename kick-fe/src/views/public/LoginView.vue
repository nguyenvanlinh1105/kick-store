<script setup>
import { ref } from 'vue'
import { useRouter, RouterLink } from 'vue-router'
import KvButton from '@/components/ui/KvButton.vue'
import KvInput from '@/components/ui/KvInput.vue'
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
  <div class="kv-login-page">
    <!-- Left: Visual Panel -->
    <div v-if="!isMobile" class="kv-login-panel" aria-hidden="true">
      <img
        src="https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=1200&q=85&auto=format&fit=crop"
        alt=""
        class="kv-login-panel__img"
      />
      <div class="kv-login-panel__overlay"></div>

      <!-- Floating brand text -->
      <div class="kv-login-panel__content">
        <div class="kv-login-panel__logo">KICK<span>VERSE</span></div>
        <p class="kv-login-panel__tagline">
          Nơi style<br/>bắt đầu từ đôi chân.
        </p>
        <div class="kv-login-panel__features">
          <div v-for="f in ['Drop độc quyền', 'Giao hàng 24h', 'Hoàn đổi 30 ngày']" :key="f" class="kv-login-panel__feature">
            <span class="kv-login-panel__feature-dot"></span>
            {{ f }}
          </div>
        </div>
      </div>

      <!-- Decorative card -->
      <div class="kv-login-panel__card">
        <div class="kv-login-panel__card-label">Thành viên Premium</div>
        <div class="kv-login-panel__card-value">10,000+ Members</div>
      </div>
    </div>

    <!-- Right: Form Panel -->
    <div class="kv-login-form-wrap">
      <div class="kv-login-form">
        <!-- Header -->
        <div class="kv-login-form__header">
          <RouterLink to="/" class="kv-login-form__back" aria-label="Về trang chủ">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <polyline points="15 18 9 12 15 6"/>
            </svg>
            Về trang chủ
          </RouterLink>
        </div>

        <div class="kv-login-form__brand" v-if="isMobile">KICK<span>VERSE</span></div>

        <h1 class="kv-login-form__title">Chào mừng<br/>trở lại.</h1>
        <p class="kv-login-form__sub">
          Chưa có tài khoản?
          <RouterLink to="/register" class="kv-login-form__link">Đăng ký ngay</RouterLink>
        </p>

        <!-- Form -->
        <form class="kv-login-form__fields" @submit.prevent="submit" novalidate>
          <div class="kv-field">
            <label class="kv-field__label" for="login-email">Email</label>
            <input
              id="login-email"
              v-model="email"
              type="email"
              class="kv-field__input"
              placeholder="ban@email.com"
              autocomplete="email"
              required
            />
          </div>

          <div class="kv-field">
            <div class="kv-field__label-row">
              <label class="kv-field__label" for="login-password">Mật khẩu</label>
              <a href="#" class="kv-field__forgot">Quên mật khẩu?</a>
            </div>
            <input
              id="login-password"
              v-model="password"
              type="password"
              class="kv-field__input"
              :class="{ 'kv-field__input--error': error }"
              placeholder="••••••••"
              autocomplete="current-password"
              required
            />
            <p v-if="error" class="kv-field__error">{{ error }}</p>
          </div>

          <button
            type="submit"
            class="kv-login-submit"
            :class="{ 'kv-login-submit--loading': loading }"
            :disabled="loading"
          >
            <span v-if="!loading">Đăng nhập</span>
            <span v-else class="kv-spinner"></span>
          </button>
        </form>

        <!-- Divider -->
        <div class="kv-login-divider">
          <span>hoặc thử nhanh theo role</span>
        </div>

        <!-- Quick Demo -->
        <div class="kv-login-quick">
          <button
            v-for="r in quickRoles"
            :key="r.key"
            type="button"
            class="kv-login-quick__btn"
            @click="quick(r.key)"
          >
            {{ r.label }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* ── Page Layout ── */
.kv-login-page {
  display: grid;
  grid-template-columns: 1fr 480px;
  min-height: 100svh;
  background: #0a0a0a;
}

@media (max-width: 959px) {
  .kv-login-page { grid-template-columns: 1fr; }
}

/* ── Visual Panel ── */
.kv-login-panel {
  position: relative;
  overflow: hidden;
}

.kv-login-panel__img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
}

.kv-login-panel__overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(
    145deg,
    rgb(10 10 10 / 0.85) 0%,
    rgb(10 10 10 / 0.4) 60%,
    rgb(10 10 10 / 0.7) 100%
  );
}

.kv-login-panel__content {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 48px;
}

.kv-login-panel__logo {
  font-family: 'Bebas Neue', sans-serif;
  font-size: 36px;
  letter-spacing: 4px;
  color: #f5f4f0;
  margin-bottom: 20px;
}

.kv-login-panel__logo span {
  background: linear-gradient(135deg, #c8a96e, #e8c97e, #c8a96e);
  background-clip: text;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.kv-login-panel__tagline {
  font-size: 40px;
  font-weight: 800;
  letter-spacing: -1px;
  line-height: 1.1;
  color: #f5f4f0;
  margin: 0 0 32px;
}

.kv-login-panel__features {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin-bottom: 40px;
}

.kv-login-panel__feature {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 13px;
  font-weight: 500;
  color: rgb(245 244 240 / 0.7);
}

.kv-login-panel__feature-dot {
  width: 5px;
  height: 5px;
  border-radius: 50%;
  background: #c8a96e;
  flex-shrink: 0;
}

/* Floating Glass Card */
.kv-login-panel__card {
  position: absolute;
  top: 40px;
  right: 32px;
  padding: 16px 22px;
  background: rgb(255 255 255 / 0.06);
  backdrop-filter: blur(20px);
  border: 1px solid rgb(200 169 110 / 0.2);
  border-radius: 12px;
}

.kv-login-panel__card-label {
  font-size: 10px;
  font-weight: 700;
  letter-spacing: 1.5px;
  text-transform: uppercase;
  color: #c8a96e;
  margin-bottom: 6px;
}

.kv-login-panel__card-value {
  font-size: 18px;
  font-weight: 700;
  color: #f5f4f0;
}

/* ── Form Panel ── */
.kv-login-form-wrap {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 48px 40px;
  background: #111111;
  border-left: 1px solid rgb(255 255 255 / 0.05);
}

@media (max-width: 959px) {
  .kv-login-form-wrap {
    background: #0a0a0a;
    border-left: none;
    padding: 40px 24px;
  }
}

.kv-login-form {
  width: 100%;
  max-width: 380px;
}

.kv-login-form__header {
  margin-bottom: 36px;
}

.kv-login-form__back {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  font-weight: 500;
  color: rgb(245 244 240 / 0.4);
  text-decoration: none;
  transition: color 0.2s;
}

.kv-login-form__back:hover { color: rgb(245 244 240 / 0.8); }

.kv-login-form__brand {
  font-family: 'Bebas Neue', sans-serif;
  font-size: 28px;
  letter-spacing: 3px;
  color: #f5f4f0;
  margin-bottom: 24px;
}

.kv-login-form__brand span {
  background: linear-gradient(135deg, #c8a96e, #e8c97e, #c8a96e);
  background-clip: text;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.kv-login-form__title {
  font-size: 36px;
  font-weight: 800;
  letter-spacing: -1px;
  line-height: 1.1;
  color: #f5f4f0;
  margin: 0 0 12px;
}

.kv-login-form__sub {
  font-size: 14px;
  color: rgb(245 244 240 / 0.45);
  margin: 0 0 36px;
}

.kv-login-form__link {
  color: #c8a96e;
  text-decoration: none;
  font-weight: 600;
  transition: opacity 0.2s;
}

.kv-login-form__link:hover { opacity: 0.8; }

/* ── Fields ── */
.kv-login-form__fields {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.kv-field {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.kv-field__label-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.kv-field__label {
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 1px;
  text-transform: uppercase;
  color: rgb(245 244 240 / 0.5);
}

.kv-field__forgot {
  font-size: 12px;
  color: rgb(245 244 240 / 0.35);
  text-decoration: none;
  transition: color 0.2s;
}

.kv-field__forgot:hover { color: #c8a96e; }

.kv-field__input {
  width: 100%;
  padding: 13px 16px;
  font-size: 15px;
  color: #f5f4f0;
  background: rgb(255 255 255 / 0.04);
  border: 1px solid rgb(255 255 255 / 0.1);
  border-radius: 8px;
  outline: none;
  transition: border-color 0.2s, background 0.2s, box-shadow 0.2s;
  -webkit-appearance: none;
}

.kv-field__input::placeholder { color: rgb(245 244 240 / 0.2); }

.kv-field__input:focus {
  border-color: rgb(200 169 110 / 0.5);
  background: rgb(255 255 255 / 0.06);
  box-shadow: 0 0 0 3px rgb(200 169 110 / 0.08);
}

.kv-field__input--error {
  border-color: #ef4444;
  box-shadow: 0 0 0 3px rgb(239 68 68 / 0.1);
}

.kv-field__error {
  font-size: 12px;
  color: #ef4444;
  margin: 0;
}

/* ── Submit ── */
.kv-login-submit {
  width: 100%;
  margin-top: 8px;
  padding: 14px;
  font-size: 13px;
  font-weight: 700;
  letter-spacing: 1.5px;
  text-transform: uppercase;
  color: #0a0a0a;
  background: linear-gradient(135deg, #c8a96e 0%, #e8c97e 50%, #c8a96e 100%);
  background-size: 200% auto;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-position 0.4s, transform 0.2s, box-shadow 0.2s, opacity 0.2s;
  box-shadow: 0 4px 16px rgb(200 169 110 / 0.2);
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 48px;
}

.kv-login-submit:hover:not(:disabled) {
  background-position: right center;
  transform: translateY(-1px);
  box-shadow: 0 8px 24px rgb(200 169 110 / 0.3);
}

.kv-login-submit:disabled,
.kv-login-submit--loading {
  opacity: 0.65;
  cursor: not-allowed;
  transform: none;
}

/* Spinner */
.kv-spinner {
  display: inline-block;
  width: 18px;
  height: 18px;
  border: 2px solid rgb(10 10 10 / 0.3);
  border-top-color: #0a0a0a;
  border-radius: 50%;
  animation: spin 0.7s linear infinite;
}

@keyframes spin { to { transform: rotate(360deg); } }

/* ── Divider ── */
.kv-login-divider {
  position: relative;
  text-align: center;
  margin: 28px 0 20px;
}

.kv-login-divider::before,
.kv-login-divider::after {
  content: '';
  position: absolute;
  top: 50%;
  width: calc(50% - 80px);
  height: 1px;
  background: rgb(255 255 255 / 0.08);
}

.kv-login-divider::before { left: 0; }
.kv-login-divider::after { right: 0; }

.kv-login-divider span {
  font-size: 11px;
  font-weight: 600;
  letter-spacing: 0.5px;
  color: rgb(245 244 240 / 0.25);
  text-transform: uppercase;
}

/* ── Quick Role Buttons ── */
.kv-login-quick {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 8px;
}

.kv-login-quick__btn {
  padding: 10px 0;
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.5px;
  text-transform: uppercase;
  color: rgb(245 244 240 / 0.45);
  background: rgb(255 255 255 / 0.04);
  border: 1px solid rgb(255 255 255 / 0.08);
  border-radius: 6px;
  cursor: pointer;
  transition: color 0.2s, border-color 0.2s, background 0.2s;
}

.kv-login-quick__btn:hover {
  color: #c8a96e;
  border-color: rgb(200 169 110 / 0.3);
  background: rgb(200 169 110 / 0.06);
}
</style>
