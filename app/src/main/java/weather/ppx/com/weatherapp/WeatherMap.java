package weather.ppx.com.weatherapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import weather.ppx.com.weatherapp.Bean.AreaInfo;
import weather.ppx.com.weatherapp.Fragment.BaseFragment;
import weather.ppx.com.weatherapp.Util.ConstantUtil;
import weather.ppx.com.weatherapp.Util.ScreenUtil;

/**
 * Created by Administrator on 2015/8/25.
 */
public class WeatherMap extends BaseActivity {



    ArrayList<AreaInfo> areaInfos=new ArrayList<AreaInfo>();
    ArrayList<ImageView> imgs=new ArrayList<ImageView>();
    int bmpHeight=0, bmpWidth=0;
    float scale;
    RelativeLayout mapLayout;
    int[] colors;
    int dip50=0;
    SeekBar seekBar;
    ImageView playBtn, prevBtn, nextBtn;
    private boolean isPlay=false;
    Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);

        _setHeaderTitle("地图");
        colors=new int[3];
        colors[0]=getResources().getColor(R.color.normal_green);
        colors[1]=getResources().getColor(R.color.normal_brown);
        colors[2]=getResources().getColor(R.color.norma_red);
        dip50=ScreenUtil.dip2px(this,60);
        initView();
    }

    private void initView() {
        playBtn=(ImageView)findViewById(R.id.playBtn);
        playBtn.setOnClickListener(listenr);
        prevBtn=(ImageView)findViewById(R.id.prevBtn);
        prevBtn.setOnClickListener(listenr);
        nextBtn=(ImageView)findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(listenr);
        seekBar=(SeekBar)findViewById(R.id.seekBar);
        mapLayout=(RelativeLayout)findViewById(R.id.mapLayout);
        ImageView mapImg=(ImageView)findViewById(R.id.mapImg);
        Bitmap bmptmp= BitmapFactory.decodeResource(getResources(),R.drawable.mpbg2);
        scale=(float) ScreenUtil.getScreenWidth(this)/bmptmp.getWidth();
        Bitmap bmp=Bitmap.createScaledBitmap(bmptmp, ScreenUtil.getScreenWidth(this), (int) (scale*bmptmp.getHeight()), false);
        bmpHeight=bmp.getHeight();
        bmpWidth=bmp.getWidth();
        mapImg.setLayoutParams(new RelativeLayout.LayoutParams(bmpWidth, bmpHeight));
        mapImg.setImageBitmap(bmp);
        if(scale!=1&&!bmptmp.isRecycled())
            bmptmp.recycle();

        setImageOnMap();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() % 5 != 0) {
                    seekBar.setProgress(seekBar.getProgress() / 5 * 5);
                }
                setAreaColors();
            }
        });
    }

    private void setImageOnMap() {
        areaInfos.add(new AreaInfo(R.drawable.gangyu, 0.082f, 0f));
        areaInfos.add(new AreaInfo(R.drawable.guanyun, 0.082f, 0.082f));
        areaInfos.add(new AreaInfo(R.drawable.xiangshui, 0.178f, 0.146f));
        areaInfos.add(new AreaInfo(R.drawable.haibing, 0.373f, 0.228f));
        areaInfos.add(new AreaInfo(R.drawable.sheyang, 0.405f, 0.311f));
        areaInfos.add(new AreaInfo(R.drawable.dafeng, 0.475f, 0.401f));
        areaInfos.add(new AreaInfo(R.drawable.dongtai, 0.536f, 0.531f));
        areaInfos.add(new AreaInfo(R.drawable.rudong, 0.573f, 0.623f));
        areaInfos.add(new AreaInfo(R.drawable.qidong, 0.705f, 0.775f));
        for (int i=0; i<areaInfos.size(); i++){
            final int j=i;
            RelativeLayout rlayout=new RelativeLayout(this);
            ImageView img=new ImageView(this);
            Bitmap bmpimg= BitmapFactory.decodeResource(getResources(),areaInfos.get(i).getResId());
            img.setImageBitmap(bmpimg);
            img.setPadding(0, 0, 0, 4);
            RelativeLayout.LayoutParams rlpimg=new RelativeLayout.LayoutParams((int) (bmpimg.getWidth()*scale), (int) (bmpimg.getHeight()*scale));
            img.setLayoutParams(rlpimg);
            imgs.add(img);
            TextView tv=new TextView(this);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(14);
            tv.setText(ConstantUtil.areaNames[i]);
            RelativeLayout.LayoutParams rlptxt=new RelativeLayout.LayoutParams(-2, -2);
            if(i==areaInfos.size()-1){
                rlptxt.addRule(RelativeLayout.CENTER_VERTICAL);
                rlptxt.leftMargin = rlpimg.height/2;
            }else {
                rlptxt.addRule(RelativeLayout.CENTER_IN_PARENT);
            }
            tv.setLayoutParams(rlptxt);
            RelativeLayout.LayoutParams rlp=new RelativeLayout.LayoutParams(-2,-2);
            rlp.leftMargin= (int) (bmpWidth*areaInfos.get(i).getXpos());
            rlp.topMargin= (int) (bmpHeight*areaInfos.get(i).getYpos());
            rlayout.setLayoutParams(rlp);
            rlayout.addView(img);
            rlayout.addView(tv);
            mapLayout.addView(rlayout);

            Drawable imgDrawable=img.getDrawable();
            imgDrawable.setColorFilter(colors[i%3], PorterDuff.Mode.SRC_IN);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupWindow(view, j);
                }
            });
        }
    }

    private void setAreaColors(){
        for (int i=0; i<imgs.size(); i++){

            Drawable imgDrawable=imgs.get(i).getDrawable();
            imgDrawable.setColorFilter(colors[((int) (Math.random() * 3))], PorterDuff.Mode.SRC_IN);

        }
    }

    View.OnClickListener listenr=new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.playBtn:
                    if(isPlay){
                        stopTimer();
                        playBtn.setImageResource(R.drawable.map_icon_play);
                    }else{
                        startTimer();
                        playBtn.setImageResource(R.drawable.map_icon_pause);
                    }
                    isPlay=!isPlay;
                    break;
                case R.id.nextBtn:
                    toNextInfo();
                case R.id.prevBtn:
                    toPrvInfo();
                    break;
            }
        }
    };

    private void showPopupWindow(View view, int pos) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_window, null);
        // 设置按钮的点击事件
        TextView txt1 = (TextView) contentView.findViewById(R.id.txt1);
        TextView txt2 = (TextView) contentView.findViewById(R.id.txt2);


        final PopupWindow popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);


        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.block_white));

        // 设置好参数之后再show
        int padding=0;
        if(pos>4) {
            padding = -dip50 * 5/2;
            contentView.setBackgroundResource(R.drawable.popbgright);
        }else {
            padding = dip50 * 2;
            contentView.setBackgroundResource(R.drawable.popbg);
        }
        popupWindow.showAsDropDown(view, padding, -(view.getHeight()/2+dip50));

    }

    private Handler handler=new Handler();
    private TimerTask task;
    private Timer timer=new Timer();
    private void startTimer() {

        task = new TimerTask(){

            @Override
            public void run() {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        toNextInfo();
                    }

                });
            }

        };
        timer.scheduleAtFixedRate(task, 0, 5000);
    }

    private void toNextInfo() {
        if(seekBar.getProgress()<100){
            seekBar.setProgress(seekBar.getProgress()+5);
            setAreaColors();
        }else if(seekBar.getProgress()==100){
            stopTimer();
            seekBar.setProgress(0);
            playBtn.setImageResource(R.drawable.map_icon_play);
        }
    }

    private void toPrvInfo() {
        if(seekBar.getProgress()>0){
            seekBar.setProgress(seekBar.getProgress()-5);
            setAreaColors();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(task!=null)
            stopTimer();
    }

    private void stopTimer() {
        task.cancel();
    }

}

