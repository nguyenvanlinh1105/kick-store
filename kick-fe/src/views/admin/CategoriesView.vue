<script setup>
import KvButton from '@/components/ui/KvButton.vue'
import KvPageHeader from '@/components/ui/KvPageHeader.vue'

const categories = [
  { name: 'Running', children: 4 },
  { name: 'Lifestyle', children: 6 },
  { name: 'Basketball', children: 3 },
  { name: 'Training', children: 2 },
]
</script>

<template>
  <div>
    <KvPageHeader title="Danh mục" description="Cây danh mục sản phẩm">
      <KvButton size="md" variant="primary">Thêm danh mục</KvButton>
    </KvPageHeader>
    <ul class="flex flex-col rounded-md bg-canvas-light">
      <li
        v-for="c in categories"
        :key="c.name"
        class="flex items-center justify-between border-b border-hairline-light px-lg py-md"
      >
        <span class="text-body-md">{{ c.name }}</span>
        <span class="text-caption-md text-mute-light">{{ c.children }} danh mục con</span>
      </li>
    </ul>
  </div>
</template>
