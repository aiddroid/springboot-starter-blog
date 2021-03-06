/*
 * Copyright 2019 allen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;

/**
 * 站点控制器
 * @author allen
 */
@Controller
public class SiteController implements ErrorController{
    
    /**
     * 登录界面
     * @param mm
     * @param request
     * @return 
     */
    @RequestMapping(value = "/site/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(ModelMap mm, HttpServletRequest request) {
        String message = "hello, world!";
        mm.addAttribute("message", message);
         
        return "/site/login";
    }
    
    @Autowired
    private Producer captchaProducer;
    
    /**
     * 图片验证码
     * @param request
     * @param response
     */
    @RequestMapping(value = "/site/captcha", method = RequestMethod.GET)
    public void captcha(HttpServletRequest request, HttpServletResponse response){
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        //生成验证码字符串
        String captchaText = captchaProducer.createText();
        request.getSession().setAttribute("captcha", captchaText);
        
        try {
            //生成验证码图片
            BufferedImage bi = captchaProducer.createImage(captchaText);
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();
            out.close();
        } catch (Exception e) {
        }
    }
    
    /**
     * 错误页面
     * @param mm
     * @param request
     * @param response
     * @return 
     */
    @RequestMapping("/error")
    public String error(ModelMap mm, HttpServletRequest request, HttpServletResponse response) {
        mm.addAttribute("uri", request.getRequestURI());
        mm.addAttribute("code", response.getStatus());
        
        return getErrorPath();
    }

    @Override
    public String getErrorPath() {
        return "/site/error";
    }
}
