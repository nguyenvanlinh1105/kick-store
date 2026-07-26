<script setup>
import { ref } from 'vue'
import { RouterLink, useRouter } from 'vue-router'

const router = useRouter()
const identityInput = ref('')
const isSubmitted = ref(false)

function handleSubmit() {
  if (!identityInput.value) return
  isSubmitted.value = true
}
</script>

<template>
  <div class="bg-surface-0 min-h-screen py-16 flex items-center justify-center">
    <div class="w-full max-w-md bg-neutral-900 border border-white/10 rounded-3xl p-8 shadow-2xl flex flex-col gap-6">
      <div class="text-center">
        <span class="text-xs font-bold text-primary uppercase tracking-widest">Khôi Phục Mật Khẩu</span>
        <h1 class="text-2xl font-extrabold text-white mt-1">Quên Mật Khẩu?</h1>
        <p class="text-xs text-neutral-400 mt-1">Nhập Email hoặc Số điện thoại đã đăng ký để nhận liên kết khôi phục</p>
      </div>

      <form v-if="!isSubmitted" @submit.prevent="handleSubmit" class="flex flex-col gap-4">
        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-neutral-300">Email hoặc Số điện thoại</label>
          <input v-model="identityInput" type="text" required placeholder="name@example.com" class="h-11 px-4 text-xs bg-black border border-white/15 rounded-xl text-white focus:outline-none focus:border-primary" />
        </div>

        <button
          type="submit"
          class="h-12 bg-primary text-black font-extrabold text-xs uppercase tracking-wider rounded-xl hover:bg-primary-hover transition-all cursor-pointer shadow-lg mt-2"
        >
          GỬI MÃ KHÔI PHỤC →
        </button>
      </form>

      <div v-else class="flex flex-col gap-4 text-center bg-black/40 p-4 border border-emerald-500/30 rounded-2xl">
        <p class="text-xs text-emerald-400 font-bold">
          ✓ Chúng tôi đã gửi hướng dẫn đặt lại mật khẩu đến <strong>{{ identityInput }}</strong>.
        </p>
        <RouterLink to="/reset-password" class="h-10 bg-primary text-black font-extrabold text-xs rounded-xl flex items-center justify-center no-underline">
          Đặt Lại Mật Khẩu Ngay
        </RouterLink>
      </div>

      <p class="text-center text-xs text-neutral-400 border-t border-white/10 pt-4">
        Quay lại
        <RouterLink to="/login" class="text-primary font-bold hover:underline no-underline">Đăng nhập</RouterLink>
      </p>
    </div>
  </div>
</template>
