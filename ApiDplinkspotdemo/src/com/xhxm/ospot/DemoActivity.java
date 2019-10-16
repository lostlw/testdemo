package com.xhxm.ospot;


import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DemoActivity extends Activity  implements View.OnClickListener ,AdStatusListener {

	
	private static final int bnt00ID = 0x1122;
	private static final int bnt01ID = 0x1223;
	private static final int bnt02ID = 0x1324;
	private static final int bnt03ID = 0x1425;
	private static final int bnt04ID = 0x1426;
	
	private DeeplinkAd xm = DeeplinkAd.getInstance();
	
	private String appid = "8fb7fd2f10c64bf29ce22d3efa0013f5";
	
	private LinearLayout tl;
	EditText ed_spot;
	EditText ed_list;
	EditText etappid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		View v = getView();
		setContentView(v);
		
		xm.setAdListener(this);
		
		if (Build.VERSION.SDK_INT >= 23 || getApplicationInfo().targetSdkVersion >= 23) {
			checkAndRequestPermission();
		} 
	}
	
	
	
	public View getView(){
		tl = new LinearLayout(getApplicationContext());
		tl.setOrientation(LinearLayout.VERTICAL);
		
		
		LinearLayout appidtx = new LinearLayout(getApplicationContext());
		appidtx.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView tvappid = new TextView(getApplicationContext());
		tvappid.setText("请输入你的APPID：");
		tvappid.setTextColor(Color.BLACK);
		appidtx.addView(tvappid);
		
		
		etappid = new EditText(getApplicationContext());
		etappid.setHint("请输入你的APPID");
		etappid.setHintTextColor(Color.GRAY);
		etappid.setTextColor(Color.BLACK);
		etappid.setId(bnt04ID);
		etappid.setText(appid);
		appidtx.addView(etappid);
		
		LinearLayout ggw = new LinearLayout(getApplicationContext());
		ggw.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView tv01 = new TextView(getApplicationContext());
		tv01.setText("请输入你的插屏广告位ID：");
		tv01.setTextColor(Color.BLACK);
		ggw.addView(tv01);
		
		ed_spot = new EditText(getApplicationContext());
		ed_spot.setHint("请输入你的插屏广告位ID");
		ed_spot.setHintTextColor(Color.GRAY);
		ed_spot.setTextColor(Color.BLACK);
		ed_spot.setText(	"1011");
		ed_spot.setInputType(InputType.TYPE_CLASS_NUMBER);
		ed_spot.setId(bnt00ID);
		
		ggw.addView(ed_spot);
		
		
		
		LinearLayout listll = new LinearLayout(getApplicationContext());
		listll.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView tv02 = new TextView(getApplicationContext());
		tv02.setText("请输入你的列表广告位ID：");
		tv02.setTextColor(Color.BLACK);
		listll.addView(tv02);
		
		ed_list = new EditText(getApplicationContext());
		ed_list.setHint("请输入你的列表广告位ID");
		ed_list.setHintTextColor(Color.GRAY);
		ed_list.setTextColor(Color.BLACK);
		ed_list.setText(	"1250");
		ed_list.setInputType(InputType.TYPE_CLASS_NUMBER);
		ed_list.setId(bnt00ID);
		
		listll.addView(ed_list);
		
		Button btn = new Button(getApplicationContext());
		btn.setId(bnt01ID);
		btn.setText("插屏");
		
		Button btn02 = new Button(getApplicationContext());
		btn02.setId(bnt02ID);
		btn02.setText("退弹");
		
		Button btn03 = new Button(getApplicationContext());
		btn03.setId(bnt03ID);
		btn03.setText("列表");
		
		Button btn04 = new Button(getApplicationContext());
		btn04.setId(bnt04ID);
		btn04.setText("老插屏");
		
		tl.addView(appidtx);
		tl.addView(ggw);
		tl.addView(listll);
		
		tl.addView(btn);
		tl.addView(btn02);
		tl.addView(btn03);
		tl.addView(btn04);
		
        
		btn.setOnClickListener(this);
		btn02.setOnClickListener(this);
		btn03.setOnClickListener(this);
		btn04.setOnClickListener(this);
		return tl;
	}




	
	
	@Override
	public void onClick(View v) {
		

		String mappid = etappid.getText().toString();
		if(!TextUtils.isEmpty(mappid)){
			xm.setID(getApplicationContext(),mappid, "");
		}else{
			Toast.makeText(getApplicationContext(), "APPID不正确", Toast.LENGTH_LONG).show();
			return;
		}
		
		switch (v.getId()) {
			
		case bnt01ID:
			
			String sid = ed_spot.getText().toString();
			if(!TextUtils.isEmpty(sid)){
				xm.setID(getApplicationContext(),appid, sid);
			}else{
				Toast.makeText(getApplicationContext(), "广告位ID不正确", Toast.LENGTH_LONG).show();
				return;
			}
			//插屏广告
			xm.loadSpotAd(getApplicationContext());
	
			
			break;
		case bnt02ID:
			sid = ed_spot.getText().toString();
			if(!TextUtils.isEmpty(sid)){
				xm.setID(getApplicationContext(),appid, sid);
			}else{
				Toast.makeText(getApplicationContext(), "广告位ID不正确", Toast.LENGTH_LONG).show();
				return;
			}
			//3 退弹, 2,自定义view界面
			xm.loadAdView(getApplicationContext(),3);
			break;
			
		case bnt03ID:
			sid = ed_list.getText().toString();
			if(!TextUtils.isEmpty(sid)){
				xm.setID(getApplicationContext(),appid, sid);
			}else{
				Toast.makeText(getApplicationContext(), "广告位ID不正确", Toast.LENGTH_LONG).show();
				return;
			}
			xm.initListAd(getApplicationContext());
			break;
		case bnt04ID:
			sid = ed_spot.getText().toString();
			if(!TextUtils.isEmpty(sid)){
				xm.setID(getApplicationContext(),appid, sid);
			}else{
				Toast.makeText(getApplicationContext(), "广告位ID不正确", Toast.LENGTH_LONG).show();
				return;
			}
			xm.showSpot(getApplicationContext());
			break;
		default:
			break;
		}
		
	}


	@Override
	public void onADClicked() {
		Log.i("log", "onADClicked");
	}


	@Override
	public void onADReceive() {
		//插屏广告
		Log.i("log", "onADReceive " );
		xm.show(getApplicationContext());
	}


	@Override
	public void onADClosed() {
		Log.i("log", "onADClosed");
	}


	@Override
	public void onNoAD(String errorinfo) {
		Log.i("log", "onNoAD");
		Toast.makeText(getApplicationContext(), "未取到广告 " + errorinfo, 1).show();
	}


	@Override
	public void onADReceive(SplashAdView exitView) {
		if(exitView==null){
			xm.showExit(DemoActivity.this);
		}else{
			
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
