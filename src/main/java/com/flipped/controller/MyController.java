package com.flipped.controller;

import com.alibaba.fastjson.JSON;
import com.flipped.dto.Pie;
import com.flipped.model.AsyncThread;

import com.flipped.utils.ClientModel;
import com.flipped.utils.GetDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
/**
 * 问题：
 * 消息阻塞：并非阻塞 ，由于线程执行顺序的问题
 * 引擎结果时延的问题: 机智的人为解决（清醒）
 * 前端报错不显示:低级错误 ,最后传过去的不是Json类型的字符串（Json类型的字符串还是与普通字符串有区别）
 * **/


/**
 * @Classname HelloWorld
 * @Description TODO
 * @Date 2021/6/17 17:27
 * @Created by zh
 */

@Controller
public class MyController {
    private Socket socket;
    private String lastMsg;

    @Autowired
    AsyncThread asyncThread;

    @RequestMapping("/")
    public  String homepage() {
        return "index";
    }

    @RequestMapping(value = "/mouseDown",method = RequestMethod.POST)
    @ResponseBody
    public String test_post(@RequestBody Pie pie) throws AWTException, InterruptedException {

        System.out.println();
        //之前的消息
        lastMsg  = GetDemo.getRes();
        String msg = JSON.toJSONString(pie);
        ClientModel clientModel=new ClientModel();
        if(socket!=null){
            clientModel.doExchange(socket, msg);
            while (true){
                String res = GetDemo.getRes();
                if(res!=null&&!res.equals(lastMsg)){
                    System.out.println("到前端："+ res);
                    return JSON.toJSONString(res);//返回java对象与json对象都可以 ，@ResponseBody自动将其转换为jsoN对象
                }
            }
        }else
            return "";
    }

    @ResponseBody
    @RequestMapping("/ai")
    public  void  aiPlay ()
    {
        socket=null;
        try {
            socket = new Socket("localhost", 10086);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("接入ai!");
    }

    @RequestMapping("/test")
    public  String test() {
        return "index_test";
    }
}

