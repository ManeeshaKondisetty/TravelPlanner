package com.umkc.travelplanner.eat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.umkc.travelplanner.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class EatFragment extends Fragment {

    private EatViewModel mEatViewModel;
    private Context mContext;
    private static final String TAG = "EAT";
    private final String BASE_URL = "https://api.foursquare.com/v2/venues/search/";
    private final String ID = "client_id=OO2P1I0OMSUPC1RDII0B35LZ1A0XVKNJUPBEPGKRMW1CC3J2";
    private final String KEY = "client_secret=KMBRXGGGSIAL5AL25YZIURABMKFSHU4ZVAULIRXB0QB131CM";
    private final String MISC = "v=20180928&limit=10&&near=kansas"
            + "&query=pizza";
    private final String url = "https://api.foursquare.com/v2/venues/search/?&client_id=OO2P1I0OMSUPC1RDII0B35LZ1A0XVKNJUPBEPGKRMW1CC3J2&client_secret=KMBRXGGGSIAL5AL25YZIURABMKFSHU4ZVAULIRXB0QB131CM&v=20180928&limit=10&&near=";

    private String addOnForFood =  "&query=";
    ArrayList<Venue> venueList;
    String mFood;
    String mPlace;
    EditText mPlaceView;
    EditText mFoodView;
    ListView mListView;
    Button mGoButton;
    Button mHistory;

    boolean flag = true;
    String mLongitude;
    String mLatitude;
    final String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
    LocationManager mLocationManager;
    LocationListener mLocationListener;
    // Time between location updates (5000 milliseconds or 5 seconds)
    final long MIN_TIME = 10;
    // Distance between location updates (1000m or 1km)
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 123;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //TODO change to your layout
        Log.d(TAG, "onCreateView: ");
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_eat, null);

        mEatViewModel = new ViewModelProvider(requireActivity()).get(EatViewModel.class);
        //TODO findViewById's here
        mContext = getContext();
        mPlaceView = (EditText) root.findViewById(R.id.place);
        mFoodView = (EditText) root.findViewById(R.id.food);
        mGoButton = (Button) root.findViewById(R.id.button);
        mHistory = (Button) root.findViewById((R.id.history));
        mListView = (ListView) root.findViewById(R.id.listview);

        //view model sync
        if(mEatViewModel.getFood() != null)
            mFoodView.setText(mEatViewModel.getFood());
        if(mEatViewModel.getPlace() != null)
            mPlaceView.setText(mEatViewModel.getPlace());
        if(mEatViewModel.getAdaptor() != null)
            mListView.setAdapter(mEatViewModel.getAdaptor());



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Venue v = (Venue)parent.getItemAtPosition(position);
                String formattedaAddress = v.getFormattedaAddress();
                String name = v.getName();
                Log.d(TAG, "onItemClick: " + formattedaAddress);
                DBHelper dbHelper = new DBHelper(getContext());
                boolean add = dbHelper.addVenue(v);
                Log.d(TAG, "onItemClick: add: " + add);
                Uri gmmIntentUri = Uri.parse("geo:0,0" + "?q=" + name + " " + formattedaAddress);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        mHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(getContext());
                List<Venue> history = dbHelper.fetch();
                CustomAdaptor adaptorC = new CustomAdaptor(getContext(), R.layout.eat_list_item, (ArrayList<Venue>) history);
                mListView.setAdapter(adaptorC);
                mEatViewModel.setAdaptor(adaptorC);
            }
        });

        mGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlFinal = url;
                mPlace = mPlaceView.getText().toString();
                mFood = mFoodView.getText().toString();
                mEatViewModel.setPlace(mPlace);
                mEatViewModel.setFood(mFood);
                String addOnForFood =  "&query=";
                urlFinal = url + mPlace;
                if(!(mFood.isEmpty())) {
                    urlFinal = urlFinal + addOnForFood + mFood;
                }
                Log.d(TAG, "onClick: url:   " + urlFinal);
                AsyncHttpClient client = new AsyncHttpClient();
                client.get( urlFinal, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            JSONArray vennueArray = response.getJSONObject("response").getJSONArray("venues");
                            String[] items = new String[vennueArray.length()];
                            JSONObject jsonObject;
                            venueList = new ArrayList<>();
                            for(int i = 0; i < vennueArray.length(); i++) {
                                jsonObject = vennueArray.getJSONObject(i);
                                JSONObject location = jsonObject.getJSONObject("location");
                                String name = jsonObject.getString("name");
                                String contextLine = "";
                                String formattedAddress = "";
                                JSONArray category = jsonObject.getJSONArray("categories");
                                for(int j=0; j<category.length(); j++) {
                                    contextLine = category.getJSONObject(j).getString("name");
                                }
                                for( int j = 0; j < location.getJSONArray("formattedAddress").length(); j++) {
                                    formattedAddress += location.getJSONArray("formattedAddress").get(j).toString() + " ";
                                }

                                Venue v = new Venue();
                                v.setName(name);
                                v.setContextLine(contextLine);
                                v.setFormattedaAddress(formattedAddress);
                                venueList.add(v);
                            }
                            CustomAdaptor adaptorC = new CustomAdaptor(getContext(), R.layout.eat_list_item, venueList);
                            mListView.setAdapter(adaptorC);
                            mEatViewModel.setAdaptor(adaptorC);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onSuccess: catch: " + e + "\n" + e.getStackTrace());
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d(TAG, "onFailure: code:" + statusCode);
                        Log.d(TAG, "onFailure: " + errorResponse);
                    }
                });
            }
        });


          return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO Your logic here
        if(mEatViewModel.isFlag())
            getLocation();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Log.d(TAG, "getLocationBasedVenues: ");
        mLocationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "onLocationChanged");
                mLongitude = String.valueOf(location.getLongitude());
                mLatitude = String.valueOf(location.getLatitude());
                Log.d(TAG, "onLocationChanged: latitude is " + mLongitude);
                Log.d(TAG, "onLocationChanged: longitude is " + mLatitude);
                getResultsUsingLocation();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Log.d(TAG, "onProviderDisabled() is called");
            }
        };
        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);
    }

    private void getResultsUsingLocation() {
        if (flag) {
            mLatitude = "39.03565142";
            mLongitude = "-94.58906533";
            flag = false;
            mEatViewModel.setFlag(false);
            String url = "https://api.foursquare.com/v2/venues/explore?&client_id=OO2P1I0OMSUPC1RDII0B35LZ1A0XVKNJUPBEPGKRMW1CC3J2&client_secret=KMBRXGGGSIAL5AL25YZIURABMKFSHU4ZVAULIRXB0QB131CM&v=20180928&ll=" +
                    mLatitude + "," + mLongitude + "&radius=1000&limit=10";
            Log.d(TAG, "getResultsUsingLocation: " + url);
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONArray groupsArray = response.getJSONObject("response").getJSONArray("groups").getJSONObject(0).getJSONArray("items");
                        JSONObject jsonObject;
                        venueList = new ArrayList<>();
                        for(int i = 0; i < groupsArray.length(); i++) {
                            jsonObject = groupsArray.getJSONObject(i).getJSONObject("venue");
                            JSONObject location = jsonObject.getJSONObject("location");
                            String name = jsonObject.getString("name");
                            String contextLine = "";
                            String formattedAddress = "";
                            JSONArray category = jsonObject.getJSONArray("categories");
                            for(int j=0; j<category.length(); j++) {
                                contextLine = category.getJSONObject(j).getString("name");
                            }
                            for( int j = 0; j < location.getJSONArray("formattedAddress").length(); j++) {
                                formattedAddress += location.getJSONArray("formattedAddress").get(j).toString() + " ";
                            }
                            Venue v = new Venue();
                            v.setName(name);
                            v.setContextLine(contextLine);
                            v.setFormattedaAddress(formattedAddress);
                            venueList.add(v);
                        }
                        CustomAdaptor adaptorC = new CustomAdaptor(getContext(), R.layout.eat_list_item, venueList);
                        mListView.setAdapter(adaptorC);
                        mEatViewModel.setAdaptor(adaptorC);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d(TAG, "onSuccess: catch: " + e + "\n" + e.getStackTrace());
                    }
                }
            });
        }

    }

}
