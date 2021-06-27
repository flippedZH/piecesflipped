package com.flipped.controller;

import com.flipped.Service.UserService;
import com.flipped.dto.GiteeParamDto;
import com.flipped.dto.IdentityParamDto;
import com.flipped.dto.JsonUser;
import com.flipped.dto.SinaParamDto;
import com.flipped.mapper.UserMapper;
import com.flipped.pojo.User;
import com.flipped.provider.GiteeProvider;
import com.flipped.provider.GithubProvider;
import com.flipped.provider.SinaProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SinaProvider sinaProvider;
    @Autowired
    private GiteeProvider giteeProvider;
    @Autowired
    private UserService userService;

    @Value("${github.client.id}")
    private  String clientId;
    @Value("${github.client.secret}")
    private  String clientSecret;

    @Value("${sina.id}")
    private String sinaId;
    @Value("${sina.secret}")
    private String sinaSecret;


    //github登陆
    @RequestMapping("/redirect")
    public  String redirect(
            @RequestParam(name = "code") String code, @RequestParam("state")
            String state,  HttpServletResponse response){
        IdentityParamDto identityParamDto =new IdentityParamDto();
        identityParamDto.setCode(code);
        identityParamDto.setState(state);
        identityParamDto.setClient_id(clientId);
        identityParamDto.setClient_secret(clientSecret);
        identityParamDto.setRedirect_uri("http://localhost:8080/redirect");
        String token = githubProvider.getToken(identityParamDto);

        //JsonUser是从网站拿到的用户信息 user 是本地用户信息 模型
        JsonUser jsonUser= githubProvider.getGithubUser(token);
        System.out.println(jsonUser);

       //JsonUser保存到数据库
        User user = userService.updateOrCreateUser(jsonUser);

        Cookie cookie=new Cookie("token",user.getToken());
        response.addCookie(cookie);
        //跳转到登录态
//        if(jsonUser!=null){
//            request.getSession().setAttribute("user",user);
//            return "redirect:/";  //这种是包含地址与页面的重定向
//        }else{
//            return "redirect:/";
//        }
        return "redirect:/";
    }

    //码云登陆
    //1、访问gitee地址 ，获取 请求参数（github发起了一个请求） code，进入重定向地址（入口）  --可伶 通过请求来传code
    //2、携带code 访问Gitee 获取token （借用工具发起请求）
    //3、验证token访问userInfo
    @RequestMapping("/loginByGitee")
    public  String loginByGitee(@RequestParam("code")String code,
                                HttpServletResponse response){
        GiteeParamDto giteeParamDto =new GiteeParamDto();
        giteeParamDto.setClient_id("b2701d72652b277dd3426c5bd9007fb5ed9bf31244621d60eaa3ff178b41553a");
        giteeParamDto.setClient_secret("a76c934d786a89fffc57594c303684d16268445700683e616c9114be3d6f1ebc");
        giteeParamDto.setCode(code);
        giteeParamDto.setRedirect_uri("http://localhost:8080/loginByGitee");
        String token = giteeProvider.getToken(giteeParamDto);
        System.out.println("token:"+token);
        JsonUser jsonUser = giteeProvider.getJsonUser(token);

        User user = userService.updateOrCreateUser(jsonUser);
        Cookie cookie=new Cookie("token",user.getToken());
        response.addCookie(cookie);
        //跳转到登录态
//        if(jsonUser!=null){
//            request.getSession().setAttribute("user",user);
//            return "redirect:/";  //这种是包含地址与页面的重定向
//        }else{
//            return "redirect:/";
//        }
        return "redirect:/";
    }

    @RequestMapping("/loginBySina")
    public String loginBySina(@RequestParam("code")String code,
                              HttpServletResponse response){
        SinaParamDto sinaParamDto=new SinaParamDto();

        sinaParamDto.setCode(code);
        sinaParamDto.setClient_id(sinaId);
        sinaParamDto.setClient_secret(sinaSecret);
        sinaParamDto.setRedirect_uri("http://127.0.0.1:8080/loginBySina");
        String token = sinaProvider.getToken(sinaParamDto);
        System.out.println("sinaToken:"+token);
        JsonUser jsonUser=sinaProvider.getJsonUser(token);
        System.out.println(jsonUser);
        User user = userService.updateOrCreateUser(jsonUser);
        Cookie cookie=new Cookie("token",user.getToken());
        response.addCookie(cookie);
        //跳转到登录态
//        if(jsonUser!=null){
//            request.getSession().setAttribute("user",user);
//            return "redirect:/";  //这种是包含地址与页面的重定向
//        }else{
//            return "redirect:/";
//        }
        return "redirect:/";
    }

    //退出登录 ：logout 清楚cookie与session
    @RequestMapping("/logout")
    public  String logout(HttpServletRequest servletRequest, HttpServletResponse servletResponse){
        servletRequest.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        servletResponse.addCookie(cookie);
        return "redirect:/";
    }
}
