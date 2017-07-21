package com.yanxuwen.retrofit.Msg;


public interface ObserverListener {
	public enum STATUS{
		SUCCESS,FAIL,TIMEOUT,ERROR,SPECIAL
	}
//	/**
//	 * 推送方法
//	 */
//	public void onNotify(int type, JSONObject jb, Msgs.Data msgs_data);
//	/**
//	 * 自定义推送方法
//	 */
//	public void onNotify(int type, Object object);

	/**
	 * 服务器请求返回的结果
	 * @param status  请求成功还是失败
	 * @param object  类型
	 */
	public void onNotifyData(STATUS status, String type, Object object);
}
