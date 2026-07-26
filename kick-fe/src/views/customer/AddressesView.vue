<script setup>
import { ref } from 'vue'
import KvModal from '@/components/ui/KvModal.vue'
import KvSelect from '@/components/ui/KvSelect.vue'
import customersJson from '@/data/json/customers.json'

const user = customersJson[0]
const addressList = ref([...user.addresses])

const showModal = ref(false)
const editingAddress = ref(null)

const form = ref({
  receiverName: '',
  phone: '',
  province: 'Hà Nội',
  district: 'Cầu Giấy',
  ward: 'Dịch Vọng',
  street: '',
  isDefault: false,
})

const provinceOptions = ['Hà Nội', 'TP. Hồ Chí Minh', 'Đà Nẵng']
const districtOptions = ['Cầu Giấy', 'Ba Đình', 'Đống Đa', 'Quận 1']
const wardOptions = ['Dịch Vọng', 'Dịch Vọng Hậu', 'Phường Bến Nghé']

function openAddModal() {
  editingAddress.value = null
  form.value = { receiverName: '', phone: '', province: 'Hà Nội', district: 'Cầu Giấy', ward: 'Dịch Vọng', street: '', isDefault: false }
  showModal.value = true
}

function openEditModal(addr) {
  editingAddress.value = addr
  form.value = { ...addr }
  showModal.value = true
}

function saveAddress() {
  if (!form.value.receiverName || !form.value.phone) return
  if (editingAddress.value) {
    Object.assign(editingAddress.value, form.value)
  } else {
    addressList.value.push({ id: Date.now(), ...form.value })
  }
  showModal.value = false
}

function setDefault(id) {
  addressList.value.forEach((a) => (a.isDefault = a.id === id))
}

function deleteAddress(id) {
  addressList.value = addressList.value.filter((a) => a.id !== id)
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="flex items-center justify-between">
      <h1 class="text-2xl font-extrabold text-slate-900">Sổ Địa Chỉ Nhận Hàng</h1>
      <button
        type="button"
        class="px-5 py-2.5 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 cursor-pointer shadow transition-all"
        @click="openAddModal"
      >
        + Thêm Địa Chỉ Mới
      </button>
    </div>

    <!-- PURE WHITE LIGHT ADDRESS CARDS -->
    <div class="grid grid-cols-1 gap-4">
      <div
        v-for="addr in addressList"
        :key="addr.id"
        class="bg-white border rounded-3xl p-6 flex flex-col gap-3 shadow-sm transition-all text-slate-900"
        :class="[addr.isDefault ? 'border-amber-600 bg-amber-500/5' : 'border-slate-200']"
      >
        <div class="flex items-center justify-between">
          <div class="flex items-center gap-3">
            <span class="text-sm font-extrabold text-slate-900">{{ addr.receiverName }}</span>
            <span class="text-xs text-slate-500 font-bold">({{ addr.phone }})</span>
            <span v-if="addr.isDefault" class="text-[10px] bg-amber-600 text-white font-extrabold px-2.5 py-0.5 rounded-full">Mặc định</span>
          </div>
          <div class="flex items-center gap-3 text-xs">
            <button @click="openEditModal(addr)" class="text-amber-600 font-extrabold hover:underline cursor-pointer">Sửa</button>
            <button v-if="!addr.isDefault" @click="deleteAddress(addr.id)" class="text-red-500 font-extrabold hover:underline cursor-pointer">Xóa</button>
          </div>
        </div>

        <p class="text-xs text-slate-600 font-medium">{{ addr.street }}, {{ addr.ward }}, {{ addr.district }}, {{ addr.province }}</p>

        <button
          v-if="!addr.isDefault"
          type="button"
          class="self-start text-[11px] font-extrabold text-slate-500 hover:text-amber-600 cursor-pointer mt-1"
          @click="setDefault(addr.id)"
        >
          Đặt làm địa chỉ mặc định
        </button>
      </div>
    </div>

    <!-- Add/Edit Address Modal -->
    <KvModal v-model="showModal" :title="editingAddress ? 'Sửa Địa Chỉ Nhận Hàng' : 'Thêm Địa Chỉ Mới'" :dark="false">
      <form @submit.prevent="saveAddress" class="flex flex-col gap-4">
        <div class="grid grid-cols-2 gap-3">
          <input v-model="form.receiverName" placeholder="Họ và tên *" required class="h-10 px-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-medium" />
          <input v-model="form.phone" placeholder="Số điện thoại *" required class="h-10 px-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-medium" />
        </div>
        <div class="grid grid-cols-3 gap-3">
          <KvSelect v-model="form.province" label="Tỉnh / Thành" :options="provinceOptions" :dark="false" />
          <KvSelect v-model="form.district" label="Quận / Huyện" :options="districtOptions" :dark="false" />
          <KvSelect v-model="form.ward" label="Phường / Xã" :options="wardOptions" :dark="false" />
        </div>
        <input v-model="form.street" placeholder="Địa chỉ cụ thể (Số nhà, đường...)" required class="h-10 px-3 text-xs bg-slate-50 border border-slate-200 rounded-xl text-slate-900 font-medium" />
        <label class="flex items-center gap-2 text-xs text-slate-700 font-bold cursor-pointer">
          <input type="checkbox" v-model="form.isDefault" class="accent-amber-600 w-4 h-4 rounded" />
          <span>Đặt làm địa chỉ mặc định</span>
        </label>
        <button type="submit" class="h-11 bg-slate-900 text-white font-extrabold text-xs rounded-xl hover:bg-amber-600 cursor-pointer shadow mt-2">LƯU ĐỊA CHỈ</button>
      </form>
    </KvModal>
  </div>
</template>
