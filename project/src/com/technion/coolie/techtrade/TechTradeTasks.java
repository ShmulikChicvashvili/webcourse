package com.technion.coolie.techtrade;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class TechTradeTasks {
	//TODO work directly with token id, name for transfer funds
	//TODO work with cash images
	//TODO change work flow when clicking on menu buttons
	//TODO add interaction with coolie activity
	//TODO add default picture
	//TODO second iteration load images using async
	//TODO partial loading of lists and grids
	//TODO bring previous screens to top instead of reloading them
	
	//TODO eran,tivan when sending to login screen we need to allow the user to cancel and go back to main screen
	//TODO add lines beneath list  title in the welcome screen
	//TODO guy make the product name in the product page bigger (tablet)
	//TODO guy rmove toasts
	//TODO tivan transfer horizontal looks bad
	//TODO tivan upload product looks bad
	//TODO tivan change default "please choose category", if they don't choose then don't let them submit"
	//TODO tivan change product name to one line so when we hit enter it continues to the next field
	//TODO tivan upload product - price field isn't set correctly. it should recieve only digits and ->dot<-.
	//TODO tivan your number field shoukd be only numbers
	//TODO tivan transfer funds submit is ugly
	//TODO guy change hint in browse
	//TODO guy add category name title in browse by category
	
	/*class asycTaskTest extends AsyncTask<Product, Void, List<Product>> {
		@Override
		protected void onPreExecute() {
			Toast.makeText(MainActivity.this, "starting connection", Toast.LENGTH_SHORT).show();
			pd = ProgressDialog.show(MainActivity.this, null, "Loading main activity...");
			pd.requestWindowFeature((int) Window.FEATURE_NO_TITLE);
		}

		@Override
		protected List<Product> doInBackground(Product... p) {
			TechTradeServer ttServer = new TechTradeServer();
//			Bitmap bmp = BitmapOperations.decodeBitmapFromResource(MainActivity.this.getResources(), R.drawable.skel_module5, 0, 0);
			Product p1 = null;
			List<Product> l = null;
			for(int i=0;i<10;++i){
				ttServer.addProduct(new Product("product name "+i, "Seller id"+i, Category.CLOTHING, (double) i, "some description"+i+"\r\nand another line",""+i, (byte[])null, "seller name "+i));
			}
//			l = ttServer.getXRecentProducts(100);
//			for (Product product : l) {
//				ttServer.removeProduct(product);
//			}
//			l = ttServer.getXRecentProducts(100);
			p1 = new Product("tivan", "tivan", Category.CLOTHING, (double) 100, "some description\r\nand another line",""+100, (byte[])null, "tivan");
			ttServer.addProduct(p1);
			l = ttServer.getProductsByName(p1);
			ttServer.removeProduct(l.get(0));
			l = ttServer.getXRecentProducts(10);
			
			return l;
		}

		@Override
		protected void onPostExecute(List<Product> rc) {
			Toast.makeText(MainActivity.this, "starting to finish", Toast.LENGTH_SHORT).show();
			Log.v("Eran", "guy!"+rc.size());
			for (Product product : rc) {
				//Log.v("Eran",product.getName());
			}
			Toast.makeText(MainActivity.this, "finished", Toast.LENGTH_SHORT).show();
		}
	}
	new asycTaskTest().execute();*/
}
