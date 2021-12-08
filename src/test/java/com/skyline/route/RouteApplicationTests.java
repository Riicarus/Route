package com.skyline.route;

import com.skyline.route.factory.MyRouterFactory;
import com.skyline.route.gate.Gate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class RouteApplicationTests {

    private MyRouterFactory myRouterFactory;
    private Gate gate;

    @Autowired
    public void setMyRouterFactory(MyRouterFactory myRouterFactory) {
        this.myRouterFactory = myRouterFactory;
    }

    @Autowired
    public void setGate(Gate gate) {
        this.gate = gate;
    }


    @Test
    void contextLoads() {
        System.out.println(myRouterFactory.getRouters().toString());
        System.out.println(Arrays.deepToString(gate.castToAdjacentMatrix()));
    }

}
