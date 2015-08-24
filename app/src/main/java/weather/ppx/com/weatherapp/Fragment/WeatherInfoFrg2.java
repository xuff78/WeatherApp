package weather.ppx.com.weatherapp.Fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import weather.ppx.com.weatherapp.R;
import weather.ppx.com.weatherapp.Util.ActUtil;

/**
 * Created by 可爱的蘑菇 on 2015/8/23.
 */
public class WeatherInfoFrg2  extends BaseFragment {


    // TODO: Rename and change types and number of parameters
    public static WeatherInfoFrg2 newInstance() {
        WeatherInfoFrg2 fragment = new WeatherInfoFrg2();
        return fragment;
    }

    LinearLayout topDaysInfoLayout, bottomDayInfoLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_weather_info_frg2, container, false);

        topDaysInfoLayout= (LinearLayout) v.findViewById(R.id.topDaysInfoLayout);
        bottomDayInfoLayout= (LinearLayout) v.findViewById(R.id.bottomDayInfoLayout);
        setTopInfoItem();
        return v;
    }

    private void setTopInfoItem(){
        for (int i=0; i<6; i++){
            LinearLayout layout=new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams llp=new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.weight=1;
            layout.setLayoutParams(llp);
            layout.addView(ActUtil.getTextView(getActivity(), "周一", 14));
            layout.addView(ActUtil.getImageView(getActivity(), R.drawable.abc_btn_check_to_on_mtrl_000, 20));
            layout.addView(ActUtil.getImageView(getActivity(), R.drawable.abc_btn_check_to_on_mtrl_000, 20));
            topDaysInfoLayout.addView(layout);
        }
    }
}
