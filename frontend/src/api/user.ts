import { get, post, put, del } from '@/utils/request'
import type { UserVO, PageResult } from '@/types'

// 用户相关接口
export const userApi = {
  // 分页查询用户列表
  getUserPage: (params: {
    pageNum?: number
    pageSize?: number
    username?: string
    nickname?: string
    status?: number
    deptId?: number
  }) => get<PageResult<UserVO>>('/system/user/page', { params }),

  // 获取用户详情
  getUserById: (userId: number) => get<UserVO>(`/system/user/${userId}`),

  // 新增用户
  createUser: (data: any) => post<number>('/system/user', data),

  // 修改用户
  updateUser: (data: any) => put('/system/user', data),

  // 删除用户
  deleteUser: (userId: number) => del(`/system/user/${userId}`),

  // 重置密码
  resetPassword: (userId: number) => put(`/system/user/${userId}/reset-password`),

  // 修改密码
  changePassword: (userId: number, data: { oldPassword: string; newPassword: string }) =>
    put(`/system/user/${userId}/change-password`, null, { params: data }),

  // 修改状态
  updateStatus: (userId: number, status: number) =>
    put(`/system/user/${userId}/status`, null, { params: { status } }),

  // 分配角色
  assignRoles: (userId: number, roleIds: number[]) =>
    put(`/system/user/${userId}/roles`, roleIds),

  // 获取用户角色ID列表
  getUserRoleIds: (userId: number) => get<number[]>(`/system/user/${userId}/role-ids`)
}