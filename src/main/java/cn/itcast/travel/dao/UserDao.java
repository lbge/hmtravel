package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

/**
 * @author Linbo Ge
 * @date 2020/3/25 - 14:17
 */
public interface UserDao {
    /**
     * 根据用户名查找数据
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 保存数据
     * @param user
     */
    public void save(User user);

    /**
     * 通过激活码查找
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 改变激活状态
     * @param user
     */
    void updateStates(User user);
}
