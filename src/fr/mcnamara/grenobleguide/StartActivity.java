package fr.mcnamara.grenobleguide;
 
import android.app.Activity; 
import android.app.ProgressDialog;
import android.content.Intent; 
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider; 
import android.net.Uri;
import android.os.Bundle;  
import android.util.Log;
import android.view.View; 
import android.widget.Button; 
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends Activity  implements LocationListener {
	private static final String TAG = "StartActivity:";
	double lat, lon = 0.0;
	Bundle valsin = new Bundle();
	final int VISIBLE = 0;
	final int INVISIBLE = 1;
	ProgressDialog dialog;
	TextView txtInfo;
	LocationManager lm;
	StringBuilder sb;
	int noOfFixes = 0;
	Button myButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);

         setContentView(R.layout.main);
        
       	 myButton = (Button) findViewById(R.id.GPS); 
    	 //myButton.setVisibility(INVISIBLE);
    	 
    	 dialog = ProgressDialog.show(this, "",  "Waiting for location...", true);
         dialog.setCancelable(true);   // 1.2
         dialog.show();         
        
         lm = (LocationManager) getSystemService(LOCATION_SERVICE);  
}
     

public void onNorthClick(View view)  {  
	    //Log.v(TAG, "North clicked!");  
        Intent myIntent = new Intent(view.getContext(), NoDistanceListView.class);

	    valsin.putInt("nsew", 0);
	    
        myIntent.putExtras(valsin);  
        startActivityForResult(myIntent, 0);
    }

    public void onSouthClick(View view)  
    {  
    	//Log.v(TAG, "South clicked!");  
        Intent myIntent = new Intent(view.getContext(), NoDistanceListView.class);

	    valsin.putInt("nsew", 1); 
        myIntent.putExtras(valsin); 
        startActivityForResult(myIntent, 0);
    }  

    public void onEastClick(View view)  
    {  
    	//Log.v(TAG, "East clicked!");  
        Intent myIntent = new Intent(view.getContext(), NoDistanceListView.class);

	    valsin.putInt("nsew", 2); 
        myIntent.putExtras(valsin); 
        startActivityForResult(myIntent, 0);
    }  

    public void onWestClick(View view)  
    {  
    	//Log.v(TAG, "West clicked!");  
        Intent myIntent = new Intent(view.getContext(), NoDistanceListView.class);

	    valsin.putInt("nsew", 3); 
        myIntent.putExtras(valsin); 
        startActivityForResult(myIntent, 0);
    }  

    public void onGPSClick(View view)  
    {  
    	//Log.v(TAG, "GPS clicked!");  
    	Intent myIntent = new Intent(view.getContext(), DistanceListView.class);  

	    valsin.putInt("nsew", 4); 
        myIntent.putExtras(valsin); 
        startActivityForResult(myIntent, 0);
    }
    

    
    @Override
	protected void onResume() {
		/*
		 * onResume is is always called after onStart, even if the app hasn't been
		 * paused
		 *
		 * add location listener and request updates every 1000ms or 10m
		 */
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10f, this);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10f, this);
		super.onResume();
	}

	@Override
	protected void onPause() {
		/* GPS, as it turns out, consumes battery like crazy */
		lm.removeUpdates(this);
		super.onResume();
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.v(TAG, "Location Changed");
		myButton.setVisibility( VISIBLE ); 
		noOfFixes++;

		/* display some of the data in the TextView */ 
     	 lon = location.getLongitude();
       	 lat = location.getLatitude();
       	 valsin.putDouble("lat", lat);
       	 valsin.putDouble("lon", lon); 
       	 Log.d(TAG, "set lat,lon:"+lat+","+lon);
         myButton.setVisibility(VISIBLE);
         dialog.dismiss(); 
         //txtInfo.setText("set lat,lon:"+lat+","+lon);
	}

	@Override
	public void onProviderDisabled(String provider) {
		/* this is called if/when the GPS is disabled in settings */
		Log.v(TAG, "Disabled");
		/* bring up the GPS settings */
		Toast.makeText(this, "GPS Disabled..", Toast.LENGTH_SHORT).show();
		//Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		//startActivity(intent);
	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.d(TAG, "Enabled");
		Toast.makeText(this, "GPS Enabled", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		/* This is called when the GPS status alters */
		switch (status) {
		case LocationProvider.OUT_OF_SERVICE:
			Log.d(TAG, "Status Changed: Out of Service");
			//Toast.makeText(this, "Status Changed: Out of Service",
			//Toast.LENGTH_SHORT).show();
			break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			Log.d(TAG, "Status Changed: Temporarily Unavailable");
			//Toast.makeText(this, "Status Changed: Temporarily Unavailable",
			//Toast.LENGTH_SHORT).show();
			break;
		case LocationProvider.AVAILABLE:
			Log.d(TAG, "Status Changed: Available");
			//Toast.makeText(this, "Status Changed: Available",
			//Toast.LENGTH_SHORT).show();
			break;
		}
	}

	@Override
	protected void onStop() {
		//finish();
		super.onStop();
	}

}