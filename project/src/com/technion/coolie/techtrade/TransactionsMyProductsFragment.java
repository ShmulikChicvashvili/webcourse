package com.technion.coolie.techtrade;

import java.util.List;
import java.util.Vector;

import com.technion.coolie.R;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListAdapter;

public class TransactionsMyProductsFragment extends TransactionsFragment {
	
	@Override
	protected void initialize() {
		initializeProductsVector();
	}

	private void initializeProductsVector() {
		/*Default behaviour if we don't have any server
		Bitmap bmp = BitmapOperations.decodeBitmapFromResource(this.getResources(), R.drawable.skel_module5, 0, 0);
		for(int i=0;i<15;++i){
			this.productVector.add(new Product("?product name "+i, "Seller id"+i, Category.CLOTHING, (double) i, "some description"+i+"\r\nand another line",""+i, bmp, "seller name "+i));
		}*/
		

		/*default behaviour in case we don't have any server
		Bitmap bmp = BitmapOperations.decodeBitmapFromResource(this.getResources(), R.drawable.skel_module5, 0, 0);
		for(int i=0;i<20;++i){
			this.productVector.add(new Product(">product name "+i, "Seller id"+i, Category.CLOTHING, (double) i, "some description"+i+"\r\nand another line",""+i, bmp, "seller name "+i));
			this.productVector.elementAt(i).setSellDateInMillis(Calendar.getInstance().getTimeInMillis());
			this.productVector.elementAt(i).setBuyerName("Moshe Cohen "+i);
		}*/
		//TODO extract this class up in ladder
		class GetMyItemsAsyncTask extends AsyncTask<Product, Void, Void> {
			@Override
			protected void onPreExecute() {
				//TODO auto created method stub
			}

			@Override
			protected Void doInBackground(Product... products) {
				TechTradeServer ttServer = new TechTradeServer();
				//List<Product> myProductsList = (List<Product>) ttServer.getPublishedProductsBySellerID(products[0]);
				List<Product> myProductsList = (List<Product>) ttServer.getXRecentProducts(10);
				if(myProductsList == null){
					myProductsList = new Vector<Product>();
				}
				for (Product product : myProductsList) {
					productVector.add(product);
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void v) {
				adapter.notifyDataSetChanged();
			}
		}

		Product product = new Product(null, UserOperations.getUserId(), null, null, null, null, (byte[])null, null);
		new GetMyItemsAsyncTask().execute(product); 
	}
	
	@Override
	protected TransactionsFragmentAdapter getGridAdapter() {
		return new TransactionsFragmentProductAdapter(getActivity(), this.productVector, R.layout.get_transactions_grid_item);
	}

	@Override
	protected OnItemClickListener getGridOnItemClickListener() {
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> gridView, View view,int pos, long id) {
				showDialogFragment(productVector.elementAt(pos));
			}
		};
	}

	@Override
	protected TransactionsDialogFragment getDialogFragment() {
		return new TransactionsMyProductsDialogFragment();
	}

	@Override
	protected String getDialogName() {
		return "my_products_dialog_fragment";
	}
}
