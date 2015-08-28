package weather.ppx.com.weatherapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 可爱的蘑菇 on 2015/8/23.
 */
public class BaseActivity extends ActionBarActivity {


    private ActionBar actionBar;
    private TextView mTitle;
    public ImageButton btnHome, btnBack, rightImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_activity);

        mTitle=(TextView)findViewById(R.id.app_header_title_tv);
        ClickListener listener = new ClickListener();
        btnHome = (ImageButton) findViewById(R.id.app_home_imagebtn);
        btnHome.setOnClickListener(listener);
        btnBack = (ImageButton) findViewById(R.id.app_back_imagebtn);
        btnBack.setOnClickListener(listener);
        rightImg = (ImageButton) findViewById(R.id.app_right_imagebtn);
        rightImg.setOnClickListener(listener);
    }

    @Override
    public void setContentView(int layoutResID) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(layoutResID, null);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.app_frame_content);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.FILL_PARENT);

        frameLayout.addView(content, -1, layoutParams);
    }

    protected void _setHeaderTitle(String title) {
        mTitle.setText(title);
    }

    protected View _setHeaderGone() {
        LinearLayout rl = (LinearLayout) findViewById(R.id.app_heder_layout);
        rl.setVisibility(View.GONE);
        return rl;
    }

    protected void _setLeftBackListener(int backImage, View.OnClickListener listener) {
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setImageResource(backImage);
        btnBack.setOnClickListener(listener);
    }

    protected void _setRightHomeListener(int backImage, View.OnClickListener listener) {
        btnHome.setVisibility(View.VISIBLE);
        btnHome.setImageResource(backImage);
        btnHome.setOnClickListener(listener);
    }

    protected void _setRightImgListener(int backImage, View.OnClickListener listener) {
        rightImg.setVisibility(View.VISIBLE);
        rightImg.setImageResource(backImage);
        rightImg.setOnClickListener(listener);
    }

    private final class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.app_back_imagebtn) {
                //ActUtil.showHome(EBaseActivity.this);
            } else if (id == R.id.app_home_imagebtn) {
                KeyBoardCancle();
                finish();
            }
        }
    }

    public void KeyBoardCancle() {

        View view = getWindow().peekDecorView();
        if (view != null) {

            InputMethodManager inputmanger = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
