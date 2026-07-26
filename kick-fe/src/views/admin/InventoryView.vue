<script setup>
import { ref } from 'vue'
import KvModal from '@/components/ui/KvModal.vue'
import productsJson from '@/data/json/products.json'
import { formatVnd } from '@/data/demo'

const inventoryList = ref(
  productsJson.map((p) => ({
    id: p.id,
    sku: p.sku,
    name: p.name,
    brand: p.brand,
    stockCount: p.stockCount,
    importPrice: 1800000,
    supplier: 'Công ty TNHH Phân Phối Thể Thao Việt Nam',
  }))
)

const showImportModal = ref(false)
const selectedSku = ref('')
const importQty = ref(10)
const importPriceInput = ref(1800000)

function openImportModal(item) {
  selectedSku.value = item.sku
  showImportModal.value = true
}

function processImport() {
  const item = inventoryList.value.find((i) => i.sku === selectedSku.value)
  if (item) {
    item.stockCount += Number(importQty.value)
  }
  showImportModal.value = false
  alert(`Đã nhập thêm ${importQty.value} sản phẩm cho mã SKU ${selectedSku.value}!`)
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-extrabold text-slate-900">Quản Lý Tồn Kho & Nhập Hàng</h1>
        <p class="text-xs text-slate-500 mt-1 font-medium">Theo dõi tồn kho thực tế theo từng biến thể SKU và tạo phiếu nhập kho</p>
      </div>
    </div>

    <!-- PURE WHITE LIGHT INVENTORY TABLE -->
    <div class="bg-white border border-slate-200 rounded-3xl overflow-hidden shadow-sm">
      <table class="w-full text-left text-xs text-slate-700">
        <thead class="bg-slate-900 text-white font-bold uppercase border-b border-slate-200">
          <tr>
            <th class="p-4">Mã SKU</th>
            <th class="p-4">Tên Sản Phẩm</th>
            <th class="p-4">Nhà Cung Cấp</th>
            <th class="p-4">Giá Nhập</th>
            <th class="p-4">Số Lượng Tồn</th>
            <th class="p-4 text-right">Thao Tác</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-slate-100">
          <tr v-for="item in inventoryList" :key="item.id" class="hover:bg-slate-50 transition-colors">
            <td class="p-4 font-mono font-extrabold text-amber-600">{{ item.sku }}</td>
            <td class="p-4 font-extrabold text-slate-900">{{ item.name }}</td>
            <td class="p-4 text-slate-500 font-medium">{{ item.supplier }}</td>
            <td class="p-4 font-extrabold text-slate-900">{{ formatVnd(item.importPrice) }}</td>
            <td class="p-4">
              <span
                class="px-2.5 py-1 font-extrabold text-[11px] rounded-full"
                :class="[item.stockCount <= 10 ? 'bg-red-500/10 text-red-600 border border-red-200' : 'bg-emerald-500/10 text-emerald-700']"
              >
                {{ item.stockCount }} đôi
              </span>
            </td>
            <td class="p-4 text-right">
              <button
                @click="openImportModal(item)"
                class="px-3.5 py-1.5 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 transition-colors cursor-pointer shadow"
              >
                + Nhập Kho
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Stock Import Receipt Modal -->
    <KvModal v-model="showImportModal" title="Tạo Phiếu Nhập Kho" :dark="false">
      <form @submit.prevent="processImport" class="flex flex-col gap-4">
        <div class="flex flex-col gap-1">
          <label class="text-xs font-bold text-slate-600">Mã SKU nhận nhập:</label>
          <span class="text-sm font-extrabold text-amber-600 font-mono">{{ selectedSku }}</span>
        </div>

        <div class="flex flex-col gap-1">
          <label class="text-xs font-bold text-slate-700">Số lượng nhập bổ sung:</label>
          <input v-model.number="importQty" type="number" min="1" required class="h-10 px-3 bg-slate-50 border border-slate-200 rounded-xl text-slate-900 text-xs font-medium" />
        </div>

        <div class="flex flex-col gap-1">
          <label class="text-xs font-bold text-slate-700">Đơn giá nhập (VNĐ):</label>
          <input v-model.number="importPriceInput" type="number" step="50000" required class="h-10 px-3 bg-slate-50 border border-slate-200 rounded-xl text-slate-900 text-xs font-medium" />
        </div>

        <button type="submit" class="h-11 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 transition-all cursor-pointer mt-2 shadow">
          HOÀN TẤT NHẬP KHO
        </button>
      </form>
    </KvModal>
  </div>
</template>
