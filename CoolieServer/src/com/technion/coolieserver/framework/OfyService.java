package com.technion.coolieserver.framework;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.technion.coolieserver.parkion.appfiles.ParkingLot;
import com.technion.coolieserver.techmine.appfiles.TecComment;
import com.technion.coolieserver.techmine.appfiles.TecLike;
import com.technion.coolieserver.techmine.appfiles.TecPost;
import com.technion.coolieserver.techmine.appfiles.TopBestComment;
import com.technion.coolieserver.techmine.appfiles.TopBestPost;
import com.technion.coolieserver.techmine.appfiles.User;
import com.technion.coolieserver.techoins.appfiles.BankAccount;
import com.technion.coolieserver.techoins.appfiles.TechoinsTransfer;
import com.technion.coolieserver.ug.Course;

/**
 * 
 * Created on 11/11/2013
 * 
 * @author Daniel Abitbul <abitbul6@gmail.com>
 * 
 */
public class OfyService {
  static {
    factory().register(Course.class);
    factory().register(BankAccount.class);
    factory().register(TechoinsTransfer.class);
    factory().register(User.class);
    factory().register(TopBestComment.class);
    factory().register(TopBestPost.class);
    factory().register(TecPost.class);
    factory().register(TecLike.class);
    factory().register(TecComment.class);
    factory().register(ParkingLot.class);
  }

  public static Objectify ofy() {
    // begins to make actions with the objectify object.
    return ObjectifyService.ofy();
  }

  public static ObjectifyFactory factory() {
    // creates the factory to work with.
    return ObjectifyService.factory();
  }
}
