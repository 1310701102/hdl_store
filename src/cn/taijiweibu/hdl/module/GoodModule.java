package cn.taijiweibu.hdl.module;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.filter.CheckSession;

import cn.taijiweibu.hdl.bean.Good;

@IocBean
@At("/good")
@Ok("json")
@Fail("http:500")
@Filters(@By(type=CheckSession.class, args={"me", "/"}))
public class GoodModule {
	
	@Inject
	protected Dao dao;
	
	/**
	 *计数商品数量，访问路径是”/good/count“。 
	 * @return
	 */
	@At
	public int count(){
		return dao.count(Good.class);
	}

	@At
	public Object query(){
		return null;
	}

}
