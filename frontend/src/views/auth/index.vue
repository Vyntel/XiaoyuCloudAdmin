<template>
  <div class="auth-container">
    <div class="auth-bg">
      <!-- 动态粒子系统 -->
      <div class="particles">
        <div class="particle" v-for="i in 50" :key="i"
             :style="{
               left: Math.random() * 100 + '%',
               top: Math.random() * 100 + '%',
               animationDelay: Math.random() * 10 + 's',
               animationDuration: (Math.random() * 10 + 10) + 's'
             }">
        </div>
      </div>

      <!-- 流动光线效果 -->
      <div class="light-streams">
        <div class="light-stream" v-for="i in 3" :key="'stream-' + i"
             :style="{ animationDelay: i * 2 + 's' }">
        </div>
      </div>

      <!-- 几何形状网格 -->
      <div class="grid-overlay">
        <svg width="100%" height="100%" class="grid-svg">
          <defs>
            <pattern id="grid" width="50" height="50" patternUnits="userSpaceOnUse">
              <path d="M 50 0 L 0 0 0 50" fill="none" stroke="rgba(102, 126, 234, 0.1)" stroke-width="1"/>
            </pattern>
          </defs>
          <rect width="100%" height="100%" fill="url(#grid)" />
        </svg>
      </div>
    </div>

    <div class="auth-wrapper">
      <!-- 切换标签 -->
      <div class="auth-tabs">
        <div class="tab-item"
             :class="{ active: activeTab === 'login' }"
             @click="activeTab = 'login'">
          <span class="tab-text">登录</span>
          <div class="tab-indicator"></div>
        </div>
        <div class="tab-item"
             :class="{ active: activeTab === 'register' }"
             @click="activeTab = 'register'">
          <span class="tab-text">注册</span>
          <div class="tab-indicator"></div>
        </div>
      </div>

      <!-- 登录表单 -->
      <transition name="slide-fade" mode="out-in">
        <div v-if="activeTab === 'login'" class="auth-card">
          <div class="card-header">
            <h1 class="title">欢迎回来</h1>
            <p class="subtitle">使用您的账户访问系统</p>
          </div>

          <el-form ref="loginFormRef" :model="loginForm" :rules="loginRules" class="auth-form">
            <el-form-item prop="username">
              <div class="input-wrapper">
                <div class="input-icon">
                  <el-icon><User /></el-icon>
                </div>
                <el-input
                  v-model="loginForm.username"
                  placeholder="请输入用户名"
                  size="large"
                  :prefix-icon="User"
                  class="custom-input"
                />
              </div>
            </el-form-item>

            <el-form-item prop="password">
              <div class="input-wrapper">
                <div class="input-icon">
                  <el-icon><Lock /></el-icon>
                </div>
                <el-input
                  v-model="loginForm.password"
                  type="password"
                  placeholder="请输入密码"
                  size="large"
                  :prefix-icon="Lock"
                  show-password
                  class="custom-input"
                />
              </div>
            </el-form-item>

            <div class="form-options">
              <el-checkbox v-model="loginForm.remember">记住我</el-checkbox>
              <a href="#" class="forgot-link">忘记密码？</a>
            </div>

            <el-form-item>
              <el-button
                type="primary"
                size="large"
                class="auth-btn"
                :loading="loginLoading"
                @click="handleLogin"
              >
                {{ loginLoading ? '登录中...' : '登 录' }}
              </el-button>
            </el-form-item>
          </el-form>
        </div>

        <!-- 注册表单 -->
        <div v-else class="auth-card">
          <div class="card-header">
            <h1 class="title">创建账户</h1>
            <p class="subtitle">加入我们的管理系统</p>
          </div>

          <el-form ref="registerFormRef" :model="registerForm" :rules="registerRules" class="auth-form">
            <el-form-item prop="username">
              <div class="input-wrapper">
                <div class="input-icon">
                  <el-icon><User /></el-icon>
                </div>
                <el-input
                  v-model="registerForm.username"
                  placeholder="请输入用户名"
                  size="large"
                  class="custom-input"
                />
              </div>
            </el-form-item>

            <el-form-item prop="email">
              <div class="input-wrapper">
                <div class="input-icon">
                  <el-icon><Message /></el-icon>
                </div>
                <el-input
                  v-model="registerForm.email"
                  placeholder="请输入邮箱"
                  size="large"
                  class="custom-input"
                />
              </div>
            </el-form-item>

            <el-form-item prop="password">
              <div class="input-wrapper">
                <div class="input-icon">
                  <el-icon><Lock /></el-icon>
                </div>
                <el-input
                  v-model="registerForm.password"
                  type="password"
                  placeholder="请输入密码"
                  size="large"
                  show-password
                  class="custom-input"
                />
              </div>
            </el-form-item>

            <el-form-item prop="confirmPassword">
              <div class="input-wrapper">
                <div class="input-icon">
                  <el-icon><Lock /></el-icon>
                </div>
                <el-input
                  v-model="registerForm.confirmPassword"
                  type="password"
                  placeholder="请确认密码"
                  size="large"
                  show-password
                  class="custom-input"
                />
              </div>
            </el-form-item>

            <el-form-item prop="nickname">
              <div class="input-wrapper">
                <div class="input-icon">
                  <el-icon><Star /></el-icon>
                </div>
                <el-input
                  v-model="registerForm.nickname"
                  placeholder="请输入昵称（可选）"
                  size="large"
                  class="custom-input"
                />
              </div>
            </el-form-item>

            <div class="form-options">
              <el-checkbox v-model="registerForm.agreeTerms">
                我同意<a href="#" class="terms-link">服务条款</a>和<a href="#" class="terms-link">隐私政策</a>
              </el-checkbox>
            </div>

            <el-form-item>
              <el-button
                type="primary"
                size="large"
                class="auth-btn"
                :loading="registerLoading"
                @click="handleRegister"
              >
                {{ registerLoading ? '注册中...' : '注 册' }}
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </transition>

      <!-- 底部信息 -->
      <div class="auth-footer">
        <p>© 2024 小羽快速开发框架. All rights reserved.</p>
        <div class="footer-links">
          <a href="#" class="footer-link">帮助中心</a>
          <span class="separator">|</span>
          <a href="#" class="footer-link">联系我们</a>
          <span class="separator">|</span>
          <a href="#" class="footer-link">关于我们</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import { User, Lock, Message, Star } from '@element-plus/icons-vue'
