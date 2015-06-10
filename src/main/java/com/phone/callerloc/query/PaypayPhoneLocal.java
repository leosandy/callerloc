package com.phone.callerloc.query;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phone.callerloc.util.HttpCharset;
import com.phone.callerloc.util.HttpReqMethod;
import com.phone.callerloc.util.HttpUtil;

/**
 * 
 * 创建日期:2015年6月9日
 * <br />从拍拍查询手机归属地
 * @author leo
 * @mender：（文件的修改者，文件创建者之外的人）
 * @version 1.0
 * Remark：<p>
 * 拍拍
	API地址： http://virtual.paipai.com/extinfo/GetMobileProductInfo?mobile=15850781443&amount=10000&callname=getPhoneNumInfoExtCallback
	参数：
	mobile：手机号码
	callname：回调函数
	amount：未知（必须）
	返回：JSON
 * </p>
 */
public class PaypayPhoneLocal extends AbstractPhoneLocal {

	/*
	 * LOG
	 */
	private transient Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 使用拍拍API 查询归属地 仅支持字符集GBK
	 */
	private static final String GET_WAY = "http://virtual.paipai.com/extinfo/GetMobileProductInfo";

	public PaypayPhoneLocal(){
		
	}
	
	public PaypayPhoneLocal(IPhoneLocal next) {
		super(next);
	}

	@Override
	protected Map<String, Object> searchPhoneNumber(String phoneNo) {
		try {
			String result = HttpUtil.sendRequest(GET_WAY, String.format("mobile=%s&amount=10000&callname=callback", phoneNo), HttpCharset.GBK, HttpReqMethod.POST);
			Matcher matcher = Pattern.compile("callback\\((\\S+)\\)").matcher(result);
			if(matcher.find()){
				JSONObject jsonObject = JSONObject.fromObject(matcher.group(1));
				logger.info("phoneNo:{}归属地查询, 拍拍返回结果：{}",phoneNo,result);
				String province = jsonObject.getString("province");
				if(StringUtils.equals(province, "未知")){
					return null;
				}
				Map<String, Object> map = new HashMap<String, Object>(2);
				map.put(RespMapKey.province.name(), province);
				if(jsonObject.containsKey("isp")){
					map.put(RespMapKey.supplier.name(), jsonObject.getString("isp"));
				}else{
					map.put(RespMapKey.supplier.name(), "");
				}
				return map;
			}
		} catch (Exception e) {
			logger.error(String.format("从拍拍查询手机归属地%s异常", phoneNo),e);
		}
		
		return null;
	}
	
	public String toString(){
		return "拍拍";
	}

}
