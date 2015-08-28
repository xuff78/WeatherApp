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
import android.widget.ImageView;
import android.widget.LinearLayout;

import weather.ppx.com.weatherapp.R;
import weather.ppx.com.weatherapp.Util.ActUtil;
import weather.ppx.com.weatherapp.Util.ScreenUtil;

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
    View selectedView=null;

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
        int toppadding=ScreenUtil.dip2px(getActivity(),5);
        for (int i=0; i<6; i++){
            final LinearLayout layout=new LinearLayout(getActivity());
            layout.setPadding(0,toppadding,0,toppadding);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams llp=new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.weight=1;
            layout.setLayoutParams(llp);
            layout.addView(ActUtil.getTextView(getActivity(), "周一", 15));
            layout.addView(ActUtil.getImageView(getActivity(), R.drawable.weather_sun_s, 20, 8));
            layout.addView(ActUtil.getImageView(getActivity(), R.drawable.weather_sun_cloud_s, 20,8));
            layout.setBackgroundResource(R.drawable.weather_day_selector);
            topDaysInfoLayout.addView(layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedView.setSelected(false);
                    layout.setSelected(true);
                    selectedView = layout;
                    setBottomInfo();
                }
            });
            if(i==0){
                layout.setSelected(true);
                selectedView=layout;
            }
        }

        setBottomInfo();
    }

    private void setBottomInfo(){
        bottomDayInfoLayout.removeAllViews();
        int marginTop= ScreenUtil.dip2px(getActivity(), 8);
        for (int i=0; i<9; i++){
            LinearLayout layout=new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams llp=new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.topMargin=marginTop;
            layout.setLayoutParams(llp);
            layout.addView(ActUtil.getTextView(getActivity(), "06-12时   |   ", 14));
            layout.addView(ActUtil.getImageView(getActivity(), R.drawable.weather_rain_s, 20));
            layout.addView(ActUtil.getTextView(getActivity(), "晴转多云", 14, 1));
            layout.addView(ActUtil.getTextView(getActivity(), "28°/30°", 14, 1));
            layout.addView(ActUtil.getTextView(getActivity(), "东南风", 14, 1));
            layout.addView(ActUtil.getTextView(getActivity(), "3-4级", 14, 1));
            bottomDayInfoLayout.addView(layout);
        }
    }
}
