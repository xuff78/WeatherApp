package weather.ppx.com.weatherapp.Bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/8.
 */
public class AreaWorkingInfo {
    private String city_code="";
    private String city_name="";
    private String date_time="";
    private ArrayList<WeatherInfo> detail=new ArrayList<>();

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public ArrayList<WeatherInfo> getDetail() {
        return detail;
    }

    public void setDetail(ArrayList<WeatherInfo> detail) {
        this.detail = detail;
    }
}
