import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { authApi, menuApi } from '@/api'
import { ElMessage } from 'element-plus'
import type { UserVO } from '@/types'

const Layout = () => import('@/views/layout/Layout.vue')

// 静态路由（不需要权限的路由）
const constantRoutes: RouteRecordRaw[] = [
  {
    path: '/auth',
    name: 'Auth',
    component: () => import('@/views/auth/index.vue'),
    meta: { title: '登录注册', requiresAuth: false }
  },
  {
    path: '/login',
    redirect: '/auth'
  }
]

// 动态路由（需要根据权限动态添加）
const asyncRoutes: RouteRecordRaw[] = [
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index.vue'),
        meta: { title: '首页', icon: 'HomeFilled' }
      }
    ]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/user',
    meta: { title: '系统管理', icon: 'Setting' },
    children: [
      {
        path: 'user',
        name: 'SystemUser',
        component: () => import('@/views/system/user/index.vue'),
        meta: { title: '用户管理', icon: 'User' }
      },
      {
        path: 'role',
        name: 'SystemRole',
        component: () => import('@/views/system/role/index.vue'),
        meta: { title: '角色管理', icon: 'Key' }
      },
      {
        path: 'menu',
        name: 'SystemMenu',
        component: () => import('@/views/system/menu/index.vue'),
        meta: { title: '菜单管理', icon: 'Menu' }
      },
      {
        path: 'dept',
        name: 'SystemDept',
        component: () => import('@/views/system/dept/index.vue'),
        meta: { title: '部门管理', icon: 'OfficeBuilding' }
      }
    ]
  }
]

// 创建路由器
const router = createRouter({
  history: createWebHistory(),
  routes: [...constantRoutes, ...asyncRoutes]
})

// 路由守卫
router.beforeEach(async (to, _from, next) => {
  const userStore = useUserStore()
  const token = userStore.token

  // 不需要认证的路由
  if (to.meta.requiresAuth === false) {
    next()
    return
  }

  // 需要认证但没有token，跳转登录
  if (!token) {
    next('/auth')
    return
  }

  // 已登录用户访问登录页，重定向到首页
  if (to.path === '/login' || to.path === '/auth') {
    next('/')
    return
  }

  // 检查用户信息是否已加载
  if (!userStore.userInfo) {
    try {
      // 获取用户信息
      const userResponse = await authApi.getUserInfo()
      userStore.setUserInfo(userResponse.data as UserVO)

      // 获取用户菜单
      const menuResponse = await menuApi.getRouterMenus()
      userStore.setMenus(menuResponse.data)
    } catch (error) {
      console.error('获取用户信息失败:', error)
      ElMessage.error('获取用户信息失败，请重新登录')
      userStore.logout()
      next('/auth')
      return
    }
  }

  next()
})

// 动态路由添加函数（暂时保留，可用于后续扩展）
// function addDynamicRoutes(menus: any[]) {
//   // 可以在这里添加基于后端菜单的动态路由
// }

export default router