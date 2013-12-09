package com.technion.coolie.skeleton;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;

public class MainActivity extends CoolieActivity {
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.skel_activity_main2);

		/*
		 * ViewStub btn; LinearLayout buttonLayout; for(int i=1; i<=10; i++) {
		 * btn = (ViewStub)
		 * findViewById(HelpFunctions.findResourceByName("skel_btn"+i,
		 * R.id.class));
		 * 
		 * LayoutParams lp = (LayoutParams) btn.getLayoutParams(); buttonLayout
		 * = (LinearLayout) btn.inflate(); buttonLayout.setLayoutParams(lp);
		 * 
		 * ((ImageView)(buttonLayout.findViewById(R.id.skel_image)))
		 * .setImageResource(HelpFunctions.findResourceByName("skel_module"+i,
		 * R.drawable.class));
		 * 
		 * final TextView tv =
		 * ((TextView)(buttonLayout.findViewById(R.id.skel_title)));
		 * tv.setText(HelpFunctions.findResourceByName("module"+i+"_name",
		 * R.string.class));
		 * 
		 * buttonLayout.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { try { String actName
		 * ="com.technion.coolie." +
		 * tv.getText().toString().toLowerCase().replaceAll
		 * ("\\s+","")+".MainActivity"; Log.v("ACTIVITY!", actName); Class<?> c
		 * = Class.forName(actName); Intent intent = new
		 * Intent(MainActivity.this, c); startActivity(intent);
		 * 
		 * } catch (ClassNotFoundException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } } });
		 * 
		 * String desc; String name =
		 * getResources().getResourceName(buttonLayout.getId());
		 * if(name.startsWith("com.technion.coolie:id/small")) desc =
		 * getString(HelpFunctions
		 * .findResourceByName("module"+i+"_short_description",
		 * R.string.class)); else desc =
		 * getString(HelpFunctions.findResourceByName
		 * ("module"+i+"_long_description", R.string.class));
		 * ((TextView)(buttonLayout
		 * .findViewById(R.id.skel_news_text))).setText(desc); }
		 */

		mViewPager = (ViewPager) findViewById(R.id.skel_main_view_pager);
		

		DemoCollectionPagerAdapter mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(
				getSupportFragmentManager(), MainActivity.this);
		
		mViewPager.setAdapter(mDemoCollectionPagerAdapter);

	}

	public class DemoCollectionPagerAdapter extends FragmentPagerAdapter {
		public DemoCollectionPagerAdapter(FragmentManager fm, Context c) {
			super(fm);
			f = new Fragment[3];
			f[0] = new fragment1();
			f[1] =  new fragment2();
			f[2] = new fragment3();

		}

		Fragment[] f;

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return f[arg0];
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}

	}

	public static class fragment1 extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Inflate the layout for this fragment
			return inflater.inflate(R.layout.skel_activity_main, container,
					false);
		}
	}

	public static class fragment2 extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Inflate the layout for this fragment
			return inflater.inflate(R.layout.skel_activity_main, container,
					false);
		}
	}

	public static class fragment3 extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Inflate the layout for this fragment
			return inflater.inflate(R.layout.skel_activity_main, container,
					false);
		}
	}

}
