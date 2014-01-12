package com.technion.coolie.server.gcm;

import android.content.Context;

public class GcmAPI implements IGcmAPI {

  @Override
  public void registerDevice(Context context, boolean hasPlayServices) {
    new GcmRegistration().doRegistration(context, hasPlayServices);
  }

}
