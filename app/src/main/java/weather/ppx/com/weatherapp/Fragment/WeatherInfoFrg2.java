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
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import weather.ppx.com.weatherapp.Bean.DayNightInfo;
import weather.ppx.com.weatherapp.Bean.WeatherInTIme;
import weather.ppx.com.weatherapp.R;
import weather.ppx.com.weatherapp.Util.ActUtil;
import weather.ppx.com.weatherapp.Util.ConstantUtil;
import weather.ppx.com.weatherapp.Util.JsonUtil;
import weather.ppx.com.weatherapp.Util.LogUtil;
import weather.ppx.com.weatherapp.Util.ScreenUtil;
import weather.ppx.com.weatherapp.Util.TimeUtil;
import weather.ppx.com.weatherapp.http.CallBack;
import weather.ppx.com.weatherapp.http.HttpHandler;

/**
 * Created by 可爱的蘑菇 on 2015/8/23.
 */
public class WeatherInfoFrg2  extends BaseFragment {


    // TODO: Rename and change types and number of parameters
    public static WeatherInfoFrg2 newInstance(String areaCode) {
        WeatherInfoFrg2 fragment = new WeatherInfoFrg2();
        Bundle b=new Bundle();
        b.putString("Code", areaCode);
        fragment.setArguments(b);
        return fragment;
    }

    LinearLayout topDaysInfoLayout, bottomDayInfoLayout;
    View selectedView=null;
    private HttpHandler weatherHandler, weatherHandler2;
    private String areaCode="";
    private ArrayList<DayNightInfo> dayNightInfos;
    private ArrayList<ArrayList<WeatherInTIme>> weatherinfos;
    private ImageView weatherIcon;
    private TextView temperatureTxt, windTxt, humidityTxt, publishTime;
    private ArrayList<TextView> txts=new ArrayList<>();
    private ArrayList<ImageView> imgs=new ArrayList<>();

