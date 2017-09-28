package cn.e3mall.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private JedisClient jedisClient;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public E3Result GetUserByToken(String token) {
		//从sso服务器中取json
		String json = jedisClient.get("SESSION:"+token);
		if (StringUtils.isNotBlank(json)) {
			//重置sso服务器数据的持久化时间
			//jedisClient.expire("SESSION:"+token, SESSION_EXPIRE);
			//将json转换成用户
			TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
			//封装成e3Result对象
			E3Result ok = E3Result.ok(user);
			return ok;
		}
		//如果没有取到数据
		return E3Result.build(201, "用户登陆已过时!");
	}

}
