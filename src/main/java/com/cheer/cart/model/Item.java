package com.cheer.cart.model;

public class Item {
    private Integer goodsId;
    private String goodsName;
    private Double goodsPrice;
    private Integer goodsNum;
    private Double goodsTotal;
    private Integer userId;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Double getGoodsTotal() {
        return goodsTotal;
    }

    public void setGoodsTotal(Double goodsTotal) {
        this.goodsTotal = goodsTotal;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("goodsId=").append(goodsId);
        sb.append(", goodsName='").append(goodsName).append('\'');
        sb.append(", goodsPrice=").append(goodsPrice);
        sb.append(", goodsNum=").append(goodsNum);
        sb.append(", goodsTotal=").append(goodsTotal);
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
