package com.xhxm.ospot;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;


public class Splash extends Activity implements AdStatusListener {
	
	public static String d = "com.xhxm.ospot.DemoActivity";
    private TextView topTextview;
    private ImageView bigimg;
    private FrameLayout mframeLayout;
    private boolean i = false;

    
    private Handler mHandler = new Handler(Looper.getMainLooper()){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int i = msg.arg1;
			if(i>0){
				onAdCountdown(i);
				Message nmsg = mHandler.obtainMessage();
				nmsg.arg1 = --i;
				this.sendMessageDelayed(nmsg, 1000);
			}else{
				startMainActivity();
			}
			
		}
    	
    };

    
    private void startMainActivity() {
    	try {
    		if(!i){
    			Intent intent = new Intent();
    			intent.setClassName(getPackageName(), d);
    			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    			startActivity(intent);
    			i = true;
    			finish();
    		}
		} catch (Exception e) {
			// TODO: handle exception
		}

    }


    private int a(float f) {
        return (int) ((f * getResources().getDisplayMetrics().density) + 0.5f);
    }

    private GradientDrawable getTextViewbg(float cornerRadius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor("#80000000"));
        gradientDrawable.setCornerRadius(cornerRadius);
        return gradientDrawable;
    }


    private Drawable a(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        if (str != null) {
            try {
            	Drawable d = packageManager.getApplicationInfo(str, 0).loadIcon(packageManager);
                return d;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return packageManager.getDefaultActivityIcon();
    }


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(getRelativeLayout());
		if (Build.VERSION.SDK_INT >= 23 || getApplicationInfo().targetSdkVersion >= 23) {
			checkAndRequestPermission();
		} 
	}
	
	private RelativeLayout getRelativeLayout(){
		RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setId(0);
        relativeLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        relativeLayout.setBackgroundColor(Color.parseColor("#ffffffff"));
        
        //ICON
        ImageView imageView = new ImageView(this);
        imageView.setId(1);
        LayoutParams layoutParams = new LayoutParams(a(70.0f), a(70.0f));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.setMargins(0, a(15.0f), 0, a(15.0f));
        imageView.setLayoutParams(layoutParams);
        imageView.setImageDrawable(a(this, getPackageName()));
        
        
        
        
        this.mframeLayout = new FrameLayout(this);
        this.mframeLayout.setId(2);
        
        LayoutParams layoutParams2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams2.addRule(RelativeLayout.ABOVE, 1);
        
        this.mframeLayout.setLayoutParams(layoutParams2);
        
        this.topTextview = new TextView(this);
        this.topTextview.setId(3);
        
        LayoutParams layoutParams3 = new LayoutParams(a(96.0f), LayoutParams.WRAP_CONTENT );
        layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT );
        layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams3.setMargins(a(16.0f), a(16.0f), a(16.0f), a(16.0f));
        
        
        this.topTextview.setLayoutParams(layoutParams3);
        this.topTextview.setBackgroundDrawable(getTextViewbg(45.0f));
        this.topTextview.setGravity(17);
        this.topTextview.setTextColor(Color.parseColor("#ffffffff"));
        this.topTextview.setTextSize(14.0f);
        this.topTextview.setVisibility(View.INVISIBLE);
        topTextview.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startMainActivity();
			}
		});
        
        
        LayoutParams adTextviewlayoutParams = new LayoutParams(a(30.0f), LayoutParams.WRAP_CONTENT);
        adTextviewlayoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, 2);
        adTextviewlayoutParams.addRule(RelativeLayout.ALIGN_RIGHT, 2);
        adTextviewlayoutParams.setMargins(0, 0, 0, a(20.0f));
        
        TextView adTextview = new TextView(this);
        adTextview.setLayoutParams(adTextviewlayoutParams);
        adTextview.setId(6);
        adTextview.setGravity(17);
        adTextview.setText("广告");
        adTextview.setTextColor(Color.parseColor("#eeeeeeee"));
        adTextview.setTextSize(10.0f);
        adTextview.setBackgroundDrawable(getTextViewbg(0));
        adTextview.setGravity(17);
        
        
        
        
        this.bigimg = new ImageView(this);
        this.bigimg.setId(4);
        
        
        layoutParams2 = new LayoutParams(-1, -1);
        layoutParams2.addRule(RelativeLayout.ABOVE, 1);
        
        this.bigimg.setLayoutParams(layoutParams2);
        this.bigimg.setScaleType(ScaleType.FIT_XY);
        
        relativeLayout.addView(imageView);
        relativeLayout.addView(this.mframeLayout);
        relativeLayout.addView(adTextview);
        relativeLayout.addView(this.topTextview);
        
        relativeLayout.addView(this.bigimg);
		
		return relativeLayout;
	}
	
	/**
	 * 请求广告view
	 */
	private void initad(){
		DeeplinkAd xm = DeeplinkAd.getInstance();
		xm.setID(getApplicationContext(),"8fb7fd2f10c64bf29ce22d3efa0013f5", "1126");
		xm.setAdListener(this);
		xm.loadAdView(getApplicationContext(), 2);
	}
	
    public void onAdCountdown(int i) {
        this.topTextview.setVisibility(View.VISIBLE);
        this.topTextview.setText(String.format("点击跳过%s", new Object[]{String.valueOf(i)}));
    }


	@Override
	public void onADClicked() {
		
	}


	@Override
	public void onADReceive() {
		
	}


	@Override
	public void onADReceive(SplashAdView view) {
		//接收到广告布局视图view,添加进自己的UI界面中
		if(view!=null && !this.isFinishing()){
			mframeLayout.addView(view);
			Message nmsg = mHandler.obtainMessage();
			nmsg.what = 1;
			nmsg.arg1 = 5;
			mHandler.sendMessage(nmsg);
		}else{
			startMainActivity();
		}
	}


	@Override
	public void onADClosed() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
		if (paramInt == 4) {
			return true;
		}
		return super.onKeyDown(paramInt, paramKeyEvent);
	}

	@Override
	public void onNoAD(String errorinfo) {
		startMainActivity();
	}
	
	//处理广告被点击回来之后的正常开屏跳转,
    protected void onPause() {
        super.onPause();
        this.i = true;
    }

    protected void onResume() {
        super.onResume();
        if (this.i) {
        	this.i = false;
        	startMainActivity();
        }
    }


	@TargetApi(Build.VERSION_CODES.M)
	private void checkAndRequestPermission() {
		List<String> lackedPermission = new ArrayList<String>();
		if (!(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
			lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
		}

		if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
			lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
		}

		// 权限都已经有了，那么直接调用SDK
		if (lackedPermission.size() == 0) {
			initad();
		} else {
			// 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
			String[] requestPermissions = new String[lackedPermission.size()];
			lackedPermission.toArray(requestPermissions);
			requestPermissions(requestPermissions, 1000);
		}
	}
	
	@TargetApi(23)
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000 && hasAllPermissionsGranted(grantResults)) {
			this.i = false;
			initad();
		}
	}

	private boolean hasAllPermissionsGranted(int[] grantResults) {
		for (int grantResult : grantResults) {
			if (grantResult == PackageManager.PERMISSION_DENIED) {
				return false;
			}
		}
		return true;
	}
}
