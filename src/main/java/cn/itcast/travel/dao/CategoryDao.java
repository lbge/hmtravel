package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @author Linbo Ge
 * @date 2020/3/26 - 14:32
 */
public interface CategoryDao {
    /**
     * 查询所有
     * @return
     */
    public List<Category> findAll();
}
