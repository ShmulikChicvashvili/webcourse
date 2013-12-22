package com.technion.coolie.joinin.data;

import java.io.IOException;
import java.io.Serializable;

import android.util.SparseBooleanArray;

/**
 * 
 * @author On
 * 
 *         An class to pass the SparseBolleanArray (which is the result of the
 *         listView) through an intent
 */
public class SerializableSparseBooleanArrayContainer implements Serializable {
  private static final long serialVersionUID = 393662066105575556L;
  private SparseBooleanArray mSparseArray;
  
  public SerializableSparseBooleanArrayContainer(final SparseBooleanArray mDataArray) {
    mSparseArray = mDataArray;
  }
  
  public SparseBooleanArray getSparseArray() {
    return mSparseArray;
  }
  
  public void setSparseArray(final SparseBooleanArray sparseArray) {
    mSparseArray = sparseArray;
  }
  
  private void writeObject(final java.io.ObjectOutputStream out) throws IOException {
    out.writeLong(serialVersionUID);
    out.write(mSparseArray.size());
    for (int i = 0; i < mSparseArray.size(); i++) {
      out.writeInt(mSparseArray.keyAt(i));
      out.writeBoolean(mSparseArray.get(mSparseArray.keyAt(i)));
    }
  }
  
  @SuppressWarnings("unused") private void readObject(final java.io.ObjectInputStream in) throws IOException,
      ClassNotFoundException {
    if (in.readLong() != serialVersionUID)
      throw new IOException("serial version mismatch");
    final int sparseArraySize = in.read();
    mSparseArray = new SparseBooleanArray(sparseArraySize);
    for (int i = 0; i < sparseArraySize; i++)
      mSparseArray.put(in.readInt(), in.readBoolean());
  }
}