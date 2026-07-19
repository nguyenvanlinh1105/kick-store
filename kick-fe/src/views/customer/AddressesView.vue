<script setup>
import { ref } from 'vue'
import KvButton from '@/components/ui/KvButton.vue'
import KvInput from '@/components/ui/KvInput.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'
import KvEmptyState from '@/components/ui/KvEmptyState.vue'

const addresses = ref([
  {
    id: 1,
    label: 'Nhà',
    fullName: 'Nguyễn Minh An',
    phone: '0901234567',
    line: '12 Nguyễn Huệ, Quận 1',
    city: 'TP. Hồ Chí Minh',
    isDefault: true,
  },
])

const showForm = ref(false)
const form = ref({ label: '', fullName: '', phone: '', line: '', city: '' })

function add() {
  addresses.value.push({
    id: Date.now(),
    ...form.value,
    isDefault: addresses.value.length === 0,
  })
  showForm.value = false
  form.value = { label: '', fullName: '', phone: '', line: '', city: '' }
}
</script>

<template>
  <div>
    <KvPageHeader title="Địa chỉ" description="Địa chỉ giao hàng đã lưu">
      <KvButton size="md" variant="primary" @click="showForm = !showForm">
        {{ showForm ? 'Đóng' : 'Thêm địa chỉ' }}
      </KvButton>
    </KvPageHeader>

    <form
      v-if="showForm"
      class="mb-xl grid gap-md rounded-md bg-surface-card p-lg sm:grid-cols-2"
      @submit.prevent="add"
    >
      <KvInput v-model="form.label" label="Nhãn" placeholder="Nhà / Công ty" required />
      <KvInput v-model="form.fullName" label="Họ tên" required />
      <KvInput v-model="form.phone" label="SĐT" required />
      <KvInput v-model="form.city" label="Thành phố" required />
      <div class="sm:col-span-2">
        <KvInput v-model="form.line" label="Địa chỉ chi tiết" required />
      </div>
      <KvButton type="submit" variant="commerce">Lưu địa chỉ</KvButton>
    </form>

    <div v-if="addresses.length" class="flex flex-col gap-md">
      <article
        v-for="a in addresses"
        :key="a.id"
        class="rounded-md border border-hairline-light bg-canvas-light p-lg"
      >
        <div class="flex items-start justify-between gap-md">
          <div>
            <p class="text-heading-md">
              {{ a.label }}
              <span
                v-if="a.isDefault"
                class="ml-sm rounded-full bg-primary px-2 py-0.5 text-caption-sm text-on-primary"
              >
                Mặc định
              </span>
            </p>
            <p class="mt-sm text-body-sm">{{ a.fullName }} · {{ a.phone }}</p>
            <p class="text-body-sm text-body-light">{{ a.line }}, {{ a.city }}</p>
          </div>
          <button
            type="button"
            class="text-caption-md text-warning"
            @click="addresses = addresses.filter((x) => x.id !== a.id)"
          >
            Xóa
          </button>
        </div>
      </article>
    </div>

    <KvEmptyState v-else title="Chưa có địa chỉ" description="Thêm địa chỉ để checkout nhanh hơn." />
  </div>
</template>
