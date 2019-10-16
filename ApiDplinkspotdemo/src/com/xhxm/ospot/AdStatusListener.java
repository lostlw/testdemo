package com.xhxm.ospot;
/**
 * 事件回调
 * @author abc
 */
public interface AdStatusListener {
	  public abstract void onADClicked();
	  public abstract void onADReceive();
	  public abstract void onADReceive(SplashAdView exitView);
	  public abstract void onADClosed();
	  public abstract void onNoAD(String errorinfo);
}
