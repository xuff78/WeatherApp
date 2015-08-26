package weather.ppx.com.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import weather.ppx.com.weatherapp.Fragment.BaseFragment;
import weather.ppx.com.weatherapp.View.SlipButton;

/**
 * Created by Administrator on 2015/8/25.
 */
public class WeatherSetting extends BaseActivity {

    SlipButton btnLocation, btnPush;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_weather_setting_frg);

        _setHeaderTitle("设置");
        initView();
    }

    private void initView() {
        btnLocation=(SlipButton)findViewById(R.id.btnLocation);
        btnPush=(SlipButton)findViewById(R.id.btnPush);

    }
}