import { authApi } from '@/api'
import type { LoginDTO, RegisterDTO } from '@/types'

const router = useRouter()

// 活跃标签页
const activeTab = ref<'login' | 'register'>('login')

// 登录表单
const loginFormRef = ref<FormInstance>()
const loginLoading = ref(false)
const loginForm = reactive<LoginDTO>({
  username: '',
  password: ''
})

const loginRules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 50, message: '用户名长度必须在2-50之间', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 100, message: '密码长度必须在6-100之间', trigger: 'blur' }
  ]
}

// 注册表单
const registerFormRef = ref<FormInstance>()
const registerLoading = ref(false)
const registerForm = reactive<RegisterDTO & { confirmPassword: string }>({
  username: '',
  password: '',
  email: '',
  confirmPassword: '',
  nickname: '',
  agreeTerms: false
})

const registerRules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 50, message: '用户名长度必须在2-50之间', trigger: 'blur' },
    { pattern: /^[a-zA-Z0-9_]+$/, message: '用户名只能包含字母、数字、下划线', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 100, message: '密码长度必须在6-100之间', trigger: 'blur' },
    { pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)/, message: '密码必须包含大小写字母和数字', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (_rule: any, value: string, callback: any) => {
        if (value !== registerForm.password) {
          callback(new Error('两次输入密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  nickname: [
    { max: 50, message: '昵称长度不能超过50', trigger: 'blur' }
  ]
}

// 登录处理
async function handleLogin() {
  if (!loginFormRef.value) return

  await loginFormRef.value.validate(async (valid) => {
    if (!valid) return

    loginLoading.value = true
    try {
      const response = await authApi.login(loginForm)
      const { token, tokenType } = response.data

      // 保存token
      const fullToken = tokenType ? `${tokenType} ${token}` : token
      localStorage.setItem('token', fullToken)

      ElMessage.success('登录成功')
      router.push('/')
    } catch (error) {
      console.error('登录失败:', error)
      ElMessage.error('登录失败，请检查用户名和密码')
    } finally {
      loginLoading.value = false
    }
  })
}

// 注册处理
async function handleRegister() {
  if (!registerFormRef.value) return

  await registerFormRef.value.validate(async (valid) => {
    if (!valid) return

    if (!registerForm.agreeTerms) {
      ElMessage.error('请同意服务条款和隐私政策')
      return
    }

    registerLoading.value = true
    try {
      // 这里应该调用注册API，但由于后端可能还没有实现，我们先模拟
      await new Promise(resolve => setTimeout(resolve, 2000)) // 模拟API调用

      ElMessage.success('注册成功，请登录')
      activeTab.value = 'login'
      // 清空注册表单
      Object.assign(registerForm, {
        username: '',
        password: '',
        confirmPassword: '',
        nickname: '',
        email: '',
        agreeTerms: false
      })
    } catch (error) {
      console.error('注册失败:', error)
      ElMessage.error('注册失败，请稍后重试')
    } finally {
      registerLoading.value = false
    }
  })
}
</script>

<style scoped lang="scss">
.auth-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--bg-primary);
  position: relative;
  overflow: hidden;
  font-family: 'Inter', sans-serif;
}

