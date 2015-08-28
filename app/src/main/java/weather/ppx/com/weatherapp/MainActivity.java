package weather.ppx.com.weatherapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import weather.ppx.com.weatherapp.Fragment.BaseFragment;
import weather.ppx.com.weatherapp.Fragment.WeatherInfoMain;


public class MainActivity extends BaseActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, BaseFragment.OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private String mTitle="赣榆";
    public static final String[] areaNames={"作业区", "赣榆", "灌云" , "响水", "海滨", "射阳", "大丰", "东台", "如东", "启动", "地图显示", "设置"};

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

            }
        });

        _setRightImgListener(R.drawable.icon_app_refresh, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(position==10){
            Intent intent=new Intent(MainActivity.this, WeatherMap.class);
            startActivity(intent);
        }else if(position==11){
            Intent intent=new Intent(MainActivity.this, WeatherSetting.class);
            startActivity(intent);
        }else {
            _setHeaderTitle(areaNames[position]);
            fragmentManager.beginTransaction().replace(R.id.container, WeatherInfoMain.newInstance()).commit();
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
}
