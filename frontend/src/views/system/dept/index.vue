<template>
  <div class="dept-manage">
    <el-card class="search-card">
      <el-button type="primary" @click="handleAdd">新增部门</el-button>
    </el-card>

    <el-card class="table-card">
      <el-table v-loading="loading" :data="tableData" row-key="id" stripe default-expand-all>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="部门名称" width="180" />
        <el-table-column prop="sort" label="排序" width="80" />
        <el-table-column prop="leaderUserName" label="负责人" width="100" />
        <el-table-column prop="phone" label="联系电话" width="130" />
        <el-table-column prop="email" label="邮箱" width="180" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 0 ? 'success' : 'danger'" size="small">
              {{ row.status === 0 ? '正常' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180">
          <template #default="{ row }">
            {{ row.createTime ? new Date(row.createTime).toLocaleString() : '' }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="handleAdd(row)">新增子部门</el-button>
            <el-button type="primary" link size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" link size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增/编辑弹窗 -->
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑部门' : '新增部门'" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="上级部门">
          <el-tree-select
            v-model="form.parentId"
            :data="deptTree"
            check-strictly
            :render-after-expand="false"
            placeholder="请选择上级部门"
            :props="{ value: 'id', label: 'name', children: 'children' }"
          />
        </el-form-item>
        <el-form-item label="部门名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="0" />
        </el-form-item>
        <el-form-item label="负责人">
          <el-input v-model="form.leaderUserId" placeholder="请输入负责人ID" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :value="0">正常</el-radio>
            <el-radio :value="1">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import { deptApi } from '@/api'
import type { DeptVO } from '@/types'

const loading = ref(false)
const tableData = ref<DeptVO[]>([])

// 弹窗
const dialogVisible = ref(false)
const formRef = ref<FormInstance>()
const form = reactive({
  id: 0,
  parentId: 0,
  name: '',
  sort: 0,
  leaderUserId: undefined,
  phone: '',
  email: '',
  status: 0
})

// 表单验证规则
const rules: FormRules = {
  deptName: [{ required: true, message: '请输入部门名称', trigger: 'blur' }]
}

// 部门树（用于上级部门选择）
const deptTree = ref<DeptVO[]>([])

// 加载部门数据
async function loadData() {
  loading.value = true
  try {
    const response = await deptApi.getDeptTree()
    tableData.value = response.data
    // 复制一份用于上级部门选择（排除自己）
    deptTree.value = JSON.parse(JSON.stringify(response.data))
  } catch (error) {
    console.error('加载部门列表失败:', error)
  } finally {
    loading.value = false
  }
}

function handleAdd(parentRow?: DeptVO) {
  Object.assign(form, {
    id: 0,
    parentId: parentRow?.id || 0,
    name: '',
    sort: 0,
    leaderUserId: undefined,
    phone: '',
    email: '',
    status: 0
  })
  dialogVisible.value = true
}

async function handleEdit(row: DeptVO) {
  try {
    const response = await deptApi.getDeptById(row.id)
    Object.assign(form, response.data)
    dialogVisible.value = true
  } catch (error) {
    console.error('加载部门详情失败:', error)
    ElMessage.error('加载部门详情失败')
  }
}

async function handleDelete(row: DeptVO) {
  try {
    await ElMessageBox.confirm(`确认删除部门 ${row.deptName}?`, '提示', { type: 'warning' })
    await deptApi.deleteDept(row.id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除部门失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

async function handleSubmit() {
  try {
    await formRef.value?.validate()
    if (form.id) {
      await deptApi.updateDept(form)
      ElMessage.success('编辑成功')
    } else {
      await deptApi.createDept(form)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (error) {
    console.error('提交失败:', error)
    ElMessage.error('提交失败')
  }
}

// 组件挂载时加载数据
onMounted(() => {
  loadData()
})
</script>

<style scoped lang="scss">
.dept-manage {
  .search-card {
    margin-bottom: 20px;
  }
}
</style>