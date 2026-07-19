<script setup>
import KvBadge from '@/components/ui/KvBadge.vue'
import KvButton from '@/components/ui/KvButton.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'

const coupons = [
  { code: 'KICK10', type: 'PERCENTAGE', value: '10%', used: 42, status: 'ACTIVE' },
  { code: 'FREESHIP', type: 'FIXED_AMOUNT', value: '30.000đ', used: 118, status: 'ACTIVE' },
  { code: 'SUMMER26', type: 'PERCENTAGE', value: '20%', used: 0, status: 'INACTIVE' },
]
</script>

<template>
  <div>
    <KvPageHeader title="Mã giảm giá" description="Coupon & campaign">
      <KvButton size="md" variant="primary">Tạo mã</KvButton>
    </KvPageHeader>
    <div class="overflow-x-auto rounded-md bg-canvas-light">
      <table class="w-full min-w-[560px] text-left text-body-sm">
        <thead>
          <tr class="border-b border-hairline-light text-caption-md text-mute-light">
            <th class="px-lg py-md font-medium">Code</th>
            <th class="px-lg py-md font-medium">Loại</th>
            <th class="px-lg py-md font-medium">Giá trị</th>
            <th class="px-lg py-md font-medium">Đã dùng</th>
            <th class="px-lg py-md font-medium">Status</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="c in coupons" :key="c.code" class="border-b border-hairline-light">
            <td class="px-lg py-md font-medium">{{ c.code }}</td>
            <td class="px-lg py-md">{{ c.type }}</td>
            <td class="px-lg py-md">{{ c.value }}</td>
            <td class="px-lg py-md">{{ c.used }}</td>
            <td class="px-lg py-md">
              <KvBadge :variant="c.status === 'ACTIVE' ? 'info' : 'soft'">{{ c.status }}</KvBadge>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
