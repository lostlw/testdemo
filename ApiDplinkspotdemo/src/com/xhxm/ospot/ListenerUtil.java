package com.xhxm.ospot;




public class ListenerUtil {
	
	
	
	private static AdStatusListener eventListener;
	
	public static void setEventListener(AdStatusListener meventListener) {
		eventListener = meventListener;
	}
	
	
	public static void onADClicked() {
		if(eventListener!=null){
			eventListener.onADClicked();
		}
	}
	
	public static void onADReceive() {
		if(eventListener!=null){
			eventListener.onADReceive();
		}
	}
	public static void onADExitReceive(SplashAdView exitView) {
		if(eventListener!=null){
			eventListener.onADReceive(exitView);
		}
	}
	public static void onNoAD(String reason) {
		if(eventListener!=null){
			eventListener.onNoAD(reason);
		}
	}
	public static void onADClosed() {
		if(eventListener!=null){
			eventListener.onADClosed();
		}
	}
	
	
}
