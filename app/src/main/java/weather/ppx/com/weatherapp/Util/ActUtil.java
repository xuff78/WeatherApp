package weather.ppx.com.weatherapp.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import weather.ppx.com.weatherapp.R;

import static weather.ppx.com.weatherapp.Util.ConstantUtil.areaNames;

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

    public static TextView getTextView(Context con, String txt, int size, int weight){
        TextView tv=new TextView(con);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(size);
        tv.setText(txt);
        LinearLayout.LayoutParams llpTxt=new LinearLayout.LayoutParams(-1,-2);
        llpTxt.weight=weight;
        tv.setLayoutParams(llpTxt);
        return  tv;
    }

    public static TextView getTextViewWithWidth(Context con, String txt, int size, int w){
        int width=ScreenUtil.dip2px(con, w);
        TextView tv=new TextView(con);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(size);
        tv.setText(txt);
        LinearLayout.LayoutParams llpTxt=new LinearLayout.LayoutParams(width,-2);
        tv.setLayoutParams(llpTxt);
        return  tv;
    }

    public static ImageView getImageView(Context con, int res, int w){
        int width=ScreenUtil.dip2px(con, w);
        ImageView iv=new ImageView(con);
        iv.setImageResource(res);
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(width,width);
        iv.setLayoutParams(llp);
        return iv;
    }

    public static ImageView getImageView(Context con, int res, int w, int topM){
        int width=ScreenUtil.dip2px(con, w);
        ImageView iv=new ImageView(con);
        iv.setImageResource(res);
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(width,width);
        llp.topMargin=topM;
        iv.setLayoutParams(llp);
        return iv;
    }

    public static String getAreaCode(String city){
        for(int i=0;i<9;i++) {
            if (city.contains(areaNames[i])) {
                return ConstantUtil.areaCodes[i];
            }
        }
        return "";
    }

    public static String getWeekDay(int day){
        String weekday="";
        switch (day){
            case 0:
        }
        return weekday;
    }

    public static void showSinglseDialog(final Activity con) {
        final String items[]={"张三","李四","王五"};
        //dialog参数设置
        AlertDialog.Builder builder=new AlertDialog.Builder(con);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("暂未获取数据，请稍后再试"); //设置内容
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                con.finish();
            }
        });
        builder.create().show();
    }
}
