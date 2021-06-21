package com.flipped.utils;

import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @Classname GetDemo
 * @Description TODO
 * @Date 2021/6/20 16:20
 * @Created by zh
 */

@Data
public class GetDemo  implements Runnable {
    private Socket socket;
    private BufferedReader bufferedReader = null;
    private static String res;

    public GetDemo(Socket socket) {
        this.socket = socket;
    }

    public static String getRes() {
        return res;
    }

    public static void setRes(String res) {
        GetDemo.res = res;
    }

    @Override
    public void run() {
        doGet();
    }

    public void doGet(){
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            res = bufferedReader.readLine();
            if(res!=null)System.out.println("收到：" + res);
        } catch (IOException e) {
            System.out.println("输入流异常");
        }
    }

}
