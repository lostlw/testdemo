package com.xhxm.ospot.util;

import java.lang.reflect.Field;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.xhxm.ospot.AdPool;
import com.xhxm.ospot.SpotActivity;
import com.xhxm.ospot.dialog.ExitDialog;
import com.xhxm.ospot.model.AdRequestInfo;

public class AdUtils {
	
	
	public static Activity getActivity() {
		Class<?> activityThreadClass = null;
		try {
			activityThreadClass = Class.forName("android.app.ActivityThread");
			Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
			Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
			activitiesField.setAccessible(true);
			Map activities = (Map) activitiesField.get(activityThread);
			
			for (Object activityRecord : activities.values()) {
				Class<?> activityRecordClass = activityRecord.getClass();
				Field pausedField = activityRecordClass.getDeclaredField("paused");
				pausedField.setAccessible(true);
				if (!pausedField.getBoolean(activityRecord)) {
					Field activityField = activityRecordClass.getDeclaredField("activity");
					activityField.setAccessible(true);
					Activity activity = (Activity) activityField.get(activityRecord);
					return activity;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static void dealFinishActivity(Activity activity) {
		activity.finish();
	}



	public static void initParams(final Context context) {
		if (null == AdRequestInfo.adRequestInfo) {
			try {
				new AdRequestInfo().getInstance(context);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
	}


	public static void showAd(Context mContext) {
		if (AdPool.ishavaintersititialadsads()) {
			try {
				Intent i = new Intent(mContext, SpotActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void showExitAd(Activity mContext, View.OnClickListener v) {
		if (AdPool.ishavaexitads()) {
			if (mContext instanceof Activity && !((Activity) mContext).isFinishing()) {
				ExitDialog mExitDialog = new ExitDialog(mContext, 0);
				mExitDialog.setActivity(mContext);
				mExitDialog.setExitOnClickListener(v);
				mExitDialog.init();
			}
		}
	}


}
