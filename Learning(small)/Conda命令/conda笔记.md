 
### 命令(命令行输入)

    //配置
    conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free/ //镜像网站配置
    conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/main/ //镜像网站配置
    conda config --set show_channel_urls yes //安装时可以看到每个网址
    //然后去.condarc里面把channels里的defalut删掉

    //命令
    conda activate base(环境) //进入或切换环境

    conda deactivate //退出当前环境

    conda create --name    test    python=2.7    requests //创建虚拟环境
                          环境名字      依赖1        依赖2

    conda env list //查看所有虚拟环境
    
    conda install xxx //在当前环境安装xxx

    conda search --name xxx//罗列某个库的各种版本

### 文件夹

envs:存放各种环境

