package com.technion.coolie.techtrade;

import java.util.List;

/**
 * 
 * Created on 7/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public interface ITechTradeServerAPI {

  ReturnCode addAccount(BankAccount account, String password);

  ReturnCode removeAccount(BankAccount account);

  BankAccount getAccount(BankAccount account);

  List<TechoinsTransfer> getHistory(BankAccount account);

  ReturnCode moveMoney(TechoinsTransfer transfer);

  // the function "getProductById" was deleted, instead use "getProductsById"
  // with list length==1

  // Returns multiple products as requested by their ID's
  List<Product> getProductsByIds(List<Product> products);

  // Returns x products that have the name as wanted
  List<Product> getProductsByName(Product product);

  // Returns x products under the wanted category
  List<Product> getProductsByCategory(Product product);

  // the function "getProductsByName&Category" was deleted, instead use
  // list1.retainAll(list2)

  // Returns X most recent added products
  List<Product> getXRecentProducts(int x);

  // Returns X random products
  List<Product> getXRandomProducts(int x);

  // Add a product to data base
  ReturnCode addProduct(Product product);

  // Returns all products that their field: sellerID equal to given id and
  // the boolean field: sold is true
  List<Product> getSoldProductsBySellerID(Product product);

  // Returns all products that their field: buyerID equal to given id and
  // the boolean field: sold is true
  List<Product> getPurchasedProductsByBuyerID(Product product);

  // Returns all products that their field: sellerID equal to given id and
  // the boolean field: sold is false
  List<Product> getPublishedProductsBySellerID(Product product);

  // Remove a product with the wanted id from database
  ReturnCode removeProduct(Product product);

  // Update product as sold and
  ReturnCode buyProduct(Product product);

}
