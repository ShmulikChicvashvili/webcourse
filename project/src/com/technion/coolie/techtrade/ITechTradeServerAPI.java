package com.technion.coolie.techtrade;

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
public interface ITechTradeServerAPI {

  ReturnCode addAccount(BankAccount account, String password)
      throws IOException;

  ReturnCode removeAccount(BankAccount account) throws IOException;

  BankAccount getAccount(BankAccount account) throws JsonSyntaxException,
      IOException;

  List<TechoinsTransfer> getHistory(BankAccount account)
      throws JsonSyntaxException, IOException;

  ReturnCode moveMoney(TechoinsTransfer transfer) throws IOException;

  // the function "getProductById" was deleted, instead use "getProductsById"
  // with list length==1

  // Returns multiple products as requested by their ID's
  List<Product> getProductsByIds(List<Product> products)
      throws JsonSyntaxException, IOException;

  // Returns x products that have the name as wanted
  List<Product> getProductsByName(Product product) throws JsonSyntaxException,
      IOException;

  // Returns x products under the wanted category
  List<Product> getProductsByCategory(Product product)
      throws JsonSyntaxException, IOException;

  // the function "getProductsByName&Category" was deleted, instead use
  // list1.retainAll(list2)

  // Returns X most recent added products
  List<Product> getXRecentProducts(int x) throws JsonSyntaxException,
      IOException;

  // Returns X random products
  List<Product> getXRandomProducts(int x) throws JsonSyntaxException,
      IOException;

  // Add a product to data base
  ReturnCode addProduct(Product product) throws IOException;

  // Returns all products that their field: sellerID equal to given id and
  // the boolean field: sold is true
  List<Product> getSoldProductsBySellerID(Product product)
      throws JsonSyntaxException, IOException;

  // Returns all products that their field: buyerID equal to given id and
  // the boolean field: sold is true
  List<Product> getPurchasedProductsByBuyerID(Product product)
      throws JsonSyntaxException, IOException;

  // Returns all products that their field: sellerID equal to given id and
  // the boolean field: sold is false
  List<Product> getPublishedProductsBySellerID(Product product)
      throws JsonSyntaxException, IOException;

  // Remove a product with the wanted id from database
  ReturnCode removeProduct(Product product) throws IOException;

  // Update product as sold and
  ReturnCode buyProduct(Product product) throws IOException;

}
