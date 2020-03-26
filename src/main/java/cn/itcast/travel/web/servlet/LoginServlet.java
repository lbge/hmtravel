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
 * @date 2020/3/26 - 9:11
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService service = new UserServiceImpl();
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
        if (u != null&& "Y".equals(u.getStatus())){
            info.setFlag(true);
        }
        HttpSession session = request.getSession();
        session.setAttribute("user",u);
        //响应数据,将信息序列化
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