    private void initHandler() {
        weatherHandler=new HttpHandler(getActivity(), new CallBack(getActivity()){
            @Override
            public void onSuccess(String method, String jsonMessage) {
                super.onSuccess(method, jsonMessage);
                if(getActivity()==null)
                    return;
//                LogUtil.i("Location", jsonMessage);
                if(method.equals(ConstantUtil.Method_CityPredict)){
                    dayNightInfos= JsonUtil.getDayNightInfo(jsonMessage);
                    weatherHandler.getCityRefined(areaCode);
                }else if(method.equals(ConstantUtil.Method_CityRefined)){
                    weatherinfos=JsonUtil.getWeatherInTimes(jsonMessage);
                    setTopInfoItem();
                    weatherHandler.getCityReal(areaCode);
                }else if(method.equals(ConstantUtil.Method_CityReal)){
                    try {
                        JSONObject obj=new JSONObject(jsonMessage);
                        String temperature=obj.getJSONObject("weather").getString("temperature");
                        String img=obj.getJSONObject("weather").getString("img");
                        String humidity=obj.getJSONObject("weather").getString("humidity");
                        String wind=obj.getJSONObject("wind").getString("power");
                        String date=obj.getString("data_time");
                        temperatureTxt.setText(temperature+"°");
                        humidityTxt.setText("相对湿度"+humidity+"%");
                        publishTime.setText( date+"  发布");
                        weatherIcon.setImageResource(ActUtil.getWeatherImg(img));
                        windTxt.setText(wind);
                        if(weatherinfos.get(0).size()>=3)
                            for(int i=0;i<3;i++) {
                                txts.get(i).setText(weatherinfos.get(0).get(i).getTime() + ":00 " + weatherinfos.get(0).get(i).getInfo());
                                imgs.get(i).setImageResource(ActUtil.getWeatherImg(weatherinfos.get(0).get(i).getImg()));
                            }
                        else {
                            for (int i = 0; i < weatherinfos.get(0).size(); i++) {
                                txts.get(i).setText(weatherinfos.get(0).get(i).getTime() + ":00 " + weatherinfos.get(0).get(i).getInfo());
                                imgs.get(i).setImageResource(ActUtil.getWeatherImg(weatherinfos.get(0).get(i).getImg()));
                            }
                            int FirstArraySize=weatherinfos.get(0).size();
                            int SeccendArraySize=3-FirstArraySize;
                            for (int j = 0; j < SeccendArraySize; j++) {
                                txts.get(FirstArraySize+j).setText(weatherinfos.get(1).get(j).getTime() + ":00 " + weatherinfos.get(1).get(j).getInfo());
                                imgs.get(FirstArraySize+j).setImageResource(ActUtil.getWeatherImg(weatherinfos.get(1).get(j).getImg()));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
//        weatherHandler2=new HttpHandler(getActivity(), new CallBack(getActivity()){
//            @Override
//            public void onSuccess(String method, String jsonMessage) {
//                super.onSuccess(method, jsonMessage);
////                LogUtil.i("Location", jsonMessage);
//                try {
//                    JSONObject obj=new JSONObject(jsonMessage);
//                    String temperature=obj.getJSONObject("weather").getString("temperature");
//                    String img=obj.getJSONObject("weather").getString("img");
//                    String humidity=obj.getJSONObject("weather").getString("humidity");
//                    String wind=obj.getJSONObject("wind").getString("power");
//                    temperatureTxt.setText(temperature+"°");
//                    humidityTxt.setText("相对湿度"+humidity+"%");
//                    windTxt.setText(wind);
//                    for(int i=0;i<weatherinfos.get(0).size();i++)
//                        rightInfoTxt1.setText(weatherinfos.get(i).get(0).getDt()+weatherinfos.get(i).get(0).getInfo());
//                    if(weatherinfos.get(0).size()<3)
//                        for(int i=0;i<(3-weatherinfos.get(0).size());i++)
//                            rightInfoTxt1.setText(weatherinfos.get(i).get(0).getDt()+weatherinfos.get(i).get(0).getInfo());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_weather_info_frg2, container, false);

        areaCode=getArguments().getString("Code");
        topDaysInfoLayout= (LinearLayout) v.findViewById(R.id.topDaysInfoLayout);
        bottomDayInfoLayout= (LinearLayout) v.findViewById(R.id.bottomDayInfoLayout);
        temperatureTxt= (TextView) v.findViewById(R.id.temperatureTxt);
        windTxt= (TextView) v.findViewById(R.id.windTxt);
        publishTime=(TextView) v.findViewById(R.id.publishTime);
        humidityTxt= (TextView) v.findViewById(R.id.humidityTxt);
        weatherIcon= (ImageView) v.findViewById(R.id.weatherIcon);
        imgs.add((ImageView) v.findViewById(R.id.topWeatherTime1));
        imgs.add ((ImageView) v.findViewById(R.id.topWeatherTime2));
        imgs.add((ImageView) v.findViewById(R.id.topWeatherTime3));
        txts.add((TextView) v.findViewById(R.id.rightInfoTxt1));
        txts.add((TextView)v.findViewById(R.id.rightInfoTxt2));
        txts.add((TextView)v.findViewById(R.id.rightInfoTxt3));
//        setTopInfoItem();
        initHandler();
        weatherHandler.getCityPredict(areaCode);
        return v;
    }

    private void setTopInfoItem(){
        int toppadding=ScreenUtil.dip2px(getActivity(),5);
        for (int i=0; i<dayNightInfos.size(); i++){
            DayNightInfo info=dayNightInfos.get(i);
            final int j=i;
            final LinearLayout layout=new LinearLayout(getActivity());
            layout.setPadding(0,toppadding,0,toppadding);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams llp=new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.weight=1;
            layout.setLayoutParams(llp);
            String weekDay= TimeUtil.getWeekDay(i);
            layout.addView(ActUtil.getTextView(getActivity(), weekDay, 15));
            layout.addView(ActUtil.getImageView(getActivity(), ActUtil.getWeatherImg(info.getDayWeatherImg()), 20, 8));
            layout.addView(ActUtil.getImageView(getActivity(), ActUtil.getWeatherImg(info.getNightWeatherImg()), 20,8));
            layout.setBackgroundResource(R.drawable.weather_day_selector);
            topDaysInfoLayout.addView(layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedView.setSelected(false);
                    layout.setSelected(true);
                    selectedView = layout;
                    setBottomInfo(j);
                }
            });
            if(i==0){
                layout.setSelected(true);
                selectedView=layout;
            }
        }

        setBottomInfo(0);
    }

    private void setBottomInfo(int pos){
        int txtSize=13;
        bottomDayInfoLayout.removeAllViews();
        int marginTop= ScreenUtil.dip2px(getActivity(), 8);
        for (int i=0; i<weatherinfos.get(pos).size(); i++){
            WeatherInTIme item=weatherinfos.get(pos).get(i);
            LinearLayout layout=new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams llp=new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.topMargin=marginTop;
            layout.setLayoutParams(llp);
            layout.addView(ActUtil.getTextViewWithWidth(getActivity(), item.getDt(), txtSize, 65));
            layout.addView(ActUtil.getTextViewWithWidth(getActivity(), "|", txtSize, 20));
            layout.addView(ActUtil.getImageView(getActivity(), ActUtil.getWeatherImg(item.getImg()), 20));
            layout.addView(ActUtil.getTextViewWithWidth(getActivity(), item.getInfo(), txtSize, 45));
            layout.addView(ActUtil.getTextViewWithWidth(getActivity(), item.getMintemp() + "°/" + item.getMaxtemp() + "°", 14, 80));
            layout.addView(ActUtil.getTextViewWithWidth(getActivity(), item.getDirect(), txtSize, 45));
            layout.addView(ActUtil.getTextViewWithWidth(getActivity(), item.getPower(), txtSize, 45));
            bottomDayInfoLayout.addView(layout);
        }
    }
}
