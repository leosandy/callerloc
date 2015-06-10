package com.phone.callerloc;

import java.util.Map;

import org.junit.Test;

import com.phone.callerloc.query.IPhoneLocal;
import com.phone.callerloc.query.PaypayPhoneLocal;
import com.phone.callerloc.query.TaobaoPhoneLocal;
import com.phone.callerloc.query.TenPayPhoneLocal;

/**
 * 
 * 创建日期:2015年6月9日
 * <br />Unit Test
 * @author leo
 * @mender：（文件的修改者，文件创建者之外的人）
 * @version 1.0
 * Remark：认为有必要的其他信息
 */
public class QueryPhoneLocTest {
	
	/**
	 * 
	 * 功能:淘宝
	 *<br /> 作者: leo
	 * <br />创建日期:2015年6月9日
	 * <br />修改者: mender
	 * <br />修改日期: modifydate
	 */
	@Test
	public void taobao(){
		IPhoneLocal search = new TaobaoPhoneLocal();
		Map<String, Object> respMap = search.getPhoneLocal("13800138000");
		System.out.println(respMap);
	}
	
	/**
	 * 
	 * 功能:财付通
	 *<br /> 作者: leo
	 * <br />创建日期:2015年6月9日
	 * <br />修改者: mender
	 * <br />修改日期: modifydate
	 */
	@Test
	public void tenpay(){
		IPhoneLocal search = new TenPayPhoneLocal();
		Map<String, Object> respMap = search.getPhoneLocal("13800138000");
		System.out.println(respMap);
	}
	
	/**
	 * 
	 * 功能:拍拍
	 *<br /> 作者: leo
	 * <br />创建日期:2015年6月9日
	 * <br />修改者: mender
	 * <br />修改日期: modifydate
	 */
	@Test
	public void paypay(){
		IPhoneLocal search = new PaypayPhoneLocal();
		Map<String, Object> respMap = search.getPhoneLocal("13800138000");
		System.out.println(respMap);
	}
	
	/**
	 * 
	 * 功能:责任链查找
	 *<br /> 作者: leo
	 * <br />创建日期:2015年6月9日
	 * <br />修改者: mender
	 * <br />修改日期: modifydate
	 */
	@Test
	public void searchByChain(){
		IPhoneLocal search = new TaobaoPhoneLocal(new TenPayPhoneLocal(new PaypayPhoneLocal()));
		Map<String, Object> respMap = search.getPhoneLocal("13800138000");
		System.out.println(respMap);
	}

}
