/**
 *  Author :  hmg25
 *  Description :
 */
package weather.ppx.com.weatherapp.http;

import java.util.HashMap;

import android.app.Activity;

import weather.ppx.com.weatherapp.Util.ConstantUtil;
import weather.ppx.com.weatherapp.Util.LogUtil;

/**
 * hmg25's android Type
 * 
 * @author Administrator
 * 
 */
public class HttpHandler extends Handle {

	private Activity mContext;

	public HttpHandler(Activity mContext, CallBack mCallBack) {
		super(mContext, mCallBack);
		this.mContext = mContext;
	}

	/**
	 * 获取所有列表
	 * 
	 */
//	public void getHomeInfo() {
//		HashMap<String, String> paramMap = new HashMap<String, String>();
//		paramMap.put("version_code", Constantparams.VERSION_CODE);
//		request(ConstantUtil.method_getHomeInfo, paramMap);
	
	public void getLocation(String La, String lo) {
		String url="http://api.map.baidu.com/geocoder/v2/?ak=nOTBQ1fXDOYG3z3qXsi8yFjX" +
				"&location="+La+","+lo+"&output=json&pois=1&mcode=" +
				"B9:80:60:E1:D4:5C:C0:00:C3:11:3F:F8:A1:70:3B:E3;weather.ppx.com.weatherapp";
		LogUtil.i("Location", "url: " + url);
		new HttpAsyncTask(mContext, this, false).execute(url, ConstantUtil.Method_Loaction, null, "", true);
	}

	public void getCityPredict(String cityCode) {
		request(ConstantUtil.Method_CityPredict, cityCode);
	}

	public void getCityRefined(String cityCode) {
		request(ConstantUtil.Method_CityRefined, cityCode);
	}

	public void getCityReal(String cityCode) {
		request(ConstantUtil.Method_CityReal, cityCode);
	}

	public void getSeaInfo(String cityCode) {
		request(ConstantUtil.Method_SeaInfo, cityCode);
	}

	public void getMapInfo() {
		request(ConstantUtil.Method_SeaInfo, "map");
	}

	protected void request(String method, String citycode) {
		request(method, citycode, true, true);
	}

	protected void requestNoDialog(String url, String citycode) {
		request(url, citycode, false, true);
	}

	protected void request(String method, String citycode, boolean showDialog, boolean canBeCancel) {
		String progressInfo = "";
		String url= ConstantUtil.Api_Url+method+citycode+".json";
		LogUtil.i("Location","url: "+url);
		HashMap<String, String> params=new HashMap<>();
		new HttpAsyncTask(mContext, this, showDialog)
				.execute(url, method, params, progressInfo, true);
	}
}
