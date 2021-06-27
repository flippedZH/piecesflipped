package com.flipped.dto;

import lombok.Data;

/**
 * @Classname SinaToken
 * @Description TODO
 * @Date 2021/6/25 22:22
 * @Created by zh
 */


@Data
public class SinaToken {
    private  String access_token;
    private  String remind_in;
    private  String expires_in;
    private  String uid;
    private  String isRealName;
}
