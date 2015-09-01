package weather.ppx.com.weatherapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import weather.ppx.com.weatherapp.Fragment.BaseFragment;
import weather.ppx.com.weatherapp.Fragment.WeatherInfoMain;
import weather.ppx.com.weatherapp.Util.LogUtil;
import weather.ppx.com.weatherapp.Util.SharedPreferencesUtil;


public class MainActivity extends BaseActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, BaseFragment.OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private String areaCode="";

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private String mTitle="赣榆作业区";
    public static final String[] areaNames={"作业区", "赣榆", "灌云" , "响水", "海滨", "射阳", "大丰", "东台", "如东", "启东", "地图显示", "设置"};
    private LocationClient mLocationClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _setHeaderTitle(mTitle);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        _setRightHomeListener(R.drawable.icon_app_location, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "开始定位", 500).show();
                if (!mLocationClient.isStarted()) {
//                    mLocationClient.registerLocationListener(locationListener);
                    mLocationClient.start();
                }
                mLocationClient.requestLocation();
            }
        });

        _setRightImgListener(R.drawable.icon_app_refresh, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        startActivity(new Intent(this, FirstPage.class));
        overridePendingTransition(0, 0);
        initLocation();
        mNavigationDrawerFragment.selectItem(1);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
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
            fragmentManager.beginTransaction()
                    .replace(R.id.container, WeatherInfoMain.newInstance(areaNames[position], areaCode)).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        mNavigationDrawerFragment.onPressBack();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void initLocation() {
        mLocationClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(2000);
        option.setAddrType("all");
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(locationListener);
        mLocationClient.start();
    }

    @Override
    protected void onDestroy() {
        if(mLocationClient!=null) {
            mLocationClient.stop();
            mLocationClient=null;
        }
        super.onDestroy();
    }

    BDLocationListener locationListener=new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (location == null)
                return;
            SharedPreferencesUtil.setString(MainActivity.this,
                    "La", "" + location.getLatitude());
            SharedPreferencesUtil.setString(MainActivity.this,
                    "Lo", "" + location.getLongitude());
            if (mLocationClient != null && mLocationClient.isStarted()) {
                mLocationClient.stop();
            }
            LogUtil.d("Location", "La:" + location.getLatitude()+"  Lo:"+location.getLongitude());
            String address = location.getDistrict();
            if(address!=null&&address.length()>0)
                Toast.makeText(getApplicationContext(), "所在位置：" + address, 500).show();
        }

    };
}
