import request from '../utils/request'

// 项目相关接口
const projectApi = {
  // 获取项目列表
  getProjects() {
    return request({
      url: '/projects',
      method: 'get'
    })
  },

  // 创建项目
  createProject(data) {
    return request({
      url: '/projects',
      method: 'post',
      data
    })
  },

  // 更新项目
  updateProject(id, data) {
    return request({
      url: `/projects/${id}`,
      method: 'put',
      data
    })
  },

  // 删除项目
  deleteProject(id) {
    return request({
      url: `/projects/${id}`,
      method: 'delete'
    })
  }
}

// 接口测试相关接口
const apiTestingApi = {
  // 获取接口集合列表
  getCollections() {
    return request({
      url: '/api-collections',
      method: 'get'
    })
  },

  // 创建接口集合
  createCollection(data) {
    return request({
      url: '/api-collections',
      method: 'post',
      data
    })
  },

  // 获取接口列表
  getApis(collectionId) {
    return request({
      url: `/api-collections/${collectionId}/apis`,
      method: 'get'
    })
  },

  // 执行接口测试
  runApiTest(data) {
    return request({
      url: '/api-test/run',
      method: 'post',
      data
    })
  }
}

// 缺陷管理相关接口
const defectApi = {
  // 获取缺陷列表
  getDefects(params) {
    return request({
      url: '/defects',
      method: 'get',
      params
    })
  },

  // 创建缺陷
  createDefect(data) {
    return request({
      url: '/defects',
      method: 'post',
      data
    })
  },

  // 更新缺陷
  updateDefect(id, data) {
    return request({
      url: `/defects/${id}`,
      method: 'put',
      data
    })
  },

  // 删除缺陷
  deleteDefect(id) {
    return request({
      url: `/defects/${id}`,
      method: 'delete'
    })
  },

  // 获取缺陷统计信息
  getDefectStats() {
    return request({
      url: '/defects/stats',
      method: 'get'
    })
  }
}

// 统计相关接口
const statsApi = {
  // 获取项目总览数据
  getDashboardStats() {
    return request({
      url: '/stats/dashboard',
      method: 'get'
    })
  },

  // 获取测试统计数据
  getTestingStats() {
    return request({
      url: '/stats/testing',
      method: 'get'
    })
  }
}

// 用户相关接口
const userApi = {
  // 用户注册
  register(data) {
    return request({
      url: '/user/register',
      method: 'post',
      data
    })
  },

  // 用户登录
  login(data) {
    return request({
      url: '/user/login',
      method: 'post',
      data
    })
  },
  logout() {
    return request({
      url: '/user/logout',
      method: 'get'
    })
  }
}

export { userApi, projectApi, apiTestingApi, defectApi, statsApi }
