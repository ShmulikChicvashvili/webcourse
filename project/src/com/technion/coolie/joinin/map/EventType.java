package com.technion.coolie.joinin.map;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.technion.coolie.R;

/**
 * Enum for the event type
 * 
 * @author Ido Gonen
 * 
 */
public enum EventType implements GetDrawable {
  SPORT {
    @Override public Drawable getDrawable(final Activity activity) {
      return activity.getResources().getDrawable(R.drawable.ji_sports_icon);
    }
    
    @Override public String toString() {
      return "Sports";
    }
  },
  FOOD {
    @Override public Drawable getDrawable(final Activity activity) {
      return activity.getResources().getDrawable(R.drawable.ji_food_icon);
    }
    
    @Override public String toString() {
      return "Food";
    }
  },
  STUDY {
    @Override public Drawable getDrawable(final Activity activity) {
      return activity.getResources().getDrawable(R.drawable.ji_study_icon);
    }
    
    @Override public String toString() {
      return "Studies";
    }
  },
  MOVIE {
    @Override public Drawable getDrawable(final Activity activity) {
      return activity.getResources().getDrawable(R.drawable.ji_movie_icon);
    }
    
    @Override public String toString() {
      return "Movies";
    }
  },
  NIGHT_LIFE {
    @Override public Drawable getDrawable(final Activity activity) {
      return activity.getResources().getDrawable(R.drawable.ji_night_life_icon);
    }
    
    @Override public String toString() {
      return "Night Life";
    }
  },
  OTHER {
    @Override public Drawable getDrawable(final Activity activity) {
      return activity.getResources().getDrawable(R.drawable.ji_other_icon);
    }
    
    @Override public String toString() {
      return "Other";
    }
  };
}
