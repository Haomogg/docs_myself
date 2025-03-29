**clion添加头文件**

```C++
include_directories(
 include                         //这个include一定要放开
  ${catkin_INCLUDE_DIRS} 	//这个也一定要保留(如果没了ros.h会找不到)
  "/home/haomo/catkin_ws/src/plumbing_head/include/**"   //这里添加路径
)
```

**ctrl+鼠标右键无法跳转到正确的函数**
需要先进入对应函数所在的文件中，让clion加载进去，才能ctrl跳转