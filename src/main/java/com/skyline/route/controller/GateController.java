/**
 * FileName: GateController
 * Author: 何锦川
 * Date: 2021/12/7 20:20
 * Description:
 * History:
 * <author>      <time>      <version>      <des>
 * 作者姓名      修改时间        版本号        描述
 */
package com.skyline.route.controller;

import com.skyline.route.gate.Gate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <功能概述>
 * <网关请求处理接口>
 *
 * @author 何锦川
 * @create 2021/12/7 20:20
 * @since 1.0.0
 */
@RestController
public class GateController {

    private Gate gate;

    @Autowired
    public void setGate(Gate gate) {
        this.gate = gate;
    }

    @RequestMapping("/api/send/{destinationId}/{message}")
    public String send(
            @PathVariable("destinationId") int destinationId,
            @PathVariable("message") String message
    ) {
        return gate.send(destinationId, message);
    }
}
