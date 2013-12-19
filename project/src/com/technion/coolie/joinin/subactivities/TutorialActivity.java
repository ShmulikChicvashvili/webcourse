package com.technion.coolie.joinin.subactivities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.technion.coolie.R;

public class TutorialActivity extends Activity implements OnClickListener {
  ViewFlipper viewFlipper;
  private static final int SWIPE_MIN_DISTANCE = 120;
  private static final int SWIPE_MAX_OFF_PATH = 250;
  private static final int SWIPE_THRESHOLD_VELOCITY = 200;
  GestureDetector gestureDetector;
  View.OnTouchListener gestureListener;
  Context thisOne = this;
  TextView helpText;
  int tutorialIndex = 0;
  int[] helps = { R.string.tut1, R.string.tut2, R.string.tut3, R.string.tut4, R.string.tut5, R.string.tut6, R.string.tut7,
      R.string.tut8, R.string.tut9, R.string.tut10, R.string.tut11 };
  
  @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.ji_tutorial);
    setTitle("Welcome to TeamApp");
    final int gallery_grid_Images[] = { R.drawable.ji_welcome1, R.drawable.ji_event2, R.drawable.ji_details3,
        R.drawable.ji_attendings4, R.drawable.ji_messages5, R.drawable.ji_menu6, R.drawable.ji_tocreate7, R.drawable.ji_creat8,
        R.drawable.ji_calendar9, R.drawable.ji_filter10, R.drawable.ji_my_events11 };
    viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
    helpText = (TextView) findViewById(R.id.help_text);
    helpText.setText(helps[tutorialIndex]);
    // Gesture detection
    gestureDetector = new GestureDetector(this, new MyGestureDetector());
    gestureListener = new View.OnTouchListener() {
      @Override public boolean onTouch(final View v, final MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
      }
    };
    viewFlipper.setOnClickListener(this);
    viewFlipper.setOnTouchListener(gestureListener);
    for (final int gallery_grid_Image : gallery_grid_Images)
      // This will create dynamic image view and add them to ViewFlipper
      setFlipperImage(gallery_grid_Image);
  }
  
  /**
   * sets the tutorial images to the flip view
   * 
   * @param res
   *          the source of the image
   */
  private void setFlipperImage(final int res) {
    Log.i("Set Filpper Called", res + "");
    final ImageView i = new ImageView(getApplicationContext());
    i.setBackgroundResource(res);
    viewFlipper.addView(i);
  }
  
  /**
   * gesture detector for swiping the images
   * 
   * @author On
   * 
   */
  class MyGestureDetector extends SimpleOnGestureListener {
    @Override public boolean onFling(final MotionEvent e1, final MotionEvent e2, final float velocityX, final float velocityY) {
      try {
        if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
          return false;
        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
          viewFlipper.setInAnimation(inFromRightAnimation());
          viewFlipper.setOutAnimation(outToLeftAnimation());
          if (tutorialIndex < viewFlipper.getChildCount() - 1) {
            viewFlipper.showNext();
            tutorialIndex++;
            helpText.setText(helps[tutorialIndex]);
          }
        } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
          viewFlipper.setInAnimation(inFromLeftAnimation());
          viewFlipper.setOutAnimation(outToRightAnimation());
          if (tutorialIndex > 0) {
            viewFlipper.showPrevious();
            tutorialIndex--;
            helpText.setText(helps[tutorialIndex]);
          }
        }
      } catch (final Exception e) {
        // nothing
      }
      return false;
    }
  }
  
  @Override public void onClick(final View arg0) {
    // overriden function - must be here and not empty
  }
  
  /**
   * animation function for in from right animation
   * 
   * @return
   */
  static Animation inFromRightAnimation() {
    final Animation $ = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, +1.2f, Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
    $.setDuration(500);
    $.setInterpolator(new AccelerateInterpolator());
    return $;
  }
  
  /**
   * animation function for out to left animation
   * 
   * @return
   */
  static Animation outToLeftAnimation() {
    final Animation $ = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.2f,
        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
    $.setDuration(500);
    $.setInterpolator(new AccelerateInterpolator());
    return $;
  }
  
  /**
   * animation function for in from left animation
   * 
   * @return
   */
  static Animation inFromLeftAnimation() {
    final Animation $ = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.2f, Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
    $.setDuration(500);
    $.setInterpolator(new AccelerateInterpolator());
    return $;
  }
  
  /**
   * animation function for out to right animation
   * 
   * @return
   */
  static Animation outToRightAnimation() {
    final Animation $ = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, +1.2f,
        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
    $.setDuration(500);
    $.setInterpolator(new AccelerateInterpolator());
    return $;
  }
}
