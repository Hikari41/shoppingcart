package com.cheer.cart.service;

import com.cheer.cart.dao.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbseractMapper {
    SqlSession sqlSession = null;
    InputStream inputStream = null;

    ShoppingCartMapper shoppingCartMapper = null;
    UserMapper userMapper = null;
    OrderMapper orderMapper = null;
    GoodsMapper goodsMapper = null;
    ItemMapper itemMapper = null;
    LocationMapper locationMapper = null;

    @Before
    public void before() {
        try {
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();

            shoppingCartMapper = sqlSession.getMapper(ShoppingCartMapper.class);
            userMapper = sqlSession.getMapper(UserMapper.class);
            orderMapper = sqlSession.getMapper(OrderMapper.class);
            goodsMapper = sqlSession.getMapper(GoodsMapper.class);
            itemMapper = sqlSession.getMapper(ItemMapper.class);
            locationMapper = sqlSession.getMapper(LocationMapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void after() {
        // 事务提交
        this.sqlSession.commit();
        this.sqlSession.close();
        try {
            if (inputStream != null) {
                this.inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}