package com.technion.coolie.tecmind;

import java.util.LinkedList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;
import com.technion.coolie.R;
import com.technion.coolie.tecmind.BL.Mine;
import com.technion.coolie.tecmind.BL.Post;
import com.technion.coolie.tecmind.BL.User;
import com.technion.coolie.tecmind.MineActivity.ServerUpdateUserData;
import com.technion.coolie.tecmind.server.ReturnCode;
import com.technion.coolie.tecmind.server.TecPost;
import com.technion.coolie.tecmind.server.TecUser;
import com.technion.coolie.tecmind.server.TecUserTitle;
import com.technion.coolie.tecmind.server.TechmineAPI;

public class TotalAccountActivity extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View inflateView = (LinearLayout) inflater.inflate(
				R.layout.techmind_activity_total_account, container, false);
		TextView from = (TextView) inflateView.findViewById(R.id.from_data);
		int index = MineActivity.exMiningDate.toString().indexOf("GMT");
		from.setText(MineActivity.exMiningDate.toString().subSequence(0, index));
		TextView to = (TextView) inflateView.findViewById(R.id.to_data);
		index = MineActivity.newMiningDate.toString().indexOf("GMT");
		to.setText(MineActivity.newMiningDate.toString().subSequence(0, index));
		TextView total = (TextView) inflateView.findViewById(R.id.total_data);
		total.setText(String.valueOf(MineActivity.totalDelta));
		return inflateView;
	}
}
