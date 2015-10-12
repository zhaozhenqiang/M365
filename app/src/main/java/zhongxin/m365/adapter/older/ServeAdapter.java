package zhongxin.m365.adapter.older;

import java.util.ArrayList;

import zhongxin.m365.R;
import zhongxin.m365.activity.older.ServeDetailActivity;
import zhongxin.m365.bean.older.ServeItem;
import zhongxin.m365.constant.UCS;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ServeAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<ServeItem> order;
	private String uid;

	public ServeAdapter(Context context,
			ArrayList<ServeItem> order) {
		this.context = context;
		this.order = order;
		uid = this.context.getSharedPreferences(UCS.USERINFO,
				Activity.MODE_PRIVATE).getString(UCS.ID, "");
	}

	@Override
	public int getCount() {
	//return ;
		return order.size();
	}

	@Override
	public Object getItem(int position) {
		return order.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.item_serve, null);
			//convertView.setTag(R.layout.item_order, holder);
		}
		TextView name = (TextView) convertView
				.findViewById(R.id.name);
		TextView time = (TextView) convertView
				.findViewById(R.id.time);
		TextView car = (TextView) convertView.findViewById(R.id.cardnumber);
		TextView content = (TextView) convertView
				.findViewById(R.id.content);
		TextView pay_state = (TextView) convertView
				.findViewById(R.id.pay_state);
		
		name.setText(order.get(position).getNickname());
		time.setText(order.get(position).getOd_state_cn());
		car.setText(order.get(position).getPlate_num());
		content.setText(order.get(position).getMo_str());
		pay_state.setText(order.get(position).getPay_state_cn());
		TextView tohandle = (TextView) convertView.findViewById(R.id.tohandle);
		tohandle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						ServeDetailActivity.class);
				intent.putExtra(UCS.ID, order.get(position)
						.getId());
				/*				intent.putExtra(UCS.TITLE, conserveFavorables.get(position)
						.getName());*/
				context.startActivity(intent);				//tohandle.setText("点中了	"+this.getItemId());
/*					Intent intent = new Intent(context,
							ConserveFavorableUpdateDetails.class);
					intent.putExtra(UCS.ID, conserveFavorables.get(position)
							.getId());
					intent.putExtra(UCS.TITLE, conserveFavorables.get(position)
							.getName());
					context.startActivity(intent);*/
			}
		});
		/*if (!("".equals(conserveFavorables.get(position).getImg_url()) || conserveFavorables
				.get(position).getImg_url() == null)) {
			BitmapUtils bitmapUtils = new BitmapUtils(context);
			// 加载网络图片
			bitmapUtils.display(listview_imageview,
					UCS.IMAGECOMMON+conserveFavorables.get(position).getImg_url());
		}else{
			
		}*/
		return convertView;
	}

}
