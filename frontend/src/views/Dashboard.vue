<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { useRouter } from 'vue-router'
import * as echarts from 'echarts'
import { statsApi } from '@/api'

const router = useRouter()
const chartRef = ref(null)
let chart = null
let refreshTimer = null

const projectStats = ref({
  total: 0,
  active: 0,
  completed: 0
})

const testStats = ref({
  totalApis: 0,
  passRate: 0,
  failedTests: 0
})

const defectStats = ref({
  total: 0,
  critical: 0,
  high: 0
})

const fetchDashboardData = async () => {
  try {
    const { data } = await statsApi.getDashboardStats()
    projectStats.value = {
      total: data.projectStats.total,
      active: data.projectStats.ongoing,
      completed: data.projectStats.completed
    }
    testStats.value = {
      totalApis: data.testCaseStats.total,
      passRate: parseFloat(data.testCaseStats.passRate),
      failedTests: data.testCaseStats.failCount
    }
    defectStats.value = {
      total: data.bugStats.total,
      critical: data.bugStats.critical,
      high: data.bugStats.high
    }
    
    if (chart) {
      updateChart(defectStats.value)
    }
  } catch (error) {
    console.error('获取数据失败:', error)
  }
}

const initChart = () => {
  if (chartRef.value) {
    chart = echarts.init(chartRef.value)
    const option = {
      tooltip: {
        trigger: 'item',
        formatter: '{b}: {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        left: 'left',
        data: ['严重缺陷', '高优先级', '普通缺陷']
      },
      series: [
        {
          name: '缺陷分布',
          type: 'pie',
          radius: '70%',
          center: ['60%', '50%'],
          data: [
            { value: defectStats.value.critical, name: '严重缺陷', itemStyle: { color: '#f56c6c' } },
            { value: defectStats.value.high, name: '高优先级', itemStyle: { color: '#e6a23c' } },
            { value: defectStats.value.total - defectStats.value.critical - defectStats.value.high, name: '普通缺陷', itemStyle: { color: '#409eff' } }
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    }
    chart.setOption(option)
  }
}

const updateChart = (stats) => {
  const option = {
    series: [
      {
        data: [
          { value: stats.critical, name: '严重缺陷', itemStyle: { color: '#f56c6c' } },
          { value: stats.high, name: '高优先级', itemStyle: { color: '#e6a23c' } },
          { value: stats.total - stats.critical - stats.high, name: '普通缺陷', itemStyle: { color: '#409eff' } }
        ]
      }
    ]
  }
  chart.setOption(option)
}

const handleDetail = (type) => {
  const routes = {
    project: '/projects',
    test: '/api-testing',
    defect: '/defects'
  }
  router.push(routes[type])
}

onMounted(() => {
  fetchDashboardData()
  initChart()
  // 每5分钟刷新一次数据
  refreshTimer = setInterval(fetchDashboardData, 5 * 60 * 1000)
})

onBeforeUnmount(() => {
  if (refreshTimer) {
    clearInterval(refreshTimer)
  }
  if (chart) {
    chart.dispose()
    chart = null
  }
})
</script>

<template>
  <div class="dashboard">
    <h2>项目总览</h2>
    
    <el-row :gutter="20" class="stats-row">
      <el-col :span="8">
        <el-card class="stats-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span>项目统计</span>
              <el-button type="primary" link @click="handleDetail('project')">查看详情</el-button>
            </div>
          </template>
          <div class="stats-content">
            <div class="stat-item">
              <span class="label">总项目数</span>
              <span class="value highlight">{{ projectStats.total }}</span>
            </div>
            <div class="stat-item">
              <span class="label">未开始</span>
              <span class="value primary">{{ projectStats.active }}</span>
            </div>
            <div class="stat-item">
              <span class="label">进行中</span>
              <span class="value primary">{{ projectStats.active }}</span>
            </div>
            <div class="stat-item">
              <span class="label">已完成</span>
              <span class="value success">{{ projectStats.completed }}</span>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="8">
        <el-card class="stats-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span>测试统计</span>
              <el-button type="primary" link @click="handleDetail('test')">查看详情</el-button>
            </div>
          </template>
          <div class="stats-content">
            <div class="stat-item">
              <span class="label">接口总数</span>
              <span class="value highlight">{{ testStats.totalApis }}</span>
            </div>
            <div class="stat-item">
              <span class="label">通过率</span>
              <span class="value success">{{ testStats.passRate }}%</span>
            </div>
            <div class="stat-item">
              <span class="label">失败用例</span>
              <span class="value error">{{ testStats.failedTests }}</span>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="8">
        <el-card class="stats-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span>缺陷统计</span>
              <el-button type="primary" link @click="handleDetail('defect')">查看详情</el-button>
            </div>
          </template>
          <div class="stats-content">
            <div class="stat-item">
              <span class="label">总缺陷数</span>
              <span class="value highlight">{{ defectStats.total }}</span>
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

    <el-row :gutter="20" class="chart-row">
      <el-col :span="12">
        <el-card class="chart-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span>缺陷分布</span>
            </div>
          </template>
          <div ref="chartRef" class="chart-container"></div>
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
  transition: all 0.3s ease;
}

.stats-card:hover {
  transform: translateY(-5px);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.stats-content {
  padding: 15px 10px;
}

.stat-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  padding: 12px;
  border-radius: 8px;
  background-color: #f8f9fa;
  transition: all 0.3s ease;
}

.stat-item:hover {
  background-color: #eef2f7;
}

.stat-item:last-child {
  margin-bottom: 0;
}

.label {
  color: #606266;
  font-weight: 500;
}

.value {
  font-size: 24px;
  font-weight: bold;
}

.highlight {
  color: #409EFF;
}

.primary {
  color: #409EFF;
}

.success {
  color: #67C23A;
}

.error {
  color: #f56c6c;
}

.warning {
  color: #e6a23c;
}

.chart-row {
  margin-top: 30px;
}

.chart-card {
  transition: all 0.3s ease;
}

.chart-card:hover {
  transform: translateY(-5px);
}

.chart-container {
  height: 400px;
  width: 100%;
  padding: 10px;
}
</style>