package com.phone.callerloc.query;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phone.callerloc.util.HttpCharset;
import com.phone.callerloc.util.HttpReqMethod;
import com.phone.callerloc.util.HttpUtil;

/**
 * 
 * 创建日期:2015年6月9日
 * <br />使用财付通查询手机归属地
 * @author 张凯
 * @mender：（文件的修改者，文件创建者之外的人）
 * @version 1.0
 * Remark：<p>
 * 	财付通
	API地址： http://life.tenpay.com/cgi-bin/mobile/MobileQueryAttribution.cgi?chgmobile=15850781443
	参数：
	chgmobile：手机号码
	返回：xml
 * </p>
 */
public class TenPayPhoneLocal extends AbstractPhoneLocal {

	/*
	 * LOG
	 */
	private transient Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 使用财付通API 查询归属地 仅支持字符集GBK
	 */
	private static final String GET_WAY = " http://life.tenpay.com/cgi-bin/mobile/MobileQueryAttribution.cgi";

	public TenPayPhoneLocal(){
		
	}
	
	public TenPayPhoneLocal(IPhoneLocal next) {
		super(next);
	}

	@Override
	protected Map<String, Object> searchPhoneNumber(String phoneNo) {
		String result = HttpUtil.sendRequest(GET_WAY,String.format("chgmobile=%s", phoneNo), HttpCharset.GBK,HttpReqMethod.POST);
		try {
			logger.info("phoneNo:{}归属地查询, 财付通返回结果：{}",phoneNo,result);
			Document doc = DocumentHelper.parseText(result);
			Node resp = doc.selectSingleNode("//retcode");
			if(resp != null &&"0".equals( resp.getText())){
				Map<String, Object> map = new HashMap<String, Object>(2);
				Node node = doc.selectSingleNode("//province");
				if(node == null){
					return null;
				}
				map.put(RespMapKey.province.name(), node.getText().replaceAll("\\s+", "").replaceAll("-", ""));
				node = doc.selectSingleNode("//supplier");
				map.put(RespMapKey.supplier.name(), node == null ?"":node.getText().replaceAll("\\s+", "").replaceAll("-", ""));
				node = doc.selectSingleNode("//city");
				map.put(RespMapKey.city.name(), node == null ?"":node.getText().replaceAll("\\s+", "").replaceAll("-", ""));
				return map;
			}
		} catch (DocumentException e) {
			logger.error(String.format("从财付通获取手机%s归属地查询异常", phoneNo),e);
		}
		return null;
	}

	public String toString() {
		return "财付通";
	}

}
