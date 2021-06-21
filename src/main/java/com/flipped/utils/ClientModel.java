package com.flipped.utils;

import lombok.Data;

import java.io.IOException;
import java.net.Socket;

/**
 * @Classname ClientMdoel
 * @Description TODO
 * @Date 2021/6/20 17:31
 * @Created by zh
 */

@Data
public class ClientModel {
    private  SendDemo sendDemo;
    private  GetDemo getDemo;

    public  void doExchange(Socket socket,String msg){
        sendDemo=new SendDemo(socket,msg);
        getDemo=new GetDemo(socket);

        Thread t1=new Thread(sendDemo);
        Thread t2=new Thread(getDemo);

        t1.start();
        t2.start();
    }
}
