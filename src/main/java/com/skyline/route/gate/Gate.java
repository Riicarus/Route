/**
 * FileName: Gate
 * Author: 何锦川
 * Date: 2021/12/7 19:51
 * Description: 网关
 * History:
 * <author>      <time>      <version>      <des>
 * 作者姓名      修改时间        版本号        描述
 */
package com.skyline.route.gate;

import com.skyline.route.core.Dijkstra;
import com.skyline.route.factory.MyRouterFactory;
import com.skyline.route.mapper.ConnectionMapper;
import com.skyline.route.mapper.RoutingMapper;
import com.skyline.route.router.ConnectionTable;
import com.skyline.route.router.Router;
import com.skyline.route.router.RoutingTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <功能概述><br/>
 * <网关，假定网关之和编号为 1 的路由器存在连接><br/>
 * <接收外界请求，找到最短路径，通过最短路径发送给路由器>
 *
 * @author 何锦川
 * @create 2021/12/7 19:51
 * @since 1.0.0
 */
@Component
public class Gate {

    /**
     * 路由表数据库操作对象
     */
    private RoutingMapper routingMapper;
    /**
     * 连接表数据库操作对象
     */
    private ConnectionMapper connectionMapper;
    /**
     * 路由器工厂，用于获取编号为 1 的路由器
     */
    private MyRouterFactory myRouterFactory;
    /**
     * 所有路由表
     */
    private final List<RoutingTable> routingTables = new ArrayList<>();
    /**
     * 编号为 1 的路由器
     */
    private Router sourceRouter;

    public Gate() {
    }

    @Autowired
    public Gate(RoutingMapper routingMapper, ConnectionMapper connectionMapper, MyRouterFactory myRouterFactory) {
        this.routingMapper = routingMapper;
        this.connectionMapper = connectionMapper;
        this.myRouterFactory = myRouterFactory;
        this.sourceRouter = myRouterFactory.getRouter(1);
        init();
    }

    /**
     * 初始化所有路由表
     */
    public void init() {
        analyzeRoutingTable();
        saveRoutingToDatabase();
    }

    /**
     * 分析所有的连接表，生成路由表
     */
    public void analyzeRoutingTable() {
        int[][] adjacentMatrix = castToAdjacentMatrix();

        // 获取最短路径
        Dijkstra dijkstra = new Dijkstra(adjacentMatrix);
        Map<Integer, ArrayList<Integer>> pathInfoMap = dijkstra.getPathInfoMap();

        for (Integer routeId : pathInfoMap.keySet()) {

            // 自己到自己的路由表
            RoutingTable selfRoutingTable = new RoutingTable(routeId + 1, routeId + 1, routeId + 1);
            routingTables.add(selfRoutingTable);

            ArrayList<Integer> pathInfo = pathInfoMap.get(routeId);
            for (int i = 0; i < pathInfo.size() - 1; i++) {
                for (int j = i + 1; j < pathInfo.size(); j++) {
                    // 路由器id从 1 开始
                    RoutingTable routingTable = new RoutingTable(pathInfo.get(i) + 1, pathInfo.get(j) + 1, pathInfo.get(i + 1) + 1);
                    if (!routingTables.contains(routingTable)) {
                        routingTables.add(routingTable);
                    }
                }
            }
        }

    }

    /**
     * 保存路由表到数据库
     */
    public void saveRoutingToDatabase() {
        for (RoutingTable routingTable : routingTables) {
            routingMapper.setRouting(routingTable);
        }
    }

    /**
     * 根据连接表生成路由图的邻接矩阵
     * @return 路由图的邻接矩阵
     */
    public int[][] castToAdjacentMatrix() {
        int routerCount = myRouterFactory.getRouters().size();
        int[][] adjacentMatrix = new int[routerCount][routerCount];

        for (int i = 0; i < routerCount; i++) {
            // 获取源路由对应的连接表
            List<ConnectionTable> connectionTables = connectionMapper.getConnection(i + 1);
            for (int j = 0; j < routerCount; j++) {
                if (i == j) {
                    adjacentMatrix[i][j] = 0;
                }
            }
            for (ConnectionTable connectionTable : connectionTables) {
                int cost = connectionTable.getCost();
                adjacentMatrix[connectionTable.getSource() - 1][connectionTable.getDestination() - 1] = (cost == -1 ? Integer.MAX_VALUE : cost);
            }
        }
        return adjacentMatrix;
    }

    /**
     * 将信息发送给路由器
     * @param destinationId 目标路由
     * @param message 信息
     * @return 发送结果
     */
    public String send(int destinationId, String message) {
        try {
            // 获取编号为 1 的路由的接收文件
            File sourceRouterFile = sourceRouter.getMessageHelper().getReceiveFile();
            
            // 拼接消息
            String messageSend = (char)destinationId + message;
            
            // 将消息写入文件
            FileWriter fileWriter = new FileWriter(sourceRouterFile, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(messageSend);
            bufferedWriter.flush();
            bufferedWriter.close();
            
        }catch (Exception e) {
            e.printStackTrace();
            return "发送失败";
        }
        
        return "发送成功";
    }
}
