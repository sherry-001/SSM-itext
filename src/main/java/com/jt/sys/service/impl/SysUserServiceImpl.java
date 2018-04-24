package com.jt.sys.service.impl;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.View;

import com.jt.common.exception.ServiceException;
import com.jt.common.vo.PageObject;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.dao.SysUserRoleDao;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;
import com.jt.sys.service.UserPdfView;


@Service
public class SysUserServiceImpl implements SysUserService{
	@Autowired
	private SysUserDao dao;
	@Autowired
	private SysUserRoleDao userRoleDao;

	@Override
	public PageObject<SysUser> findPageObjects(Integer pageCurrent, String username) {
		// 验证数据的合法性,如果先与1比较,在为空时会抛出空指针异常
		// username不进行判定,username可以为null
		if(pageCurrent==null || pageCurrent<1)
			throw new ServiceException("参数不合法,pageCurrent= "+pageCurrent);
		int pageSize = 3;
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysUser> list = dao.findPageObjects(startIndex, pageSize, username);
		int rowCount = dao.getRowCount(username);
		PageObject<SysUser> pObject = new PageObject<>();
		//这里没有设置pageCount,因对象在转化为json串的时候底层会调用get方法,因此可以计算到pageCount的值
		pObject.setPageCurrent(pageCurrent);
		pObject.setPageSize(pageSize);
		pObject.setRecords(list);
		pObject.setRowCount(rowCount);
		return pObject;
	}
	
	@RequiresPermissions("sys:user:update")
	@Override
	public int validById(Integer id, Integer valid, String modifiedUser) {
		
		if(id==null || id<1)
			throw new ServiceException("参数不合法,id = "+id);
		if(valid!=0 && valid!=1)
			throw new ServiceException("参数不合法,valid = "+valid);
		if(StringUtils.isEmpty(modifiedUser))
			throw new ServiceException("修改用户不能为空");
		
		int rows = 0;
		try {
			rows = dao.validById(id, valid, modifiedUser);
		} catch (Exception e) {
			throw new ServiceException("底层正在维护");
		}
		if(rows==0)
			throw new ServiceException("此记录可能已经不存在");
		return rows;
	}

	@Override
	public int saveObject(SysUser user,String roleIds) {
		if(user==null || roleIds==null)
			throw new ServiceException("保存对象或者角色不能为空");
		if(StringUtils.isEmpty(user.getUsername()))
			throw new ServiceException("用户名不能为空");
		if(StringUtils.isEmpty(user.getPassword()))
			throw new ServiceException("密码不能为空");
		//密码验证...
		//对用户密码进行加密
		String salt = UUID.randomUUID().toString();
		user.setSalt(salt);
		SimpleHash sh = new SimpleHash("MD5",  //由shiro提供的API
				user.getPassword(), salt);
		user.setPassword(sh.toString());
		//目前还没有考虑事务控制
		//保存用户信息
		int rows = dao.insertObject(user);
		//保存用户与角色的关系数据
		int row = userRoleDao.insertObject(user.getId(), roleIds.split(","));
		return rows;
	}
	
	/**
	 * 控制用户登录
	 */
	@Override
	public void login(String username, String password) {
		System.out.println(username+":"+password);
		//1 数据合法性验证
		if(StringUtils.isEmpty(username))
			throw new ServiceException("用户名不能为空");
		if(StringUtils.isEmpty(password))
			throw new ServiceException("密码不能为空");
		//2 获取subject主体对象
		Subject subject = SecurityUtils.getSubject();
		//3 封装用户名和密码
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		//4 执行身份认证
		try {
			System.out.println("傻瓜"+token);
			subject.login(token);
			/*此请求会提交给SecurityManager,
			 * SecurityManager会调用认证处理器Authenticator
			 * 认证处理器会去访问相关Realm对象获取认证信息
			 */
		} catch (AuthenticationException e) {
			// 为什么要使用我们自己的类处理???
			e.printStackTrace();
			throw new AuthenticationException("用户名或密码不正确");
		}
	}

	@Override
	public Workbook findObjects() throws Exception {
		//查询获取所有的用户信息
		List<SysUser> sysUsers = dao.findObjects();
		System.out.println(sysUsers);
		//创建一个workbook
		Workbook workbook = new XSSFWorkbook();	
		//得到一个POI工具类
		//CreationHelper helper = workbook.getCreationHelper();
		// 在Excel工作簿中建一工作表，其名为缺省值, 也可以指定Sheet名称
        Sheet sheet = workbook.createSheet("userdata");
        // 用于格式化单元格的数据
        DataFormat format = workbook.createDataFormat();
        
        // 设置字体
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 20); // 字体高度
        font.setColor(Font.COLOR_NORMAL); // 字体颜色
        font.setFontName("黑体"); // 字体
        font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 宽度
        //font.setItalic(true); // 是否使用斜体
        // font.setStrikeout(true); //是否使用划线
        
        // 设置单元格类型保存文字
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 水平布局：居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直居中
        cellStyle.setWrapText(true);  // 自动换行?
        
        //设置单元格格式保存数字?
        CellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle2.setDataFormat(format.getFormat("＃,##0.0"));
        
        //设置单元格格式保存日期
        CellStyle cellStyle3 = workbook.createCellStyle();
        cellStyle3.setDataFormat(format.getFormat("yyyy-MM-dd HH:mm:ss"));
        //创建表头
        Cell head = sheet.createRow(0).createCell(0);
        head.setCellValue("user list");
        head.setCellStyle(cellStyle);
        Row row = sheet.createRow(1);
        Field[] fields = sysUsers.get(0).getClass().getDeclaredFields();
       for(int i=1; i<fields.length;i++){
    	   System.out.println(fields[i]);
    	   Cell cell = row.createCell(i-1);
    	   String name = fields[i].getName();
    	   System.out.println(name);
    	   cell.setCellValue(name);
    	   cell.setCellStyle(cellStyle);
       }
       
       //插入数据
       for(int i =0;i<sysUsers.size();i++){
    	   SysUser sysUser = sysUsers.get(i);
    	   row = sheet.createRow(2+i);
    	   for(int j=1; j<fields.length;j++){
    		   Cell cell = row.createCell(j-1);
    		   Field field = fields[j];
    		   String name = field.getName();
    		   name ="get"+name.substring(0, 1).toUpperCase().concat(name.substring(1));
    		   Object value = sysUser.getClass().getMethod(name).invoke(sysUser);
    		   System.out.println(field.getType());
    		   if(field.getType()==String.class && value!=null){
    			   cell.setCellValue((String)value);
    		   }else if(field.getType()==Integer.class && value!=null){
    			   cell.setCellValue((Integer)value);
    			   cell.setCellStyle(cellStyle2);
    		   }else if(field.getType()==Date.class && value!=null){
    			   System.out.println(value);
    			   cell.setCellValue((Date)value);
    			   cell.setCellStyle(cellStyle3);
    		   }
    	   }
       }
       return workbook;
	}

	@Override
	public View findObjectsPdf() {
		List<SysUser> sysUsers = dao.findObjects();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sysUsers", sysUsers);
		UserPdfView pdf = new UserPdfView();
		pdf.setAttributesMap(map);
		return pdf;
	}
 }

	
