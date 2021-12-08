/**
 * FileName: Router
 * Author: 何锦川
 * Date: 2021/12/6 17:56
 * Description: 路由
 * History:
 * <author>      <time>      <version>      <des>
 * 作者姓名      修改时间        版本号        描述
 */
package com.skyline.route.router;

import java.io.File;

/**
 * <功能概述>
 * <路由>
 *
 * @author 何锦川
 * @create 2021/12/6 17:56
 * @since 1.0.0
 */
public class Router {

    /**
     * 路由器编号
     * */
    private int routerId;
    /**
     * 连接表适配器
     * */
    private ConnectionTableConfigurator connectionTableConfigurator;
    /**
     * 路由表适配器
     * */
    private RoutingTableConfigurator routingTableConfigurator;
    /**
     * 文件信息收发器
     */
    private MessageHelper messageHelper;

    public Router() {
    }

    public Router(int routerId, ConnectionTableConfigurator connectionTableConfigurator, RoutingTableConfigurator routingTableConfigurator, MessageHelper messageHelper) {
        this.routerId = routerId;
        this.connectionTableConfigurator = connectionTableConfigurator;
        this.routingTableConfigurator = routingTableConfigurator;
        this.messageHelper = messageHelper;

        // 加载守护线程
        RunnableMessageListener messageListener = new RunnableMessageListener(routerId, messageHelper, routingTableConfigurator);
        Thread messageListenerThread = new Thread(messageListener);
        messageListenerThread.setName("MessageListenerThread_" + routerId);
        messageListenerThread.start();
    }

    public int getRouterId() {
        return routerId;
    }

    public void setRouterId(int routerId) {
        this.routerId = routerId;
    }

    public ConnectionTableConfigurator getConnectionTableConfigurator() {
        return connectionTableConfigurator;
    }

    public void setConnectionTableConfigurator(ConnectionTableConfigurator connectionTableConfigurator) {
        this.connectionTableConfigurator = connectionTableConfigurator;
    }

    public RoutingTableConfigurator getRoutingTableConfigurator() {
        return routingTableConfigurator;
    }

    public void setRoutingTableConfigurator(RoutingTableConfigurator routingTableConfigurator) {
        this.routingTableConfigurator = routingTableConfigurator;
    }

    public MessageHelper getMessageHelper() {
        return messageHelper;
    }

    public void setMessageHelper(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    @Override
    public String toString() {
        return "Router{" +
                "routerId=" + routerId +
                ", connectionTableConfigurator=" + connectionTableConfigurator +
                ", routingTableConfigurator=" + routingTableConfigurator +
                '}';
    }
}


/**
 * 守护线程，监听文件是否发生变化
 * */
class RunnableMessageListener implements Runnable {

    /**
     * 源路由id
     * */
    private final int routerId;
    /**
     * 文件信息收发器
     * */
    private final MessageHelper messageHelper;
    /**
     * 路由表适配器
     * */
    private final RoutingTableConfigurator routingTableConfigurator;
    /**
     * 线程运行控制
     * */
    boolean run = true;

    public RunnableMessageListener(int routerId, MessageHelper messageHelper, RoutingTableConfigurator routingTableConfigurator) {
        this.routerId = routerId;
        this.messageHelper = messageHelper;
        this.routingTableConfigurator = routingTableConfigurator;
    }

    @Override
    public void run() {
        while (run) {
            // 根据路由表，转发
            if (messageHelper.getReceiveFile().length() >= 2) {
                messageHelper.read();
                int destinationId = messageHelper.getDestinationRouteId();

                // 打印信息
                if (destinationId == routerId) {
                    System.out.println("ROUTER_" + routerId + "_GET MESSAGE:");
                }else {
                    System.out.println("ROUTER_" + routerId + "_FORWARD MESSAGE:");
                }
                System.out.println(messageHelper.getReceiveBuffer().substring(1));

                // 转发
                int forwarderId = routingTableConfigurator.getForwarderId(routerId, destinationId);
                messageHelper.send(forwarderId);

                // 再读一次文件使缓存为空
                messageHelper.read();
            }
        }
    }

    public void setRun(boolean run) {
        this.run = run;
    }
}
