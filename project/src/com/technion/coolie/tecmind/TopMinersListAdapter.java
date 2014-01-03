package com.technion.coolie.tecmind;

import java.util.List;

import com.technion.coolie.R;
import com.technion.coolie.tecmind.BL.Title;
import com.technion.coolie.tecmind.server.TecUser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TopMinersListAdapter extends BaseAdapter {
	private Context mContext;
	private List<TecUser> miners;

	public TopMinersListAdapter(Context context, List<TecUser> item) {
		mContext = context;
		miners = item;
	}

	@Override
	public int getCount() {
		return miners.size();
	}

	@Override
	public Object getItem(int position) {
		return miners.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class HoldView {
		TextView userName;
		TextView techionsValue;
		TextView title;
		ImageView titleIcon;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		HoldView holder = null;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			view = inflater.inflate(R.layout.techmind_top_post_list_view, null);
			holder = new HoldView();
			holder.userName = (TextView) view
					.findViewById(R.id.user_name);
			holder.techionsValue = (TextView) view.findViewById(R.id.techions_value);
			holder.title = (TextView) view
					.findViewById(R.id.title);
			holder.titleIcon = (ImageView) view.findViewById(R.id.title_icon);
			view.setTag(holder);

		} else {
			view = convertView;
			holder = (HoldView) view.getTag();
		}
		
		TecUser user = (TecUser) getItem(position);
		String title = user.getTitle().toString();
		Title enumTitle = Title.valueOf(title);
		holder.userName.setText(user.getName());
		holder.title.setText(title);
		holder.techionsValue.setText("Mine " + String.valueOf(user.getTotalTechoins() + "  Techions!"));
		
		if (enumTitle.equals(Title.ATUDAI)){
			holder.titleIcon.setImageResource(R.drawable.techmind_atudai);
		}else if (enumTitle.equals(Title.NERD)){
			holder.titleIcon.setImageResource(R.drawable.techmind_cool_nerd);
		}else if(enumTitle.equals(Title.KNIGHT_NERD)){
			holder.titleIcon.setImageResource(R.drawable.techmind_solider);
		}else {
			holder.titleIcon.setImageResource(R.drawable.techmind_super_nerd);
		}
		
		return view;
	}
}
