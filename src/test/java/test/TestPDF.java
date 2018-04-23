package test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jt.common.util.PDFUtil;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class TestPDF {
	// 基本字体和样式
	static BaseFont bf =null;
	static Font font = null;
	static String CHANG_LINE="\n";
	static Font UNDER_LINE=null;
	
	public static void main(String[] args) {
		// 表示上下左右的边距
		Document document = new Document(PageSize.A4, 33, 33, 33, 33);
		PdfWriter writer =null;
		try {
			bf = BaseFont.createFont("C:/Windows/Fonts/ARIALUNI.TTF", 
					BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			font = new Font(bf, 14, Font.NORMAL);
			UNDER_LINE = new Font(bf, 14, Font.UNDERLINE);
			writer = PdfWriter.getInstance(document, 
					new FileOutputStream("helloworld.pdf"));
			//打开document
			document.open();
			Paragraph title = new Paragraph();
			// 设置页面格式
			title.setSpacingBefore(8);
			title.setSpacingAfter(2);
			title.setAlignment(Paragraph.ALIGN_CENTER);
			
			// 设置PDF标题
			title.add(new Chunk("付款申请书", new Font(bf, 16,Font.BOLD)));
			document.add(title);
			
			//抬头
			Paragraph head = PDFUtil.getParagraph("一封陌生女人的来信");
			head.setSpacingBefore(30);
			document.add(head);
			
			//文字参数
			Map<String,String> map = new HashMap<String, String>();  
            map.put("name", "ranqiqiang");  
            map.put("sex", "男");  
            map.put("height", "178");  
            map.put("time", new Date().toString()); 
            
            //文字数据
            String str = getString();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static String getString(){  
        String str =   
              "{#}工程师们，大家晚上好,我叫:{name},性别:{sex},身高:{height}cm."  
            + "当前时间是：{time}{#}"  
            + "这是文字行的测试数据，下面我们尝试表格型的测试数据。"  
            + "{#}这里展示我们的订单信息：{#}";  
        return str;  
    } 
	
	public static List<Paragraph> getPars(String context ,Map<String,String> map){  
        int index = 0;  
        List<Paragraph> list = new ArrayList<Paragraph>();  
        Paragraph p = getDefaultParagraph(null);  
        while((index  = context.indexOf(BEGIN)) > -1){  
            String text = context.substring(0,index);  
            context = context.substring(index, context.length());  
            index = context.indexOf(END);  
            String param =  null;  
            if(index > 0){  
                 param = context.substring(BEGIN.length(),index);  
            }  
            p.add(text);  
            if(!NEW_LINE.equals(param)){  
                Object value = map.get(param);  
                if(value != null){  
                    p.add(new Chunk(value.toString(),UNDER_LINE));  
                }else{  
                    p.add(new Chunk(param));  
                }  
            }else{  
                list.add(p);  
                p = getDefaultParagraph(null);  
                p.setSpacingBefore(0);  
            }  
            context = context.substring(index+END.length(),context.length());  
        }  
        list.add(p);  
        list.add(getParagraph(context));  
        return list;  
    }  

}
