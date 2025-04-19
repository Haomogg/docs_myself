package com.zhenzikj.mapper;

import com.zhenzikj.vo.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BlogMapper {

	public Blog selectByDbid(@Param("dbid") int dbid);

	public List<Blog> list();
}
