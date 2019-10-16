package com.xhxm.ospot.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xhxm.ospot.Util;
import com.xhxm.ospot.constant.AdColorConst;
import com.xhxm.ospot.constant.AdStringZhConst;
import com.xhxm.ospot.constant.DimensConst;
import com.xhxm.ospot.util.Imgstrs;
import com.xhxm.ospot.util.UIUtils;

public class AdSpotView extends RelativeLayout {

	public static final int BGVIEWID = 0x8765008;
	public static final int REVIEWID = 0x8765009;
	public static final int EVALUID = 0x8765010;
	public static final int SPOTVIEW_ID = 0x8765011;
	public static final int POSTERIMGID = 0x8765000;
	public static final int CLOSEIMGID = 0x8765001;
	public static final int AD_ICON = 0x8765002;
	public static final int AD_NAME = 0x8765003;
	public static final int AD_INVALUATE = 0x8765004;
	public static final int AD_OPENAd = 0x8765005;
	public static final int AD_BRIEF = 0x8765006;

	
	private Context context;


	private CloseViewBtn closeViewBtn;
	private int mWidth;


	
	public AdSpotView(Context context) {
		this(context, null);
		this.context = context;
	}

	public AdSpotView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	

	public void initView() {
		mWidth = UIUtils.getmScreenWidth(context);
		setGravity(Gravity.CENTER);
		RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		setLayoutParams(rl);
		setId(SPOTVIEW_ID);
		setBackgroundColor(AdColorConst.bg);

		LinearLayout bgview = new LinearLayout(context);
		int reviewWidth = mWidth * 564 / UIUtils.SCREEN_WIDTH;
		LinearLayout.LayoutParams bgviewParams = new LinearLayout.LayoutParams(
				reviewWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
		bgview.setOrientation(LinearLayout.VERTICAL);
		bgview.setGravity(Gravity.CENTER);
		int radiu = UIUtils.dip2px(context, 4);
		Util.setBackgroud(bgview, UIUtils
				.getRoundRectDrawable((int) (radiu * UIUtils
						.getDensity(context))));
		this.addView(bgview, bgviewParams);

		RelativeLayout review = new RelativeLayout(context);
		review.setId(REVIEWID);
		int topImgWidth = reviewWidth;
		int topImgHeight = (topImgWidth * 526 / 564)
				- UIUtils.dip2px(context, 1);
		RelativeLayout.LayoutParams reviewParams = new RelativeLayout.LayoutParams(
				topImgWidth, topImgHeight);
		bgview.addView(review, reviewParams);

		RoundedImageView adPosterImg = new RoundedImageView(context);
		adPosterImg.setId(POSTERIMGID);
		adPosterImg.setCornerRadiiDP(radiu, radiu, 0, 0);
		Util.setBackgroud(adPosterImg, new ColorDrawable(Color.TRANSPARENT));
		LinearLayout.LayoutParams topParam = new LinearLayout.LayoutParams(
				topImgWidth, topImgHeight);
		adPosterImg.setLayoutParams(topParam);
		adPosterImg.setScaleType(ScaleType.FIT_XY);
		review.addView(adPosterImg);

		closeViewBtn = new CloseViewBtn(context, CLOSEIMGID);
		review.addView(
				closeViewBtn,
				closeViewBtn.addParams(REVIEWID, 70, 564,
						UIUtils.dip2px(context, 5)));


		LinearLayout centerView = new LinearLayout(context);
		RelativeLayout.LayoutParams cenetrParam = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		cenetrParam.addRule(RelativeLayout.BELOW, REVIEWID);
		centerView.setOrientation(LinearLayout.HORIZONTAL);
		centerView.setBackgroundColor(Color.parseColor("#FFFFFF"));
		bgview.addView(centerView, cenetrParam);


		LinearLayout mixtureView = new LinearLayout(context);
		mixtureView.setBackgroundColor(Color.TRANSPARENT);
		mixtureView.setOrientation(LinearLayout.VERTICAL);
		mixtureView.setGravity(Gravity.CENTER_VERTICAL);
		LinearLayout.LayoutParams mixtureViewParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mixtureViewParams.topMargin = UIUtils.dip2px(context, 13);
		mixtureView.setLayoutParams(mixtureViewParams);
		centerView.addView(mixtureView);




		TextView briefText = new TextView(context);
		briefText.setId(AD_BRIEF);
		briefText.setTextColor(AdColorConst.gray);
		briefText.setTextSize(DimensConst.GAME_NAME_TEXT_SIZE);
		briefText.setMaxLines(3);
		briefText.setEllipsize(TruncateAt.END);
		Util.setBackgroud(briefText, new ColorDrawable(Color.WHITE));
		LinearLayout.LayoutParams briefParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		int marginH = UIUtils.dip2px(context, 17);
		int marginV = UIUtils.dip2px(context, 16);
		briefParams.setMargins(marginH, marginV, marginH, marginV);
		briefParams.gravity = Gravity.CENTER;
		bgview.addView(briefText, briefParams);

		Button install = new Button(context);
		install.setId(AD_OPENAd);
		install.setText(UIUtils.getTextContext(AdStringZhConst.OPEN_KEY));
		install.setGravity(Gravity.CENTER);
		install.setTextSize(DimensConst.DOWN_BUTTION_TEXT_SIZE);
		install.setTextColor(Color.WHITE);
		int downllWidth = 500 * reviewWidth / 564;
		int downllHeight = 102 * reviewWidth / 500;
		LinearLayout.LayoutParams installParams = new LinearLayout.LayoutParams(
				downllWidth, downllHeight);
		installParams.bottomMargin = UIUtils.dip2px(context, 14);
		install.setLayoutParams(installParams);
		Util.setBackgroud(install, Util.readDrawable(context,Imgstrs.install_button));
		bgview.addView(install);
	}
}
