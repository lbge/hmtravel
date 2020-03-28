package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImageDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImageDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

/**
 * @author Linbo Ge
 * @date 2020/3/27 - 10:57
 */
public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImageDao routeImageDao = new RouteImageDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        PageBean<Route> pageBean = new PageBean<Route>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid, rname);
        pageBean.setTotalCount(totalCount);
        //设置总页码数
        int totalPage = (int) Math.ceil((double) totalCount / pageSize);
        pageBean.setTotalPage(totalPage);
        //设置查询开始点
        int start = (currentPage - 1) * pageSize;
        //设置list
        List<Route> routes = routeDao.findByPage(cid, start, pageSize, rname);
        pageBean.setList(routes);
        return pageBean;
    }

    @Override
    public Route findOne(String rid) {
        Route route = routeDao.findOne(Integer.parseInt(rid));
        List<RouteImg> routeImgList = routeImageDao.findByRid(route.getRid());
        route.setRouteImgList(routeImgList);
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }
}
