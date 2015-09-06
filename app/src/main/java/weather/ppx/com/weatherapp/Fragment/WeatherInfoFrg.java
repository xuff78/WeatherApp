package weather.ppx.com.weatherapp.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

import common.eric.com.ebaselibrary.util.ToastUtils;
import weather.ppx.com.weatherapp.Adapter.MainInfoAdapter;
import weather.ppx.com.weatherapp.Bean.WorkingInfo;
import weather.ppx.com.weatherapp.R;
import weather.ppx.com.weatherapp.Util.LogUtil;
import weather.ppx.com.weatherapp.http.CallBack;
import weather.ppx.com.weatherapp.http.HttpHandler;

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
    public static WeatherInfoFrg newInstance(String areaCode) {
        WeatherInfoFrg fragment = new WeatherInfoFrg();
        Bundle b=new Bundle();
        b.putString("Code", areaCode);
        fragment.setArguments(b);
        return fragment;
    }

    private String areaCode="";
    RecyclerView mRecyclerView;
    MainInfoAdapter myAdapter;
    private HttpHandler weatherHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_weather_info_frg, container, false);

        areaCode=getArguments().getString("Code");
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
        initHandler();
        weatherHandler.getSeaInfo(areaCode);
        return v;
    }

    private void initHandler() {
        weatherHandler=new HttpHandler(getActivity(), new CallBack(getActivity()){
            @Override
            public void onSuccess(String method, String jsonMessage) {
                super.onSuccess(method, jsonMessage);
                LogUtil.i("Location", jsonMessage);
//                JSONObject result= JSONObject.parseObject(jsonMessage);
//                List<WorkingInfo> details= JSONArray.parseArray(result.getJSONArray("detail").toJSONString(), WorkingInfo.class);
//                ToastUtils.show(getActivity(), "size: " + details.size());
            }
        });
    }



    @Override
    public void onDestroyView() {
//        mWebView.destroy();
        super.onDestroyView();
    }



}
