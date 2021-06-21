package com.flipped.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Classname ClientUtils
 * @Description TODO
 * @Date 2021/6/20 14:39
 * @Created by zh
 */


public class ClientUtils {
    public static Socket connect(){
        Socket socket=null;
        try {
            socket = new Socket("localhost", 10086);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }
    public  void sendMsg(Socket socket,String s){
        PrintWriter pw= null;//将输出流包装为打印流
        try {
            pw = new PrintWriter(socket.getOutputStream());
            pw.write(myToJson(s).toJSONString());
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                System.out.println("断开2");
            } catch (IOException e) {
                e.printStackTrace();
            }
            pw.close();
        }
    }


    public  String  getMsg(Socket socket){
        BufferedReader bufferedReader = null;
        String msg = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            msg = bufferedReader.readLine();
            if(msg!=null){
                System.out.println("收到：" + msg);
                return msg;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
                System.out.println("断开1");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static JSONObject myToJson(String s) {
        JSONObject jsonObject = new JSONObject();
        String[] trim = s.trim().split(" ");
        jsonObject.put("x", trim[0]);
        jsonObject.put("y", trim[1]);
        jsonObject.put("color", trim[2]);
        return jsonObject;
    }

}


