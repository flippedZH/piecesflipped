package com.flipped.provider;

import com.alibaba.fastjson.JSONObject;
import com.flipped.dto.GiteeParamDto;
import com.flipped.dto.GiteeToken;
import com.flipped.dto.IdentityParamDto;
import com.flipped.dto.JsonUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Classname GiteeProvider
 * @Description TODO
 * @Date 2021/6/23 14:53
 * @Created by zh
 */
@Component
public class GiteeProvider {

    public String getToken(GiteeParamDto giteeParamDto){
        //post请求(携带大量参数) 获取Token
        MediaType mediaTypeJson = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaTypeJson, JSONObject.toJSONString(giteeParamDto));
        System.out.println("body:"+JSONObject.toJSONString(giteeParamDto));
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token")
                .post(body)
                .build();
        System.out.println("request:"+request);
        try (Response response = client.newCall(request).execute()) {
            String res=response.body().string();
            GiteeToken giteeToken = JSONObject.parseObject(res, GiteeToken.class);
            String token=giteeToken.getAccess_token();
            return token;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public JsonUser getJsonUser(String token){
        //get请求（携带token） 获取 用户信息
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token="+token)
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
