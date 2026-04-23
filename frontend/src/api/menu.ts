import { get, post, put, del } from '@/utils/request'
import type { MenuVO } from '@/types'

// 菜单相关接口
export const menuApi = {
  // 获取菜单树形结构
  getMenuTree: () => get<MenuVO[]>('/system/menu/tree'),

  // 获取路由菜单（用于动态路由）
  getRouterMenus: () => get<MenuVO[]>('/system/menu/router'),

  // 分页查询菜单列表
  getMenuPage: (params: {
    pageNum?: number
    pageSize?: number
    menuName?: string
    status?: number
  }) => get('/system/menu/page', { params }),

  // 获取菜单详情
  getMenuById: (menuId: number) => get<MenuVO>(`/system/menu/${menuId}`),

  // 新增菜单
  createMenu: (data: any) => post<number>('/system/menu', data),

  // 修改菜单
  updateMenu: (data: any) => put('/system/menu', data),

  // 删除菜单
  deleteMenu: (menuId: number) => del(`/system/menu/${menuId}`)
}