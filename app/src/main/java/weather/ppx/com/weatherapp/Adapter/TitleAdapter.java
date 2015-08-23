package weather.ppx.com.weatherapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.net.URLEncoder;

import weather.ppx.com.weatherapp.MainActivity;
import weather.ppx.com.weatherapp.R;

public class TitleAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context con;
    private String[] areaNames= MainActivity.areaNames;

    public TitleAdapter(Context context){
        this.mInflater = LayoutInflater.from(context);
        con=context;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return areaNames.length;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AbsListView.LayoutParams alp=new  AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView title = new TextView(con);
        title.setPadding(0,15,0,15);
        title.setGravity(Gravity.CENTER);
        title.setText(areaNames[position]);
        title.setLayoutParams(alp);
        if(position==0||position==10||position==11){
            title.setTextSize(18);
            title.setTextColor(con.getResources().getColor(R.color.normal_blue));
        }else{
            title.setTextSize(16);
            title.setTextColor(Color.BLACK);
        }
        return title;
    }

}
