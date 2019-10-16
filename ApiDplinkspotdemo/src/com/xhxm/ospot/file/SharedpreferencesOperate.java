package com.xhxm.ospot.file;

import android.content.Context;

import com.xhxm.ospot.Util;

public class SharedpreferencesOperate {

	// init接口请求时间
	public static String PRETIME = "ze";

	public static String CHKAPPS = "zg";

	public static long getInitime(Context context) {
		return Myshared.getLong(context, PRETIME, 0);
	}

	public static void setInitime(Context context, long pretime) {
		Myshared.saveData(context, PRETIME, pretime);
	}

	public static void removeData(Context context, String key) {
		Myshared.removeData(context, key);
	}

	public static void setChkapps(Context context, String pks) {
		if (!Util.isNull(pks)) {
			Myshared.saveData(context, CHKAPPS, pks.trim());
		}
	}

	public static String getChkapps(Context context) {
		String appid = Myshared.getString(context, CHKAPPS, "");
		return appid;
	}
}
