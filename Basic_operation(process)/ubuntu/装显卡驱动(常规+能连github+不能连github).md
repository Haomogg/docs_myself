### （2）intel显卡
```
sudo -E su
```

#### **[1] 安装**

```bash
sudo apt-get install intel-media-va-driver-non-free libva-drm2 libva-x11-2 vainfo
```

#### **[2] 检查环境变量**

确保设置了正确的环境变量，特别是`LD_LIBRARY_PATH`：  export LD_LIBRARY_PATH=/usr/lib/x86_64-linux-gnu:$LD_LIBRARY_PATH


```bash
export LD_LIBRARY_PATH=/usr/lib/x86_64-linux-gnu:$LD_LIBRARY_PATH
echo 'export LD_LIBRARY_PATH=/usr/lib/x86_64-linux-gnu:$LD_LIBRARY_PATH' >> ~/.bashrc
source ~/.bashrc
```

#### **[3] 添加用户到组**

- **检查用户组**

用户需要属于`video`组才能访问显卡设备节点。你可以使用以下命令检查当前用户的组：

```
groups $USER
```

dynamicx@standard3:~$ groups dynamicx
dynamicx : dynamicx adm cdrom sudo dip video plugdev render lxd


- **添加用户到`video`组**

如果用户不属于`video`组，可以使用以下命令将用户添加到`video`组：

```
sudo usermod -aG video $USER
```

- **检查设备节点权限**

接下来，我们检查显卡设备节点的权限，确保这些节点的组权限设置正确：

```
ls -l /dev/dri
```

确保这些设备节点的组是`video`，并且组权限是`rw`（读写）。

```
total 0
drwxr-xr-x 2 root root 80 Jun 16 09:37 by-path
crw-rw----+ 1 root video 226, 0 Jun 16 09:37 card0
crw-rw----+ 1 root render 226, 128 Jun 16 09:37 renderD128
```
dynamicx@standard3:~$ ls -l /dev/dri
total 0
drwxr-xr-x 2 root root         80 Mar 26 11:54 by-path
crw-rw---- 1 root video  226,   0 Mar 26 11:54 card0
crw-rw---- 1 root render 226, 128 Mar 26 11:54 renderD128

- **添加用户到`render`组**（因为有一个不是video组的,是render组的）（可能是导致clinfo找不到设备的原因）**（自启脚本的root用户也要添加一次！）**

```
sudo usermod -aG render $USER
```

- **修改设备节点权限（如果必要）**：

```
sudo chmod 660 /dev/dri/*
sudo chgrp video /dev/dri/*
```
Adding dynamicx to the video group...
Adding dynamicx to the render group...

Installation completed successfully.

Next steps:
Add OpenCL users to the video and render group: 'sudo usermod -a -G video,render USERNAME'
   e.g. if the user running OpenCL host applications is foo, run: sudo usermod -a -G video,render foo
   Current user has been already added to the video and render group

If you use 8th Generation Intel® Core™ processor, add:
   i915.alpha_support=1
   to the 4.14 kernel command line, in order to enable OpenCL functionality for this platform.

