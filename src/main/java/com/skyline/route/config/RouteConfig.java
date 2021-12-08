/**
 * FileName: RouteConfig
 * Author: 何锦川
 * Date: 2021/12/6 21:09
 * Description:
 * History:
 * <author>      <time>      <version>      <des>
 * 作者姓名      修改时间        版本号        描述
 */
package com.skyline.route.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <功能概述>
 * <路由器配置>
 *
 * @author 何锦川
 * @create 2021/12/6 21:09
 * @since 1.0.0
 */
@Configuration
@ConfigurationProperties(prefix = "config.route")
public class RouteConfig {

    /**
     * 初始化路由器数量
     * */
    private int routeCount;

    public RouteConfig() {
    }

    public RouteConfig(int routeCount) {
        this.routeCount = routeCount;
    }

    public int getRouteCount() {
        return routeCount;
    }

    public void setRouteCount(int routeCount) {
        this.routeCount = routeCount;
    }
}
