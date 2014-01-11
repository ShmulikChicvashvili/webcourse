package com.technion.coolie.techtrade;

import java.io.Serializable;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.googleapis.media.MediaHttpUploader.UploadState;
import com.technion.coolie.CooliePriority;
import com.technion.coolie.R;
import com.technion.coolie.skeleton.CoolieAsyncRequest;

//TODO: second iteration - add a limitation to "description" field

public class UploadProduct extends TechTradeActivity {
	private ImageView imgV;
	private TextView sName;
	private Spinner categories;
	private EditText pName;
	private EditText pPrice;
	private EditText sellerPhone;
	private EditText des;
	private Uri imageURI;
	private static final int GET_GALLERY_IMAGE = 1000;
	
	String categoriesArray[] = { "Choose a Category",
			Category.LECTURE_SUMMARIES.toString(),
			Category.TUTORIAL_SUMMARIES.toString(),
			Category.GENERAL_SUMMARIES.toString(), Category.BOOKS.toString(),
			Category.CLOTHING.toString(), Category.ELECTRONICS.toString(),
			Category.OFFICE_SUPPLIES.toString(), Category.OTHER.toString() };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_upload_product);

		if (UserOperations.isUserConnected() == false){
			Toast.makeText(getApplicationContext(),
					"Please register to view content", Toast.LENGTH_SHORT).show();
			// Intent myIntent = new Intent(getBaseContext(), .class);//TODO
			// change to the login activity
			// startActivity(myIntent);
		}

		imgV = (ImageView) findViewById(R.id.get_upload_product_img);
		categories = (Spinner) findViewById(R.id.get_upload_product_activity_category_spinner);
		pName = (EditText) findViewById(R.id.get_upload_product_activity_enter_product_name);
		sName = (TextView) findViewById(R.id.get_upload_product_activity_enter_seller_name);
		pPrice = (EditText) findViewById(R.id.get_upload_product_activity_enter_price);
		des = (EditText) findViewById(R.id.get_upload_product_activity_enter_description);
		sellerPhone = (EditText) findViewById(R.id.get_upload_product_activity_enter_seller_number);

		categories.setAdapter(new ArrayAdapter<String>(UploadProduct.this,
				android.R.layout.simple_list_item_1, categoriesArray));

		// TODO get real
		sName.setText(UserOperations.getUserName());
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		// Get resulting image from gallery
		if (requestCode == GET_GALLERY_IMAGE && resultCode == Activity.RESULT_OK) {
			//putting picture in image here
			imageURI = data.getData();
			imgV.setImageURI(imageURI);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate if the process is
		// killed and restarted.
		savedInstanceState.putParcelable("getProductImageURI", imageURI);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// Restore UI state from the savedInstanceState.
		// This bundle has also been passed to onCreate.
		if(savedInstanceState!=null){
			imageURI = savedInstanceState.getParcelable("getProductImageURI");
			imgV.setImageURI(imageURI);
		}
	}

	public void selectImage(View view){
		final Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult( Intent.createChooser(intent, "Complete action using"),
				GET_GALLERY_IMAGE);
	}

	public void submitProduct(View view){
		Product productToUpload = createProductFromData();
		if(!productToUpload.canUpload()){
			informUserOfMissingData(productToUpload);
			return;
		}

		CoolieAsyncRequest UploadProduct = new CoolieAsyncRequest(this,CooliePriority.IMMEDIATELY){
			@Override
			public Void actionOnServer(Void... params) {
				TechTradeServer ttServer = new TechTradeServer();
				//TODO maybe complicate this
				ReturnCode rc = ttServer.addProduct(createProductFromData());
				return null;
			}

			@Override
			public Void onResult(Void result) {
				return null;
			}
		};
		
		UploadProduct.run();
		Intent homeIntent = new Intent(this, MainActivity.class);
		startActivity(homeIntent);
	}

	protected Product createProductFromData(){
		Product product = new Product();
		product.setName(pName.getText().toString());
		if(pPrice.getText().toString() == null || pPrice.getText().toString().length() == 0){
			product.setPrice(null);
		}else{
			product.setPrice(Double.parseDouble(pPrice.getText().toString()));
		}
		product.setSellerPhoneNumber(sellerPhone.getText().toString());
		product.setCategory(Category.getValueOf(categories.getSelectedItem().toString()));
		product.setDescripstion(des.getText().toString());
		if(imageURI != null){
			imgV.buildDrawingCache();
			product.setImage(imgV.getDrawingCache());
		}else{
			product.setImage(null);
		}
		// TODO get id and name and save it from real place
		product.setSellerName(UserOperations.getUserName());
		product.setSellerId(UserOperations.getUserId());

		return product;
	}

	protected void informUserOfMissingData(Product product){
		String errorMsg = "";
		if(product.getName().length() == 0 || product.getName()==null){
			errorMsg = addToErrorMessge(errorMsg, "name");
		}
		if(product.getPrice() == null){
			errorMsg = addToErrorMessge(errorMsg, "price");
		}
		if(product.getSellerPhoneNumber().length() == 0 || product.getSellerPhoneNumber()==null){
			errorMsg = addToErrorMessge(errorMsg, "phone number");
		}
		if(product.getCategory() == Category.INVALID || product.getCategory()==null){
			errorMsg = addToErrorMessge(errorMsg, "catagory");
		}
		if(product.getDescripstion().length() == 0 || product.getDescripstion()==null){
			errorMsg = addToErrorMessge(errorMsg, "description");			
		}
		if(product.getImage()==null){
			errorMsg = addToErrorMessge(errorMsg, "image");			
		}
		if(product.getSellerName().length() == 0 || product.getSellerName()==null){
			errorMsg = addToErrorMessge(errorMsg, "seller name");
		}
		if(product.getSellerId().length() == 0 || product.getSellerId()==null){
			errorMsg = addToErrorMessge(errorMsg, "seller id");
		}
		Toast.makeText(getApplicationContext(),
				errorMsg+".", Toast.LENGTH_SHORT).show();

	}

	protected String addToErrorMessge(String errorMsg, String missingField){
		if(errorMsg.length()==0){
			return (missingField + " is missing");
		}else{
			return (errorMsg + ", " + missingField + " is missing");
		}
	}
}