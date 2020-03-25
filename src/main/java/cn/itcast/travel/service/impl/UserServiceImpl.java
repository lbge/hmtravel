package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

/**
 * @author Linbo Ge
 * @date 2020/3/25 - 14:16
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean regist(User user) {
        //1.根据用户名查询用户对象
        User u = userDao.findByUsername(user.getUsername());
        if (u != null) {
            return false;
        }
        //2.保存用户信息
        //设置激活码
        user.setCode(UuidUtil.getUuid());
        //设置激活状态
        user.setStatus("N");
        userDao.save(user);

        //3.发送邮件
        String content = "<a href='http://localhost/travel/activeUserServlet?code=" + user.getCode() + "'>点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(), content, "激活邮件");
        return true;
    }

    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if (user != null) {
            //2.调用dao修改激活状态
            userDao.updateStates(user);
            return true;
        } else {
            return false;
        }


    }
}
