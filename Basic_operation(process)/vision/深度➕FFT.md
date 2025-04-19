# 风车调试
## windmill
1. 确认在launch文件中选择的颜色 **一定要** 
+ true -> 打红色
+ false -> 打蓝色
2. 确认有识别框 **R标处有绿点**
3. 看二值化 确认R标和箭头没有糊在一起
```
red_lower_hsv_s
red_lower_hsv_v
```
如果连在一起 下限给高 蓝色同理
## fft
1. 先调静止 **勾选is_static**
调光心 
+ r 想往上调大 想往下调小
+ y 想往左调大 想往右调小
2. 调移动的
```
rosrun plotjuggler plotjuggler 
```
拉/forecast/debug_result/orientation/x 
/forecast/debug_result/orientation/y
x要拟合上y
根据result_img的hz调
```
tracking_threshold: 180 # 60Hz
#tracking_threshold: 150 # 50Hz
#tracking_threshold: 120 # 40Hz
#tracking_threshold: 100 # 30Hz
```

先write一个周期 调参完写入txt 用visualize生图 记得改路径


