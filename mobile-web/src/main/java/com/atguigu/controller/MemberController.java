package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisConstant;
import com.atguigu.enity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.service.MemberService;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class MemberController {
    @DubboReference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;

    @ResponseBody
    @RequestMapping("/check")
    public Result login(HttpServletResponse response, @RequestBody Map<String, String> map) {
        String phone = map.get("telephone");
        String code = map.get("validateCode");
        String s = jedisPool.getResource().get(phone + RedisConstant.SENDTYPE_LOGIN);
        if (StringUtils.isBlank(s) || !s.equals(code)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        /**根据手机号查找会员账号**/
        Member member = memberService.findByTelephone(phone);
        Member newMember = new Member();
        /**不是会员就自动注册为会员**/
        if (member == null) {
            newMember.setPhoneNumber(phone);
            newMember.setRegTime(new Date());
            memberService.add(newMember);
        }
        /*添加cookie*/
        Cookie cookie = new Cookie("login_member_telephone", phone);
        cookie.setPath("/");
        /*设置30天的有效期*/
        cookie.setMaxAge(60 * 60 * 24 * 30);
        response.addCookie(cookie);
        return new Result(true, MessageConstant.LOGIN_SUCCESS, member == null ? newMember : member);
    }
}
