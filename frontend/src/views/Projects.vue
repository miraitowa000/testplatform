<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { projectApi } from '@/api'

const projects = ref([
  {
    id: 1,
    name: '电商系统测试项目',
    description: '电商平台核心功能测试',
    status: '进行中',
    progress: 45,
    startDate: '2024-01-01',
    endDate: '2024-06-30'
  },
  {
    id: 2,
    name: '支付系统升级测试',
    description: '支付系统新功能测试',
    status: '规划中',
    progress: 0,
    startDate: '2024-02-01',
    endDate: '2024-04-30'
  }
])

const dialogVisible = ref(false)
const projectForm = ref({
  name: '',
  description: '',
  startTime: '',
  endTime: ''
})

const handleAdd = () => {
  dialogVisible.value = true
}

const handleSubmit = async () => {
  try {
    const user_id = JSON.parse(localStorage.getItem('user_id') || '{}')
    const formData = {
      name: projectForm.value.name,
      description: projectForm.value.description,
      startTime: projectForm.value.startTime,
      endTime: projectForm.value.endTime,
      ownerId: user_id
    }
    const res = await projectApi.createProject(formData)
    // 假设后端返回新项目数据在 res.data
    projects.value.push(res.data)
    dialogVisible.value = false
    ElMessage.success('项目创建成功')
    // 清空表单
    projectForm.value = { name: '', description: '', startDate: '', endDate: '' }
  } catch (error) {
    ElMessage.error(error.message || '项目创建失败')
  }
}
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
            <el-tag :type="project.status === '进行中' ? 'success' : 'info'">
              {{ project.status }}
            </el-tag>
          </div>
        </template>
        
        <div class="project-info">
          <p class="description">{{ project.description }}</p>
          
          <div class="progress-section">
            <span class="label">项目进度</span>
            <el-progress :percentage="project.progress" />
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
            <el-button type="primary" link>
              <el-icon><Edit /></el-icon>编辑
            </el-button>
            <el-button type="primary" link>
              <el-icon><View /></el-icon>查看详情
            </el-button>
            <el-button type="danger" link>
              <el-icon><Delete /></el-icon>删除
            </el-button>
          </div>
        </div>
      </el-card>
    </div>

    <el-dialog
      v-model="dialogVisible"
      title="新建项目"
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
            v-model="projectForm.startDate"
            type="date"
            placeholder="开始日期"
            style="margin-right: 10px"
          />
          <el-date-picker
            v-model="projectForm.endDate"
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
</style>