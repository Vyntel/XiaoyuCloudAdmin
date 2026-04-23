<template>
  <div class="config-manage">
    <el-card class="search-card">
      <el-form :inline="true">
        <el-form-item label="配置Key">
          <el-input v-model="searchForm.configKey" placeholder="请输入配置Key" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
          <el-button type="success" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="table-card">
      <el-table v-loading="loading" :data="tableData" row-key="id" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="configKey" label="配置键名" width="200" />
        <el-table-column prop="configValue" label="配置值" width="200" />
        <el-table-column prop="configType" label="类型" width="80">
          <template #default="{ row }">
            <el-tag :type="row.configType === 'Y' ? 'warning' : 'info'" size="small">
              {{ row.configType === 'Y' ? '内置' : '自定义' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="isPublic" label="公开" width="80">
          <template #default="{ row }">
            <el-tag :type="row.isPublic === 1 ? 'success' : 'info'" size="small">
              {{ row.isPublic === 1 ? '公开' : '私有' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 0 ? 'success' : 'danger'" size="small">
              {{ row.status === 0 ? '正常' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" link size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const searchForm = ref({
  configKey: ''
})
const tableData = ref([
  {
    id: 1,
    configKey: 'sys.index.skinName',
    configValue: 'skin-purple-light',
    configType: 'N',
    isPublic: 1,
    status: 0,
    createTime: '2024-01-01 10:00:00'
  },
  {
    id: 2,
    configKey: 'sys.account.captchaStatus',
    configValue: '0',
    configType: 'N',
    isPublic: 1,
    status: 0,
    createTime: '2024-01-01 10:00:00'
  }
])

function handleSearch() {
  loading.value = true
  setTimeout(() => {
    loading.value = false
  }, 500)
}

function handleReset() {
  searchForm.value.configKey = ''
  handleSearch()
}

function handleAdd() {
  ElMessage.info('新增配置')
}

function handleEdit(row?: any) {
  ElMessage.info('编辑配置: ' + row?.configKey)
}

function handleDelete(row?: any) {
  ElMessageBox.confirm(`确认删除配置 ${row?.configKey}?`, '提示', { type: 'warning' }).then(() => {
    ElMessage.success('删除成功')
  })
}
</script>

<style scoped lang="scss">
.config-manage {
  .search-card {
    margin-bottom: 20px;
  }
}
</style>