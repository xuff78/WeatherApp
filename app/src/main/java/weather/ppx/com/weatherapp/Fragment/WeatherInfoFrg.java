package weather.ppx.com.weatherapp.Fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import weather.ppx.com.weatherapp.Adapter.MainInfoAdapter;
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

    RecyclerView mRecyclerView;
    MainInfoAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_weather_info_frg, container, false);

        mRecyclerView = (RecyclerView)v.findViewById(R.id.mRecyclerView);
        // 设置LinearLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 设置ItemAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
        mRecyclerView.setHasFixedSize(true);
        // 初始化自定义的适配器
        myAdapter = new MainInfoAdapter(getActivity());
        // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(myAdapter);
        return v;
    }



    @Override
    public void onDestroyView() {
//        mWebView.destroy();
        super.onDestroyView();
    }



}