```
dynamicx@standard6:~$ sudo dmesg | grep -i error
[    1.454118] RAS: Correctable Errors collector initialized.
[    3.004129] i915 0000:00:02.0: Direct firmware load for i915/adlp_dmc.bin failed with error -2
[    3.004149] i915 0000:00:02.0: Direct firmware load for i915/adlp_dmc_ver2_16.bin failed with error -2
[    3.005669] i915 0000:00:02.0: [drm] *ERROR* GT0: GuC firmware i915/adlp_guc_70.bin: fetch failed -2
[    3.006305] i915 0000:00:02.0: [drm] *ERROR* GT0: GuC initialization failed -2
[    3.006305] i915 0000:00:02.0: [drm] *ERROR* GT0: Enabling uc failed (-5)
[    3.006306] i915 0000:00:02.0: [drm] *ERROR* GT0: Failed to initialize GPU, declaring it wedged!
[    5.746209] Serial bus multi instantiate pseudo device driver INT3515:00: error -6: IRQ index 1 not found
[    5.746213] Serial bus multi instantiate pseudo device driver INT3515:00: error -6: Error requesting irq at index 1
[    5.756105] EDAC igen6 MC1: HANDLING IBECC MEMORY ERROR
[    5.756109] EDAC igen6 MC0: HANDLING IBECC MEMORY ERROR
[    5.889547] iwlwifi 0000:00:14.3: Direct firmware load for iwlwifi-so-a0-gf-a0-89.ucode failed with error -2
[    5.891229] iwlwifi 0000:00:14.3: Direct firmware load for iwlwifi-so-a0-gf-a0-88.ucode failed with error -2
[    5.891261] iwlwifi 0000:00:14.3: Direct firmware load for iwlwifi-so-a0-gf-a0-87.ucode failed with error -2
[    5.891299] iwlwifi 0000:00:14.3: Direct firmware load for iwlwifi-so-a0-gf-a0-86.ucode failed with error -2
[    5.897072] iwlwifi 0000:00:14.3: Direct firmware load for iwlwifi-so-a0-gf-a0-85.ucode failed with error -2
[    5.902501] iwlwifi 0000:00:14.3: Direct firmware load for iwlwifi-so-a0-gf-a0-84.ucode failed with error -2
[    5.902533] iwlwifi 0000:00:14.3: Direct firmware load for iwlwifi-so-a0-gf-a0-83.ucode failed with error -2
[    5.902555] iwlwifi 0000:00:14.3: Direct firmware load for iwlwifi-so-a0-gf-a0-82.ucode failed with error -2
[    5.902578] iwlwifi 0000:00:14.3: Direct firmware load for iwlwifi-so-a0-gf-a0-81.ucode failed with error -2
[    5.902599] iwlwifi 0000:00:14.3: Direct firmware load for iwlwifi-so-a0-gf-a0-80.ucode failed with error -2
[    5.902620] iwlwifi 0000:00:14.3: Direct firmware load for iwlwifi-so-a0-gf-a0-79.ucode failed with error -2
[    5.902641] iwlwifi 0000:00:14.3: Direct firmware load for iwlwifi-so-a0-gf-a0-78.ucode failed with error -2
[    5.902662] iwlwifi 0000:00:14.3: Direct firmware load for iwlwifi-so-a0-gf-a0-77.ucode failed with error -2
[   16.272149] platform INT3515:02: deferred probe pending: Serial bus multi instantiate pseudo device driver: Error creating i2c-client, idx 0
[   16.272152] platform INT3515:01: deferred probe pending: Serial bus multi instantiate pseudo device driver: Error creating i2c-client, idx 0
```
少什么装什么
```
# 下载 DMC 固件
wget https://git.kernel.org/pub/scm/linux/kernel/git/firmware/linux-firmware.git/plain/i915/adlp_dmc.bin -O /lib/firmware/i915/adlp_dmc.bin
wget https://git.kernel.org/pub/scm/linux/kernel/git/firmware/linux-firmware.git/plain/i915/adlp_dmc_ver2_16.bin -O /lib/firmware/i915/adlp_dmc_ver2_16.bin

# 下载 GuC 固件
wget https://git.kernel.org/pub/scm/linux/kernel/git/firmware/linux-firmware.git/plain/i915/adlp_guc_70.bin -O /lib/firmware/i915/adlp_guc_70.bin
```
没权限就加sudo

确保下载的固件文件权限正确
```
sudo chown root:root /lib/firmware/i915/adlp_dmc.bin
sudo chown root:root /lib/firmware/i915/adlp_dmc_ver2_16.bin
sudo chown root:root /lib/firmware/i915/adlp_guc_70.bin

sudo chmod 644 /lib/firmware/i915/adlp_dmc.bin
sudo chmod 644 /lib/firmware/i915/adlp_dmc_ver2_16.bin
sudo chmod 644 /lib/firmware/i915/adlp_guc_70.bin
```
更新 initramfs
如前所述，更新 initramfs 以确保新固件被识别：
```
sudo update-initramfs -u
```
重启系统
完成上述步骤后，重启系统以确保所有更改生效：
```
sudo reboot
```
检查系统日志
重启后，再次检查 dmesg 输出，验证固件是否已被成功加载：
```
sudo dmesg | grep i915
```
刷刷固件
```
sudo apt update
sudo apt install --reinstall linux-firmware
```
见证奇迹的时刻
```
clinfo

```

### 看死哪里了
strace clinfo

要手动安装 Intel® Graphics Compute Runtime for oneAPI Level Zero 和 OpenCL™ 驱动程序，可以按照以下步骤进行：

### 步骤 1：检查系统要求
确保您的系统符合以下要求：
- 6代至11代的 Intel® Core™ 处理器。
- BIOS中未禁用集成显卡。

