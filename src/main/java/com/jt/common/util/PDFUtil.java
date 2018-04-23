package com.jt.common.util;

import java.io.IOException;

import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;



public class PDFUtil {
	
	//基本字体和样式
	public static BaseFont bf;
	public static Font font;
	
	public static final String BEGIN = "{";  
    public static final String END = "}";  
    public static final String NEW_LINE = "#";  
    public static final float DEFAULT_LEADING = 20;  
    public static final float DEFAULT_LINE_INDENT = 30;  
	
    
	static {
		try {
			bf=BaseFont.createFont("C:/Windows/Fonts/ARIALUNI.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			font = new Font(bf, 14, Font.NORMAL);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Paragraph getParagraph(Chunk chunk){
		return new Paragraph(chunk);
	}
	
	public static Paragraph getParagraph(String string){
		return new Paragraph(string,font);
	}
	
	 // 默认段落样式  
    public static Paragraph getDefaultParagraph(String context){  
        Paragraph p = getParagraph(context);  
        // 默认行间距  
        p.setLeading(DEFAULT_LEADING);  
        // 默认首行空隙  
        p.setFirstLineIndent(DEFAULT_LINE_INDENT);  
        return p;  
    } 
}
