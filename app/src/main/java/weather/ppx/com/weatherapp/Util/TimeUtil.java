package weather.ppx.com.weatherapp.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 可爱的蘑菇 on 2015/9/8.
 */
public class TimeUtil {

    public static Date getDate(String strDate){
        if(strDate!=null&&strDate.length()>11) {
            String data=strDate.substring(11);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
            try {
                Date date = simpleDateFormat.parse(data);
                System.out.println(date);
                return date;
            } catch (ParseException px) {
                px.printStackTrace();
            }
        }
        return null;
    }
}
