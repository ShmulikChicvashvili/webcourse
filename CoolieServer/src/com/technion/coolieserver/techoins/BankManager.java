/**
 * 
 */
package com.technion.coolieserver.techoins;

import static com.technion.coolieserver.framework.OfyService.ofy;

import java.util.Date;
import java.util.List;

import com.googlecode.objectify.VoidWork;
import com.technion.coolieserver.techoins.appfiles.Action;
import com.technion.coolieserver.techoins.appfiles.BankAccount;
import com.technion.coolieserver.techoins.appfiles.BankersFunctions;
import com.technion.coolieserver.techoins.appfiles.Degree;
import com.technion.coolieserver.techoins.appfiles.IBankersFunctions;
import com.technion.coolieserver.techoins.appfiles.ReturnCode;
import com.technion.coolieserver.techoins.appfiles.TechoinsTransfer;

/**
 * @author Omer
 * 
 */
public class BankManager {

  static IBankersFunctions bankersFunctions = new BankersFunctions();

  public static ReturnCode addStudent(String id, String password, Degree d) {

    if (getAccount(id) != null)
      return ReturnCode.STUDENT_ALREADY_EXISTS;

    ofy()
        .save()
        .entity(
            new BankAccount(id, bankersFunctions.getStartCredit(id, password),
                d)).now();
    return ReturnCode.SUCCESS;
  }

  /**
   * 
   * @param id
   * @return
   */
  public static ReturnCode removeStudent(String id) {

    BankAccount bankStudent = getAccount(id);
    if (bankStudent == null)
      return ReturnCode.STUDENT_NOT_EXISTS;

    ofy().delete().entity(bankStudent);
    return ReturnCode.SUCCESS;
  }

  public static BankAccount getAccount(String id) {
    return ofy().load().type(BankAccount.class).id(id).now();
  }

  /**
   * 
   * @param fromID
   * @param toID
   * @param action
   * @param amount
   * @param date
   * @return
   */
  public static ReturnCode moveMoney(String fromID, String toID, Action action,
      double amount, Date date) {
    if (getAccount(fromID) == null || getAccount(toID) == null)
      return ReturnCode.STUDENT_NOT_EXISTS;
    ofy().save()
        .entity(new TechoinsTransfer(fromID, toID, action, amount, date)).now();

    setStudentCredit(fromID, -amount);
    setStudentCredit(toID, amount);

    return ReturnCode.SUCCESS;
  }

  /**
   * 
   * @param id_
   * @param amount_
   */
  private static void setStudentCredit(String id_, double amount_) {

    final String id = id_;
    final double amount = amount_;

    ofy().transact(new VoidWork() {

      @Override
      public void vrun() {
        BankAccount account = ofy().load().type(BankAccount.class).id(id).now();
        account.setCredit(account.credit() + amount);
        ofy().save().entity(account);
      }
    });
  }

  /**
   * 
   * @param id
   * @return
   */
  public static List<TechoinsTransfer> getHistory(String id) {
    List<TechoinsTransfer> list1 = ofy().load().type(TechoinsTransfer.class)
        .filter("fromID", id).list();
    List<TechoinsTransfer> list2 = ofy().load().type(TechoinsTransfer.class)
        .filter("toID", id).list();
    list1.addAll(list2);
    return list1;
  }
}
