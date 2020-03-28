package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

/**
 * @author Linbo Ge
 * @date 2020/3/28 - 10:17
 */
public interface RouteImageDao {
    /**
     * 根据rid查询图片
     * @param rid
     * @return
     */
    public List<RouteImg> findByRid(int rid);
}
