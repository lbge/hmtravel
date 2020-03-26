package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

/**
 * @author Linbo Ge
 * @date 2020/3/25 - 14:16
 */
public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    boolean regist(User user);

    /**
     * 激活
     * @param code
     * @return
     */
    boolean active(String code);

    /**
     * 登录方法
     * @param user
     * @return
     */
    User login(User user);
}
