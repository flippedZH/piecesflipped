package com.flipped.dto;

import lombok.Data;

/**
 * @Classname GithubUser
 * @Description TODO
 * @Date 2021/6/21 17:03
 * @Created by zh
 */

@Data
public class JsonUser {
    private String id;
    private String name;
    private String login;
    private String avatarUrl;
    private String profile_image_url;
}

