<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { userApi } from '../api'
import { ElMessage } from 'element-plus'
import { Message, Lock } from '@element-plus/icons-vue'

const router = useRouter()

const form = ref({
  email: '',
  password: '',
  confirmPassword: ''
})

const rules = {
  email: [
    { required: true, message: '请输入邮箱地址', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== form.value.password) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

const formRef = ref()

const handleRegister = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await userApi.register(form.value)
        ElMessage.success('注册成功')
        router.push('/login')
      } catch (error) {
        console.error('注册失败:', error)
      }
    }
  })
}
</script>

<template>
  <div class="register-page">
    <div class="register-container">
      <div class="register-box">
        <div class="register-header">
          <h2>注册账号</h2>
          <p class="subtitle">欢迎加入我们的测试平台</p>
        </div>
        
        <el-form
          ref="formRef"
          :model="form"
          :rules="rules"
          class="register-form"
        >
          <el-form-item prop="email">
            <el-input
              v-model="form.email"
              placeholder="请输入邮箱地址"
              type="email"
              :prefix-icon="Message"
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
          
          <el-form-item prop="confirmPassword">
            <el-input
              v-model="form.confirmPassword"
              placeholder="请再次输入密码"
              type="password"
              show-password
              :prefix-icon="Lock"
            />
          </el-form-item>
          
          <el-form-item>
            <el-button type="primary" class="register-button" @click="handleRegister">
              立即注册
            </el-button>
          </el-form-item>
        </el-form>
        
        <div class="login-link">
          已有账号？<el-link type="primary" @click="router.push('/login')">立即登录</el-link>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.register-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  
  .register-container {
    width: 100%;
    max-width: 440px;
    padding: 20px;
    
    .register-box {
      background: rgba(255, 255, 255, 0.9);
      backdrop-filter: blur(10px);
      border-radius: 16px;
      padding: 40px;
      box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
      
      .register-header {
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
      
      .register-form {
        :deep(.el-input) {
          --el-input-height: 44px;
          
          .el-input__wrapper {
            background: rgba(255, 255, 255, 0.8);
            border-radius: 8px;
            box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
          }
        }
        
        .register-button {
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
      
      .login-link {
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