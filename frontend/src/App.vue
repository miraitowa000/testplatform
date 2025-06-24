<script setup>
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { userApi } from '@/api'

const router = useRouter()
const route = useRoute()
const isCollapse = ref(false)

const isAuthPage = computed(() => {
  return ['/login', '/register'].includes(route.path)
})

const handleSelect = (key) => {
  router.push(key)
}

const handleDropdown = async (command) => {
  if (command === 'logout') {
    try {
      await userApi.logout()
      localStorage.removeItem('token')
      ElMessage.success('已退出登录')
      router.push('/login')
    } catch (e) {
      ElMessage.error('退出登录失败')
    }
  }
}
</script>

<template>
  <template v-if="isAuthPage">
    <router-view />
  </template>
  
  <el-container v-else class="layout-container">
    <el-aside :width="isCollapse ? '64px' : '200px'" class="aside">
      <el-menu
        :default-active="$route.path"
        class="el-menu-vertical"
        :collapse="isCollapse"
        @select="handleSelect"
      >
        <el-menu-item index="/dashboard">
          <el-icon><Monitor /></el-icon>
          <template #title>项目总览</template>
        </el-menu-item>
        <el-menu-item index="/projects">
          <el-icon><Folder /></el-icon>
          <template #title>项目管理</template>
        </el-menu-item>
        <el-menu-item index="/api-testing">
          <el-icon><Connection /></el-icon>
          <template #title>接口测试</template>
        </el-menu-item>
        <el-menu-item index="/defects">
          <el-icon><Warning /></el-icon>
          <template #title>缺陷管理</template>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <el-button
          type="text"
          :icon="isCollapse ? 'Expand' : 'Fold'"
          @click="isCollapse = !isCollapse"
        />
        <div class="header-right">
          <el-dropdown @command="handleDropdown">
            <el-avatar :size="32" />
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人信息</el-dropdown-item>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
@import '@/styles/theme.scss';

.layout-container {
  height: 100vh;
  background: $gradient-primary;
}

.aside {
  background: $background-white;
  backdrop-filter: $backdrop-blur;
  border-right: none;
  transition: width 0.3s;
}

.el-menu-vertical {
  border-right: none;
  background: transparent;
}

.header {
  background: $background-white;
  backdrop-filter: $backdrop-blur;
  border-bottom: 1px solid rgba(220, 223, 230, 0.3);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.main {
  background: $background-white;
  backdrop-filter: $backdrop-blur;
  margin: 16px;
  border-radius: $border-radius-card;
  box-shadow: $box-shadow-card;
}
</style>
