package com.xhxm.ospot;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xhxm.ospot.AsyncImageLoader.ImageCallback;
import com.xhxm.ospot.model.AdContent;
import com.xhxm.ospot.task.PostLogThread;
import com.xhxm.ospot.task.ThreadPool;
import com.xhxm.ospot.view.ListAdView;
import com.xhxm.ospot.view.Sitem;

public class ListActivity extends Activity implements OnItemClickListener {

	boolean textflag = false;

	private Activity context;
	LinearLayout bgview;
	ListAdView gview;
	ListView lv;


	private List<AdContent> ads = new ArrayList<AdContent>();

	private AppAdapter adapter;

	private ListAdView scv;
	ListView i;

	public void setActivity(Activity paramActivity) {
		this.context = paramActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setActivity(this);
		ads = AdPool.getIntersititialAds();
		for (int i = 0; i < ads.size(); i++) {
			PostLogThread plog = new PostLogThread(context, ads.get(i)
					.getShow(), 1);
			ThreadPool.getThreadPool().execute(plog);
		}
		scv = initGview();
		context.setContentView(scv);

	}

	private ListAdView initGview() {
		if (gview == null) {
			gview = new ListAdView(context);
			i = (ListView) gview.findViewById(ListAdView.GRIDVIEWID);
			adapter = new AppAdapter();
			adapter.setView(i);
			i.setAdapter(adapter);
			i.setOnItemClickListener(this);
		}
		return gview;
	}

	class AppAdapter extends BaseAdapter {

		private View lv;

		AsyncImageLoader mAsyncImageLoader;
		Drawable loadDrawable;

		public void setView(View lv) {
			this.lv = lv;
		}

		public AppAdapter() {
			mAsyncImageLoader = new AsyncImageLoader(context);
			loadDrawable = new ColorDrawable(Color.TRANSPARENT);
		}

		@Override
		public int getCount() {
			return ads.size();
		}

		@Override
		public Object getItem(int position) {
			return ads.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			Hodler hodler = null;
			if (convertView == null) {
				hodler = new Hodler();
				convertView = new Sitem(context);
				createView((Sitem) convertView, hodler);

				convertView.setTag(hodler);
			} else {
				hodler = (Hodler) convertView.getTag();
			}
			setView(hodler, position);
			return convertView;
		}

		public void setView(final Hodler hodler, final int position) {
			final AdContent ad = ads.get(position);
			hodler.image.setTag(ad.getIconurl());
			Bitmap cachedImage = mAsyncImageLoader.loadBitmap(ad.getIconurl(),
					new ImageCallback() {
						@Override
						public void imageLoaded(Bitmap imageDrawable,
								String imageUrl) {
							if (imageDrawable != null) {
								ImageView imageViewByTag = (ImageView) lv
										.findViewWithTag(imageUrl);
								if (imageViewByTag != null) {
									imageViewByTag
											.setImageBitmap(imageDrawable);
								} else {
									hodler.image.setImageBitmap(imageDrawable);
								}
							}
						}
					});
			if (cachedImage == null) {
				hodler.image.setImageDrawable(loadDrawable);
			} else {
				hodler.image.setImageBitmap(cachedImage);
			}

			hodler.tv1.setText(ad.getTitle());
			hodler.price.setText("打开试玩20秒,返回领" + ad.getPrice() + "元");

			hodler.openbtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					AdContent mAdContent = ads.get(position);
					openAd(mAdContent,position);
				}
			});
		}

		public void createView(Sitem relative, Hodler hodler) {
			hodler.image = (ImageView) relative.findViewById(Sitem.LOGOID);
			hodler.tv1 = (TextView) relative.findViewById(Sitem.TNAMEID);
			hodler.price = (TextView) relative.findViewById(Sitem.TPRICE);
			hodler.openbtn = (Button) relative.findViewById(Sitem.OPENBTNID);
		}

		class Hodler {
			private ImageView image;
			private TextView tv1;
			private TextView price;
			private Button openbtn;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AdPool.removIntersititialads();
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	protected void onRestart() {
		super.onRestart();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		AdContent mAdContent = ads.get(position);
		openAd(mAdContent,position);
		

	}

	private void openAd(AdContent mAdContent,int position) {
		try {
			Util.openDeepLinkApp(context, mAdContent.getDeepLink(),
					mAdContent.getPkg());
			PostLogThread plog = new PostLogThread(context, mAdContent.getClick(), 2);
			ThreadPool.getThreadPool().execute(plog);
			//已点击过的广告移除列表
			AdPool.removIntersititialads(position);
			adapter.notifyDataSetChanged();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
