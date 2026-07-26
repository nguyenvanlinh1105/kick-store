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
        <h1 class="text-2xl font-extrabold text-white">Quản Lý Thương Hiệu</h1>
        <p class="text-xs text-neutral-400 mt-1">Danh sách đối tác hãng sản xuất sneaker chính hãng</p>
      </div>

      <button @click="showModal = true" class="px-5 py-2.5 bg-primary text-black font-extrabold text-xs rounded-xl hover:bg-primary-hover cursor-pointer">
        + Thêm Thương Hiệu
      </button>
    </div>

    <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
      <div
        v-for="b in brands"
        :key="b.id"
        class="bg-neutral-900 border border-white/10 rounded-2xl p-5 flex items-center justify-between"
      >
        <div class="flex items-center gap-3">
          <span class="text-2xl">{{ b.logo }}</span>
          <span class="text-sm font-bold text-white">{{ b.name }}</span>
        </div>
        <button @click="brands = brands.filter(x => x.id !== b.id)" class="text-neutral-500 hover:text-red-400 cursor-pointer">✕</button>
      </div>
    </div>

    <KvModal v-model="showModal" title="Thêm Thương Hiệu Mới" :dark="true">
      <form @submit.prevent="addBrand" class="flex flex-col gap-4">
        <input v-model="newBrandName" placeholder="Tên hãng (VD: Salomon, Asics)..." required class="h-10 px-3 bg-black border border-white/15 rounded-lg text-white text-xs" />
        <button type="submit" class="h-11 bg-primary text-black font-extrabold text-xs rounded-xl">LƯU THƯƠNG HIỆU</button>
      </form>
    </KvModal>
  </div>
</template>
