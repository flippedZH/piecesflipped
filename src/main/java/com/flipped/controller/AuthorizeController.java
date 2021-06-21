package com.flipped.controller;

import com.flipped.dto.GithubUser;
import com.flipped.dto.IdentityParamDto;
import com.flipped.provider.GithubProvider;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Classname AuthorizeController
 * @Description TODO
 * @Date 2021/6/21 14:25
 * @Created by zh
 */
/**
1、访问github的地址，接收返回的code值（）
 2、
 */


@Controller
public class AuthorizeController{

    @Autowired
    private GithubProvider githubProvider;

    @Value("${Client_id}")
    private  String clientId;

    @Value("${Client_secret}")
    private  String clientSecret;

    @RequestMapping("/redirect")
    public  String redirect(
            @RequestParam(name = "code") String code, @RequestParam("state")
            String state, HttpServletRequest request){
        IdentityParamDto identityParamDto =new IdentityParamDto();
        identityParamDto.setCode(code);
        identityParamDto.setState(state);
        identityParamDto.setClient_id(clientId);
        identityParamDto.setClient_secret(clientSecret);
        identityParamDto.setRedirect_uri("http://localhost:8080/redirect");

        String token = githubProvider.getToken(identityParamDto);
        GithubUser githubUser = githubProvider.getGithubUser(token);
        System.out.println(githubUser);
        if(githubUser!=null){
            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";  //这种是包含地址与页面的重定向
        }else{
            return "redirect:/";
        }
    }
}
