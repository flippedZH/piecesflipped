package com.flipped.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Classname NewClient
 * @Description TODO
 * @Date 2021/6/20 9:57
 * @Created by zh
 */

@Data
public class NewClient {

    private String  requestPos;
    private String responsePos;

    public NewClient(String requestPos, String responsePos) {
        this.requestPos = requestPos;
        this.responsePos = responsePos;
    }

    public static  void runClient(){
        Socket socket=connect();
        Send send=new Send(socket);
        Listen listen=new Listen(socket);

        Thread t1=new Thread(send);
        Thread t2=new Thread(listen);

        t1.start();
        t2.start();
    }

    public static Socket  connect(){
        Socket socket=null;
        try {
           socket = new Socket("localhost", 10086);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }
}

class Send implements Runnable{
    private Socket socket;

    public Send(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        PrintWriter pw= null;//将输出流包装为打印流
        try {
            pw = new PrintWriter(socket.getOutputStream());
            Scanner sc=new Scanner(System.in);
            System.out.println("输入：");
            String s=null;
            while (sc.hasNext()){
                s=sc.nextLine();
                pw.write(myToJson(s).toJSONString());
                pw.flush();
            }
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

    public JSONObject myToJson(String s) {
        JSONObject jsonObject = new JSONObject();
        String[] trim = s.trim().split(" ");
        jsonObject.put("x", trim[0]);
        jsonObject.put("y", trim[1]);
        jsonObject.put("color", trim[2]);
        return jsonObject;
    }

}

class Listen implements Runnable{
    private Socket socket;

    public Listen (Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        String msg = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((msg = bufferedReader.readLine()) != null) {
                System.out.println("收到：" + msg);
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
    }
}



