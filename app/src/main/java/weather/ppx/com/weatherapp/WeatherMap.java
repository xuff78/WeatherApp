package weather.ppx.com.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import weather.ppx.com.weatherapp.Fragment.BaseFragment;

/**
 * Created by Administrator on 2015/8/25.
 */
public class WeatherMap extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);

        _setHeaderTitle("地图");
        initView();
    }

    private void initView() {
    }

    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_window, null);
        // 设置按钮的点击事件
        TextView txt1 = (Button) contentView.findViewById(R.id.txt1);
        TextView txt2 = (Button) contentView.findViewById(R.id.txt2);


        final PopupWindow popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);


        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.block_white));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }

}

