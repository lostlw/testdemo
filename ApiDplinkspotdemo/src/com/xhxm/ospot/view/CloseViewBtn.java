package com.xhxm.ospot.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xhxm.ospot.Util;
import com.xhxm.ospot.util.AdUtils;
import com.xhxm.ospot.util.Imgstrs;
import com.xhxm.ospot.util.UIUtils;

public class CloseViewBtn extends ImageView {

	private int width ;
	private Context context;

	public CloseViewBtn(Context context, int id) {
		super(context);
		this.context = context;
		width = UIUtils.getmScreenWidth(context);
		initview(context, id);
	}

	public CloseViewBtn(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private void initview(Context context, int id) {
		setId(id);
		Util.setBackgroud(this, Util.readDrawable(context,Imgstrs.close));
	}
	
	/**
	 * 
	 * @param RVIEWID 相对父布局
	 * @param size 切图所占空间
	 * @param screenSize 切图的宽度
	 * @param margin 
	 * @return
	 */

	public RelativeLayout.LayoutParams addParams(int RVIEWID,int size,int screenWidth,int margin) {
		int num = width * screenWidth / UIUtils.SCREEN_WIDTH ;
		num = num * size / screenWidth ;
		RelativeLayout.LayoutParams tivParams = new RelativeLayout.LayoutParams(
				num, num);
		tivParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RVIEWID);
		tivParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RVIEWID);
		tivParams.topMargin = margin;
		tivParams.rightMargin = margin;
		return tivParams;
	}
	
	public RelativeLayout.LayoutParams addParams(int RVIEWID){
		int num = AdUtils.dip2px(context, (int) (35 / 1.5f));

//		if(AdUtils.showClostBtnTime(context)==1){
//		}
		int margin = AdUtils.dip2px(context, 2);
		RelativeLayout.LayoutParams tivParams = new RelativeLayout.LayoutParams(
				num, num);
		tivParams.addRule(RelativeLayout.ALIGN_RIGHT, RVIEWID);
		tivParams.addRule(RelativeLayout.ALIGN_TOP, RVIEWID);
		tivParams.topMargin = margin;
		tivParams.rightMargin = margin;
		return tivParams;
	}

}
