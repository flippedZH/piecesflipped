package com.flipped.utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Classname SendDemo
 * @Description TODO
 * @Date 2021/6/20 16:17
 * @Created by zh
 */
public class SendDemo implements Runnable {
    private Socket socket;
    private String msg;

    public SendDemo(Socket socket, String msg) {
        this.socket = socket;
        this.msg = msg;
    }

    @Override
    public void run(){
        doSend();
    }

    public void doSend() {
        try {
            PrintWriter pw= new PrintWriter(socket.getOutputStream());
            ;//将输出流包装为打印流
            pw.write(msg);
            System.out.println("发送："+msg);
            pw.flush();
        } catch (IOException e) {
            System.out.println("输出流异常");
        }
    }

//    public static JSONObject myToJson(String s) {
//        JSONObject jsonObject = new JSONObject();
//        String[] trim = s.trim().split(" ");
//        jsonObject.put("x", trim[0]);
//        jsonObject.put("y", trim[1]);
//        jsonObject.put("color", trim[2]);
//        return jsonObject;
//    }

}
