package test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysRole;
import com.jt.sys.service.SysRoleService;
import com.jt.sys.service.impl.SysRoleServiceImpl;


public class TestSysRole {
	ClassPathXmlApplicationContext ctx;
	
	@Before
	public void init(){
		ctx = new ClassPathXmlApplicationContext("spring-configs.xml");
	}
	
	@Test
	public void test01(){
		SysRoleService service = ctx.getBean("sysRoleServiceImpl", SysRoleService.class);
		/*PageObject<SysRole> object = service.findPageObjects(1);
		System.out.println(object);*/
		SysRole role = new SysRole();
		role.setName("项目经理");
		role.setNote("note");
		
		service.insertObject(role);
	}
	
	@Test
	public void test02(){
		String id ="50";
		String[] ids = id.split(",");
		System.out.println(ids);
	}
}
