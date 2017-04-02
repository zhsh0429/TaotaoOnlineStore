package com.taotao.sso.service;


import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserService {

	TaotaoResult checkUserData(String param, Integer type);
	
	TaotaoResult registerUser(TbUser tbUser);
	
	TaotaoResult login(String usernaem, String password);

	TaotaoResult getUserByToken(String token);

	TaotaoResult deleteUserByToken(String token);
}
