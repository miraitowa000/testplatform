<script setup>
import { ref } from 'vue'

const projectStats = ref({
  total: 10,
  active: 6,
  completed: 4
})

const testStats = ref({
  totalApis: 150,
  passRate: 85,
  failedTests: 12,
  pendingTests: 8
})

const defectStats = ref({
  total: 45,
  critical: 5,
  high: 15,
  medium: 20,
  low: 5
})
</script>

<template>
  <div class="dashboard">
    <h2>项目总览</h2>
    
    <el-row :gutter="20" class="stats-row">
      <el-col :span="8">
        <el-card class="stats-card">
          <template #header>
            <div class="card-header">
              <span>项目统计</span>
              <el-button type="text">查看详情</el-button>
            </div>
          </template>
          <div class="stats-content">
            <div class="stat-item">
              <span class="label">总项目数</span>
              <span class="value">{{ projectStats.total }}</span>
            </div>
            <div class="stat-item">
              <span class="label">进行中</span>
              <span class="value">{{ projectStats.active }}</span>
            </div>
            <div class="stat-item">
              <span class="label">已完成</span>
              <span class="value">{{ projectStats.completed }}</span>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="8">
        <el-card class="stats-card">
          <template #header>
            <div class="card-header">
              <span>测试统计</span>
              <el-button type="text">查看详情</el-button>
            </div>
          </template>
          <div class="stats-content">
            <div class="stat-item">
              <span class="label">接口总数</span>
              <span class="value">{{ testStats.totalApis }}</span>
            </div>
            <div class="stat-item">
              <span class="label">通过率</span>
              <span class="value">{{ testStats.passRate }}%</span>
            </div>
            <div class="stat-item">
              <span class="label">失败用例</span>
              <span class="value error">{{ testStats.failedTests }}</span>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="8">
        <el-card class="stats-card">
          <template #header>
            <div class="card-header">
              <span>缺陷统计</span>
              <el-button type="text">查看详情</el-button>
            </div>
          </template>
          <div class="stats-content">
            <div class="stat-item">
              <span class="label">总缺陷数</span>
              <span class="value">{{ defectStats.total }}</span>
            </div>
            <div class="stat-item">
              <span class="label">严重缺陷</span>
              <span class="value error">{{ defectStats.critical }}</span>
            </div>
            <div class="stat-item">
              <span class="label">高优先级</span>
              <span class="value warning">{{ defectStats.high }}</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.dashboard {
  padding: 20px;
}

.stats-row {
  margin-top: 20px;
}

.stats-card {
  height: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.stats-content {
  padding: 10px 0;
}

.stat-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
}

.stat-item:last-child {
  margin-bottom: 0;
}

.label {
  color: #606266;
}

.value {
  font-weight: bold;
}

.error {
  color: #f56c6c;
}

.warning {
  color: #e6a23c;
}
</style>