package com.technion.coolie.letmein;

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
import android.widget.ImageView;
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

		inviteList.setAdapter(new WelcomeInvitationAdapter(MainActivity.this));
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
		public String imageIndex; // TODO: this is "id" in gilad terms.

		public Invite(String name, String timeOfArrival, String imageIndex) {
			this.name = name;
			this.timeOfArrival = timeOfArrival;
			this.imageIndex = imageIndex;
		}
	}

	public static class WelcomeInvitationAdapter extends BaseAdapter {

		private Context context;
		private List<Invite> invites = initInvites();

		private static List<Invite> initInvites() {
			return Arrays.asList(
					new Invite("Dad", "Arriving in 8 hours", String
							.valueOf(R.drawable.lmi_dad)),
					new Invite("My best friend", "Tommorow 18:00", String
							.valueOf(R.drawable.lmi_winger)),
					new Invite("Abed", "October 19", String
							.valueOf(R.drawable.lmi_abed)));
		}

		public WelcomeInvitationAdapter(Context context) {
			this.context = context;
		}

		private class ViewHolder {
			public TextView name;
			public TextView timeOfArrival;
			public ImageView image;
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
			return 0;
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
				holder.image = (ImageView) $
						.findViewById(R.id.lmi_contact_image);
				$.setTag(holder);
			} else {
				$ = convertView;
				holder = (ViewHolder) $.getTag();
			}

			Invite i = (Invite) getItem(position);

			holder.name.setText(i.name);
			holder.timeOfArrival.setText(i.timeOfArrival);
			holder.image.setImageResource(Integer.parseInt(i.imageIndex));

			return $;
		}
	}

}
