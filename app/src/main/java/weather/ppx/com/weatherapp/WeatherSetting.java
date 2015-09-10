package weather.ppx.com.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import weather.ppx.com.weatherapp.Fragment.BaseFragment;
import weather.ppx.com.weatherapp.Util.ConstantUtil;
import weather.ppx.com.weatherapp.Util.SharedPreferencesUtil;
import weather.ppx.com.weatherapp.View.SlipButton;

/**
 * Created by Administrator on 2015/8/25.
 */
public class WeatherSetting extends BaseActivity {

    SlipButton btnLocation, btnPush;
    private ImageView themeChecked1, themeChecked2, bgImg1, bgImg2;
    int bgType=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_weather_setting_frg);

        _setLeftBackListener(R.drawable.icon_app_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        _setHeaderTitle("设置");
        initView();
    }

    private void initView() {
        bgType=SharedPreferencesUtil.getInt(this, "BgType", 0);
        btnLocation=(SlipButton)findViewById(R.id.btnLocation);
        btnLocation.setNowChoose(SharedPreferencesUtil.getValue(WeatherSetting.this, ConstantUtil.AutoLocation, true));
        btnLocation.SetOnChangedListener(new SlipButton.OnChangedListener() {
            @Override
            public void OnChanged(boolean CheckState) {
                SharedPreferencesUtil.setValue(WeatherSetting.this, ConstantUtil.AutoLocation, CheckState);
            }
        });
        btnPush=(SlipButton)findViewById(R.id.btnPush);

        bgImg1=(ImageView)findViewById(R.id.bgImg1);
        themeChecked1=(ImageView)findViewById(R.id.themeChecked1);
        bgImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeChecked1.setVisibility(View.VISIBLE);
                themeChecked2.setVisibility(View.GONE);
                SharedPreferencesUtil.setInt(WeatherSetting.this, "BgType", 0);
            }
        });
        bgImg2=(ImageView)findViewById(R.id.bgImg2);
        themeChecked2=(ImageView)findViewById(R.id.themeChecked2);
        bgImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeChecked2.setVisibility(View.VISIBLE);
                themeChecked1.setVisibility(View.GONE);
                SharedPreferencesUtil.setInt(WeatherSetting.this, "BgType", 1);
            }
        });
        if(bgType==0){
            themeChecked1.setVisibility(View.VISIBLE);
        }else if(bgType==1){
            themeChecked2.setVisibility(View.VISIBLE);
        }
    }
}
