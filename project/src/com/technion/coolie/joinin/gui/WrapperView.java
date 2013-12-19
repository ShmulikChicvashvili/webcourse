package com.technion.coolie.joinin.gui;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technion.coolie.R;

public class WrapperView extends RelativeLayout {
  private LinearLayout mLoading;
  private final LinearLayout mBlocker;
  Activity mActivity;
  
  public WrapperView(final Activity context, final View mainView) {
    super(context);
    mActivity = context;
    setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
        android.view.ViewGroup.LayoutParams.MATCH_PARENT));
    mainView.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
        android.view.ViewGroup.LayoutParams.MATCH_PARENT));
    addView(mainView);
    mBlocker = new LinearLayout(mActivity);
    initLoadingView();
    mBlocker.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
        android.view.ViewGroup.LayoutParams.MATCH_PARENT));
    mBlocker.setBackgroundColor(0x20ffffff);
    mBlocker.setClickable(true);
    addView(mBlocker);
    mBlocker.setVisibility(View.INVISIBLE);
  }
  
  @SuppressWarnings("deprecation") private void initLoadingView() {
    final Display display = mActivity.getWindowManager().getDefaultDisplay();
    mLoading = (LinearLayout) ((LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
        R.layout.ji_loading, null);
    assert mLoading != null;
    final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
    mLoading.setLayoutParams(lp);
    lp.setMargins(display.getWidth() / 2 - 150, display.getHeight() / 2 - 100, 0, 0);
    addView(mLoading);
    hideProgress();
  }
  
  public void showProgress(final String text, final boolean blockTouch) {
    ((TextView) findViewById(R.id.progress)).setText(text);
    mLoading.setVisibility(View.VISIBLE);
    if (blockTouch)
      mBlocker.setVisibility(View.VISIBLE);
  }
  
  public void hideProgress() {
    mLoading.setVisibility(View.INVISIBLE);
    mBlocker.setVisibility(View.INVISIBLE);
  }
}
