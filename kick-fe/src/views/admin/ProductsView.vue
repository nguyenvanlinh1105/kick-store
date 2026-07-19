<script setup>
import { ref } from 'vue'
import KvBadge from '@/components/ui/KvBadge.vue'
import KvButton from '@/components/ui/KvButton.vue'
import KvFilterPill from '@/components/ui/KvFilterPill.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'
import { DEMO_PRODUCTS } from '@/data/demo'

const filter = ref('ALL')
const filters = [
  { key: 'ALL', label: 'Tất cả' },
  { key: 'ACTIVE', label: 'Active' },
  { key: 'DRAFT', label: 'Draft' },
]

const rows = DEMO_PRODUCTS.map((p, i) => ({
  ...p,
  status: i % 5 === 0 ? 'DRAFT' : 'ACTIVE',
  stock: 12 + i * 3,
}))
</script>

<template>
  <div>
    <KvPageHeader title="Sản phẩm" description="Quản lý catalog KickVerse">
      <KvButton size="md" variant="primary" to="/admin/products/new">Thêm sản phẩm</KvButton>
    </KvPageHeader>

    <div class="mb-lg flex flex-wrap gap-sm">
      <KvFilterPill
        v-for="f in filters"
        :key="f.key"
        :active="filter === f.key"
        @click="filter = f.key"
      >
        {{ f.label }}
      </KvFilterPill>
    </div>

    <div class="overflow-x-auto rounded-md bg-canvas-light">
      <table class="w-full min-w-[720px] text-left text-body-sm">
        <thead>
          <tr class="border-b border-hairline-light text-caption-md text-mute-light">
            <th class="px-lg py-md font-medium">Sản phẩm</th>
            <th class="px-lg py-md font-medium">Giá</th>
            <th class="px-lg py-md font-medium">Tồn</th>
            <th class="px-lg py-md font-medium">Trạng thái</th>
            <th class="px-lg py-md font-medium" />
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="p in rows.filter((r) => filter === 'ALL' || r.status === filter)"
            :key="p.id"
            class="border-b border-hairline-light"
          >
            <td class="px-lg py-md">
              <div class="flex items-center gap-md">
                <img :src="p.image" :alt="p.name" class="h-12 w-12 rounded-md object-cover" />
                <div>
                  <p class="font-medium">{{ p.name }}</p>
                  <p class="text-caption-md text-mute-light">{{ p.brand }}</p>
                </div>
              </div>
            </td>
            <td class="px-lg py-md">{{ p.priceLabel }}</td>
            <td class="px-lg py-md">{{ p.stock }}</td>
            <td class="px-lg py-md">
              <KvBadge :variant="p.status === 'ACTIVE' ? 'info' : 'soft'">{{ p.status }}</KvBadge>
            </td>
            <td class="px-lg py-md text-right">
              <KvButton size="sm" variant="ghost" :to="`/admin/products/${p.id}`">Sửa</KvButton>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
