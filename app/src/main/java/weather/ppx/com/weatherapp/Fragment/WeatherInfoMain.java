package weather.ppx.com.weatherapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import weather.ppx.com.weatherapp.BaseActivity;
import weather.ppx.com.weatherapp.R;
import weather.ppx.com.weatherapp.Util.ActUtil;

/**
 * Created by �ɰ���Ģ�� on 2015/8/23.
 */
public class WeatherInfoMain extends BaseFragment {


    // TODO: Rename and change types and number of parameters
    public static WeatherInfoMain newInstance(String area, String code) {
        WeatherInfoMain fragment = new WeatherInfoMain();
        Bundle b=new Bundle();
        b.putString("Area", area);
        b.putString("Code", code);
        fragment.setArguments(b);
        return fragment;
    }

    ViewPager mViewPager;
    private String areaName, areaCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        areaName=getArguments().getString("Area");
        areaCode=getArguments().getString("Code");
        View v= inflater.inflate(R.layout.fragment_info_main, container, false);
        mViewPager=(ViewPager)v.findViewById(R.id.mViewPager);
        mViewPager.setAdapter(new WeatherInfoAdapter(getActivity().getSupportFragmentManager()));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    ((BaseActivity)getActivity())._setHeaderTitle(areaName+"作业区");

                }else
                    ((BaseActivity)getActivity())._setHeaderTitle(ActUtil.getCityName(areaName));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return v;
    }

    public class WeatherInfoAdapter extends FragmentStatePagerAdapter {
        public WeatherInfoAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        //�õ�ÿ��item
        @Override
        public Fragment getItem(int position) {
            Fragment frg=null;
            switch (position){
                case 0:
                    frg=WeatherInfoFrg.newInstance(areaCode);
                    break;
                case 1:
                    frg=WeatherInfoFrg2.newInstance(areaCode);
                    break;
            }
            return frg;
        }


        // ��ʼ��ÿ��ҳ��ѡ��
        @Override
        public Object instantiateItem(ViewGroup arg0, int arg1) {
            // TODO Auto-generated method stub
            return super.instantiateItem(arg0, arg1);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

    }
}
