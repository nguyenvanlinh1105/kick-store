<script setup>
import { ref, computed } from 'vue'
import { RouterLink } from 'vue-router'
import productsJson from '@/data/json/products.json'
import KvSelect from '@/components/ui/KvSelect.vue'
import { formatVnd } from '@/data/demo'

const search = ref('')
const categoryFilter = ref('all')
const productList = ref([...productsJson])

const categoryOptions = [
  { value: 'all', label: 'Tất cả danh mục' },
  { value: 'running', label: 'Running Shoes' },
  { value: 'lifestyle', label: 'Lifestyle Streetwear' },
]

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
        <h1 class="text-2xl font-extrabold text-slate-900">Quản Lý Sản Phẩm</h1>
        <p class="text-xs text-slate-500 mt-1 font-medium">Danh sách toàn bộ sản phẩm sneaker trong hệ thống ({{ filteredProducts.length }} sản phẩm)</p>
      </div>

      <RouterLink
        to="/admin/products/new"
        class="px-5 py-2.5 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 no-underline shadow-md transition-all shrink-0"
      >
        + Thêm Sản Phẩm Mới
      </RouterLink>
    </div>

    <!-- Filters & Search Toolbar -->
    <div class="bg-white border border-slate-200 rounded-3xl p-4 flex flex-col sm:flex-row gap-4 items-center justify-between shadow-sm">
      <input
        v-model="search"
        type="text"
        placeholder="Tìm tên sản phẩm, mã SKU..."
        class="w-full sm:w-80 h-10 px-4 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 focus:outline-none focus:border-amber-600 font-medium"
      />

      <div class="w-56">
        <KvSelect v-model="categoryFilter" :options="categoryOptions" :dark="false" />
      </div>
    </div>

    <!-- Product Table -->
    <div class="bg-white border border-slate-200 rounded-3xl overflow-hidden shadow-sm">
      <table class="w-full text-left text-xs text-slate-700">
        <thead class="bg-slate-900 text-white font-bold uppercase tracking-wider border-b border-slate-200">
          <tr>
            <th class="p-4">Sản Phẩm</th>
            <th class="p-4">Thương Hiệu</th>
            <th class="p-4">Giá Bán</th>
            <th class="p-4">Tồn Kho</th>
            <th class="p-4">Trạng Thái</th>
            <th class="p-4 text-right">Thao Tác</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-slate-100">
          <tr v-for="p in filteredProducts" :key="p.id" class="hover:bg-slate-50 transition-colors">
            <td class="p-4 flex items-center gap-3">
              <img :src="p.image" :alt="p.name" class="w-12 h-12 object-cover rounded-xl shrink-0 border border-slate-200" />
              <div>
                <span class="font-extrabold text-slate-900 block">{{ p.name }}</span>
                <span class="text-[10px] text-slate-400 font-mono font-bold">SKU: {{ p.sku }}</span>
              </div>
            </td>
            <td class="p-4 font-extrabold text-amber-600">{{ p.brand }}</td>
            <td class="p-4 font-extrabold text-slate-900">{{ formatVnd(p.price) }}</td>
            <td class="p-4">
              <span class="font-extrabold" :class="[p.stockCount <= 10 ? 'text-red-500' : 'text-emerald-600']">
                {{ p.stockCount }} đôi
              </span>
            </td>
            <td class="p-4">
              <span class="px-2.5 py-1 bg-emerald-500/10 text-emerald-700 font-extrabold text-[10px] rounded-full">
                Đang hiển thị
              </span>
            </td>
            <td class="p-4 text-right">
              <div class="flex items-center justify-end gap-2">
                <RouterLink :to="`/admin/products/${p.id}`" class="px-3 py-1.5 bg-slate-100 text-slate-800 font-extrabold rounded-xl hover:bg-slate-900 hover:text-white no-underline transition-colors">
                  Sửa
                </RouterLink>
                <button @click="deleteProduct(p.id)" class="px-3 py-1.5 bg-red-50 text-red-600 font-extrabold rounded-xl border border-red-200 hover:bg-red-600 hover:text-white cursor-pointer transition-colors">
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
