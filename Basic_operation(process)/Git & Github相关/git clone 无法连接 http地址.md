### git clone 无法连接 http地址

==可能会出现以下情况== 

- pre-commit检查卡住
- 远程clone http链接用不了
- ping http ping不通

原因：自己挂的代理端口与本地git使用的端口不一致

解决方法：

- 打开自己的代理软件，找到端口设置，发现代理挂在7897端口上


![alt text](<Clash verge端口号.png>)


- 回到home目录下，找到gitconfig（windows回到用户目录下）

```shell
vim ./.gitconfig 
```

> windows直接打开这个隐藏文件就行，如果没找到，就是你还没有设置本地github的用户名以及邮箱，参考以下命令

```shell
git config --global user.name "username"

git config --global user.email  useremail@qq.com
```
- 在gitconfig末尾添加对应端口

```shell
[http]
        proxy = 127.0.0.1:端口号
[https]
        proxy = 127.0.0.1:端口号
```

