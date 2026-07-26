<script setup>
import { ref } from 'vue'
import { RouterLink, useRouter } from 'vue-router'

const router = useRouter()
const newPassword = ref('')
const confirmPassword = ref('')
const isSuccess = ref(false)

function handleReset() {
  if (!newPassword.value || newPassword.value !== confirmPassword.value) {
    alert('Mật khẩu nhập lại không trùng khớp!')
    return
  }
  isSuccess.value = true
  setTimeout(() => {
    router.push('/login')
  }, 1500)
}
</script>

<template>
  <div class="bg-surface-0 min-h-screen py-16 flex items-center justify-center">
    <div class="w-full max-w-md bg-neutral-900 border border-white/10 rounded-3xl p-8 shadow-2xl flex flex-col gap-6">
      <div class="text-center">
        <span class="text-xs font-bold text-primary uppercase tracking-widest">Bảo Mật Tài Khoản</span>
        <h1 class="text-2xl font-extrabold text-white mt-1">Đặt Mật Khẩu Mới</h1>
        <p class="text-xs text-neutral-400 mt-1">Vui lòng nhập mật khẩu mới đủ an toàn (tối thiểu 8 ký tự)</p>
      </div>

      <form v-if="!isSuccess" @submit.prevent="handleReset" class="flex flex-col gap-4">
        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-neutral-300">Mật khẩu mới</label>
          <input v-model="newPassword" type="password" required minlength="8" placeholder="••••••••" class="h-11 px-4 text-xs bg-black border border-white/15 rounded-xl text-white focus:outline-none focus:border-primary" />
        </div>

        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-neutral-300">Xác nhận mật khẩu mới</label>
          <input v-model="confirmPassword" type="password" required minlength="8" placeholder="••••••••" class="h-11 px-4 text-xs bg-black border border-white/15 rounded-xl text-white focus:outline-none focus:border-primary" />
        </div>

        <button
          type="submit"
          class="h-12 bg-primary text-black font-extrabold text-xs uppercase tracking-wider rounded-xl hover:bg-primary-hover transition-all cursor-pointer shadow-lg mt-2"
        >
          LƯU MẬT KHẨU MỚI
        </button>
      </form>

      <div v-else class="text-center text-emerald-400 font-bold text-xs bg-black/40 p-4 border border-emerald-500/30 rounded-2xl">
        ✓ Mật khẩu đã được cập nhật thành công! Đang chuyển đến trang đăng nhập...
      </div>
    </div>
  </div>
</template>
