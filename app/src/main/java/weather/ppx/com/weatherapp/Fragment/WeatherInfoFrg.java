package weather.ppx.com.weatherapp.Fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weather.ppx.com.weatherapp.R;
import weather.ppx.com.weatherapp.Util.LogUtil;

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
                mWebView.loadUrl("javascript:setData('" + getJsonData() +"')");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        return v;
    }

    String[] formData1={"1", "2.8", "3.5", "3", "3", "3.4", "3.2", "2.9", "3.5", "3.5", "2.1", "1.3"};
    String[] formData2={"40", "20", "40", "45", "40", "38", "36", "29", "35", "40", "21", "13"};
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
