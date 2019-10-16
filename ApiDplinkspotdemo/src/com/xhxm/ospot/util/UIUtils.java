package com.xhxm.ospot.util;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.xhxm.ospot.constant.AdStringZhConst;

public class UIUtils {

	public static final int SCREEN_WIDTH = 750;

	public static final int SCREEN_HEIGHT = 1334;
	
	/**
	 * 获取屏幕density属性的等级 包括:low, mid, high, x
	 */
	public static float getDensity(Context context) {
		WindowManager mWindowManager = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE));
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
		return mDisplayMetrics.density;
	}

	/**
	 * 
	 * 获取屏幕宽度
	 */
	public static int getmScreenWidth(Context context) {

		return getMesureSize(context, true);
	}

	/**
	 * 
	 * 获取屏幕高度
	 */
	public static int getmScreenHeight(Context context) {
		return getMesureSize(context, false);
	}

	private static int getMesureSize(Context context, boolean isWidth) {
		WindowManager mWindowManager = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE));
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
		float density = mDisplayMetrics.density;
		int width = mDisplayMetrics.widthPixels;
		int height = mDisplayMetrics.heightPixels;
		if (mDisplayMetrics.widthPixels <= 320) {
			width = (int) Math.ceil(width * density);
			height = (int) Math.ceil(height * density);
		}
		return isWidth ? width : height;
	}

	/**
	 * 
	* <p>Title: getScreenOrientation</p>
	* <p>Description:获取屏幕方向 </p>
	* @return
	 */
	public static int getScreenOrientation(Context context){
		 int screenOrientation = 2;
		 if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {    
			 screenOrientation = 1;   //横屏
		    } else if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {    
		    	screenOrientation = 2;     //竖屏
		    }   
		 return screenOrientation;     //竖屏
	}
	
	public static int dip2px(Context context, float dipValue) {
		return (int) (dipValue * getDensity(context) + 0.5f);
	}

	public int px2dip(Context context, float pxValue) {
		return (int) (pxValue / getDensity(context) + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = getDensity(context);
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = getDensity(context);
		return (int) (pxValue / fontScale + 0.5f);
	}

	// 生成一个圆角的背景
	public static Drawable getRoundRectDrawable(int radiuDp) {
		GradientDrawable draw = null;
		int roundRadius = radiuDp;
		int solidColor = Color.parseColor("#FFFFFF");
		draw = new GradientDrawable();
		draw.setColor(solidColor);
		draw.setCornerRadius(roundRadius);
		return draw;
	}

	public static Drawable getRoundRectDrawable(int radiuDp, boolean isSelect) {
		GradientDrawable draw = null;
		int roundRadius = radiuDp;
		int solidColor = Color.parseColor("#FFFFFF");
		draw = new GradientDrawable();
		draw.setColor(solidColor);
		int strokeWidth = 2;
		int strokeColor = Color.parseColor("#e8e8e8");
		if (isSelect) {
			strokeColor = Color.parseColor("#fd8a9d");
		}
		draw.setStroke(strokeWidth, strokeColor);
		draw.setCornerRadius(roundRadius);
		return draw;
	}


	public static String getTextContext(String key) {
		String textContext = "";
		AdStringZhConst adStringZhConst = new AdStringZhConst();
		textContext = adStringZhConst.getMap().get(key);
		return textContext;
	}
}