package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @author Linbo Ge
 * @date 2020/3/26 - 14:30
 */
public interface CategoryService {

    public List<Category> findAll();
}
