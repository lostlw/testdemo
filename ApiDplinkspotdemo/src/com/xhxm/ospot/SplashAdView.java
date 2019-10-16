package com.xhxm.ospot;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.xhxm.ospot.model.AdContent;
import com.xhxm.ospot.task.PostLogThread;
import com.xhxm.ospot.util.UIUtils;

public class SplashAdView extends FrameLayout implements View.OnClickListener {

	private Context context;
	private ImageView vx;
	private AsyncImageLoader mAsyncImageLoader;
	private AdContent mAdContent;

	public SplashAdView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		reaustAd();
	}

	public SplashAdView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		reaustAd();
	}

	public SplashAdView(Context context) {
		super(context);
		this.context = context;
		reaustAd();
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		
		AdPool.removExitads();
		new PostLogThread(context, mAdContent.getShow(),1).start();
	}

	public void reaustAd() {
		SplashAdView.this.mAdContent = AdPool.getSplashAd();
		init();
	}

	public void init() {
		initView();
		bindAdData();
	}
	
	public void initView() {
		vx = new ImageView(context);
		
		int screenwidth = UIUtils.getmScreenWidth(context);
		
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(screenwidth,
				(int)(screenwidth * 1.43));
		vx.setScaleType(ScaleType.FIT_XY);
		SplashAdView.this.addView(vx, params);
		mAsyncImageLoader = new AsyncImageLoader(context);
		
	}
	
	

	/**
	 * 初始化点击事件等
	 * 
	 * @param ad
	 */
	public void bindAdData() {

		setImage(vx, mAdContent.getImgLink());
		vx.setOnClickListener(this);
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
		ListenerUtil.onADClicked();
		try {
			Util.openDeepLinkApp(context, mAdContent.getDeepLink(),mAdContent.getPkg());
			new PostLogThread(context, mAdContent.getClick(),2).start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
