package zhongxin.m365.activity.older;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import zhongxin.m365.R;
import zhongxin.m365.activity.BaseActivity;
import zhongxin.m365.adapter.older.SelectServeAdapter;
import zhongxin.m365.bean.older.FatherServe;
import zhongxin.m365.bean.older.SubServe;
import zhongxin.m365.constant.UCS;
import zhongxin.m365.utils.HttpUtils;
import zhongxin.m365.utils.HttpUtils.BackJson;
import zhongxin.m365.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * 选择服务
 */
public class SelectServeActivity extends BaseActivity implements
		OnChildClickListener,OnGroupClickListener, OnClickListener{
	private String tag = this.getClass().getSimpleName();
	private List<FatherServe> fatherList;// 存储标题
	private TextView basetitle;// 选择服务类别
	private Button gooerderbtn;// 去预约
	private SelectServeAdapter adapter;
	// 存储选中的美容服务项目
	private Map<String, SubServe> itemMap = new HashMap<String, SubServe>();// 存储选中的Item内的服务
	// 存储美容服务项目 LisT<List<>>
	private ArrayList<ArrayList<SubServe>> lists;
	private ExpandableListView expandableListView;
	private LinearLayout ishasresult;
    String[] intentId = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectservice);
		Log.d(tag, "------------------------oncreate（）");
		getintentdate();

		getStoreService(); // 获取门店服务详情

		initView();

	}

	private void initView() {
		basetitle = (TextView) findViewById(R.id.basetitle);
			basetitle.setText("新建预约单");
	
		gooerderbtn = (Button) findViewById(R.id.gooerderbtn);
		gooerderbtn.setOnClickListener(this);
		expandableListView = (ExpandableListView) findViewById(R.id.expendlistview);
		expandableListView.setGroupIndicator(null);//去掉右侧的收缩按钮！！！！
		expandableListView.setOnChildClickListener(this);// 子listView点击事件
		// expandableListView.setGroupIndicator(this.getResources().getDrawable(R.drawable.expendlistview_expandandfold));
		expandableListView.setOnGroupClickListener(this);//新添加监听事件，，，组按钮点击事件                    xq
		ishasresult = (LinearLayout) findViewById(R.id.ishasresult);
		gooerderbtn.setVisibility(View.INVISIBLE);// 设置不可见 （获取到数据后 设置可见）
	}

	public void getintentdate() {
		// TODO Auto-generated method stub
		if (getIntent().getExtras() != null) {
			String serveValue = getIntent().getExtras().getString("serveValue");
			intentId = serveValue.split(",");
			
		}
	}

	private void getStoreService() {
		// TODO Auto-generated method stub
		//String url = "http://api.v1.1jia2.com.cn/beauty/beauty_info_one";// 'beauty/beauty_info_one'
		//order/index/getserviceitem
		//cust_id	
		SharedPreferences preferences = getSharedPreferences(UCS.USERINFO,
				Activity.MODE_PRIVATE);
		String cust_id = preferences.getString("cust_id", null);
		String url = UCS.URLCOMMON + "order/index/getserviceitem";
		String keys[] = {"cust_id"};
		String values[] = {cust_id};
		
		
		//String keys[] = { UCS.SID ,UCS.NAME};
		//String values[] = { "6","beauty" };
		HttpUtils.upload(SelectServeActivity.this, url, keys, values,
				new BackJson() {

					@Override
					public void backJson(String json) {
						// TODO Auto-generated method stub
						UIFresh(json);
					}
				});
	}

	private void UIFresh(String json) {
		// TODO Auto-generated method stub
		JSONObject dates;
		try {
			lists = new ArrayList<ArrayList<SubServe>>();
			dates = new JSONObject(json);
			fatherList = new ArrayList<FatherServe>();
			if (dates.getString("code").equals("1")) {
				JSONArray data = dates.getJSONArray(UCS.DATA);
				//JSONArray data = mJson.getJSONArray(UCS.DATA);
				Gson gson = new Gson();
				
				//JSONArray item = data.getJSONArray("item");
				fatherList = gson.fromJson(data.toString(),
						new TypeToken<ArrayList<FatherServe>>() {
						}.getType());
				System.out.println(fatherList.size());
				for(int i=0;i<fatherList.size();i++){
					lists.add(fatherList.get(i).getItem());
				}
				if(intentId !=null){
					for(String id:intentId){
						for(int i=0;i<lists.size();i++){
							for(int j=0;j<lists.get(i).size();j++){
								if(lists.get(i).get(j).getId().equals(id)){
									lists.get(i).get(j).setFlag("1");
									itemMap.put(lists.get(i).get(j).getName(),
											lists.get(i).get(j));
								}
							}
						}
					}
				}
				
				
			} else {
				String msg = dates.getString(UCS.RS_MSG);
				ToastUtils.defaultErrorImageToast(getApplicationContext(), msg);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//if (!fatherList.isEmpty() && !lists.isEmpty()) {
		if (!fatherList.isEmpty()){
			gooerderbtn.setVisibility(View.VISIBLE);
			adapter = new SelectServeAdapter(getApplicationContext(), lists,
					fatherList);
			expandableListView.setAdapter(adapter);
			for(int i = 0; i < adapter.getGroupCount(); i++){  
                
				   expandableListView.expandGroup(i);  
				                          
				}
		} else {
			ishasresult.removeAllViews();
			LinearLayout layout = (LinearLayout) LayoutInflater.from(
					getApplicationContext()).inflate(R.layout.nonedate, null);
			ishasresult.addView(layout);
		}

	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
		case R.id.gooerderbtn:// 去预约
			if (itemMap.isEmpty()) {
				ToastUtils.defaultErrorImageToast(getApplicationContext(),
						"请选择服务项目");

			} else {
				/*Intent intent = new Intent(getApplicationContext(),
						SuitBeautyCarOrderActivity.class);*/
	            Intent intent=new Intent();  

				Bundle bundle = new Bundle();
				String text="";
				String number="";
				for(Map.Entry<String, SubServe> entry: itemMap.entrySet()){
					number+=(entry.getValue().getId()+",");
					text += (entry.getValue().getName()+" ");
				}
				//bundle.putSerializable("map", (Serializable) itemMap);
				bundle.putString("textSelect", text);
				bundle.putString("numberSelect", number);
				
				intent.putExtras(bundle);
                setResult(RESULT_OK, intent);  
                finish();  
			}
			break;
		case R.id.back:// 返回
			finish();
			break;
		}
	}

	@Override
	public boolean onChildClick(ExpandableListView exlistview, View childview,
			int groupposition, int childposition, long childid) {
		TextView subtitle = (TextView) childview.findViewById(R.id.subtitl);
		String subtitlename = subtitle.getText().toString();
		CheckBox isselect = (CheckBox) childview.findViewById(R.id.isselcet);
		if (isselect.isChecked()) {
			SubServe service = lists.get(groupposition).get(childposition);
			service.setFlag("2");
			itemMap.remove(subtitlename);
			isselect.setChecked(false);
			//subtitle.setTextColor(Color.BLACK);TextColordarker
			//subtitle.setTextColor(getResources().getColor(R.color.TextColordarker));
		} else {
			SubServe service = lists.get(groupposition).get(childposition);
			service.setFlag("1");
			itemMap.put(subtitlename,
					lists.get(groupposition).get(childposition));
			isselect.setChecked(true);
			//subtitle.setTextColor(getResources().getColor(R.color.orange));
		}
		adapter.notifyDataSetChanged();// 刷新数据
		return true;
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		getStoreService();
	}
	//控制是否在头部显示 所打开的美容项目分类
	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
/*		TextView subtitle = (TextView) v.findViewById(R.id.subtitl);
		String subtitlename = subtitle.getText().toString();
		CheckBox isselect = (CheckBox) childview.findViewById(R.id.isselcet);
		TextView sub = (TextView) childview.findViewById(R.id.subtitl);	*/
		ImageView arror = (ImageView) v.findViewById(R.id.arror);
		if(parent.isGroupExpanded(groupPosition)){
			arror.setBackgroundResource(R.drawable.arrow_d);
		}
		else{
			arror.setBackgroundResource(R.drawable.arrow_u);
		}
		return false;
	}
}
