package com.phone.callerloc.util;

/**
 * 
 * 创建日期:2015年6月9日
 * <br />Description：对本文件的详细描述，原则上不能少于50字
 * @author leo
 * @mender：（文件的修改者，文件创建者之外的人）
 * @version 1.0
 * Remark：认为有必要的其他信息
 */
public enum HttpCharset {

	GBK("GBK"),UTF8("UTF-8");
	private String charset;
	
	private HttpCharset(String charset){
		this.charset = charset;
	}
	
	public String charset(){
		return this.charset;
	}
}
