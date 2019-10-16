package com.xhxm.ospot.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xhxm.ospot.Util;
import com.xhxm.ospot.constant.DimensConst;
import com.xhxm.ospot.util.Imgstrs;
import com.xhxm.ospot.util.UIUtils;

 public class Sitem extends LinearLayout {

	
	public static final int TNAMEID = 0x32345671;
	public static final int LOGOID = 0x32345672;
	public static final int ItemID = 0x32345673;
	public static final int TPRICE = 0x32345674;
	public static final int OPENBTNID = 0x32345675;
	
	public Sitem(Context context) {
		super(context);
		createItem(context);
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void createItem(Context context) {

		
		int pad5 = Util.dip2px(context, 5);
		
		this.setId(ItemID);
		this.setPadding(pad5, pad5, pad5, pad5);
		this.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.setGravity(Gravity.CENTER_VERTICAL);
		this.setBackground(getRoundRectDrawable(40, Color.WHITE, true, 10));
		
		
		LinearLayout.LayoutParams viewlp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		viewlp.setMargins(pad5, pad5, pad5, pad5);
		setLayoutParams(viewlp);
		
		LinearLayout.LayoutParams imgrlp = new LinearLayout.LayoutParams(
				Util.dip2px(context, 75), Util.dip2px(context, 75));
		imgrlp.setMargins(5, 5, 5, 5);
		imgrlp.gravity =  Gravity.CENTER_VERTICAL;
		final ImageView iconImg = new ImageView(context);
		iconImg.setId(LOGOID);
		iconImg.setScaleType(ScaleType.FIT_XY);
		iconImg.setFocusable(false);
		addView(iconImg, imgrlp);

		
		LinearLayout rightview = new LinearLayout(context);
		rightview.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams rightviewlp = new LinearLayout.LayoutParams(
				Util.dip2px(context, 150),LayoutParams.WRAP_CONTENT);
		rightviewlp.leftMargin = Util.dip2px(context, 10);
		rightview.setLayoutParams(rightviewlp);
		
		TextView tname = new MarqueeTextView(context);
		tname.setId(TNAMEID);
		tname.setTextSize(15);
		tname.setSingleLine(true);
		tname.setTextColor(Color.BLACK);
		
		
		TextView mprice = new MarqueeTextView(context);
		mprice.setId(TPRICE);
		mprice.setTextSize(11);
		mprice.setSingleLine(true);
		mprice.setTextColor(Color.BLACK);
		
	    LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(-2, -2);
	    localLayoutParams2.setMargins(0, 2, 0, 2);
	    localLayoutParams2.gravity = Gravity.LEFT;
	    localLayoutParams2.topMargin = 10;
		tname.setLayoutParams(localLayoutParams2);
		mprice.setLayoutParams(localLayoutParams2);
		
		
		rightview.addView(tname);
		rightview.addView(mprice);
		
		addView(rightview);
		
		
		
		
		Button openbtn = new Button(context);
		openbtn.setId(OPENBTNID);
		openbtn.setText("打开领钱");
		openbtn.setGravity(Gravity.CENTER);
		openbtn.setTextSize(DimensConst.DOWN_NUM_TEXT_SIZE);
		openbtn.setTextColor(Color.WHITE);
		int downllWidth = Util.dip2px(context, (int) (150 / 1.5f));
		int downllHeight = Util.dip2px(context, (int) (60 / 1.5f));
		LinearLayout.LayoutParams installParams = new LinearLayout.LayoutParams(
				downllWidth, downllHeight);
		installParams.leftMargin = UIUtils.dip2px(context,50);
		installParams.rightMargin = UIUtils.dip2px(context,10);
		installParams.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
		Util.setBackgroud(openbtn, Util.readDrawable(context,Imgstrs.install_button));
		addView(openbtn,installParams);
		
		
		setFocusable(false);

	}
	
	public static GradientDrawable getRoundRectDrawable(int radius, int color, boolean isFill, int strokeWidth){
        float[] radiuz = {radius, radius, radius, radius, radius, radius, radius, radius};
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadii(radiuz);
        drawable.setColor(isFill ? color : Color.TRANSPARENT);
        drawable.setStroke(isFill ? 0 : strokeWidth, color);
        return drawable;
}

}
