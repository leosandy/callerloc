package com.phone.callerloc.query;

import java.util.Map;

/**
 * 
 * 创建日期:2015年6月9日
 * <br />手机归属地查询
 * @author leo
 * @mender：（文件的修改者，文件创建者之外的人）
 * @version 1.0
 * Remark：认为有必要的其他信息
 */
public interface IPhoneLocal {

	/**
	 * 
	 * 功能:查询归属地
	 *<br /> 作者: leo
	 * <br />创建日期:2015年6月9日
	 * <br />修改者: mender
	 * <br />修改日期: modifydate
	 * @param phoneNo
	 * @return
	 */
	public Map<String, Object> getPhoneLocal(String phoneNo);
}
