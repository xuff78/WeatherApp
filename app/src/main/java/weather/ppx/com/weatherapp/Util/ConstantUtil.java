package weather.ppx.com.weatherapp.Util;

import weather.ppx.com.weatherapp.R;

public class ConstantUtil {

//	public static final String AreCode="AreCode";
	public static final String AreName="AreName";
	public static final String AutoLocation="AutoLocation";
	public static final String SelectArea="SelectArea";

	public static final String[] cityName={"赣榆县", "灌云县", "响水县", "滨海县", "射阳县", "大丰市", "东台市", "如东县", "启东市"};
	public static final String[] areaNames={"赣榆", "灌云" , "响水", "滨海", "射阳", "大丰", "东台", "如东", "启东"};
	public static final String[] areaCodes={"101191003", "101191004" , "101190702", "101190703", "101190705", "101190708", "101190707", "101190504", "101190507"};
	public static final int[] weatherRes={R.mipmap.weather_icon_0, R.mipmap.weather_icon_1, R.mipmap.weather_icon_2, R.mipmap.weather_icon_3
			, R.mipmap.weather_icon_4, R.mipmap.weather_icon_5, R.mipmap.weather_icon_6, R.mipmap.weather_icon_7, R.mipmap.weather_icon_8
			, R.mipmap.weather_icon_9, R.mipmap.weather_icon_10, R.mipmap.weather_icon_11, R.mipmap.weather_icon_12, R.mipmap.weather_icon_13
			, R.mipmap.weather_icon_14, R.mipmap.weather_icon_15, R.mipmap.weather_icon_16, R.mipmap.weather_icon_17, R.mipmap.weather_icon_18
			, R.mipmap.weather_icon_19, R.mipmap.weather_icon_20, R.mipmap.weather_icon_21, R.mipmap.weather_icon_22, R.mipmap.weather_icon_23
			, R.mipmap.weather_icon_24, R.mipmap.weather_icon_25, R.mipmap.weather_icon_26, R.mipmap.weather_icon_27, R.mipmap.weather_icon_28
			, R.mipmap.weather_icon_29, R.mipmap.weather_icon_30, R.mipmap.weather_icon_31, R.mipmap.weather_icon_32, R.mipmap.weather_icon_33
			, R.mipmap.weather_icon_36, R.mipmap.weather_icon_53};
	
	public static final String Api_Url="http://222.190.109.118/Data/Products/app/";

	public static final String LocationInfo="http://222.190.109.118/Data/Products/app/";
	public static final String Method_Loaction="Location";
	public static final String Method_CityPredict="city/predict/";
	public static final String Method_CityReal="city/real/";
	public static final String Method_CityRefined="city/refined/";
	public static final String Method_SeaInfo="sea/info/";
}
