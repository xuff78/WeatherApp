package weather.ppx.com.weatherapp.Fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weather.ppx.com.weatherapp.R;
import weather.ppx.com.weatherapp.Util.ActUtil;
import weather.ppx.com.weatherapp.Util.LogUtil;
import weather.ppx.com.weatherapp.Util.ScreenUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeatherInfoFrg.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeatherInfoFrg#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherInfoFrg extends BaseFragment {


    // TODO: Rename and change types and number of parameters
    public static WeatherInfoFrg newInstance() {
        WeatherInfoFrg fragment = new WeatherInfoFrg();
        return fragment;
    }

    WebView mWebView;
    TextView moreHint;
    LinearLayout topDaysInfoLayout, bottomDayInfoLayout1, bottomDayInfoLayout2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_weather_info_frg, container, false);
        mWebView=(WebView)v.findViewById(R.id.mWebView);
        mWebView.setBackgroundColor(1);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/test.html");
        mWebView.setWebChromeClient(new WebChromeClient());

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                mWebView.loadUrl("javascript:setData('" + getJsonData() + "')");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        moreHint=(TextView)v.findViewById(R.id.moreHint);
        topDaysInfoLayout= (LinearLayout) v.findViewById(R.id.topDaysInfoLayout);
        bottomDayInfoLayout2 = (LinearLayout) v.findViewById(R.id.bottomDayInfoLayout2);
        bottomDayInfoLayout1 = (LinearLayout) v.findViewById(R.id.bottomDayInfoLayout1);
        setTopInfoItem();
        v.findViewById(R.id.showMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomDayInfoLayout2.isShown()) {
                    moreHint.setText("显示");
                    bottomDayInfoLayout2.setVisibility(View.GONE);
                }else {
                    bottomDayInfoLayout2.setVisibility(View.VISIBLE);
                    moreHint.setText("隐藏");
                }
            }
        });
        return v;
    }

    private void setTopInfoItem(){

        int Swidth=40;
        int Lwidth=60;
        topDaysInfoLayout.addView(ActUtil.getTextViewWithWidth(getActivity(), "时间", 15, Lwidth));
        topDaysInfoLayout.addView(ActUtil.getTextViewWithWidth(getActivity(), "风", 15, Lwidth));
        topDaysInfoLayout.addView(ActUtil.getTextViewWithWidth(getActivity(), "潮位", 15, Swidth));
        topDaysInfoLayout.addView(ActUtil.getTextViewWithWidth(getActivity(), "浪高", 15, Swidth));
        topDaysInfoLayout.addView(ActUtil.getTextViewWithWidth(getActivity(), "风向", 15, Lwidth));
        topDaysInfoLayout.addView(ActUtil.getTextViewWithWidth(getActivity(), "安全\n等级", 15, Lwidth));

        int marginTop= ScreenUtil.dip2px(getActivity(), 8);
        for (int i=0; i<9; i++){
            LinearLayout layout=new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams llp=new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.topMargin=marginTop;
            layout.setLayoutParams(llp);
            layout.addView(ActUtil.getTextViewWithWidth(getActivity(), "06-12时", 14, Lwidth));
            layout.addView(ActUtil.getTextViewWithWidth(getActivity(), "3-4级", 14, Lwidth));
            layout.addView(ActUtil.getTextViewWithWidth(getActivity(), "1.4", 14, Swidth));
            layout.addView(ActUtil.getTextViewWithWidth(getActivity(), "50", 14, Swidth));
            layout.addView(ActUtil.getTextViewWithWidth(getActivity(), "东南波", 14, Lwidth));
            layout.addView(ActUtil.getTextViewWithWidth(getActivity(), "极不安全", 14, Lwidth));
            if(i>3){
                bottomDayInfoLayout2.addView(layout);
            }else
                bottomDayInfoLayout1.addView(layout);
        }
    }

    String[] formData1={"0.8", "2.2", "1.3", "1.5", "1.2", "0.7", "0.6", "0.7", "1", "1.2", "1.1", "0.7"};
    String[] formData2={"40", "40", "40", "45", "46", "48", "38", "35", "35", "40", "45", "30"};
    private String getJsonData() {
        String jsonData="";
        try {
            JSONObject jsonObj=new JSONObject();
            JSONArray array=new JSONArray();
            for(int i=0;i<formData1.length;i++){
                JSONObject objsub=new JSONObject();
                objsub.put("data", formData1[i]);
                array.put(i, objsub);
            }
            jsonObj.put("FristFormInfo1", array);

            array=new JSONArray();
            for(int i=0;i<formData2.length;i++){
                JSONObject objsub=new JSONObject();
                objsub.put("data", formData2[i]);
                array.put(i, objsub);
            }
            jsonObj.put("FristFormInfo2", array);

            jsonData=jsonObj.toString();
            LogUtil.d("Weather", jsonObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData.toString();
    }

    @Override
    public void onDestroyView() {
        mWebView.destroy();
        super.onDestroyView();
    }



}
