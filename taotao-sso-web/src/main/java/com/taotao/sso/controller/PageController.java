package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	@RequestMapping("/page/register")
	public String register(){
		return "register";
	}
	
	@RequestMapping("/page/login")
	public String login(String url, Model model){
		model.addAttribute("redirect", url);
		return "login";
	}
}
