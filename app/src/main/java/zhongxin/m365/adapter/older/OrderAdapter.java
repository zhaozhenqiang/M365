package zhongxin.m365.adapter.older;

import java.util.ArrayList;

import zhongxin.m365.R;
import zhongxin.m365.bean.older.OrderItem;
import zhongxin.m365.constant.UCS;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class OrderAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<OrderItem> order;
	private String uid;

	public OrderAdapter(Context context,
			ArrayList<OrderItem> order) {
		this.context = context;
		this.order = order;
		uid = this.context.getSharedPreferences(UCS.USERINFO,
				Activity.MODE_PRIVATE).getString(UCS.ID, "");
	}

	@Override
	public int getCount() {
		//	return 5;
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
					R.layout.item_order, null);
			//convertView.setTag(R.layout.item_order, holder);
		}

		TextView name = (TextView) convertView
				.findViewById(R.id.name);
		TextView handle = (TextView) convertView
				.findViewById(R.id.tohandle);
		TextView time = (TextView) convertView
				.findViewById(R.id.time);
		TextView car = (TextView) convertView.findViewById(R.id.cardnumber);
		TextView content = (TextView) convertView
				.findViewById(R.id.content);
		
		name.setText(order.get(position).getNickname());
		time.setText(order.get(position).getNd_time());
		car.setText(order.get(position).getPlate_num());
		content.setText(order.get(position).getMo_str());
		//是否已经处理od_line 1:已下单；2：已处理；3：已取消
		if( !order.get(position).getOd_line().equals("1")){
			handle.setVisibility(View.GONE);
		}/*
	*//*handle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					Intent intent = new Intent(context,
							ConserveFavorableUpdateDetails.class);
					intent.putExtra(UCS.ID, conserveFavorables.get(position)
							.getId());
					intent.putExtra(UCS.TITLE, conserveFavorables.get(position)
							.getName());
					context.startActivity(intent);
			}
		});
		*/
		
		handle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, ""+position, Toast.LENGTH_LONG).show();
				//tohandle.setText("点中了	"+this.getItemId());
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