// 背景动画系统
.auth-bg {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: hidden;
}

// 粒子系统
.particles {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  pointer-events: none;

  .particle {
    position: absolute;
    width: 2px;
    height: 2px;
    background: var(--accent-color);
    border-radius: 50%;
    box-shadow: 0 0 6px var(--accent-color);
    animation: float-particle 15s linear infinite;

    &:nth-child(3n) {
      background: var(--primary-color);
      box-shadow: 0 0 8px var(--primary-color);
      animation-duration: 20s;
    }

    &:nth-child(5n) {
      background: var(--success-color);
      box-shadow: 0 0 10px var(--success-color);
      animation-duration: 25s;
    }
  }
}

@keyframes float-particle {
  0% {
    transform: translateY(100vh) rotate(0deg);
    opacity: 0;
  }
  10% {
    opacity: 1;
  }
  90% {
    opacity: 1;
  }
  100% {
    transform: translateY(-100px) rotate(360deg);
    opacity: 0;
  }
}

// 流动光线效果
.light-streams {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  pointer-events: none;

  .light-stream {
    position: absolute;
    top: 0;
    left: -100px;
    width: 200px;
    height: 2px;
    background: linear-gradient(90deg,
      transparent 0%,
      var(--primary-color) 20%,
      var(--accent-color) 50%,
      var(--primary-color) 80%,
      transparent 100%
    );
    box-shadow: 0 0 20px var(--primary-color), 0 0 40px var(--accent-color);
    animation: light-flow 8s linear infinite;

    &:nth-child(2) {
      top: 30%;
      animation-delay: 2s;
    }

    &:nth-child(3) {
      top: 70%;
      animation-delay: 4s;
    }
  }
}

@keyframes light-flow {
  0% {
    left: -200px;
    opacity: 0;
  }
  10% {
    opacity: 1;
  }
  90% {
    opacity: 1;
  }
  100% {
    left: calc(100% + 200px);
    opacity: 0;
  }
}

// 几何网格背景
.grid-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  opacity: 0.3;

  .grid-svg {
    width: 100%;
    height: 100%;
  }
}

// 主容器
.auth-wrapper {
  width: 100%;
  max-width: 1200px;
  padding: 40px;
  position: relative;
  z-index: 1;
}

