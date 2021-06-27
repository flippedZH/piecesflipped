package com.flipped.dto;

import lombok.Data;

/**
 * @Classname SinaParamDto
 * @Description TODO
 * @Date 2021/6/25 21:03
 * @Created by zh
 */


//https://api.weibo.com/oauth2/access_token?client_id=YOUR_CLIENT_ID&client_secret=YOUR_CLIENT_SECRET&grant_type=authorization_code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI&code=CODE


@Data
public class SinaParamDto {
    private  String grant_type="authorization_code";
    private  String code;
    private  String client_id;
    private  String redirect_uri;
    private  String client_secret;
}
