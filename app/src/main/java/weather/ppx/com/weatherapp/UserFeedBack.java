package weather.ppx.com.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import weather.ppx.com.weatherapp.Util.ConstantUtil;
import weather.ppx.com.weatherapp.Util.SharedPreferencesUtil;
import weather.ppx.com.weatherapp.View.SlipButton;

/**
 * Created by Administrator on 2015/10/19.
 */
public class UserFeedBack extends BaseActivity {

    SlipButton btnLocation, btnPush;
    private ImageView themeChecked1, themeChecked2, bgImg1, bgImg2;
    int bgType=0;
    private ArrayList<RelativeLayout> layouts=new ArrayList<RelativeLayout>();
    private int[] resLayout={R.id.feedbackLayout1, R.id.feedbackLayout2, R.id.feedbackLayout3, R.id.feedbackLayout4
            , R.id.feedbackLayout5, R.id.feedbackLayout6, R.id.feedbackLayout7};

    private boolean[]checks=new boolean[resLayout.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);


        _setHeaderTitle("用户反馈");
        initView();
    }

    private void initView() {
        for (int i=0;i<resLayout.length;i++) {
            RelativeLayout rl = (RelativeLayout) findViewById(resLayout[i]);
            rl.setTag(i);
            rl.setOnClickListener(listener);
            layouts.add(rl);
            checks[i]=false;
        }
    }

    View.OnClickListener listener=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            int pos=(Integer) v.getTag();
            RelativeLayout layout = layouts.get(pos);
            ImageView checkbox = (ImageView) layout.getChildAt(0);
            if(checks[pos]) {
                checkbox.setBackgroundResource(R.drawable.app_login_remember_unsel);
            }else{
                checkbox.setBackgroundResource(R.drawable.app_login_remember_sel);
            }
            checks[pos]=!checks[pos];
        }
    };
}
