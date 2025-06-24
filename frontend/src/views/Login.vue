<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'
import { userApi } from '@/api'

const router = useRouter()

const form = ref({
  username: '',
  password: ''
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

const formRef = ref()

const handleLogin = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const res = await userApi.login(form.value)
        if (res.token) {
          localStorage.setItem('token', res.token)
          ElMessage.success('登录成功')
          await router.push('/dashboard')
        }
      } catch (error) {
        console.error('登录失败:', error)
        ElMessage.error(error.message || '登录失败')
      }
    }
  })
}
</script>

<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-box">
        <div class="login-header">
          <h2>测试平台</h2>
          <p class="subtitle">欢迎使用测试管理平台</p>
        </div>
        
        <el-form
          ref="formRef"
          :model="form"
          :rules="rules"
          class="login-form"
        >
          <el-form-item prop="username">
            <el-input
              v-model="form.username"
              placeholder="请输入用户名"
              :prefix-icon="User"
            />
          </el-form-item>
          
          <el-form-item prop="password">
            <el-input
              v-model="form.password"
              placeholder="请输入密码"
              type="password"
              show-password
              :prefix-icon="Lock"
            />
          </el-form-item>
          
          <el-form-item>
            <el-button type="primary" class="login-button" @click="handleLogin">
              登录
            </el-button>
          </el-form-item>
        </el-form>
        
        <div class="register-link">
          还没有账号？<el-link type="primary" @click="router.push('/register')">立即注册</el-link>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.login-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  
  .login-container {
    width: 100%;
    max-width: 440px;
    padding: 20px;
    
    .login-box {
      background: rgba(255, 255, 255, 0.9);
      backdrop-filter: blur(10px);
      border-radius: 16px;
      padding: 40px;
      box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
      
      .login-header {
        text-align: center;
        margin-bottom: 40px;
        
        h2 {
          margin: 0;
          font-size: 28px;
          color: #303133;
          font-weight: 600;
        }
        
        .subtitle {
          margin: 10px 0 0;
          color: #606266;
          font-size: 16px;
        }
      }
      
      .login-form {
        :deep(.el-input) {
          --el-input-height: 44px;
          
          .el-input__wrapper {
            background: rgba(255, 255, 255, 0.8);
            border-radius: 8px;
            box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
          }
        }
        
        .login-button {
          width: 100%;
          height: 44px;
          font-size: 16px;
          border-radius: 8px;
          background: linear-gradient(90deg, #667eea 0%, #764ba2 100%);
          border: none;
          margin-top: 20px;
          
          &:hover {
            background: linear-gradient(90deg, #5a6fd6 0%, #6a4494 100%);
          }
        }
      }
      
      .register-link {
        text-align: center;
        margin-top: 24px;
        color: #606266;
        
        .el-link {
          font-weight: 600;
          margin-left: 4px;
        }
      }
    }
  }
}
</style>