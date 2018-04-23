package com.jt.sys.service.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.jt.sys.dao.SysUserDao;
import com.jt.sys.entity.SysUser;

/**
 * 借助此realm类型的对象
 * @author soft01
 */
public class ShiroUserRealm extends AuthorizingRealm{
	
	@Autowired
	private SysUserDao userDao;
	
	/** 此方法中完成权限数据的获取与封装
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//1 获取用户信息
		String username = (String)principals.getPrimaryPrincipal();
		//2 获取用户权限信息
		List<String> list =userDao.findUserPermissions(username);
		//3 去除用户重复或者为空的权限信息(一个用户可以由多个角色,多个角色的权限)
		Set<String> set = new HashSet<String>();
		for (String perm : list) {
			if(perm!=null &&!("".equals(perm)))
				set.add(perm);
		}
		//4 对用户权限信息进行封装,创建实现类的对象封装数据
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(set);
		return info;
	}
	
	/**在此方法中完成认证数据的获取以及封装
	 * @param token 封装了主体subject的身份信息
	 * Subject.login(token)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1 从token中获取Subject身份信息
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		String username = upToken.getUsername();
		//2 根据用户信息进行数据库查询
		SysUser user = userDao.findUserByUserName(username);
		//3 对查询结果进行验证(例如这个用户是否已经被禁用)
		if(user==null)
			throw new AuthenticationException("此用户不存在");
		if(user.getValid()==0)
			throw new AuthenticationException("此用户已经被禁用");
		//4 对查询结果进行数据封装	
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, //封装的principle
																user.getPassword(),
																credentialsSalt,//加密和解密的方式要一样
																getName());
		//5 返回封装结果
		return info;
	}
}
