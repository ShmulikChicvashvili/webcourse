package com.technion.coolie.techtrade;

import java.util.Vector;

public interface ProductApiI {
	//TODO remove this class if it's not used
	
	// Returns a product ptr to product with the same id
	Product getProductById(Long id);

	// Returns multiple products as requested by their ID's
	Vector<Product> getProductsByIds(Vector<Long> pids);

	// Returns x products that have the name as wanted
	Vector<Product> getProductsByName(String name, int from, int to);

	// Returns x products under the wanted category
	Vector<Product> getProductsByCategory(Category category, int from, int to);

	// Returns x products under the wanted category with the same name
	Vector<Product> getProductsByNameAndCategory(String name,
			Category category, int from, int to);

	// Returns X most recent added products
	Vector<Product> getXRecentProducts(int x);

	// Returns X random products
	Vector<Product> getXRandomProducts(int x);

	// Add a product to data base
	Boolean addProduct(Product p);

	// Returns all products that their field: sellerID equal to given id and
	// the boolean field: sold is true
	Vector<Product> getSoldProductsBySellerID(String id);
	
	// Returns all products that their field: buyerID equal to given id and
	// the boolean field: sold is true
	Vector<Product> getPurchasedProductsByBuyerID(String id);
	
	// Returns all products that their field: sellerID equal to given id and
	// the boolean field: sold is false
	Vector<Product> getPublishedProductsBySellerID(String id);

	// Remove a product with the wanted id from database
	Boolean removeProduct(Long id);

	// Update product as sold and
	Boolean buyProduct(Long buyerID, Long productID);

}
