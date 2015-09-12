package weather.ppx.com.weatherapp.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import weather.ppx.com.weatherapp.Bean.AreaWorkingInfo;
import weather.ppx.com.weatherapp.Bean.DayNightInfo;
import weather.ppx.com.weatherapp.Bean.WeatherInTIme;
import weather.ppx.com.weatherapp.Bean.WeatherInfo;
import weather.ppx.com.weatherapp.Fragment.WeatherInfoFrg;

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
                for(int j=0;j<arrayItem.length();j++) {
                    WeatherInTIme item = new WeatherInTIme();
                    JSONObject itemObj = arrayItem.getJSONObject(j);
                    item.setDt(itemObj.getString("dt"));
                    item.setImg(itemObj.getString("img"));
                    item.setInfo(itemObj.getString("info"));
                    item.setMaxtemp(itemObj.getString("maxtemp"));
                    item.setMintemp(itemObj.getString("mintemp"));
                    item.setDirect(itemObj.getString("direct"));
                    item.setPower(itemObj.getString("power"));
                    item.setTime(itemObj.getString("time"));
                    dayInfo.add(item);
                }
                infos.add(dayInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return infos;
    }

    public static AreaWorkingInfo getWorkingInfo(String jsonStr){
        AreaWorkingInfo workingInfo=new AreaWorkingInfo();
        try {
            JSONObject data=new JSONObject(jsonStr);
            workingInfo.setCity_code(data.getString("city_code"));
            workingInfo.setCity_name(data.getString("city_name"));
            workingInfo.setDate_time(data.getString("date_time"));
            JSONArray array=data.getJSONArray("detail");
            ArrayList<WeatherInfo> infos=new ArrayList<WeatherInfo>();
            for(int i=0;i<array.length();i++){
                WeatherInfo item=new WeatherInfo();
                JSONObject itemObj=array.getJSONObject(i);
                item.setDt(itemObj.getString("dt"));
                item.setTime(itemObj.getString("time"));
                item.setSafe(itemObj.getString("safe"));
                item.setWaDT(itemObj.getString("waDT"));
//                item.setImg(itemObj.getString("img"));
                item.settL(itemObj.getString("tL"));
                item.setWaH(itemObj.getString("waH"));
                item.setWiDT(itemObj.getString("wiDT"));
                item.setWiDV(itemObj.getString("wiDV"));
                item.setWiSI(itemObj.getString("wiSI"));
                item.setWiSV(itemObj.getString("wiSV"));
                item.setWaDV(itemObj.getString("waDV"));
                infos.add(item);
            }
            workingInfo.setDetail(infos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return workingInfo;
    }

    public static ArrayList<AreaWorkingInfo> getMapInfo(String jsonStr){
        ArrayList<AreaWorkingInfo> workingInfo=new ArrayList<AreaWorkingInfo>();
        try {
            JSONArray data=new JSONArray(jsonStr);
            for (int i=0;i<data.length();i++){
                workingInfo.add(getWorkingInfo(data.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  workingInfo;
    }
}
