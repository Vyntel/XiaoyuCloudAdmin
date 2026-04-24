import { get, post } from '@/utils/request'
import type { LoginVO, LoginDTO, RegisterDTO } from '@/types'

// 认证相关接口
export const authApi = {
  // 登录
  login: (data: LoginDTO) => post<LoginVO>('/auth/login', data),

  // 注册
  register: (data: RegisterDTO) => post('/auth/register', data),

  // 登出
  logout: () => post('/auth/logout'),

  // 获取验证码
  getCaptcha: () => get('/auth/captcha'),

  // 刷新Token
  refreshToken: (refreshToken: string) => post('/auth/refresh', { refreshToken }),

  // 获取用户信息
  getUserInfo: () => get('/auth/user-info')
}