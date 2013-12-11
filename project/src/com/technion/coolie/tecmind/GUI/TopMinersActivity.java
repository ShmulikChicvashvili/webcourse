package com.technion.coolie.tecmind.GUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragment;
import com.technion.coolie.R;

public class TopMinersActivity extends SherlockFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return (LinearLayout) inflater.inflate(R.layout.techmind_activity_top_miners, container, false);

    }
}


