package com.lxw.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxw.seckill.pojo.User;
import com.lxw.seckill.utils.vo.LoginVo;
import com.lxw.seckill.utils.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService extends IService<User> {

    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    //根据Cookie获取用户
    User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response);
}
