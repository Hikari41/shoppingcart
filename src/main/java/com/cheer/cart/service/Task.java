package com.cheer.cart.service;

public class Task extends AbseractMapper implements Runnable{
    private Integer orderId;
    private Integer userId;

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public void run() {
        Backstage backstage = new Backstage();
        before();
        orderMapper.deleteOrder(this.orderId,this.userId);
        after();
        System.out.println("订单超时，已取消！");
    }
}
