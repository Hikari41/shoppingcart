package com.cheer.cart.dao;

import com.cheer.cart.model.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsMapper {

    //  展示商品信息
    List<Map<String,Goods>> getGoods();

    //  添加商品
    void addGoods(Goods goods);

    //  删除商品
    void deleteGoods(Integer goodsId);

    //  修改商品价格
    void updatePri(Goods goods);

    //  修改商品库存
    void updateSur(Goods goods);

    //  商品id查询库存
    Integer getSurplus(Integer goodsId);

    //  商品名称查询库存
    Integer getSur(String goodsName);

    //  以Id获取信息
    Goods getId(Integer goodsId);

    //  以goodsName获取信息
    Goods getName(String goodsName);

    //  是否有此商品
    Integer getGoodsId(Integer goodsId);

    //  获取商品价格
    Double getPrice(Integer goodsId);
}
