package cn.taijiweibu.hdl.module;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.filter.CheckSession;

import cn.taijiweibu.hdl.bean.User;
import cn.taijiweibu.hdl.util.ToolKit;

@IocBean
@At("/user")
@Ok("json:{locked:'password|salt',ignoreNull:true}")
@Fail("http:500")
@Filters(@By(type=CheckSession.class, args={"me", "/"}))

public class UserModule {
	
	@Inject
	protected Dao dao;
	
	/**
	 * 计数用户数量的方法，访问路径是”user/count“。
	 * @return
	 */
	@At
	public int count(){
		return dao.count(User.class);
	}
	
	/**
	 * 用户登录验证方法，访问路径是”user/login“。
	 * @param phoneNumber
	 * @param password
	 * @param httpSession
	 * @return
	 */
	@At
	@Filters()
	public Object login(@Param("phoneNumber")String phoneNumber, @Param("password")String password, HttpSession httpSession){
		User user = dao.fetch(User.class, Cnd.where("phoneNumber", "=", phoneNumber));
		password = ToolKit.passwordEncode(password, user.getSalt());
		if(user != null && user.getPassword().equals(password)){
			httpSession.setAttribute("me", user.getName());
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 等出方法，访问路径是”user/logout“。
	 * @param session
	 */
	@At
	@Ok(">>:/")
	public void logout(HttpSession session) {
		session.invalidate();
	}
	
	/**
	 * 
	 * @param user
	 * @param create
	 * @return
	 */
	protected String checkUser(User user, boolean create){
		if (user == null){
			return "空对象";
		}
		
		if (create) {
			if(Strings.isBlank(user.getPhoneNumber()) || Strings.isBlank(user.getPassword()))
				return "手机号码/密码不能为空！";
		} else {
			if(Strings.isBlank(user.getPassword()))
				return "密码不能为空";
		}
		
		String password = user.getPassword().trim();
		if (6 > password.length() || password.length() > 12) {
			return "密码长度错误";
		}
		
		user.setPassword(password);
		if(create) {
			int count = dao.count(User.class, Cnd.where("phoneNumber", "=", user.getPhoneNumber()));
			if(count != 0) {
				return "此手机号码已经被注册了，可以直接登陆。";
			}
		} else {
			if (user.getId() < 1) {
				return "用户Id非法！";
			}
		}
		
		if (user.getName() != null)
			user.setName(user.getName().trim());
		
		if (user.getPhoneNumber() != null)
			user.setPhoneNumber(user.getPhoneNumber().trim());
		
		return null;
	}
	
	@At
	public Object add(@Param("..")User user) {
		NutMap request = new NutMap();
		String massage = checkUser(user, true);
		
		if(massage != null) {
			return request.setv("ok", false).setv("massage", massage);
		}
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setSalt(ToolKit.saltEcode());
		user.setPassword(ToolKit.passwordEncode(user.getPassword(), user.getSalt()));
		user = dao.insert(user);
		return request.setv("ok", true).setv("data", user);
	}
	
	@At
	public Object update(@Param("..")User user) {
		NutMap request = new NutMap();
		String massage = checkUser(user, false);
		if(massage != null) {
			return request.setv("ok", false).setv("massage", massage);
		}
		user.setName(null);				//不允许修改姓名
		user.setPhoneNumber(null);		//不允许修改手机
		user.setCreateTime(null);		//不允许修改创建时间
		user.setUpdateTime(new Date());	//设置正确的更新时间
		user.setSalt(ToolKit.saltEcode());
		user.setPassword(ToolKit.passwordEncode(user.getPassword(), user.getSalt()));
		dao.updateIgnoreNull(user);
		return request.setv("ok", true);
	}
	
	@At
	public Object query(@Param("phoneNumber")String phoneNumber, @Param("..")Pager pager){
		Cnd cnd = Strings.isBlank(phoneNumber)? null : Cnd.where("phoneNumber", "=", phoneNumber);
		QueryResult queryResult = new QueryResult();
		queryResult.setList(dao.query(User.class, cnd, pager));
		pager.setRecordCount(dao.count(User.class, cnd));
		queryResult.setPager(pager);
		return queryResult;		//默认分页是第1页，每页20条。
	}
	
}
