package zhongxin.m365.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import zhongxin.m365.basedialog.BaseDialog;
import zhongxin.m365.basedialog.BaseDialog.DoselectOk;
import zhongxin.m365.basedialog.DownLoadNoticeDialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HttpUtils {

	private final static String TAG = HttpUtils.class.getSimpleName();

	/**
	 * 无加载对话框 对接口的上传和获取接口中的数据，发送到主线程
	 * 
	 * @param url
	 *            上传接口url
	 * @param keys
	 *            给接口的键
	 * @param values
	 *            给接口的值
	 * @param backJson
	 *            回调
	 */
	public static final void upload(final String url, final String[] keys,
			final String[] values, final BackJson backJson) {
		Log.i(TAG, "-------upload url = " + url);
		final int what = 1;
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == what) {
					String json = (String) msg.obj;
					Log.i(TAG, "-------down json = " + json);
					backJson.backJson(json);
				}
			};
		};

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				client.getParams().setParameter(
						CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
				// 读取超时
				client.getParams().setParameter(
						CoreConnectionPNames.SO_TIMEOUT, 5000);
				HttpPost post = new HttpPost(url);
				String ua = "\"Mozilla/5.0 (Linux; U; Android 4.2.2; zh-cn; HUAWEI G750-T20 Build/HuaweiG750-T20) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30\"";
				String key = "1409110378";
				String token = "beb78f46b0de7e1c9cc9849294eda43f";
				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

				post.addHeader("key", key);
				post.addHeader("token", token);
				post.addHeader("User-Agent", ua);

				for (int i = 0; i < keys.length; i++) {
					params.add(new BasicNameValuePair(keys[i], values[i]));
				}
				UrlEncodedFormEntity entity;
				try {
					entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
					post.setEntity(entity);
					HttpResponse response = client.execute(post);
					if (response.getStatusLine().getStatusCode() == 200) {
						String json = EntityUtils.toString(
								response.getEntity(), HTTP.UTF_8);
						Message msg = Message.obtain();
						msg.what = what;
						msg.obj = json;
						handler.sendMessage(msg);
					} else {
						// stuscode！=200
						int statuscode = response.getStatusLine()
								.getStatusCode();
						System.out.print(statuscode);
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();

				}

			}
		}).start();
	}

	public interface BackJson {
		public void backJson(String json);
	}

	// ------------------------------------------------------------------------------
	/**
	 * 包含加载对话框
	 * 
	 * @param activity
	 * @param url
	 * @param keys
	 * @param values
	 * @param backJson
	 */
	public static final void upload(final Activity activity, final String url,
			final String[] keys, final String[] values, final BackJson backJson) {
		Log.i(TAG, "-------upload url = " + url);
		Log.i(TAG, "-------upload keys = " + keys);
		Log.i(TAG, "-------upload values = " + values);
		final int what = 1;
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == what) {
					String json = (String) msg.obj;
					Log.i(TAG, "-------down json = " + json);
					backJson.backJson(json);
				}
			};
		};

		// 显示加载对话框
		final DownLoadNoticeDialog loadNoticeDialog = new DownLoadNoticeDialog(
				activity);
		loadNoticeDialog.showDialog();

		new Thread(new Runnable() {
			@Override
			public void run() {

				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(url);
				String ua = "\"Mozilla/5.0 (Linux; U; Android 4.2.2; zh-cn; HUAWEI G750-T20 Build/HuaweiG750-T20) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30\"";
				String key = "1409110378";
				String token = "beb78f46b0de7e1c9cc9849294eda43f";
				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

				post.addHeader("key", key);
				post.addHeader("token", token);
				post.addHeader("User-Agent", ua);

				for (int i = 0; i < keys.length; i++) {
					params.add(new BasicNameValuePair(keys[i], values[i]));
				}
				UrlEncodedFormEntity entity;
				try {
					entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
					post.setEntity(entity);
					client.getParams().setParameter(
							CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
					// 读取超时
					client.getParams().setParameter(
							CoreConnectionPNames.SO_TIMEOUT, 5000);

					HttpResponse response = client.execute(post);
					if (loadNoticeDialog != null) {
						loadNoticeDialog.dismiss(); // 关闭加载对话框
						
						//activity.runOnUiThread();
					}

					if (response.getStatusLine().getStatusCode() == 200) {
						String json = EntityUtils.toString(
								response.getEntity(), HTTP.UTF_8);

						Message msg = Message.obtain();
						msg.what = what;
						msg.obj = json;
						handler.sendMessage(msg);
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					mHandler.post(new Runnable() {
						public void run() {
							if (loadNoticeDialog != null) {
								loadNoticeDialog.dismiss(); // 关闭加载对话框

							}
							BaseDialog.showDialogforSelect(activity, null,
									"网络连接异常，您确认打开网络连接吗？", new DoselectOk() {

										@Override
										public void doselectok() {
											// TODO Auto-generated method stub
											if (android.os.Build.VERSION.SDK_INT > 10) {
												// 3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
												activity.startActivity(new Intent(
														android.provider.Settings.ACTION_SETTINGS));
											} else {
												activity.startActivity(new Intent(
														android.provider.Settings.ACTION_WIRELESS_SETTINGS));
											}

										}
									});
						}
					});

				} catch (ClientProtocolException e) {
					e.printStackTrace();
					mHandler.post(new Runnable() {
						public void run() {
							if (loadNoticeDialog != null) {
								loadNoticeDialog.dismiss(); // 关闭加载对话框

							}
							BaseDialog.showDialogforSelect(activity, null,
									"网络连接异常，您确认打开网络连接吗？", new DoselectOk() {

										@Override
										public void doselectok() {
											// TODO Auto-generated method stub
											if (android.os.Build.VERSION.SDK_INT > 10) {
												// 3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
												activity.startActivity(new Intent(
														android.provider.Settings.ACTION_SETTINGS));
											} else {
												activity.startActivity(new Intent(
														android.provider.Settings.ACTION_WIRELESS_SETTINGS));
											}

										}
									});
						}
					});

				} catch (IOException e) {
					e.printStackTrace();

					mHandler.post(new Runnable() {
						public void run() {
							if (loadNoticeDialog != null) {
								loadNoticeDialog.dismiss(); // 关闭加载对话框

							}
							BaseDialog.showDialogforSelect(activity, null,
									"网络连接异常，您确认打开网络连接吗？", new DoselectOk() {

										@Override
										public void doselectok() {
											// TODO Auto-generated method stub
											if (android.os.Build.VERSION.SDK_INT > 10) {
												// 3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
												activity.startActivity(new Intent(
														android.provider.Settings.ACTION_SETTINGS));
											} else {
												activity.startActivity(new Intent(
														android.provider.Settings.ACTION_WIRELESS_SETTINGS));
											}

										}
									});
						}
					});

				}

			}
		}).start();
	}

	final static Handler mHandler = new Handler();

}
