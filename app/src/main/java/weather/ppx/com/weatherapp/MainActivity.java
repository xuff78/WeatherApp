package weather.ppx.com.weatherapp;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UpdateConfig;

import weather.ppx.com.weatherapp.Fragment.BaseFragment;
import weather.ppx.com.weatherapp.Fragment.WeatherInfoMain;
import weather.ppx.com.weatherapp.Util.ActUtil;
import weather.ppx.com.weatherapp.Util.ConstantUtil;
import weather.ppx.com.weatherapp.Util.LogUtil;
import weather.ppx.com.weatherapp.Util.SharedPreferencesUtil;


public class MainActivity extends BaseActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, BaseFragment.OnFragmentInteractionListener, AMapLocationListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private String areaCode="";
    private ImageView mainBgImg;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private String mTitle="赣榆作业区";
    public static final String[] areaNames={"作业区", "赣榆", "灌云" , "响水", "滨海", "射阳", "大丰", "东台", "如东", "启东", "地图显示", "设置"};
    int selectedPos=1;
//    private String areaName="";
    private int bgType=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _setHeaderTitle(mTitle);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mainBgImg=(ImageView)findViewById(R.id.mainBgImg);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        _setRightHomeListener(R.drawable.icon_app_location, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "开始定位", 500).show();
                initLocation();
            }
        });

        _setRightImgListener(R.drawable.icon_app_refresh, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });
        startActivity(new Intent(this, FirstPage.class));
        overridePendingTransition(0, 0);
        mNavigationDrawerFragment.selectItem(SharedPreferencesUtil.getInt(this, ConstantUtil.SelectArea, 8));
        if (SharedPreferencesUtil.getValue(this, ConstantUtil.AutoLocation, true))
            initLocation();

        UpdateConfig.setDebug(true);
        UmengUpdateAgent.update(this);
    }

    public void refreshData(){
        mNavigationDrawerFragment.selectItem(selectedPos);
    }


    @Override
    protected void onResume() {
        super.onResume();
        int type=SharedPreferencesUtil.getInt(this, "BgType", 0);
        setBg(type);
    }

    public void setBg(int bgType){
        if(this.bgType!=bgType) {
            this.bgType = bgType;
            if (bgType == 0) {
                mainBgImg.setBackgroundResource(R.drawable.mianbg);
            } else if (bgType == 1) {
                mainBgImg.setBackgroundResource(R.drawable.main_bg2);
            }
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(position==0){

        }else if(position==10){
            Intent intent=new Intent(MainActivity.this, WeatherMap.class);
            startActivity(intent);
        }else if(position==11){
            Intent intent=new Intent(MainActivity.this, WeatherSetting.class);
            startActivity(intent);
        }else {
            _setHeaderTitle(areaNames[position]+"作业区");
            selectedPos=position;
            SharedPreferencesUtil.setInt(this, ConstantUtil.SelectArea, position);
            SharedPreferencesUtil.setString(this, ConstantUtil.AreName, areaNames[position]);
            areaCode=ActUtil.getAreaCode(areaNames[position]);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, WeatherInfoMain.newInstance(areaNames[position], areaCode)).commit();
        }
    }

    @Override
    public void onBackPressed() {
        mNavigationDrawerFragment.onPressBack();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    LocationManagerProxy mLocationManagerProxy;
    private void initLocation() {
        mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, -1, 15, this);
        mLocationManagerProxy.setGpsEnable(true);
    }

    @Override
    protected void onDestroy() {
        if (mLocationManagerProxy != null) {
            mLocationManagerProxy.removeUpdates(this);
            mLocationManagerProxy.destroy();
        }
        mLocationManagerProxy = null;
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onLocationChanged(final AMapLocation amapLocation) {
        if(amapLocation != null && amapLocation.getAMapException().getErrorCode() == 0){
            //获取位置信息
//            BigDecimal dbLa = new BigDecimal(amapLocation.getLatitude());
//            BigDecimal dbLo = new BigDecimal(amapLocation.getLongitude());
//            String latitude = dbLa.toString();
//            String longitude = dbLo.toString();
//            handler.getLocation(latitude, longitude);
            if(amapLocation.getCity()!=null) {
//                Toast.makeText(this, "城市：" + amapLocation.getCity() + "\n" +
//                        "位置：" + amapLocation.getAddress(), 500).show();
                String currentCity=SharedPreferencesUtil.getString(this, ConstantUtil.AreName);
                if(!amapLocation.getCity().contains(currentCity)) {
                    final String getAreaCode = ActUtil.getAreaCode(amapLocation.getCity());
                    if(getAreaCode.length()>0) {
                        ActUtil.showTwoOptionsDialog(MainActivity.this, "您所在的城市为" + amapLocation.getCity() + "，与当前城市不一致，是否切换成"
                                + amapLocation.getCity(), new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                    areaCode = getAreaCode;
                                    int areaCodePos = ActUtil.getPosByAreaCode(getAreaCode);
                                    String cityName = ConstantUtil.areaNames[areaCodePos];
                                    SharedPreferencesUtil.setString(MainActivity.this, ConstantUtil.AreName, cityName);
                                    selectedPos = areaCodePos + 1;//实际上的菜单位置要比纯地区名加1，因为第一个是【作业区】标题
                                    SharedPreferencesUtil.setInt(MainActivity.this, ConstantUtil.SelectArea, selectedPos);
                                    mNavigationDrawerFragment.selectItem(selectedPos);

                            }
                        });
                    } else {
                        ActUtil.showSinglseDialog(MainActivity.this, "您所在的城市为" + amapLocation.getCity() + "，切换至如东");
                        mNavigationDrawerFragment.selectItem(8);
                    }
                }
                mLocationManagerProxy.removeUpdates(MainActivity.this);
                LogUtil.i("Location", "La:" + amapLocation.getLatitude() + "  Lo:" + amapLocation.getLongitude());
            }
        }
    }
}
