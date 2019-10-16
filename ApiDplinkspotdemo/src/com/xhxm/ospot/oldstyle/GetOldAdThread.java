package com.xhxm.ospot.oldstyle;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.xhxm.ospot.AdConfig;
import com.xhxm.ospot.model.AdContent;
import com.xhxm.ospot.model.AdRequestInfo;
import com.xhxm.ospot.operate.AdOperate;
import com.xhxm.ospot.util.AdUtils;

public class GetOldAdThread extends Thread {

	private Context mContext;
	private AdContent adContent;
	

	public GetOldAdThread(Context context) {
		this.mContext = context;
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
				adContent = adOperate.getAdTask(AdRequestInfo.adRequestInfo);
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
		if (null != adContent) {
			Activity ac = AdUtils.getActivity();
			if (ac != null && !ac.isFinishing()) {
				AdDialog ad = new AdDialog(ac, 0);
				ad.setActivity(ac);
				ad.setads(adContent);
				ad.init();
			}
		}  
	}
}
