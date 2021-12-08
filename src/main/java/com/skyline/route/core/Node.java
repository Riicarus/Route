/**
 * FileName: Node
 * Author: 何锦川
 * Date: 2021/12/7 23:24
 * Description:
 * History:
 * <author>      <time>      <version>      <des>
 * 作者姓名      修改时间        版本号        描述
 */
package com.skyline.route.core;


/**
 * 子节点
 */
public class Node {

    private int id;
    private Node parentNode;
    private int distance;

    public Node(int id, Node parentNode, int distance) {
        this.id = id;
        this.parentNode = parentNode;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", parentNode=" + parentNode.getId() +
                ", distance=" + distance +
                "}\n";
    }
}
