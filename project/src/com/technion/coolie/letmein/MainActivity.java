package com.technion.coolie.letmein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;

public class MainActivity extends CoolieActivity {

	private ListView inviteList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lmi_activity_main);

		inviteList = (ListView) findViewById(R.id.lmi_invite_list_view);

		inviteList.setAdapter(new MyAdapter(MainActivity.this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.lmi_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	private static class Invite {
		public String name;
		public String timeOfArrival;

		public Invite(String name, String timeOfArrival) {
			this.name = name;
			this.timeOfArrival = timeOfArrival;
		}
	}

	public static class MyAdapter extends BaseAdapter {

		private Context context;
		private List<Invite> invites = initInvites(); // TODO: change to the
														// real deal.

		private static List<Invite> initInvites() {
			List<Invite> invites = new ArrayList<Invite>();
			List<Invite> sample = Arrays.asList(
					new Invite("Osher", "Yesterday"), new Invite("Gilad",
							"Never"), new Invite("Yaniv", "Always"));

			for (int i = 0; i < 100; ++i) {
				invites.addAll(sample);
			}

			return invites;
		}

		public MyAdapter(Context context) {
			this.context = context;
		}

		private class ViewHolder {
			public TextView name;
			public TextView timeOfArrival;
		}

		@Override
		public int getCount() {
			return invites.size();
		}

		@Override
		public Object getItem(int position) {
			return invites.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0; // TODO: maybe change for something else.
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View $;
			ViewHolder holder;

			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				$ = inflater.inflate(R.layout.lmi_invite, null);

				holder = new ViewHolder();
				holder.name = (TextView) $.findViewById(R.id.lmi_contact_name);
				holder.timeOfArrival = (TextView) $
						.findViewById(R.id.lmi_contact_time_of_arrival);
				$.setTag(holder);
			} else {
				$ = convertView;
				holder = (ViewHolder) $.getTag();
			}

			Invite i = (Invite) getItem(position);

			holder.name.setText(i.name);
			holder.timeOfArrival.setText(i.timeOfArrival);

			return $;
		}
	}

}
