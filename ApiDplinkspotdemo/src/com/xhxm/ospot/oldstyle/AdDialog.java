package com.xhxm.ospot.oldstyle;

import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.xhxm.ospot.AsyncImageLoader;
import com.xhxm.ospot.AsyncImageLoader.ImageCallback;
import com.xhxm.ospot.Util;
import com.xhxm.ospot.model.AdContent;
import com.xhxm.ospot.task.PostLogThread;
import com.xhxm.ospot.util.AdUtils;
import com.xhxm.ospot.util.Imgstrs;
import com.xhxm.ospot.view.CloseViewBtn;

public class AdDialog extends Dialog implements View.OnClickListener {

	
	
	
	public AdDialog(Context context) {
		super(context);
	}

	public AdDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public AdDialog(Context context, int theme) {
		super(context, 16973840);
	}



	private AsyncImageLoader mAsyncImageLoader;
	private Drawable loadDrawable;
	private Activity context;
	LinearLayout bgview;
	CloseViewBtn tiv;
	AdView adview;
	ListView lv;
	ImageView img;
	
	
	AdContent ad;

	int pos = 0;


	public void setActivity(Activity paramActivity) {
		this.context = paramActivity;
	}

	public void init() {

//		Util.setBackgroud(install, Util.readDrawable(context,Imgstrs.install_button));
		loadDrawable = Util.readDrawable(context,Imgstrs.x);
		mAsyncImageLoader = new AsyncImageLoader(context);
		
		View appview = initpopview();
		addMyView();
		
		setContentView(appview);
		setCancelable(false);
		show();
	}

	public void setads(AdContent allads) {
		if (allads != null) {
			this.ad = allads;
		}
	}


	@Override
	public void show() {
		super.show();
		new PostLogThread(context, ad.getShow(), 1).start();
	}

	private AdView initpopview() {
		if (adview == null) {
			adview = new AdView(context);
			adview.setBackgroundColor(Color.argb(100, 0, 0, 0));
			bgview = (LinearLayout) adview.findViewById(AdView.BGVIEWID);
			img = (ImageView) adview.findViewById(AdView.POPIMGID);
//			tiv = (QuickShortcut) adview.findViewById(AdView.CLOSEIMGID);
//			tiv.setOnClickListener(this);

			bgview.setOnClickListener(new BgDownloadClickListener());
			mHandler.sendEmptyMessageDelayed(5, 5 * 1000);
		}
		return adview;
	}

	private class BgDownloadClickListener implements View.OnClickListener {



		@Override
		public void onClick(View v) {
			predownload(ad);
		}
	}



	private void addMyView() {
		
		Button downloadbtn = new Button(context);
		downloadbtn.setFocusable(false);
		
		Util.setBackgroud(downloadbtn, Util.readDrawable(context,Imgstrs.s));
		
		downloadbtn.setId(AdView.DBTNID);
		downloadbtn.setTextSize(18);
		downloadbtn.setPadding(1, 0, 1, 1);
		downloadbtn.setText("立即打开");
		downloadbtn.setTextColor(Color.WHITE);
		downloadbtn.setGravity(Gravity.CENTER);
		
		RelativeLayout.LayoutParams btnParams = new RelativeLayout.LayoutParams(AdUtils.dip2px(context, (int) (190 / 1.5f)),
				AdUtils.dip2px(context, (int) (50 / 1.5f)));
		btnParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		btnParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		int bottomMargin = AdUtils.dip2px(context, 10);
		btnParams.bottomMargin = bottomMargin;
		
		((RelativeLayout)(adview.findViewById(AdView.RVIEWID))).addView(downloadbtn, btnParams);

		Bitmap cachedImage = mAsyncImageLoader.loadBitmap(ad.getImgLink(), new ImageCallback() {
			@Override
			public void imageLoaded(Bitmap imageDrawable, String imageUrl) {
				if (imageDrawable != null && !imageDrawable.isRecycled()) {
					if (ad.getImgLink().equals(imageUrl)) {
						img.setImageBitmap(imageDrawable);
					}
				}
			}
		});
		if (cachedImage == null || cachedImage.isRecycled()) {
			img.setImageDrawable(loadDrawable);
		} else {
			img.setImageBitmap(cachedImage);
		}


		downloadbtn.setBackgroundDrawable(getBtnThemeBg(new Random().nextInt(4) + 1));
		downloadbtn.setOnClickListener(new MyAppDownloadClickListener(ad));
		
		img.setOnClickListener(new MyAppDownloadClickListener(ad));
		setBtnLoc(new Random().nextInt(3) + 1, btnParams);
	}


	
	public Drawable getBtnThemeBg(int id) {
		Drawable drawable = Util.readDrawable(context,Imgstrs.q);
		switch (id) {
		case 1:
			drawable = Util.readDrawable(context,Imgstrs.q);
			break;
		case 2:
			drawable = Util.readDrawable(context,Imgstrs.r);
			break;
		case 3:
			drawable = Util.readDrawable(context,Imgstrs.s);
			break;
		case 4:
			drawable = Util.readDrawable(context,Imgstrs.t);
			break;
		default:
			break;
		}
		return drawable;
	}
	
	/**
	 * 顶部3个tab点击监听事件
	 */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case AdView.CLOSEIMGID:
			dismiss();
			break;

		default:
			break;
		}
	}

	/**
	 * 监听下载,关闭按钮点击事件
	 * 
	 * @author abc
	 */
	private class MyAppDownloadClickListener implements View.OnClickListener {

		private AdContent ad;

		public MyAppDownloadClickListener(AdContent ad) {
			this.ad = ad;
		}

		@Override
		public void onClick(View v) {
			predownload(ad);
		}
	}

	/**
	 * 下载并提交点击事件
	 * 
	 * @param ad
	 * @param type
	 *            2,下载并提交点击事件
	 */
	private void predownload(AdContent ad) {
		
		try {
			Util.openDeepLinkApp(context, ad.getDeepLink(),
					ad.getPkg());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new PostLogThread(context, ad.getClick(), 2).start();
		dismiss();
	}
	




	@Override
	public void dismiss() {
		super.dismiss();
	}

	/*
	 * @Override 屏蔽home键 public void onAttachedToWindow() {
	 * this.getWindow().setType
	 * (WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
	 * super.onAttachedToWindow(); }
	 */

	private void setBtnLoc(int id, RelativeLayout.LayoutParams btnParams) {
		switch (id) {
		case 1:
			btnParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			int leftMargin = AdUtils.dip2px(context, 10);
			btnParams.leftMargin = leftMargin;
			break;
		case 2:
			btnParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
			break;
		case 3:
			btnParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			int rightMargin = AdUtils.dip2px(context, 10);
			btnParams.rightMargin = rightMargin;
			break;
		default:
			break;
		}
	}







	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 12:

				AdContent ad = (AdContent) msg.obj;
				predownload(ad);

				break;
			
			case 5:
				// 延迟增加广告关闭按钮
				
				tiv = new CloseViewBtn(context,AdView.CLOSEIMGID);
				adview.addView(tiv,tiv.addParams(AdView.RVIEWID));
				tiv.setOnClickListener(AdDialog.this);
				
				break;
				
			}
		}
	};



}
