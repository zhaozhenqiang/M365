package zhongxin.m365.adapter.older;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import zhongxin.m365.R;
import zhongxin.m365.bean.older.OrderCar;

/**
 * 名称下拉菜单  适配器
 * 
 * @author zzq
 * 
 */
public class CardNumberAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<OrderCar> runPacks;

	public CardNumberAdapter(Context context,
			ArrayList<OrderCar> runPacks) {
		this.context = context;
		this.runPacks = runPacks;
	}

	@Override
	public int getCount() {
		return runPacks.size();
	}

	@Override
	public Object getItem(int position) {
		return runPacks.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_cardnumber, null);
		//}
		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView dian = (TextView) convertView.findViewById(R.id.dian);
		ImageView ima = (ImageView) convertView.findViewById(R.id.select);
		ima.setVisibility(View.INVISIBLE);
		name.setTextColor(convertView.getResources().getColor(R.color.TextColordark));
		dian.setTextColor(convertView.getResources().getColor(R.color.TextColordark));

		name.setText(runPacks.get(position).getPlate_num());
		if(runPacks.get(position).getSelected()!=null){
			name.setTextColor(convertView.getResources().getColor(R.color.orange));
			dian.setTextColor(convertView.getResources().getColor(R.color.orange));
			ima.setVisibility(View.VISIBLE);
		}
		return convertView;
	}
}
