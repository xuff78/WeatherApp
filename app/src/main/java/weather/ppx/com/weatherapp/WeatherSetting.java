package weather.ppx.com.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import weather.ppx.com.weatherapp.Fragment.BaseFragment;
import weather.ppx.com.weatherapp.View.SlipButton;

/**
 * Created by Administrator on 2015/8/25.
 */
public class WeatherSetting extends BaseActivity {

    SlipButton btnLocation, btnPush;
    private ImageView themeChecked1, themeChecked2, bgImg1, bgImg2;


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

        bgImg1=(ImageView)findViewById(R.id.bgImg1);
        themeChecked1=(ImageView)findViewById(R.id.themeChecked1);
        bgImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeChecked1.setVisibility(View.VISIBLE);
                themeChecked2.setVisibility(View.GONE);
            }
        });
        bgImg2=(ImageView)findViewById(R.id.bgImg2);
        themeChecked2=(ImageView)findViewById(R.id.themeChecked2);
        bgImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeChecked2.setVisibility(View.VISIBLE);
                themeChecked1.setVisibility(View.GONE);
            }
        });
    }
}
