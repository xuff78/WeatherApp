package weather.ppx.com.weatherapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import common.eric.com.ebaselibrary.util.ToastUtils;
import weather.ppx.com.weatherapp.Util.ActUtil;
import weather.ppx.com.weatherapp.Util.ConstantUtil;
import weather.ppx.com.weatherapp.Util.LogUtil;
import weather.ppx.com.weatherapp.Util.SharedPreferencesUtil;
import weather.ppx.com.weatherapp.View.SlipButton;

/**
 * Created by Administrator on 2015/10/19.
 */
public class UserFeedBack extends BaseActivity {

    SlipButton btnLocation, btnPush;
    private ImageView themeChecked1, themeChecked2, bgImg1, bgImg2;
    private EditText feedbackEdt;
    int bgType=0;
    private ProgressDialog progressDialog;
    private ArrayList<RelativeLayout> layouts=new ArrayList<RelativeLayout>();
    private int[] resLayout={R.id.feedbackLayout1, R.id.feedbackLayout2, R.id.feedbackLayout3, R.id.feedbackLayout4
            , R.id.feedbackLayout5, R.id.feedbackLayout6, R.id.feedbackLayout7};

    private boolean[]checks=new boolean[resLayout.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);


        _setHeaderTitle("用户反馈");
        initView();
    }

    private void initView() {
        feedbackEdt=(EditText)findViewById(R.id.feedbackEdt);
        for (int i=0;i<resLayout.length;i++) {
            RelativeLayout rl = (RelativeLayout) findViewById(resLayout[i]);
            rl.setTag(i);
            rl.setOnClickListener(listener);
            layouts.add(rl);
            checks[i]=false;
        }
        findViewById(R.id.feedbackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback = "";
                boolean hasFeedBack = false;
                for (int i = 0; i < checks.length; i++) {
                    feedback += String.valueOf(checks[i])+",";
                    if (checks[i])
                        hasFeedBack = true;
                }
                String edtTxt = feedbackEdt.getText().toString();
                final String fb=feedback+edtTxt;
                if (!hasFeedBack && edtTxt.length() == 0) {
                    ToastUtils.show(UserFeedBack.this, "请先反馈问题");
                } else {
                    progressDialog=new ProgressDialog(UserFeedBack.this);
                    progressDialog.setMessage("数据获取中..");
                    progressDialog.show();
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            getRemoteInfo(fb);
                        }
                    }.start();
                }
            }
        });
    }

    View.OnClickListener listener=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            int pos=(Integer) v.getTag();
            RelativeLayout layout = layouts.get(pos);
            ImageView checkbox = (ImageView) layout.getChildAt(0);
            if(checks[pos]) {
                checkbox.setBackgroundResource(R.drawable.app_login_remember_unsel);
            }else{
                checkbox.setBackgroundResource(R.drawable.app_login_remember_sel);
            }
            checks[pos]=!checks[pos];
        }
    };

    public void getRemoteInfo(String msg) {
        // 命名空间
        String nameSpace = "http://tempuri.org/";
        // 调用的方法名称
        String methodName = "UserData";
        // EndPoint
        String endPoint = "http://222.190.109.118/jfapp/WebService.asmx";
        // SOAP Action
        String soapAction = "http://tempuri.org/UserData";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("msg", msg);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
            Object response = envelope.bodyIn;
            SoapObject object = (SoapObject) response;
            int count=object.getPropertyCount();
            String result = "";
            for(int index=0;index<count;index++){
                result+=object.getProperty(index).toString();
            }

            // 将WebService返回的结果显示在TextView中
            LogUtil.i("test1", result);
            final String putout=result;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    ToastUtils.show(UserFeedBack.this, putout);
                    ActUtil.showSinglseDialog(UserFeedBack.this, "反馈已发送，感谢您的支持", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
//            try {
//                final SoapPrimitive soapPrimitive =(SoapPrimitive) envelope.getResponse();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressDialog.dismiss();
//                        ToastUtils.show(UserFeedBack.this, soapPrimitive.toString());
//                    }
//                });
//            } catch (Exception ex) {
//
//            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    ToastUtils.show(UserFeedBack.this, "发送失败,请稍后重试");
                }
            });
        }

    }
}
