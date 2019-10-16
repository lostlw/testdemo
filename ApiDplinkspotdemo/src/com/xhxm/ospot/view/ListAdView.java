package com.xhxm.ospot.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.test01.R;
import com.xhxm.ospot.Util;

public class ListAdView extends LinearLayout {

	public static final int GRIDVIEWID = 0x52345670;

	public static final int TOPCLOSEID = 0x52345671;
	public static final int TITLEID = 0x52345672;
	public static final int BGVIEWID = 0x52345673;

	public ListAdView(Context context) {
		super(context);
		createItem(context);
	}

	private void createItem(Context context) {

		int padingdip = Util.dip2px(context, (int) (10 / 1.5f));
		
		setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout topview = new LinearLayout(context);
		LinearLayout.LayoutParams topviewlp = new LinearLayout.LayoutParams(
		LayoutParams.MATCH_PARENT, Util.dip2px(context, 100));
		
		topview.setBackgroundResource(R.drawable.banner);
		this.addView(topview, topviewlp);
		
		LinearLayout bgview = new LinearLayout(context);
		bgview.setBackgroundColor(Color.TRANSPARENT);
		LinearLayout.LayoutParams bgviewParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.addView(bgview, bgviewParams);

		LinearLayout bgview2 = new LinearLayout(context);
		bgview2.setPadding(padingdip, padingdip, padingdip, padingdip);
		bgview2.setOrientation(LinearLayout.VERTICAL);
		bgview2.setId(BGVIEWID);
		bgview2.setGravity(Gravity.CENTER_HORIZONTAL);
		RelativeLayout.LayoutParams viewlp2 = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		viewlp2.addRule(RelativeLayout.CENTER_IN_PARENT);
		bgview2.setBackgroundColor(Color.parseColor("#eaeaea"));

		bgview.addView(bgview2, viewlp2);
		



		ListView listview = new ListView(context);
		listview.setPadding(0, padingdip, 0, 0);
		listview.setId(GRIDVIEWID);
		listview.setCacheColorHint(0);
		listview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		listview.setDivider(new ColorDrawable(Color.TRANSPARENT));
		listview.setDividerHeight(Util.dip2px(context, 10));
		int dip5 = Util.dip2px(context, 5);

		RelativeLayout.LayoutParams middlerlp = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		middlerlp.topMargin = Util.dip2px(context, 10);
		middlerlp.bottomMargin = dip5;
		middlerlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		bgview2.addView(listview, middlerlp);

	}

}
