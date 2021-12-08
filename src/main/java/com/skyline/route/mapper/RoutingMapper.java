/**
 * FileName: RoutingMapper
 * Author: 何锦川
 * Date: 2021/12/6 19:14
 * Description: 路由表操作对象
 * History:
 * <author>      <time>      <version>      <des>
 * 作者姓名      修改时间        版本号        描述
 */
package com.skyline.route.mapper;

import com.skyline.route.router.RoutingTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <功能概述>
 * <路由表操作对象>
 *
 * @author 何锦川
 * @create 2021/12/6 19:14
 * @since 1.0.0
 */
@Repository
public interface RoutingMapper {

    /**
     * 保存路由表
     * @param routingTable 路由表
     */
    void setRouting(RoutingTable routingTable);

    /**
     * 获取该路由器所有的路由表
     * @param sourceId 源路由id
     * @return 该路由器所有的路由表
     */
    List<RoutingTable> getRoutingTable(@Param("sourceId") int sourceId);

    /**
     * 获取转发路由id
     * @param sourceId 源路由id
     * @param destinationId 目标路由id
     * @return 转发路由id
     */
    int getForwarderId(@Param("sourceId") int sourceId, @Param("destinationId") int destinationId);

}
