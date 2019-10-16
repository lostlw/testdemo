package com.xhxm.ospot.task;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.xhxm.ospot.AdConfig;
import com.xhxm.ospot.AdPool;
import com.xhxm.ospot.ListenerUtil;
import com.xhxm.ospot.SplashAdView;
import com.xhxm.ospot.model.AdContent;
import com.xhxm.ospot.model.AdRequestInfo;
import com.xhxm.ospot.operate.AdOperate;

public class GetAdThread extends Thread {

	private Context mContext;
	private AdContent ad;
	
	private int type;//1,2,3

	public GetAdThread(Context context,int type) {
		this.mContext = context;
		this.type = type;
	}


	public Handler handler = new Handler(Looper.getMainLooper()) {
		public void handleMessage(android.os.Message msg) {
			getAdresult();
		}
	};


	private static Object obj = new Object();

	@Override
	public void run() {
		super.run();
		synchronized (obj) {
			try {
				AdOperate adOperate = new AdOperate(mContext);
				ad = adOperate.getAdTask(AdRequestInfo.adRequestInfo);
			} catch (Exception e) {
				e.printStackTrace();
				AdConfig.init().setMsg(e.getMessage());
			}finally{
				Message msg = handler.obtainMessage();
				handler.sendMessage(msg);
			}
		}
	}

	public void getAdresult() {
		if (null != ad) {
			if(type == 1){
				AdPool.addIntersititialad(ad);
				ListenerUtil.onADReceive();
			}else if(type == 2){
				AdPool.addSplashAd(ad);
				ListenerUtil.onADExitReceive(new SplashAdView(mContext));
			}else if(type == 3){
				AdPool.addSplashAd(ad);
				ListenerUtil.onADExitReceive(null);
			}
		} else {
			ListenerUtil.onNoAD(AdConfig.init().getMsg());
		}
	}
}
