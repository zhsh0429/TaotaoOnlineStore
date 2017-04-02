package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private TbUserMapper tbUserMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${USER_SESSION}")
	private String USER_SESSION;
	@Value("${USER_SESSION_EXPIRE}")
	private Integer USER_SESSION_EXPIRE;
	
	@Override
	public TaotaoResult checkUserData(String param, Integer type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		if(type == 1){
			criteria.andUsernameEqualTo(param);
		}else if(type == 2){
			criteria.andPhoneEqualTo(param);
		}else if (type == 3) {
			criteria.andEmailEqualTo(param);
		}else{
			return TaotaoResult.build(400, "wrong type param");
		}
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			return TaotaoResult.ok(false);
		}
		return TaotaoResult.ok(true);
	}

	@Override
	public TaotaoResult registerUser(TbUser tbUser) {
		//1.check username
		String username = tbUser.getUsername();
		if(StringUtils.isBlank(username)){
			return TaotaoResult.build(400, "username cannot be empty");
		}else{
			if(!(boolean) checkUserData(username,1).getData()){
				return TaotaoResult.build(400, "username exist");
			}
		}
		//2.check phone
		String phone = tbUser.getPhone();
		if(StringUtils.isBlank(phone)){
			return TaotaoResult.build(400, "phone cannot be empty");
		}else{
			if(!(boolean) checkUserData(phone,2).getData()){
				return TaotaoResult.build(400, "phone exist");
			}
		}
		//3.check email
		String email = tbUser.getEmail();
		if(StringUtils.isBlank(email)){
			return TaotaoResult.build(400, "email cannot be empty");
		}else{
			if(!(boolean) checkUserData(email,3).getData()){
				return TaotaoResult.build(400, "email exist");
			}
		}
		//4.MD5 password & set
		String password = tbUser.getPassword();
		String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
		tbUser.setPassword(md5Password);
		//set time
		tbUser.setCreated(new Date());
		tbUser.setUpdated(new Date());
		//insert
		tbUserMapper.insert(tbUser);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult login(String username, String password) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		//check username
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			return TaotaoResult.build(400, "username or password is wrong");
		}
		TbUser tbUser = list.get(0);
		//check password
		if(!tbUser.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
			return TaotaoResult.build(400, "username or password is wrong");
		}
		//put tbUser to Redis
		String token = UUID.randomUUID().toString();
		tbUser.setPassword(null);
		jedisClient.set(USER_SESSION + ":" + token, JsonUtils.objectToJson(tbUser));
		jedisClient.expire(USER_SESSION + ":" + token, USER_SESSION_EXPIRE);
		return TaotaoResult.ok(token);
	}

	@Override
	public TaotaoResult getUserByToken(String token) {
		if(StringUtils.isNotBlank(token)){
			String jsonUser = jedisClient.get(USER_SESSION + ":" + token);
			if(StringUtils.isNotBlank(jsonUser)){
				jedisClient.expire(USER_SESSION + ":" + token, USER_SESSION_EXPIRE);
				TbUser user = JsonUtils.jsonToPojo(jsonUser, TbUser.class);
				return TaotaoResult.ok(user);
			}else{
				return TaotaoResult.build(400, "invalidated token");
			}
		}
		return TaotaoResult.build(400, "token is blank");
	}

	@Override
	public TaotaoResult deleteUserByToken(String token) {
		if(StringUtils.isNotBlank(token)){
			if(StringUtils.isNotBlank(jedisClient.get(USER_SESSION + ":" + token))){
				jedisClient.expire(USER_SESSION + ":" + token, 1);
				return TaotaoResult.ok();
			}else{
				return TaotaoResult.build(400, "token is not exist");
			}
		}
		
		return TaotaoResult.build(400, "token is blanket");
	}

	
}
