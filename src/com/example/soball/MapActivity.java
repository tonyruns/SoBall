package com.example.soball;

//import com.example.mapdemo.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MapActivity extends Activity implements
	OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, LocationListener{

	private GoogleApiClient mGoogleApiClient;
	private Location mLastLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		buildGoogleApiClient();
		mGoogleApiClient.connect();
		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync( this);

		 
	}
	
//	@Override
//	protected void onResume(){
//		mGoogleApiClient.connect();
//	}
	@Override
	public void onMapReady(GoogleMap map) {
    	map.setMyLocationEnabled(true);

		if (mLastLocation != null){
			double lat = mLastLocation.getLatitude();
			double lng = mLastLocation.getLongitude();
			
	    	map.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng) , 14.0f));
		}else{
	    	map.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(0, 0) , 14.0f) );
		}

    }
	
	protected synchronized void buildGoogleApiClient() {
	    mGoogleApiClient = new GoogleApiClient.Builder(this)
	        .addConnectionCallbacks(this)
	        .addOnConnectionFailedListener(this)
	        .addApi(LocationServices.API)
	        .build();
	}
	
	@Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
        	double lat = mLastLocation.getLatitude();
    		double lng = mLastLocation.getLongitude();
    		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
    		mapFragment.getMap().moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng) , 14.0f));
        }
    }
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onLocationChanged(Location location) {
		double lat = mLastLocation.getLatitude();
		double lng = mLastLocation.getLongitude();
		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMap().moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng) , 14.0f));
	    //handleNewLocation(location);
	}
	
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.map, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
