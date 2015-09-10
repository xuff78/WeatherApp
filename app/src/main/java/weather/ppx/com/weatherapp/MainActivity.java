package weather.ppx.com.weatherapp;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

import weather.ppx.com.weatherapp.Fragment.BaseFragment;
import weather.ppx.com.weatherapp.Fragment.WeatherInfoMain;
import weather.ppx.com.weatherapp.Util.ActUtil;
import weather.ppx.com.weatherapp.Util.ConstantUtil;
import weather.ppx.com.weatherapp.Util.LogUtil;
import weather.ppx.com.weatherapp.Util.SharedPreferencesUtil;
import weather.ppx.com.weatherapp.http.CallBack;
import weather.ppx.com.weatherapp.http.HttpHandler;


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
    private WeatherInfoMain mainFrgment;
    public static final String[] areaNames={"作业区", "赣榆", "灌云" , "响水", "海滨", "射阳", "大丰", "东台", "如东", "启东", "地图显示", "设置"};
    int selectedPos=1;
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
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, WeatherInfoMain.newInstance(areaNames[selectedPos], areaCode)).commit();
            }
        });
        startActivity(new Intent(this, FirstPage.class));
        overridePendingTransition(0, 0);
        if (SharedPreferencesUtil.getValue(this, ConstantUtil.AutoLocation, true))
            initLocation();
        mNavigationDrawerFragment.selectItem(SharedPreferencesUtil.getInt(this, ConstantUtil.SelectArea, 1));
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
        // update the main content by replacing fragments
        selectedPos=position;
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
            SharedPreferencesUtil.setInt(this, ConstantUtil.SelectArea, position);
            areaCode=ActUtil.getAreaCode(areaNames[position]);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, WeatherInfoMain.newInstance(areaNames[position], areaCode)).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
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

    LocationManagerProxy mLocationManagerProxy;
    private void initLocation() {
        mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, 10*1000, 15, this);
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
    public void onLocationChanged(AMapLocation amapLocation) {
        if(amapLocation != null && amapLocation.getAMapException().getErrorCode() == 0){
            //获取位置信息
//            BigDecimal dbLa = new BigDecimal(amapLocation.getLatitude());
//            BigDecimal dbLo = new BigDecimal(amapLocation.getLongitude());
//            String latitude = dbLa.toString();
//            String longitude = dbLo.toString();
//            handler.getLocation(latitude, longitude);
            if(amapLocation.getCity()!=null) {
                Toast.makeText(this, "城市：" + amapLocation.getCity() + "\n" +
                        "位置：" + amapLocation.getAddress(), 500).show();
                String getAreaCode = ActUtil.getAreaCode(amapLocation.getCity());
                if(getAreaCode.length()>0) {
                    areaCode=getAreaCode;
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, WeatherInfoMain.newInstance(areaNames[selectedPos], areaCode)).commit();
                    SharedPreferencesUtil.setString(this, ConstantUtil.AreCode, areaCode);
                }
                mLocationManagerProxy.removeUpdates(MainActivity.this);

            }
            LogUtil.i("Location", "La:" + amapLocation.getLatitude() + "  Lo:" + amapLocation.getLongitude());
        }
    }
}
