package com.zhenzikj.service;

import com.zhenzikj.mapper.BlogMapper;
import com.zhenzikj.vo.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogMapper blogMapper;

    public Blog selectByDbid(int dbid){
        return blogMapper.selectByDbid(dbid);
    }
    /**
     * 通用查询
     */
    public List<Blog> list(){
        return this.blogMapper.list();
    }
}
