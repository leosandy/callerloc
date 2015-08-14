package com.phone.callerloc.query;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractPhoneLocal implements IPhoneLocal {

	private static final String [] charteredCitys = new String[]{"北京","上海","天津","重庆"};
	/**
	 * LOG
	 */
	private transient Logger logger = LoggerFactory.getLogger(getClass());
	protected IPhoneLocal next;
	

	public AbstractPhoneLocal(){
		
	}
	
	public AbstractPhoneLocal(IPhoneLocal next) {
		this.next = next;
	}
	
	public Map<String, Object> getPhoneLocal(String phoneNo) {
		logger.info("从 {} 获取号码归属地信息",this);
		Map<String, Object> respMap = searchPhoneNumber(phoneNo);
		if(respMap == null){
			if(this.next != null){
				return next.getPhoneLocal(phoneNo);
			}
		}
		return respMap;
	}
	
	protected boolean isCharteredCity(String province){
		for (String city : charteredCitys) {
			if(city.equals(province)){
				return true;
			}
		}
		return false;
	}
	
	
	public static void main(String[] args) {
		System.out.println();
	}
	protected abstract Map<String, Object> searchPhoneNumber(String phoneNo);

	 enum RespMapKey{
		 /**
		  * 省份
		  */
		province,
		/**
		 * 城市
		 */
		city,
		/**
		 * 供应商
		 */
		supplier
	}
}
