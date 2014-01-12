package com.technion.coolie.techtrade;

/**
 * 
 * Created on 7/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public enum TechoinsEnum {
  ADD_ACCOUNT("addAccount"), REMOVE_ACCOUNT("removeAccount"), GET_ACCOUNT(
      "getAccount"), MOVE_MONEY("moveMoney"), GET_HISTORY("getHistory"), BANK_ACCOUNT(
      "BankAccount"), TECHOINS_SERVLET("Techoins"), TECHOINS_TRANSFER(
      "TechoinsTransfer"), ADD_PRODUCT("addProduct"), PRODUCT("Product"), REMOVE_PRODUCT(
      "removeProduct"), BUY_PRODUCT("buyProduct"), GET_PRODUCTS_BY_IDS(
      "getProductsByIds"), PRODUCT_LIST("ProductList"), GET_PRODUCTS_BY_NAME(
      "getProductsByName"), GET_PRODUCTS_BY_CATEGORY("getProductsByCategory"), GET_X_RECENT_PRODUCTS(
      "getXRecentProducts"), GET_X_RANDOM_PRODUCTS("getXRandomProducts"), GET_SOLD_PRODUCTS_BY_SELLER_ID(
      "getSoldProductsBySellerID"), GET_PURCHASED_PRODUCTS_BY_BUYER_ID(
      "getPurchasedProductsByBuyerID"), GET_PUBLISHED_PRODUCTS_BY_SELLER_ID(
      "getPublishedProductsBySellerID");

  private final String value;

  private TechoinsEnum(String s) {
    value = s;
  }

  public String value() {
    return value;
  }

}
