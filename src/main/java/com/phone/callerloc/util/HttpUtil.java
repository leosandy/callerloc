package com.phone.callerloc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 创建日期:2015年6月10日
 * <br />Http Tools
 * @author leo
 * @mender：（文件的修改者，文件创建者之外的人）
 * @version 1.0
 * Remark：认为有必要的其他信息
 */
public class HttpUtil {

	/**
	 * LOG
	 */
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	/**
	 * 
	 * 功能:http request
	 *<br /> 作者: leo
	 * <br />创建日期:2015年6月9日
	 * <br />修改者: mender
	 * <br />修改日期: modifydate
	 * @param url
	 * @param param
	 * @param charset
	 * @param requestMethod
	 * @return
	 */
	public static String sendRequest(final String url, final String param,final HttpCharset charset,final HttpReqMethod requestMethod) {
		String result = "";
		BufferedReader in = null;
		HttpURLConnection connection = null;
		OutputStream out = null;
		try {
			logger.info("post请求参数url:{},params:{}",url,param);
			URL paostUrl = new URL(url);
			//参数配置
			connection = (HttpURLConnection) paostUrl.openConnection();
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestMethod(requestMethod.name());
			connection.setDoOutput(true); //http正文内，因此需要设为true, 默认情况下是false
			connection.setDoInput(true); //设置是否从httpUrlConnection读入，默认情况下是true
			connection.setUseCaches(false); //Post 请求不能使用缓存
			connection.setInstanceFollowRedirects(true); //URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
			connection.setConnectTimeout(30000); //设置连接主机超时时间
			connection.setReadTimeout(30000); //设置从主机读取数据超时
			
			//打开连接
			out = connection.getOutputStream();
			if(param != null)
			out.write(param.getBytes());
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset.name()));
			String line = in.readLine(); 
			while (line != null) {  
	            result += line;  
	            line = in.readLine();  
	       }  
            
		} catch (Exception e) {
			logger.error("post请求发生异常"+",url="+url+",param:"+param,e);
		} finally {
			try {
				
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (IOException e) {
				logger.error("关闭connection异常"+param,e);
			}
		}
		return result;
	}

}
