package weather.ppx.com.weatherapp.http;

import android.content.Context;
import android.widget.Toast;

import common.eric.com.ebaselibrary.util.ToastUtils;
import weather.ppx.com.weatherapp.Util.JsonValidator;


/**
 * 业务逻辑处理类，负责联网的调用，及对数据的处理。此超类中封装了对联网模块的调用。
 */
public abstract class Handle implements HttpCallback {
	
	private static final String TAG = "Handle";

	/**
	 * 用于在HttpAsyncTask类中显示UI（显示进度条）
	 */
	protected Context mContext;

	/**
	 * 回调对象
	 */
	protected CallBack mCallBack;

	/**
	 * UI界面用于区分不同请求的页面请求id
	 */
	protected int mUiReqId;

	/**
	 * 唯一构造函数，仅包内子类可见
	 * 
	 * @param uiReqId
	 *            各ui的请求id
	 * @param context
	 *            Context对象
	 * @param callback
	 *            HandleCallback对象
	 */
	protected Handle(Context context, CallBack callback) {
		mContext = context;
		mCallBack = callback;
	}
	
	/**
	 * 处理请求返回的结果
	 */
	public void httpCallback(String reqUrl, String result) {
//		result = result.replaceAll("\\r\\n\\t", "");
//		if(result.startsWith("{")){//new JsonValidator().validate(result)){
		if(result.equals(GlbsNet.HTTP_ERROR_MESSAGE)) {
			ToastUtils.show(mContext, result);
			mCallBack.onHTTPException(reqUrl,result);
		}else
			mCallBack.onSuccess(reqUrl,result);
//		}else{
//			mCallBack.onHTTPException(reqUrl,result);
//		}
		
	}
}
