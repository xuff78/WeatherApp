package weather.ppx.com.weatherapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import weather.ppx.com.weatherapp.R;

/**
 * Created by Administrator on 2015/8/25.
 */
public class WeatherSetting extends BaseFragment {


    // TODO: Rename and change types and number of parameters
    public static WeatherSetting newInstance() {
        WeatherSetting fragment = new WeatherSetting();
        return fragment;
    }

    LinearLayout topDaysInfoLayout, bottomDayInfoLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_weather_info_frg2, container, false);
        return v;
    }
}