### 步骤 2：准备环境
1. **更新系统**：
   ```bash
   sudo apt-get update
   sudo apt-get upgrade
   ```

2. **安装必要的工具**：
   ```bash
   sudo apt-get install -y curl gpg-agent
   ```

### 步骤 3：添加 Intel 的软件源
1. **添加 GPG 密钥**：
   ```bash
   curl https://repositories.intel.com/graphics/intel-graphics.key | gpg --dearmor --output /usr/share/keyrings/intel-graphics.gpg
   ```

2. **添加软件源**：
   ```bash
   echo 'deb [arch=amd64 signed-by=/usr/share/keyrings/intel-graphics.gpg] https://repositories.intel.com/graphics/ubuntu focal-legacy main' | sudo tee /etc/apt/sources.list.d/intel.gpu.focal.list
   ```

### 步骤 4：安装依赖项
根据是否需要 NUMA 支持选择安装：
```bash
# 如果需要 NUMA 支持
sudo apt-get install -y --no-install-recommends libnuma1 ocl-icd-libopencl1

# 如果不需要 NUMA 支持
sudo apt-get install -y --no-install-recommends ocl-icd-libopencl1
```

### 步骤 5：下载和安装驱动
1. **下载驱动包**：
   ```bash
   curl -L -O https://github.com/intel/compute-runtime/releases/download/21.48.21782/intel-gmmlib_21.3.3_amd64.deb
   curl -L -O https://github.com/intel/intel-graphics-compiler/releases/download/igc-1.0.9441/intel-igc-core_1.0.9441_amd64.deb
   curl -L -O https://github.com/intel/intel-graphics-compiler/releases/download/igc-1.0.9441/intel-igc-opencl_1.0.9441_amd64.deb
   curl -L -O https://github.com/intel/compute-runtime/releases/download/21.48.21782/intel-opencl-icd_21.48.21782_amd64.deb
   curl -L -O https://github.com/intel/compute-runtime/releases/download/21.48.21782/intel-level-zero-gpu_1.2.21782_amd64.deb
   ```

2. **安装驱动包**：
   ```bash
   sudo dpkg -i intel-*.deb
   ```

### 步骤 6：验证安装
1. **检查驱动安装情况**：
   ```bash
   dpkg-query -l | grep intel
   ```

2. **添加用户到视频和渲染组**：
   ```bash
   sudo usermod -a -G video,render $USER
   ```

### 步骤 7：重启
重启系统以使更改生效。

### 注意事项
- 确保在安装过程中能够访问互联网以下载包。
- 如果在安装过程中遇到任何错误，请检查系统日志以获取更多信息。

完成以上步骤后，您应该能够成功安装 Intel® Graphics Compute Runtime。

sudo apt-get update
sudo apt-get install intel-opencl-icd
clinfo
sudo apt-get install intel-opencl-icd 
sudo apt-get check
sudo apt-get update
sudo dmesg | grep -i error
三个.bin 跟之前一样
 sudo dmesg | grep -i error
sudo update-initramfs -u
sudo dmesg | grep i915
strace clinfo
sudo apt install intel-opencl-icd
传包
sudo dpkg -i intel-*.deb
dpkg-query -l | grep intel
sudo usermod -a -G video,render $USER
sudo dmesg | grep -i error
sudo apt-get update
sudo apt-get install -y intel-microcode
sudo apt-get install -y intel-media-va-driver
sudo apt-get install -y firmware-iwlwifi
sudo reboot
三个.bin 跟之前一样
sudo update-initramfs -u
sudo reboot
 sudo dmesg | grep i915
sudo apt update
sudo apt install --reinstall linux-firmware
sudo dmesg | grep -i error
sudo apt-get update
sudo apt-get install -y intel-opencl-icd intel-microcode firmware-misc-nonfree firmware-iwlwifi
sudo dmesg | grep i915
sudo apt-get purge intel-opencl-icd intel-level-zero-gpu level-zero

dynamicx@leggedBalance:~$ sudo apt-get purge intel-opencl-icd intel-level-zero-gpu level-zero
Reading package lists... Done
Building dependency tree       
Reading state information... Done
Package 'level-zero' is not installed, so not removed
The following packages were automatically installed and are no longer required:
  libclang-cpp10 libigc1 libigdfcl1 libllvmspirvlib10 libopencl-clang10 linux-headers-6.6.13-x64v4-xanmod1 ros-noetic-octomap
