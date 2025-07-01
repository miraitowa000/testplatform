import axios from 'axios'
import { ElMessage } from 'element-plus'

// 创建axios实例
const request = axios.create({
  baseURL: '/api', // API的基础URL
  timeout: 15000 // 请求超时时间
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    // 在请求发送之前做一些处理
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  error => {
    // 处理请求错误
    console.error('Request error:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    const res = response.data
    // 根据后端接口返回格式处理响应
    if (res.code === 200 || res.code === 0) { // 成功状态码为200或0
      return res
    } else {
      const errorMsg = res.msg || res.message || '请求失败'
      return Promise.reject(new Error(errorMsg))
    }
  },
  error => {
    console.error('Response error:', error)
    const message = error.response?.data?.message || error.message || '请求失败'
    ElMessage.error(message)
    return Promise.reject(error)
  }
)

export default request