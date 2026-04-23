<template>
  <div class="login-container">
    <div class="login-bg">
      <div class="bg-shape bg-shape-1"></div>
      <div class="bg-shape bg-shape-2"></div>
      <div class="bg-shape bg-shape-3"></div>
    </div>

    <div class="login-card">
      <div class="login-header">
        <h1 class="title">小羽快速开发框架</h1>
        <p class="subtitle">基于 Spring Boot + Vue 3 的企业级开发平台</p>
      </div>

      <el-form ref="formRef" :model="form" :rules="rules" class="login-form" @submit.prevent="handleLogin">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" size="large" :prefix-icon="User" />
        </el-form-item>

        <el-form-item prop="password">
          <el-input
            v-model="form.password"
            type="password"
            placeholder="请输入密码"
            size="large"
            :prefix-icon="Lock"
            show-password
            @keyup.enter="handleLogin"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" size="large" class="login-btn" :loading="loading" @click="handleLogin">
            {{ loading ? '登录中...' : '登 录' }}
          </el-button>
        </el-form-item>
      </el-form>

      <div class="login-footer">
        <span>请使用系统管理员账号登录</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'
import { authApi } from '@/api'
import type { LoginDTO } from '@/types'

const router = useRouter()

const formRef = ref<FormInstance>()
const loading = ref(false)

const form = reactive<LoginDTO>({
  username: '',
  password: ''
})

const rules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 50, message: '用户名长度必须在2-50之间', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 100, message: '密码长度必须在6-100之间', trigger: 'blur' }
  ]
}

async function handleLogin() {
  if (!formRef.value) return

  await formRef.value.validate(async (valid) => {
    if (!valid) return

    loading.value = true
    try {
      const response = await authApi.login(form)
      const { token, tokenType } = response.data

      // 保存token
      const fullToken = tokenType ? `${tokenType} ${token}` : token
      localStorage.setItem('token', fullToken)

      ElMessage.success('登录成功')
      router.push('/')
    } catch (error) {
      console.error('登录失败:', error)
      // 不模拟登录，让用户看到真实的错误
      ElMessage.error('登录失败，请检查用户名和密码')
    } finally {
      loading.value = false
    }
  })
}
</script>

<style scoped lang="scss">
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--bg-primary);
  position: relative;
  overflow: hidden;

  // 动态星空背景
  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-image:
      radial-gradient(2px 2px at 20px 30px, rgba(255, 255, 255, 0.5), transparent),
      radial-gradient(2px 2px at 40px 70px, rgba(0, 255, 136, 0.3), transparent),
      radial-gradient(1px 1px at 90px 40px, rgba(102, 126, 234, 0.4), transparent),
      radial-gradient(1px 1px at 130px 80px, rgba(255, 71, 87, 0.3), transparent),
      radial-gradient(2px 2px at 160px 30px, rgba(255, 165, 2, 0.4), transparent);
    background-repeat: repeat;
    background-size: 200px 100px;
    animation: twinkle 4s ease-in-out infinite alternate;
  }

  // 几何形状装饰
  .bg-shape {
    position: absolute;
    border-radius: 50%;
    filter: blur(80px);
    opacity: 0.4;
    animation: float 8s ease-in-out infinite;
  }
}

.login-bg {
  position: absolute;
  inset: 0;
  overflow: hidden;

  .bg-shape {
    position: absolute;
    border-radius: 50%;
    filter: blur(80px);
    opacity: 0.4;
  }

  .bg-shape-1 {
    width: 500px;
    height: 500px;
    background: var(--primary-gradient);
    top: -150px;
    right: -150px;
    animation: float 8s ease-in-out infinite;
  }

  .bg-shape-2 {
    width: 400px;
    height: 400px;
    background: linear-gradient(135deg, var(--accent-color), var(--success-color));
    bottom: -100px;
    left: -100px;
    animation: float 10s ease-in-out infinite reverse;
  }

  .bg-shape-3 {
    width: 300px;
    height: 300px;
    background: linear-gradient(135deg, var(--danger-color), var(--warning-color));
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    animation: float 12s ease-in-out infinite;
  }
}

