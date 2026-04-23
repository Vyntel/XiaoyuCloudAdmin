import { get, post, put, del } from '@/utils/request'
import type { RoleVO, PageResult } from '@/types'

// 角色相关接口
export const roleApi = {
  // 分页查询角色列表
  getRolePage: (params: {
    pageNum?: number
    pageSize?: number
    roleName?: string
    roleKey?: string
    status?: number
  }) => get<PageResult<RoleVO>>('/system/role/page', { params }),

  // 获取角色详情
  getRoleById: (roleId: number) => get<RoleVO>(`/system/role/${roleId}`),

  // 新增角色
  createRole: (data: any) => post<number>('/system/role', data),

  // 修改角色
  updateRole: (data: any) => put('/system/role', data),

  // 删除角色
  deleteRole: (roleId: number) => del(`/system/role/${roleId}`),

  // 修改状态
  updateStatus: (roleId: number, status: number) =>
    put(`/system/role/${roleId}/status`, null, { params: { status } }),

  // 分配菜单权限
  assignMenus: (roleId: number, menuIds: number[]) =>
    put(`/system/role/${roleId}/menus`, menuIds),

  // 获取角色菜单ID列表
  getRoleMenuIds: (roleId: number) => get<number[]>(`/system/role/${roleId}/menu-ids`)
}