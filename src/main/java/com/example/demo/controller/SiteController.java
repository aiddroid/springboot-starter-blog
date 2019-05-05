/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author allen
 */
@Controller
@RequestMapping("/site")
public class SiteController {
    
    @RequestMapping("login")
    public String login(ModelMap mm) {
        String message = "hello, world!";
        mm.addAttribute("message", message);
        
        return "/site/login";
    }
}