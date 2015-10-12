package zhongxin.m365.views;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
/*
 * zzq
 http://blog.csdn.net/aaawqqq/article/details/39851755 主要参考
 http://blog.csdn.net/zwx622/article/details/38340991  焦点优化 失去焦点隐藏图片
 * */
public class ClearableEditText extends EditText {
	private final String TAG = "MyEditText";
	private Drawable dRight;
	private Rect rBounds;

	public ClearableEditText(Context paramContext) {
		super(paramContext);
		initEditText();
	}

	public ClearableEditText(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		initEditText();
	}

	public ClearableEditText(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		initEditText();
	}

	// 初始化edittext 控件
	private void initEditText() {
		setEditTextDrawable();
		addTextChangedListener(new TextWatcher() { // 对文本内容改变进行监听
			@Override
			public void afterTextChanged(Editable paramEditable) {
			}

			@Override
			public void beforeTextChanged(CharSequence paramCharSequence,
					int paramInt1, int paramInt2, int paramInt3) {
			}

			@Override
			public void onTextChanged(CharSequence paramCharSequence,
					int paramInt1, int paramInt2, int paramInt3) {
				ClearableEditText.this.setEditTextDrawable();
			}
		});
	}

	// 控制图片的显示
	public void setEditTextDrawable() {
		if (getText().toString().length() == 0) {
			setCompoundDrawables(null, null, null, null);
		} else {
			setCompoundDrawables(null, null, this.dRight, null);
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		this.dRight = null;
		this.rBounds = null;

	}

	/**
	 * 添加触摸事件 点击之后 出现 清空editText的效果
	 */
	@Override
	public boolean onTouchEvent(MotionEvent paramMotionEvent) {
		if ((this.dRight != null) && (paramMotionEvent.getAction() == 1)) {
			this.rBounds = this.dRight.getBounds();
			int i = (int) paramMotionEvent.getRawX();// 距离屏幕的距离
			// int i = (int) paramMotionEvent.getX();//距离边框的距离
			if (i > getRight() - 3 * this.rBounds.width()) {
				setText("");
				paramMotionEvent.setAction(MotionEvent.ACTION_CANCEL);
			}
		}
		return super.onTouchEvent(paramMotionEvent);
	}

	/**
	 * 显示右侧X图片的
	 * 
	 * 左上右下
	 */
	@Override
	public void setCompoundDrawables(Drawable paramDrawable1,
			Drawable paramDrawable2, Drawable paramDrawable3,
			Drawable paramDrawable4) {
		if (paramDrawable3 != null)
			this.dRight = paramDrawable3;
		super.setCompoundDrawables(paramDrawable1, paramDrawable2,
				paramDrawable3, paramDrawable4);
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		if (focused) {

		} else {
			setCompoundDrawables(null, null, null, null);
		}
	}
}