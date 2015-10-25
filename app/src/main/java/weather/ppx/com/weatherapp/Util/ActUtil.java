package weather.ppx.com.weatherapp.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

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

    public static ImageView getImageViewS(Context con, int res, int w, int h){
        int width=ScreenUtil.dip2px(con, w);
        int height=ScreenUtil.dip2px(con, h);
        ImageView iv=new ImageView(con);
        iv.setBackgroundResource(res);
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(width,height);
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
            if (city.contains(ConstantUtil.areaNames[i])) {
                return ConstantUtil.areaCodes[i];
            }
        }
        return "";
    }

    public static int getPosByAreaCode(String code){
        for(int i=0;i<9;i++) {
            if (code.contains(ConstantUtil.areaCodes[i])) {
                return i;
            }
        }
        return -1;
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
        AlertDialog.Builder builder=new AlertDialog.Builder(con, 32);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage(content); //设置内容
        builder.setPositiveButton("确定", listener);
        builder.setCancelable(false);
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

    public static String getCityName(String areaName) {
        for (int i=0;i<ConstantUtil.cityName.length;i++){
            if(ConstantUtil.cityName[i].contains(areaName))
                return ConstantUtil.cityName[i];
        }
        return "";
    }

    public static int getDegree(String direct) {
        int degree=-45;
        if(direct.startsWith("东北")){
            degree*=5;
        }else if(direct.startsWith("东南")){
            degree*=7;
        }else if(direct.startsWith("西南")){
            degree*=1;
        }else if(direct.startsWith("西北")){
            degree*=3;
        }else if(direct.startsWith("北")){
            degree*=4;
        }else if(direct.startsWith("东")){
            degree*=6;
        }else if(direct.startsWith("南")){
            degree*=0;
        }else if(direct.startsWith("西")){
            degree*=2;
        }

        return degree;
    }

    public static void setDirectImg(String direct, ImageView img) {
        int degree=-45;
        if(direct.startsWith("东北")){
            degree*=5;
        }else if(direct.startsWith("东南")){
            degree*=7;
        }else if(direct.startsWith("西南")){
            degree*=1;
        }else if(direct.startsWith("西北")){
            degree*=3;
        }else if(direct.startsWith("北")){
            degree*=4;
        }else if(direct.startsWith("东")){
            degree*=6;
        }else if(direct.startsWith("南")){
            degree*=0;
        }else if(direct.startsWith("西")){
            degree*=2;
        }
//        ViewHelper.setPivotX(img, 0.5f);
//        ViewHelper.setPivotY(img, 0.5f);
        ViewHelper.setRotation(img, -degree);
    }
}
