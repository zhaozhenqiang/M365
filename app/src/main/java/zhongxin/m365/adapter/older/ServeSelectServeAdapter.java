package zhongxin.m365.adapter.older;

import java.util.ArrayList;
import java.util.List;

import zhongxin.m365.R;
import zhongxin.m365.bean.older.FatherServe;
import zhongxin.m365.bean.older.SubServe;

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

public class ServeSelectServeAdapter extends BaseExpandableListAdapter {
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
		//final ChildHolderView childHolderView;
		//if (convertview != null) {
			//childHolderView = (ChildHolderView) convertview.getTag();
		//} else {
			//childHolderView = new ChildHolderView();
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
			sprice.setText(service.getPrice());
			number.setText(""+service.getNumber());
			lprice.setText(""+service.getFavorable());
			
			if(service.getNumber()>0){
				subtitle.setTextColor(mContext.getResources().getColor(R.color.orange));
				lpricetext.setTextColor(mContext.getResources().getColor(R.color.orange));
				sprice.setTextColor(mContext.getResources().getColor(R.color.orange));
				numbertext.setTextColor(mContext.getResources().getColor(R.color.orange));
				number.setTextColor(mContext.getResources().getColor(R.color.orange));
				lprice.setTextColor(mContext.getResources().getColor(R.color.orange));
				number.setBackgroundResource(R.drawable.selecte_02);
				lprice.setBackgroundResource(R.drawable.selecte_02);
				//lprice.setTextColor(Color.BLUE);
				
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
	                	
	                	int num = Integer.parseInt(s.toString());
	                	if(num == 0){
	                		
	                		
	                		
	                		
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
