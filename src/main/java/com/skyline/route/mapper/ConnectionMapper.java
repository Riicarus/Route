/**
 * FileName: ConnectionMapper
 * Author: 何锦川
 * Date: 2021/12/6 18:32
 * Description: 数据库连接表连接表
 * History:
 * <author>      <time>      <version>      <des>
 * 作者姓名      修改时间        版本号        描述
 */
package com.skyline.route.mapper;

import com.skyline.route.router.ConnectionTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <功能概述>
 * <数据库连接表>
 *
 * @author 何锦川
 * @create 2021/12/6 18:32
 * @since 1.0.0
 */
@Repository
public interface ConnectionMapper {

    /**
     * 保存连接表
     * @param connection 连接表
     */
    void setConnection(ConnectionTable connection);

    /**
     * 获取连接表
     * @param sourceId 源路由id
     * @return 源路由对应的连接表
     */
    List<ConnectionTable> getConnection(@Param("sourceId") int sourceId);

    /**
     * 获取所有连接表
     * @return 所有连接表
     */
    List<ConnectionTable> getAll();

}
