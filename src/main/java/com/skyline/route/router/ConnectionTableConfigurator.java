/**
 * FileName: ConnectionTableConfigurator
 * Author: 何锦川
 * Date: 2021/12/6 18:02
 * Description: 路由器连接表配置器
 * History:
 * <author>      <time>      <version>      <des>
 * 作者姓名      修改时间        版本号        描述
 */
package com.skyline.route.router;

import com.skyline.route.mapper.ConnectionMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * <功能概述>
 * <路由器连接表配置器>
 *
 * @author 何锦川
 * @create 2021/12/6 18:02
 * @since 1.0.0
 */
public class ConnectionTableConfigurator {

    /**
     * 该路由器对应的所有连接表
     * */
    private List<ConnectionTable> connectionTables = new ArrayList<>();
    /**
     * 连接表数据库操作对象
     * */
    private ConnectionMapper connectionMapper;

    /**
     * 将所有连接表保存到数据库
     */
    public void saveToDatabase() {
        for (ConnectionTable connectionTable : connectionTables) {
            connectionMapper.setConnection(connectionTable);
        }
    }

    /**
     * 读取数据库中对应的连接表
     */
    public void readFromDatabase(int sourceId) {
        connectionTables = connectionMapper.getConnection(sourceId);
    }

    public ConnectionTableConfigurator() {
    }

    public ConnectionTableConfigurator(ConnectionMapper connectionMapper) {
        this.connectionMapper = connectionMapper;
    }

    public List<ConnectionTable> getConnectionTables() {
        return connectionTables;
    }

    public ConnectionMapper getConnectionMapper() {
        return connectionMapper;
    }

    public void setConnectionMapper(ConnectionMapper connectionMapper) {
        this.connectionMapper = connectionMapper;
    }

    @Override
    public String toString() {
        return "ConnectionTableConfigurator{" +
                "connectionTables=" + connectionTables +
                '}';
    }
}
