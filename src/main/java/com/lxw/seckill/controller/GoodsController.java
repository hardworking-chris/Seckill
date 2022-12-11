package com.lxw.seckill.controller;

import com.lxw.seckill.pojo.User;
import com.lxw.seckill.service.IGoodsService;
import com.lxw.seckill.utils.vo.DetailVo;
import com.lxw.seckill.utils.vo.GoodsVo;
import com.lxw.seckill.utils.vo.RespBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;
/**
 * 商品
 *
 * @author lxw
 * @since 1.0.0
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;
    /**
     * 跳转商品列表页
     *
     * @return
     */
    @RequestMapping(value = "/toList2", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toList2(HttpServletRequest request, HttpServletResponse
            response, Model model, User user) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //Redis中获取页面，如果不为空，直接返回页面
        String html = (String) valueOperations.get("goodsList");
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());
        // return "goodsList";
        //如果为空，手动渲染，存入Redis并返回
        WebContext context = new WebContext(request, response,
                request.getServletContext(), request.getLocale(),
                model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList",
                context);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsList", html, 60, TimeUnit.SECONDS);
        }
        return html;
    }
    /**
     * 跳转商品详情页
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/toDetail2/{goodsId}", produces =
            "text/html;charset=utf-8")
    @ResponseBody
    public String toDetail2(HttpServletRequest request, HttpServletResponse
            response, Model model, User user,
                           @PathVariable Long goodsId) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //Redis中获取页面，如果不为空，直接返回页面
        String html = (String) valueOperations.get("goodsDetail:" + goodsId);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.findGoodsVoByGoodsID(goodsId);
        model.addAttribute("goods", goods);
        Date startDate = goods.getStartDate();
        Date endDate = goods.getEndDate();
        Date nowDate = new Date();
        //秒杀状态
        int secKillStatus = 0;
        //剩余开始时间
        int remainSeconds = 0;
        //秒杀还未开始
        if (nowDate.before(startDate)) {
            remainSeconds = (int) ((startDate.getTime() - nowDate.getTime()) /
                    1000);
            // 秒杀已结束
        } else if (nowDate.after(endDate)) {
            secKillStatus = 2;
            remainSeconds = -1;
            // 秒杀中
        } else {
            secKillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("secKillStatus", secKillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        // return "goodsDetail";
        //如果为空，手动渲染，存入Redis并返回
        WebContext context = new WebContext(request, response,
                request.getServletContext(), request.getLocale(),
                model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail",
                context);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsDetail:" + goodsId, html, 60,
                    TimeUnit.SECONDS);
        }
        return html;
    }


    @RequestMapping(value = "/toList", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    public String toList(Model model, User user, HttpServletRequest request, HttpServletResponse response) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList");
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());

        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", webContext);
        if (!org.thymeleaf.util.StringUtils.isEmpty(html)) {
            valueOperations.set("goodsList", html, 60, TimeUnit.SECONDS);
        }
        return html;
    }

    @ApiOperation("商品详情")
    @RequestMapping(value = "/detail/{goodsId}", method = RequestMethod.GET)
    @ResponseBody
    public RespBean toDetail(User user, @PathVariable Long goodsId) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
//        String html = (String) valueOperations.get("goodsDetail:" + goodsId);
//        if (!StringUtils.isEmpty(html)) {
//            return html;
//        }

//        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsID(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        //秒杀状态
        int seckillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;

        if (nowDate.before(startDate)) {
            //秒杀还未开始0
            remainSeconds = (int) ((startDate.getTime() - nowDate.getTime()) / 1000);
        } else if (nowDate.after(endDate)) {
            //秒杀已经结束
            seckillStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀进行中
            seckillStatus = 1;
            remainSeconds = 0;
        }
//        model.addAttribute("remainSeconds", remainSeconds);
//        model.addAttribute("goods", goodsVo);
//        model.addAttribute("seckillStatus", seckillStatus);

//        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
//        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", webContext);
//        if (!StringUtils.isEmpty(html)) {
//            valueOperations.set("goodsDetail:" + goodsId, html, 60, TimeUnit.SECONDS);
//        }
        DetailVo detailVo = new DetailVo();
        detailVo.setTUser(user);
        detailVo.setGoodsVo(goodsVo);
        detailVo.setRemainSeconds(remainSeconds);
        detailVo.setSecKillStatus(seckillStatus);
        return RespBean.success(detailVo);
    }
}