package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

/**
 * @author Linbo Ge
 * @date 2020/3/28 - 13:36
 */
public interface FavoriteDao {
    /**
     * 查询是否收藏
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByRidAndUid(int rid, int uid);

    /**
     * 查询收藏次数
     * @param rid
     * @return
     */
    int findCountByRid(int rid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void add(int rid, int uid);
}