Use 'sudo apt autoremove' to remove them.
The following packages will be REMOVED:
  intel-level-zero-gpu* intel-opencl-icd*
0 upgraded, 0 newly installed, 2 to remove and 31 not upgraded.
After this operation, 14.0 MB disk space will be freed.
Do you want to continue? [Y/n] y
(Reading database ... 262816 files and directories currently installed.)
Removing intel-level-zero-gpu (1.2.21782) ...
Removing intel-opencl-icd (21.48.21782) ...
Processing triggers for libc-bin (2.31-0ubuntu9.17) ...
(Reading database ... 262800 files and directories currently installed.)
Purging configuration files for intel-opencl-icd (21.48.21782) ...
dynamicx@leggedBalance:~$ sudo wget -qO - https://repositories.intel.com/graphics/intel-graphics.key | sudo gpg --dearmor --output /usr/share/keyrings/intel-graphics.gpg
File '/usr/share/keyrings/intel-graphics.gpg' exists. Overwrite? (y/N) y
dynamicx@leggedBalance:~$ echo "deb [arch=amd64 signed-by=/usr/share/keyrings/intel-graphics.gpg] https://repositories.intel.com/graphics/ubuntu focal main" | sudo tee /etc/apt/sources.list.d/intel-graphics.list
deb [arch=amd64 signed-by=/usr/share/keyrings/intel-graphics.gpg] https://repositories.intel.com/graphics/ubuntu focal main
dynamicx@leggedBalance:~$ sudo apt update
Hit:1 https://mirrors.ustc.edu.cn/ubuntu focal InRelease
Hit:2 https://mirrors.ustc.edu.cn/ubuntu focal-updates InRelease
Hit:3 https://mirrors.ustc.edu.cn/ubuntu focal-backports InRelease
Hit:4 https://mirrors.ustc.edu.cn/ubuntu focal-security InRelease
Ign:5 http://mirrors.tuna.tsinghua.edu.cn/ros/ubuntu focal InRelease
Get:6 http://mirrors.tuna.tsinghua.edu.cn/ros2/ubuntu focal InRelease [4,685 B]
Hit:7 http://mirrors.tuna.tsinghua.edu.cn/ros/ubuntu focal Release
Ign:9 http://robotpkg.openrobots.org/packages/debian/pub focal InRelease      
Hit:10 http://robotpkg.openrobots.org/packages/debian/pub focal Release
Get:12 https://repositories.intel.com/graphics/ubuntu focal InRelease [3,195 B]
Hit:13 https://repositories.intel.com/graphics/ubuntu focal-legacy InRelease
Get:14 https://repositories.intel.com/graphics/ubuntu focal/main amd64 Packages [198 kB]
Fetched 206 kB in 5s (43.3 kB/s)    
Reading package lists... Done
Building dependency tree       
Reading state information... Done
31 packages can be upgraded. Run 'apt list --upgradable' to see them.
dynamicx@leggedBalance:~$ sudo apt install intel-opencl-icd intel-level-zero-gpu level-zero
Reading package lists... Done
Building dependency tree       
Reading state information... Done
The following packages were automatically installed and are no longer required:
  libclang-cpp10 libllvmspirvlib10 libopencl-clang10
  linux-headers-6.6.13-x64v4-xanmod1 ros-noetic-octomap
Use 'sudo apt autoremove' to remove them.
The following additional packages will be installed:
  intel-igc-cm libigc1 libigdfcl1 libigdgmm12 libz3-4
The following NEW packages will be installed:
  intel-igc-cm intel-level-zero-gpu intel-opencl-icd level-zero libigdgmm12
  libz3-4
The following packages will be upgraded:
  libigc1 libigdfcl1
