package com.lxw.seckill.controller;

import com.lxw.seckill.pojo.User;
import com.lxw.seckill.service.IGoodsService;
import com.lxw.seckill.service.UserService;
import com.lxw.seckill.utils.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    UserService userService;
    @Autowired
    IGoodsService goodsService;


    @RequestMapping("/toList")
    public String toList(User user, Model model){
//        if(StringUtils.isEmpty(ticket)){
//            return "login";
//        }
////        User user = (User) session.getAttribute(ticket);
//        User user = userService.getUserByCookie(ticket, request, response);
//        if(null == user){
//            return "login";
//        }
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());
        return "goodsList";
    }


    /**
     * 跳转商品详情页
     */
    @RequestMapping("/toDetail/{goodsID}")
    public String toDetail(Model model, User user, @PathVariable Long goodsID){
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsID(goodsID);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        //秒杀状态
        int secKillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        //秒杀还未开始
        if (nowDate.before(startDate)){
            remainSeconds = (int)((startDate.getTime() - nowDate.getTime())/1000);
        } else if (nowDate.after(endDate)) {
            secKillStatus = 2;
            remainSeconds = -1;
        }else {
            secKillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("secKillStatus", secKillStatus);
        model.addAttribute("goods", goodsVo);
        return "goodsDetail";
    }

}
