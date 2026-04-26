# Redis 安装和启动说明

## macOS 安装 Redis

### 方法1: 使用 Homebrew（推荐）
```bash
# 如果还没有安装 Homebrew，先安装它
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# 安装 Redis
brew install redis

# 启动 Redis（前台运行）
redis-server

# 或者后台运行
brew services start redis
```

### 方法2: 手动下载安装
```bash
# 下载 Redis
curl -O http://download.redis.io/redis-stable.tar.gz

# 解压
tar -xzvf redis-stable.tar.gz

# 编译
cd redis-stable
make

# 启动
src/redis-server
```

### 方法3: 使用 Docker
```bash
docker run -d --name redis -p 6379:6379 redis:latest
```

## 验证 Redis 是否运行
```bash
redis-cli ping
# 应该返回: PONG
```

## Redis 默认配置
- 主机: localhost
- 端口: 6379
- 密码: 无（如果需要密码，请在 application.yml 中配置）
