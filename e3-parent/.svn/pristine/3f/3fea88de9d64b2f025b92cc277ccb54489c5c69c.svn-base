package cn.e3mall.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.sso.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public E3Result login(String username, String password) {
		//查询用户名和密码是否有数据
		//密码进行md5加密
		String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		criteria.andPasswordEqualTo(md5Pass);
		//执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		if (list==null||list.size()<1) {
			return E3Result.build(400, "用户名或密码错误!");
		}
		TbUser user = list.get(0);
		//如果查到数据
		//创建token
		String token = UUID.randomUUID().toString();
		//添加进sso服务器(Redis)
		//key SESSION:+token		value=user对象
		user.setPassword(null);
		jedisClient.set("SESSION:"+token, JsonUtils.objectToJson(user));
		//设置Session的过期时间
		jedisClient.expire("SESSION:"+token, SESSION_EXPIRE);
		//E3Result.data=token返回给Controller
		return E3Result.ok(token);
	}

}
