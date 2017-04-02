package com.taotao.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;

public class LoginInterceptor implements HandlerInterceptor{

	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;
	@Value("${SSO_URL}")
	private String SSO_URL;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = CookieUtils.getCookieValue(request, TOKEN_KEY);
		//token doesn't exist
		if(StringUtils.isBlank(token)){
			String requestURL = request.getRequestURL().toString();
			response.sendRedirect(SSO_URL + "/page/login?url=" + requestURL);
			return false;
		}
		//get token
		TaotaoResult taotaoResult = userService.getUserByToken(token);
		//result != 200
		if(taotaoResult.getStatus() != 200){
			String requestURL = request.getRequestURL().toString();
			response.sendRedirect(SSO_URL + "/page/login?url=" + requestURL);
			return false;
		}
		TbUser tbUser = (TbUser) taotaoResult.getData();
		request.setAttribute("user", tbUser);
		return true;
	}

}
