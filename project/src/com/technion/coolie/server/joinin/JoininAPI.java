/**
 * 
 */
package com.technion.coolie.server.joinin;

import com.google.gson.Gson;
import com.technion.coolie.server.Communicator;

/**
 * 
 * Created on 25/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public class JoininAPI implements IJoininAPI {

  Gson gson = new Gson();
  String FUNC = "function";

  @Override
  public ReturnCode addEvent(Event event) {
    return ReturnCode.valueOf(Communicator.execute(
        JoininEnum.JOININ_SERVLET.value(), "function",
        JoininEnum.ADD_EVENT.toString(), JoininEnum.EVENT.value(),
        gson.toJson(event)));
  }

  @Override
  public ReturnCode updateEvent(Event event) {
    return addEvent(event);
  }

  @Override
  public ReturnCode removeEvent(Event event) {
    return ReturnCode.valueOf(Communicator.execute(
        JoininEnum.JOININ_SERVLET.value(), "function",
        JoininEnum.REMOVE_EVENT.toString(), JoininEnum.EVENT.value(),
        gson.toJson(event)));
  }

  @Override
  public Event getEvent(Event event) {
    return gson.fromJson(Communicator.execute(
        JoininEnum.JOININ_SERVLET.value(), "function",
        JoininEnum.GET_EVENT.toString(), JoininEnum.EVENT.value(),
        gson.toJson(event)), Event.class);
  }

  @Override
  public Event getAllEvents() {
    return gson.fromJson(Communicator.execute(
        JoininEnum.JOININ_SERVLET.value(), "function",
        JoininEnum.GET_ALL_EVENTS.toString()), Event.class);
  }

  @Override
  public ReturnCode joinToEvent(Event event, FacebookUser fbUser) {
    return ReturnCode.valueOf(Communicator.execute(
        JoininEnum.JOININ_SERVLET.value(), "function",
        JoininEnum.JOIN_TO_EVENT.toString(), JoininEnum.EVENT.value(),
        gson.toJson(event), JoininEnum.FB_USER.value(), gson.toJson(fbUser)));
  }

  @Override
  public ReturnCode leaveEvent(Event event, FacebookUser fbUser) {
    return ReturnCode.valueOf(Communicator.execute(
        JoininEnum.JOININ_SERVLET.value(), "function",
        JoininEnum.LEAVE_EVENT.toString(), JoininEnum.EVENT.value(),
        gson.toJson(event), JoininEnum.FB_USER.value(), gson.toJson(fbUser)));
  }

}
