<script setup>
import { ref } from 'vue'
import KvButton from '@/components/ui/KvButton.vue'
import KvInput from '@/components/ui/KvInput.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'

const sku = ref('')
const qty = ref('')
const type = ref('ADJUSTMENT')
const log = ref([
  { sku: 'KV-1001', type: 'EXPORT', qty: -2, at: '10:24' },
  { sku: 'KV-1003', type: 'IMPORT', qty: 20, at: '09:10' },
])

function submit() {
  if (!sku.value || !qty.value) return
  log.value.unshift({
    sku: sku.value,
    type: type.value,
    qty: Number(qty.value),
    at: new Date().toLocaleTimeString('vi-VN', { hour: '2-digit', minute: '2-digit' }),
  })
  sku.value = ''
  qty.value = ''
}
</script>

<template>
  <div>
    <KvPageHeader title="Kiểm kho" description="Nhập / xuất / điều chỉnh tồn" />

    <form
      class="mb-xl grid max-w-xl gap-md rounded-md bg-canvas-light p-lg sm:grid-cols-2"
      @submit.prevent="submit"
    >
      <KvInput v-model="sku" label="SKU" placeholder="KV-1001" required />
      <KvInput v-model="qty" label="Số lượng (+/−)" type="number" required />
      <label class="flex flex-col gap-xs sm:col-span-2">
        <span class="text-caption-md text-mute-light">Loại giao dịch</span>
        <select
          v-model="type"
          class="h-12 rounded-sm border border-ash-light px-md outline-none focus:border-2 focus:border-primary"
        >
          <option>IMPORT</option>
          <option>EXPORT</option>
          <option>ADJUSTMENT</option>
          <option>ORDER_RESERVE</option>
          <option>ORDER_RELEASE</option>
        </select>
      </label>
      <KvButton type="submit" variant="primary">Ghi nhận</KvButton>
    </form>

    <div class="rounded-md bg-canvas-light p-lg">
      <h2 class="mb-md text-heading-md">Nhật ký gần đây</h2>
      <ul>
        <li
          v-for="(l, i) in log"
          :key="i"
          class="flex justify-between border-b border-hairline-light py-md text-body-sm"
        >
          <span>{{ l.at }} · {{ l.sku }} · {{ l.type }}</span>
          <span class="font-medium">{{ l.qty > 0 ? `+${l.qty}` : l.qty }}</span>
        </li>
      </ul>
    </div>
  </div>
</template>
