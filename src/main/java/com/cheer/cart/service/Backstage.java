package com.cheer.cart.service;

import com.cheer.cart.model.*;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Backstage extends AbseractMapper{
    static Scanner sc = new Scanner(System.in);

    //生产UUID
    public Integer UUID(){
        Integer uuid = UUID.randomUUID().toString().hashCode();
        if(uuid < 0){
            uuid = -uuid;
        }
        return uuid;
    }

    public void homepage() {
        System.out.println("***********欢迎进入购物车系统***********");
        System.out.println("1.登录\t2.注册\t3.退出系统");
        int choose = sc.nextInt();
        switch (choose) {
            case 1:
                User user = signin();
                chooseGoods(user);
                before();
                break;
            case 2:
                register();
                System.out.println("自动返回上级");
                homepage();
                break;
            case 3:
                System.out.println("已退出，感谢使用！");
                before();
                break;
                default:
                    System.out.println("请输入正确的数字");
                    System.out.println();
                    homepage();
        }
    }

    //   是否添加商品
    public void chooseGoods(User user){
        System.out.print("是否添加商品(y):");
        String yn = sc.next();
        switch (yn) {
            case "y":
                buy(user);
                userhome(user);
                break;
            default:
                userhome(user);
                break;
        }
    }

    //  注册用户
    public void register() {
        before();
        User user = new User();
        Integer uuid = UUID();
        user.setUserId(uuid);

        System.out.print("请输入用户名：");
        String name = sc.next();
        while(0 != userMapper.getUser(name)) {
            System.out.print("用户名已被使用，请重新输入：");
            System.out.print("请输入用户名：");
            name = sc.next();
        }
        user.setUsername(name);
        System.out.print("请输入密码：");
        String pass = sc.next();
        user.setPassword(pass);

        userMapper.add(user);
        System.out.println("注册用户成功！");
        after();
    }

    //  登录
    public User signin() {
        before();
        System.out.print("请输入用户名：");
        String name = sc.next();
        System.out.print("请输入密码：");
        String pass = sc.next();
        while (1 != userMapper.entry(name,pass)) {
            for (int i = 2; i > 0; i--) {
                System.out.println("用户名或密码错误，请重新输入!(您还有" + i + "次机会)");
                System.out.print("请输入用户名：");
                name = sc.next();
                System.out.print("请输入密码：");
                pass = sc.next();
            }
            System.out.println();
            homepage();
        }
        System.out.println("登录成功！");
        User user = userMapper.get(name);
        mall();
        after();
        return user;
    }

    //  商城界面
    public List<Map<String,Goods>> mall() {
        before();
        System.out.println("欢迎进入商城：");
        //List<Map<String,Goods>> list = goodsMapper.getGoods();
        List<Map<String,Goods>> hashMap = goodsMapper.getGoods();
        for (Map<String,Goods> map: hashMap) {
            for (Map.Entry<String,Goods> entry: map.entrySet()) {
                System.out.printf("%s : %s\t\t",entry.getKey(),entry.getValue() );
            }
            System.out.println();
        }
        return hashMap;
    }

    //  购物车界面
    public List<Map<String,Item>> cart(User user) {
        System.out.println();
        System.out.println("您的购物车详情:");
        List<Map<String,Item>> mapList = itemMapper.getItem(user.getUserId());
        for (Map<String,Item> map: mapList) {
            for (Map.Entry<String,Item> entry: map.entrySet()) {
                System.out.printf("%s:%s\t\t",entry.getKey(),entry.getValue() );
            }
            System.out.println();
        }
        after();
        return mapList;
    }

    //  购物车操作界面
    public void shopping(User user) {
        System.out.println();
        System.out.println("1.添加商品\t2.修改商品数量\t3.删除商品\t4.选择商品进行结算\t5.清空购物车\t任意数字键返回上级");
        int choose = sc.nextInt();
        switch (choose) {
            case 1:
                buy(user);
                break;
            case 2:
                updateNum(user);
                break;
            case 3:
                deleteItemGoods(user);
                break;
            case 4:
                addOrder(user);
                break;
            case 5:
                deleteAll(user);
                break;
                default:
                    userhome(user);
        }
    }

    // 用户操作界面
    public void userhome(User user) {
        before();
        System.out.println("尊敬的用户您好：1.查看购物车\t2.查看收获地址\t3.查看订单\t4.查看余额\t任意数字键返回上级");
        int choose = sc.nextInt();
        switch (choose) {
            case 1:
                cart(user);
                shopping(user);
                break;
            case 2:
                getLoc(user);
                break;
            case 3:
                getOrder(user);
                break;
            case 4:
                getMoney(user);
                break;
                default:
                    System.out.println("已返回上级菜单");
                    mall();
                    chooseGoods(user);
                    after();
        }
    }

    //  选择商品添加到购物车
    public void buy(User user) {
        System.out.println();
        before();
        Item item = new Item();
        System.out.print("请选择您要添加到购物车的商品编号：");
        int num = sc.nextInt();
        while (1 != goodsMapper.getGoodsId(num)){
            System.out.println("该商品不存在，请输入正确的商品编号：");
            num = sc.nextInt();
        }
        Goods goods = goodsMapper.getId(num);
        item.setUserId(user.getUserId());
        item.setGoodsId(goods.getGoodsId());
        item.setGoodsName(goods.getGoodsName());
        item.setGoodsPrice(goods.getPrice());
        System.out.print("请选择您要购买的数量：");
        int num2 = sc.nextInt();
        while (num2 > goodsMapper.getSurplus(num)){
            System.out.print("商品库存不足，请重新输入：");
            num2 = sc.nextInt();
        }
        item.setGoodsNum(num2);
        double money = goods.getPrice() * num2;
        item.setGoodsTotal(money);
        if(1 != itemMapper.getGoodsId(num,user.getUserId())) {
            itemMapper.addItem(item);
            after();
            System.out.print("添加商品成功！\n是否继续添加（y）：");
            String choose = sc.next();
            while (choose.equals("y")) {
                System.out.println();
                buy(user);
            }
            System.out.println("已返回上级！");
            userhome(user);
        }else {
            int newNum = num2 + itemMapper.getNum(num,user.getUserId());
            double newTotal = money + itemMapper.getTotal(num,user.getUserId());
            itemMapper.updateItem(newNum,newTotal,num,user.getUserId());
            after();
            System.out.print("添加商品成功！\n是否继续添加（y）：");
            String choose = sc.next();
            while (choose.equals("y")) {
                System.out.println();
                buy(user);
            }
            System.out.println("已返回上级菜单");
            userhome(user);
        }
    }

    //  修改商品数量
    public void updateNum(User user) {
        before();
        System.out.println();
        System.out.print("请输入要修改的商品名称：");
        String name = sc.next();
        while (1 != itemMapper.getCount(name,user.getUserId())){
            System.out.println("该商品不存在，请输入正确的商品名称：");
            name = sc.next();
        }
        System.out.print("请输入要修改的数量：");
        int newnum = sc.nextInt();
        Goods goods = goodsMapper.getName(name);
        double newTotal = newnum * goods.getPrice();
        itemMapper.updateNum(newnum,newTotal,name,user.getUserId());
        after();
        System.out.println("修改成功！自动返回上级");
        userhome(user);
    }

    //删除购物车中某商品
    public void deleteItemGoods(User user){
        before();
        System.out.println("请输入您要从购物车中删除的商品名称");
        String name = sc.next();
        while (0 == itemMapper.getGoodsName(name,user.getUserId())) {
            System.out.print("购物车中不存在该商品，请重新输入(n)：");
            name = sc.next();
            if(name.equals("n")) {
                shopping(user);
            }
        }
        itemMapper.deleteItem(name,user.getUserId());
        after();
        System.out.println("删除成功！");
        System.out.println("是否继续删除（y）：");
        String choose = sc.next();
        switch (choose) {
            case "y":
                deleteItemGoods(user);
                break;
                default:
                    System.out.println();
                    System.out.println("已返回上级页面！");
                    shopping(user);
        }
    }

    //  清空购物车
    public void deleteAll(User user) {
        before();
        itemMapper.deleteAll(user.getUserId());
        after();
        System.out.println("购物车已清空！");
        System.out.println("自动返回上级");
        shopping(user);
    }

    //  添加收获地址
    public void addloc(User user) {
        before();
         Location location = new Location();
         System.out.println("添加收货地址");
         System.out.print("请输入收件人姓名：");
         location.setAddressee(sc.next());
         System.out.print("请输入收件人手机号：");
         location.setPhoneNum(sc.next());
         System.out.print("请输入收获地址：");
         String locname = sc.next();
         while (1 == locationMapper.getCount(locname,user.getUserId())) {
             System.out.println("您已存在该地址，请输入一个新地址：");
             locname = sc.next();
         }
        location.setLocName(locname);
         location.setUserId(user.getUserId());
         locationMapper.addLoc(location);
         after();
        System.out.println("添加收货地址成功！");
        getLocList(user);
        System.out.println("自动返回上级");
        locPage(user);
    }

    // 修改收货地址
    public void updateLoc(User user){
        before();
        System.out.println("请选择要修改的地址名称");
        String locname = sc.next();
        while (1 != locationMapper.getCount(locname,user.getUserId())) {
            System.out.println("该地址不存在，请输入正确地址：");
            locname = sc.next();
        }
        Location location = new Location();
        System.out.println("1.修改地址名称\t2.修改收件人姓名\t3.修改手机号\t4.全部修改\t5.重新选择\t任意数字键返回上级");
        int choose = sc.nextInt();
        switch (choose) {
            case 1:
                System.out.print("请输入新地址：");
                location.setLocName(sc.next());
                locationMapper.updateLoc(location,locname,user.getUserId());
                after();
                System.out.println("修改成功！\n收货地址如下：");
                getLocList(user);
                System.out.println("已返回上级");
                locPage(user);
                break;
            case 2:
                System.out.print("请输入新收件人：");
                location.setAddressee(sc.next());
                locationMapper.updateLoc(location,locname,user.getUserId());
                after();
                System.out.println("修改成功！\n收货地址如下：");
                getLocList(user);
                System.out.println("已返回上级");
                locPage(user);
                break;
            case 3:
                System.out.print("请输入新手机号：");
                location.setPhoneNum(sc.next());
                locationMapper.updateLoc(location,locname,user.getUserId());
                after();
                System.out.println("修改成功！\n收货地址如下：");
                getLocList(user);
                System.out.println("已返回上级");
                locPage(user);
                break;
            case 4:
                System.out.print("请输入新收件人：");
                location.setAddressee(sc.next());
                System.out.print("请输入新手机号");
                location.setPhoneNum(sc.next());
                System.out.print("请输入新地址：");
                location.setLocName(sc.next());
                locationMapper.updateLoc(location,locname,user.getUserId());
                after();
                System.out.println("修改成功！\n收货地址如下：");
                getLocList(user);
                System.out.println("已返回上级");
                locPage(user);
                break;
            case 5:
                updateLoc(user);
                break;
                default:
                    userhome(user);
        }
    }

    //  删除收获地址
    public void deleteLoc(User user) {
        before();
        System.out.println("请输入您要删除的地址名称：");
        String locname = sc.next();
        while (1 != locationMapper.getCount(locname,user.getUserId())){
            System.out.println("您的收货地址信息中不存在该地址，请重新输入：");
            locname = sc.next();
        }
        locationMapper.delete(locname,user.getUserId());
        after();
        System.out.println("删除成功！自动返回上级");
        locPage(user);
    }

    //  查看收获地址
    public void getLoc(User user) {
        before();
        if(0 == locationMapper.getId(user.getUserId())) {
            System.out.print("您暂无收货地址，是否添加（y）：");
            String choose = sc.next();
            switch (choose) {
                case "y":
                    addloc(user);
                    break;
                default:
                    System.out.println();
                    System.out.println("返回上级！");
                    userhome(user);
            }
        }
        getLocList(user);
        locPage(user);
    }

    //  地址操作界面
    public void locPage(User user)  {
        System.out.println("1.添加地址\t2.修改地址\t3.删除地址 任意数字键返回上级");
        int choose = sc.nextInt();
        switch (choose) {
            case 1:
                addloc(user);
                break;
            case 2:
                updateLoc(user);
                break;
            case 3:
                deleteLoc(user);
                break;
            default:
                userhome(user);
        }
    }

    //  显示地址信息
    public List<Map<String,Location>> getLocList(User user) {
        before();
        System.out.println();
        System.out.println("您的收货地址如下：");
        List<Map<String,Location>> mapList= locationMapper.getLoc(user.getUserId());
        for (Map<String,Location> map: mapList) {
            for (Map.Entry<String,Location> entry: map.entrySet()) {
                System.out.printf("%s : %s\t\t",entry.getKey(),entry.getValue() );
            }
            System.out.println();
        }
        after();
        return mapList;
    }

    //  查看订单
    public void getOrder(User user) {
        before();
        if (0 == orderMapper.getId(user.getUserId())){
            System.out.println("您暂无订单,是否进入购物车进行结算(y):");
            String choose = sc.next();
            switch (choose) {
                case "y":
                    addOrder(user);
                    break;
                    default:
                        System.out.println("已返回上级！");
                        shopping(user);
            }
        }
        showOrder(user);
        orderPage(user);
    }

    //   显示订单
    public void showOrder(User user) {
        List<Map<String,Order>> mapList = orderMapper.getOrder(user.getUserId());
        for (Map<String,Order> map: mapList) {
            for (Map.Entry<String,Order> entry: map.entrySet()) {
                System.out.printf("%s : %s\t\t",entry.getKey(),entry.getValue() );
            }
            System.out.println();
        }
    }

    // 将购物车中某商品添加到订单
    public void addOrder(User user){
        before();
        Order order = new Order();
        int uuid = UUID();

        System.out.println("输入商品名称下单:");
        String name = sc.next();
        while (1 != itemMapper.getCount(name,user.getUserId())) {
            System.out.println("您的购物车中不存在此商品（按1进去商城添加商品，或任意数字键返回上级）:");
            int choose = sc.nextInt();
            switch (choose) {
                case 1:
                    mall();
                    buy(user);
                    break;
                default:
                    userhome(user);
            }
            while (1 == orderMapper.getCount(name,user.getUserId())){
                System.out.println("订单中已存在该商品，请重新输入(或按n返回上级)：");
                name = sc.next();
                if (name.equals("n")) {
                    System.out.println();
                    System.out.println("已返回上级！");
                    userhome(user);
                }
            }
        }
        int sur = goodsMapper.getSur(name);
        int itemSur = itemMapper.getGoodsName(name,user.getUserId());
        if (itemSur > sur)
        {
            System.out.println("商品库存不足！");
            System.out.println("自动返回上级！");
            userhome(user);
        }
        double total = itemMapper.getMoeny(name,user.getUserId());
        order.setOrderId(uuid);
        order.setUserId(user.getUserId());
        order.setGoodsName(name);
        order.setTotal(total);
        orderMapper.addOrder(order);
        System.out.println("订单创建成功！");
        System.out.println("您的订单详情如下：");
        showOrder(user);
        after();
        System.out.print("是否继续添加商品(y)：");
        String choose = sc.next();
        while (choose.equals("y")) {
            System.out.println();
            addOrder(user);
        }
        System.out.println("已返回上级！");
        shopping(user);
    }

    //  订单操作界面
    public void orderPage(User user) {
        System.out.println();
        System.out.println("1.结算\t2.取消订单\t任意数字键返回上级");
        int choose = sc.nextInt();
        switch (choose) {
            case 1:
                System.out.println("是否结算（y）:");
                String choos = sc.next();
                switch (choos) {
                    case "y":
                    settle(user);
                    break;
                    default:
                        System.out.println("退出结算！");
                        userhome(user);
                }
                break;
            case 2:
                deleteOrder(user);
                break;
                default:
                    userhome(user);
        }
    }

    //  结算订单
    public void settle(User user) {
        before();
        System.out.print("请选择您需要结算的订单号：");
        int orderId = sc.nextInt();
        while(1 != orderMapper.getNum(orderId,user.getUserId())) {
            System.out.println();
            System.out.print("输入的订单号有误，请重新输入：");
            orderId = sc.nextInt();
        }
        getLocList(user);
        System.out.println();
        System.out.println("请选择您的收货地址：");
        String locname = sc.next();
        while (1 != locationMapper.getCount(locname,user.getUserId())) {
            System.out.println("请输入正确的地址名称（或按y添加地址信息）：");
            locname = sc.next();
            if (locname.equals("y")) {
                addloc(user);
            }
        }
        double total = orderMapper.getTotal(orderId,user.getUserId());
        double money = userMapper.getMoney(user.getUserId());
        if (total > money) {
            System.out.println("你的余额不足！（转入充值页面）");
            rech(user);
        }
        orderMapper.deleteOrder(orderId,user.getUserId());
        after();

        before();
        System.out.println("订单已结算，您本次消费" + total + "元。");
        System.out.println("收货地址为：" + locationMapper.getLocation(locname,user.getUserId()));
        System.out.println("您的余额为：" + (money - total) + "元。");
        after();

        System.out.println("是否继续结算(y)：");
        String choos = sc.next();
        switch (choos) {
            case "y":
                System.out.println();
                settle(user);
                break;
                default:
                    System.out.println("已返回上级菜单！");
                    shopping(user);
        }
        System.out.println("已返回上级菜单！");
        userhome(user);
    }

    //  订单超时
    public void overtime(Integer orderId,Integer userId){
        ScheduledExecutorService service= Executors.newScheduledThreadPool(10);
        Task task = new Task();
        task.setOrderId(orderId);
        task.setUserId(userId);
        service.schedule(task,10,TimeUnit.SECONDS);
    }

    //  取消订单
    public void deleteOrder(User user) {
        before();
        System.out.println("请选择要删除的订单号：");
        int orderId = sc.nextInt();
        while (1 != orderMapper.getNum(orderId,user.getUserId())) {
            System.out.println("不存在该订单，请输入正确订单号：");
            orderId = sc.nextInt();
        }
        orderMapper.deleteOrder(orderId,user.getUserId());
        after();
        System.out.println("订单取消成功！");
        System.out.println("自动返回上级！");
        userhome(user);
    }

    //  查询账户余额
    public void getMoney(User user) {
        before();
        System.out.println("您的余额为：" +  userMapper.getMoney(user.getUserId()));
        System.out.print("是否充值（y）：");
        String choose = sc.next();
        switch (choose) {
            case "y":
                rech(user);
                break;
                default:
                    System.out.println("已返回上级菜单！");
                    userhome(user);
        }

    }

    //充值
    public void rech(User user){
        before();
        System.out.print("请输入充值金额:");
        double money = sc.nextDouble();
        money = money + userMapper.getMoney(user.getUserId());
        userMapper.updateMoney(money,user.getUserId());
        after();
        System.out.println("充值成功，自动返回上一级菜单");
        userhome(user);
    }

    //  添加商品信息
    public void addGoods() {
        before();
        Goods goods = new Goods();
        goods.setGoodsId(2);
        goods.setGoodsName("football");
        goods.setPrice(1000.00);
        goods.setSurplus(100);
        goodsMapper.addGoods(goods);
        after();
    }

    //  删除商品信息
    public void deleteGoods(){
        before();
        goodsMapper.deleteGoods(1);
        after();
    }

    //  修改商品价格或者库存
    public void  updatePri() {
        before();
        Goods goods = new Goods();
        goods.setPrice(200.00);
        goods.setGoodsId(1);
        goodsMapper.updatePri(goods);
        after();
    }

    //  修改库存
    public void updateSur() {
        before();
        Goods goods = new Goods();
        goods.setSurplus(6);
        goods.setGoodsId(1);
        goodsMapper.updateSur(goods);
        after();
    }

    public static void main(String[] args) {
        Backstage backstage = new Backstage();
        backstage.addGoods();
    }
}
