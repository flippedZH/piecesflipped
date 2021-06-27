package com.flipped.dto;

import lombok.Data;

/**
 * @Classname GiteeParamDto
 * @Description TODO
 * @Date 2021/6/23 15:39
 * @Created by zh
 */


@Data
public class GiteeParamDto {
    private  String grant_type="authorization_code";
    private  String code;
    private  String client_id;
    private  String redirect_uri;
    private  String client_secret;
}
