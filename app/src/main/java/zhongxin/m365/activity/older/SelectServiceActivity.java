package zhongxin.m365.activity.older;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zhongxin.m365.R;
import zhongxin.m365.activity.BaseActivity;
import zhongxin.m365.adapter.older.ExpendLvBeautyAdapter;
import zhongxin.m365.bean.older.FatherServe;
import zhongxin.m365.bean.older.SubServe;
import zhongxin.m365.constant.UCS;
import zhongxin.m365.utils.HttpUtils;
import zhongxin.m365.utils.HttpUtils.BackJson;
import zhongxin.m365.utils.ToastUtils;

import com.google.gson.Gson;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

/**准备删除
 * 单个门店美容服务 详情
 */
public class SelectServiceActivity extends BaseActivity implements
		OnChildClickListener,OnGroupClickListener, OnClickListener, OnGroupExpandListener, OnGroupCollapseListener, OnScrollListener {
	private String tag = this.getClass().getSimpleName();
	private String sid;// 门店sid
	private List<SubServe> subList;
	private SubServe subService;
	private List<FatherServe> titles;// 存储标题
	private TextView basetitle;// 选择服务类别
	private Button gooerderbtn;// 去预约
	private ExpendLvBeautyAdapter adapter;
	// 存储选中的美容服务项目
	private Map<String, SubServe> itemMap = new HashMap<String, SubServe>();// 存储选中的Item内的服务
	// 存储美容服务项目 LisT<List<>>
	private List<List<SubServe>> lists;
	private ExpandableListView expandableListView;
	private LinearLayout ishasresult;
    private String name;
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
		expandableListView.setOnGroupExpandListener(this);  //expandableListView 展开的监听事件
		expandableListView.setOnGroupCollapseListener(this);//expandableListView关闭的监听事件
		expandableListView.setOnGroupClickListener(this);//新添加监听事件，，，组按钮点击事件                    xq
		expandableListView.setOnScrollListener(this);//expandableListView 滑动检测事件
		ishasresult = (LinearLayout) findViewById(R.id.ishasresult);
		gooerderbtn.setVisibility(View.INVISIBLE);// 设置不可见 （获取到数据后 设置可见）
	}

	public void getintentdate() {
		// TODO Auto-generated method stub
		if (getIntent().getExtras() != null) {
			sid = getIntent().getExtras().getString(UCS.SID);
			
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
		HttpUtils.upload(SelectServiceActivity.this, url, keys, values,
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
			lists = new ArrayList<List<SubServe>>();
			dates = new JSONObject(json);
			titles = new ArrayList<FatherServe>();
			if (dates.getString("code").equals("1")) {
				JSONArray allservice = dates.getJSONArray("data");
				for (int i = 0; i < allservice.length(); i++) {
					JSONObject mservice = allservice.getJSONObject(i);
					String title = mservice.getString(UCS.TITLE);
					String id = mservice.getString("id");
					FatherServe flag = new FatherServe();
					flag.setId(id);
					flag.setTitle(title);
					titles.add(flag);
					JSONArray subs = mservice.getJSONArray("item");
					subList = new ArrayList<SubServe>();
					for (int j = 0; j < subs.length(); j++) {
						Gson gson = new Gson();

						subService = new SubServe();
						JSONObject msub = subs.getJSONObject(j);
						subService = gson.fromJson(msub.toString(),
								SubServe.class);
						subList.add(subService); // 将子项目存储到集合中

					}
					lists.add(subList);// 存储所有 子项目
				}

			} else {
				String msg = dates.getString(UCS.RS_MSG);
				ToastUtils.defaultErrorImageToast(getApplicationContext(), msg);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!titles.isEmpty() && !lists.isEmpty()) {
			gooerderbtn.setVisibility(View.VISIBLE);
			adapter = new ExpendLvBeautyAdapter(getApplicationContext(), lists,
					titles);
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
				intent.putExtra(UCS.SID, sid);
				intent.putExtra(UCS.NAME, name);
				bundle.putSerializable("map", (Serializable) itemMap);
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
			subtitle.setTextColor(Color.BLACK);
		} else {
			SubServe service = lists.get(groupposition).get(childposition);
			service.setFlag("1");
			itemMap.put(subtitlename,
					lists.get(groupposition).get(childposition));
			isselect.setChecked(true);
			subtitle.setTextColor(Color.BLUE);
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

	private int the_group_expand_position,position_child_count;
	private boolean isExpanding;
	public void onGroupExpand(int groupposition) {
		// TODO Auto-generated method stub
		the_group_expand_position = groupposition;
		position_child_count = lists.get(groupposition).size();
		// if(linear.getVisibility()==View.GONE){
		// linear.setVisibility(View.VISIBLE);
		// }
		isExpanding = true;
		//adapter.notifyDataSetChanged();

		//this.notify();
	}

	@Override
	public void onGroupCollapse(int groupposition) {
		// TODO Auto-generated method stub
		isExpanding = false;
		//adapter.notifyDataSetChanged();
		//this.notify();
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int firstVisibleItem) {

		if (isExpanding) {
			if (firstVisibleItem <= the_group_expand_position
					|| firstVisibleItem > (the_group_expand_position + position_child_count)) {
				System.out.println(firstVisibleItem
						+ "******************");
				System.out.println(the_group_expand_position
						+ "******************111111");
			} else {
//			String title=	titles.get(the_group_expand_position).getTitle();
//				expendtitle.setText(((Map) parentData
//						.get(the_group_expand_position)).get("parend")
//						.toString());

				
			}
		}
	
		
	}

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
