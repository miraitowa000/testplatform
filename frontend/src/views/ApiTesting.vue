<script setup>
import { ref } from 'vue'

const activeTab = ref('collections')

const collections = ref([
  {
    id: 1,
    name: '用户服务接口',
    apis: [
      { id: 1, name: '用户登录', method: 'POST', path: '/api/auth/login', status: 'passed' },
      { id: 2, name: '用户注册', method: 'POST', path: '/api/auth/register', status: 'failed' },
      { id: 3, name: '获取用户信息', method: 'GET', path: '/api/users/{id}', status: 'passed' }
    ]
  },
  {
    id: 2,
    name: '订单服务接口',
    apis: [
      { id: 4, name: '创建订单', method: 'POST', path: '/api/orders', status: 'passed' },
      { id: 5, name: '订单列表', method: 'GET', path: '/api/orders', status: 'pending' }
    ]
  }
])

const currentApi = ref({
  method: 'GET',
  url: '',
  headers: [{ key: '', value: '' }],
  params: [{ key: '', value: '' }],
  body: ''
})

const addHeader = () => {
  currentApi.value.headers.push({ key: '', value: '' })
}

const addParam = () => {
  currentApi.value.params.push({ key: '', value: '' })
}

const removeHeader = (index) => {
  currentApi.value.headers.splice(index, 1)
}

const removeParam = (index) => {
  currentApi.value.params.splice(index, 1)
}

const sendRequest = () => {
  // TODO: 实现发送请求逻辑
}
</script>

<template>
  <div class="api-testing">
    <el-container>
      <el-aside width="300px" class="collections-aside">
        <div class="aside-header">
          <h3>接口集合</h3>
          <el-button type="primary" size="small">
            <el-icon><Plus /></el-icon>新建集合
          </el-button>
        </div>
        
        <el-menu>
          <el-sub-menu v-for="collection in collections" :key="collection.id" :index="String(collection.id)">
            <template #title>
              <el-icon><Folder /></el-icon>
              <span>{{ collection.name }}</span>
            </template>
            
            <el-menu-item 
              v-for="api in collection.apis" 
              :key="api.id"
              :index="String(api.id)"
            >
              <div class="api-item">
                <span class="method" :class="api.method.toLowerCase()">{{ api.method }}</span>
                <span class="api-name">{{ api.name }}</span>
                <el-tag 
                  size="small" 
                  :type="api.status === 'passed' ? 'success' : api.status === 'failed' ? 'danger' : 'info'"
                >
                  {{ api.status }}
                </el-tag>
              </div>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      
      <el-main class="api-main">
        <div class="request-panel">
          <div class="url-line">
            <el-select v-model="currentApi.method" class="method-select">
              <el-option label="GET" value="GET" />
              <el-option label="POST" value="POST" />
              <el-option label="PUT" value="PUT" />
              <el-option label="DELETE" value="DELETE" />
            </el-select>
            <el-input v-model="currentApi.url" placeholder="请输入请求URL" />
            <el-button type="primary" @click="sendRequest">发送请求</el-button>
          </div>
          
          <el-tabs v-model="activeTab" class="request-tabs">
            <el-tab-pane label="Params" name="params">
              <div class="params-list">
                <div v-for="(param, index) in currentApi.params" :key="index" class="param-item">
                  <el-input v-model="param.key" placeholder="参数名" />
                  <el-input v-model="param.value" placeholder="参数值" />
                  <el-button type="danger" circle @click="removeParam(index)">
                    <el-icon><Delete /></el-icon>
                  </el-button>
                </div>
                <el-button type="primary" link @click="addParam">
                  <el-icon><Plus /></el-icon>添加参数
                </el-button>
              </div>
            </el-tab-pane>
            
            <el-tab-pane label="Headers" name="headers">
              <div class="headers-list">
                <div v-for="(header, index) in currentApi.headers" :key="index" class="header-item">
                  <el-input v-model="header.key" placeholder="Header名" />
                  <el-input v-model="header.value" placeholder="Header值" />
                  <el-button type="danger" circle @click="removeHeader(index)">
                    <el-icon><Delete /></el-icon>
                  </el-button>
                </div>
                <el-button type="primary" link @click="addHeader">
                  <el-icon><Plus /></el-icon>添加Header
                </el-button>
              </div>
            </el-tab-pane>
            
            <el-tab-pane label="Body" name="body">
              <el-input
                v-model="currentApi.body"
                type="textarea"
                :rows="10"
                placeholder="请输入请求体"
              />
            </el-tab-pane>
          </el-tabs>
        </div>
        
        <div class="response-panel">
          <div class="response-header">
            <h3>响应结果</h3>
            <div class="response-status">
              <el-tag type="success">200 OK</el-tag>
              <span>100 ms</span>
            </div>
          </div>
          <el-input
            type="textarea"
            :rows="15"
            placeholder="响应内容"
            readonly
          />
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<style scoped>
.api-testing {
  height: 100%;
}

.collections-aside {
  border-right: 1px solid #dcdfe6;
  height: 100%;
}

.aside-header {
  padding: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #dcdfe6;
}

.aside-header h3 {
  margin: 0;
}

.api-item {
  display: flex;
  align-items: center;
  gap: 10px;
}

.method {
  font-size: 12px;
  font-weight: bold;
  padding: 2px 6px;
  border-radius: 4px;
}

.get { color: #67c23a; background-color: #f0f9eb; }
.post { color: #409eff; background-color: #ecf5ff; }
.put { color: #e6a23c; background-color: #fdf6ec; }
.delete { color: #f56c6c; background-color: #fef0f0; }

.api-name {
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.api-main {
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 20px;
}

.url-line {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.method-select {
  width: 100px;
}

.request-tabs {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 15px;
}

.params-list,
.headers-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.param-item,
.header-item {
  display: flex;
  gap: 10px;
}

.response-panel {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 15px;
}

.response-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.response-header h3 {
  margin: 0;
}

.response-status {
  display: flex;
  align-items: center;
  gap: 10px;
}
</style>