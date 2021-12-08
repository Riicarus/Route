/**
 * FileName: RoutingTable
 * Author: 何锦川
 * Date: 2021/12/6 18:34
 * Description: 路由表
 * History:
 * <author>      <time>      <version>      <des>
 * 作者姓名      修改时间        版本号        描述
 */
package com.skyline.route.router;

import java.util.Objects;

/**
 * <功能概述>
 * <路由表>
 *
 * @author 何锦川
 * @create 2021/12/6 18:34
 * @since 1.0.0
 */
public class RoutingTable {

    /**
     * 路由表id
     * */
    private int id;
    /**
     * 源路由id
     * */
    private int source;
    /**
     * 目标路由id
     * */
    private int destination;
    /**
    * 转发路由id
    * */
    private int forwarder;

    public RoutingTable() {
    }

    public RoutingTable(int id, int source, int destination, int forwarder) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.forwarder = forwarder;
    }

    public RoutingTable(int source, int destination, int forwarder) {
        this.source = source;
        this.destination = destination;
        this.forwarder = forwarder;
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

    public int getForwarder() {
        return forwarder;
    }

    public void setForwarder(int forwarder) {
        this.forwarder = forwarder;
    }

    @Override
    public String toString() {
        return "RoutingTable{" +
                "id=" + id +
                ", source=" + source +
                ", destination=" + destination +
                ", forwarder=" + forwarder +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutingTable that = (RoutingTable) o;
        return source == that.source && destination == that.destination && forwarder == that.forwarder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination, forwarder);
    }
}
