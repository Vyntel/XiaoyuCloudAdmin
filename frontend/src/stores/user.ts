import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { UserVO, MenuVO } from '@/types'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref<UserVO | null>(null)
  const menus = ref<MenuVO[]>([])

  // 设置 Token
  function setToken(newToken: string) {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }

  // 设置用户信息
  function setUserInfo(info: UserVO) {
    userInfo.value = info
  }

  // 设置菜单
  function setMenus(newMenus: MenuVO[]) {
    menus.value = newMenus
  }

  // 登出
  function logout() {
    token.value = ''
    userInfo.value = null
    menus.value = []
    localStorage.removeItem('token')
  }

  // 检查是否已登录
  function isLoggedIn() {
    return !!token.value
  }

  return {
    token,
    userInfo,
    menus,
    setToken,
    setUserInfo,
    setMenus,
    logout,
    isLoggedIn
  }
})