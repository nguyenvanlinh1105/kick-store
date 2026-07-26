<script setup>
import { ref } from 'vue'
import { useRoute, useRouter, RouterLink } from 'vue-router'
import productsJson from '@/data/json/products.json'
import KvSelect from '@/components/ui/KvSelect.vue'

const route = useRoute()
const router = useRouter()

const isEdit = route.params.id && route.params.id !== 'new'
const existingProduct = isEdit ? productsJson.find((p) => String(p.id) === String(route.params.id)) : null

const form = ref({
  name: existingProduct?.name || '',
  brand: existingProduct?.brand || 'Nike',
  category: existingProduct?.category || 'lifestyle',
  price: existingProduct?.price || 2500000,
  importPrice: 1800000,
  compareAt: existingProduct?.compareAt || '',
  sku: existingProduct?.sku || 'NK-SNEAKER-2026',
  description: existingProduct?.description || '',
  metaTitle: existingProduct?.name ? `${existingProduct.name} - KickVerse` : '',
  metaDesc: existingProduct?.description || '',
  slug: existingProduct?.slug || 'new-sneaker',
})

const brandOptions = [
  { value: 'Nike', label: 'Nike' },
  { value: 'Adidas', label: 'Adidas' },
  { value: 'Jordan', label: 'Jordan' },
  { value: 'New Balance', label: 'New Balance' },
  { value: 'Puma', label: 'Puma' },
  { value: 'Converse', label: 'Converse' },
]

const categoryOptions = [
  { value: 'lifestyle', label: 'Lifestyle Streetwear' },
  { value: 'running', label: 'Running Shoes' },
]

// Dynamic Variants Table Rows
const variants = ref([
  { size: 39, color: 'Trắng', sku: `${form.value.sku}-39W`, stock: 10, price: form.value.price, importPrice: form.value.importPrice },
  { size: 40, color: 'Trắng', sku: `${form.value.sku}-40W`, stock: 15, price: form.value.price, importPrice: form.value.importPrice },
  { size: 41, color: 'Trắng', sku: `${form.value.sku}-41W`, stock: 12, price: form.value.price, importPrice: form.value.importPrice },
])

function addVariantRow() {
  variants.value.push({
    size: 42,
    color: 'Đen',
    sku: `${form.value.sku}-${Date.now().toString().slice(-4)}`,
    stock: 10,
    price: form.value.price,
    importPrice: form.value.importPrice,
  })
}

function removeVariantRow(idx) {
  variants.value.splice(idx, 1)
}

