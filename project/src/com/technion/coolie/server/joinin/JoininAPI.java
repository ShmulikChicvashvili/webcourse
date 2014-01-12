/**
 * 
 */
package com.technion.coolie.server.joinin;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
  public Long addEvent(Event event) {
    return Long.valueOf(Communicator.execute(JoininEnum.JOININ_SERVLET.value(),
        "function", JoininEnum.ADD_EVENT.toString(), JoininEnum.EVENT.value(),
        gson.toJson(event)));
  }

  @Override
  public Long updateEvent(Event event) {
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
  public List<Event> getAllEvents() {
    return gson.fromJson(Communicator.execute(
        JoininEnum.JOININ_SERVLET.value(), "function",
        JoininEnum.GET_ALL_EVENTS.toString()), new TypeToken<List<Event>>() {
      // default usage
    }.getType());
  }

  @Override
  public ReturnCode joinToEvent(Event event, FacebookUser fbUser) {
    return ReturnCode.valueOf(Communicator.execute(
        JoininEnum.JOININ_SERVLET.value(), "function",
        JoininEnum.JOIN_TO_EVENT.toString(), JoininEnum.EVENT.value(),
        gson.toJson(event), JoininEnum.FB_USER.value(), gson.toJson(fbUser)));
  }

  @Override
  public ReturnCode removeFromEvent(Event event, FacebookUser fbUser) {
    return ReturnCode.valueOf(Communicator.execute(
        JoininEnum.JOININ_SERVLET.value(), "function",
        JoininEnum.REMOVE_FROM_EVENT.toString(), JoininEnum.EVENT.value(),
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
