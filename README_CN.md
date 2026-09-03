# 喜报 飞跃版

[[English]](https://github.com/Yuang114514/Xibao-Fly/blob/Fabric/README.md)[简体中文]

直接把 [Xibao++](https://github.com/GregTaoo/Xibao-Plus-Plus) 移植到了 Fabric 26.2

## 特性

### 继承自 喜报++
- 在你从服务器断链时显示一个“喜报”或“悲报”页面
- 3首喜庆音乐和1首悲伤音乐
- 撒花

### 扩展功能

- 使用 `DisconnectedScreen` 的重写而非Mixin来更加友好地兼容其它模组
- 重构的撒花运动轨迹

## 安装

### 从Actions下载

每次推送的构建可以在 [Actions 页面](https://github.com/Yuang114514/Xibao-Fly/actions) 下载。

### 从源码构建

看不懂就别想了，你不配
1. Clone this repo or download the zip archive to your PC.
2. At the root folder, run:
```bash
./gradlew jar
```
> Windows users should use `./gradlew.bat` instead.
3. When the build task finished, find the artifact in `/build/libs`
4. Copy the jar file in the folder to your modded Minecraft instance's `mods` folder.
5. Start Minecraft and enjoy it!

### 从 Modrinth 下载

_即将到来_

~~这似冯modrinth审核能不能快点~~