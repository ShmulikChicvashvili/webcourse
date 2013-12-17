package com.technion.coolieserver.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.technion.coolieserver.framework.Authentication;
import com.technion.coolieserver.teletech.TeletechManager;
import com.technion.coolieserver.teletech.appfiles.ContactInformation;
import com.technion.coolieserver.teletech.appfiles.IParseContacts;
import com.technion.coolieserver.teletech.appfiles.ParseContactsStub;

public class AddContacts extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    if (!Authentication.checkAuth(req))
      resp.getWriter().println("ERROR");

    IParseContacts parser = new ParseContactsStub();
    List<ContactInformation> contacts = parser.parse();
    resp.getWriter().println(TeletechManager.addContacts(contacts));
  }
}