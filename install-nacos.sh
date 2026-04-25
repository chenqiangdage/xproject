#!/bin/bash

# Nacos 安装和启动脚本
# Nacos版本
NACOS_VERSION="2.3.2"
NACOS_DIR="$HOME/nacos"

echo "========================================="
echo "Nacos $NACOS_VERSION 安装脚本"
echo "========================================="

# 检查是否已安装
if [ -d "$NACOS_DIR" ]; then
    echo "检测到Nacos已安装在: $NACOS_DIR"
    read -p "是否重新安装? (y/n): " REINSTALL
    if [ "$REINSTALL" != "y" ]; then
        echo "使用现有安装"
        cd "$NACOS_DIR"
        sh bin/startup.sh -m standalone
        exit 0
    fi
    rm -rf "$NACOS_DIR"
fi

# 创建目录
mkdir -p "$NACOS_DIR"
cd "$NACOS_DIR"

echo ""
echo "正在下载 Nacos $NACOS_VERSION..."
echo "如果下载速度慢，请手动下载后解压到: $NACOS_DIR"
echo ""
echo "下载地址:"
echo "1. GitHub: https://github.com/alibaba/nacos/releases/download/$NACOS_VERSION/nacos-server-$NACOS_VERSION.tar.gz"
echo "2. Gitee镜像: https://gitee.com/mirrors/Nacos/releases/download/$NACOS_VERSION/nacos-server-$NACOS_VERSION.tar.gz"
echo ""

# 尝试下载（可能需要较长时间）
curl -L "https://github.com/alibaba/nacos/releases/download/$NACOS_VERSION/nacos-server-$NACOS_VERSION.tar.gz" -o nacos.tar.gz

# 检查下载是否成功
if [ ! -f "nacos.tar.gz" ] || [ ! -s "nacos.tar.gz" ]; then
    echo ""
    echo "❌ 下载失败或文件为空"
    echo ""
    echo "请手动执行以下步骤:"
    echo "1. 访问: https://github.com/alibaba/nacos/releases/download/$NACOS_VERSION/nacos-server-$NACOS_VERSION.tar.gz"
    echo "2. 下载文件到: $NACOS_DIR/nacos.tar.gz"
    echo "3. 然后运行: cd $NACOS_DIR && tar -xzf nacos.tar.gz"
    echo "4. 启动: sh bin/startup.sh -m standalone"
    exit 1
fi

# 解压
echo "正在解压..."
tar -xzf nacos.tar.gz

# 清理压缩包
rm -f nacos.tar.gz

echo ""
echo "✅ Nacos 安装完成!"
echo "安装目录: $NACOS_DIR"
echo ""
echo "启动命令:"
echo "  cd $NACOS_DIR"
echo "  sh bin/startup.sh -m standalone"
echo ""
echo "访问地址:"
echo "  http://localhost:8848/nacos"
echo "  默认用户名: nacos"
echo "  默认密码: nacos"
echo ""

# 询问是否立即启动
read -p "是否立即启动 Nacos? (y/n): " START_NOW
if [ "$START_NOW" = "y" ]; then
    echo "正在启动 Nacos (单机模式)..."
    sh bin/startup.sh -m standalone
    echo ""
    echo "Nacos 启动中..."
    echo "请等待几秒后访问: http://localhost:8848/nacos"
fi
