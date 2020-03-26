package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Linbo Ge
 * @date 2020/3/26 - 13:59
 */
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    private CategoryService service = new CategoryServiceImpl();

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = service.findAll();
        writeValue(categories, response);
    }

}
