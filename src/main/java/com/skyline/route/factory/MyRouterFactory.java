/**
 * FileName: RouterFactory
 * Author: 何锦川
 * Date: 2021/12/6 18:09
 * Description: 路由器工厂类
 * History:
 * <author>      <time>      <version>      <des>
 * 作者姓名      修改时间        版本号        描述
 */
package com.skyline.route.factory;

import com.skyline.route.router.MessageHelper;
import com.skyline.route.config.RouteConfig;
import com.skyline.route.router.ConnectionTable;
import com.skyline.route.router.ConnectionTableConfigurator;
import com.skyline.route.router.Router;
import com.skyline.route.router.RoutingTableConfigurator;
import com.skyline.route.mapper.ConnectionMapper;
import com.skyline.route.mapper.RoutingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <功能概述>
 * <路由器工厂类>
 *
 * @author 何锦川
 * @create 2021/12/6 18:09
 * @since 1.0.0
 */
@Component
public class MyRouterFactory {

    /**
     * 连接表数据库操作对象
     * */
    private final ConnectionMapper connectionMapper;
    /**
     * 路由表数据库操作对象
     * */
    private final RoutingMapper routingMapper;
    /**
     * 路由器配置类
     * */
    private final RouteConfig routeConfig;
    /**
     * 生成的路由器列表
     */
    private final List<Router> routers = new ArrayList<>();

    @Autowired
    public MyRouterFactory(ConnectionMapper connectionMapper, RoutingMapper routingMapper, RouteConfig routeConfig) {
        this.connectionMapper = connectionMapper;
        this.routingMapper = routingMapper;
        this.routeConfig = routeConfig;
        init();
    }

    /**
     * 初始化工厂，创建路由对象
     */
    private void init() {
        int routeCount = routeConfig.getRouteCount();

        for (int i = 0; i < routeCount; i++) {
            createRouter();
        }
    }

    /**
     * 创建路由器对象
     */
    public void createRouter() {

        ConnectionTableConfigurator connectionTableConfigurator = new ConnectionTableConfigurator(connectionMapper);

        RoutingTableConfigurator routingTableConfigurator = new RoutingTableConfigurator(routingMapper);

        MessageHelper messageHelper = new MessageHelper("routeReceive_" + (routers.size() + 1) + ".txt");

        // 初始化路由器对象
        Router router = new Router( routers.size() + 1 , connectionTableConfigurator, routingTableConfigurator, messageHelper);

        // 初始化路由器连接表
        setConnectionAndDelay(router);

        // 记录路由器
        storeRouter(router);
    }

    /**
     * 设置路由器与其他路由器的连接延迟，由随机数产生 <br/>
     * 随机数范围 1~100，假定大于 80 的 延迟为不可连接
     */
    private void setConnectionAndDelay(Router router) {
        ConnectionTableConfigurator connectionTableConfigurator = router.getConnectionTableConfigurator();

        List<ConnectionTable> connectionTables = connectionTableConfigurator.getConnectionTables();

        Random random = new Random();

        for (Router destination : routers) {
            int delay = random.nextInt(100) + 1;

            // 无向图，需要同时建立两个连接表
            ConnectionTable connectionTable_1 = new ConnectionTable();
            ConnectionTable connectionTable_2 = new ConnectionTable();

            // 设置源路由
            connectionTable_1.setSource(router.getRouterId());
            connectionTable_2.setSource(destination.getRouterId());

            // 设置目标路由
            connectionTable_1.setDestination(destination.getRouterId());
            connectionTable_2.setDestination(router.getRouterId());

            // 设置连接代价
            connectionTable_1.setCost(delay <= 80 ? delay : -1);
            connectionTable_2.setCost(delay <= 80 ? delay : -1);

            // 保存到缓存
            connectionTables.add(connectionTable_1);
            connectionTables.add(connectionTable_2);
        }
        // 保存到数据库
        connectionTableConfigurator.saveToDatabase();
    }

    /**
     * 保存路由器
     * @param router 创建好的路由器
     */
    private void storeRouter(Router router) {
        routers.add(router);
    }

    public List<Router> getRouters() {
        return routers;
    }

    public Router getRouter(int id) {
        return routers.get(id - 1);
    }
}
