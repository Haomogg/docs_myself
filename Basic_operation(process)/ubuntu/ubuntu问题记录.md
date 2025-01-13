1. 电脑突然用不了输入法：右上角点击搜狗输入法：重新启动

2. 绝对不要更新ubuntu相关软件(Software updater)，因为会更新内核，导致无法无线联网

3. ubuntu中解压时，哪种压缩包就用哪种解压命令(如zip用unzip -O GBK xxxx.zip ； rar用rar x xxx.rar),不要直接右键解压，会乱码甚至内存爆掉

4. 如果进不去图形界面：
启动->ubuntu advanced->resume(重进)
如果还不行：启动->ubuntu advanced->root(以root进入命令行)->此时可以利用命令行操控系统->查问题(如df -h查是不是磁盘空间不足等)

5. ubuntu20.04遇到那些清华源无法更新：将下述复制进/etc/apt/sources.list中(直接覆盖)
```shell
deb https://mirrors.ustc.edu.cn/ubuntu/ focal main restricted universe multiverse
deb https://mirrors.ustc.edu.cn/ubuntu/ focal-updates main restricted universe multiverse
deb https://mirrors.ustc.edu.cn/ubuntu/ focal-backports main restricted universe multiverse
deb https://mirrors.ustc.edu.cn/ubuntu/ focal-security main restricted universe multiverse
```
        
6. 不要使用sudo apt-get remove xxxx

7. 杀死进程
命令：htop

8. WeChat缩放
可手动修改 ~/.deepinwine/Mejituu-WeChat/scale.txt 文件设置为你需要的缩放值(2.5)
然后重启微信:右键WeChat：Launch using dedicated graphics card

9. 安装软件打不开软件中心(.deb文件)
安装gdebi(sudo apt-get install gdebi)
然后右键对应的.deb文件安装即可
或者sudo apt install ./xxxx.deb来

10. rviz模型白色，且robotmodel很多error——>重启下roscore

11. gazebo界面不完整
在.bashrc中加入下面代码
```shell
export QT_AUTO_SCREEN_SCALE_FACTOR=0 
export QT_SCREEN_SCALE_FACTORS=[1.0]
```



