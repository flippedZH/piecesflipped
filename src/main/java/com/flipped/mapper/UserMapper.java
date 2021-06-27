package com.flipped.mapper;

import com.flipped.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import javax.servlet.http.Cookie;

/**
 * @Classname UserMapper
 * @Description TODO
 * @Date 2021/6/21 22:52
 * @Created by zh
 */

@Mapper
@Repository
public  interface  UserMapper {

    @Insert("INSERT INTO `github_users`  (`user_id`,`login`,`name`) VALUES (#{userId},#{login},#{name})")
    public void addUser(User user);

    @Select("SELECT * FROM `github_users` where user_id=#{userId}")
    public boolean selectUser(User user);

    @Select("select * from  `user` where account_id=#{accountId}")
    User findUserById(String accountId);

    @Update("update `user` set  `name`=#{name},`gmtModified`=#{gmtModified},`avatar_url`=#{avatarUrl},`token`=#{token} where `account_id`=#{accountId}")
    void updateUser(User user);

    @Insert("insert into `user` (account_id,name,token,gmtCreate,avatar_url) values (#{accountId},#{name},#{token},#{gmtCreate},#{avatarUrl})")
    void insertUser(User user);

    @Select("select * from  `user` where token=#{token}")
    User findUserByToken(String token);
}
