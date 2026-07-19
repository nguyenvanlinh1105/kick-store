<script setup>
import KvBadge from '@/components/ui/KvBadge.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'
import { DEMO_PRODUCTS } from '@/data/demo'

const rows = DEMO_PRODUCTS.map((p, i) => ({
  sku: `KV-${1000 + p.id}`,
  name: p.name,
  stock: i === 2 ? 3 : 20 + i * 4,
  reserved: i,
  status: i === 2 ? 'LOW' : 'OK',
}))
</script>

<template>
  <div>
    <KvPageHeader title="Kho hàng" description="Tồn kho & đặt chỗ theo SKU" />

    <div class="overflow-x-auto rounded-md bg-canvas-light">
      <table class="w-full min-w-[640px] text-left text-body-sm">
        <thead>
          <tr class="border-b border-hairline-light text-caption-md text-mute-light">
            <th class="px-lg py-md font-medium">SKU</th>
            <th class="px-lg py-md font-medium">Sản phẩm</th>
            <th class="px-lg py-md font-medium">Tồn</th>
            <th class="px-lg py-md font-medium">Reserved</th>
            <th class="px-lg py-md font-medium">Trạng thái</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="r in rows" :key="r.sku" class="border-b border-hairline-light">
            <td class="px-lg py-md">{{ r.sku }}</td>
            <td class="px-lg py-md">{{ r.name }}</td>
            <td class="px-lg py-md">{{ r.stock }}</td>
            <td class="px-lg py-md">{{ r.reserved }}</td>
            <td class="px-lg py-md">
              <KvBadge :variant="r.status === 'LOW' ? 'commerce' : 'info'">{{ r.status }}</KvBadge>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