2 upgraded, 6 newly installed, 0 to remove and 29 not upgraded.
Need to get 65.8 MB of archives.
After this operation, 233 MB of additional disk space will be used.
Do you want to continue? [Y/n] y
Get:1 https://mirrors.ustc.edu.cn/ubuntu focal/universe amd64 libz3-4 amd64 4.8.7-4build1 [6,792 kB]
Get:2 https://repositories.intel.com/graphics/ubuntu focal-legacy/main amd64 libigc1 amd64 1.0.12504.6+i537~20.04 [20.4 MB]
Get:3 https://repositories.intel.com/graphics/ubuntu focal-legacy/main amd64 intel-igc-cm amd64 1.0.176+i537~20.04 [15.2 MB]
Get:4 https://repositories.intel.com/graphics/ubuntu focal-legacy/main amd64 libigdgmm12 amd64 22.3.1+i529~20.04 [143 kB]
Get:5 https://repositories.intel.com/graphics/ubuntu focal-legacy/main amd64 libigdfcl1 amd64 1.0.12504.6+i537~20.04 [19.6 MB]
Get:6 https://repositories.intel.com/graphics/ubuntu focal-legacy/main amd64 intel-level-zero-gpu amd64 1.3.24595.35+i538~20.04 [1,626 kB]
Get:7 https://repositories.intel.com/graphics/ubuntu focal-legacy/main amd64 intel-opencl-icd amd64 22.43.24595.35+i538~20.04 [1,938 kB]
Get:8 https://repositories.intel.com/graphics/ubuntu focal-legacy/main amd64 level-zero amd64 1.8.8+i524~u20.04 [97.8 kB]
Fetched 65.8 MB in 15s (4,432 kB/s)                                           
(Reading database ... 262797 files and directories currently installed.)
Preparing to unpack .../0-libigc1_1.0.12504.6+i537~20.04_amd64.deb ...
Unpacking libigc1 (1.0.12504.6+i537~20.04) over (1.0.3627-2) ...
Selecting previously unselected package intel-igc-cm.
Preparing to unpack .../1-intel-igc-cm_1.0.176+i537~20.04_amd64.deb ...
Unpacking intel-igc-cm (1.0.176+i537~20.04) ...
Selecting previously unselected package libigdgmm12:amd64.
Preparing to unpack .../2-libigdgmm12_22.3.1+i529~20.04_amd64.deb ...
Unpacking libigdgmm12:amd64 (22.3.1+i529~20.04) ...
Selecting previously unselected package libz3-4:amd64.
Preparing to unpack .../3-libz3-4_4.8.7-4build1_amd64.deb ...
Unpacking libz3-4:amd64 (4.8.7-4build1) ...
Preparing to unpack .../4-libigdfcl1_1.0.12504.6+i537~20.04_amd64.deb ...
Unpacking libigdfcl1 (1.0.12504.6+i537~20.04) over (1.0.3627-2) ...
Selecting previously unselected package intel-level-zero-gpu.
Preparing to unpack .../5-intel-level-zero-gpu_1.3.24595.35+i538~20.04_amd64.de
b ...
Unpacking intel-level-zero-gpu (1.3.24595.35+i538~20.04) ...
Selecting previously unselected package intel-opencl-icd.
Preparing to unpack .../6-intel-opencl-icd_22.43.24595.35+i538~20.04_amd64.deb 
...
Unpacking intel-opencl-icd (22.43.24595.35+i538~20.04) ...
Selecting previously unselected package level-zero.
Preparing to unpack .../7-level-zero_1.8.8+i524~u20.04_amd64.deb ...
Unpacking level-zero (1.8.8+i524~u20.04) ...
Setting up libigc1 (1.0.12504.6+i537~20.04) ...
Setting up libigdgmm12:amd64 (22.3.1+i529~20.04) ...
Setting up intel-igc-cm (1.0.176+i537~20.04) ...
Setting up level-zero (1.8.8+i524~u20.04) ...
Setting up libz3-4:amd64 (4.8.7-4build1) ...
Setting up libigdfcl1 (1.0.12504.6+i537~20.04) ...
Setting up intel-opencl-icd (22.43.24595.35+i538~20.04) ...
Setting up intel-level-zero-gpu (1.3.24595.35+i538~20.04) ...
Processing triggers for libc-bin (2.31-0ubuntu9.17) ...
dynamicx@leggedBalance:~$ sudo reboot
Connection to 192.168.100.2 closed by remote host.
Connection to 192.168.100.2 closed.
yuyu@yuyu:~$ sudo poweroff
[sudo] yuyu 的密码： 
yuyu@yuyu:~$ wired
dynamicx@192.168.100.2's password: 
Welcome to Ubuntu 20.04.6 LTS (GNU/Linux 6.1.38-rt12-x64v3-xanmod1 x86_64)

 * Documentation:  https://help.ubuntu.com
 * Management:     https://landscape.canonical.com
 * Support:        https://ubuntu.com/pro

 System information as of Sun 06 Apr 2025 04:23:26 PM UTC

  System load:  11.33               Temperature:           84.0 C
  Usage of /:   28.0% of 451.60GB   Processes:             405
  Memory usage: 5%                  Users logged in:       0
  Swap usage:   0%                  IPv4 address for wlo1: 192.168.177.8

 * Strictly confined Kubernetes makes edge and IoT secure. Learn how MicroK8s
   just raised the bar for easy, resilient and secure K8s cluster deployment.

   https://ubuntu.com/engage/secure-kubernetes-at-the-edge

 * Introducing Expanded Security Maintenance for Applications.
   Receive updates to over 25,000 software packages with your
   Ubuntu Pro subscription. Free for personal use.

     https://ubuntu.com/pro

