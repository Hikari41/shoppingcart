package com.cheer.cart.dao;

import com.cheer.cart.model.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemMapper {

    //  查看购物车
    List<Map<String,Item>> getItem(Integer userId);

    //  添加到购物车
    void addItem(Item item);

    //  删除购物车商品
    void deleteItem(@Param("goodsName") String name, @Param("userId") Integer userId);

    //  清空购物车
    void deleteAll(Integer userId);

    //  修改购物车中商品数量
    void updateNum(@Param("goodsNum") Integer goodsNum,@Param("goodsTotal") Double goodsTotal,@Param("goodsName") String goodsName, @Param("userId") Integer userId);

    //  以goodsname查看用户购物车中是否有此商品
    Integer getCount(@Param("goodsName") String goodsName,@Param("userId") Integer userId);

    //  更新购物车中商品数量和价格
    void updateItem(@Param("goodsNum") Integer goodsNum,@Param("goodsTotal") Double goodsTotal,
                    @Param("goodsId") Integer goodsId,@Param("userId") Integer userId);

    //  以id查看购物车是否有此商品
    Integer getGoodsId(@Param("goodsId") Integer goodsId,@Param("userId") Integer userId);

    //  以商品名查看购物车是否有此商品
    Integer getGoodsName(@Param("goodsName") String goodsName,@Param("userId") Integer userId);

    //  id获取某商品的数量
    Integer getNum(@Param("goodsId") Integer goodsId,@Param("userId") Integer userId);

    //  商品名获取数量
    Integer getGoodsNum(@Param("goodsName") Integer goodsName,@Param("userId") Integer userId);

    //  id获取某商品的总价
    Double getTotal(@Param("goodsId") Integer goodsId,@Param("userId") Integer userId);

    //  name获取某商品的总价
    Double getMoeny(@Param("goodsName") String goodsName,@Param("userId") Integer userId);

    //  以用户id获取购物车信息
    Item getId(Integer userId);

    //  id获取某个商品名称
    String getName(@Param("goodsId")Integer goodsId, @Param("userId")Integer userId);
}
