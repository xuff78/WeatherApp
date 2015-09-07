package weather.ppx.com.weatherapp.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import weather.ppx.com.weatherapp.Bean.DayNightInfo;
import weather.ppx.com.weatherapp.Bean.WeatherInTIme;

/**
 * Created by Administrator on 2015/9/7.
 */
public class JsonUtil {

    public static ArrayList<DayNightInfo> getDayNightInfo(String jsonStr){
        ArrayList<DayNightInfo> infos=new ArrayList<DayNightInfo>();
        try {
            JSONObject data=new JSONObject(jsonStr);
            JSONArray array=data.getJSONArray("detail");
            for(int i=0;i<array.length();i++){
                DayNightInfo item=new DayNightInfo();
                JSONObject itemObj=array.getJSONObject(i);
                item.setDate(itemObj.getString("date"));
                item.setDayWeatherImg(itemObj.getJSONObject("day").getJSONObject("weather").getString("img"));
                item.setNightWeatherImg(itemObj.getJSONObject("night").getJSONObject("weather").getString("img"));
                infos.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return infos;
    }

    public static ArrayList<ArrayList<WeatherInTIme>> getWeatherInTimes(String jsonStr){
        ArrayList<ArrayList<WeatherInTIme>> infos=new ArrayList<>();
        try {
            JSONObject data=new JSONObject(jsonStr);
            JSONArray array=data.getJSONArray("detail");
            for(int i=0;i<array.length();i++){
                ArrayList<WeatherInTIme> dayInfo=new ArrayList<WeatherInTIme>();
                JSONArray arrayItem=array.getJSONArray(i);
                for(int j=0;j<array.length();j++) {
                    WeatherInTIme item = new WeatherInTIme();
                    JSONObject itemObj = arrayItem.getJSONObject(j);
                    item.setDt(itemObj.getString("dt"));
                    item.setImg(itemObj.getString("img"));
                    item.setInfo(itemObj.getString("info"));
                    item.setInfo(itemObj.getString("maxtemp"));
                    item.setInfo(itemObj.getString("mintemp"));
                    item.setInfo(itemObj.getString("direct"));
                    item.setInfo(itemObj.getString("power"));
                    dayInfo.add(item);
                }
                infos.add(dayInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return infos;
    }
}
