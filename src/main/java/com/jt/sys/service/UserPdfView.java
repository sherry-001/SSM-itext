package com.jt.sys.service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractPdfView;


import com.jt.common.util.PDFUtil;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.entity.SysUser;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class UserPdfView extends AbstractPdfView{
	@Autowired
	private SysUserDao userDao;
	public static final String TITLE="sys_user";
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		document.open();
		//设置标题字体与居中显示
		Paragraph title = PDFUtil.getParagraph(new Chunk(
				TITLE, new Font(PDFUtil.bf, 16, Font.BOLD)));
		title.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(title);
		
		// 先获取正文的内容
		List<SysUser> sysUsers = userDao.findObjects();
		Field[] fields = createHeader(sysUsers);
		//正文表格
		PdfPTable table = new PdfPTable(fields.length-1);
		// 设置这个是干嘛的? 先不设置,看下结果
		//table.setSpacingAfter(spacing);
		for (Field field : fields) {
			table.addCell(PDFUtil.getParagraph(field.getName()));
		}
	}
	
	public Field[] createHeader(List<SysUser> sysUsers){
		Field[] fields = sysUsers.get(0).getClass().getDeclaredFields();
        Field[] fields1 = sysUsers.get(0).getClass().getSuperclass().getDeclaredFields();
        int len=fields.length;
        fields=Arrays.copyOf(fields, len+fields1.length);
        System.out.println(Arrays.toString(fields));
        System.arraycopy(fields1, 0, fields, len, fields1.length);
		return fields;
	}
}
