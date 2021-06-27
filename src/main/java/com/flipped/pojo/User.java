package com.flipped.pojo;

import lombok.Data;

/**
 * @Classname User
 * @Description TODO
 * @Date 2021/6/21 23:30
 * @Created by zh
 */

@Data
public class User {
    private Long id;
    private String accountId;
    private String name;
    private String token;
    private String bio;
    private String avatarUrl;
    private Long gmtCreate;
    private Long gmtModified;
}
