package cn.e3mall.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.sso.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private TbUserMapper userMapper;

	@Override
	public E3Result checkData(String value, Integer type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 得到type判断查询用户名还是手机号
		if (type == 1) {
			criteria.andUsernameEqualTo(value);
		} else if (type == 2) {
			criteria.andPhoneEqualTo(value);
		} else {
			return E3Result.build(400,"数据类型错误" );
		}
		List<TbUser> list = userMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			// 如果结果集有数据返回false,没有数据返回true
			return E3Result.ok(false);
		}
		return E3Result.ok(true);
	}

	
	@Override
	public E3Result register(TbUser user) {
		//验证用户传递过来的用户名和密码和手机号是否为空
		if (!StringUtils.isNotBlank(user.getUsername()) || !StringUtils.isNotBlank(user.getPassword()) || !StringUtils.isNotBlank(user.getPhone())) {
			return E3Result.build(400, "数据不完整，注册失败!");
		}
		//校验用户名和手机号是否存在
		if(!(boolean) checkData(user.getUsername(),1).getData()) return E3Result.build(400, "此用户名已经被占用");
		if(!(boolean) checkData(user.getPhone(),2).getData()) return E3Result.build(400, "手机号已经被占用");
		//对密码进行MD5加密
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		//对create和update补齐
		Date date=new Date();
		user.setCreated(date);
		user.setUpdated(date);
		//添加进数据库
		userMapper.insert(user);
		return E3Result.ok();
	}
}