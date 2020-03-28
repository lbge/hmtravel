package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author Linbo Ge
 * @date 2020/3/26 - 13:39
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService service = new UserServiceImpl();

    /**
     * 注册功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码校验
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只使用一次
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码校验失败
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }
        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用service
//        UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();
        //4.响应结果
        if (flag) {
            //成功
            info.setFlag(true);
        } else {
            //失败
            info.setFlag(false);
            info.setErrorMsg("注册失败");
        }

        //将info序列化并返回给客户端
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 登录功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//        UserService service = new UserServiceImpl();
        User u = service.login(user);
        ResultInfo info = new ResultInfo();
        //判断u是否为null
        if (u == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        //判断用户是否激活
        if (u != null && !"Y".equals(u.getStatus())) {
            //用户未激活
            info.setFlag(false);
            info.setErrorMsg("用户未激活");
        }
        if (u != null && "Y".equals(u.getStatus())) {
            request.getSession().setAttribute("user", u);
            info.setFlag(true);
        }
        //响应数据,将信息序列化
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);
    }

    /**
     * 查询单个
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object user = request.getSession().getAttribute("user");
        /*ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), user);*/
        writeValue(user,response);
    }

    /**
     * 退出登录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /**
     * 用户激活
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");
        if (code != null) {
            UserService service = new UserServiceImpl();
            boolean flag = service.active(code);
            String msg = null;
            if (flag) {
                //激活成功
                msg = "激活成功，请<a href='login.html'>登录</a>";
            } else {
                //激活失败
                msg = "激活失败";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
}