package zhongxin.m365.activity.older;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import zhongxin.m365.R;
import zhongxin.m365.activity.BaseActivity;
import zhongxin.m365.bean.older.PackageChildItem;
import zhongxin.m365.bean.older.PackageItem;
import zhongxin.m365.constant.UCS;
import zhongxin.m365.utils.HttpUtils;
import zhongxin.m365.utils.HttpUtils.BackJson;
import zhongxin.m365.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * 单个门店美容服务 详情
 */
public class ServeSelectPackageActivity extends BaseActivity implements
		OnGroupClickListener, OnClickListener{
	private String tag = this.getClass().getSimpleName();
/*	private String sid;// 门店sid
	private List<SubServe> subList;
	private PackageChildItem subService;*/
	private List<PackageItem> fatherList;// 存储标题
	private TextView basetitle;// 选择服务类别
	private Button gooerderbtn;// 去预约
	private PackageAdapter adapter;
	// 存储选中的美容服务项目
	private Map<String, PackageChildItem> itemMap = new HashMap<String, PackageChildItem>();// 存储选中的Item内的服务
	// 存储美容服务项目 LisT<List<>>
	private ArrayList<ArrayList<PackageChildItem>> lists;
	private ExpandableListView expandableListView;
	private LinearLayout ishasresult;
   // private String name;
    String[] intentId = null;////来回传递的值
    int[] intentNum=null;
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
		basetitle.setText("新建服务工单");
	
		gooerderbtn = (Button) findViewById(R.id.gooerderbtn);
		gooerderbtn.setOnClickListener(this);
		expandableListView = (ExpandableListView) findViewById(R.id.expendlistview);
		expandableListView.setGroupIndicator(null);//去掉右侧的收缩按钮！！！！
		//expandableListView.setOnChildClickListener(this);// 子listView点击事件
		// expandableListView.setGroupIndicator(this.getResources().getDrawable(R.drawable.expendlistview_expandandfold));
		expandableListView.setOnGroupClickListener(this);//新添加监听事件，，，组按钮点击事件                    xq
		ishasresult = (LinearLayout) findViewById(R.id.ishasresult);
		gooerderbtn.setVisibility(View.INVISIBLE);// 设置不可见 （获取到数据后 设置可见）
	}

	public void getintentdate() {
		if (getIntent().getExtras() != null) {
			String serveValue = getIntent().getExtras().getString("packageId");
			String packageNum = getIntent().getExtras().getString("packageNum");
			intentId = serveValue.split(",");//重进入的加载！！！
			String[] intentNums = packageNum.split(",");
			intentNum = new int[intentNums.length];
			for(int i=0;i<intentNums.length;i++){
				if(intentNums[i]==null||intentNums[i].equals("")){
					//intentNum[i]=0;
				}else{
					intentNum[i]=Integer.parseInt(intentNums[i]);
				}
					
			}
			
		}
	}

	private void getStoreService() {
		
		//cust_id	
		SharedPreferences preferences = getSharedPreferences(UCS.USERINFO,
				Activity.MODE_PRIVATE);
		String mobile = preferences.getString("mobile", null);
		String url = UCS.URLCOMMON + "order/ods/getmembercomboinfo";
		String keys[] = {"mobile"};//http://192.168.0.210:90/v2/app/carpark/erp-business/
		String values[] = {mobile};
		HttpUtils.upload(ServeSelectPackageActivity.this, url, keys, values,
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
			lists = new ArrayList<ArrayList<PackageChildItem>>();
			dates = new JSONObject(json);
			fatherList = new ArrayList<PackageItem>();
			if (dates.getString("code").equals("1")) {
				JSONArray data = dates.getJSONArray(UCS.DATA);
				//JSONArray data = mJson.getJSONArray(UCS.DATA);
				Gson gson = new Gson();
				
				//JSONArray item = data.getJSONArray("item");
				fatherList = gson.fromJson(data.toString(),
						new TypeToken<ArrayList<PackageItem>>() {
						}.getType());
				System.out.println(fatherList.size());
				for(int i=0;i<fatherList.size();i++){
					lists.add(fatherList.get(i).getItems());
				}
				if(intentId !=null){///这一段转入转出的代码多加点注释绝不为过啊！！！
					for(int x=0;x<intentId.length;x++){
						for(int i=0;i<lists.size();i++){
							for(int j=0;j<lists.get(i).size();j++){
								if(lists.get(i).get(j).getAss_id().equals(intentId[x])){
									lists.get(i).get(j).setNumber(intentNum[x]);
									itemMap.put(lists.get(i).get(j).getAss_id(),
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
			adapter = new PackageAdapter(getApplicationContext(), lists,
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
			/*if (itemMap.isEmpty()) {
				ToastUtils.defaultErrorImageToast(getApplicationContext(),
						"请选择服务项目");
			} else {*/
				/*Intent intent = new Intent(getApplicationContext(),
						SuitBeautyCarOrderActivity.class);*/
	            Intent intent=new Intent();  

				Bundle bundle = new Bundle();
				String text="";
				String number="";
				String id="";
				for(Entry<String, PackageChildItem> entry: itemMap.entrySet()){
					id+=(entry.getValue().getAss_id()+",");
					text += (entry.getValue().getServ_name()+" ");
					number += (entry.getValue().getNumber()+",");//肯定会多传一组有关数目的参数
				}
				System.out.println(number+"************"+text+"************"+id);
				//bundle.putSerializable("map", (Serializable) itemMap);
				bundle.putString("textSelect", text);
				bundle.putString("numberSelect", number);
				bundle.putString("idSelect", id);
				intent.putExtras(bundle);
                setResult(RESULT_OK, intent);  
                finish();  
		//	}
			break;
		case R.id.back:// 返回
			finish();
			break;
		}
	}

	/*@Override
	public boolean onChildClick(ExpandableListView exlistview, View childview,
			int groupposition, int childposition, long childid) {
		TextView subtitle = (TextView) childview.findViewById(R.id.subtitl);
		String subtitlename = subtitle.getText().toString();
		CheckBox isselect = (CheckBox) childview.findViewById(R.id.isselcet);
		if (isselect.isChecked()) {
			PackageChildItem service = lists.get(groupposition).get(childposition);
			service.setFlag("2");
			itemMap.remove(subtitlename);
			isselect.setChecked(false);
			//subtitle.setTextColor(Color.BLACK);TextColordarker
			//subtitle.setTextColor(getResources().getColor(R.color.TextColordarker));
		} else {
			PackageChildItem service = lists.get(groupposition).get(childposition);
			service.setFlag("1");
			itemMap.put(subtitlename,
					lists.get(groupposition).get(childposition));
			isselect.setChecked(true);
			//subtitle.setTextColor(getResources().getColor(R.color.orange));
		}
		adapter.notifyDataSetChanged();// 刷新数据
		return true;
	}
*/
	@Override
	protected void onRestart() {
		super.onRestart();
		getStoreService();
	}
	//控制是否在头部显示 所打开的美容项目分类
/*
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
	}*/
	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		ImageView arror = (ImageView) v.findViewById(R.id.arror);
/*		if (arror.getBackground().equals(R.drawable.arrow_u)){
			arror.setBackgroundResource(R.drawable.arrow_d);
			adapter.notifyDataSetChanged();
		}
		else{
			arror.setBackgroundResource(R.drawable.arrow_u);
			adapter.notifyDataSetChanged();
		}*/
		if(parent.isGroupExpanded(groupPosition)){
			arror.setBackgroundResource(R.drawable.arrow_d);
		}
		else{
			arror.setBackgroundResource(R.drawable.arrow_u);
		}
		return false;
	}
	
	 class PackageAdapter extends BaseExpandableListAdapter {
			private Context mContext;
			private ArrayList<ArrayList<PackageChildItem>> mList;// 存储 服务项目类别，及该类别下的子项目
			private List<PackageItem> mtitles;// 存储标题

			// 存储选中的套餐项目，是用个重量级的对象组，还是用两个数组，或者是二维数组，两个字符串？？？
			int[] id=new int[20];
			int[] number=new int[20];
			String idString;
			String numbString;
			
			public PackageAdapter(Context mContext,
					ArrayList<ArrayList<PackageChildItem>> mList, List<PackageItem> mtitles) {
				super();
				this.mContext = mContext;
				this.mList = mList;
				this.mtitles = mtitles;
			}

			public Object getChild(int groupposition, int childposition) {
				return mList.get(groupposition).get(childposition);
			}

			@Override
			public long getChildId(int groupposition, int childposition) {
				return childposition;
			}

			@Override
			public int getChildrenCount(int groupposition) {
				return mList.get(groupposition).size();
			}

			@Override
			public View getChildView(final int groupposition, final int childposition,
					boolean isLastChild, View convertview, ViewGroup parent) {
				//final ChildHolderView childHolderView;
				//if (convertview != null) {
					//childHolderView = (ChildHolderView) convertview.getTag();
				//} else {
					//childHolderView = new ChildHolderView();
					convertview = LayoutInflater.from(mContext).inflate(R.layout.item_package, null);
					final TextView subtitle = (TextView) convertview.findViewById(R.id.subtitl);
					final TextView totalnum = (TextView) convertview.findViewById(R.id.totalnum);
					final TextView sprice = (TextView) convertview.findViewById(R.id.sprice);
					final TextView numbertext = (TextView) convertview.findViewById(R.id.numbertext);
					//convertview.setTag(childHolderView);
					final EditText number = (EditText) convertview.findViewById(R.id.number);
					
					PackageChildItem service = mList.get(groupposition).get(childposition);
					subtitle.setText(service.getServ_name());
					sprice.setText(service.getServ_price());
					totalnum.setText(""+service.getAss_baltimes()+"次");
					if(service.getNumber()>0){
						number.setText(""+service.getNumber());
						subtitle.setTextColor(mContext.getResources().getColor(R.color.orange));
						totalnum.setTextColor(mContext.getResources().getColor(R.color.orange));
						sprice.setTextColor(mContext.getResources().getColor(R.color.orange));
						numbertext.setTextColor(mContext.getResources().getColor(R.color.orange));
						number.setTextColor(mContext.getResources().getColor(R.color.orange));
						//lprice.setTextColor(mContext.getResources().getColor(R.color.orange));
						number.setBackgroundResource(R.drawable.selecte_02);
					//	lprice.setBackgroundResource(R.drawable.selecte_02);
						//lprice.setTextColor(Color.BLUE);
					//	itemMap.put(service.getService_id(), service);//将数量大于零的服务存进map
						
					}
					else{
						subtitle.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
						totalnum.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
						sprice.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
						numbertext.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
						number.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
						//lprice.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
						number.setBackgroundResource(R.drawable.n_selecte);
						//lprice.setBackgroundResource(R.drawable.n_selecte);
					//	itemMap.remove(service.getService_id());//将数不大于零的服务取出map
					}
						number.addTextChangedListener(new TextWatcher() {  
			                @Override  
			                public void onTextChanged(CharSequence s, int start, int before, int count) {  
			                      
			                }  
			                  
			                @Override  
			                public void beforeTextChanged(CharSequence s, int start,   
			                        int count,int after) {  
			                      
			                }  
			                  
			                @Override  
			                public void afterTextChanged(Editable s) {  
			                	boolean isNum = s.toString().matches("[0-9]+"); 
			                	int num;			                	
			                	if(!isNum){
			                	ToastUtils.defaultErrorImageToast(getApplicationContext(),
			        						"服务次数必须为大于等于零的正数！");
			                	mList.get(groupposition).get(childposition).setNumber(0);
			                	itemMap.remove(mList.get(groupposition).get(childposition).getAss_id());
			                	subtitle.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
								totalnum.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
								sprice.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
								numbertext.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
								number.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
								//lprice.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
								number.setBackgroundResource(R.drawable.n_selecte);
			                	return;	
			                	}
			                	else{
			                		num = Integer.parseInt(s.toString());
			                	}
			                	if(num == 0){
			                		subtitle.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
									totalnum.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
									sprice.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
									numbertext.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
									number.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
									//lprice.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
									number.setBackgroundResource(R.drawable.n_selecte);
			                		mList.get(groupposition).get(childposition).setNumber(num);
			                		itemMap.remove(mList.get(groupposition).get(childposition).getAss_id());
			                	}
			                	else{
			                		if(num>Integer.parseInt(mList.get(groupposition).get(childposition).getAss_baltimes())){
			                			ToastUtils.defaultErrorImageToast(getApplicationContext(),
				        						"服务次数不能大于剩余总次数！");
			                			number.setText("");
			                			return;
			                		}
			        				subtitle.setTextColor(mContext.getResources().getColor(R.color.orange));
			        				totalnum.setTextColor(mContext.getResources().getColor(R.color.orange));
			        				sprice.setTextColor(mContext.getResources().getColor(R.color.orange));
			        				numbertext.setTextColor(mContext.getResources().getColor(R.color.orange));
			        				number.setTextColor(mContext.getResources().getColor(R.color.orange));
			        				//lprice.setTextColor(mContext.getResources().getColor(R.color.orange));
			        				number.setBackgroundResource(R.drawable.selecte_02);
			        			//	lprice.setBackgroundResource(R.drawable.selecte_02);
			                		
			                		mList.get(groupposition).get(childposition).setNumber(num);
			                		itemMap.put(mList.get(groupposition).get(childposition).getAss_id(), mList.get(groupposition).get(childposition));//将数量大于零的服务存进map
			                	//往map里放
			                	}
			                    //将editText中改变的值设置的HashMap中  
			                   // hashMap.put(position, s.toString());  
			                }
			            });  
					
					
					// 怎么选出来
					if (isLastChild) {
						LinearLayout l = (LinearLayout) convertview
								.findViewById(R.id.back);
						l.setBackgroundResource(R.drawable.newreservationlist_02);
					}
				////////////////////////}

				//childHolderView.lprice.setText(service.getPrice_s());
				//TextView dian = (TextView) convertview.findViewById(R.id.dian);
				/*if (service.getFlag() != null && service.getFlag().equals("1")) {
				//	childHolderView.isselect.setChecked(true);
					//dian.setTextColor(mContext.getResources().getColor(R.color.orange));
					childHolderView.subtitle.setTextColor(mContext.getResources().getColor(R.color.orange));
				} else {
				//	childHolderView.isselect.setChecked(false);
					childHolderView.subtitle.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
					//dian.setTextColor(mContext.getResources().getColor(R.color.TextColordark));

				}*/
				return convertview;
			}

			@Override
			public Object getGroup(int groupposition) {
				return mList.get(groupposition);
			}

			@Override
			public int getGroupCount() {
				return mList.size();
			}

			@Override
			public long getGroupId(int groupposition) {
				return groupposition;
			}

			@Override
			public View getGroupView(int groupposition, boolean isExpanded,
					View convertview, ViewGroup parent) {
				GroupHolderView groupHolderView;
				if (convertview != null) {
					groupHolderView = (GroupHolderView) convertview.getTag();
				} else {
					groupHolderView = new GroupHolderView();
					convertview = LayoutInflater.from(mContext).inflate(R.layout.item_group_onetitle, null);
					groupHolderView.title = (TextView) convertview.findViewById(R.id.title);
					convertview.setTag(groupHolderView);
				groupHolderView.title.setText(mtitles.get(groupposition).getName());
				}
				return convertview;
			}

			@Override
			public boolean hasStableIds() {
				return true;
			}

			@Override
			public boolean isChildSelectable(int arg0, int arg1) {
				return true;
			}
		/*	class ChildHolderView {
				TextView sprice, lprice, subtitle,number;
				//CheckBox isselect;// 是否被选中
			}*/

			class GroupHolderView {
				TextView title;
				ImageView parentImageViw;
			}
		}
	
}
