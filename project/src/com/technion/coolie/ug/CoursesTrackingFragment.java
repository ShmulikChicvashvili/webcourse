package com.technion.coolie.ug;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.technion.coolie.CoolieActivity;
import com.technion.coolie.R;

public class CoursesTrackingFragment extends CoolieActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ug_tracking_list);
        
        
        final Button registrationButton = (Button) findViewById(R.id.ug_button_registrate);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	try {
					HttpResponse h = new UGHttpGet().execute().get();
				} 
            	catch (Exception e)
				{
					e.printStackTrace();
				} 
            }
        });
        
        
        final ListView listview = (ListView) findViewById(R.id.ug_tracking_list);
        
        String[] values = new String[] { "11111", "234503", "234141","234123", "234503", "234141",
        								 "234123", "234503", "234141","234123", "234503", "234141",
        								 "234123", "234503", "234141","234123", "234503", "234141",
        								 "234123", "234503", "234141","234123", "234503", "234141",
        								 "234123", "234503", "234141","234123", "234503", "234141",
        								 "234123", "234503", "234141","234123", "234503", "23414"};

        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
          list.add(values[i]);
        }
       
      //  final TrackingListAdapter adapter = new TrackingListAdapter(this, list);
     //   listview.setAdapter(adapter);
	}
	
}
