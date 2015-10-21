package zhongxin.m365;

/**
 * Created by zhaozhenqiang on 2015/10/10.
 * 专门为选取头像而设置的页面！可以选择照片，及拍照并剪切
 */

import java.io.File;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TestActivity extends Activity {
    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 480;
    private static int output_Y = 480;

    private ImageView headImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        headImage = (ImageView) findViewById(R.id.image);
        Button buttonLocal = (Button) findViewById(R.id.click_local);
        buttonLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromGallery();
            }
        });
        Button buttonCamera = (Button) findViewById(R.id.click_camera);
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromCameraCapture();
            }
        });
    }

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {

        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }

                break;

            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            headImage.setImageBitmap(photo);
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }
/*    private ImageView imageView;
    private Button clickBtnLocal;
    private Button clickBtnCamera;

    private Uri photoUri;

    private final int PIC_FROM_CAMERA = 1;
    private final int PIC_FROM＿LOCALPHOTO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        imageView = (ImageView) findViewById(R.id.image);
        clickBtnLocal = (Button) findViewById(R.id.click_local);
        clickBtnCamera = (Button) findViewById(R.id.click_camera);

        //本地选择
        clickBtnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doHandlerPhoto(PIC_FROM＿LOCALPHOTO);// 从相册中去获取
            }
        });

        //拍照
        clickBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doHandlerPhoto(PIC_FROM_CAMERA);// 用户点击了从照相机获取
            }
        });
    }

    *//**
     * 根据不同方式选择图片设置ImageView
     *
     * @param type 0-本地相册选择，非0为拍照
     *//*
    private void doHandlerPhoto(int type) {
        try {
            //保存裁剪后的图片文件
            File pictureFileDir = new File(Environment.getExternalStorageDirectory(), "/upload");
            if (!pictureFileDir.exists()) {
                pictureFileDir.mkdirs();
            }
            File picFile = new File(pictureFileDir, "upload.jpeg");
            if (!picFile.exists()) {
                picFile.createNewFile();
            }
            photoUri = Uri.fromFile(picFile);

            if (type == PIC_FROM＿LOCALPHOTO) {
                Intent intent = getCropImageIntent();
                startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(cameraIntent, PIC_FROM_CAMERA);
            }

        } catch (Exception e) {
            Log.i("HandlerPicError", "处理图片出现错误");
        }
    }

    *//**
     * 调用图片剪辑程序
     *//*
    public Intent getCropImageIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image*//*");
        setIntentParams(intent);
        return intent;
    }

    *//**
     * 启动裁剪
     *//*
    private void cropImageUriByTakePhoto() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image*//*");
        setIntentParams(intent);
        startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
    }

    *//**
     * 设置公用参数
     *//*
    private void setIntentParams(Intent intent) {
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 600);
        intent.putExtra("noFaceDetection", true); // no face detection
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PIC_FROM_CAMERA: // 拍照
                try {
                    cropImageUriByTakePhoto();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case PIC_FROM＿LOCALPHOTO:
                try {
                    if (photoUri != null) {
                        Bitmap bitmap = decodeUriAsBitmap(photoUri);
                        imageView.setImageBitmap(bitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }*/
}
