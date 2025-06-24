# 测试平台（Test Platform）

本项目为前后端分离的测试管理平台，支持项目总览、项目管理、接口测试、缺陷管理等功能。

## 技术栈

- 前端：Vue 3、Vite、Element Plus、Vue Router、Axios、Sass
- 后端：Spring Boot 2.7.x、MyBatis-Plus、Spring Security、MySQL、Redis、JWT、Druid、Lombok

## 目录结构

```
├── frontend/   # 前端项目（Vue 3）
├── backend/    # 后端项目（Spring Boot）
```

## 环境准备

- Node.js >= 16.x
- JDK >= 1.8
- MySQL >= 5.7
- Redis >= 5.x

## 前端启动

```bash
cd frontend
npm install
npm run dev
```

- 默认端口：5173
- 代理配置：`/api` 代理到 `http://127.0.0.1:8088`（可在 `vite.config.js` 修改）

## 后端启动

```bash
cd backend
# 修改 src/main/resources/application.yml 数据库和Redis配置
mvn clean package
java -jar target/test-platform-backend-1.0.0.jar
# 或
mvn spring-boot:run
```
- 默认端口：8080（可在 application.yml 修改）
- 数据库：请先创建 `test_platform` 数据库，并配置好账号密码

## 常用命令

### 前端
- `npm run dev`  本地开发
- `npm run build`  打包生产

### 后端
- `mvn spring-boot:run`  本地开发
- `mvn clean package`  打包

## 主要功能

- 用户注册/登录/退出
- 项目总览（数据统计、图表）
- 项目管理（增删改查、成员管理）
- 接口测试（用例管理、调试、报告）
- 缺陷管理（缺陷记录、状态流转、统计）

## 其他说明

- Swagger接口文档：访问 `http://localhost:8080/swagger-ui.html`
- 前端打包后静态文件可部署到任意静态服务器
- 后端支持JWT鉴权，token存储于Redis

## 常见问题

1. **端口冲突**：请确保5173（前端）、8080（后端）未被占用。
2. **数据库连接失败**：请检查MySQL配置、账号密码、端口。
3. **Redis连接失败**：请检查Redis服务是否启动。
4. **接口跨域问题**：已在后端配置CORS，前端开发环境通过Vite代理解决。

---

如有问题欢迎提issue或联系开发者。 