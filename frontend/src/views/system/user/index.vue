<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import { userApi, deptApi, roleApi } from '@/api'
import type { UserVO, RoleVO } from '@/types'

// 搜索表单
const searchForm = reactive({
  keyword: '',
  status: ''
})

// 表格数据
const loading = ref(false)
const tableData = ref<UserVO[]>([])

// 分页
const pagination = reactive({
  current: 1,
  size: 10,
  total: 0
})

// 部门树
const deptTree = ref([])

// 角色列表
const roleList = ref<RoleVO[]>([])

// 弹窗
const dialogVisible = ref(false)
const formRef = ref<FormInstance>()
const form = reactive({
  id: 0,
  username: '',
  nickname: '',
  password: '',
  email: '',
  phone: '',
  deptId: undefined,
  roleIds: [] as number[],
  status: 0
})

const rules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 50, message: '用户名长度必须在2-50之间', trigger: 'blur' }
  ],
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    { max: 50, message: '昵称长度不能超过50', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 100, message: '密码长度必须在6-100之间', trigger: 'blur' }
  ],
  email: [
    { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
  ]
}

const dialogTitle = computed(() => (form.id ? '编辑用户' : '新增用户'))

// 加载数据
async function loadData() {
  loading.value = true
  try {
    const response = await userApi.getUserPage({
      pageNum: pagination.current,
      pageSize: pagination.size,
      username: searchForm.keyword,
      nickname: searchForm.keyword,
      status: searchForm.status ? Number(searchForm.status) : undefined
    })
    tableData.value = response.data.records
    pagination.total = response.data.total
  } catch (error) {
    console.error('加载用户列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 加载部门树
async function loadDeptTree() {
  try {
    const response = await deptApi.getDeptTree()
    deptTree.value = response.data.map((dept: any) => ({
      value: dept.id,
      label: dept.deptName,
      children: dept.children?.map((child: any) => ({
        value: child.id,
        label: child.deptName
      }))
    }))
  } catch (error) {
    console.error('加载部门树失败:', error)
  }
}

// 加载角色列表
async function loadRoleList() {
  try {
    const response = await roleApi.getRolePage({ pageSize: 100 })
    roleList.value = response.data.records.map((role: any) => ({
      id: role.id,
      roleName: role.roleName
    }))
  } catch (error) {
    console.error('加载角色列表失败:', error)
  }
}

// 搜索
function handleSearch() {
  pagination.current = 1
  loadData()
}

function handleReset() {
  searchForm.keyword = ''
  searchForm.status = ''
  handleSearch()
}

function handleAdd() {
  Object.assign(form, {
    id: 0,
    username: '',
    nickname: '',
    password: '',
    email: '',
    phone: '',
    deptId: undefined,
    roleIds: [],
    status: 0
  })
  dialogVisible.value = true
}

async function handleEdit(row: UserVO) {
  try {
    const [userResponse, roleIdsResponse] = await Promise.all([
      userApi.getUserById(row.id),
      userApi.getUserRoleIds(row.id)
    ])
    Object.assign(form, {
      ...userResponse.data,
      roleIds: roleIdsResponse.data || []
    })
    dialogVisible.value = true
  } catch (error) {
    console.error('加载用户详情失败:', error)
    ElMessage.error('加载用户详情失败')
  }
}

async function handleDelete(row: UserVO) {
  try {
    await ElMessageBox.confirm(`确认删除用户 ${row.nickname}?`, '提示', { type: 'warning' })
    await userApi.deleteUser(row.id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除用户失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

async function handleSubmit() {
  try {
    await formRef.value?.validate()
    const submitData = {
      ...form,
      phone: form.phone // 后端字段是phone
    }
    if (form.id) {
      await userApi.updateUser(submitData)
      if (form.roleIds?.length > 0) {
        await userApi.assignRoles(form.id, form.roleIds)
      }
      ElMessage.success('编辑成功')
    } else {
      const userId = await userApi.createUser(submitData)
      if (form.roleIds?.length > 0 && userId.data) {
        await userApi.assignRoles(userId.data, form.roleIds)
      }
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (error) {
    console.error('提交失败:', error)
    ElMessage.error('提交失败')
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
  loadDeptTree()
  loadRoleList()
})
</script>

<style scoped lang="scss">
.user-manage {
  .search-card {
    margin-bottom: 30px;
    background: var(--bg-card);
    border: 1px solid var(--border-color);
    border-radius: var(--border-radius-lg);
    box-shadow: var(--shadow-md);
    backdrop-filter: blur(10px);

    :deep(.el-card__body) {
      padding: 30px;
    }

    :deep(.el-form) {
      .el-form-item__label {
        color: var(--text-secondary);
        font-weight: 500;
        margin-bottom: 8px;
      }

      .el-input {
        .el-input__wrapper {
          background: rgba(255, 255, 255, 0.05);
          border: 1px solid var(--border-color);
          border-radius: var(--border-radius);
          transition: all var(--transition-normal);

          &:hover {
            border-color: var(--primary-color);
            box-shadow: 0 0 10px rgba(102, 126, 234, 0.2);
          }

          &.is-focus {
            border-color: var(--accent-color);
            box-shadow: 0 0 20px rgba(0, 255, 136, 0.3);
          }
        }
      }

      .el-select {
        .el-select__wrapper {
          background: rgba(255, 255, 255, 0.05);
          border: 1px solid var(--border-color);
          border-radius: var(--border-radius);
          transition: all var(--transition-normal);

          &:hover {
            border-color: var(--primary-color);
          }
        }
      }
    }

    .el-button {
      height: 44px;
      border-radius: var(--border-radius);
      font-weight: 500;
      transition: all var(--transition-normal);

      &.el-button--primary {
        background: var(--primary-gradient);
        border: none;
        box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);

        &:hover {
          transform: translateY(-1px);
          box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
        }
      }

      &.el-button--default {
        background: var(--bg-card);
        border: 1px solid var(--border-color);
        color: var(--text-secondary);

        &:hover {
          border-color: var(--primary-color);
          color: var(--primary-color);
          background: var(--bg-card-hover);
        }
      }
    }
  }

  .table-card {
    background: var(--bg-card);
    border: 1px solid var(--border-color);
    border-radius: var(--border-radius-lg);
    box-shadow: var(--shadow-md);
    backdrop-filter: blur(10px);

    :deep(.el-card__body) {
      padding: 30px;
    }

    :deep(.el-table) {
      background: transparent;
      color: var(--text-primary);

      .el-table__header {
        background: var(--bg-tertiary);

        th {
          background: transparent;
          color: var(--text-secondary);
          font-weight: 600;
          border-bottom: 1px solid var(--border-color);
        }
      }

      .el-table__row {
        background: transparent;
        border-bottom: 1px solid var(--border-hover);

        &:hover {
          background: var(--bg-card-hover);
        }

        td {
          color: var(--text-primary);
          border-bottom: 1px solid var(--border-color);
        }
      }

      .el-tag {
        border-radius: var(--border-radius-sm);
        font-weight: 500;
      }
    }

    :deep(.el-pagination) {
      .el-pagination__total,
      .el-pagination__jump,
      .el-pagination__sizes {
        color: var(--text-secondary);
      }

      .el-pagination__sizes .el-input {
        .el-input__wrapper {
          background: var(--bg-card);
          border: 1px solid var(--border-color);
          color: var(--text-primary);
        }
      }

      .el-pager {
        .number {
          background: var(--bg-card);
          border: 1px solid var(--border-color);
          color: var(--text-secondary);
          transition: all var(--transition-normal);

          &:hover,
          &.is-active {
            background: var(--primary-gradient);
            border-color: var(--primary-color);
            color: white;
            box-shadow: 0 0 10px rgba(102, 126, 234, 0.3);
          }
        }
      }

      .btn-prev,
      .btn-next {
        background: var(--bg-card);
        border: 1px solid var(--border-color);
        color: var(--text-secondary);

        &:hover {
          border-color: var(--primary-color);
          color: var(--primary-color);
        }
      }
    }

    .pagination {
      margin-top: 30px;
      display: flex;
      justify-content: flex-end;
    }
  }

  // 弹窗样式
  :deep(.el-dialog) {
    background: var(--bg-secondary);
    border: 1px solid var(--border-color);
    border-radius: var(--border-radius-lg);
    box-shadow: var(--shadow-lg), 0 0 40px rgba(0, 0, 0, 0.5);

    .el-dialog__header {
      background: var(--bg-tertiary);
      border-bottom: 1px solid var(--border-color);
      margin: 0;
      padding: 25px 30px;

      .el-dialog__title {
        color: var(--text-primary);
        font-size: 20px;
        font-weight: 600;
        font-family: 'JetBrains Mono', monospace;
      }
    }

    .el-dialog__body {
      padding: 30px;
      background: var(--bg-secondary);
    }

    .el-dialog__footer {
      background: var(--bg-tertiary);
      border-top: 1px solid var(--border-color);
      padding: 20px 30px;

      .el-button {
        border-radius: var(--border-radius);
        font-weight: 500;
        transition: all var(--transition-normal);

        &.el-button--primary {
          background: var(--primary-gradient);
          border: none;
          box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);

          &:hover {
            transform: translateY(-1px);
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
          }
        }

        &.el-button--default {
          background: var(--bg-card);
          border: 1px solid var(--border-color);
          color: var(--text-secondary);

          &:hover {
            border-color: var(--primary-color);
            color: var(--primary-color);
          }
        }
      }
    }
  }

  // 表单样式
  :deep(.el-form) {
    .el-form-item__label {
      color: var(--text-secondary);
      font-weight: 500;
      margin-bottom: 10px;
    }

    .el-input {
      .el-input__wrapper {
        background: rgba(255, 255, 255, 0.05);
        border: 1px solid var(--border-color);
        border-radius: var(--border-radius);
        transition: all var(--transition-normal);

        &:hover {
          border-color: var(--primary-color);
        }

        &.is-focus {
          border-color: var(--accent-color);
          box-shadow: 0 0 20px rgba(0, 255, 136, 0.3);
        }
      }

      .el-input__inner {
        color: var(--text-primary);

        &::placeholder {
          color: var(--text-tertiary);
        }
      }
    }

    .el-select {
      .el-select__wrapper {
        background: rgba(255, 255, 255, 0.05);
        border: 1px solid var(--border-color);
        border-radius: var(--border-radius);
        transition: all var(--transition-normal);

        &:hover {
          border-color: var(--primary-color);
        }
      }

      .el-select__placeholder {
        color: var(--text-tertiary);
      }
    }

    .el-radio-group {
      .el-radio {
        .el-radio__input {
          &.is-checked {
            .el-radio__inner {
              background: var(--accent-color);
              border-color: var(--accent-color);
            }
          }
        }

        .el-radio__label {
          color: var(--text-primary);
        }
      }
    }
  }

  // 树选择器样式
  :deep(.el-tree-select) {
    .el-select__wrapper {
      background: rgba(255, 255, 255, 0.05);
      border: 1px solid var(--border-color);
      border-radius: var(--border-radius);
    }
  }

  // 树组件样式
  :deep(.el-tree) {
    background: transparent;
    color: var(--text-primary);

    .el-tree-node {
      &:hover {
        background: var(--bg-card-hover);
      }

      &.is-current {
        background: var(--primary-color);
        color: white;
      }
    }
  }
}
</style>