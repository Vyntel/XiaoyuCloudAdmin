<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="stat-card">
          <div class="stat-icon" style="background: linear-gradient(135deg, #409eff, #337ecc)">
            <el-icon><User /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">1,234</div>
            <div class="stat-label">用户总数</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card">
          <div class="stat-icon" style="background: linear-gradient(135deg, #67c23a, #529b2e)">
            <el-icon><Key /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">12</div>
            <div class="stat-label">角色数量</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card">
          <div class="stat-icon" style="background: linear-gradient(135deg, #e6a23c, #c8922e)">
            <el-icon><Menu /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">28</div>
            <div class="stat-label">菜单数量</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card">
          <div class="stat-icon" style="background: linear-gradient(135deg, #f56c6c, #d94242)">
            <el-icon><OfficeBuilding /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">8</div>
            <div class="stat-label">部门数量</div>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="16">
        <el-card header="系统信息">
          <div class="sys-info">
            <div class="info-item">
              <span class="label">框架版本</span>
              <span class="value">xiaoyu-cloud v1.0.0</span>
            </div>
            <div class="info-item">
              <span class="label">Spring Boot</span>
              <span class="value">3.5.x</span>
            </div>
            <div class="info-item">
              <span class="label">Vue</span>
              <span class="value">3.5.x</span>
            </div>
            <div class="info-item">
              <span class="label">数据库</span>
              <span class="value">MySQL 8.0</span>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card header="快捷操作">
          <div class="quick-actions">
            <el-button type="primary" plain>用户管理</el-button>
            <el-button type="success" plain>角色管理</el-button>
            <el-button type="warning" plain>菜单管理</el-button>
            <el-button type="info" plain>部门管理</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { User, Key, Menu, OfficeBuilding } from '@element-plus/icons-vue'
</script>

<style scoped lang="scss">
.dashboard {
  .stat-card {
    background: #fff;
    border-radius: 12px;
    padding: 24px;
    display: flex;
    align-items: center;
    gap: 16px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);

    .stat-icon {
      width: 60px;
      height: 60px;
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 28px;
      color: #fff;
    }

    .stat-info {
      .stat-value {
        font-size: 28px;
        font-weight: 700;
        color: #333;
      }
      .stat-label {
        font-size: 14px;
        color: #999;
        margin-top: 4px;
      }
    }
  }

  .sys-info {
    .info-item {
      display: flex;
      justify-content: space-between;
      padding: 12px 0;
      border-bottom: 1px solid #f0f0f0;

      &:last-child {
        border-bottom: none;
      }

      .label {
        color: #666;
      }

      .value {
        color: #333;
        font-weight: 500;
      }
    }
  }

  .quick-actions {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;

    .el-button {
      width: calc(50% - 5px);
    }
  }
}
</style>