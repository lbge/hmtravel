package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * @author Linbo Ge
 * @date 2020/3/28 - 10:29
 */
public interface SellerDao {
    /**
     * 根据id查找卖家信息
     * @param id
     * @return
     */
    public Seller findById(int id);
}