// 标签页切换
.auth-tabs {
  display: flex;
  justify-content: center;
  margin-bottom: 40px;

  .tab-item {
    position: relative;
    padding: 16px 40px;
    cursor: pointer;
    transition: all var(--transition-normal);
    border-radius: var(--border-radius-lg) var(--border-radius-lg) 0 0;

    .tab-text {
      font-size: 18px;
      font-weight: 600;
      font-family: 'JetBrains Mono', monospace;
      color: var(--text-tertiary);
      letter-spacing: 1px;
      transition: all var(--transition-normal);
    }

    .tab-indicator {
      position: absolute;
      bottom: 0;
      left: 50%;
      transform: translateX(-50%);
      width: 0;
      height: 3px;
      background: linear-gradient(90deg, var(--primary-color), var(--accent-color));
      border-radius: 2px;
      transition: all var(--transition-normal);
      box-shadow: 0 0 10px var(--primary-color);
    }

    &:hover .tab-text {
      color: var(--text-secondary);
    }

    &.active {
      background: var(--bg-card);
      border: 1px solid var(--border-color);
      border-bottom: none;
      box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);

      .tab-text {
        color: var(--accent-color);
        text-shadow: 0 0 10px var(--accent-color);
      }

      .tab-indicator {
        width: 60px;
      }
    }
  }
}

// 认证卡片
.auth-card {
  background: var(--bg-card);
  border: 1px solid var(--border-color);
  border-radius: 0 var(--border-radius-lg) var(--border-radius-lg) var(--border-radius-lg);
  backdrop-filter: blur(20px);
  box-shadow:
    0 25px 50px -12px rgba(0, 0, 0, 0.5),
    0 0 0 1px rgba(255, 255, 255, 0.1),
    inset 0 1px 0 rgba(255, 255, 255, 0.1);
  padding: 60px;
  position: relative;
  overflow: hidden;

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 2px;
    background: linear-gradient(90deg,
      transparent,
      var(--primary-color),
      var(--accent-color),
      var(--danger-color),
      transparent
    );
    opacity: 0;
    transition: opacity var(--transition-normal);
  }

  &:hover::before {
    opacity: 1;
  }

  .card-header {
    text-align: center;
    margin-bottom: 50px;

    .title {
      font-size: 36px;
      font-weight: 700;
      font-family: 'JetBrains Mono', monospace;
      background: linear-gradient(135deg, var(--primary-color), var(--accent-color));
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
      margin-bottom: 12px;
      letter-spacing: 2px;
      text-shadow: 0 0 20px rgba(102, 126, 234, 0.3);
      animation: neon-pulse 3s ease-in-out infinite alternate;
    }

    .subtitle {
      font-size: 16px;
      color: var(--text-secondary);
      font-weight: 300;
      letter-spacing: 0.5px;
    }
  }
}

@keyframes neon-pulse {
  0%, 100% {
    text-shadow: 0 0 20px rgba(102, 126, 234, 0.3);
  }
  50% {
    text-shadow:
      0 0 20px rgba(102, 126, 234, 0.5),
      0 0 30px rgba(102, 126, 234, 0.4),
      0 0 40px rgba(102, 126, 234, 0.3);
  }
}

