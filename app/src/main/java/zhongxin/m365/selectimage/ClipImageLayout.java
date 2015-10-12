package zhongxin.m365.selectimage;

import zhongxin.m365.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

/**
 * http://blog.csdn.net/lmj623565791/article/details/39761281
 * 
 * @author zhy
 * 
 */
public class ClipImageLayout extends RelativeLayout {

	private ClipZoomImageView mZoomImageView;
	private ClipImageBorderView mClipImageView;

	private int mHorizontalPadding;
	private String mClipImageSrc;

	public void setmClipImageSrc(String mClipImageSrc) {
		this.mClipImageSrc = mClipImageSrc;
		Uri mUri = Uri.parse(mClipImageSrc);
		mZoomImageView.setImageURI(mUri);
	}

	public ClipImageLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ClipImageLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.mClipImage, defStyle, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.mClipImage_ClipImageHorizontalPadding:
				mHorizontalPadding = a.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_PX, 20, getResources()
										.getDisplayMetrics()));
				break;
			default:
				break;
			}
		}

		mZoomImageView = new ClipZoomImageView(context);
		mClipImageView = new ClipImageBorderView(context);

		android.view.ViewGroup.LayoutParams lp = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);

		this.addView(mZoomImageView, lp);
		this.addView(mClipImageView, lp);

		// 计算padding的px
		mHorizontalPadding = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding, getResources()
						.getDisplayMetrics());
		mZoomImageView.setHorizontalPadding(mHorizontalPadding);
		mClipImageView.setHorizontalPadding(mHorizontalPadding);
	}

	/**
	 * 对外公布设置边距的方法,单位为dp
	 * 
	 * @param mHorizontalPadding
	 */
	public void setHorizontalPadding(int mHorizontalPadding) {
		this.mHorizontalPadding = mHorizontalPadding;
	}

	/**
	 * 裁切图片
	 * 
	 * @return
	 */
	public Bitmap clip() {
		return mZoomImageView.clip();
	}

}
