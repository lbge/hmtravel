package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * 线路service
 * @author Linbo Ge
 * @date 2020/3/27 - 10:57
 */
public interface RouteService {
    /**
     * 根据类别分页查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    public PageBean<Route> pageQuery(int cid,int currentPage,int pageSize,String rname);
}
