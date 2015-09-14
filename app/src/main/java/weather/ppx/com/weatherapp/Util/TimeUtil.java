package weather.ppx.com.weatherapp.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by 可爱的蘑菇 on 2015/9/8.
 */
public class TimeUtil {

    public static String getDate(String strDate){
        if(strDate!=null&&strDate.length()>11) {
//            String data=strDate.substring(11);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm");
            try {
                Date date = simpleDateFormat.parse(strDate);
                SimpleDateFormat showFormat = new SimpleDateFormat("MM月dd日 HH时");
                System.out.println(date);
                return showFormat.format(date);
            } catch (ParseException px) {
                px.printStackTrace();
            }
        }
        return "";
    }

    public static String getWeekDay(int after){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        if(after!=0)
            c.add(Calendar.DAY_OF_MONTH, after);
//        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
//        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
//        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="日";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
        return"周"+mWay;
    }
}
