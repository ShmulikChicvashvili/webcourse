package com.technion.coolie.joinin.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.maps.GeoPoint;
import com.google.gson.Gson;
import com.technion.coolie.joinin.directions.TravelWay;
import com.technion.coolie.joinin.facebook.FacebookUser;
import com.technion.coolie.joinin.map.EventType;

public class ClientEvent extends Event implements Parcelable {
  TravelWay mTravelWay = TravelWay.NO_TRAVEL;

  @Deprecated
  public ClientEvent() {
  }

  public ClientEvent(final long eventId, final String name,
      final String address, final String description, final long latitude,
      final long longitude, final EventDate when, final long invited,
      final EventType type, final String owner) {
    super(eventId, name, address, description, latitude, longitude, when,
        invited, type, owner);
  }

  public ClientEvent(final Event e) {
    super(e);
  }

  public TravelWay getTravelWay() {
    return mTravelWay;
  }

  public void setTravelWay(final TravelWay mTravelWay) {
    this.mTravelWay = mTravelWay;
  }

  public static List<ClientEvent> toList(final List<Event> es) {
    final List<ClientEvent> $ = new ArrayList<ClientEvent>();
    for (final Event e : es)
      $.add(new ClientEvent(e));
    return $;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    dest.writeLong(mId);
    dest.writeString(mName);
    dest.writeString(mAddress);
    dest.writeString(mDescription);
    dest.writeLong(mLatitude);
    dest.writeLong(mLongitude);
    dest.writeLong(mWhen);
    dest.writeLong(mInvited);
    dest.writeInt(mType.ordinal());
    dest.writeString(mOwner);
    dest.writeInt(mTravelWay.ordinal());
    dest.writeTypedList(new LinkedList<FacebookUser>(mSubscribed));
  }

  public ClientEvent(final Parcel in) {
    super(in.readLong(), in.readString(), in.readString(), in.readString(), in
        .readLong(), in.readLong(), new EventDate(in.readLong()),
        in.readLong(), EventType.values()[in.readInt()], in.readString());
    mTravelWay = TravelWay.values()[in.readInt()];
    final List<FacebookUser> $ = new LinkedList<FacebookUser>();
    in.readTypedList($, FacebookUser.CREATOR);
    mSubscribed = new HashSet<FacebookUser>($);
  }

  @SuppressWarnings("rawtypes")
  public static final Parcelable.Creator<?> CREATOR = new Parcelable.Creator() {
    @Override
    public ClientEvent createFromParcel(final Parcel in) {
      return new ClientEvent(in);
    }

    @Override
    public ClientEvent[] newArray(final int size) {
      return new ClientEvent[size];
    }
  };

  public GeoPoint getGeoPoint() {
    return new GeoPoint((int) getLatitude(), (int) getLongitude());
  }

  public static ClientEvent toClientEvent(final String s) {
    final Event $ = Event.toEvent(s);
    return $ == null ? null : new ClientEvent($);
  }

  @Override
  public String toString() {
    return new Gson().toJson(this, Event.class);
  }
}
