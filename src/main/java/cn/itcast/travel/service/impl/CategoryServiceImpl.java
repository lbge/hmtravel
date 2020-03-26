package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Linbo Ge
 * @date 2020/3/26 - 14:31
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //1.从redis中查询
        Jedis jedis = JedisUtil.getJedis();
        Set<String> categories = jedis.zrange("category", 0, -1);
        List<Category> categoryList = null;
        //2.判断查询到集合是否为空
        if (categories == null || categories.size() == 0) {
            //3.如果为空，调用数据库查询并写入缓存
            System.out.println("从数据库查询");
            categoryList = categoryDao.findAll();
            for (int i = 0; i < categoryList.size(); i++) {

                jedis.zadd("category", categoryList.get(i).getCid(), categoryList.get(i).getCname());
            }
        } else {
            //4.不为空,将set数据存入list
            System.out.println("从缓存加载");
            categoryList = new ArrayList<Category>();
            for (String name : categories) {
                Category category = new Category();
                category.setCname(name);
                categoryList.add(category);
            }
        }
        return categoryList;
    }
}
