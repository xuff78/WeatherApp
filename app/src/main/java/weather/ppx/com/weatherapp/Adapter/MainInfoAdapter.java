package weather.ppx.com.weatherapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weather.ppx.com.weatherapp.Bean.AreaWorkingInfo;
import weather.ppx.com.weatherapp.Bean.WeatherInfo;
import weather.ppx.com.weatherapp.R;
import weather.ppx.com.weatherapp.Util.ActUtil;
import weather.ppx.com.weatherapp.Util.LogUtil;
import weather.ppx.com.weatherapp.Util.ScreenUtil;

/**
 * Created by 可爱的蘑菇 on 2015/8/27.
 */
public class MainInfoAdapter extends RecyclerView.Adapter{


    private Context mContext;
    private AreaWorkingInfo workingInfo;

    public MainInfoAdapter(Context context, AreaWorkingInfo workingInfo)
    {
        this.mContext = context;
        this.workingInfo=workingInfo;
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
            holder=new BottomViewHolder(v);
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
                WeatherInfo info=workingInfo.getDetail().get(0);
                th.dateTxt.setText(info.getDt());
                th.fengxiangTxt.setText("风速：" + info.getWiSV()+"级");
                th.langgaoTxt.setText("浪高：" + info.getWaH()+"m");
                th.chaoweiTxt.setText("潮位：" + info.gettL()+"cm");
                th.boxiangTxt.setText("波向：" + info.getWaDT());
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
        return 3;
    }

    // 重写的自定义ViewHolder
    public class TopViewHolder extends RecyclerView.ViewHolder
    {
        TextView dateTxt, fengxiangTxt, chaoweiTxt, langgaoTxt, boxiangTxt;
        public TopViewHolder( View v )
        {
            super(v);
            dateTxt=(TextView)v.findViewById(R.id.fengxiangTxt);
            fengxiangTxt=(TextView)v.findViewById(R.id.fengxiangTxt);
            chaoweiTxt=(TextView)v.findViewById(R.id.chaoweiTxt);
            langgaoTxt=(TextView)v.findViewById(R.id.langgaoTxt);
            boxiangTxt=(TextView)v.findViewById(R.id.boxiangTxt);
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
        public BottomViewHolder( View v )
        {
            super(v);
            mWebView=(WebView)v.findViewById(R.id.mWebView);
//            mWebView.setBackgroundColor(1);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.setBackgroundColor(mContext.getResources().getColor(R.color.hard_blue));
            mWebView.loadUrl("file:///android_asset/test.html");
            mWebView.setWebChromeClient(new WebChromeClient());
            mWebView.setFocusable(false);
            mWebView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    mWebView.loadUrl("javascript:setData('" + getJsonData() + "')");
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
        }
    }

    private void setTopInfoItem(MidViewHolder mh){

        int Swidth = 40;
        int Lwidth = 60;
        mh.topDaysInfoLayout.addView(ActUtil.getTextViewWithWidth(mContext, "时间", 15, Lwidth));
        mh.topDaysInfoLayout.addView(ActUtil.getTextViewWithWidth(mContext, "风", 15, Lwidth));
        mh.topDaysInfoLayout.addView(ActUtil.getTextViewWithWidth(mContext, "浪高\n(m)", 15, Swidth));
        mh.topDaysInfoLayout.addView(ActUtil.getTextViewWithWidth(mContext, "潮位\n(cm)", 15, Swidth));
        mh.topDaysInfoLayout.addView(ActUtil.getTextViewWithWidth(mContext, "波向", 15, Lwidth));
        mh.topDaysInfoLayout.addView(ActUtil.getTextViewWithWidth(mContext, "安全\n等级", 15, Lwidth));

        int marginTop= ScreenUtil.dip2px(mContext, 2);
        int itemHeight= ScreenUtil.dip2px(mContext, 26);
        for (int i=0; i<workingInfo.getDetail().size(); i++){
            WeatherInfo info=workingInfo.getDetail().get(i);
            LinearLayout layout=new LinearLayout(mContext);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setGravity(Gravity.CENTER);
            if(info.getSafe().equals("不安全"))
                layout.setBackgroundColor(mContext.getResources().getColor(R.color.normal_orange));
            else if(info.getSafe().equals("极不安全"))
                layout.setBackgroundColor(mContext.getResources().getColor(R.color.norma_red));
            LinearLayout.LayoutParams llp=new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, itemHeight);
            llp.topMargin=marginTop;
            layout.setLayoutParams(llp);
            layout.addView(ActUtil.getTextViewWithWidth(mContext, info.getDt(), 14, Lwidth));
            layout.addView(ActUtil.getTextViewWithWidth(mContext, info.getWiSV(), 14, Lwidth));
//            layout.addView(ActUtil.getImageView(mContext, R.drawable.icon_wind, 20));
            layout.addView(ActUtil.getTextViewWithWidth(mContext, info.getWaH(), 14, Swidth));
            layout.addView(ActUtil.getTextViewWithWidth(mContext, info.gettL(), 14, Swidth));
            layout.addView(ActUtil.getTextViewWithWidth(mContext, info.getWaDT(), 14, Lwidth));
            layout.addView(ActUtil.getTextViewWithWidth(mContext, info.getSafe(), 14, Lwidth));
            if(i>3){
                mh.bottomDayInfoLayout2.addView(layout);
            }else
                mh.bottomDayInfoLayout1.addView(layout);
        }
    }

    String[] formData1={"0.8", "2.2", "1.3", "1.5", "1.2", "0.7", "0.6", "0.7", "1", "1.2", "1.1", "0.7"};
    String[] formData2={"40", "40", "40", "45", "46", "48", "38", "35", "35", "40", "45", "30"};
    String[] formData3={"1.3", "1.9", "2.0", "1.4", "2.7", "1.7", "2.6", "1.2", "1.7", "0.8", "1.0", "2.3"};
    String[] formData4={"46.3", "32.2", "36.6", "75.1", "34.6", "50.2", "36.3", "32.2", "46.6", "75.1", "34.6", "50.2"};
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

            jsonData=jsonObj.toString();
            LogUtil.d("Weather", jsonObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }
}