Expanded Security Maintenance for Applications is not enabled.

44 updates can be applied immediately.
18 of these updates are standard security updates.
To see these additional updates run: apt list --upgradable

34 additional security updates can be applied with ESM Apps.
Learn more about enabling ESM Apps service at https://ubuntu.com/esm

Failed to connect to https://changelogs.ubuntu.com/meta-release-lts. Check your Internet connection or proxy settings


Last login: Sun Apr  6 16:03:12 2025 from 192.168.100.1
[setupvars.sh] OpenVINO environment initialized
dynamicx@leggedBalance:~$ sudo poweroff
Connection to 192.168.100.2 closed by remote host.
Connection to 192.168.100.2 closed.
yuyu@yuyu:~$ wired
dynamicx@192.168.100.2's password: 
Welcome to Ubuntu 20.04.6 LTS (GNU/Linux 6.1.38-rt12-x64v3-xanmod1 x86_64)

 * Documentation:  https://help.ubuntu.com
 * Management:     https://landscape.canonical.com
 * Support:        https://ubuntu.com/pro

 System information as of Sun 06 Apr 2025 04:25:12 PM UTC

  System load:  1.39                Temperature:           80.0 C
  Usage of /:   28.0% of 451.60GB   Processes:             462
  Memory usage: 5%                  Users logged in:       0
  Swap usage:   0%                  IPv4 address for wlo1: 192.168.177.8

 * Strictly confined Kubernetes makes edge and IoT secure. Learn how MicroK8s
   just raised the bar for easy, resilient and secure K8s cluster deployment.

   https://ubuntu.com/engage/secure-kubernetes-at-the-edge

 * Introducing Expanded Security Maintenance for Applications.
   Receive updates to over 25,000 software packages with your
   Ubuntu Pro subscription. Free for personal use.

     https://ubuntu.com/pro

Expanded Security Maintenance for Applications is not enabled.

44 updates can be applied immediately.
18 of these updates are standard security updates.
To see these additional updates run: apt list --upgradable

34 additional security updates can be applied with ESM Apps.
Learn more about enabling ESM Apps service at https://ubuntu.com/esm

Failed to connect to https://changelogs.ubuntu.com/meta-release-lts. Check your Internet connection or proxy settings


Last login: Sun Apr  6 16:23:26 2025 from 192.168.100.1
[setupvars.sh] OpenVINO environment initialized
dynamicx@leggedBalance:~$ clinfo
Number of platforms                               1
  Platform Name                                   Intel(R) OpenCL HD Graphics
  Platform Vendor                                 Intel(R) Corporation
  Platform Version                                OpenCL 3.0 
  Platform Profile                                FULL_PROFILE
  Platform Extensions                             cl_khr_byte_addressable_store cl_khr_device_uuid cl_khr_fp16 cl_khr_global_int32_base_atomics cl_khr_global_int32_extended_atomics cl_khr_icd cl_khr_local_int32_base_atomics cl_khr_local_int32_extended_atomics cl_intel_command_queue_families cl_intel_subgroups cl_intel_required_subgroup_size cl_intel_subgroups_short cl_khr_spir cl_intel_accelerator cl_intel_driver_diagnostics cl_khr_priority_hints cl_khr_throttle_hints cl_khr_create_command_queue cl_intel_subgroups_char cl_intel_subgroups_long cl_khr_il_program cl_intel_mem_force_host_memory cl_khr_subgroup_extended_types cl_khr_subgroup_non_uniform_vote cl_khr_subgroup_ballot cl_khr_subgroup_non_uniform_arithmetic cl_khr_subgroup_shuffle cl_khr_subgroup_shuffle_relative cl_khr_subgroup_clustered_reduce cl_intel_device_attribute_query cl_khr_suggested_local_work_size cl_intel_split_work_group_barrier cl_intel_spirv_media_block_io cl_intel_spirv_subgroups cl_khr_spirv_no_integer_wrap_decoration cl_intel_unified_shared_memory cl_khr_mipmap_image cl_khr_mipmap_image_writes cl_intel_planar_yuv cl_intel_packed_yuv cl_khr_int64_base_atomics cl_khr_int64_extended_atomics cl_khr_image2d_from_buffer cl_khr_depth_images cl_khr_3d_image_writes cl_intel_media_block_io cl_intel_va_api_media_sharing cl_intel_sharing_format_query cl_khr_pci_bus_info cl_intel_subgroup_local_block_io 
  Platform Host timer resolution                  1ns
  Platform Extensions function suffix             INTEL

  Platform Name                                   Intel(R) OpenCL HD Graphics
