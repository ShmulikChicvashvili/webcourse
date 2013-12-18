package com.technion.coolie.skeleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.technion.coolie.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationModuleAdapter extends BaseAdapter {

	private static final int ITEM_VIEW_TYPE_MODULE = 0;
    private static final int ITEM_VIEW_TYPE_SEPARATOR = 1;
    private static final int ITEM_VIEW_TYPE_COUNT = 2;
    
	Context mContext;
	Comparator<CoolieModule> comp;
	List<CoolieModule> favoriteModules = new ArrayList<CoolieModule>();
	List<CoolieModule> nonFavoriteModules = new ArrayList<CoolieModule>();
	Separator favoritesTitle;
	Separator nonFavoritesTitle;
	int favoriteModulesIndex;
	int otherModulesIndex;
	
	private List<Object> listObjects = new ArrayList<Object>();
	
	private class Separator
	{
		String title;
	}
	
	private class ModuleViewHolder
	{
		TextView title;
		ImageButton star;
		ImageView image;
	}
	
	private class SeparatorHolder
	{
		TextView title;
	}
	
	public NavigationModuleAdapter(Context c) {
		mContext = c;
		CoolieModule[] allModules = CoolieModule.values();
		comp = new Comparator<CoolieModule>()
		{

			@Override
			public int compare(CoolieModule m1, CoolieModule m2) {
				return m1.getName(mContext).compareTo(m2.getName(mContext));
			}
	
		};
		
		Arrays.sort(allModules, comp);

		
		for(CoolieModule m : allModules)
		{
			if(m.isFavorite())
				favoriteModules.add(m);
			else
				nonFavoriteModules.add(m);
		}
		
		favoritesTitle = new Separator();
		favoritesTitle.title = mContext.getString(R.string.skel_favorite_modules_title);
		
		nonFavoritesTitle = new Separator();
		nonFavoritesTitle.title = mContext.getString(R.string.skel_nonfavorite_modules_title);
		
		createListObjects();
	}
	
	private void createListObjects() {
		listObjects.clear();
		favoriteModulesIndex = 0;
		if(favoriteModules.size()!=0)
		{
			listObjects.add(favoritesTitle);
			listObjects.addAll(favoriteModules);
		}
		
		otherModulesIndex = listObjects.size();
		if(nonFavoriteModules.size()!=0)
		{
			listObjects.add(nonFavoritesTitle);
			listObjects.addAll(nonFavoriteModules);
		}
	}

	@Override
	public int getCount() {
		return listObjects.size();
	}

	@Override
    public Object getItem(int position) {
        return listObjects.get(position);
    }

	@Override
    public long getItemId(int position) {
        return position;
    }
	
	@Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_COUNT;
    }
	
	@Override
    public boolean isEnabled(int position) {
        // A separator cannot be clicked !
        return getItemViewType(position) != ITEM_VIEW_TYPE_SEPARATOR;
    }
	
	@Override
    public int getItemViewType(int position) {
        return (listObjects.get(position) instanceof Separator) ? ITEM_VIEW_TYPE_SEPARATOR : ITEM_VIEW_TYPE_MODULE;
    }

	@Override
    public View getView(final int position, View convertView, ViewGroup parent) {
		final int type = getItemViewType(position);
		View v = null;
		if(type == ITEM_VIEW_TYPE_SEPARATOR)
	    {
			SeparatorHolder holder = null;

			if (convertView == null) {	        
	        	LayoutInflater inf = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inf.inflate(R.layout.skel_navigation_separator, null);
				
				holder = new SeparatorHolder();
				holder.title = (TextView) v.findViewById(R.id.skel_nav_separator_title);
				
				v.setTag(holder);
	        }
			else
			{
				v = convertView;
				holder = (SeparatorHolder) v.getTag();
			}
			
			
			Separator curr = (Separator) listObjects.get(position);
			holder.title.setText(curr.title);
	    }
		
		if(type == ITEM_VIEW_TYPE_MODULE)
		{
			ModuleViewHolder holder = null;

			if (convertView == null) {	        
	        	LayoutInflater inf = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inf.inflate(R.layout.skel_navigation_module_item, null);
				
				holder = new ModuleViewHolder();
				holder.image = (ImageView) v.findViewById(R.id.skel_nav_module_image);
				holder.title = (TextView) v.findViewById(R.id.skel_nav_module_name);
				holder.star = (ImageButton) v.findViewById(R.id.skel_nav_module_star);
				
				v.setTag(holder);
			}			
			else
			{
				v = convertView;
				holder = (ModuleViewHolder) v.getTag();
			}
			
			final CoolieModule m = (CoolieModule) listObjects.get(position);
			holder.image.setImageResource(m.getPhotoRes());
			holder.title.setText(m.getName(mContext));
			holder.star.setImageResource(position<otherModulesIndex ? R.drawable.skel_checked_star : R.drawable.skel_unchecked_star);
			
			v.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					m.addUsage();
			        Intent intent = new Intent(mContext, m.getActivity());
			        mContext.startActivity(intent);
				}			
			});
			
			holder.star.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					if(position > otherModulesIndex)
					{
						ImageButton b = (ImageButton) arg0;
						b.setImageResource(R.drawable.skel_checked_star);
						m.setFavorite();
						nonFavoriteModules.remove(m);
						int i = 0;
						if(favoriteModules.size()==0)
							favoriteModules.add(m);
						else
						{
							for(; i<=favoriteModules.size(); i++)
							{
								if(i==0)
								{
									if(favoriteModules.get(0).getName(mContext).compareTo(m.getName(mContext))>0)
									{
										favoriteModules.add(0, m);
										break;
									}
								}
								else if (i==favoriteModules.size())
								{
									if(favoriteModules.get(i-1).getName(mContext).compareTo(m.getName(mContext))<0)
									{
										favoriteModules.add(i, m);
										break;
									}
								}
								else if(favoriteModules.get(i-1).getName(mContext).compareTo(m.getName(mContext))<0
										&&favoriteModules.get(i).getName(mContext).compareTo(m.getName(mContext))>0)
								{
									favoriteModules.add(i, m);
									break;
								}
							}
						}
					}
					else
					{
						ImageButton b = (ImageButton) arg0;
						b.setImageResource(R.drawable.skel_unchecked_star);
						m.setNotFavorite();
						favoriteModules.remove(m);
						int i = 0;
						if(nonFavoriteModules.size()==0)
							nonFavoriteModules.add(m);
						else
						{
							for(; i<=nonFavoriteModules.size(); i++)
							{
								if(i==0)
								{
									if(nonFavoriteModules.get(0).getName(mContext).compareTo(m.getName(mContext))>0)
									{
										nonFavoriteModules.add(0, m);
										break;
									}
								}
								else if (i==nonFavoriteModules.size())
								{
									if(nonFavoriteModules.get(i-1).getName(mContext).compareTo(m.getName(mContext))<0)
									{
										nonFavoriteModules.add(i, m);
										break;
									}
								}
								else if(nonFavoriteModules.get(i-1).getName(mContext).compareTo(m.getName(mContext))<0
										&&nonFavoriteModules.get(i).getName(mContext).compareTo(m.getName(mContext))>0)
								{
									nonFavoriteModules.add(i, m);
									break;
								}
							}
						}
					}
					createListObjects();
                    notifyDataSetChanged();
				}
			});
		}
        return v;
	}
}
