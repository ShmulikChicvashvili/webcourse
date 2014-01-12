package com.technion.coolie.server.techoins;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonSyntaxException;

/**
 * 
 * Created on 7/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public interface ITechoinsAPI {

  /**
   * 
   * @param account
   *          - the account to add with all fields initialized correctly
   * 
   * @param password
   *          - the password of the account's owner
   * 
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  ReturnCode addAccount(BankAccount account, String password)
      throws IOException;

  /**
   * 
   * @param account
   *          - the account to remove with id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  ReturnCode removeAccount(BankAccount account) throws IOException;

  /**
   * 
   * @param account
   *          - the account to get with id field initialized correctly
   * @return - the requested object
   * @throws IOException
   * @throws JsonSyntaxException
   */
  BankAccount getAccount(BankAccount account) throws JsonSyntaxException,
      IOException;

  /**
   * 
   * @param account
   *          - the account to get its history of money transfers with id field
   *          initialized correctly
   * @return - list of transfers related to the given account
   * @throws IOException
   * @throws JsonSyntaxException
   */
  List<TechoinsTransfer> getHistory(BankAccount account)
      throws JsonSyntaxException, IOException;

  /**
   * 
   * @param transfer
   *          - the transfer to make and to store
   * @return SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  ReturnCode moveMoney(TechoinsTransfer transfer) throws IOException;

  /**
   * 
   * @param product
   *          - the product to search, with name field initialized correctly
   * @return - list of products with matched name
   * @throws IOException
   * @throws JsonSyntaxException
   */
  List<Product> getProductsByName(Product product) throws JsonSyntaxException,
      IOException;

  /**
   * 
   * @param product
   *          - the product to search, with category field initialized correctly
   * @return - list of products with matched category
   * @throws IOException
   * @throws JsonSyntaxException
   */
  List<Product> getProductsByCategory(Product product)
      throws JsonSyntaxException, IOException;

  // the function "getProductsByName&Category" was deleted, instead use
  // list1.retainAll(list2)

  /**
   * 
   * @param n
   *          - number of products to retrieve
   * @return - list of the n most recently added products
   * @throws IOException
   * @throws JsonSyntaxException
   */
  List<Product> getXRecentProducts(int x) throws JsonSyntaxException,
      IOException;

  /**
   * 
   * @param n
   *          - number of products to retrieve
   * @return - list of n random products
   * @throws IOException
   * @throws JsonSyntaxException
   */
  List<Product> getXRandomProducts(int x) throws JsonSyntaxException,
      IOException;

  /**
   * 
   * @param product
   *          - the product to add with all fields initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  ReturnCode addProduct(Product product) throws IOException;

  /**
   * 
   * @param product
   *          - the product to search, with sellerId field initialized correctly
   * @return - list of products with matched sellerId
   * @throws IOException
   * @throws JsonSyntaxException
   */
  List<Product> getSoldProductsBySellerID(Product product)
      throws JsonSyntaxException, IOException;

  /**
   * 
   * @param product
   *          - the product to search, with buyerId field initialized correctly
   * @return - list of products with matched buyerId
   * @throws IOException
   * @throws JsonSyntaxException
   */
  List<Product> getPurchasedProductsByBuyerID(Product product)
      throws JsonSyntaxException, IOException;

  /**
   * 
   * @param product
   *          - the product to search, with sellerId and publishDate fields
   *          initialized correctly
   * @return - list of products with matched sellerId
   * @throws IOException
   * @throws JsonSyntaxException
   */
  List<Product> getPublishedProductsBySellerID(Product product)
      throws JsonSyntaxException, IOException;

  /**
   * 
   * @param product_
   *          - the product to remove with id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  ReturnCode removeProduct(Product product) throws IOException;

  /**
   * 
   * @param product_
   *          - the product to buy with id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  ReturnCode buyProduct(Product product) throws IOException;

  /**
   * 
   * @param s
   *          - the string to look for in the products names
   * @return - list of products with names that contain the given string
   * @throws IOException
   * @throws JsonSyntaxException
   */
  List<Product> getByName(String s) throws JsonSyntaxException, IOException;

  /**
   * 
   * @param s
   *          - the string to look for in the products descriptions
   * @return - list of products with descriptions that contain the given string
   * @throws IOException
   * @throws JsonSyntaxException
   */
  List<Product> getByDescription(String s) throws JsonSyntaxException,
      IOException;

}
