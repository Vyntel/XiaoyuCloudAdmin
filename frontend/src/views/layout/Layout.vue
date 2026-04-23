<template>
  <div class="layout">
    <!-- 侧边栏 -->
    <aside class="sidebar" :class="{ collapsed: isCollapsed }">
      <div class="logo">
        <el-icon class="logo-icon" size="28"><Monitor /></el-icon>
        <span v-if="!isCollapsed" class="logo-text">小羽框架</span>
      </div>

      <!-- 菜单 -->
      <el-menu
        :default-active="activeMenu"
        class="sidebar-menu"
        :collapse="isCollapsed"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409eff"
        :router="true"
      >
        <template v-for="menu in menuList" :key="menu.id">
          <el-sub-menu v-if="menu.children && menu.children.length > 0" :index="menu.path">
            <template #title>
              <el-icon>
                <component :is="getIcon(menu.icon)" />
              </el-icon>
              <span>{{ menu.menuName }}</span>
            </template>
            <template v-for="child in menu.children" :key="child.id">
              <el-menu-item :index="child.path">
                <el-icon>
                  <component :is="getIcon(child.icon)" />
                </el-icon>
                <template #title>{{ child.menuName }}</template>
              </el-menu-item>
            </template>
          </el-sub-menu>
          <el-menu-item v-else :index="menu.path">
            <el-icon>
              <component :is="getIcon(menu.icon)" />
            </el-icon>
            <template #title>{{ menu.menuName }}</template>
          </el-menu-item>
        </template>
      </el-menu>
    </aside>

    <!-- 右侧内容 -->
    <div class="main">
      <!-- 头部 -->
      <header class="header">
        <div class="header-left">
          <el-icon class="collapse-btn" @click="toggleCollapse">
            <Fold v-if="!isCollapsed" />
            <Expand v-else />
          </el-icon>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item v-for="item in breadcrumbs" :key="item.path" :to="item.path">
              {{ item.meta?.title }}
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>

        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-icon><User /></el-icon>
              <span class="username">{{ userStore.userInfo?.nickname || '用户' }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </header>

      <!-- 内容区 -->
      <main class="content">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { useUserStore } from '@/stores/user'
import {
  HomeFilled,
  Setting,
  User,
  Key,
  Menu as MenuIcon,
  OfficeBuilding,
  Fold,
  Expand,
  Monitor
} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const isCollapsed = ref(false)

const activeMenu = computed(() => route.path)

const breadcrumbs = computed(() => {
  return route.matched.filter((item) => item.meta?.title)
})

// 动态菜单列表
const menuList = computed(() => {
  return userStore.menus
})

// 获取图标组件
function getIcon(iconName?: string) {
  const iconMap: Record<string, any> = {
    HomeFilled,
    Setting,
    User,
    Key,
    Menu: MenuIcon,
    OfficeBuilding,
    Monitor
  }
  return iconName && iconMap[iconName] ? iconMap[iconName] : Setting
}

function toggleCollapse() {
  isCollapsed.value = !isCollapsed.value
}

async function handleCommand(command: string) {
  if (command === 'logout') {
    await ElMessageBox.confirm('确认退出登录?', '提示', {
      type: 'warning'
    })
    userStore.logout()
    router.push('/auth')
  }
}

// 组件挂载时检查登录状态
onMounted(() => {
  if (!userStore.isLoggedIn()) {
    router.push('/login')
  }
})
</script>

<style scoped lang="scss">
.layout {
  display: flex;
  height: 100vh;
  width: 100%;
  background: var(--bg-primary);
  overflow: hidden;
}

.sidebar {
  width: var(--sidebar-width);
  background: var(--bg-secondary);
  transition: width var(--transition-normal) cubic-bezier(0.4, 0, 0.2, 1);
  overflow: hidden;
  border-right: 1px solid var(--border-color);
  box-shadow: 2px 0 20px rgba(0, 0, 0, 0.3);
  backdrop-filter: blur(10px);

  &.collapsed {
    width: var(--sidebar-collapsed-width);
  }

  .logo {
    height: var(--header-height);
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 12px;
    background: var(--bg-tertiary);
    border-bottom: 1px solid var(--border-color);
    position: relative;

    &::before {
      content: '';
      position: absolute;
      bottom: 0;
      left: 0;
      right: 0;
      height: 1px;
      background: linear-gradient(90deg, transparent, var(--accent-color), transparent);
    }

    .logo-icon {
      font-size: 32px;
      color: var(--accent-color);
      filter: drop-shadow(0 0 10px rgba(0, 255, 136, 0.3));
      animation: pulse 2s ease-in-out infinite;
    }

    .logo-text {
      font-size: 20px;
      font-weight: 700;
      font-family: 'JetBrains Mono', monospace;
      background: linear-gradient(135deg, var(--primary-color), var(--accent-color));
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
      white-space: nowrap;
      letter-spacing: 1px;
      text-shadow: 0 0 15px rgba(102, 126, 234, 0.3);
    }
  }

  .sidebar-menu {
    border-right: none;
    height: calc(100vh - var(--header-height));
    overflow-y: auto;
    padding: 20px 0;

    :deep(.el-menu) {
      background: transparent;
      border-right: none;
    }

    :deep(.el-menu-item) {
      height: 50px;
      line-height: 50px;
      margin: 4px 16px;
      border-radius: var(--border-radius);
      color: var(--text-secondary);
      transition: all var(--transition-normal);

      &:hover {
        background: var(--bg-card-hover);
        color: var(--accent-color);
        box-shadow: 0 4px 15px rgba(0, 255, 136, 0.2);
      }

      &.is-active {
        background: var(--primary-gradient);
        color: white;
        box-shadow: 0 0 20px rgba(102, 126, 234, 0.3);
      }
    }

    :deep(.el-sub-menu) {
      .el-sub-menu__title {
        height: 50px;
        line-height: 50px;
        margin: 4px 16px;
        border-radius: var(--border-radius);
        color: var(--text-secondary);
        transition: all var(--transition-normal);

        &:hover {
          background: var(--bg-card-hover);
          color: var(--accent-color);
        }
      }

      &.is-active .el-sub-menu__title {
        background: var(--primary-gradient);
        color: white;
      }
    }
  }
}

.main {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.header {
  height: var(--header-height);
  background: var(--bg-secondary);
  border-bottom: 1px solid var(--border-color);
  box-shadow: 0 2px 20px rgba(0, 0, 0, 0.3);
  backdrop-filter: blur(10px);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 30px;
  position: relative;

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 1px;
    background: linear-gradient(90deg, transparent, var(--primary-color), transparent);
  }

  .header-left {
    display: flex;
    align-items: center;
    gap: 20px;

    .collapse-btn {
      font-size: 24px;
      cursor: pointer;
      color: var(--text-secondary);
      padding: 8px;
      border-radius: var(--border-radius-sm);
      transition: all var(--transition-normal);

      &:hover {
        color: var(--accent-color);
        background: var(--bg-card-hover);
        box-shadow: 0 0 15px rgba(0, 255, 136, 0.2);
      }
    }

    .breadcrumb {
      :deep(.el-breadcrumb__item) {
        .el-breadcrumb__inner {
          color: var(--text-secondary);
          font-weight: 500;

          &:hover {
            color: var(--accent-color);
          }
        }

        &:last-child .el-breadcrumb__inner {
          color: var(--text-primary);
          font-weight: 600;
        }
      }
    }
  }

  .header-right {
    .user-info {
      display: flex;
      align-items: center;
      gap: 10px;
      cursor: pointer;
      padding: 12px 16px;
      border-radius: var(--border-radius);
      background: var(--bg-card);
      border: 1px solid var(--border-color);
      transition: all var(--transition-normal);

      &:hover {
        background: var(--bg-card-hover);
        border-color: var(--primary-color);
        box-shadow: 0 0 15px rgba(102, 126, 234, 0.2);
      }

      .el-icon {
        color: var(--accent-color);
        font-size: 18px;
      }

      .username {
        color: var(--text-primary);
        font-weight: 500;
        margin-left: 6px;
      }
    }
  }
}

.content {
  flex: 1;
  padding: 30px;
  overflow-y: auto;
  background: var(--bg-primary);
  position: relative;

  // 微妙的背景纹理
  &::before {
    content: '';
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-image:
      radial-gradient(circle at 25% 25%, rgba(102, 126, 234, 0.03) 0%, transparent 50%),
      radial-gradient(circle at 75% 75%, rgba(0, 255, 136, 0.03) 0%, transparent 50%);
    background-size: 400px 400px;
    pointer-events: none;
    z-index: -1;
  }
}

// 过渡动画 - 更流畅的页面切换
.fade-enter-active,
.fade-leave-active {
  transition: all var(--transition-normal) cubic-bezier(0.4, 0, 0.2, 1);
}

.fade-enter-from {
  opacity: 0;
  transform: translateX(20px);
}

.fade-leave-to {
  opacity: 0;
  transform: translateX(-20px);
}

// 页面加载动画
.page-enter-active {
  animation: fadeInUp var(--transition-slow) ease-out;
}
</style>