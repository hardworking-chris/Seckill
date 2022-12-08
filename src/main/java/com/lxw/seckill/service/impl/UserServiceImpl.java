package com.lxw.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxw.seckill.exception.GlobalException;
import com.lxw.seckill.mapper.UserMapper;
import com.lxw.seckill.pojo.User;
import com.lxw.seckill.service.UserService;
import com.lxw.seckill.utils.CookieUtil;
import com.lxw.seckill.utils.MD5Util;
import com.lxw.seckill.utils.UUIDUtil;
import com.lxw.seckill.utils.vo.LoginVo;
import com.lxw.seckill.utils.vo.RespBean;
import com.lxw.seckill.utils.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author 10096
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2022-11-30 05:18:00
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
//        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//        }
//        if (!ValidatorUtil.isMobile(mobile)){
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }
        User user = userMapper.selectById(mobile);
        if(user == null){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //判断密码是否正确
        if (!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //生成cookie
        String ticket = UUIDUtil.uuid();
        //将用户信息存入redis中
        redisTemplate.opsForValue().set("user:"+ticket, user);
//        request.getSession().setAttribute(ticket, user);5666656t
        CookieUtil.setCookie(request, response, "userTicket", ticket);
        return RespBean.success(ticket);
    }

    @Override
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.isEmpty(userTicket)){
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        if(null != user){
            CookieUtil.setCookie(request, response, "userTicket", userTicket);
        }
        return user;
    }
}




