package weather.ppx.com.weatherapp.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
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

    public static void showSinglseDialog(final Activity con, String content) {
        //dialog参数设置
        AlertDialog.Builder builder=new AlertDialog.Builder(con);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage(content); //设置内容
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public static void showSinglseDialog(final Activity con, String content, DialogInterface.OnClickListener listener) {
        //dialog参数设置
        AlertDialog.Builder builder=new AlertDialog.Builder(con);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage(content); //设置内容
        builder.setPositiveButton("确定", listener);
        builder.create().show();
    }

    public static void showTwoOptionsDialog(final Activity con, String content, final View.OnClickListener listener) {
        int padding=ScreenUtil.dip2px(con, 25);
        //dialog参数设置
        AlertDialog.Builder builder=new AlertDialog.Builder(con);  //先得到构造器
        builder.setTitle("提示"); //设置标题
//        builder.setMessage(content); //设置内容
        TextView txt=new TextView(con);
        txt.setPadding(padding, padding/2, padding, padding/2);
        txt.setTextSize(16);
        txt.setTextColor(Color.BLACK);
        txt.setText(content);
        builder.setView(txt);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                listener.onClick(new View(con));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    public static int getWeatherImg(String weatherInt) {
        int imgRes=0;
        int dataInt=Integer.valueOf(weatherInt);
        if((dataInt<34&&dataInt>-1)||dataInt==36||dataInt==53){
            imgRes=ConstantUtil.weatherRes[dataInt];
        }
        return imgRes;
    }
}
