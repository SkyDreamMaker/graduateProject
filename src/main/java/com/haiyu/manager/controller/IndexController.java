package com.haiyu.manager.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
    浏览页定向管理
 */
@Controller
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("login")
    public String tologin(){
        logger.info("定向登陆页");
        return "login";
    }

    @RequestMapping("registerPage")
    public String toregister(){
        logger.info("定向注册页");
        return "registerPage";
    }

    @RequestMapping("home")
    public String home(){
        logger.info("定向主页");
        return "home";
    }

    @RequestMapping("logout")
    public String logout(){
        logger.info("退出系统");
        Subject subject = SecurityUtils.getSubject();
        subject.logout(); // shiro底层删除session的会话信息
        return "redirect:login";
    }

}
