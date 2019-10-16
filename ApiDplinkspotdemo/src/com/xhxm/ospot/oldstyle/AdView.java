package com.xhxm.ospot.oldstyle;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class AdView extends RelativeLayout {

	private int width;
	private int height;
	public static final int VIEWFLIPPERID = 0x8765400;
	public static final int CLOSEIMGID = 0x8765401;
	public static final int PCVID = 0x8765402;
	public static final int POPIMGID = 0x8765403;
	public static final int TSIZEID = 0x8765404;
	public static final int DBTNID = 0x8765405;
	public static final int BGVIEWID = 0x8765406;
	public static final int RVIEWID = 0x8765407;


	public AdView(Context context) {
		super(context);
		setDispaly(context);
		initview(context);
	}

	private void initview(Context context) {

		int temp = width < height ? width : height;
		int mwidth = (int) (temp * 0.75);
		setGravity(Gravity.CENTER);

		LinearLayout bgview = new LinearLayout(context);
		bgview.setId(BGVIEWID);
		bgview.setBackgroundColor(Color.TRANSPARENT);
		RelativeLayout.LayoutParams bgviewParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.addView(bgview, bgviewParams);

		RelativeLayout.LayoutParams viewParams = new RelativeLayout.LayoutParams(
				mwidth, mwidth);
		viewParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		RelativeLayout rview = new RelativeLayout(context);
		rview.setId(RVIEWID);
		rview.setBackgroundColor(Color.TRANSPARENT);
		// rview.setBackgroundDrawable(AdUtils.readDrawable(context,
		// "default_bg"));

		this.addView(rview, viewParams);

		RelativeLayout.LayoutParams vfParams = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

		ImageView img = new ImageView(context);
		img.setId(AdView.POPIMGID);
		img.setScaleType(ScaleType.FIT_XY);
		rview.addView(img, vfParams);

		// QuickShortcut tiv = new QuickShortcut(context);
		// tiv.setId(CLOSEIMGID);
		// tiv.setBackgroundDrawable(AdUtils.readDrawable(Imgstrs.z));

		// int flag = AdConfig.init().getZoclbtn(context);
		//
		// int num = AdUtils.dip2px(context, (int) (31 / 1.5f));
		// int margin = AdUtils.dip2px(context, 2);
		// if(flag==1){
		// num = AdUtils.dip2px(context, (int) (19 / 1.5f));
		// margin = AdUtils.dip2px(context, 6);
		// tiv.setBackgroundDrawable(AdUtils.readDrawable(Imgstrs.u));
		// }
		//
		// RelativeLayout.LayoutParams tivParams = new
		// RelativeLayout.LayoutParams(num, num);
		// tivParams.addRule(RelativeLayout.ALIGN_RIGHT, RVIEWID);
		// tivParams.addRule(RelativeLayout.ALIGN_TOP, RVIEWID);
		// tivParams.topMargin = margin;
		// tivParams.rightMargin = margin;
		// addView(tiv, tivParams);

	}

	private void setDispaly(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		float density = dm.density;
		width = dm.widthPixels;
		height = dm.heightPixels;
		if (width <= 320) {
			width = (int) Math.ceil(width * density);
			height = (int) Math.ceil(height * density);
		}
	}

}
