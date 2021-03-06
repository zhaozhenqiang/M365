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
import zhongxin.m365.bean.older.FatherServe;
import zhongxin.m365.bean.older.SubServe;
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
public class ServeSelectServeActivity extends BaseActivity implements
		OnGroupClickListener, OnClickListener{
	private String tag = this.getClass().getSimpleName();
/*	private String sid;// 门店sid
	private List<SubServe> subList;
	private SubServe subService;*/
	private List<FatherServe> fatherList;// 存储标题
	private TextView basetitle;// 选择服务类别
	private Button gooerderbtn;// 去预约
	private ServeSelectServeAdapter adapter;
	// 存储选中的美容服务项目
	private Map<String, SubServe> itemMap = new HashMap<String, SubServe>();// 存储选中的Item内的服务
	// 存储美容服务项目 LisT<List<>>
	private ArrayList<ArrayList<SubServe>> lists;
	private ExpandableListView expandableListView;
	private LinearLayout ishasresult;
   // private String name;
    String[] intentId = null;////来回传递的值
    int[] intentNum=null;
    int[] intentFavor=null;
    
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
boolean bigCar = true;
	public void getintentdate() {
		// TODO Auto-generated method stub
		String carsizeValue = getIntent().getExtras().getString("carsizeValue");

		if(carsizeValue.contains("小车")){
			bigCar=false;
		}

		if (getIntent().getExtras() != null&&!getIntent().getExtras().getString("serveNum").equals("")) {
			String serveNum = getIntent().getExtras().getString("serveNum");
			String serveId = getIntent().getExtras().getString("serveId");
			String serveFavor = getIntent().getExtras().getString("serveFavor");

			intentId = serveId.split(",");// 重进入的加载！！！
			String[] intentNums = serveNum.split(",");
			String[] intentFavors = serveFavor.split(",");
			intentNum = new int[intentNums.length];
			intentFavor = new int[intentFavors.length];
			for (int i = 0; i < intentNums.length; i++) {
				intentNum[i] = Integer.parseInt(intentNums[i]);
				intentFavor[i] = Integer.parseInt(intentFavors[i]);
			}
		}
	}

	private void getStoreService() {
		
		//cust_id	
		SharedPreferences preferences = getSharedPreferences(UCS.USERINFO,
				Activity.MODE_PRIVATE);
		String cust_id = preferences.getString("cust_id", null);
		String url = UCS.URLCOMMON + "order/index/getserviceitem";
		String keys[] = {"cust_id"};
		String values[] = {cust_id};
		HttpUtils.upload(ServeSelectServeActivity.this, url, keys, values,
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
					for(int x=0;x<intentId.length;x++){
						for(int i=0;i<lists.size();i++){
							for(int j=0;j<lists.get(i).size();j++){
								if(lists.get(i).get(j).getId().equals(intentId[x])){
									lists.get(i).get(j).setNumber(intentNum[x]);
									lists.get(i).get(j).setFavorable(intentFavor[x]);
									itemMap.put(lists.get(i).get(j).getId(),
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
			adapter = new ServeSelectServeAdapter(getApplicationContext(), lists,
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
				String id="";
				String price="";
				String s_price="";
				String b_price="";
				double all_money=0;
				String serveFavor="";
				String is_size="";
				for(Map.Entry<String, SubServe> entry: itemMap.entrySet()){
					number+=(entry.getValue().getNumber()+",");
					text += (entry.getValue().getName()+" ");
					id+=(entry.getValue().getId()+",");
					price += (entry.getValue().getPrice()+",");
					b_price += (entry.getValue().getBvehicle_price()+",");
					s_price += (entry.getValue().getSvehicle_price()+",");
					serveFavor += (entry.getValue().getFavorable()+",");
					if(entry.getValue().getPrice()==null||entry.getValue().getPrice().equals("")){
						entry.getValue().setPrice("0");
					}
					System.out.println(entry.getValue()+"***********"+entry.getValue().getPrice()+"********"+entry.getValue().getFavorable());
					double priceFlag ;
					if(entry.getValue().getIs_carsize().equals("2")){
						priceFlag = Double.valueOf(entry.getValue().getPrice());
					}else{
						if(bigCar){
							priceFlag=Double.valueOf(entry.getValue().getBvehicle_price());
						}
						else{
							priceFlag=Double.valueOf(entry.getValue().getSvehicle_price());
						}
					}
					
					
					int favorable = entry.getValue().getFavorable();
					all_money += priceFlag*entry.getValue().getNumber()-favorable;
					//(entry.getValue().getNumber()-);
					is_size+=(entry.getValue().getIs_carsize()+",");//
				}
				//bundle.putSerializable("map", (Serializable) itemMap);
				bundle.putString("textSelect", text);
				bundle.putString("numberSelect", number);
				bundle.putString("idSelect", id);
				bundle.putString("favorSelect", serveFavor);
				
				bundle.putString("price", price);
				bundle.putString("b_price", b_price);
				bundle.putString("s_price", s_price);
				bundle.putString("all_money", ""+all_money);
				System.out.println("***************"+all_money);
				bundle.putString("is_size", is_size);
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
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		getStoreService();
	}
	//控制是否在头部显示 所打开的美容项目分类
/*
	private int the_group_expand_position,position_child_count;
	private boolean isExpanding;
	public void onGroupExpand(int groupposition) {
		the_group_expand_position = groupposition;
		position_child_count = lists.get(groupposition).size();
		isExpanding = true;
	}*/
	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		ImageView arror = (ImageView) v.findViewById(R.id.arror);
		if(parent.isGroupExpanded(groupPosition)){
			arror.setBackgroundResource(R.drawable.arrow_d);
		}
		else{
			arror.setBackgroundResource(R.drawable.arrow_u);
		}
		return false;
	}
	
	
	class ServeSelectServeAdapter extends BaseExpandableListAdapter {
		private Context mContext;
		private ArrayList<ArrayList<SubServe>> mList;// 存储 服务项目类别，及该类别下的子项目
		private List<FatherServe> mtitles;// 存储标题
		public ServeSelectServeAdapter(Context mContext,
				ArrayList<ArrayList<SubServe>> mList, List<FatherServe> mtitles) {
			super();
			this.mContext = mContext;
			this.mList = mList;
			this.mtitles = mtitles;
		}

		public Object getChild(int groupposition, int childposition) {
			// TODO Auto-generated method stub
			return mList.get(groupposition).get(childposition);
		}

		@Override
		public long getChildId(int groupposition, int childposition) {
			// TODO Auto-generated method stub
			return childposition;
		}

		@Override
		public int getChildrenCount(int groupposition) {
			// TODO Auto-generated method stub
			return mList.get(groupposition).size();
		}

		@Override
		public View getChildView(final int groupposition, final int childposition,
				boolean isLastChild, View convertview, ViewGroup parent) {
				convertview = LayoutInflater.from(mContext).inflate(R.layout.item_serve_selectserve, null);
				final TextView subtitle = (TextView) convertview.findViewById(R.id.subtitl);
				final TextView lpricetext = (TextView) convertview.findViewById(R.id.lpricetext);
				final TextView sprice = (TextView) convertview.findViewById(R.id.sprice);
				final TextView numbertext = (TextView) convertview.findViewById(R.id.numbertext);
				//convertview.setTag(childHolderView);
				final EditText number = (EditText) convertview.findViewById(R.id.number);
				final EditText lprice = (EditText) convertview.findViewById(R.id.lprice);
				SubServe service = mList.get(groupposition).get(childposition);
				subtitle.setText(service.getName());
				if(service.getIs_carsize().equals("2")){
					sprice.setText(service.getPrice());
				}else{
					if(bigCar){
						sprice.setText(service.getBvehicle_price());
					}else{
						sprice.setText(service.getSvehicle_price());
					}
				}
				if(service.getNumber()>0){
					number.setText(""+service.getNumber());
					lprice.setText(""+service.getFavorable());
					subtitle.setTextColor(mContext.getResources().getColor(R.color.orange));
					lpricetext.setTextColor(mContext.getResources().getColor(R.color.orange));
					sprice.setTextColor(mContext.getResources().getColor(R.color.orange));
					numbertext.setTextColor(mContext.getResources().getColor(R.color.orange));
					number.setTextColor(mContext.getResources().getColor(R.color.orange));
					lprice.setTextColor(mContext.getResources().getColor(R.color.orange));
					number.setBackgroundResource(R.drawable.selecte_02);
					lprice.setBackgroundResource(R.drawable.selecte_02);
				}
				else{
					subtitle.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
					lpricetext.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
					sprice.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
					numbertext.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
					number.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
					lprice.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
					number.setBackgroundResource(R.drawable.n_selecte);
					lprice.setBackgroundResource(R.drawable.n_selecte);
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
		                	itemMap.remove(mList.get(groupposition).get(childposition).getId());
		                	subtitle.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
		                	lpricetext.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
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
		                		lpricetext.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
								sprice.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
								numbertext.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
								number.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
								//lprice.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
								number.setBackgroundResource(R.drawable.n_selecte);
		                		mList.get(groupposition).get(childposition).setNumber(num);
		                		itemMap.remove(mList.get(groupposition).get(childposition).getId());
		                	}
		                	else{
		                		subtitle.setTextColor(mContext.getResources().getColor(R.color.orange));
		        				lpricetext.setTextColor(mContext.getResources().getColor(R.color.orange));
		        				sprice.setTextColor(mContext.getResources().getColor(R.color.orange));
		        				numbertext.setTextColor(mContext.getResources().getColor(R.color.orange));
		        				number.setTextColor(mContext.getResources().getColor(R.color.orange));
		        				lprice.setTextColor(mContext.getResources().getColor(R.color.orange));
		        				number.setBackgroundResource(R.drawable.selecte_02);
		        				lprice.setBackgroundResource(R.drawable.selecte_02);
		                		mList.get(groupposition).get(childposition).setNumber(num);
		                		itemMap.put(mList.get(groupposition).get(childposition).getId(), mList.get(groupposition).get(childposition));//将数量大于零的服务存进map
		                	//往map里放
		                	}
		                }
		            });  
				
					lprice.addTextChangedListener(new TextWatcher() {  
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
		        						"优惠必须为大于等于零的正数！");
		                	return;	
		                	}
		                	
		                	else{
		                		num = Integer.parseInt(s.toString());
		                	}
		                	String spriceFlag;
		    				if(mList.get(groupposition).get(childposition).getIs_carsize().equals("2")){
		    					spriceFlag= mList.get(groupposition).get(childposition).getPrice();
		    				}else{
		    					if(bigCar){
		    						spriceFlag= mList.get(groupposition).get(childposition).getBvehicle_price();
		    					}
		    					spriceFlag= mList.get(groupposition).get(childposition).getSvehicle_price();
		    				}
		    				double priceFlag = Double.parseDouble(spriceFlag); 
		    				//if() 改写判断了
		                	if(num>priceFlag){
			                	ToastUtils.defaultErrorImageToast(getApplicationContext(),
			        						"优惠力度不能大于原价！");
			                	lprice.setText("");
			                	return;
		                	}
		                	
		                		mList.get(groupposition).get(childposition).setFavorable(num);
		                }
		            });  //悠悠快哉，安然乐哉
					
				
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
			// TODO Auto-generated method stub
			return mList.get(groupposition);
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		@Override
		public long getGroupId(int groupposition) {
			// TODO Auto-generated method stub
			return groupposition;
		}

		@Override
		public View getGroupView(int groupposition, boolean isExpanded,
				View convertview, ViewGroup parent) {
			GroupHolderView groupHolderView;
			//if (convertview != null) {
			//	groupHolderView = (GroupHolderView) convertview.getTag();
			//} else {
				groupHolderView = new GroupHolderView();
				convertview = LayoutInflater.from(mContext).inflate(
						R.layout.item_group_onetitle, null);
				groupHolderView.title = (TextView) convertview
						.findViewById(R.id.title);
				convertview.setTag(groupHolderView);
				/*	ImageView arror = (ImageView) convertview.findViewById(R.id.arror);
				 if(isExpanded){
					 arror.setBackgroundResource(R.drawable.arrow_u);
		            }else{
		            	arror.setBackgroundResource(R.drawable.arrow_d);
		            }*/
	/*			LinearLayout parentLayout = (LinearLayout) convertview
						.findViewById(R.id.group);*/

	/*			groupHolderView.parentImageViw = (ImageView) parentLayout
						.findViewById(R.id.arror);*/

				// 判断isExpanded就可以控制是按下还是关闭，同时更换图片
	/*			if (isExpanded) {
					parentLayout.setBackgroundResource(R.drawable.btnstylewhite);
					System.out.println("开闭是否有判断执行呢？？？？？这是第一个");
					groupHolderView.parentImageViw
							.setBackgroundResource(R.drawable.arrow_u);
				} else {
					parentLayout.setBackgroundResource(R.drawable.arrow_d);
					System.out.println("开闭是否有判断执行呢？？？？？这是第二个");

					groupHolderView.parentImageViw
							.setBackgroundResource(R.drawable.arrow_d);
				}*/

			//}
			groupHolderView.title.setText(mtitles.get(groupposition).getTitle());
		//	}
			return convertview;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			// TODO Auto-generated method stub
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
