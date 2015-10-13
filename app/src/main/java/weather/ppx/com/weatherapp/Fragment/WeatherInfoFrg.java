package weather.ppx.com.weatherapp.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import common.eric.com.ebaselibrary.util.ToastUtils;
import weather.ppx.com.weatherapp.Adapter.MainInfoAdapter;
import weather.ppx.com.weatherapp.Bean.AreaWorkingInfo;
import weather.ppx.com.weatherapp.MainActivity;
import weather.ppx.com.weatherapp.R;
import weather.ppx.com.weatherapp.Util.JsonUtil;
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
    private RecyclerView mRecyclerView;
    private MainInfoAdapter myAdapter;
    private HttpHandler weatherHandler;
    private AreaWorkingInfo workingInfo;
    private View hintLayout;
    private SwipeRefreshLayout mSwipeLayout;
    private boolean refresh=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_weather_info_frg, container, false);

        areaCode=getArguments().getString("Code");
        mRecyclerView = (RecyclerView)v.findViewById(R.id.mRecyclerView);
        hintLayout=v.findViewById(R.id.hintLayout);
        // 设置LinearLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 设置ItemAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
        mRecyclerView.setHasFixedSize(true);
        initHandler();
        weatherHandler.getSeaInfo(areaCode);
        mSwipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefresh);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh=true;
                weatherHandler.getSeaInfo(areaCode);
            }
        });
        return v;
    }

    private void initHandler() {
        weatherHandler=new HttpHandler(getActivity(), new CallBack(getActivity()){
            @Override
            public void onSuccess(String method, String jsonMessage) {
                super.onSuccess(method, jsonMessage);
                if(getActivity()==null)
                    return;
//                LogUtil.i("Location", jsonMessage);
                mSwipeLayout.setRefreshing(false);
                if(refresh)
                    ToastUtils.show(getActivity(),"数据已更新");
                refresh=false;
                hintLayout.setVisibility(View.GONE);
                workingInfo= JsonUtil.getWorkingInfo(jsonMessage);
                myAdapter = new MainInfoAdapter(getActivity(), workingInfo);
                mRecyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onHTTPException(String method, String jsonMessage) {
                super.onHTTPException(method, jsonMessage);
                refresh=false;
                mSwipeLayout.setRefreshing(false);
            }
        });
    }



    @Override
    public void onDestroyView() {
//        mWebView.destroy();
        super.onDestroyView();
    }



}
