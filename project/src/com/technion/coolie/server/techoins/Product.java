package com.technion.coolie.server.techoins;

public class Product {

  private Long id;
  private String name;
  private String sellerId;
  private String buyerId;
  private Category category;
  private Double price;
  private byte[] image;
  private String descripstion;
  // TODO add tags if we can.
  private String sellerPhoneNumber;
  private String buyerPhoneNumber;
  private boolean sold;
  private long publishDate;
  private long sellDate;

  // private int countViews; TODO

  public Product(String name1, String sellerId1, Category category1,
      Double price1, String descripstion1, String sellerPhoneNumber1,
      byte[] image1) {
    this.name = name1;
    this.sellerId = sellerId1;
    this.buyerId = null;
    this.category = category1;
    this.price = price1;
    this.image = image1;
    this.descripstion = descripstion1;
    // TODO add tags if we can.
    this.sellerPhoneNumber = sellerPhoneNumber1;
    this.buyerPhoneNumber = null;
    this.sold = false;
    this.publishDate = 0; // TODO change to current date in long
    this.sellDate = 0;
  }

  Product() {
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSellerId(String sellerId) {
    this.sellerId = sellerId;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public void setDescripstion(String descripstion) {
    this.descripstion = descripstion;
  }

  public void setSellerPhoneNumber(String sellerPhoneNumber) {
    this.sellerPhoneNumber = sellerPhoneNumber;
  }

  public void setSold(boolean sold) {
    this.sold = sold;
  }

  public void setPublishDate(long publishDate) {
    this.publishDate = publishDate;
  }

  public boolean isSold() {
    return sold;
  }

  public long getPublishDate() {
    return publishDate;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getSellerId() {
    return sellerId;
  }

  public String getBuyerId() {
    return buyerId;
  }

  public Category getCategory() {
    return category;
  }

  public String getCategoryName() {
    return category.toString();
  }

  public Double getPrice() {
    return price;
  }

  public byte[] getImage() {
    return image;
  }

  public String getDescripstion() {
    return descripstion;
  }

  public String getSellerPhoneNumber() {
    return sellerPhoneNumber;
  }

  public void setSold() {
    this.sold = true;
    this.sellDate = 0;
    // TODO handke the sale
  }

  // public int numOfViews(){ //TODO
  // return countViews;
  // }
  // TODO: notifications

  public void setBuyerId(String buyerId2) {
    buyerId = buyerId2;
  }

  public String getBuyerPhoneNumber() {
    return buyerPhoneNumber;
  }

  public void setBuyerPhoneNumber(String buyerPhoneNumber) {
    this.buyerPhoneNumber = buyerPhoneNumber;
  }

  public long getSellDate() {
    return sellDate;
  }

  public void setSellDate(long sellDate) {
    this.sellDate = sellDate;
  }

  public void setImageUrl(byte[] image) {
    this.image = image;
  }
}
