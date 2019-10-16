package com.xhxm.ospot.task;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.xhxm.ospot.AdConfig;
import com.xhxm.ospot.AdPool;
import com.xhxm.ospot.ListActivity;
import com.xhxm.ospot.ListenerUtil;
import com.xhxm.ospot.model.AdContent;
import com.xhxm.ospot.model.AdRequestInfo;
import com.xhxm.ospot.operate.AdOperate;

public class GetAdListThread extends Thread {

	private Context mContext;

	public GetAdListThread(Context context) {
		this.mContext = context;

	}
	
	private List<AdContent> ad;

	// public void setRequestNextAd(boolean isRequestNextAd) {
	// this.misRequestNextAd = isRequestNextAd;
	// }

	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			openActivity() ;
		};
	};


	private static Object obj = new Object();

	@Override
	public void run() {
		super.run();
		synchronized (obj) {
			try {
				AdOperate adOperate = new AdOperate(mContext);
				ad = adOperate.getAdsTask(AdRequestInfo.adRequestInfo);
				if (null != ad) {
					
				}else{
//					ListenerUtil.onNoAD("no app");
				}
			} catch (Exception e) {
				e.printStackTrace();
//				ListenerUtil.onNoAD("web error");
				AdConfig.init().setMsg("网络异常" + e.getMessage());
			}finally{
				Message msg = handler.obtainMessage();
				msg.obj = ad;
				handler.sendMessage(msg);
			}
		}
	}

	public void openActivity() {
		if (null != ad && !ad.isEmpty()) {
			
			AdPool.setIntersititialad(ad);
//			ListenerUtil.onADReceive();
			
				Intent i = new Intent(mContext, ListActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(i);

		} else {
			// 回调
			ListenerUtil.onNoAD(AdConfig.init().getMsg());
		}
	}
}
