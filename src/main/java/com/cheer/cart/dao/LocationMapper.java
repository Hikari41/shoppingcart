package com.cheer.cart.dao;

import com.cheer.cart.model.Location;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LocationMapper {

    //  添加收货地址（收货地址，收件人，手机号）
    void addLoc(Location location);

    //  修改收货地址(收件人，手机号，地址)
    void updateLoc(Location location, @Param("locName") String locName, @Param("userId") Integer userId);

    //  删除收货地址
    void delete(@Param("locName") String locName,@Param("userId")Integer userId);

    //  查看该地址
    Location getLocation(@Param("locName") String locName,@Param("userId")Integer userId);

    //  查看收获地址
    List<Map<String,Location>> getLoc(Integer userId);

    //  查询用户是否有地址
    Integer getId(Integer useId);

    //  查看该用户是否已存在该地址
    Integer getCount(@Param("locName") String locName,@Param("userId")Integer userId);
}
