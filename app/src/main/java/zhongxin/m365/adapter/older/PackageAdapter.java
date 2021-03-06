package zhongxin.m365.adapter.older;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zhongxin.m365.R;
import zhongxin.m365.bean.older.PackageChildItem;
import zhongxin.m365.bean.older.PackageItem;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PackageAdapter extends BaseExpandableListAdapter {
	private Context mContext;
	private ArrayList<ArrayList<PackageChildItem>> mList;// 存储 服务项目类别，及该类别下的子项目
	private List<PackageItem> mtitles;// 存储标题

	// 存储选中的套餐项目，是用个重量级的对象组，还是用两个数组，或者是二维数组，两个字符串？？？
	int[] id=new int[20];
	int[] number=new int[20];
	String idString;
	String numbString;
	public Map<String, PackageChildItem> itemMap = new HashMap<String, PackageChildItem>();// 存储选中的Item内的服务

	
	
	public PackageAdapter(Context mContext,
			ArrayList<ArrayList<PackageChildItem>> mList, List<PackageItem> mtitles) {
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
			number.setText(""+service.getNumber());
			totalnum.setText(""+service.getTotal_times()+"次");
			
			if(service.getNumber()>0){
				subtitle.setTextColor(mContext.getResources().getColor(R.color.orange));
				totalnum.setTextColor(mContext.getResources().getColor(R.color.orange));
				sprice.setTextColor(mContext.getResources().getColor(R.color.orange));
				numbertext.setTextColor(mContext.getResources().getColor(R.color.orange));
				number.setTextColor(mContext.getResources().getColor(R.color.orange));
				//lprice.setTextColor(mContext.getResources().getColor(R.color.orange));
				number.setBackgroundResource(R.drawable.selecte_02);
			//	lprice.setBackgroundResource(R.drawable.selecte_02);
				//lprice.setTextColor(Color.BLUE);
				itemMap.put(service.getService_id(), service);//将数量大于零的服务存进map
				
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
				itemMap.remove(service.getService_id());//将数不大于零的服务取出map
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
	                	
	                	int num = Integer.parseInt(s.toString());
	                	if(num == 0){
	                		mList.get(groupposition).get(childposition).setNumber(num);
	                	}
	                	else{
	        				subtitle.setTextColor(mContext.getResources().getColor(R.color.orange));
	        				totalnum.setTextColor(mContext.getResources().getColor(R.color.orange));
	        				sprice.setTextColor(mContext.getResources().getColor(R.color.orange));
	        				numbertext.setTextColor(mContext.getResources().getColor(R.color.orange));
	        				number.setTextColor(mContext.getResources().getColor(R.color.orange));
	        				//lprice.setTextColor(mContext.getResources().getColor(R.color.orange));
	        				number.setBackgroundResource(R.drawable.selecte_02);
	        			//	lprice.setBackgroundResource(R.drawable.selecte_02);
	                		
	                		mList.get(groupposition).get(childposition).setNumber(num);
	                		itemMap.put(mList.get(groupposition).get(childposition).getService_id(), mList.get(groupposition).get(childposition));//将数量大于零的服务存进map
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
