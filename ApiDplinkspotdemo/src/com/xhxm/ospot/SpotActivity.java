package com.xhxm.ospot;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xhxm.ospot.model.AdContent;
import com.xhxm.ospot.task.PostLogThread;
import com.xhxm.ospot.util.AdUtils;
import com.xhxm.ospot.view.CloseViewBtn;
import com.xhxm.ospot.view.AdSpotView;
import com.xhxm.ospot.view.RoundedImageView;

public class SpotActivity extends Activity implements OnClickListener {

	private AsyncImageLoader mAsyncImageLoader;
	private Activity mContext;

	private AdSpotView madView;
	// 广告内容
	private AdContent mAdContent;

	private void setActivity(Activity paramActivity) {
		this.mContext = paramActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setActivity(this);
		showAd();
	}

	// 获得广告，根据广告类型展示广告
	public void showAd() {
		mAdContent = AdPool.getIntersititialAd();
		if (mAdContent == null) {
			AdUtils.dealFinishActivity(mContext);
			return;
		}
		mAsyncImageLoader = new AsyncImageLoader(mContext);
		showPortraitAAd();

		new PostLogThread(mContext, mAdContent.getShow(), 1).start();
		AdPool.removIntersititialads();
	}

	public void showPortraitAAd() {
		madView = new AdSpotView(mContext);
		madView.initView();

		CloseViewBtn closeViewBtn = (CloseViewBtn) madView
				.findViewById(AdSpotView.CLOSEIMGID);
		TextView briefText = (TextView) madView
				.findViewById(AdSpotView.AD_BRIEF);
		RoundedImageView adPosterImg = (RoundedImageView) madView
				.findViewById(AdSpotView.POSTERIMGID);

		Button openbtn = (Button) madView.findViewById(AdSpotView.AD_OPENAd);

		setImage(adPosterImg, mAdContent.getImgLink());
		String brief = mAdContent.getImgDesc();
		if (!TextUtils.isEmpty(brief)) {
			briefText.setText(getCharSequence(brief));
		}
		openbtn.setOnClickListener(this);
		closeViewBtn.setOnClickListener(this);
		madView.setOnClickListener(this);

		setContentView(madView);
	}
	
	@SuppressLint("NewApi")
	private CharSequence getCharSequence(String brief){
		CharSequence charSequence;
		if (android.os.Build.VERSION.SDK_INT >= 24) {
			charSequence = Html.fromHtml(brief, Html.FROM_HTML_MODE_LEGACY);
		} else {
			charSequence = Html.fromHtml(brief);
		}
		return charSequence;
	}

	public void setImage(final ImageView view, final String iconUrl) {
		if (!TextUtils.isEmpty(iconUrl)) {
			Bitmap cachedImage = mAsyncImageLoader.loadBitmap(iconUrl,
					new com.xhxm.ospot.AsyncImageLoader.ImageCallback() {
						@Override
						public void imageLoaded(Bitmap imageDrawable,
								String imageUrl) {
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
		switch (view.getId()) {
		case AdSpotView.CLOSEIMGID:// 竖屏A关闭按钮
			dealCloseAd();
			break;

		case AdSpotView.SPOTVIEW_ID://
		case AdSpotView.AD_OPENAd://
			ListenerUtil.onADClicked();
			try {
				Util.openDeepLinkApp(mContext, mAdContent.getDeepLink(),
						mAdContent.getPkg());
				new PostLogThread(mContext, mAdContent.getClick(), 2).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			dealCloseAd();
			break;
		}
	}

	public void dealCloseAd() {
		ListenerUtil.onADClosed();
		AdUtils.dealFinishActivity(mContext);
	}

	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
		if (paramInt == 4) {
			return true;
		}
		return super.onKeyDown(paramInt, paramKeyEvent);
	}
}
