/**
 * FileName: ConnectionTable
 * Author: 何锦川
 * Date: 2021/12/6 18:34
 * Description: 连接表
 * History:
 * <author>      <time>      <version>      <des>
 * 作者姓名      修改时间        版本号        描述
 */
package com.skyline.route.router;

/**
 * <功能概述>
 * <连接表>
 *
 * @author 何锦川
 * @create 2021/12/6 18:34
 * @since 1.0.0
 */
public class ConnectionTable {

    /**
     * 连接信息id
     * */
    private int id;
    /**
     * 源路由
     * */
    private int source;
    /**
     * 目标路由
     * */
    private int destination;
    /**
     * 连接代价，-1 为不可连接
     * */
    private int cost;

    public ConnectionTable() {
    }

    public ConnectionTable(int id, int source, int destination, int cost) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.cost = cost;
    }

    public ConnectionTable(int source, int destination, int cost) {
        this.source = source;
        this.destination = destination;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "ConnectionTable{" +
                "id=" + id +
                ", source=" + source +
                ", destination=" + destination +
                ", cost=" + cost +
                '}';
    }
}
