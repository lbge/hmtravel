package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Linbo Ge
 * @date 2020/3/27 - 10:36
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        //2.处理参数
        int cid = 0;//类别id
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }
        int pageSize = 0;//每页显示条数
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            //如果未传递，设置默认值
            pageSize = 5;
        }
        int currentPage = 0;//当前页码
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            //如果未传递，设置默认值
            currentPage = 1;
        }
        //3.调用service查询PageBean对象
        PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize, rname);
        //4.将PageBean对象序列化并返回
        writeValue(routePageBean, response);
    }

    /**
     * 根据id查询一个旅游线路的详情
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        Route route = routeService.findOne(rid);
        writeValue(route, response);
    }

    /**
     * 判断当前用户是否收藏该线路
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            //用户未登录
            uid = 0;
        } else {
            uid = user.getUid();
        }
        boolean flag = favoriteService.isFavorite(rid, uid);
        writeValue(flag, response);
    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            //用户未登录
            return;
        } else {
            uid = user.getUid();
        }
        favoriteService.add(rid,uid);
    }
}
