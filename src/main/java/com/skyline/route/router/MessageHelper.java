/**
 * FileName: MessageHelper
 * Author: 何锦川
 * Date: 2021/12/6 21:31
 * Description:
 * History:
 * <author>      <time>      <version>      <des>
 * 作者姓名      修改时间        版本号        描述
 */
package com.skyline.route.router;

import java.io.*;

/**
 * <功能概述>
 * <信息获取与转发>
 *
 * @author 何锦川
 * @create 2021/12/6 21:31
 * @since 1.0.0
 */
public class MessageHelper {

    /**
     * 接收信息文件，接收到后立即清空
     * */
    private File receiveFile;
    /**
     * 信息缓存
     */
    private StringBuilder receiveBuffer;

    public MessageHelper() {
    }

    /**
     * MessageHelper 构造， 每一个路由器对应一个 MessageHelper，构造时创建对应的文件区域<br/>
     * 构造时加载守护线程进行文件监听
     * @param filename 文件名，格式：routeReceive_[routeId].txt
     */
    public MessageHelper(String filename) {
        File receive = new File(filename);

        if (!receive.exists()) {
            try {
                receive.createNewFile();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        receiveFile = receive;

        // 清空文件内容
        clear();

    }

    /**
     * 清除接收文件内容
     */
    public void clear() {
        try {
            FileWriter fileWriter = new FileWriter(receiveFile, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("");
            bufferedWriter.flush();
            bufferedWriter.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取接收文件内容
     */
    public void read() {

        StringBuilder receive = new StringBuilder();

        try {
            FileReader fileReader = new FileReader(receiveFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // 按字节读取文件
            char[] chars = new char[1024];
            int length;
            while ((length = bufferedReader.read(chars)) != -1) {
                receive.append(new String(chars, 0, length));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        receiveBuffer = receive;

        // 清空接收文件
        clear();
    }

    /**
     * 将接收到的信息发送给转发路由，使用线程锁，避免并发读写导致信息传递混乱
     * @param forwarderId 转发路由id
     */
    public synchronized void send(int forwarderId) {
        try {
            // 找到对应的转发路由文件
            File forwarderFile = new File("routeReceive_" + forwarderId + ".txt");

            FileWriter fileWriter = new FileWriter(forwarderFile, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(receiveBuffer.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取目标路由id
     * @return 目标路由 id
     */
    public int getDestinationRouteId() {

        // 获取 缓存数据中的第一个字节，为 目标路由id
        return receiveBuffer.charAt(0);
    }

    public File getReceiveFile() {
        return receiveFile;
    }

    public void setReceiveFile(File receiveFile) {
        this.receiveFile = receiveFile;
    }

    public StringBuilder getReceiveBuffer() {
        return receiveBuffer;
    }

    public void setReceiveBuffer(StringBuilder receiveBuffer) {
        this.receiveBuffer = receiveBuffer;
    }
}

