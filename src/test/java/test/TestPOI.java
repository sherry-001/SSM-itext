package test;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jt.sys.service.SysUserService;

public class TestPOI {
	private ClassPathXmlApplicationContext ctx;
	
	@Before
	public void init(){
		ctx = new ClassPathXmlApplicationContext("spring-configs.xml");
	}
	
	@After
	public void destory(){
		ctx.close();
	}
	
	@Test
	public void test001() throws Exception{
		SysUserService service = ctx.getBean("sysUserServiceImpl",SysUserService.class);
		Workbook workbook = service.findObjects();
		FileOutputStream out = new FileOutputStream("workbook.xlsx");
		workbook.write(out);
		out.close();
	}
}
