<script setup>
import { ref, computed } from 'vue'
import { RouterLink } from 'vue-router'
import productsJson from '@/data/json/products.json'
import { formatVnd } from '@/data/demo'

const search = ref('')
const categoryFilter = ref('all')
const productList = ref([...productsJson])

const filteredProducts = computed(() => {
  return productList.value.filter((p) => {
    if (categoryFilter.value !== 'all' && p.category !== categoryFilter.value) return false
    if (search.value && !p.name.toLowerCase().includes(search.value.toLowerCase())) return false
    return true
  })
})

function deleteProduct(id) {
  if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này khỏi hệ thống?')) {
    productList.value = productList.value.filter((p) => p.id !== id)
  }
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
      <div>
        <h1 class="text-2xl font-extrabold text-white">Quản Lý Sản Phẩm</h1>
        <p class="text-xs text-neutral-400 mt-1">Danh sách toàn bộ sản phẩm sneaker trong hệ thống</p>
      </div>

      <RouterLink
        to="/admin/products/new"
        class="px-5 py-2.5 bg-primary text-black font-extrabold text-xs rounded-xl hover:bg-primary-hover no-underline shadow-lg"
      >
        + Thêm Sản Phẩm Mới
      </RouterLink>
    </div>

    <!-- Filters & Search Toolbar -->
    <div class="bg-neutral-900 border border-white/10 rounded-2xl p-4 flex flex-col sm:flex-row gap-4 items-center justify-between">
      <input
        v-model="search"
        type="text"
        placeholder="Tìm tên sản phẩm, mã SKU..."
        class="w-full sm:w-72 h-10 px-4 text-xs bg-black border border-white/15 rounded-xl text-white focus:outline-none focus:border-primary"
      />

      <select v-model="categoryFilter" class="h-10 px-4 text-xs bg-black border border-white/15 rounded-xl text-white">
        <option value="all">Tất cả danh mục</option>
        <option value="running">Running Shoes</option>
        <option value="lifestyle">Lifestyle Streetwear</option>
      </select>
    </div>

    <!-- Product Table -->
    <div class="bg-neutral-900 border border-white/10 rounded-2xl overflow-hidden">
      <table class="w-full text-left text-xs text-neutral-300">
        <thead class="bg-black/60 text-white font-bold uppercase tracking-wider border-b border-white/10">
          <tr>
            <th class="p-4">Sản Phẩm</th>
            <th class="p-4">Thương Hiệu</th>
            <th class="p-4">Giá Bán</th>
            <th class="p-4">Tồn Kho</th>
            <th class="p-4">Trạng Thái</th>
            <th class="p-4 text-right">Thao Tác</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-white/5">
          <tr v-for="p in filteredProducts" :key="p.id" class="hover:bg-white/5 transition-colors">
            <td class="p-4 flex items-center gap-3">
              <img :src="p.image" :alt="p.name" class="w-12 h-12 object-cover rounded-xl shrink-0" />
              <div>
                <span class="font-bold text-white block">{{ p.name }}</span>
                <span class="text-[10px] text-neutral-400 font-mono">SKU: {{ p.sku }}</span>
              </div>
            </td>
            <td class="p-4 font-bold text-primary">{{ p.brand }}</td>
            <td class="p-4 font-extrabold text-white">{{ formatVnd(p.price) }}</td>
            <td class="p-4">
              <span class="font-bold" :class="[p.stockCount <= 10 ? 'text-red-400' : 'text-emerald-400']">
                {{ p.stockCount }} đôi
              </span>
            </td>
            <td class="p-4">
              <span class="px-2.5 py-1 bg-emerald-500/20 text-emerald-400 font-bold text-[10px] rounded-full">
                Đang hiển thị
              </span>
            </td>
            <td class="p-4 text-right">
              <div class="flex items-center justify-end gap-2">
                <RouterLink :to="`/admin/products/${p.id}`" class="px-3 py-1.5 bg-neutral-800 text-white font-bold rounded-lg hover:bg-neutral-700 no-underline">
                  Sửa
                </RouterLink>
                <button @click="deleteProduct(p.id)" class="px-3 py-1.5 bg-red-500/20 text-red-400 font-bold rounded-lg hover:bg-red-500/30 cursor-pointer">
                  Xóa
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
