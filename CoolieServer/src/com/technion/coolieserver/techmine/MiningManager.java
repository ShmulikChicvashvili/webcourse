package com.technion.coolieserver.techmine;

import static com.technion.coolieserver.framework.OfyService.ofy;

import com.technion.coolieserver.techmine.appfiles.IGetters;
import com.technion.coolieserver.techmine.appfiles.ReturnCode;

public class MiningManager {

  public static <E extends IGetters> E getEntity(E e) {
    return (E) ofy().load().type(e.getClass()).id(e.getId()).now();
  }

  public static <E extends IGetters> ReturnCode addEntity(E e) {

    if (getEntity(e) != null)
      return ReturnCode.ENTITY_ALREADY_EXISTS;

    ofy().save().entity(e).now();
    return ReturnCode.SUCCESS;

  }

  public static <E extends IGetters> ReturnCode removeEntity(E e_) {

    E e = getEntity(e_);
    if (e == null)
      return ReturnCode.ENTITY_NOT_EXISTS;

    ofy().delete().entity(e);
    return ReturnCode.SUCCESS;
  }

}
