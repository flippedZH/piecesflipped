package com.flipped.provider;

import com.alibaba.fastjson.JSONObject;
import com.flipped.dto.*;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Classname SinaProvider
 * @Description TODO
 * @Date 2021/6/25 21:02
 * @Created by zh
 */

//https://api.weibo.com/oauth2/access_token?client_id=914376252&client_secret=d554b741ae3a50fbfc851ca7f68dd2ff&grant_type=authorization_code&redirect_uri=http://127.0.0.1:8080/loginBySina&code=bfb9188ae6ad13e6f0ba1a17a4fa3d50

@Component
public class SinaProvider {

    public String getToken(SinaParamDto sinaParamDto){
        String url="https://api.weibo.com/oauth2/access_token?client_id=914376252&client_secret=d554b741ae3a50fbfc851ca7f68dd2ff&" +
                "grant_type=authorization_code&redirect_uri=http://127.0.0.1:8080/loginBySina&code="+sinaParamDto.getCode();
        //post请求(携带大量参数) 获取Token
        MediaType mediaTypeJson = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaTypeJson, JSONObject.toJSONString(sinaParamDto));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        System.out.println(request);
        try (Response response = client.newCall(request).execute()) {
            String res=response.body().string();
            System.out.println("res:"+res);
            SinaToken sinaToken = JSONObject.parseObject(res, SinaToken.class);
            System.out.println(sinaToken);
            String token=sinaToken.getAccess_token();
            System.out.println("token:"+token);
            return token;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public String  getUid(String token){
        //post请求(携带大量参数) 获取Token
        MediaType mediaTypeJson = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaTypeJson, "");
        Request request = new Request.Builder()
                .url("https://api.weibo.com/oauth2/get_token_info?access_token="+token)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String res=response.body().string();
            SinaUid sinaUid = JSONObject.parseObject(res, SinaUid.class);
            return sinaUid.getUId();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    //https://api.weibo.com/2/users/show.json?access_token=2.00YBikvB0ECdszb949f5cd85_zg23D&uid=1769516304
    public JsonUser getJsonUser(String token){
        //get请求（携带token） 获取 用户信息
        OkHttpClient client = new OkHttpClient();
        String uid=getUid(token);
        System.out.println("uid:"+uid);
        Request request = new Request.Builder()
                .url("https://api.weibo.com/2/users/show.json?access_token="+token+"&uid="+uid)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String res= response.body().string();
            System.out.println("res:"+res);
            JsonUser sinaUser=JSONObject.parseObject(res,JsonUser.class);
            System.out.println("sinaUser："+sinaUser);

            return sinaUser;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
