/**
 * FileName: RoutingTableConfigurator
 * Author: 何锦川
 * Date: 2021/12/6 18:03
 * Description: 路由表适配器
 * History:
 * <author>      <time>      <version>      <des>
 * 作者姓名      修改时间        版本号        描述
 */
package com.skyline.route.router;

import com.skyline.route.mapper.RoutingMapper;
import org.springframework.jca.work.DelegatingWork;

import java.util.ArrayList;
import java.util.List;

/**
 * <功能概述>
 * <路由表适配器>
 *
 * @author 何锦川
 * @create 2021/12/6 18:03
 * @since 1.0.0
 */
public class RoutingTableConfigurator {

    /**
     * 该路由器对应的所有路由表
     * */
    private List<RoutingTable> routingTables = new ArrayList<>();
    /**
     * 路由表操作对象
     * */
    private RoutingMapper routingMapper;

    /**
     * 将所有路由表保存到数据库
     */
    public void saveToDatabase() {
        for (RoutingTable routingTable : routingTables) {
            routingMapper.setRouting(routingTable);
        }
    }

    /**
     * 读取数据库中对应的连接表
     */
    public void readFromDatabase(int sourceId) {
        routingTables = routingMapper.getRoutingTable(sourceId);
    }

    /**
     * 获取转发路由id
     * @param sourceId 源路由id
     * @param destinationId 目标路由id
     * @return 转发路由id
     */
    public int getForwarderId(int sourceId, int destinationId) {
        return routingMapper.getForwarderId(sourceId, destinationId);
    }

    public RoutingTableConfigurator() {
    }

    public RoutingTableConfigurator(RoutingMapper routingMapper) {
        this.routingMapper = routingMapper;
    }

    public List<RoutingTable> getRoutingTables() {
        return routingTables;
    }

    public RoutingMapper getRoutingMapper() {
        return routingMapper;
    }

    public void setRoutingMapper(RoutingMapper routingMapper) {
        this.routingMapper = routingMapper;
    }

    @Override
    public String toString() {
        return "RoutingTableConfigurator{" +
                "routingTables=" + routingTables +
                '}';
    }
}
