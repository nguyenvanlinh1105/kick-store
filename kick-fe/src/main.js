import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import { initCrossTabAuthSync } from '@/utils/crossTabAuthSync'

const app = createApp(App)

app.use(createPinia())
app.use(router)

// Initialize session synchronization across tabs
initCrossTabAuthSync()

app.mount('#app')
