package weather.ppx.com.weatherapp.http;

import android.app.Activity;
import android.app.Dialog;

import weather.ppx.com.weatherapp.Util.LogUtil;


/**
 * 回调接口，使用OA系统的程序进行实现
 * 
 */
public class CallBack {
	public static final String TAG = "CallBack";
	
	Activity mContext;
	private Dialog dialog;
	
	public CallBack(Activity con) {
		// TODO Auto-generated constructor stub
		mContext=con;
	}
	
	/**
	 * 成功回调函数
	 * 
	 * @param jsonMessage 回调成功字符串信息
	 */
	public void onSuccess(String method,String jsonMessage){
		LogUtil.e(TAG, "onSuccess:"+jsonMessage);
	}
	
	/**
	 * 失败回调函数
	 * 
	 * @param jsonMessage 回调失败字符串信息
	 */
	public void onFailure(String method,String jsonMessage){
		LogUtil.e(TAG, "onFailure:" + jsonMessage);
	}
	
	/**
	 * 处理HTTP网络连接异常
	 * @param jsonMessage
	 */
	public void onHTTPException(String method,String jsonMessage){
		LogUtil.e(TAG, "onHTTPException:"+jsonMessage);
	}
	
	/**
	 * 处理未登陆事件
	 * @param jsonMessage
	 */
	public void onUnLogin(String jsonMessage){
		LogUtil.e(TAG, "onUnLogin:"+jsonMessage);
	}
	
	/**
	 * 没有sim卡，或sim无法使用的情况下触发的事件
	 * @param jsonMessage
	 */
	public void onUnSIM(String jsonMessage){
		LogUtil.e(TAG, "onUnSIM:"+jsonMessage);
	}
	
	/**
	 * 取消后的回调
	 */
	public void onCancel(){
		LogUtil.e(TAG, "onCancel");
	}
}
