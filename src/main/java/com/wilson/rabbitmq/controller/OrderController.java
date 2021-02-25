package com.wilson.rabbitmq.controller;

import com.wilson.rabbitmq.model.CreateOrderVo;
import com.wilson.rabbitmq.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @ResponseBody
    @RequestMapping("/list")
    public String sendDelayMessage( HttpServletRequest request) {
        CreateOrderVo order = new CreateOrderVo();
        orderService.createOrder(order);
        return "success";
    }
}
