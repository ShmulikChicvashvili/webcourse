package com.technion.coolie.server.techoins;

import java.util.List;

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
   */
  ReturnCode addAccount(BankAccount account, String password);

  /**
   * 
   * @param account
   *          - the account to remove with id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   */
  ReturnCode removeAccount(BankAccount account);

  /**
   * 
   * @param account
   *          - the account to get with id field initialized correctly
   * @return - the requested object
   */
  BankAccount getAccount(BankAccount account);

  /**
   * 
   * @param account
   *          - the account to get its history of money transfers with id field
   *          initialized correctly
   * @return - list of transfers related to the given account
   */
  List<TechoinsTransfer> getHistory(BankAccount account);

  /**
   * 
   * @param transfer
   *          - the transfer to make and to store
   * @return SUCCESS if went well, error code otherwise
   */
  ReturnCode moveMoney(TechoinsTransfer transfer);

  /**
   * 
   * @param product
   *          - the product to search, with name field initialized correctly
   * @return - list of products with matched name
   */
  List<Product> getProductsByName(Product product);

  /**
   * 
   * @param product
   *          - the product to search, with category field initialized correctly
   * @return - list of products with matched category
   */
  List<Product> getProductsByCategory(Product product);

  // the function "getProductsByName&Category" was deleted, instead use
  // list1.retainAll(list2)

  /**
   * 
   * @param n
   *          - number of products to retrieve
   * @return - list of the n most recently added products
   */
  List<Product> getXRecentProducts(int x);

  /**
   * 
   * @param n
   *          - number of products to retrieve
   * @return - list of n random products
   */
  List<Product> getXRandomProducts(int x);

  /**
   * 
   * @param product
   *          - the product to add with all fields initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   */
  ReturnCode addProduct(Product product);

  /**
   * 
   * @param product
   *          - the product to search, with sellerId field initialized correctly
   * @return - list of products with matched sellerId
   */
  List<Product> getSoldProductsBySellerID(Product product);

  /**
   * 
   * @param product
   *          - the product to search, with buyerId field initialized correctly
   * @return - list of products with matched buyerId
   */
  List<Product> getPurchasedProductsByBuyerID(Product product);

  /**
   * 
   * @param product
   *          - the product to search, with sellerId and publishDate fields
   *          initialized correctly
   * @return - list of products with matched sellerId
   */
  List<Product> getPublishedProductsBySellerID(Product product);

  /**
   * 
   * @param product_
   *          - the product to remove with id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   */
  ReturnCode removeProduct(Product product);

  /**
   * 
   * @param product_
   *          - the product to buy with id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   */
  ReturnCode buyProduct(Product product);

}
