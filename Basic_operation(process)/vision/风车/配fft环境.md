```shell
git clone https://github.com/fastlib/fCWT.git
cd fCWT
mkdir -p build
cd build
cmake ../
```
---------------------------------
在fcwt.h加
```cpp
#include <cassert>
#include <cstring>
```
--------------------------------
```shell
$ make 
$ sudo make install
```
在cmakelist加
```cmake
include_directories(
        include
        ${catkin_INCLUDE_DIRS}
        ${FFTW3_INCLUDE_DIRS}
        ${CERES_INCLUDE_DIRS}
        ${FCWT_INCLUDE_DIRS} <-
)
target_link_libraries(
        ${PROJECT_NAME}
        ${catkin_LIBRARIES}
        ${FFTW3_LIBRARIES}
        ${FCWT_LIBRARIES} <-
        ${CERES_LIBRARIES}
)
```
------------------------------
# Install dependencies

Dependencies:

- ROS Noetic

- fftw-3.3.10

  ```bash
  cd fftw-3.3.10
  mkdir build
  cd build
  cmake ..
  make -j
  sudo make install
  ```

- gflags

  ```bash
  cd gflags
  mkdir build && cd build
  cmake .. -DGFLAGS_NAMESPACE=google -DCMAKE_CXX_FLAGS=-fPIC ..
  make -j
  sudo make install
  ```

- glog-0.6.0

  ```bash
  cd glog-0.6.0
  mkdir build && cd build
  cmake -DGFLAGS_NAMESPACE=google -DCMAKE_CXX_FLAGS=-fPIC -DBUILD_SHARED_LIBS=ON ..
  make -j
  sudo make install
  ```

  - ceres-solver-1.14.0

  ```bash
  cd ceres-solver
  mkdir build && cd build
  cmake ..
  make -j
  sudo make install
  ```
  最后装ceres-solver！！！！！！！！！！！！！！！！
  
编译windmill时要先激活openvino

编译报错
编译程序的时候遇到如下报错：
```
  Could not find a package configuration file provided by "CGAL" with any of
  the following names:

    CGALConfig.cmake
    cgal-config.cmake

  Add the installation prefix of "CGAL" to CMAKE_PREFIX_PATH or set
  "CGAL_DIR" to a directory containing one of the above files.  If "CGAL"
  provides a separate development package or SDK, be sure it has been
  installed.
```

解决方法
缺少cgal库，直接安装就可以。

sudo apt-get install libcgal-dev

