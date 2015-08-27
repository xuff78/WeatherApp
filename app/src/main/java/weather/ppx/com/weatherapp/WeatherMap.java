package weather.ppx.com.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import weather.ppx.com.weatherapp.Fragment.BaseFragment;

/**
 * Created by Administrator on 2015/8/25.
 */
public class WeatherMap extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);

        _setHeaderTitle("地图");
        initView();
    }

    private void initView() {

    }

}