Number of devices                                 1
  Device Name                                     Intel(R) Graphics [0x46a6]
  Device Vendor                                   Intel(R) Corporation
  Device Vendor ID                                0x8086
  Device Version                                  OpenCL 3.0 NEO 
  Driver Version                                  22.43.24595.35
  Device OpenCL C Version                         OpenCL C 1.2 
  Device Type                                     GPU
  Device Profile                                  FULL_PROFILE
  Device Available                                Yes
  Compiler Available                              Yes
  Linker Available                                Yes
  Max compute units                               96
  Max clock frequency                             1400MHz
  Device Partition                                (core)
    Max number of sub-devices                     0
    Supported partition types                     None
    Supported affinity domains                    (n/a)
  Max work item dimensions                        3
  Max work item sizes                             512x512x512
  Max work group size                             512
  Preferred work group size multiple              64
  Max sub-groups per work group                   64
  Sub-group sizes (Intel)                         8, 16, 32
  Preferred / native vector sizes                 
    char                                                16 / 16      
    short                                                8 / 8       
    int                                                  4 / 4       
    long                                                 1 / 1       
    half                                                 8 / 8        (cl_khr_fp16)
    float                                                1 / 1       
    double                                               1 / 1        (n/a)
  Half-precision Floating-point support           (cl_khr_fp16)
    Denormals                                     Yes
    Infinity and NANs                             Yes
    Round to nearest                              Yes
    Round to zero                                 Yes
    Round to infinity                             Yes
    IEEE754-2008 fused multiply-add               Yes
    Support is emulated in software               No
  Single-precision Floating-point support         (core)
    Denormals                                     Yes
    Infinity and NANs                             Yes
    Round to nearest                              Yes
    Round to zero                                 Yes
    Round to infinity                             Yes
    IEEE754-2008 fused multiply-add               Yes
    Support is emulated in software               No
    Correctly-rounded divide and sqrt operations  No
  Double-precision Floating-point support         (n/a)
  Address bits                                    64, Little-Endian
  Global memory size                              13062381568 (12.17GiB)
  Error Correction support                        No
  Max memory allocation                           4294959104 (4GiB)
  Unified memory for Host and Device              Yes
  Shared Virtual Memory (SVM) capabilities        (core)
    Coarse-grained buffer sharing                 Yes
    Fine-grained buffer sharing                   No
    Fine-grained system sharing                   No
    Atomics                                       No
  Minimum alignment for any data type             128 bytes
  Alignment of base address                       1024 bits (128 bytes)
  Preferred alignment for atomics                 
    SVM                                           64 bytes
    Global                                        64 bytes
    Local                                         64 bytes
  Max size for global variable                    65536 (64KiB)
  Preferred total size of global vars             4294959104 (4GiB)
  Global Memory cache type                        Read/Write
  Global Memory cache size                        2949120 (2.812MiB)
  Global Memory cache line size                   64 bytes
  Image support                                   Yes
    Max number of samplers per kernel             16
    Max size for 1D images from buffer            268434944 pixels
    Max 1D or 2D image array size                 2048 images
    Base address alignment for 2D image buffers   4 bytes
    Pitch alignment for 2D image buffers          4 pixels
    Max 2D image size                             16384x16384 pixels
    Max planar YUV image size                     16384x16352 pixels
    Max 3D image size                             2048x2048x2048 pixels
    Max number of read image args                 128
    Max number of write image args                128
    Max number of read/write image args           128
  Max number of pipe args                         0
  Max active pipe reservations                    0
  Max pipe packet size                            0
  Local memory type                               Local
  Local memory size                               65536 (64KiB)
  Max number of constant args                     8
  Max constant buffer size                        4294959104 (4GiB)
  Max size of kernel argument                     2048 (2KiB)
  Queue properties (on host)                      
    Out-of-order execution                        Yes
    Profiling                                     Yes
  Queue properties (on device)                    
    Out-of-order execution                        No
    Profiling                                     No
    Preferred size                                0
    Max size                                      0
  Max queues on device                            0
  Max events on device                            0
  Prefer user sync for interop                    Yes
  Profiling timer resolution                      52ns
  Execution capabilities                          
    Run OpenCL kernels                            Yes
    Run native kernels                            No
    Sub-group independent forward progress        No
    IL version                                    SPIR-V_1.2 
    SPIR versions                                 1.2 
  printf() buffer size                            4194304 (4MiB)
  Built-in kernels                                (n/a)
  Device Extensions                               cl_khr_byte_addressable_store cl_khr_device_uuid cl_khr_fp16 cl_khr_global_int32_base_atomics cl_khr_global_int32_extended_atomics cl_khr_icd cl_khr_local_int32_base_atomics cl_khr_local_int32_extended_atomics cl_intel_command_queue_families cl_intel_subgroups cl_intel_required_subgroup_size cl_intel_subgroups_short cl_khr_spir cl_intel_accelerator cl_intel_driver_diagnostics cl_khr_priority_hints cl_khr_throttle_hints cl_khr_create_command_queue cl_intel_subgroups_char cl_intel_subgroups_long cl_khr_il_program cl_intel_mem_force_host_memory cl_khr_subgroup_extended_types cl_khr_subgroup_non_uniform_vote cl_khr_subgroup_ballot cl_khr_subgroup_non_uniform_arithmetic cl_khr_subgroup_shuffle cl_khr_subgroup_shuffle_relative cl_khr_subgroup_clustered_reduce cl_intel_device_attribute_query cl_khr_suggested_local_work_size cl_intel_split_work_group_barrier cl_intel_spirv_media_block_io cl_intel_spirv_subgroups cl_khr_spirv_no_integer_wrap_decoration cl_intel_unified_shared_memory cl_khr_mipmap_image cl_khr_mipmap_image_writes cl_intel_planar_yuv cl_intel_packed_yuv cl_khr_int64_base_atomics cl_khr_int64_extended_atomics cl_khr_image2d_from_buffer cl_khr_depth_images cl_khr_3d_image_writes cl_intel_media_block_io cl_intel_va_api_media_sharing cl_intel_sharing_format_query cl_khr_pci_bus_info cl_intel_subgroup_local_block_io 

