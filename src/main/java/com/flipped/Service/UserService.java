package com.flipped.Service;

import com.flipped.dto.JsonUser;
import com.flipped.mapper.UserMapper;
import com.flipped.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Classname UserService
 * @Description TODO
 * @Date 2021/6/27 10:34
 * @Created by zh
 */





@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    //存入数据库
    public User updateOrCreateUser(JsonUser jsonUser){
        User user=new User();
        user.setName(jsonUser.getName());
        //登录一次 创建一个uuid
        user.setToken(UUID.randomUUID().toString());
        user.setAccountId(jsonUser.getId());

        User userById = userMapper.findUserById(jsonUser.getId());
        if(jsonUser.getAvatarUrl()!=null){
            user.setAvatarUrl(jsonUser.getAvatarUrl());
        }else {
            user.setAvatarUrl(jsonUser.getProfile_image_url());
        }
        if(userById!=null){
            //更新用户
            //假的 ：数据库有了就认为更改了
            user.setGmtModified(System.currentTimeMillis());
            userMapper.updateUser(user);
        }else {
            //创建新用户
            user.setGmtCreate(System.currentTimeMillis());
            userMapper.insertUser(user);
        }
        System.out.println("user:"+user);
        return user;
    }
}
