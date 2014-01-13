package com.technion.coolie.techtrade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.graphics.Bitmap;


public class Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -121600915135933398L;
	//product details
	private Long 		id;
	private String		name;
	private Double 		price;
	private Category 	category;
	private String 		descripstion;
	private byte[] 		imageByteArray;
	private long 		publishDateInMillis;
	private long 		sellDateInMillis;
	private boolean 	sold;
	//seller details
	private String		sellerId;
	private String 		sellerName;
	private String 		sellerPhoneNumber;
	//buyer details
	private String 		buyerId;
	private String 		buyerName;
	private String 		buyerPhoneNumber;
	// TODO add tags if we can.
	// TODO private int countViews; 
	//TODO make sure that omer sets the id

	//has to be here
	Product(){
		productInitAux(null, null, null, null, null,null,null);		
		this.imageByteArray = null;
	};

	Product(Product p){
		id=p.getId();
		name=p.getName();
		price=p.getPrice();
		category=p.getCategory();
		descripstion=p.getDescripstion();
		imageByteArray=p.getImageByteArray();
		sold=p.isSold();
		sellerId=p.getSellerId();
		sellerName=p.getSellerName();
		sellerPhoneNumber=p.getSellerPhoneNumber();
		buyerId=p.getBuyerId();
		buyerName=p.getBuyerName();
		buyerPhoneNumber=p.getBuyerPhoneNumber();
	};

	public Product(String name1, String sellerId1, Category category1, Double price1, String descripstion1,String sellerPhoneNumber1, byte[] image1, String sellerName) {
		productInitAux(name1, sellerId1, category1, price1, descripstion1,sellerPhoneNumber1,sellerName);
		this.imageByteArray = image1;
	}

	public Product(String name1, String sellerId1, Category category1, Double price1, String descripstion1,String sellerPhoneNumber1, String url, String sellerName) {
		productInitAux(name1, sellerId1, category1, price1, descripstion1,sellerPhoneNumber1,sellerName);
		this.imageByteArray = BitmapOperations.decodeToByteArray(BitmapOperations.decodeBitmapFromFile(url, 0, 0));
	}

	public Product(String name1, String sellerId1, Category category1, Double price1, String descripstion1,String sellerPhoneNumber1, Bitmap bitmap, String sellerName) {
		productInitAux(name1, sellerId1, category1, price1, descripstion1,sellerPhoneNumber1,sellerName);
		this.imageByteArray = BitmapOperations.decodeToByteArray(bitmap);
	}

	private void productInitAux(String name1, String sellerId1,
			Category category1, Double price1, String descripstion1,
			String sellerPhoneNumber1, String sellerName) {
		this.id = null;
		this.name = name1;
		this.price = price1;
		this.category = category1;
		this.descripstion = descripstion1;
		this.publishDateInMillis = Calendar.getInstance().getTimeInMillis();

		this.sold = false;
		this.sellDateInMillis = 0;

		this.sellerId = sellerId1;
		this.sellerName = sellerName;
		this.sellerPhoneNumber = sellerPhoneNumber1;

		this.buyerId = null;
		this.buyerName = null;
		this.buyerPhoneNumber = null;
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

	public void setImageByteArray(byte[] image) {
		this.imageByteArray = image;
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

	public void setPublishDateInMillis(long publishDate) {
		this.publishDateInMillis = publishDate;
	}

	public boolean isSold() {
		return sold;
	}

	public long getPublishDateInMillis() {
		return publishDateInMillis;
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
	
	public String getPriceString() {
		return Utils.parseDoubleToString(this.getPrice());
	}

	public byte[] getImageByteArray() {
		return imageByteArray;
	}



	public String getDescripstion() {
		return descripstion;
	}

	public String getSellerPhoneNumber() {
		return sellerPhoneNumber;
	}

	public void setSold() {
		this.sold = true;
		this.sellDateInMillis = Calendar.getInstance().getTimeInMillis();
		//TODO handle the sale
	}

	public void setBuyerId(String buyerId2) {
		buyerId = buyerId2;
	}

	public String getBuyerPhoneNumber() {
		return buyerPhoneNumber;
	}

	public void setBuyerPhoneNumber(String buyerPhoneNumber) {
		this.buyerPhoneNumber = buyerPhoneNumber;
	}

	public String getSellDate() {
		return formatMillisToDate(sellDateInMillis);
	}

	public String getPublishDate() {
		return formatMillisToDate(publishDateInMillis);
	}

	private String formatMillisToDate(Long millis) {
		String dateFormat = "dd/MM/yyyy";
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(millis);
	}

	public long getSellDateInMillis() {
		return sellDateInMillis;
	}

	public void setSellDateInMillis(long sellDate) {
		this.sellDateInMillis = sellDate;
	}

	public Bitmap getImage() {
		if(this.imageByteArray == null) return null;
		return BitmapOperations.decodeByteArray(this.imageByteArray);
	}

	public void setImage(Bitmap image) {
		this.imageByteArray = BitmapOperations.decodeToByteArray(image);
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public boolean canUpload(){
		return (name != null && price!= null && category != Category.INVALID &&
				/*descripstion != null && imageByteArray != null &&*/ sellerId != null && 
				sellerName != null && sellerPhoneNumber != null);
	}

	// public int numOfViews(){ //TODO
	// return countViews;
	// }
	// TODO: notifications
}
