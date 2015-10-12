package zhongxin.m365.selectimage;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import zhongxin.m365.R;
import zhongxin.m365.constant.UCS;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;


/**
 * 仿iPhone弹窗 上传图片
 * 
 * @author
 * 
 */
@ContentView(R.layout.activity_pop_up)
public class PopUpActivity extends Activity {

	public static final String TAG = "PopUpActivity";
	@ViewInject(R.id.photo_xiangji)
	private TextView photo_xiangji;
	@ViewInject(R.id.photo_xiangce)
	private TextView photo_xiangce;
	@ViewInject(R.id.del_photo)
	private TextView del_photo;
	@ViewInject(R.id.cancel)
	private TextView cancel;
	@ViewInject(R.id.dialog_layout)
	private LinearLayout dialog_layout;
	// ===========================================================================================//
	/** 使用照相机拍照获取图片 */
	public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
	/** 使用相册中的图片 */
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
	/** 从Intent获取图片路径的KEY */
	public static final String KEY_PHOTO_PATH = "photo_path";
	/** 拍照得到的图片路径 */
	private Uri photoUri;
	private String picPath;
	private Bitmap photo;
	private String saveDir;
	private String fileName;
	private File file;
	private File savePicInLocal;
	private Intent lastIntent;
	private String flag;
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		lastIntent = getIntent();
		initData();
	}

	private void initData() {
		flag = "111";
		//优惠券id
		id = 0;
	}

	/** 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	@OnClick({ R.id.photo_xiangji, R.id.photo_xiangce, R.id.del_photo,
			R.id.cancel, R.id.dialog_layout })
	private void mOnclick(View v) {
		switch (v.getId()) {
		case R.id.photo_xiangji:// 选择相册
			takePhoto();
			break;
		case R.id.photo_xiangce:// 拍照
			pickPhoto();
			break;
		case R.id.del_photo:
			// TODO 删除图片
			break;
		case R.id.cancel:
			finish();
			break;
		case R.id.dialog_layout:
			Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
					Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	/** 回调方法 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SELECT_PIC_BY_TACK_PHOTO) {
			// 两种方式获得拍摄照片的返回值
			if (data == null) {
				Toast.makeText(PopUpActivity.this, "未拍摄照片", Toast.LENGTH_LONG)
						.show();
			} else {
				Uri uri = data.getData();
				if (uri != null) {//uri null 必然导致后面photo 为null;但是即便uri不为空，也可能导致photo为空，所以有一下思路；
					photo = BitmapFactory.decodeFile(uri.getPath());
				}
				if (photo == null) {
					Bundle bundle = data.getExtras();
					if (bundle != null) {
						photo = (Bitmap) bundle.get("data");
						savePicInLocal = SavePicInLocal(photo);
						picPath = savePicInLocal.getPath();
						if (photo != null && !photo.isRecycled()) {
							photo.recycle();
							photo = null;
						}
					} else {
						Toast.makeText(PopUpActivity.this, "未拍摄照片",
								Toast.LENGTH_LONG).show();
					}
				}
			}
			if (!(picPath == null || "".equals(picPath))) {
				// TODO 跳转到裁剪View
				Toast.makeText(PopUpActivity.this, picPath,  Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(PopUpActivity.this,
						ClipImageActivity.class);
				intent.putExtra(UCS.CLIP_IMAGE_URI, picPath);
/*				intent.putExtra(UCS.FLAG, flag);
				intent.putExtra(UCS.ID, id);*/
				startActivity(intent);

			} else {
				Toast.makeText(PopUpActivity.this, "未获得拍照的Path", Toast.LENGTH_SHORT).show();
			}
		} else if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {
			doPhoto(requestCode, data);
			if (!(picPath == null || "".equals(picPath))) {
//				// TODO 跳转到裁剪View
				Toast.makeText(PopUpActivity.this, picPath,  Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(PopUpActivity.this,
						ClipImageActivity.class);
				intent.putExtra(UCS.CLIP_IMAGE_URI, picPath);
/*					intent.putExtra(UCS.FLAG, flag);
				intent.putExtra(UCS.ID, id);*/
				startActivity(intent);
			} else {
				Toast.makeText(PopUpActivity.this, "未获得图片的Path",  Toast.LENGTH_SHORT).show();
			}
		}
		finish();
	}

	// ============================================================================================//

	/** 从相册中取图片 */
	private void pickPhoto() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
	}

	/** 拍照获取图片 */
	private void takePhoto() {
		String state = Environment.getExternalStorageState(); // 判断是否存在sd卡
		if (state.equals(Environment.MEDIA_MOUNTED)) { // 直接调用系统的照相机
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
		} else {
			Toast.makeText(PopUpActivity.this, "请检查手机是否有SD卡", Toast.LENGTH_LONG)
					.show();
		}
	}

	/** 选择图片后，获取图片的路径 */
	private void doPhoto(int requestCode, Intent data) {
		if (requestCode == SELECT_PIC_BY_PICK_PHOTO) // 从相册取图片，有些手机有异常情况，请注意
		{
			if (data == null) {
				Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
				return;
			}
			photoUri = data.getData();
			if (photoUri == null) {
				Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
				return;
			}
		}
		String[] pojo = { MediaStore.Images.Media.DATA };
		Log.i(TAG, "pojo[0] = " + pojo[0]);/////////////
		Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
		Log.i(TAG, "cursor[0] = " + cursor);/////////////
		if (cursor != null) {
			int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
			Log.i(TAG, "columnIndex = " + columnIndex);

			cursor.moveToFirst();
			Log.i(TAG, "cursor1] = " + cursor);/////////////
			picPath = cursor.getString(columnIndex);
			cursor.close();
		}
		Log.i(TAG, "imagePath = " + picPath);
		if (picPath != null
				&& (picPath.endsWith(".png") || picPath.endsWith(".PNG")
						|| picPath.endsWith(".jpg") || picPath.endsWith(".JPG"))) {
			lastIntent.putExtra(KEY_PHOTO_PATH, picPath);
			setResult(Activity.RESULT_OK, lastIntent);
			finish();
		} else {

			Toast.makeText(this, "选择图片文件不正确"+"___"+picPath, Toast.LENGTH_LONG).show();
		}
	}

	/** 保存拍摄的照片到手机的sd卡 */
	@SuppressWarnings("finally")
	private File SavePicInLocal(Bitmap bitmap) {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ByteArrayOutputStream baos = null; // 字节数组输出流
		try {
			baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

			byte[] byteArray = baos.toByteArray();// 字节数组输出流转换成字节数组
			// Log.i("geek", "本地的raw的文件的二进制GetPictureData="
			// + GetPictureData().toString());
			Log.i("geek", "本地的raw的文件的二进制byteArray=" + byteArray.toString());
			saveDir = Environment.getExternalStorageDirectory() + "/liangPic";
			File dir = new File(saveDir);
			if (!dir.exists()) {
				dir.mkdirs(); // 创建文件夹
			}
			fileName = saveDir + "/" + System.currentTimeMillis() + ".jpg";
			file = new File(fileName);
			file.delete();
			if (!file.exists()) {
				file.createNewFile();// 创建文件
				Log.i("PicDir", file.getPath());
				// Toast.makeText(PersonalEditUserInfo.this, fileName + "保存成功",
				// 1000).show();
			}
			Log.i("PicDir", file.getPath());
			// 将字节数组写入到刚创建的图片文件中
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(byteArray);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return file;
		}

	}
}
