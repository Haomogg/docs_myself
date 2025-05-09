# 寻找R标

​        二值化处理后，首先根据矩形框的长宽比例和面积大小初步筛选疑似R标的轮廓。**r_icon_min_rect_ratio_、r_icon_max_rect_ratio_、r_icon_min_area_、r_icon_max_area_**可以动态调参。

```c++
for (auto &contour : contours) 
{
    auto bounding_rect = cv::boundingRect(contour);
    if(bounding_rect.size().area() > r_icon_max_area_)
        continue;
    float rect_ratio = bounding_rect.width >= bounding_rect.height ? bounding_rect.width / bounding_rect.height : bounding_rect.height / bounding_rect.width;
    float area = bounding_rect.size().area();
    float length = 2 * bounding_rect.width + 2 * bounding_rect.height;

    if((rect_ratio > r_icon_min_rect_ratio_ && rect_ratio < r_icon_max_rect_ratio_) && (area > r_icon_min_area_ && area < r_icon_max_area_))
        hull_vec_.push_back(contour);
}
```

​        现在以识别效果来辅助说明寻找R标的逻辑。

![](../fft_forecast/visualize/debug.png)

​        然后取点1和点2的中点为tmp_point1，点3和点4的中点为tmp_point2，通过tmp_point1和tmp_point2的距离与tmp_point1和疑似R标的距离的比例，作进一步的筛选，求tmp_point2和tmp_point1构成的向量、疑似R标和tmp_point1的向量，求这两个向量的夹角。

```c++
auto moment = cv::moments(contour);
int cx = int(moment.m10 / moment.m00);
int cy = int(moment.m01 / moment.m00);

auto r = cv::Point2f(cx, cy);
auto tmp_point1 = (p1 + p2) / 2;
auto tmp_point2 = (p3 + p4) / 2;
double ratio = getL2Distance(tmp_point1, tmp_point2) / getL2Distance(tmp_point1, r);

if (ratio <= 0.45 && ratio >= 0.25) 
{
    int p34_2_p12_vec_x = (p3.x + p4.x) / 2 - (p1.x + p2.x) / 2;
    int p34_2_p12_vec_y = (p3.y + p4.y) / 2 - (p1.y + p2.y) / 2;

    int r_2_p12_vec_x = r.x - (p1.x + p2.x) / 2;
    int r_2_p12_vec_y = r.y - (p1.y + p2.y) / 2;
    double cos_theta =
        (p34_2_p12_vec_x * r_2_p12_vec_x +
         p34_2_p12_vec_y * r_2_p12_vec_y) /
        (sqrt(pow(p34_2_p12_vec_x, 2) + pow(p34_2_p12_vec_y, 2)) *
         sqrt(pow(r_2_p12_vec_x, 2) + pow(r_2_p12_vec_y, 2)));
    double radian = acos(cos_theta);
    radian_vec.emplace_back(std::make_pair(r, radian));
}
```

​        接着对夹角大小进行排序，夹角最小的为所求的R标。

```c++
std::sort(radian_vec.begin(), radian_vec.end(), [](const auto &v1, const auto &v2) { return v1.second < v2.second; });
auto r = radian_vec[0].first;
```

​        可在windmill.h中设置限制发布到**/prediction**的帧率。

```c++
ros::Rate rate_ = 50;
```

