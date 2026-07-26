<script setup>
import { ref } from 'vue'
import { DEMO_BRANDS } from '@/data/demo'
import KvModal from '@/components/ui/KvModal.vue'

const brands = ref([...DEMO_BRANDS])
const showModal = ref(false)
const newBrandName = ref('')
const newBrandLogo = ref('👟')

function addBrand() {
  if (!newBrandName.value) return
  brands.value.push({
    id: Date.now().toString(),
    name: newBrandName.value,
    logo: newBrandLogo.value,
  })
  newBrandName.value = ''
  showModal.value = false
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-extrabold text-slate-900">Quản Lý Thương Hiệu</h1>
        <p class="text-xs text-slate-500 mt-1 font-medium">Danh sách đối tác hãng sản xuất sneaker chính hãng</p>
      </div>

      <button @click="showModal = true" class="px-5 py-2.5 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 transition-all cursor-pointer shadow">
        + Thêm Thương Hiệu
      </button>
    </div>

    <!-- PURE WHITE LIGHT BRANDS CARDS -->
    <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
      <div
        v-for="b in brands"
        :key="b.id"
        class="bg-white border border-slate-200 rounded-3xl p-5 flex items-center justify-between shadow-sm hover:shadow-md transition-all"
      >
        <div class="flex items-center gap-3">
          <span class="text-2xl">{{ b.logo }}</span>
          <span class="text-sm font-extrabold text-slate-900">{{ b.name }}</span>
        </div>
        <button @click="brands = brands.filter(x => x.id !== b.id)" class="text-slate-400 hover:text-red-600 font-bold cursor-pointer transition-colors">✕</button>
      </div>
    </div>

    <KvModal v-model="showModal" title="Thêm Thương Hiệu Mới" :dark="false">
      <form @submit.prevent="addBrand" class="flex flex-col gap-4">
        <input v-model="newBrandName" placeholder="Tên hãng (VD: Salomon, Asics)..." required class="h-10 px-3 bg-slate-50 border border-slate-200 rounded-xl text-slate-900 text-xs font-medium" />
        <button type="submit" class="h-11 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 shadow">LƯU THƯƠNG HIỆU</button>
      </form>
    </KvModal>
  </div>
</template>
