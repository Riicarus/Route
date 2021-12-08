package com.skyline.route.core;


import java.util.*;

public class Dijkstra {

    /**
     * 所有节点
     * */
    private final HashMap<Integer, Node> nodeMap = new HashMap<>();
    /**
     * 遍历过的节点
     */
    private final Set<Node> searched = new HashSet<>();
    /**
     * 未遍历的节点
     * */
    private final Set<Node> unsearched = new HashSet<>();
    /**
     * 最短路径距离
     * */
    private final Map<Integer, Integer> pathDistanceMap = new HashMap<>();
    /**
     * 最短路径经过的节点
     * */
    private final Map<Integer, ArrayList<Integer>> pathInfoMap = new HashMap<>();
    /**
     * 邻接矩阵
     */
    private final int[][] adjacentMatrix;

    public Dijkstra(int[][] adjacentMatrix) {
        this.adjacentMatrix = adjacentMatrix;
        Node startNode = buildMap(searched, unsearched, adjacentMatrix);
        computePath(startNode);
        createPathMap();
    }

    /**
     * 计算最短路径
     * @param startNode 初始节点
     */
    private void computePath(Node startNode) {
        int minDis = Integer.MAX_VALUE;

        Node nextStartNode = null;

        // 被遍历完全，退出
        if (unsearched.isEmpty()) {
            return;
        }

        // 找到距离初始节点最近的节点，记录为下一次的初始节点
        for (Node node : unsearched) {
            if (minDis > node.getDistance()) {
                minDis = node.getDistance();
                nextStartNode = node;
            }
        }

        // 将找到的节点放入 searched
        searched.add(nextStartNode);
        unsearched.remove(nextStartNode);

        // 更新 unsearched 中的节点到最初始节点的距离
        for (Node node : unsearched) {
            // 如果 初始节点到最初始节点的距离+初始节点到该节点的的距离 < 该节点到最初始节点的距离，更新其到最初始节点的距离，更新父节点为初始节点
            int newDistance = startNode.getDistance() + adjacentMatrix[startNode.getId()][node.getId()];
            if (newDistance > 0 && newDistance < node.getDistance()) {
                node.setDistance(newDistance);
                node.setParentNode(startNode);
            }
        }

        // 递归进行下一个初始节点的遍历
        computePath(nextStartNode);
    }

    /**
     * 根据计算出的最短距离和父节点法分析出最短路径
     */
    private void createPathMap() {
        for (Integer nodeId : nodeMap.keySet()) {
            Node node = nodeMap.get(nodeId);
            pathDistanceMap.put(nodeId, node.getDistance());

            ArrayList<Integer> pathInfo = new ArrayList<>();
            pathInfo.add(nodeId);
            while (node.getParentNode().getId() != 0) {
                pathInfo.add(node.getParentNode().getId());
                node = node.getParentNode();
            }
            pathInfo.add(0);
            Collections.reverse(pathInfo);
            pathInfoMap.put(nodeId, pathInfo);
        }
    }

    /**
     * 初始化所有子节点
     * @param searched 未被遍历的节点集合
     * @param unsearched 已经被遍历的节点集合
     * @param adjacentMatrix 邻接矩阵
     * @return 路由图起始节点
     */
    private Node buildMap(Set<Node> searched, Set<Node> unsearched, int[][] adjacentMatrix) {

        Node startNode = new Node(0, null, adjacentMatrix[0][0]);
        startNode.setParentNode(startNode);

        nodeMap.put(0, startNode);
        // 第一个节点设置为已遍历
        searched.add(startNode);

        // 生成所有节点
        for (int i = 1; i < adjacentMatrix[0].length; i++) {
            Node node = new Node(i, nodeMap.get(0), adjacentMatrix[0][i]);
            nodeMap.put(i, node);
            // 设置为未遍历
            unsearched.add(node);
        }

        return startNode;
    }

    public Map<Integer, Integer> getPathDistanceMap() {
        return pathDistanceMap;
    }

    public Map<Integer, ArrayList<Integer>> getPathInfoMap() {
        return pathInfoMap;
    }
}

