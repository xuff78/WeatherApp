package weather.ppx.com.weatherapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weather.ppx.com.weatherapp.Bean.AreaWorkingInfo;
import weather.ppx.com.weatherapp.Bean.WeatherInfo;
import weather.ppx.com.weatherapp.R;
import weather.ppx.com.weatherapp.Util.ActUtil;
import weather.ppx.com.weatherapp.Util.ConstantUtil;
import weather.ppx.com.weatherapp.Util.LogUtil;
import weather.ppx.com.weatherapp.Util.ScreenUtil;
import weather.ppx.com.weatherapp.Util.TimeUtil;

/**
 * Created by 可爱的蘑菇 on 2015/8/27.
 */
public class MainInfoAdapter extends RecyclerView.Adapter{


    private Context mContext;
    private AreaWorkingInfo workingInfo;
    private int formWidth, formedge, timeCenterPos=-1, timeCenterPos2=-1, textWidth=0;
    private String publishDate="";

    public MainInfoAdapter(Activity context, AreaWorkingInfo workingInfo)
    {
        this.mContext = context;
        this.workingInfo=workingInfo;
        formedge=ScreenUtil.dip2px(context,46);
        formWidth= ScreenUtil.getScreenWidth(context)-formedge*2;
        textWidth=ScreenUtil.dip2px(context,80);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i )
    {
        // 给ViewHolder设置布局文件
        RecyclerView.ViewHolder holder=null;
        if(i==0) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_maininfo_top, viewGroup, false);
            holder=new TopViewHolder(v);
        }else if(i==1) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_maininfo_mid, viewGroup, false);
            holder=new MidViewHolder(v);
        }else if(i==2) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_maininfo_bottom, viewGroup, false);
            holder=new BottomViewHolder(v, "file:///android_asset/form1.html");
        }else if(i==3) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_maininfo_bottom, viewGroup, false);
            holder=new BottomViewHolder(v, "file:///android_asset/form2.html");
        }
        return holder;
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder viewHolder, int i )
    {
        // 给ViewHolder设置元素
        if(i==0) {
            TopViewHolder th=(TopViewHolder)viewHolder;
            if(workingInfo.getDetail().size()>0){
                Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
                t.setToNow(); // 取得系统时间。
                int hour = t.hour; // 0-23
                for(int j=0;j<workingInfo.getDetail().size();j++) {
                    WeatherInfo info = null;
                    WeatherInfo tempInfo = workingInfo.getDetail().get(j);
                    String[] date=tempInfo.getDt().split("日");
                    int day=Integer.valueOf(date[0]);
                    if(day==t.monthDay)
                    if(Integer.valueOf(tempInfo.getTime())>hour){
                        info=tempInfo;
                    }else if(workingInfo.getDetail().size()>(j+1)&&Integer.valueOf(tempInfo.getTime())>Integer.valueOf(workingInfo.getDetail().get(j+1).getTime()))
                        info=workingInfo.getDetail().get(j+1);
                    if(info!=null) {
                        th.dateTxt.setText(info.getDt());
                        if (info.getSafe().equals("不安全")) {
                            th.safeTxt.setTextColor(mContext.getResources().getColor(R.color.normal_orange));
                            th.safeTxt.setText("不安全");
                        }else if (info.getSafe().equals("极不安全")) {
                            th.safeTxt.setTextColor(mContext.getResources().getColor(R.color.norma_red));
                            th.safeTxt.setText("极不安全");
                        }
                        th.fengxiangTxt.setText("风速：" + info.getWiSV() + "m/s");
                        ActUtil.setDirectImg(info.getWiDT(), th.windDirect);
                        th.langgaoTxt.setText("浪高：" + info.getWaH() + "m");
                        th.chaoweiTxt.setText("潮位：" + info.gettL() + "cm");
                        th.boxiangTxt.setText("波向：" + info.getWaDT());
                        String pt="";
                        if(workingInfo.getDate_time().contains(" ")) {
                            String[] timeInfo=workingInfo.getDate_time().split(" ");
                            publishDate=timeInfo[0];
                            pt = timeInfo[1];
                        }else
                            pt=workingInfo.getDate_time();
                        th.publishTime.setText(pt + "  发布");
                        return;
                    }
                }
            }
        }else if(i==1) {
            MidViewHolder mh=(MidViewHolder)viewHolder;
            setTopInfoItem(mh);
        }else if(i==2) {
            BottomViewHolder ph=(BottomViewHolder)viewHolder;
        }
    }

    @Override
    public int getItemCount()
    {
        // 返回数据总数
        return 4;
    }

    // 重写的自定义ViewHolder
    public class TopViewHolder extends RecyclerView.ViewHolder
    {
        ImageView windDirect;
        TextView dateTxt, fengxiangTxt, chaoweiTxt, langgaoTxt, boxiangTxt, publishTime, safeTxt;
        public TopViewHolder( View v )
        {
            super(v);
            windDirect=(ImageView)v.findViewById(R.id.windDirect);
            dateTxt=(TextView)v.findViewById(R.id.dateTxt);
            fengxiangTxt=(TextView)v.findViewById(R.id.fengxiangTxt);
            chaoweiTxt=(TextView)v.findViewById(R.id.chaoweiTxt);
            langgaoTxt=(TextView)v.findViewById(R.id.langgaoTxt);
            boxiangTxt=(TextView)v.findViewById(R.id.boxiangTxt);
            publishTime=(TextView)v.findViewById(R.id.publishTime);
            safeTxt=(TextView)v.findViewById(R.id.safeTxt);
        }
    }

    public class MidViewHolder extends RecyclerView.ViewHolder
    {

        TextView moreHint;
        LinearLayout topDaysInfoLayout, bottomDayInfoLayout1, bottomDayInfoLayout2;

        public MidViewHolder( View v )
        {
            super(v);
            moreHint=(TextView)v.findViewById(R.id.moreHint);
            topDaysInfoLayout= (LinearLayout) v.findViewById(R.id.topDaysInfoLayout);
            bottomDayInfoLayout2 = (LinearLayout) v.findViewById(R.id.bottomDayInfoLayout2);
            bottomDayInfoLayout1 = (LinearLayout) v.findViewById(R.id.bottomDayInfoLayout1);
            v.findViewById(R.id.showMore).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bottomDayInfoLayout2.isShown()) {
                        moreHint.setText("显示");
                        bottomDayInfoLayout2.setVisibility(View.GONE);
                    } else {
                        bottomDayInfoLayout2.setVisibility(View.VISIBLE);
                        moreHint.setText("隐藏");
                    }
                }
            });
        }
    }

    public class BottomViewHolder extends RecyclerView.ViewHolder
    {
        WebView mWebView;
        TextView dateTxt1,dateTxt2,dateTxt3;
        public BottomViewHolder(View v, String url)
        {
            super(v);
            dateTxt1=(TextView)v.findViewById(R.id.dateTxt1);
            dateTxt2=(TextView)v.findViewById(R.id.dateTxt2);
            dateTxt3=(TextView)v.findViewById(R.id.dateTxt3);
            mWebView=(WebView)v.findViewById(R.id.mWebView);
//            mWebView.setBackgroundColor(1);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.setBackgroundColor(mContext.getResources().getColor(R.color.hard_blue));
            mWebView.setWebChromeClient(new WebChromeClient());
            mWebView.clearCache(false);
            mWebView.setFocusable(false);
            mWebView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    mWebView.loadUrl("javascript:setData('" + getJsonStr() + "')");
                    RelativeLayout.LayoutParams rlp1= (RelativeLayout.LayoutParams) dateTxt1.getLayoutParams();
                    rlp1.leftMargin=formedge+(formWidth/12*timeCenterPos-textWidth)/2;
                    dateTxt1.setLayoutParams(rlp1);
                    dateTxt1.setText(TimeUtil.getDate(publishDate));
                    RelativeLayout.LayoutParams rlp2= (RelativeLayout.LayoutParams) dateTxt2.getLayoutParams();
                    rlp2.leftMargin=formedge+formWidth/12*timeCenterPos+(formWidth/12*(12-timeCenterPos)-textWidth)/2;
                    dateTxt2.setLayoutParams(rlp2);
                    dateTxt2.setText(TimeUtil.getNextDate(publishDate, 1));
                    if(timeCenterPos2!=-1){
                        RelativeLayout.LayoutParams rlp3= (RelativeLayout.LayoutParams) dateTxt3.getLayoutParams();
                        rlp3.leftMargin=formedge+formWidth/12*timeCenterPos2+(formWidth/12*(12-timeCenterPos2)-textWidth)/2;
                        dateTxt3.setLayoutParams(rlp3);
                        dateTxt3.setText(TimeUtil.getNextDate(publishDate, 2));
                    }

                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            mWebView.loadUrl(url);
        }
    }

    private void setTopInfoItem(MidViewHolder mh){

        int Swidth = 40;
        int Lwidth = 60;
        mh.topDaysInfoLayout.addView(ActUtil.getTextViewWithWidth(mContext, "时间", 14, Lwidth));
        mh.topDaysInfoLayout.addView(ActUtil.getTextViewWithWidth(mContext, "风\n(m/s)", 15, 50));
        mh.topDaysInfoLayout.addView(ActUtil.getTextViewWithWidth(mContext, "潮位\n(cm)", 15, 50));
        mh.topDaysInfoLayout.addView(ActUtil.getTextViewWithWidth(mContext, "浪高\n(m)", 15, Swidth));
        mh.topDaysInfoLayout.addView(ActUtil.getTextViewWithWidth(mContext, "波向", 14, Lwidth));
        mh.topDaysInfoLayout.addView(ActUtil.getTextViewWithWidth(mContext, "安全\n等级", 14, Lwidth));

        int marginTop= ScreenUtil.dip2px(mContext, 2);
        int itemHeight= ScreenUtil.dip2px(mContext, 26);
//        Bitmap tmp= BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_wind);
        for (int i=0; i<workingInfo.getDetail().size(); i++){
            WeatherInfo info=workingInfo.getDetail().get(i);
            LinearLayout layout=new LinearLayout(mContext);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setGravity(Gravity.CENTER);
            String safeValue="1";
            if(info.getSafe().equals("不安全")) {
                safeValue="2";
                layout.setBackgroundColor(mContext.getResources().getColor(R.color.normal_orange));
            }else if(info.getSafe().equals("极不安全")) {
                layout.setBackgroundColor(mContext.getResources().getColor(R.color.norma_red));
                safeValue="3";
            }
            LinearLayout.LayoutParams llp=new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, itemHeight);
            llp.topMargin=marginTop;
            layout.setLayoutParams(llp);
            layout.addView(ActUtil.getTextViewWithWidth(mContext, info.getDt(), 14, Lwidth));
            layout.addView(ActUtil.getTextViewWithWidth(mContext, info.getWiSV(), 14, 35));
            ImageView img=ActUtil.getImageViewS(mContext, 0, 12, 12);
            layout.addView(img);
            ActUtil.setDirectImg(workingInfo.getDetail().get(i).getWiDT(), img);
            layout.addView(ActUtil.getTextViewWithWidth(mContext, info.gettL(), 14, 50));
            layout.addView(ActUtil.getTextViewWithWidth(mContext, info.getWaH(), 14, 40));
            layout.addView(ActUtil.getTextViewWithWidth(mContext, info.getWaDT(), 14, Lwidth));
            layout.addView(ActUtil.getTextViewWithWidth(mContext, info.getSafe(), 14, Lwidth));
            if(i>8){
                mh.bottomDayInfoLayout2.addView(layout);
            }else
                mh.bottomDayInfoLayout1.addView(layout);
        }
    }

    private String getJsonStr() {
        timeCenterPos=-1;
        timeCenterPos2=-1;
        String jsonData="";
        try {
            int max=12;
            JSONObject jsonObj=new JSONObject();
            if(workingInfo.getDetail().size()<max)
                max=workingInfo.getDetail().size();

            JSONArray array=new JSONArray();
            JSONArray array2=new JSONArray();
            JSONArray array3=new JSONArray();
            JSONArray array4=new JSONArray();
            JSONArray array5=new JSONArray();
            for(int i=0;i<max;i++){
                JSONObject objsub=new JSONObject();
                String safeTxt=workingInfo.getDetail().get(i).getSafe();
                String safeLevel="";
                if(safeTxt.equals("安全")){
                    safeLevel="1";
                }else if(safeTxt.equals("不安全")){
                    safeLevel="2";
                }else if(safeTxt.equals("极不安全")){
                    safeLevel="3";
                }
                objsub.put("data", safeLevel);
                array.put(i, objsub);
                objsub=new JSONObject();
                JSONObject wind=new JSONObject();
                wind.put("value", workingInfo.getDetail().get(i).getWiSV());
                wind.put("symbol", "arrow");
                wind.put("symbolSize", 5);
                wind.put("symbolRotate", ActUtil.getDegree(workingInfo.getDetail().get(i).getWiDT()));
                objsub.put("data",  wind);
                array2.put(i, objsub);
                objsub=new JSONObject();

                JSONObject direct=new JSONObject();
                direct.put("value", workingInfo.getDetail().get(i).getWaH());
                direct.put("symbol", "arrow");
                direct.put("symbolSize", 5);
                direct.put("symbolRotate", ActUtil.getDegree(workingInfo.getDetail().get(i).getWaDT()));
                objsub.put("data",  direct);
                array3.put(i, objsub);
                objsub=new JSONObject();
                objsub.put("data", workingInfo.getDetail().get(i).gettL());
                array4.put(i, objsub);
                objsub=new JSONObject();
                String time=workingInfo.getDetail().get(i).getTime();
                if(i>0){
                    if(Integer.valueOf(time)<Integer.valueOf(workingInfo.getDetail().get(i-1).getTime())) {
                        if(timeCenterPos==-1)
                            timeCenterPos = i;
                        else
                            timeCenterPos2= i;
                    }
                }
                objsub.put("data", time);
                array5.put(i, objsub);
            }
            jsonObj.put("FristFormInfo1", array);
            jsonObj.put("FristFormInfo2", array2);
            jsonObj.put("SecondFormInfo1", array3);
            jsonObj.put("SecondFormInfo2", array4);
            jsonObj.put("tags", array5);

            jsonData=jsonObj.toString();
            Log.i("Weather", jsonObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    String[] formData1={"0.8", "2.2", "1.3", "1.5", "1.2", "0.7", "0.6", "0.7", "1", "1.2", "1.1", "0.7"};
    String[] formData2={"40", "40", "40", "45", "46", "48", "38", "35", "35", "40", "45", "30"};
    String[] formData3={"1.3", "1.9", "2.0", "1.4", "2.7", "1.7", "2.6", "1.2", "1.7", "0.8", "1.0", "2.3"};
    String[] formData4={"46.3", "32.2", "36.6", "75.1", "34.6", "50.2", "36.3", "32.2", "46.6", "75.1", "34.6", "50.2"};
    String[] tags={"46", "32", "36", "75", "34", "50", "36", "32", "46", "75", "34", "50"};
    private String getJsonData() {
        String jsonData="";
        try {
            JSONObject jsonObj=new JSONObject();
            JSONArray array=new JSONArray();
            for(int i=0;i<formData1.length;i++){
                JSONObject objsub=new JSONObject();
                objsub.put("data", formData1[i]);
                array.put(i, objsub);
            }
            jsonObj.put("FristFormInfo1", array);

            array=new JSONArray();
            for(int i=0;i<formData2.length;i++){
                JSONObject objsub=new JSONObject();
                objsub.put("data", formData2[i]);
                array.put(i, objsub);
            }
            jsonObj.put("FristFormInfo2", array);

            array=new JSONArray();
            for(int i=0;i<formData3.length;i++){
                JSONObject objsub=new JSONObject();
                objsub.put("data", formData3[i]);
                array.put(i, objsub);
            }
            jsonObj.put("SecondFormInfo1", array);

            array=new JSONArray();
            for(int i=0;i<formData4.length;i++){
                JSONObject objsub=new JSONObject();
                objsub.put("data", formData4[i]);
                array.put(i, objsub);
            }
            jsonObj.put("SecondFormInfo2", array);

            array=new JSONArray();
            for(int i=0;i<tags.length;i++){
                JSONObject objsub=new JSONObject();
                objsub.put("data", tags[i]);
                array.put(i, objsub);
            }
            jsonObj.put("tags", array);

            jsonData=jsonObj.toString();
            LogUtil.d("Weather", jsonObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }
}
