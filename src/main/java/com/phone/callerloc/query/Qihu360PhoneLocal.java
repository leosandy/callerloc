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
 * <br />360手机归属地查询
 * @author leo
 * @mender：（文件的修改者，文件创建者之外的人）
 * @version 1.0
 * Remark：<p>
 * 淘宝网
	API地址： http://cx.shouji.360.cn/phonearea.php?number=13800138000
	参数：
	tel：手机号码
	返回：JSON
 * </p>
 */
public class Qihu360PhoneLocal extends AbstractPhoneLocal {
	
	public Qihu360PhoneLocal(){
		
	}
	public Qihu360PhoneLocal(IPhoneLocal next) {
		super(next);
	}

	/*
	 *LOG
	 */
	private transient Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 使用360API 查询归属地
	 */
	private static final String GET_WAY = "http://cx.shouji.360.cn/phonearea.php";

	/**
	 * 
	 * 功能:电话归属地查询
	 * <p>返回结果：<code style="color:red;">
	 * {"code":0,"data":{"province":"\u5317\u4eac","city":"","sp":"\u79fb\u52a8"}}
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
			String result = HttpUtil.sendRequest(String.format("%s?number=%s",GET_WAY,phoneNo),null,HttpCharset.UTF8,HttpReqMethod.GET);
			logger.info("phoneNo:{}归属地查询, 奇虎360返回结果：{}",phoneNo,result);
			JSONObject json = JSONObject.fromObject(result);
			if(json.getInt("code") != 0){
				return null;
			}
			
			json = json.getJSONObject("data");
			String province = json.getString("province");
			
			if(StringUtils.isBlank(province)){
				return null;
			}
			
			Map<String, Object> map = new HashMap<String, Object>(2);
			map.put(RespMapKey.province.name(), province);
			map.put(RespMapKey.supplier.name(), json.containsKey("sp") ? json.getString("sp").replaceAll("\\s+", ""):"");
			String city = json.containsKey("city") ?json.getString("city").replaceAll("\\s+", ""):"";
			//直辖市 省、市相同
			if(StringUtils.isBlank(city) && isCharteredCity(province)){
				city = province;
			}
			map.put(RespMapKey.city.name(), city);
			
			return map;
		} catch (Exception e) {
			logger.error(String.format("从奇虎360查询手机归属地%s异常", phoneNo),e);
			return null;
		}
	}
	
	public String toString(){
		return "奇虎360";
	}

}
