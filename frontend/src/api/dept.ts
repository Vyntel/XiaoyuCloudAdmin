import { get, post, put, del } from '@/utils/request'
import type { DeptVO, PageResult } from '@/types'

// 部门相关接口
export const deptApi = {
  // 获取部门树形结构
  getDeptTree: () => get<DeptVO[]>('/system/dept/tree'),

  // 分页查询部门列表
  getDeptPage: (params: {
    pageNum?: number
    pageSize?: number
    deptName?: string
    status?: number
  }) => get<PageResult<DeptVO>>('/system/dept/page', { params }),

  // 获取部门详情
  getDeptById: (deptId: number) => get<DeptVO>(`/system/dept/${deptId}`),

  // 新增部门
  createDept: (data: any) => post<number>('/system/dept', data),

  // 修改部门
  updateDept: (data: any) => put('/system/dept', data),

  // 删除部门
  deleteDept: (deptId: number) => del(`/system/dept/${deptId}`)
}