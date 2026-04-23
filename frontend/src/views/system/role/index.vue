<template>
  <div class="role-manage">
    <!-- 搜索区域 -->
    <el-card class="search-card">
      <el-form :inline="true">
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增角色</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 表格区域 -->
    <el-card class="table-card">
      <el-table v-loading="loading" :data="tableData" row-key="id" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="roleName" label="角色名称" width="150" />
        <el-table-column prop="roleKey" label="权限标识" width="150" />
        <el-table-column prop="dataScope" label="数据范围" width="120">
          <template #default="{ row }">
            <el-tag v-if="row.dataScope === 1" type="success" size="small">全部数据</el-tag>
            <el-tag v-else-if="row.dataScope === 2" type="warning" size="small">自定义数据</el-tag>
            <el-tag v-else-if="row.dataScope === 3" type="info" size="small">本部门数据</el-tag>
            <el-tag v-else-if="row.dataScope === 4" type="warning" size="small">本部门及以下</el-tag>
            <el-tag v-else type="danger" size="small">仅本人数据</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" width="80" />
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
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="success" link size="small" @click="handlePermission(row)">权限</el-button>
            <el-button type="danger" link size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          v-model:current-page="pagination.current"
          v-model:page-size="pagination.size"
          :page-sizes="[10, 20, 50, 100]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 新增/编辑弹窗 -->
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑角色' : '新增角色'" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName" />
        </el-form-item>
        <el-form-item label="权限标识" prop="roleKey">
          <el-input v-model="form.roleKey" :disabled="!!form.id" />
        </el-form-item>
        <el-form-item label="数据范围">
          <el-select v-model="form.dataScope">
            <el-option :value="1" label="全部数据" />
            <el-option :value="2" label="自定义数据" />
            <el-option :value="3" label="本部门数据" />
            <el-option :value="4" label="本部门及以下" />
            <el-option :value="5" label="仅本人数据" />
          </el-select>
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="0" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :value="0">正常</el-radio>
            <el-radio :value="1">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <!-- 权限分配弹窗 -->
    <el-dialog v-model="permissionDialogVisible" title="分配权限" width="600px">
      <el-tree
        ref="menuTreeRef"
        :data="menuTree"
        show-checkbox
        node-key="id"
        :props="{ label: 'menuName', children: 'children' }"
        v-model:checked-keys="form.menuIds"
      />
      <template #footer>
        <el-button @click="permissionDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handlePermissionSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import { roleApi, menuApi } from '@/api'
import type { RoleVO, MenuVO } from '@/types'

const loading = ref(false)
const tableData = ref<RoleVO[]>([])

// 分页
const pagination = reactive({
  current: 1,
  size: 10,
  total: 0
})

// 弹窗
const dialogVisible = ref(false)
const permissionDialogVisible = ref(false)
const formRef = ref<FormInstance>()
const form = reactive({
  id: 0,
  roleName: '',
  roleKey: '',
  sort: 0,
  dataScope: 1,
  status: 0,
  remark: '',
  menuIds: [] as number[]
})

// 菜单树
const menuTree = ref<MenuVO[]>([])
// const menuTreeRef = ref()

// 表单验证规则
const rules: FormRules = {
  roleName: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
  roleKey: [
    { required: true, message: '请输入权限标识', trigger: 'blur' },
    { pattern: /^[a-zA-Z0-9_]+$/, message: '权限标识只能包含字母、数字、下划线', trigger: 'blur' }
  ]
}

// 加载角色列表
async function loadData() {
  loading.value = true
  try {
    const response = await roleApi.getRolePage({
      pageNum: pagination.current,
      pageSize: pagination.size
    })
    tableData.value = response.data.records
    pagination.total = response.data.total
  } catch (error) {
    console.error('加载角色列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 加载菜单树
async function loadMenuTree() {
  try {
    const response = await menuApi.getMenuTree()
    menuTree.value = response.data
  } catch (error) {
    console.error('加载菜单树失败:', error)
  }
}

function handleAdd() {
  Object.assign(form, {
    id: 0,
    roleName: '',
    roleKey: '',
    sort: 0,
    dataScope: 1,
    status: 0,
    remark: '',
    menuIds: []
  })
  dialogVisible.value = true
}

async function handleEdit(row: RoleVO) {
  try {
    const [roleResponse, menuIdsResponse] = await Promise.all([
      roleApi.getRoleById(row.id),
      roleApi.getRoleMenuIds(row.id)
    ])
    Object.assign(form, {
      ...roleResponse.data,
      menuIds: menuIdsResponse.data || []
    })
    dialogVisible.value = true
  } catch (error) {
    console.error('加载角色详情失败:', error)
    ElMessage.error('加载角色详情失败')
  }
}

async function handlePermission(row: RoleVO) {
  try {
    const [roleResponse, menuIdsResponse] = await Promise.all([
      roleApi.getRoleById(row.id),
      roleApi.getRoleMenuIds(row.id)
    ])
    Object.assign(form, {
      ...roleResponse.data,
      menuIds: menuIdsResponse.data || []
    })
    permissionDialogVisible.value = true
  } catch (error) {
    console.error('加载角色权限失败:', error)
    ElMessage.error('加载角色权限失败')
  }
}

async function handleDelete(row: RoleVO) {
  try {
    await ElMessageBox.confirm(`确认删除角色 ${row.name}?`, '提示', { type: 'warning' })
    await roleApi.deleteRole(row.id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除角色失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

async function handleSubmit() {
  try {
    await formRef.value?.validate()
    if (form.id) {
      await roleApi.updateRole(form)
      ElMessage.success('编辑成功')
    } else {
      await roleApi.createRole(form)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (error) {
    console.error('提交失败:', error)
    ElMessage.error('提交失败')
  }
}

async function handlePermissionSubmit() {
  try {
    if (form.id && form.menuIds) {
      await roleApi.assignMenus(form.id, form.menuIds)
      ElMessage.success('权限分配成功')
      permissionDialogVisible.value = false
      loadData()
    }
  } catch (error) {
    console.error('权限分配失败:', error)
    ElMessage.error('权限分配失败')
  }
}

function handleSizeChange(pageSize: number) {
  pagination.size = pageSize
  pagination.current = 1
  loadData()
}

function handleCurrentChange(current: number) {
  pagination.current = current
  loadData()
}

// 组件挂载时加载数据
onMounted(() => {
  loadData()
  loadMenuTree()
})
</script>

<style scoped lang="scss">
.role-manage {
  .search-card {
    margin-bottom: 20px;
  }

  .table-card {
    .pagination {
      margin-top: 20px;
      display: flex;
      justify-content: flex-end;
    }
  }
}
</style>