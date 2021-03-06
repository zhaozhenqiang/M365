package zhongxin.m365.adapter.older;

import java.util.ArrayList;
import java.util.List;

import zhongxin.m365.R;
import zhongxin.m365.bean.older.FatherServe;
import zhongxin.m365.bean.older.SubServe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectServeAdapter extends BaseExpandableListAdapter {
	private Context mContext;
	private ArrayList<ArrayList<SubServe>> mList;// 存储 服务项目类别，及该类别下的子项目
	private List<FatherServe> mtitles;// 存储标题

	public SelectServeAdapter(Context mContext,
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
	public View getChildView(int groupposition, int childposition,
			boolean isLastChild, View convertview, ViewGroup parent) {
		ChildHolderView childHolderView;
		if (convertview != null) {
			childHolderView = (ChildHolderView) convertview.getTag();
		} else {
			childHolderView = new ChildHolderView();
			convertview = LayoutInflater.from(mContext).inflate(
					R.layout.item_order_selectserve, null);
			childHolderView.subtitle = (TextView) convertview
					.findViewById(R.id.subtitl);
			childHolderView.lprice = (TextView) convertview
					.findViewById(R.id.lprice);
			childHolderView.sprice = (TextView) convertview
					.findViewById(R.id.sprice);
			childHolderView.isselect = (CheckBox) convertview
					.findViewById(R.id.isselcet);
			convertview.setTag(childHolderView);
			
			// 怎么选出来
			if (isLastChild) {
				LinearLayout l = (LinearLayout) convertview
						.findViewById(R.id.back);
				l.setBackgroundResource(R.drawable.newreservationlist_02);
			}
		}
		SubServe service = mList.get(groupposition).get(childposition);
		childHolderView.subtitle.setText(service.getName());
		//childHolderView.sprice.setText(service.getPrice_b());
		//childHolderView.lprice.setText(service.getPrice_s());
		TextView dian = (TextView) convertview.findViewById(R.id.dian);
		if (service.getFlag() != null && service.getFlag().equals("1")) {
			childHolderView.isselect.setChecked(true);
			dian.setTextColor(mContext.getResources().getColor(R.color.orange));
			childHolderView.subtitle.setTextColor(mContext.getResources().getColor(R.color.orange));
		} else {
			childHolderView.isselect.setChecked(false);
			childHolderView.subtitle.setTextColor(mContext.getResources().getColor(R.color.TextColordark));
			dian.setTextColor(mContext.getResources().getColor(R.color.TextColordark));

		}
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
			convertview = LayoutInflater.from(mContext).inflate(
					R.layout.item_group_onetitle, null);
			groupHolderView.title = (TextView) convertview
					.findViewById(R.id.title);
			convertview.setTag(groupHolderView);

			LinearLayout parentLayout = (LinearLayout) convertview
					.findViewById(R.id.group);

			groupHolderView.parentImageViw = (ImageView) parentLayout
					.findViewById(R.id.arror);
		groupHolderView.title.setText(mtitles.get(groupposition).getTitle());
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
	/*
	@Override
   public void onGroupCollapsed (int groupPosition){
		GroupHolderView groupHolderView;
		groupHolderView = (GroupHolderView) this.getGroup(groupPosition);
		groupHolderView.parentImageViw = 
		groupHolderView.parentImageViw
					.setBackgroundResource(R.drawable.arrow_d);
		
   }

//当组收缩状态的时候此方法被调用。

   //    参数： groupPosition 收缩状态的组索引
	@Override

     public void onGroupExpanded(int groupPosition){
		GroupHolderView groupHolderView;
		
		groupHolderView = (GroupHolderView) this.getGroup(groupPosition);
		groupHolderView.parentImageViw
					.setBackgroundResource(R.drawable.arrow_d);
	}*/

	class ChildHolderView {
		TextView sprice, lprice, subtitle;
		CheckBox isselect;// 是否被选中
	}

	class GroupHolderView {
		TextView title;
		ImageView parentImageViw;
	}
}
