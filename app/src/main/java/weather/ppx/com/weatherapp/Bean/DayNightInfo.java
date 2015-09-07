package weather.ppx.com.weatherapp.Bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by Administrator on 2015/9/7.
 */
public class DayNightInfo extends EBaseModel {

    private String dayWeatherImg="";
    private String nightWeatherImg="";
    private String date="";

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayWeatherImg() {
        return dayWeatherImg;
    }

    public void setDayWeatherImg(String dayWeatherImg) {
        this.dayWeatherImg = dayWeatherImg;
    }

    public String getNightWeatherImg() {
        return nightWeatherImg;
    }

    public void setNightWeatherImg(String nightWeatherImg) {
        this.nightWeatherImg = nightWeatherImg;
    }
}
