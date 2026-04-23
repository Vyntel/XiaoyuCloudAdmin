<template>
  <div class="dict-manage">
    <el-card class="search-card">
      <el-form :inline="true">
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增字典</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="table-card">
      <el-table v-loading="loading" :data="tableData" row-key="id" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="字典名称" width="150" />
        <el-table-column prop="code" label="字典类型" width="150" />
        <el-table-column prop="description" label="描述" width="200" />
        <el-table-column prop="sort" label="排序" width="80" />
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
            <el-button type="primary" link size="small" @click="handleData(row)">数据</el-button>
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
const tableData = ref([
  {
    id: 1,
    name: '用户性别',
    code: 'user_sex',
    description: '用户性别列表',
    sort: 1,
    status: 0,
    createTime: '2024-01-01 10:00:00'
  },
  {
    id: 2,
    name: '菜单状态',
    code: 'menu_status',
    description: '菜单状态列表',
    sort: 2,
    status: 0,
    createTime: '2024-01-01 10:00:00'
  }
])

function handleAdd() {
  ElMessage.info('新增字典')
}

function handleEdit(row?: any) {
  ElMessage.info('编辑字典: ' + row?.name)
}

function handleData(row?: any) {
  ElMessage.info('字典数据: ' + row?.name)
}

function handleDelete(row?: any) {
  ElMessageBox.confirm(`确认删除字典 ${row?.name}?`, '提示', { type: 'warning' }).then(() => {
    ElMessage.success('删除成功')
  })
}
</script>

<style scoped lang="scss">
.dict-manage {
  .search-card {
    margin-bottom: 20px;
  }
}
</style>