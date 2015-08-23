package weather.ppx.com.weatherapp.Fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import weather.ppx.com.weatherapp.R;

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
                mWebView.loadUrl("javascript:setData('" + "let me test" + "'," + 99 +")");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        mWebView.destroy();
        super.onDestroyView();
    }



}
