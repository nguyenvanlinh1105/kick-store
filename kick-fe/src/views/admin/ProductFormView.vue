<script setup>
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import KvButton from '@/components/ui/KvButton.vue'
import KvInput from '@/components/ui/KvInput.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'

const route = useRoute()
const router = useRouter()
const isNew = route.params.id === 'new'

const form = ref({
  name: '',
  brand: '',
  price: '',
  status: 'DRAFT',
  description: '',
})

function save() {
  router.push('/admin/products')
}
</script>

<template>
  <div>
    <KvPageHeader
      :title="isNew ? 'Thêm sản phẩm' : `Sửa sản phẩm #${route.params.id}`"
      description="Form catalog — sẽ nối API Product"
    />

    <form class="max-w-2xl space-y-md rounded-md bg-canvas-light p-lg" @submit.prevent="save">
      <KvInput v-model="form.name" label="Tên sản phẩm" required />
      <div class="grid gap-md sm:grid-cols-2">
        <KvInput v-model="form.brand" label="Thương hiệu" required />
        <KvInput v-model="form.price" label="Giá (VND)" type="number" required />
      </div>
      <label class="flex flex-col gap-xs">
        <span class="text-caption-md text-mute-light">Trạng thái</span>
        <select
          v-model="form.status"
          class="h-12 rounded-sm border border-ash-light bg-canvas-light px-md outline-none focus:border-2 focus:border-primary"
        >
          <option value="DRAFT">DRAFT</option>
          <option value="ACTIVE">ACTIVE</option>
          <option value="INACTIVE">INACTIVE</option>
        </select>
      </label>
      <label class="flex flex-col gap-xs">
        <span class="text-caption-md text-mute-light">Mô tả</span>
        <textarea
          v-model="form.description"
          rows="4"
          class="rounded-sm border border-ash-light bg-canvas-light px-md py-sm outline-none focus:border-2 focus:border-primary"
        />
      </label>
      <div class="flex gap-sm pt-sm">
        <KvButton type="submit" variant="primary">Lưu</KvButton>
        <KvButton type="button" variant="secondary" to="/admin/products">Hủy</KvButton>
      </div>
    </form>
  </div>
</template>
