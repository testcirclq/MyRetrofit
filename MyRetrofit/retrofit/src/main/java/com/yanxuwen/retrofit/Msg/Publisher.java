package com.yanxuwen.retrofit.Msg;

import java.util.LinkedList;
import java.util.List;


/**
 * 推送到每个继承Observer的类，然后在该类判断返回类型是否符合自己，符合的话，执行自己想要的操作
 */
public class Publisher {
	private List<ObserverListener> mObs = new LinkedList<ObserverListener>();
	private static Publisher ins = null;
	public static Publisher getInstance(){
		if(ins == null){
			ins = new Publisher();
		}
		return ins;
	}
	/**
	 * 添加想要推送的类
	 */
	public void addOb(ObserverListener ob){
		if(!mObs.contains(ob)){
			mObs.add(ob);
		}
	}
	/**
	 * 移除要推送的类
	 */
	public void removeOb(ObserverListener ob){
		mObs.remove(ob);
	}
//	 /**
//	 * 后台推送的消息类型
//	 */
//	public void publish(int type,JSONObject jb,Msgs.Data msgs_data) {
//		 for (int i = 0; i < mObs.size(); i++) {
//			 ObserverListener ob = mObs.get(i);
//	        	ob.onNotify(type,jb,msgs_data);
//	        }
//	 }
//	/**
//	 *自定义推送方法
//	 */
//	public void publish(int type,Object object) {
//		 for (int i = 0; i < mObs.size(); i++) {
//			 ObserverListener ob = mObs.get(i);
//	        	ob.onNotify(type,object);
//	        }
//	 }
	/**
	 *自定义推送方法数据请求的放回结果
	 */
	public void publishdata(ObserverListener obl,ObserverListener.STATUS status,String type,Object object) {
		for (int i = 0; i < mObs.size(); i++) {
			ObserverListener ob = mObs.get(i);
			if(obl!=null&&obl==ob){
				ob.onNotifyData(status,type, object);
			}else if(obl==null){
				ob.onNotifyData(status,type, object);
			}

		}
	}
	public void clear() {
		mObs.clear();
	}
}
