package cn.taijiweibu.hdl;

import java.util.Date;

import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import cn.taijiweibu.hdl.bean.User;
import cn.taijiweibu.hdl.util.ToolKit;

public class MainSetup implements Setup {

	
	@Override
	public void init(NutConfig conf) {
		
		Ioc	ioc = conf.getIoc();
		Dao dao = ioc.get(Dao.class);
		Daos.createTablesInPackage(dao, "cn.taijiweibu.hdl", false);
		//初始化默认根用户
		if(dao.count(User.class) == 0){
			String salt = ToolKit.saltEcode(6);
			User user = new User();
			user.setName("黄栋梁");
			user.setPhoneNumber("18151997967");
			user.setSalt(salt);
			user.setPassword(ToolKit.passwordEncode("123456", salt));
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			dao.insert(user);
		}
	}
	
	
	@Override
	public void destroy(NutConfig conf) {
		
	}

}