NULL platform behavior
  clGetPlatformInfo(NULL, CL_PLATFORM_NAME, ...)  Intel(R) OpenCL HD Graphics
  clGetDeviceIDs(NULL, CL_DEVICE_TYPE_ALL, ...)   Success [INTEL]
  clCreateContext(NULL, ...) [default]            Success [INTEL]
  clCreateContextFromType(NULL, CL_DEVICE_TYPE_DEFAULT)  Success (1)
    Platform Name                                 Intel(R) OpenCL HD Graphics
    Device Name                                   Intel(R) Graphics [0x46a6]
  clCreateContextFromType(NULL, CL_DEVICE_TYPE_CPU)  No devices found in platform
  clCreateContextFromType(NULL, CL_DEVICE_TYPE_GPU)  Success (1)
    Platform Name                                 Intel(R) OpenCL HD Graphics
    Device Name                                   Intel(R) Graphics [0x46a6]
  clCreateContextFromType(NULL, CL_DEVICE_TYPE_ACCELERATOR)  No devices found in platform
  clCreateContextFromType(NULL, CL_DEVICE_TYPE_CUSTOM)  No devices found in platform
  clCreateContextFromType(NULL, CL_DEVICE_TYPE_ALL)  Success (1)
    Platform Name                                 Intel(R) OpenCL HD Graphics
    Device Name                                   Intel(R) Graphics [0x46a6]

ICD loader properties
  ICD loader Name                                 OpenCL ICD Loader
  ICD loader Vendor                               OCL Icd free software
  ICD loader Version                              2.2.11
  ICD loader Profile                              OpenCL 2.1
	NOTE:	your OpenCL library only supports OpenCL 2.1,
		but some installed platforms support OpenCL 3.0.
		Programs using 3.0 features may crash
		or behave unexpectedly





