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
        <h1 class="text-2xl font-extrabold text-slate-900">Quản Lý Cây Danh Mục</h1>
        <p class="text-xs text-slate-500 mt-1 font-medium">Cấu hình danh mục phân loại sản phẩm đa cấp</p>
      </div>

      <button @click="showModal = true" class="px-5 py-2.5 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 transition-all cursor-pointer shadow">
        + Thêm Danh Mục Mới
      </button>
    </div>

    <!-- PURE WHITE LIGHT TABLE CARD -->
    <div class="bg-white border border-slate-200 rounded-3xl overflow-hidden shadow-sm">
      <table class="w-full text-left text-xs text-slate-700">
        <thead class="bg-slate-900 text-white font-bold border-b border-slate-200 uppercase">
          <tr>
            <th class="p-4">Tên Danh Mục</th>
            <th class="p-4">URL Slug</th>
            <th class="p-4">Danh Mục Cha</th>
            <th class="p-4">Số Sản Phẩm</th>
            <th class="p-4 text-right">Thao Tác</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-slate-100">
          <tr v-for="cat in categories" :key="cat.id" class="hover:bg-slate-50 transition-colors">
            <td class="p-4 font-extrabold text-slate-900">{{ cat.name }}</td>
            <td class="p-4 font-mono text-slate-500">{{ cat.slug }}</td>
            <td class="p-4 text-amber-600 font-extrabold">{{ cat.parent }}</td>
            <td class="p-4 font-bold text-slate-900">{{ cat.count }} sp</td>
            <td class="p-4 text-right">
              <button @click="categories = categories.filter(c => c.id !== cat.id)" class="text-red-500 font-extrabold hover:underline cursor-pointer">Xóa</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <KvModal v-model="showModal" title="Thêm Danh Mục Mới" :dark="false">
      <form @submit.prevent="addCategory" class="flex flex-col gap-4">
        <input v-model="newCatName" placeholder="Tên danh mục..." required class="h-10 px-3 bg-slate-50 border border-slate-200 rounded-xl text-slate-900 text-xs font-medium" />
        <button type="submit" class="h-11 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 shadow">THÊM DANH MỤC</button>
      </form>
    </KvModal>
  </div>
</template>
