<script setup>
import { ref } from 'vue'
import { storeToRefs } from 'pinia'
import KvButton from '@/components/ui/KvButton.vue'
import KvInput from '@/components/ui/KvInput.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'
import { useAuthStore } from '@/stores/auth'

const auth = useAuthStore()
const { user } = storeToRefs(auth)

const form = ref({
  fullName: user.value?.fullName || '',
  email: user.value?.email || '',
  phone: user.value?.phone || '',
})

const saved = ref(false)

function save() {
  if (user.value) {
    user.value.fullName = form.value.fullName
    user.value.phone = form.value.phone
  }
  saved.value = true
}
</script>

<template>
  <div>
    <KvPageHeader title="Hồ sơ" description="Cập nhật thông tin cá nhân" />

    <form class="max-w-lg space-y-md rounded-md bg-surface-card p-lg" @submit.prevent="save">
      <KvInput v-model="form.fullName" label="Họ và tên" required />
      <KvInput v-model="form.email" type="email" label="Email" disabled hint="Email không đổi được tại đây" />
      <KvInput v-model="form.phone" type="tel" label="Số điện thoại" />
      <p v-if="saved" class="text-caption-md text-link-light">Đã lưu thay đổi (UI demo).</p>
      <KvButton type="submit" variant="primary">Lưu hồ sơ</KvButton>
    </form>
  </div>
</template>
