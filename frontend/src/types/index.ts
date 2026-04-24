// API 类型定义
export interface LoginDTO {
  username: string
  password: string
  remember?: boolean
  captchaCode?: string
  captchaKey?: string
}

export interface LoginVO {
  token: string
  tokenType: string
  expiresAt: number
}

export interface RegisterDTO extends LoginDTO {
  email: string
  confirmPassword?: string
  nickname?: string
  agreeTerms?: boolean
}

export interface UserVO {
  id: number
  username: string
  nickname: string
  avatar?: string
  email?: string
  phone?: string
  sex?: number
  deptId?: number
  deptName?: string
  postIds?: number[]
  postNames?: string[]
  roleIds?: number[]
  roleNames?: string[]
  status: number
  createTime?: string
  updateTime?: string
  remark?: string
}

export interface PageResult<T> {
  records: T[]
  total: number
  current: number
  size: number
  pages?: number
}

export interface MenuVO {
  id: number
  parentId: number
  menuName: string
  path: string
  component?: string
  icon?: string
  sort: number
  visible: number
  status: number
  perm?: string
  menuType: 'M' | 'C' | 'F'
  children?: MenuVO[]
}

export interface DeptVO {
  id: number
  parentId: number
  deptName: string
  sort: number
  leaderUserId?: number
  leaderName?: string
  phone?: string
  email?: string
  status: number
  createTime?: string
  children?: DeptVO[]
}

export interface RoleVO {
  id: number
  roleName: string
  roleKey: string
  sort: number
  dataScope: number
  status: number
  remark?: string
  menuIds?: number[]
  createTime?: string
}

// Result 封装
export interface Result<T = unknown> {
  code: number
  message: string
  data: T
}

export interface PageParam {
  current: number
  size: number
  keyword?: string
}