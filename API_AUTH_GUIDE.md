# API 认证授权使用指南

## 系统架构

```
客户端 → Gateway (鉴权) → 微服务
         ↑
      Redis (Token存储)
```

## 认证流程

### 1. 登录获取 Token

**请求:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "123456"
  }'
```

**响应:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "username": "admin"
  }
}
```

### 2. 携带 Token 访问受保护的资源

**请求:**
```bash
curl http://localhost:8080/api/user/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

**响应:**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "name": "User_1",
    "age": 21
  }
}
```

### 3. 登出（使 Token 失效）

**请求:**
```bash
curl -X POST http://localhost:8080/api/auth/logout \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

**响应:**
```json
{
  "code": 200,
  "message": "success",
  "data": null
}
```

## 白名单接口

以下接口无需 Token 即可访问：
- `/api/auth/login` - 登录接口

## 错误响应

### 未提供 Token
```json
HTTP 401 Unauthorized
```

### Token 无效或已过期
```json
HTTP 401 Unauthorized
```

### Token 已被加入黑名单（已登出）
```json
HTTP 401 Unauthorized
```

## 服务间调用

当 gateway 验证 Token 通过后，会自动将用户信息添加到请求头中传递给下游服务：
- `X-User-Id`: 用户ID
- `X-Username`: 用户名

在微服务中可以通过以下方式获取当前用户信息：

```java
// 在 user-service 或 order-service 中
Long userId = UserContextUtil.getCurrentUserId();
String username = UserContextUtil.getCurrentUsername();
```

## Token 特性

- **有效期**: 24小时
- **存储**: Redis（用于快速验证和黑名单管理）
- **格式**: JWT (JSON Web Token)
- **签名算法**: HMAC-SHA256

## Redis 中的 Key 说明

- `token:{token}` - 存储有效的 Token，TTL 24小时
- `token:blacklist:{token}` - Token 黑名单（已登出），TTL 24小时

## 测试示例

```bash
# 1. 登录
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123456"}' | jq -r '.data.token')

echo "Token: $TOKEN"

# 2. 访问用户服务
curl http://localhost:8080/api/user/1 \
  -H "Authorization: Bearer $TOKEN"

# 3. 访问订单服务
curl http://localhost:8080/api/order/1 \
  -H "Authorization: Bearer $TOKEN"

# 4. 登出
curl -X POST http://localhost:8080/api/auth/logout \
  -H "Authorization: Bearer $TOKEN"

# 5. 再次访问（应该失败）
curl http://localhost:8080/api/user/1 \
  -H "Authorization: Bearer $TOKEN"
```
