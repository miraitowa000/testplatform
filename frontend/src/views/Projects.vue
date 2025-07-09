<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { projectApi } from '@/api'

const projects = ref([])

const dialogVisible = ref(false)
const projectForm = ref({
  name: '',
  description: '',
  startTime: '',
  endTime: ''
})

const isEdit = ref(false)
const editProjectId = ref(null)

const handleAdd = () => {
  isEdit.value = false
  projectForm.value = { name: '', description: '', startTime: '', endTime: '' }
  dialogVisible.value = true
}

const handleEdit = (project) => {
  isEdit.value = true
  editProjectId.value = project.id
  projectForm.value = {
    name: project.name,
    description: project.description,
    startTime: project.startTime,
    endTime: project.endTime
  }
  dialogVisible.value = true
}

function formatDate(date) {
  if (!date) return ''
  const d = new Date(date)
  const year = d.getFullYear()
  const month = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

const handleSubmit = async () => {
  try {
    const user_id = JSON.parse(localStorage.getItem('user_id') || '{}')
    const formData = {
      name: projectForm.value.name,
      description: projectForm.value.description,
      startTime: formatDate(projectForm.value.startTime),
      endTime: formatDate(projectForm.value.endTime),
      ownerId: user_id
    }
    let res
    if (isEdit.value && editProjectId.value) {
      res = await projectApi.updateProject(editProjectId.value, formData)
      // 更新本地 projects 列表
      const idx = projects.value.findIndex(p => p.id === editProjectId.value)
      if (idx !== -1) projects.value[idx] = { ...projects.value[idx], ...res.data }
      ElMessage.success('项目编辑成功')
    } else {
      res = await projectApi.createProject(formData)
      projects.value.push(res.data)
      ElMessage.success('项目创建成功')
    }
    dialogVisible.value = false
    // 清空表单
    projectForm.value = { name: '', description: '', startTime: '', endTime: '' }
  } catch (error) {
    ElMessage.error(error.message || (isEdit.value ? '项目编辑失败' : '项目创建失败'))
  }
}

const handleDelete = (project) => {
  ElMessageBox.confirm(
    `确定要删除项目「${project.name}」吗？`,
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      await projectApi.deleteProject(project.id)
      // 从本地 projects 列表移除
      projects.value = projects.value.filter(p => p.id !== project.id)
      ElMessage.success('删除成功')
    } catch (error) {
      ElMessage.error(error.message || '删除失败')
    }
  }).catch(() => {})
}

const statusText = status => {
  switch (status) {
    case 0: return '未开始'
    case 1: return '进行中'
    case 2: return '已完成'
    default: return '未知'
  }
}
const statusTagType = status => {
  switch (status) {
    case 0: return 'info'
    case 1: return 'success'
    case 2: return 'default'
    default: return 'info'
  }
}

const getProgress = (project) => {
  const start = new Date(project.startTime).getTime()
  const end = new Date(project.endTime).getTime()
  const now = Date.now()
  if (!start || !end || end <= start) return 0
  if (now <= start) return 0
  if (now >= end) return 100
  return Math.round(((now - start) / (end - start)) * 100)
}

onMounted(async () => {
  try {
    const res = await projectApi.getProjects()
    // 假设后端返回项目数组在 res.data
    projects.value = res.data || []
  } catch (error) {
    ElMessage.error('获取项目列表失败')
  }
})
</script>

<template>
  <div class="projects">
    <div class="page-header">
      <h2>项目管理</h2>
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>新建项目
      </el-button>
    </div>

    <div class="project-list">
      <el-card v-for="project in projects" :key="project.id" class="project-card">
        <template #header>
          <div class="card-header">
            <h3>{{ project.name }}</h3>
            <el-tag :type="statusTagType(project.status)">
              {{ statusText(project.status) }}
            </el-tag>
          </div>
        </template>
        
        <div class="project-info">
          <p class="description">{{ project.description }}</p>
          
          <div class="progress-section">
            <span class="label">项目进度</span>
            <el-progress :percentage="getProgress(project)" />
          </div>
          
          <div class="date-section">
            <div class="date-item">
              <span class="label">开始日期</span>
              <span>{{ project.startTime }}</span>
            </div>
            <div class="date-item">
              <span class="label">结束日期</span>
              <span>{{ project.endTime }}</span>
            </div>
          </div>
          
          <div class="actions">
            <el-button type="primary" link @click="handleEdit(project)">
              <el-icon><Edit /></el-icon>编辑
            </el-button>
            <el-button type="primary" link>
              <el-icon><View /></el-icon>查看详情
            </el-button>
            <el-button type="danger" link @click="handleDelete(project)">
              <el-icon><Delete /></el-icon>删除
            </el-button>
          </div>
        </div>
      </el-card>
    </div>

    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑项目' : '新建项目'"
      width="50%"
    >
      <el-form :model="projectForm" label-width="100px">
        <el-form-item label="项目名称">
          <el-input v-model="projectForm.name" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="项目描述">
          <el-input
            v-model="projectForm.description"
            type="textarea"
            placeholder="请输入项目描述"
          />
        </el-form-item>
        <el-form-item label="起止时间">
          <el-date-picker
            v-model="projectForm.startTime"
            type="date"
            placeholder="开始日期"
            style="margin-right: 10px"
          />
          <el-date-picker
            v-model="projectForm.endTime"
            type="date"
            placeholder="结束日期"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.projects {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.project-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.project-card {
  height: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-header h3 {
  margin: 0;
  font-size: 16px;
}

.project-info {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.description {
  color: #606266;
  margin: 0;
}

.progress-section,
.date-section {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.date-section {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.date-item {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.label {
  color: #909399;
  font-size: 14px;
}

.actions {
  display: flex;
  justify-content: flex-end;
  gap: 15px;
  margin-top: 10px;
}

.avatar-img {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
  margin-right: 8px;
  border: 1px solid #eee;
}
</style>