// 表单样式
.auth-form {
  .input-wrapper {
    position: relative;
    margin-bottom: 20px;

    .input-icon {
      position: absolute;
      left: 16px;
      top: 50%;
      transform: translateY(-50%);
      z-index: 2;
      color: var(--text-tertiary);
      transition: all var(--transition-normal);
      font-size: 18px;
    }

    :deep(.el-input) {
      .el-input__wrapper {
        background: rgba(255, 255, 255, 0.05);
        border: 1px solid var(--border-color);
        border-radius: var(--border-radius);
        padding: 16px 16px 16px 56px;
        backdrop-filter: blur(10px);
        transition: all var(--transition-normal);
        box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1);

        &:hover {
          border-color: var(--primary-color);
          box-shadow:
            inset 0 1px 0 rgba(255, 255, 255, 0.1),
            0 0 10px rgba(102, 126, 234, 0.2);
        }

        &.is-focus {
          border-color: var(--accent-color);
          box-shadow:
            inset 0 1px 0 rgba(255, 255, 255, 0.1),
            0 0 20px rgba(0, 255, 136, 0.3),
            0 0 40px rgba(0, 255, 136, 0.1);
        }
      }

      .el-input__inner {
        color: var(--text-primary);
        font-size: 16px;
        font-weight: 400;
        background: transparent;
        border: none;
        padding: 0;

        &::placeholder {
          color: var(--text-tertiary);
          font-weight: 300;
        }
      }
    }

    // 输入框聚焦时图标发光
    .el-input.is-focus + .input-icon {
      color: var(--accent-color);
      text-shadow: 0 0 10px var(--accent-color);
    }
  }

  .form-options {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30px;

    :deep(.el-checkbox) {
      .el-checkbox__label {
        color: var(--text-secondary);
        font-size: 14px;
      }

      .el-checkbox__input {
        &.is-checked {
          .el-checkbox__inner {
            background: var(--accent-color);
            border-color: var(--accent-color);
          }
        }
      }
    }

    .forgot-link,
    .terms-link {
      color: var(--primary-color);
      text-decoration: none;
      font-size: 14px;
      font-weight: 500;
      transition: all var(--transition-normal);

      &:hover {
        color: var(--accent-color);
        text-shadow: 0 0 8px var(--accent-color);
      }
    }
  }

  .auth-btn {
    width: 100%;
    height: 60px;
    font-size: 18px;
    font-weight: 600;
    font-family: 'JetBrains Mono', monospace;
    letter-spacing: 2px;
    background: var(--primary-gradient);
    border: none;
    border-radius: var(--border-radius);
    color: white;
    position: relative;
    overflow: hidden;
    transition: all var(--transition-normal);
    box-shadow:
      0 4px 15px rgba(102, 126, 234, 0.3),
      inset 0 1px 0 rgba(255, 255, 255, 0.1);

    &::before {
      content: '';
      position: absolute;
      top: 0;
      left: -100%;
      width: 100%;
      height: 100%;
      background: linear-gradient(90deg,
        transparent,
        rgba(255, 255, 255, 0.2),
        transparent
      );
      transition: left var(--transition-slow);
    }

    &:hover {
      transform: translateY(-2px);
      box-shadow:
        0 8px 25px rgba(102, 126, 234, 0.4),
        0 0 30px rgba(102, 126, 234, 0.2),
        inset 0 1px 0 rgba(255, 255, 255, 0.1);

      &::before {
        left: 100%;
      }
    }

    &:active {
      transform: translateY(0);
    }
  }
}

// 底部信息
.auth-footer {
  text-align: center;
  margin-top: 40px;
  padding: 30px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: var(--border-radius-lg);
  backdrop-filter: blur(10px);
  border: 1px solid var(--border-color);

  p {
    color: var(--text-tertiary);
    font-size: 14px;
    margin-bottom: 16px;
    font-weight: 300;
  }

  .footer-links {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 16px;

    .footer-link {
      color: var(--text-secondary);
      text-decoration: none;
      font-size: 13px;
      font-weight: 400;
      transition: all var(--transition-normal);

      &:hover {
        color: var(--primary-color);
        text-shadow: 0 0 8px var(--primary-color);
      }
    }

    .separator {
      color: var(--text-muted);
      font-size: 12px;
    }
  }
}

// 页面切换动画
.slide-fade-enter-active,
.slide-fade-leave-active {
  transition: all var(--transition-normal) cubic-bezier(0.4, 0, 0.2, 1);
}

.slide-fade-enter-from {
  opacity: 0;
  transform: translateX(30px);
}

.slide-fade-leave-to {
  opacity: 0;
  transform: translateX(-30px);
}

// 响应式设计
@media (max-width: 768px) {
  .auth-wrapper {
    padding: 20px;
  }

  .auth-card {
    padding: 40px 30px;
  }

  .card-header .title {
    font-size: 28px;
  }

  .auth-tabs .tab-item {
    padding: 12px 30px;

    .tab-text {
      font-size: 16px;
    }
  }
}

@media (max-width: 480px) {
  .auth-card {
    padding: 30px 20px;
  }

  .card-header {
    margin-bottom: 30px;

    .title {
      font-size: 24px;
    }
  }

  .auth-form .input-wrapper .input-icon {
    left: 12px;
  }

  .auth-form .input-wrapper :deep(.el-input .el-input__wrapper) {
    padding: 12px 12px 12px 48px;
  }
}
</style>