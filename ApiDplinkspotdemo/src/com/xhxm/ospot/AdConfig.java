package com.xhxm.ospot;

import android.text.TextUtils;


public class AdConfig  {

	
	
	public static String BASEURL = "http://api.awake.durianclicks.com/api/v1/req.jsp";
	public static String PKGURL = "http://api.awake.durianclicks.com/api/v1/pkg.jsp?chid=70006";
	public static String LISTURL = "http://api.awake.durianclicks.com/api/v1/list.jsp";

	
	/**
	 * 1,插屏, 2,开屏  3,退弹
	 */
	public final static int ADTYPE_SPOT = 1;
	public final static int ADTYPE_SPLASH = 2;
	public final static int ADTYPE_EXIT = 3;
	
	String msg = "";

	static AdConfig c = null;

	public static AdConfig init() {
		if (c == null) {
			c = new AdConfig();
		}
		return c;
	}

	
	private String slotId = "";

	private String appId = "";
	public String getSlotId() {
		return slotId;
	}

	public void setSlotId(String slotId) {
		if(!TextUtils.isEmpty(slotId)){
			this.slotId = slotId;
		}
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
