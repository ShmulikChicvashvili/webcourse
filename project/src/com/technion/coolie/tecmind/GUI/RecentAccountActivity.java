package com.technion.coolie.tecmind.GUI;

import com.actionbarsherlock.app.SherlockFragment;
import com.technion.coolie.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class RecentAccountActivity extends SherlockFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return (LinearLayout) inflater.inflate(R.layout.techmind_activty_recent_account, container, false);
    }
}
