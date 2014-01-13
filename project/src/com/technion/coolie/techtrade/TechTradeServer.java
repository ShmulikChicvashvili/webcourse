package com.technion.coolie.techtrade;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.technion.coolie.server.Communicator;

/**
 * 
 * Created on 7/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public class TechTradeServer implements ITechTradeServerAPI {

	Gson gson = new Gson();

	@Override
	public ReturnCode addAccount(BankAccount account, String password)
			throws IOException {
		return ReturnCode.valueOf(Communicator.execute(
				TechoinsEnum.TECHOINS_SERVLET.value(), "function",
				TechoinsEnum.ADD_ACCOUNT.toString(), TechoinsEnum.BANK_ACCOUNT.value(),
				gson.toJson(account), "password", password));
	}

	@Override
	public ReturnCode removeAccount(BankAccount account) throws IOException {
		return ReturnCode.valueOf(Communicator.execute(
				TechoinsEnum.TECHOINS_SERVLET.value(), "function",
				TechoinsEnum.REMOVE_ACCOUNT.toString(),
				TechoinsEnum.BANK_ACCOUNT.value(), gson.toJson(account)));
	}

	@Override
	public BankAccount getAccount(BankAccount account)
			throws JsonSyntaxException, IOException {
		return gson.fromJson(Communicator.execute(
				TechoinsEnum.TECHOINS_SERVLET.value(), "function",
				TechoinsEnum.GET_ACCOUNT.toString(), TechoinsEnum.BANK_ACCOUNT.value(),
				gson.toJson(account)), BankAccount.class);
	}

	@Override
	public ReturnCode moveMoney(TechoinsTransfer transfer) throws IOException {
		return ReturnCode.valueOf(Communicator.execute(
				TechoinsEnum.TECHOINS_SERVLET.value(), "function",
				TechoinsEnum.MOVE_MONEY.toString(),
				TechoinsEnum.TECHOINS_TRANSFER.value(), gson.toJson(transfer)));
	}

	@Override
	public List<TechoinsTransfer> getHistory(BankAccount account)
			throws JsonSyntaxException, IOException {
		return gson.fromJson(Communicator.execute(
				TechoinsEnum.TECHOINS_SERVLET.value(), "function",
				TechoinsEnum.GET_HISTORY.toString(), TechoinsEnum.BANK_ACCOUNT.value(),
				gson.toJson(account)), new TypeToken<List<TechoinsTransfer>>() {
			// default usage
		}.getType());
	}

	@Override
	public ReturnCode addProduct(Product product) throws IOException {
		return ReturnCode.valueOf(Communicator.execute(
				TechoinsEnum.TECHOINS_SERVLET.value(), "function",
				TechoinsEnum.ADD_PRODUCT.toString(), TechoinsEnum.PRODUCT.value(),
				gson.toJson(product)));
	}

	@Override
	public ReturnCode removeProduct(Product product) throws IOException {
		return ReturnCode.valueOf(Communicator.execute(
				TechoinsEnum.TECHOINS_SERVLET.value(), "function",
				TechoinsEnum.REMOVE_PRODUCT.toString(), TechoinsEnum.PRODUCT.value(),
				gson.toJson(product)));
	}

	@Override
	public ReturnCode buyProduct(Product product) throws IOException {
		return ReturnCode.valueOf(Communicator.execute(
				TechoinsEnum.TECHOINS_SERVLET.value(), "function",
				TechoinsEnum.BUY_PRODUCT.toString(), TechoinsEnum.PRODUCT.value(),
				gson.toJson(product)));
	}

	@Override
	public List<Product> getProductsByIds(List<Product> products)
			throws JsonSyntaxException, IOException {
		return gson.fromJson(Communicator.execute(
				TechoinsEnum.TECHOINS_SERVLET.value(), "function",
				TechoinsEnum.GET_PRODUCTS_BY_IDS.toString(),
				TechoinsEnum.PRODUCT_LIST.value(), gson.toJson(products)),
				new TypeToken<List<Product>>() {
			// default usage
		}.getType());
	}

	@Override
	public List<Product> getProductsByName(Product product)
			throws JsonSyntaxException, IOException {
		return gson.fromJson(Communicator.execute(
				TechoinsEnum.TECHOINS_SERVLET.value(), "function",
				TechoinsEnum.GET_PRODUCTS_BY_NAME.toString(),
				TechoinsEnum.PRODUCT.value(), gson.toJson(product)),
				new TypeToken<List<Product>>() {
			// default usage
		}.getType());
	}

	@Override
	public List<Product> getProductsByCategory(Product product)
			throws JsonSyntaxException, IOException {
		return gson.fromJson(Communicator.execute(
				TechoinsEnum.TECHOINS_SERVLET.value(), "function",
				TechoinsEnum.GET_PRODUCTS_BY_CATEGORY.toString(),
				TechoinsEnum.PRODUCT.value(), gson.toJson(product)),
				new TypeToken<List<Product>>() {
			// default usage
		}.getType());
	}

	@Override
	public List<Product> getXRecentProducts(int x) throws JsonSyntaxException,
	IOException {
		return gson.fromJson(Communicator.execute(
				TechoinsEnum.TECHOINS_SERVLET.value(), "function",
				TechoinsEnum.GET_X_RECENT_PRODUCTS.toString(), "num",
				((Integer) x).toString()), new TypeToken<List<Product>>() {
			// default usage
		}.getType());
	}

	@Override
	public List<Product> getXRandomProducts(int x) throws JsonSyntaxException,
	IOException {
		return gson.fromJson(Communicator.execute(
				TechoinsEnum.TECHOINS_SERVLET.value(), "function",
				TechoinsEnum.GET_X_RANDOM_PRODUCTS.toString(), "num",
				((Integer) x).toString()), new TypeToken<List<Product>>() {
			// default usage
		}.getType());
	}

	@Override
	public List<Product> getSoldProductsBySellerID(Product product)
			throws JsonSyntaxException, IOException {
		return gson.fromJson(Communicator.execute(
				TechoinsEnum.TECHOINS_SERVLET.value(), "function",
				TechoinsEnum.GET_SOLD_PRODUCTS_BY_SELLER_ID.toString(),
				TechoinsEnum.PRODUCT.value(), gson.toJson(product)),
				new TypeToken<List<Product>>() {
			// default usage
		}.getType());
	}

	@Override
	public List<Product> getPurchasedProductsByBuyerID(Product product)
			throws JsonSyntaxException, IOException {
		return gson.fromJson(Communicator.execute(
				TechoinsEnum.TECHOINS_SERVLET.value(), "function",
				TechoinsEnum.GET_PURCHASED_PRODUCTS_BY_BUYER_ID.toString(),
				TechoinsEnum.PRODUCT.value(), gson.toJson(product)),
				new TypeToken<List<Product>>() {
			// default usage
		}.getType());
	}

	@Override
	public List<Product> getPublishedProductsBySellerID(Product product)
			throws JsonSyntaxException, IOException {
		return gson.fromJson(Communicator.execute(
				TechoinsEnum.TECHOINS_SERVLET.value(), "function",
				TechoinsEnum.GET_PUBLISHED_PRODUCTS_BY_SELLER_ID.toString(),
				TechoinsEnum.PRODUCT.value(), gson.toJson(product)),
				new TypeToken<List<Product>>() {
			// default usage
		}.getType());
	}

}
