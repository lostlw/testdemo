package com.xhxm.ospot.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.xhxm.ospot.AdPool;
import com.xhxm.ospot.AsyncImageLoader;
import com.xhxm.ospot.ListenerUtil;
import com.xhxm.ospot.Util;
import com.xhxm.ospot.constant.AdIdConst;
import com.xhxm.ospot.model.AdContent;
import com.xhxm.ospot.task.PostLogThread;
import com.xhxm.ospot.util.UIUtils;
import com.xhxm.ospot.view.AdExitView;
import com.xhxm.ospot.view.AdLandScapeExitlView;

public class ExitDialog extends Dialog implements View.OnClickListener {

	private AsyncImageLoader mAsyncImageLoader;
	private Activity mContext;

	private AdExitView mAdExitView;
	private AdLandScapeExitlView mAdLandScapeExitlView;
	// 广告内容
	private AdContent mAdContent;
	
	private View.OnClickListener exitClickListener;

	public ExitDialog(Context context) {
		super(context);
	}

	public ExitDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public ExitDialog(Context context, int theme) {
		super(context, 16973840);
	}

	public void setActivity(Activity paramActivity) {
		this.mContext = paramActivity;
	}
	public void setExitOnClickListener(View.OnClickListener exitClickListener) {
		if(exitClickListener!=null){
			this.exitClickListener = exitClickListener;
		}
	}
	
	

	public void init() {
		// 初始化展示广告
		initAd();
		// dialog展示
		show();
	}

	// 获得广告，根据广告类型展示广告
	public void initAd() {
		mAdContent = AdPool.getSplashAd();
		if(mAdContent == null){
			dismiss();
			return;
		}
		mAsyncImageLoader = new AsyncImageLoader(mContext);



		int screenOrientation = UIUtils.getScreenOrientation(mContext);
		if(screenOrientation == 1){
			showLandScapeBAd();
		}else if(screenOrientation == 2){
			showPortraitBAd();
		}
		
		
		AdPool.removExitads();
		new PostLogThread(mContext, mAdContent.getShow(),1).start();
		
	}

	public void showPortraitBAd() {
		mAdExitView = new AdExitView(mContext);
		setContentView(mAdExitView);

		// 插屏图
		setImage(mAdExitView.getMadIv(), mAdContent.getImgLink());

		mAdExitView.getMadIv().setOnClickListener(this);
		mAdExitView.getCancelTv().setOnClickListener(this);

		
		
		if(exitClickListener!=null){
			mAdExitView.getConfirmTv().setOnClickListener(exitClickListener);
		}else{
			mAdExitView.getConfirmTv().setOnClickListener(this);
		}
	}
	public void showLandScapeBAd() {
		mAdLandScapeExitlView = new AdLandScapeExitlView(mContext);
		setContentView(mAdLandScapeExitlView);
		
		// 插屏图
		setImage(mAdLandScapeExitlView.getMadIv(), mAdContent.getImgLink());
		
		mAdLandScapeExitlView.getMadIv().setOnClickListener(this);
		mAdLandScapeExitlView.getCancelTv().setOnClickListener(this);
		
		
		
		if(exitClickListener!=null){
			mAdLandScapeExitlView.getConfirmTv().setOnClickListener(exitClickListener);
		}else{
			mAdLandScapeExitlView.getConfirmTv().setOnClickListener(this);
		}
		
	}

	public void setImage(final ImageView view, final String iconUrl) {
		if (!TextUtils.isEmpty(iconUrl)) {
			Bitmap cachedImage = mAsyncImageLoader.loadBitmap(iconUrl,
					new com.xhxm.ospot.AsyncImageLoader.ImageCallback() {
						@Override
						public void imageLoaded(Bitmap imageDrawable, String imageUrl) {
							if (imageDrawable != null) {
								if (iconUrl.equals(imageUrl)) {
									view.setImageBitmap(imageDrawable);
								}
							}
						}
					});
			if (cachedImage != null) {
				view.setImageBitmap(cachedImage);
			}
		}

	}

	// 点击事件
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case AdIdConst.CONFIRMTVID:// 竖屏的关闭按钮
			
			mContext.finish();
			System.exit(0);
			
			break;
		case AdIdConst.CANCELTVID:// 竖屏的关闭按钮
			dealCloseAd();
			break;

		case AdIdConst.AD_PIG_IMAGE_TV:// 竖屏A的布局
			ListenerUtil.onADClicked();
			try {
				Util.openDeepLinkApp(mContext, mAdContent.getDeepLink(),mAdContent.getPkg());
				
				new PostLogThread(mContext, mAdContent.getClick(),2).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			dealCloseAd();
			break;
		}
	}

	public void dealCloseAd() {
		dismiss();
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void dismiss() {
		super.dismiss();
		ListenerUtil.onADClosed();
	}


}
