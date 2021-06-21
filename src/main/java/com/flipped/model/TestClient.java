package com.flipped.model;

import com.flipped.utils.ClientUtils;
import com.flipped.utils.GetDemo;
import com.flipped.utils.SendDemo;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Classname TestClinet
 * @Description TODO
 * @Date 2021/6/20 16:12
 * @Created by zh
 */

public class TestClient  {
    private static String msg;

    public static void main(String[] args) {
        Socket socket=null;
        msg=null;
        try {
            socket = new Socket("localhost", 10086);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner=new Scanner(System.in);
        System.out.println(">");
        while (scanner.hasNext()){
            msg=scanner.nextLine();
            test.function(socket,msg);
        }
    }
}
class test{
    public  static  void function(Socket socket,String  msg){

//        SendDemo sendDemo=new SendDemo(socket,msg);
//        GetDemo getDemo=new GetDemo(socket);

//        Thread t1=new Thread(sendDemo);
//        Thread t2=new Thread(getDemo);
//
//        t1.start();
//        t2.start();
    }
}


