import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

import App from './App.vue'
import router from './router'
import './styles/index.scss'

const app = createApp(App)

// 注册所有 Element Plus 图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

// 全局错误处理
app.config.errorHandler = (err, _instance, _info) => {
  console.error('Global error:', err)
  ElMessage.error('系统异常，请稍后重试')
}

// 未捕获的Promise错误
window.addEventListener('unhandledrejection', event => {
  console.error('Unhandled promise rejection:', event.reason)
  ElMessage.error('网络请求异常，请检查网络连接')
  event.preventDefault()
})

// 全局loading状态管理（可选）
app.config.globalProperties.$showLoading = (text = '加载中...') => {
  return ElMessage.loading(text)
}

app.use(createPinia())
app.use(router)
app.use(ElementPlus)

app.mount('#app')