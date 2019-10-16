package com.xhxm.ospot.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xhxm.ospot.Util;
import com.xhxm.ospot.constant.AdColorConst;
import com.xhxm.ospot.constant.AdIdConst;
import com.xhxm.ospot.constant.AdStringZhConst;
import com.xhxm.ospot.constant.DimensConst;
import com.xhxm.ospot.util.Imgstrs;
import com.xhxm.ospot.util.UIUtils;
/**
 * 退出界面
 * @author dell
 */
public class AdExitView extends android.widget.RelativeLayout {
	private int mWidth;
	private int mHeight;
	// 广告图片
	private RoundedImageView madIv;



	private Button cancelTv;
	private Button confirmTv;
	
	


	//整体布局
	private RelativeLayout mbgRl;
	
	public AdExitView(Context context) {
		super(context);
		mWidth = UIUtils.getmScreenWidth(context);
		mHeight = UIUtils.getmScreenHeight(context);
		initview(context);
	}

	public AdExitView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	// 正常展示插屏广告界面
	private void initview(Context context) {
		
		mbgRl = new RelativeLayout(context);
		mbgRl.setId(AdIdConst.PORTRAIT_DETAIL_LL);
		RelativeLayout.LayoutParams bgviewParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mbgRl.setLayoutParams(bgviewParams);
		mbgRl.setBackgroundColor(AdColorConst.bg);
		this.addView(mbgRl);

		// dialog界面包含下载按钮
		RelativeLayout dialogRl = new RelativeLayout(context);
		mbgRl.addView(dialogRl);
		
		int dialogWidth = 588 * mWidth / UIUtils.SCREEN_WIDTH;
		int dialogHeight = 836 * dialogWidth / 588;


		
		
		
		RelativeLayout.LayoutParams dialogRlParams = new RelativeLayout.LayoutParams(
				dialogWidth, dialogHeight);
		dialogRlParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		dialogRl.setLayoutParams(dialogRlParams);

		// 广告界面
		RelativeLayout adRl = new RelativeLayout(context);
		dialogRl.addView(adRl);
		adRl.setId(AdIdConst.ADVIEWID);
		int adRlHeight = 779 * dialogWidth / 588;
		RelativeLayout.LayoutParams adRlParams = new RelativeLayout.LayoutParams(
				dialogWidth, adRlHeight);
		adRl.setLayoutParams(adRlParams);

		// 广告图片布局
		madIv = new RoundedImageView(context);
		madIv.setId(AdIdConst.AD_PIG_IMAGE_TV);
		int adIvHeigth = 577 * dialogWidth / 588;
		RelativeLayout.LayoutParams adIvParams = new RelativeLayout.LayoutParams(
				dialogWidth, adIvHeigth);
		madIv.setLayoutParams(adIvParams); 
		madIv.setScaleType(ScaleType.FIT_XY);
		int corner = UIUtils.dip2px(context, 4);
		madIv.setCornerRadiiDP(corner, corner, 0, 0);
		adRl.addView(madIv);


		
		// 广告详情布局
		RelativeLayout adDetailRl = new RelativeLayout(context);
		adRl.addView(adDetailRl);
		int adDetailRlHeigth = 205 * dialogWidth / 588;
		RelativeLayout.LayoutParams adDetailRlParams = new RelativeLayout.LayoutParams(
				dialogWidth, adDetailRlHeigth);
		adDetailRlParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adDetailRl.setLayoutParams(adDetailRlParams);

		RoundedImageView adDetailBgIv = new RoundedImageView(context);
		RelativeLayout.LayoutParams adDetailBgIvParams = new RelativeLayout.LayoutParams(
				dialogWidth, adDetailRlHeigth);
		adDetailRl.addView(adDetailBgIv);
		adDetailBgIv.setLayoutParams(adDetailBgIvParams);
		adDetailBgIv.setScaleType(ScaleType.FIT_XY);
		adDetailBgIv.setCornerRadiiDP(0, 0, corner, corner);
		adDetailBgIv.setImageDrawable(new ColorDrawable(Color.WHITE));

		
		int m80 = UIUtils.dip2px(context, 20);
		int m15 = UIUtils.dip2px(context, 10);
		int m110 = UIUtils.dip2px(context, 95);
		
		int m66 = UIUtils.dip2px(context, 32);
		
		// 底部布局
		LinearLayout bottomLl = new LinearLayout(context);
		bottomLl.setOrientation(LinearLayout.VERTICAL);
		RelativeLayout.LayoutParams bottomLlParam = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		bottomLlParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
		bottomLlParam.topMargin = m80;
		bottomLl.setLayoutParams(bottomLlParam);
		
		
		// 退出提示
		TextView exitTv = new TextView(context);
		exitTv.setId(AdIdConst.EXITITLEID);
		exitTv.setTextSize(DimensConst.DOWN_BUTTION_TEXT_SIZE);
		exitTv.setTextColor(Color.BLACK);
		exitTv.setText("是否退出" + Util.getAppName(context) + "?");

		
		LinearLayout.LayoutParams exitTvParam = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		exitTvParam.gravity = Gravity.CENTER;
//		exitTvParam.bottomMargin = m15;
		exitTv.setLayoutParams(exitTvParam);
		
		bottomLl.addView(exitTv);
		
		
		adDetailRl.addView(bottomLl);

		

		// 下载视图
		RelativeLayout mdownll = new RelativeLayout(context);
		int downllWidth = 480 * dialogWidth / 588;
		int downllHeight = 117 * downllWidth / 480;
		
		LinearLayout.LayoutParams downllParam = new LinearLayout.LayoutParams(
				downllWidth, downllHeight);
		
//		downllParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		downllParam.gravity = Gravity.CENTER_HORIZONTAL;
		downllParam.topMargin = m15;
		downllParam.bottomMargin = m15;
		mdownll.setLayoutParams(downllParam);
		
//		mdownll.setBackgroundDrawable(Util.readDrawable(Imgstrs.install_button));
		
		bottomLl.addView(mdownll);
//		dialogRl.addView(mdownll);

		Drawable blue = Util.readDrawable(context,Imgstrs.blue);
		Drawable gray = Util.readDrawable(context,Imgstrs.gray);
		
		// 确认按钮
		confirmTv = new Button(context);
		confirmTv.setId(AdIdConst.CONFIRMTVID);
		confirmTv.setTextSize(DimensConst.DOWN_BUTTION_TEXT_SIZE);
		confirmTv.setTextColor(Color.parseColor("#0d9cff"));
		confirmTv.setText(UIUtils.getTextContext(AdStringZhConst.CONFIRM));
		confirmTv.setPadding(1, 1, 1, 1);
		
		Util.setBackgroud(confirmTv, blue);
		
		RelativeLayout.LayoutParams numberParam = new RelativeLayout.LayoutParams(
				m110, m66);
		numberParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		numberParam.leftMargin = m15;
		
		confirmTv.setLayoutParams(numberParam);
		
		mdownll.addView(confirmTv);
		
		// 取消按钮
		cancelTv = new Button(context);
		cancelTv.setId(AdIdConst.CANCELTVID);
		cancelTv.setTextSize(DimensConst.DOWN_BUTTION_TEXT_SIZE);
		cancelTv.setTextColor(Color.parseColor("#bfbfbf"));
		cancelTv.setText(UIUtils.getTextContext(AdStringZhConst.CANCEL));
		cancelTv.setPadding(1, 1, 1, 1);
		
		
		Util.setBackgroud(cancelTv, gray);
		
		RelativeLayout.LayoutParams cancelTvParam = new RelativeLayout.LayoutParams(
				m110, m66);
		
		cancelTvParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		cancelTvParam.rightMargin = m15;
		
		cancelTv.setLayoutParams(cancelTvParam);
		mdownll.addView(cancelTv);
		
		
	}
	
	
	
	
    
	public Button getCancelTv() {
		return cancelTv;
	}


	public Button getConfirmTv() {
		return confirmTv;
	}


	/** 广告背景大图片 */
	public RoundedImageView getMadIv() {
		return madIv;
	}


	
	/**整体布局*/
	public RelativeLayout getMbgRl() {
		return mbgRl;
	}
	
   
}
