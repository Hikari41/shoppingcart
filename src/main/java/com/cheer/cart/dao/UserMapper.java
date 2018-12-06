package com.cheer.cart.dao;

import com.cheer.cart.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    //  查询用户名是否存在
    Integer getUser(String username);

    //  查询输入密码是否正确
    Integer getPass(String password);

    //  注册账号,往数据库用户表添加数据
    void add(User user);

    //  登录账号
    int entry(@Param("username") String username, @Param("password") String password);

    //  修改账号密码
    User update(User user);

    //  充值
    void updateMoney(@Param("money") Double money,@Param("userId") Integer userId);

    //  查询金额
    Double getMoney(Integer userId);

    //  以name查询用户信息
    User get(String name);

    //  以id查询用户信息
    User getId(String userId);
}
