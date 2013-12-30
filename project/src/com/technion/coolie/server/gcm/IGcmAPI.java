package com.technion.coolie.server.gcm;

import android.content.Context;

public interface IGcmAPI {

  public void registerDevice(Context context, boolean hasPlayServices);

}
