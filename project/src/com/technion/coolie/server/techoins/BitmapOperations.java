package com.technion.coolie.server.techoins;

import java.io.ByteArrayOutputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class BitmapOperations {

  /*
   * usage example
   * mImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(),
   * R.id.myimage, 100, 100));
   */

  public static Bitmap decodeByteArray(byte[] byteArray) {
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
  }

  public static byte[] decodeToByteArray(Bitmap bitmap) {
    ByteArrayOutputStream blob = new ByteArrayOutputStream();
    bitmap.compress(CompressFormat.PNG, 0, blob);
    return blob.toByteArray();
  }

  public static Bitmap decodeBitmapFromFile(String url, int reqWidth,
      int reqHeight) {

    // First decode with inJustDecodeBounds=true to check dimensions
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(url, options);

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeFile(url, options);
  }

  public static Bitmap decodeBitmapFromResource(Resources res, int resId,
      int reqWidth, int reqHeight) {

    // First decode with inJustDecodeBounds=true to check dimensions
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeResource(res, resId, options);

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeResource(res, resId, options);
  }

  private static int calculateInSampleSize(BitmapFactory.Options options,
      int reqWidth, int reqHeight) {
    if (reqHeight == 0 || reqWidth == 0) {
      return options.inSampleSize;
    }
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

      final int halfHeight = height / 2;
      final int halfWidth = width / 2;

      // Calculate the largest inSampleSize value that is a power of 2 and keeps
      // both
      // height and width larger than the requested height and width.
      while ((halfHeight / inSampleSize) > reqHeight
          && (halfWidth / inSampleSize) > reqWidth) {
        inSampleSize *= 2;
      }
    }

    return inSampleSize;
  }
}
