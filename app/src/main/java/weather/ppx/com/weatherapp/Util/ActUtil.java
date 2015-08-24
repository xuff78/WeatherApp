package weather.ppx.com.weatherapp.Util;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 可爱的蘑菇 on 2015/8/25.
 */
public class ActUtil {

    public static TextView getTextView(Context con, String txt, int size){
        TextView tv=new TextView(con);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(size);
        tv.setText(txt);
        return  tv;
    }

    public static ImageView getImageView(Context con, int res, int w){
        int width=ScreenUtil.dip2px(con, w);
        ImageView iv=new ImageView(con);
        iv.setImageResource(res);
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(width,width);
        llp.topMargin=5;
        iv.setLayoutParams(llp);
        return iv;
    }
}
