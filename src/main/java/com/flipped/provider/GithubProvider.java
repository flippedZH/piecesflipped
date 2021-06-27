package com.flipped.provider;

import com.alibaba.fastjson.JSONObject;
import com.flipped.dto.IdentityParamDto;
import com.flipped.dto.JsonUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Classname GithubProvider
 * @Description TODO
 * @Date 2021/6/21 17:19
 * @Created by zh
 */

//与github交互的逻辑

@Component
public class GithubProvider {

    //通过携带 code的参数 来发送请求  从github获取token
    public String getToken(IdentityParamDto identityParamDto){

        //post请求(携带大量参数) 获取Token
        MediaType mediaTypeJson = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaTypeJson, JSONObject.toJSONString(identityParamDto));
        System.out.println("body:"+JSONObject.toJSONString(identityParamDto));
        Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String res=response.body().string();
                String s=res.split("&")[0].split("=")[1];
                System.out.println(s);
                return s;
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
    }

    public JsonUser getGithubUser(String token){
        //get请求（携带token） 获取 用户信息
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token=")
                    .header("Authorization","token "+token)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String res= response.body().string();
                System.out.println("res:"+res);
                JsonUser githubUser=JSONObject.parseObject(res,JsonUser.class);
                System.out.println("githubUser："+githubUser);
                return githubUser;
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
    }
}
