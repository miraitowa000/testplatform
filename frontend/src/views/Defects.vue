<script setup>
import { ref } from 'vue'

const defects = ref([
  {
    id: 1,
    title: '登录页面验证码显示异常',
    description: '在Chrome浏览器下，验证码图片偶尔无法正常显示',
    severity: 'high',
    status: 'open',
    assignee: '张三',
    createTime: '2024-01-15',
    project: '电商系统测试项目'
  },
  {
    id: 2,
    title: '订单提交失败',
    description: '在高并发情况下，订单提交出现超时错误',
    severity: 'critical',
    status: 'in_progress',
    assignee: '李四',
    createTime: '2024-01-16',
    project: '电商系统测试项目'
  }
])

const dialogVisible = ref(false)
const defectForm = ref({
  title: '',
  description: '',
  severity: '',
  project: '',
  assignee: ''
})

const handleAdd = () => {
  dialogVisible.value = true
}

const handleSubmit = () => {
  // TODO: 提交表单逻辑
  dialogVisible.value = false
}

const severityOptions = [
  { label: '严重', value: 'critical' },
  { label: '高', value: 'high' },
  { label: '中', value: 'medium' },
  { label: '低', value: 'low' }
]

const statusOptions = [
  { label: '待处理', value: 'open' },
  { label: '处理中', value: 'in_progress' },
  { label: '已解决', value: 'resolved' },
  { label: '已关闭', value: 'closed' }
]

const getSeverityType = (severity) => {
  const types = {
    critical: 'danger',
    high: 'warning',
    medium: '',
    low: 'info'
  }
  return types[severity] || ''
}

const getStatusType = (status) => {
  const types = {
    open: 'danger',
    in_progress: 'warning',
    resolved: 'success',
    closed: 'info'
  }
  return types[status] || ''
}

const getStatusLabel = (status) => {
  const labels = {
    open: '待处理',
    in_progress: '处理中',
    resolved: '已解决',
    closed: '已关闭'
  }
  return labels[status] || status
}
</script>

<template>
  <div class="defects">
    <div class="page-header">
      <h2>缺陷管理</h2>
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>新建缺陷
      </el-button>
    </div>

    <el-table :data="defects" style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      
      <el-table-column prop="title" label="标题" min-width="200">
        <template #default="{ row }">
          <el-button type="primary" link>{{ row.title }}</el-button>
        </template>
      </el-table-column>
      
      <el-table-column prop="severity" label="严重程度" width="120">
        <template #default="{ row }">
          <el-tag :type="getSeverityType(row.severity)" effect="dark">
            {{ severityOptions.find(opt => opt.value === row.severity)?.label }}
          </el-tag>
        </template>
      </el-table-column>
      
      <el-table-column prop="status" label="状态" width="120">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">
            {{ getStatusLabel(row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      
      <el-table-column prop="assignee" label="处理人" width="120" />
      
      <el-table-column prop="project" label="所属项目" width="180" />
      
      <el-table-column prop="createTime" label="创建时间" width="180" />
      
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" link>
            <el-icon><Edit /></el-icon>编辑
          </el-button>
          <el-button type="success" link>
            <el-icon><View /></el-icon>详情
          </el-button>
          <el-button type="danger" link>
            <el-icon><Delete /></el-icon>删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      v-model="dialogVisible"
      title="新建缺陷"
      width="50%"
    >
      <el-form :model="defectForm" label-width="100px">
        <el-form-item label="缺陷标题">
          <el-input v-model="defectForm.title" placeholder="请输入缺陷标题" />
        </el-form-item>
        
        <el-form-item label="缺陷描述">
          <el-input
            v-model="defectForm.description"
            type="textarea"
            :rows="4"
            placeholder="请输入缺陷描述"
          />
        </el-form-item>
        
        <el-form-item label="严重程度">
          <el-select v-model="defectForm.severity" placeholder="请选择严重程度">
            <el-option
              v-for="option in severityOptions"
              :key="option.value"
              :label="option.label"
              :value="option.value"
            />
          </el-select>
        </el-form-item>
        
        <el-form-item label="所属项目">
          <el-select v-model="defectForm.project" placeholder="请选择项目">
            <el-option label="电商系统测试项目" value="电商系统测试项目" />
            <el-option label="支付系统升级测试" value="支付系统升级测试" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="指派给">
          <el-select v-model="defectForm.assignee" placeholder="请选择处理人">
            <el-option label="张三" value="张三" />
            <el-option label="李四" value="李四" />
          </el-select>
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
.defects {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>