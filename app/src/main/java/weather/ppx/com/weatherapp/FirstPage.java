package weather.ppx.com.weatherapp;

import android.os.Bundle;
import android.os.Handler;

/**
 * Created by 可爱的蘑菇 on 2015/8/30.
 */
public class FirstPage extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage);

        _setHeaderGone();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }
}
