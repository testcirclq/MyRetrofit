package com.yanxuwen.retrofit.Msg;

import java.util.LinkedList;
import java.util.List;


/**
 * 推送到每个继承Observer的类，然后在该类判断返回类型是否符合自己，符合的话，执行自己想要的操作
 */
public class Publisher {
	ObserverListener mObs;
	/**
	 * 添加想要推送的类
	 */
	public void setOb(ObserverListener ob){
		this.mObs=ob;
	}
	/**
	 * 移除要推送的类
	 */
	public void removeOb(){
		mObs=null;
	}
	/**
	 *自定义推送方法数据请求的放回结果
	 */
	public void publishdata(ObserverListener obl,ObserverListener.STATUS status,String type,Object object) {
		obl.onNotifyData(status,type, object);
	}
}