@keyframes float {
  0%, 100% {
    transform: translate(0, 0) rotate(0deg);
  }
  50% {
    transform: translate(30px, 30px) rotate(180deg);
  }
}

@keyframes twinkle {
  0%, 100% {
    opacity: 0.3;
  }
  50% {
    opacity: 0.8;
  }
}

.login-card {
  width: 480px;
  padding: 60px 50px;
  background: var(--bg-card);
  border-radius: var(--border-radius-lg);
  box-shadow:
    0 25px 50px -12px rgba(0, 0, 0, 0.5),
    0 0 0 1px rgba(255, 255, 255, 0.1),
    inset 0 1px 0 rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(20px);
  position: relative;
  z-index: 1;
  border: 1px solid rgba(255, 255, 255, 0.1);
  animation: fadeInUp 0.8s ease-out;

  // 科技边框光效
  &::before {
    content: '';
    position: absolute;
    top: -2px;
    left: -2px;
    right: -2px;
    bottom: -2px;
    background: linear-gradient(45deg,
      transparent,
      var(--primary-color),
      var(--accent-color),
      transparent,
      var(--danger-color)
    );
    border-radius: var(--border-radius-lg);
    z-index: -1;
    opacity: 0;
    transition: opacity var(--transition-normal);
  }

  &:hover::before {
    opacity: 0.3;
  }

  .login-header {
    text-align: center;
    margin-bottom: 50px;

    .title {
      font-size: 32px;
      font-weight: 700;
      font-family: 'JetBrains Mono', monospace;
      background: linear-gradient(135deg, var(--primary-color), var(--accent-color));
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
      margin-bottom: 12px;
      letter-spacing: 3px;
      text-shadow: 0 0 20px rgba(102, 126, 234, 0.3);
      animation: glow 3s ease-in-out infinite alternate;
    }

    .subtitle {
      font-size: 16px;
      color: var(--text-secondary);
      font-weight: 300;
      letter-spacing: 1px;
    }
  }

  .login-form {
    .login-btn {
      width: 100%;
      height: 56px;
      font-size: 16px;
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
      box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);

      &::before {
        content: '';
        position: absolute;
        top: 0;
        left: -100%;
        width: 100%;
        height: 100%;
        background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
        transition: left var(--transition-normal);
      }

      &:hover {
        transform: translateY(-2px);
        box-shadow:
          0 8px 25px rgba(102, 126, 234, 0.4),
          0 0 30px rgba(102, 126, 234, 0.2);
        background: linear-gradient(135deg, var(--primary-hover), var(--accent-color));

        &::before {
          left: 100%;
        }
      }

      &:active {
        transform: translateY(0);
      }
    }
  }

  .login-footer {
    text-align: center;
    margin-top: 32px;
    font-size: 14px;
    color: var(--text-tertiary);
    font-weight: 300;
    letter-spacing: 0.5px;
  }
}

:deep(.el-input__wrapper) {
  padding: 12px 16px;
  border-radius: var(--border-radius);
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  transition: all var(--transition-normal);

  &:hover {
    border-color: var(--primary-color);
    box-shadow: 0 0 10px rgba(102, 126, 234, 0.2);
  }

  &.is-focus {
    border-color: var(--accent-color);
    box-shadow: 0 0 20px rgba(0, 255, 136, 0.3);
    background: rgba(255, 255, 255, 0.08);
  }
}

:deep(.el-input__inner) {
  height: 48px;
  color: var(--text-primary);
  font-size: 15px;
  font-weight: 400;

  &::placeholder {
    color: var(--text-tertiary);
    font-weight: 300;
  }
}

:deep(.el-form-item__label) {
  color: var(--text-secondary);
  font-weight: 500;
  margin-bottom: 8px;
}
</style>