function handleSave() {
  alert('Đã lưu thông tin sản phẩm thành công!')
  router.push('/admin/products')
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <div class="flex items-center gap-3">
        <RouterLink to="/admin/products" class="text-xs text-amber-600 font-extrabold hover:underline no-underline">← Quay lại danh sách</RouterLink>
        <h1 class="text-2xl font-extrabold text-slate-900">{{ isEdit ? 'Chỉnh Sửa Sản Phẩm' : 'Tạo Sản Phẩm Mới' }}</h1>
      </div>
      <button
        type="button"
        class="px-6 py-2.5 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 cursor-pointer shadow-lg transition-all"
        @click="handleSave"
      >
        💾 LƯU SẢN PHẨM
      </button>
    </div>

    <!-- 1. BASIC INFO (PURE WHITE LIGHT) -->
    <div class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-4 shadow-sm">
      <h2 class="text-xs font-extrabold text-slate-900 uppercase tracking-wider border-b border-slate-100 pb-3">1. Thông Tin Cơ Bản</h2>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-slate-700">Tên sản phẩm *</label>
          <input v-model="form.name" type="text" required placeholder="Nhập tên giày..." class="h-10 px-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-medium" />
        </div>

        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-slate-700">Mã SKU gốc *</label>
          <input v-model="form.sku" type="text" required placeholder="NK-AF1-2026" class="h-10 px-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-mono font-bold" />
        </div>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
        <KvSelect v-model="form.brand" label="Thương hiệu" :options="brandOptions" :dark="false" />
        <KvSelect v-model="form.category" label="Danh mục" :options="categoryOptions" :dark="false" />

        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-slate-700">Giá niêm yết (VNĐ) *</label>
          <input v-model.number="form.price" type="number" step="50000" class="h-10 px-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-bold" />
        </div>
      </div>

      <div class="flex flex-col gap-1.5">
        <label class="text-xs font-bold text-slate-700">Mô tả chi tiết sản phẩm (Rich Text)</label>
        <textarea v-model="form.description" rows="4" placeholder="Nhập mô tả về chất liệu, kiểu dáng, đệm lót..." class="p-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-medium leading-relaxed"></textarea>
      </div>
    </div>

    <!-- 2. DYNAMIC VARIANTS MATRIX (PURE WHITE LIGHT) -->
    <div class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-4 shadow-sm">
      <div class="flex items-center justify-between border-b border-slate-100 pb-3">
        <h2 class="text-xs font-extrabold text-slate-900 uppercase tracking-wider">2. Cấu Hình Biến Thể Sản Phẩm (Size / Màu / SKU)</h2>
        <button type="button" @click="addVariantRow" class="px-3.5 py-1.5 bg-amber-500/10 text-amber-700 border border-amber-500/20 text-xs font-extrabold rounded-xl hover:bg-amber-500 hover:text-white cursor-pointer transition-colors">
          + Thêm Biến Thể
        </button>
      </div>

      <div class="overflow-x-auto">
        <table class="w-full text-xs text-left border-collapse">
          <thead class="bg-slate-900 text-white font-bold border-b border-slate-200 uppercase">
            <tr>
              <th class="p-3">Size EU</th>
              <th class="p-3">Màu Sắc</th>
              <th class="p-3">Mã SKU Biến Thể</th>
              <th class="p-3">Tồn Kho</th>
              <th class="p-3">Giá Nhập</th>
              <th class="p-3">Giá Bán</th>
              <th class="p-3 text-center">Xóa</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-slate-100">
            <tr v-for="(v, idx) in variants" :key="idx" class="hover:bg-slate-50 transition-colors">
              <td class="p-2"><input v-model.number="v.size" type="number" class="w-16 h-8 px-2 bg-slate-50 border border-slate-200 rounded-lg text-slate-900 text-xs font-bold" /></td>
              <td class="p-2"><input v-model="v.color" type="text" class="w-24 h-8 px-2 bg-slate-50 border border-slate-200 rounded-lg text-slate-900 text-xs font-medium" /></td>
              <td class="p-2"><input v-model="v.sku" type="text" class="w-36 h-8 px-2 bg-slate-50 border border-slate-200 rounded-lg text-slate-900 text-xs font-mono font-bold" /></td>
              <td class="p-2"><input v-model.number="v.stock" type="number" class="w-20 h-8 px-2 bg-slate-50 border border-slate-200 rounded-lg text-slate-900 text-xs font-bold" /></td>
              <td class="p-2"><input v-model.number="v.importPrice" type="number" class="w-28 h-8 px-2 bg-slate-50 border border-slate-200 rounded-lg text-slate-900 text-xs font-medium" /></td>
              <td class="p-2"><input v-model.number="v.price" type="number" class="w-28 h-8 px-2 bg-slate-50 border border-slate-200 rounded-lg text-slate-900 text-xs font-bold text-amber-600" /></td>
              <td class="p-2 text-center">
                <button @click="removeVariantRow(idx)" class="text-red-500 font-extrabold hover:underline cursor-pointer">✕</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- 3. SEO METADATA (PURE WHITE LIGHT) -->
    <div class="bg-white border border-slate-200 rounded-3xl p-6 flex flex-col gap-4 shadow-sm">
      <h2 class="text-xs font-extrabold text-slate-900 uppercase tracking-wider border-b border-slate-100 pb-3">3. Tối Ưu Hóa SEO Metadata</h2>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-slate-700">SEO Meta Title</label>
          <input v-model="form.metaTitle" type="text" class="h-10 px-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-medium" />
        </div>

        <div class="flex flex-col gap-1.5">
          <label class="text-xs font-bold text-slate-700">URL Slug</label>
          <input v-model="form.slug" type="text" class="h-10 px-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-mono font-bold" />
        </div>
      </div>

      <div class="flex flex-col gap-1.5">
        <label class="text-xs font-bold text-slate-700">SEO Meta Description</label>
        <textarea v-model="form.metaDesc" rows="2" class="p-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-medium"></textarea>
      </div>
    </div>
  </div>
</template>
