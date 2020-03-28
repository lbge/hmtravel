package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

/**
 * @author Linbo Ge
 * @date 2020/3/28 - 13:34
 */
public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);

        return favorite != null;//对象有值返回true，没值返回false
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }
}
