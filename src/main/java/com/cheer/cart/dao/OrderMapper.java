package com.cheer.cart.dao;

import com.cheer.cart.model.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderMapper {

    //  查看订单
    List<Map<String,Order>> getOrder(Integer userId);

    //  删除订单
    void deleteOrder(@Param("orderId") Integer orderId, @Param("userId") Integer userId);

    //  添加订单
    void addOrder(Order order);

    //  查询用户是否有订单
    Integer getId(Integer userId);

    //  查看订单中是否存在该商品记录
    Integer getCount(@Param("goodsName") String goodsName, @Param("userId") Integer userId);

    // 查看此订单的总额
    Double getTotal(@Param("orderId") Integer orderId, @Param("userId") Integer userId);

    //  查看某条订单是否存在
    Integer getNum(@Param("orderId") Integer orderId, @Param("userId") Integer userId);
}
