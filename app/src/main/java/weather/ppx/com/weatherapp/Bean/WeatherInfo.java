package weather.ppx.com.weatherapp.Bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by 可爱的蘑菇 on 2015/9/7.
 */
public class WeatherInfo extends EBaseModel {
    private String dt="";
    private String time="";
    private String safe="";
    private String waDT="";
    private String tL="";
    private String waH="";
    private String wiDT="";
    private String wiDV="";
    private String wiSI="";
    private String wiSV="";
    private String waDV="";

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSafe() {
        return safe;
    }

    public void setSafe(String safe) {
        this.safe = safe;
    }

    public String getWaDT() {
        return waDT;
    }

    public void setWaDT(String waDT) {
        this.waDT = waDT;
    }

    public String gettL() {
        return tL;
    }

    public void settL(String tL) {
        this.tL = tL;
    }

    public String getWaH() {
        return waH;
    }

    public void setWaH(String waH) {
        this.waH = waH;
    }

    public String getWiDT() {
        return wiDT;
    }

    public void setWiDT(String wiDT) {
        this.wiDT = wiDT;
    }

    public String getWiDV() {
        return wiDV;
    }

    public void setWiDV(String wiDV) {
        this.wiDV = wiDV;
    }

    public String getWiSI() {
        return wiSI;
    }

    public void setWiSI(String wiSI) {
        this.wiSI = wiSI;
    }

    public String getWiSV() {
        return wiSV;
    }

    public void setWiSV(String wiSV) {
        this.wiSV = wiSV;
    }

    public String getWaDV() {
        return waDV;
    }

    public void setWaDV(String waDV) {
        this.waDV = waDV;
    }
}
