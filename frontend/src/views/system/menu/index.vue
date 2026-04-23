<template>
  <div class="menu-manage">
    <el-card class="search-card">
      <el-button type="primary" @click="handleAdd">新增菜单</el-button>
    </el-card>

    <el-card class="table-card">
      <el-table v-loading="loading" :data="tableData" row-key="id" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="菜单名称" width="180" />
        <el-table-column prop="icon" label="图标" width="100">
          <template #default="{ row }">
            <span v-if="row.icon">{{ row.icon }}</span>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column prop="menuType" label="类型" width="80">
          <template #default="{ row }">
            <el-tag v-if="row.menuType === 'M'" type="warning" size="small">目录</el-tag>
            <el-tag v-else-if="row.menuType === 'C'" size="small">菜单</el-tag>
            <el-tag v-else type="info" size="small">按钮</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="path" label="路由路径" min-width="150" />
        <el-table-column prop="component" label="组件" min-width="150" />
        <el-table-column prop="permission" label="权限标识" min-width="120" />
        <el-table-column prop="sort" label="排序" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 0 ? 'success' : 'danger'" size="small">
              {{ row.status === 0 ? '显示' : '隐藏' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="handleAdd(row)">新增子菜单</el-button>
            <el-button type="primary" link size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" link size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="650px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="上级菜单">
          <el-tree-select
            v-model="form.parentId"
            :data="menuTree"
            check-strictly
            :render-after-expand="false"
            placeholder="请选择上级菜单"
            :props="{ value: 'id', label: 'name', children: 'children' }"
          />
        </el-form-item>
        <el-form-item label="菜单类型" prop="menuType">
          <el-radio-group v-model="form.menuType">
            <el-radio value="M">目录</el-radio>
            <el-radio value="C">菜单</el-radio>
            <el-radio value="F">按钮</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="form.menuName" />
        </el-form-item>
        <el-form-item label="路由路径" prop="path" v-if="form.menuType !== 'F'">
          <el-input v-model="form.path" placeholder="/system/user" />
        </el-form-item>
        <el-form-item label="组件路径" prop="component" v-if="form.menuType === 'C'">
          <el-input v-model="form.component" placeholder="system/user/index" />
        </el-form-item>
        <el-form-item label="图标" v-if="form.menuType !== 'F'">
          <el-input v-model="form.icon" placeholder="Setting" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="0" />
        </el-form-item>
        <el-form-item label="是否显示" v-if="form.menuType !== 'F'">
          <el-radio-group v-model="form.visible">
            <el-radio :value="0">是</el-radio>
            <el-radio :value="1">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="权限标识" prop="perm" v-if="form.menuType === 'F'">
          <el-input v-model="form.perm" placeholder="system:user:add" />
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
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import { menuApi } from '@/api'
import type { MenuVO } from '@/types'

// 菜单树
const menuTree = ref<MenuVO[]>([])

// 表格数据
const loading = ref(false)
const tableData = ref<MenuVO[]>([])

// 弹窗
const dialogVisible = ref(false)
const formRef = ref<FormInstance>()
const form = reactive({
  id: 0,
  parentId: 0,
  menuType: 'M',
  name: '',
  path: '',
  component: '',
  icon: '',
  sort: 0,
  visible: 0,
  status: 0,
  perm: ''
})

const rules: FormRules = {
  menuName: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
  path: [
    { required: true, message: '请输入路由路径', trigger: 'blur' },
    { pattern: /^\/.*$/, message: '路由路径必须以/开头', trigger: 'blur' }
  ],
  perm: [
    { pattern: /^[a-zA-Z0-9:_]+$/, message: '权限标识格式不正确', trigger: 'blur' }
  ]
}

const dialogTitle = computed(() => (form.id ? '编辑菜单' : '新增菜单'))

// 加载菜单数据
async function loadData() {
  loading.value = true
  try {
    const response = await menuApi.getMenuTree()
    tableData.value = flattenMenuTree(response.data)
    menuTree.value = [
      {
        id: 0,
        label: '顶级菜单',
        children: response.data
      }
    ]
  } catch (error) {
    console.error('加载菜单列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 扁平化菜单树为表格数据
function flattenMenuTree(tree: MenuVO[], result: MenuVO[] = []): MenuVO[] {
  for (const item of tree) {
    result.push(item)
    if (item.children && item.children.length > 0) {
      flattenMenuTree(item.children, result)
    }
  }
  return result
}

function handleAdd(parent?: MenuVO) {
  Object.assign(form, {
    id: 0,
    parentId: parent?.id || 0,
    menuType: 'M',
    name: '',
    path: '',
    component: '',
    icon: '',
    sort: 0,
    visible: 0,
    status: 0,
    perm: ''
  })
  dialogVisible.value = true
}

async function handleEdit(row: MenuVO) {
  try {
    const response = await menuApi.getMenuById(row.id)
    Object.assign(form, response.data)
    dialogVisible.value = true
  } catch (error) {
    console.error('加载菜单详情失败:', error)
    ElMessage.error('加载菜单详情失败')
  }
}

async function handleDelete(row: MenuVO) {
  try {
    await ElMessageBox.confirm(`确认删除菜单 ${row.menuName}?`, '提示', { type: 'warning' })
    await menuApi.deleteMenu(row.id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除菜单失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

async function handleSubmit() {
  try {
    await formRef.value?.validate()
    const submitData = {
      ...form,
      status: form.visible // 后端用status表示状态
    }
    if (form.id) {
      await menuApi.updateMenu(submitData)
      ElMessage.success('编辑成功')
    } else {
      await menuApi.createMenu(submitData)
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
.menu-manage {
  .search-card {
    margin-bottom: 20px;
  }
}
</style>