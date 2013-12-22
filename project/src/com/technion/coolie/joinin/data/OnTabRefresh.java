package com.technion.coolie.joinin.data;

/**
 * @author Shimon Kama
 * 
 *         This interface is used by a Fragment activity in order to refresh its
 *         children.
 */
public interface OnTabRefresh {
  /**
   * Refresh a child fragment.
   * 
   * @param pos
   *          The position of the fragment to be refreshed.
   */
  public void onRefresh(int pos);
}
