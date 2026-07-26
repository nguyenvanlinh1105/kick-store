<script setup>
import { ref } from 'vue'
import KvModal from '@/components/ui/KvModal.vue'

const categories = ref([
  { id: 1, name: 'Running Shoes', slug: 'running', count: 12, parent: 'Thể thao' },
  { id: 2, name: 'Lifestyle Streetwear', slug: 'lifestyle', count: 24, parent: 'Thời trang' },
  { id: 3, name: 'Limited Drops', slug: 'limited', count: 5, parent: 'Sưu tầm' },
])

const showModal = ref(false)
const newCatName = ref('')

function addCategory() {
  if (!newCatName.value) return
  categories.value.push({
    id: Date.now(),
    name: newCatName.value,
    slug: newCatName.value.toLowerCase().replace(/\s+/g, '-'),
    count: 0,
    parent: 'Danh mục gốc',
  })
  newCatName.value = ''
  showModal.value = false
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-extrabold text-white">Quản Lý Cây Danh Mục</h1>
        <p class="text-xs text-neutral-400 mt-1">Cấu hình danh mục phân loại sản phẩm đa cấp</p>
      </div>

      <button @click="showModal = true" class="px-5 py-2.5 bg-primary text-black font-extrabold text-xs rounded-xl hover:bg-primary-hover cursor-pointer">
        + Thêm Danh Mục Mới
      </button>
    </div>

    <div class="bg-neutral-900 border border-white/10 rounded-2xl overflow-hidden">
      <table class="w-full text-left text-xs text-neutral-300">
        <thead class="bg-black text-white font-bold border-b border-white/10 uppercase">
          <tr>
            <th class="p-4">Tên Danh Mục</th>
            <th class="p-4">URL Slug</th>
            <th class="p-4">Danh Mục Cha</th>
            <th class="p-4">Số Sản Phẩm</th>
            <th class="p-4 text-right">Thao Tác</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-white/5">
          <tr v-for="cat in categories" :key="cat.id" class="hover:bg-white/5">
            <td class="p-4 font-bold text-white">{{ cat.name }}</td>
            <td class="p-4 font-mono text-neutral-400">{{ cat.slug }}</td>
            <td class="p-4 text-primary font-bold">{{ cat.parent }}</td>
            <td class="p-4 font-bold text-white">{{ cat.count }} sp</td>
            <td class="p-4 text-right">
              <button @click="categories = categories.filter(c => c.id !== cat.id)" class="text-red-400 font-bold hover:underline cursor-pointer">Xóa</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <KvModal v-model="showModal" title="Thêm Danh Mục Mới" :dark="true">
      <form @submit.prevent="addCategory" class="flex flex-col gap-4">
        <input v-model="newCatName" placeholder="Tên danh mục..." required class="h-10 px-3 bg-black border border-white/15 rounded-lg text-white text-xs" />
        <button type="submit" class="h-11 bg-primary text-black font-extrabold text-xs rounded-xl">THÊM DANH MỤC</button>
      </form>
    </KvModal>
  </div>
</template>
