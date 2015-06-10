package com.phone.callerloc.query;

import java.util.HashMap;
import java.util.Map;

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
 * <br />淘宝手机归属地查询
 * @author leo
 * @mender：（文件的修改者，文件创建者之外的人）
 * @version 1.0
 * Remark：<p>
 * 淘宝网
	API地址： http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=15850781443
	参数：
	tel：手机号码
	返回：JSON
 * </p>
 */
public class TaobaoPhoneLocal extends AbstractPhoneLocal {
	
	
	public TaobaoPhoneLocal(){
		
	}
	public TaobaoPhoneLocal(IPhoneLocal next) {
		super(next);
	}

	/*
	 *LOG
	 */
	private transient Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 使用淘宝API 查询归属地
	       仅支持字符集GBK
	 */
	private static final String GET_WAY = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm";

	/**
	 * 
	 * 功能:电话归属地查询
	 * <p><code style="color:red;">
	 * Error:
	 * 	__GetZoneResult_ = {
		province:''
		}
	 * </code>
	 * </p>
	 * <p>
	 * <code style="color:green;">
	 * Success:
	 * 	__GetZoneResult_ = {
     *	mts:'1581028',
     *	province:'北京',
     *	catName:'中国移动',
     *	telString:'15810283539',
	 *	areaVid:'29400',
	 *	ispVid:'3236139',
	 *	carrier:'北京移动'
	 *	}
	 * </code>
	 * </p> 
	 *<br /> 作者: leo
	 * <br />创建日期:2015年6月9日
	 * <br />修改者: mender
	 * <br />修改日期: modifydate
	 * @param phoneNo
	 * @return
	 * 
	 */
	@Override
	protected Map<String, Object> searchPhoneNumber(String phoneNo) {
		try {
			String result = HttpUtil.sendRequest(GET_WAY, String.format("tel=%s", phoneNo),HttpCharset.GBK,HttpReqMethod.POST);
			result = result.split("=")[1];
			logger.info("phoneNo:{}归属地查询, 淘宝返回结果：{}",phoneNo,result);
			JSONObject json = JSONObject.fromObject(result);
			String province = json.getString("province");
			if(StringUtils.isBlank(province)){
				return null;
			}
			
			Map<String, Object> map = new HashMap<String, Object>(2);
			map.put(RespMapKey.province.name(), province);
			if(json.containsKey("carrier")){
				map.put(RespMapKey.supplier.name(), json.getString("carrier"));
			}else{
				map.put(RespMapKey.supplier.name(), "");
			}
			return map;
		} catch (Exception e) {
			logger.error(String.format("从支付宝查询手机归属地%s异常", phoneNo),e);
			return null;
		}
	}
	
	public String toString(){
		return "淘宝";
	}

}
