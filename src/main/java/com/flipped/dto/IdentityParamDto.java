package com.flipped.dto;

import lombok.Data;

/**
 * @Classname IdentityParamDto
 * @Description TODO
 * @Date 2021/6/21 17:08
 * @Created by zh
 */

@Data
public class IdentityParamDto {
    private String  client_id;
    private String  client_secret;
    private String  code;
    private String  redirect_uri;
    private String  state;
}
