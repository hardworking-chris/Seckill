package com.lxw.seckill.controller;


import com.lxw.seckill.service.UserService;
import com.lxw.seckill.utils.vo.LoginVo;
import com.lxw.seckill.utils.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
@Slf4j //lombok注解，用来生成日志
public class LoginController {

    @Autowired
    private UserService userService;
    /**
     * 跳转登陆页面
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    /**
     * 登录功能
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean doLogin(@Validated LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){
        return userService.doLogin(loginVo, request, response);
    }

}
