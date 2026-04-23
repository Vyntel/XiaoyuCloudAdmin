import axios, { type AxiosInstance, type AxiosRequestConfig } from 'axios'
import { ElMessage } from 'element-plus'
import type { Result } from '@/types'

// 创建 axios 实例
const service: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
service.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  (response) => {
    const res = response.data as Result
    if (res.code === 200) {
      return response
    }
    ElMessage.error(res.message || '请求失败')
    return Promise.reject(new Error(res.message || '请求失败'))
  },
  (error) => {
    const { response } = error
    if (response) {
      const { status, data } = response
      switch (status) {
        case 401:
          // Token 过期或无效，清除登录状态并跳转登录页
          localStorage.removeItem('token')
          ElMessage.error('登录已过期，请重新登录')
          // 延迟跳转，避免消息被立即清除
          setTimeout(() => {
            window.location.href = '/login'
          }, 1000)
          break
        case 403:
          ElMessage.error('没有权限访问该资源')
          break
        case 404:
          ElMessage.error('请求的资源不存在')
          break
        case 500:
          ElMessage.error('服务器内部错误')
          break
        default:
          ElMessage.error(data?.message || `请求失败 (${status})`)
      }
    } else {
      ElMessage.error('网络错误，请检查网络连接')
    }
    return Promise.reject(error)
  }
)

export default service

// 封装请求方法
export function get<T>(url: string, config?: AxiosRequestConfig): Promise<Result<T>> {
  return service.get<Result<T>>(url, config).then((res) => res.data)
}

export function post<T>(url: string, data?: unknown, config?: AxiosRequestConfig): Promise<Result<T>> {
  return service.post<Result<T>>(url, data, config).then((res) => res.data)
}

export function put<T>(url: string, data?: unknown, config?: AxiosRequestConfig): Promise<Result<T>> {
  return service.put<Result<T>>(url, data, config).then((res) => res.data)
}

export function del<T>(url: string, config?: AxiosRequestConfig): Promise<Result<T>> {
  return service.delete<Result<T>>(url, config).then((res) => res.data)
}