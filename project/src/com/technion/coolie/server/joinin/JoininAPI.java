/**
 * 
 */
package com.technion.coolie.server.joinin;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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
  public Long addEvent(Event event) throws NumberFormatException, IOException {
    return Long.valueOf(Communicator.execute(JoininEnum.JOININ_SERVLET.value(),
        "function", JoininEnum.ADD_EVENT.toString(), JoininEnum.EVENT.value(),
        gson.toJson(event)));
  }

  @Override
  public Long updateEvent(Event event) throws NumberFormatException,
      IOException {
    return addEvent(event);
  }

  @Override
  public ReturnCode removeEvent(Event event) throws IOException {
    return ReturnCode.valueOf(Communicator.execute(
        JoininEnum.JOININ_SERVLET.value(), "function",
        JoininEnum.REMOVE_EVENT.toString(), JoininEnum.EVENT.value(),
        gson.toJson(event)));
  }

  @Override
  public Event getEvent(Event event) throws JsonSyntaxException, IOException {
    return gson.fromJson(Communicator.execute(
        JoininEnum.JOININ_SERVLET.value(), "function",
        JoininEnum.GET_EVENT.toString(), JoininEnum.EVENT.value(),
        gson.toJson(event)), Event.class);
  }

  @Override
  public List<Event> getAllEvents() throws JsonSyntaxException, IOException {
    return gson.fromJson(Communicator.execute(
        JoininEnum.JOININ_SERVLET.value(), "function",
        JoininEnum.GET_ALL_EVENTS.toString()), new TypeToken<List<Event>>() {
      // default usage
    }.getType());
  }

  @Override
  public ReturnCode joinToEvent(Event event, FacebookUser fbUser)
      throws IOException {
    return ReturnCode.valueOf(Communicator.execute(
        JoininEnum.JOININ_SERVLET.value(), "function",
        JoininEnum.JOIN_TO_EVENT.toString(), JoininEnum.EVENT.value(),
        gson.toJson(event), JoininEnum.FB_USER.value(), gson.toJson(fbUser)));
  }

  @Override
  public ReturnCode removeFromEvent(Event event, FacebookUser fbUser)
      throws IOException {
    return ReturnCode.valueOf(Communicator.execute(
        JoininEnum.JOININ_SERVLET.value(), "function",
        JoininEnum.REMOVE_FROM_EVENT.toString(), JoininEnum.EVENT.value(),
        gson.toJson(event), JoininEnum.FB_USER.value(), gson.toJson(fbUser)));
  }

  @Override
  public ReturnCode leaveEvent(Event event, FacebookUser fbUser)
      throws IOException {
    return ReturnCode.valueOf(Communicator.execute(
        JoininEnum.JOININ_SERVLET.value(), "function",
        JoininEnum.LEAVE_EVENT.toString(), JoininEnum.EVENT.value(),
        gson.toJson(event), JoininEnum.FB_USER.value(), gson.toJson(fbUser)));
  }

}
