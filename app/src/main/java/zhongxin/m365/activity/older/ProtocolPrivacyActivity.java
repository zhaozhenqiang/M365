package zhongxin.m365.activity.older;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import zhongxin.m365.R;
import zhongxin.m365.activity.BaseActivity;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_protocolprivacy)
public class ProtocolPrivacyActivity extends BaseActivity {
	static final String TAG = "ProtocolPrivacyActivity";
String url = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		basetitle.setText("隐私条款");
		url="http://v2.1jia2.cn/test/demo/bound.html";
		System.out.println(url);
		if(getIntent().getStringExtra("title")!=null){
			basetitle.setText("用户协议");
			url="http://v2.1jia2.cn/test/demo/service.html";
			//http://v2.1jia2.cn/test/demo/service.html
		}
		//String tpl = getFromAssets("service.html");  
		WebView webview = (WebView) findViewById(R.id.webshow);
		WebSettings settings = webview.getSettings();
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		settings.setAllowFileAccess(true); // 允许访问文件
		settings.setDefaultTextEncodingName("utf-8");
		//WebSettings wSet = webview.getSettings();
/*		wSet.setJavaScriptEnabled(true);
*/		// 如果页面中链接，如果希望点击链接继续在当前browser中响应，
		// 而不是新开Android的系统browser中响应该链接，
		// 必须覆盖webview的WebViewClient对象
		// 当前的android项目app的打通
		webview.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
				view.loadUrl(url);
				return true;
			}
		});
		
		//webview.loadDataWithBaseURL(null, tpl, "text/html", "utf-8", null);
//http://192.168.0.210:90/v2/test/demo/service.html
		webview.loadUrl(url);

	}
	public String getFromAssets(String fileName) {  
        try {  
            InputStreamReader inputReader = new InputStreamReader(  
                    getResources().getAssets().open(fileName));  
            BufferedReader bufReader = new BufferedReader(inputReader);  
            String line = "";  
            String Result = "";  
            while ((line = bufReader.readLine()) != null)  
                Result += line;  
            return Result;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return "";  
    }  

}
