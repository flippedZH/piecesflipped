package com.flipped.model;

import com.alibaba.fastjson.JSONObject;
import com.flipped.utils.ClientUtils;
import com.flipped.utils.GetDemo;
import com.flipped.utils.SendDemo;
import lombok.Data;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Classname asybc
 * @Description TODO
 * @Date 2021/6/19 15:47
 * @Created by zh
 */

@Data
@Component
public class AsyncThread {
   private static String  msg;

    @Async
    public void run(){
        System.out.println("异步线程:");
//        String msg=clientModel.getGetDemo().getMsg();
//        System.out.println("到前端："+msg);
    }
}

