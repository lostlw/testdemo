package com.xhxm.ospot;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.xhxm.ospot.oldstyle.GetOldAdThread;
import com.xhxm.ospot.task.GetAdListThread;
import com.xhxm.ospot.task.GetAdThread;
import com.xhxm.ospot.task.InitAdThread;
import com.xhxm.ospot.task.ThreadPool;
import com.xhxm.ospot.util.AdUtils;

public class DeeplinkAd {

	private static DeeplinkAd manager = new DeeplinkAd();
	

	public static DeeplinkAd getInstance() {
		return manager;
	}
	
	
	public void loadAdView(Context context,int type){
		initExit(context,type);
	}
	
	public void loadSpotAd(Context context){
		initInterstitial(context);
	}
	
	public void setAdListener(AdStatusListener meventListener){
		ListenerUtil.setEventListener(meventListener);
	}
	
	
	
	public void setID(Context context,String appid, String slotId) {
		AdConfig.init().setAppId(appid);
		AdConfig.init().setSlotId(slotId);
		ThreadPool.getThreadPool().execute(new InitAdThread(context));
		
	}

	/**
	 * 插屏广告
	 * @param paramContext
	 */
	private void initInterstitial(Context paramContext) {
		//存在未展示的广告,直接回调,
		if(AdPool.ishavaintersititialadsads()){
			ListenerUtil.onADReceive();
			return;
		}
		//请求广告
		if (Util.checkNetWork(paramContext)) {
			AdUtils.initParams(paramContext);
			ThreadPool.getThreadPool().execute(new GetAdThread(paramContext,AdConfig.ADTYPE_SPOT));
		}
	}
	
	
	public void initListAd(final Context paramContext) {
		if (Util.checkNetWork(paramContext)) {
			AdUtils.initParams(paramContext);
			ThreadPool.getThreadPool().execute(new GetAdListThread(paramContext));
		}
	}
	
	

	
	
	/**
	 * 加载退弹广告到广告池
	 * @param paramContext
	 */
	private void initExit(final Context paramContext,final int type) {
		if(AdPool.ishavaexitads()){
			if (type == AdConfig.ADTYPE_SPLASH) {
				ListenerUtil.onADExitReceive(new SplashAdView(paramContext));
			} else if(type == AdConfig.ADTYPE_EXIT){
				ListenerUtil.onADExitReceive(null);
			}
			return;
		}
		if (Util.checkNetWork(paramContext)) {
			AdUtils.initParams(paramContext);
			ThreadPool.getThreadPool().execute(new GetAdThread(paramContext,type));
		}
	}
	
	
	
	public void showExit(Activity mContext) {
		AdUtils.showExitAd(mContext,null);
	}
	public void showExit(Activity mContext,View.OnClickListener v) {
		AdUtils.showExitAd(mContext,v);
	}
	public void show(Context mContext) {
		AdUtils.showAd(mContext);
	}

	public void showSpot(Context mContext) {
		AdUtils.initParams(mContext);
		ThreadPool.getThreadPool().execute(new GetOldAdThread(mContext));
	}


}
