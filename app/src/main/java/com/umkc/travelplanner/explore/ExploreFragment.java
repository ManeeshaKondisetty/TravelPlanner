package com.umkc.travelplanner.explore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.umkc.travelplanner.R;
import com.umkc.travelplanner.eat.CustomAdaptor;
import com.umkc.travelplanner.eat.DBHelper;
import com.umkc.travelplanner.eat.Venue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ExploreFragment extends Fragment {
    private final String TAG = "EXPLORE";
    private ListView mListView;
    private HashMap<String, String> mStateNameToCodeMap;
    private final String url1 = "https://developer.nps.gov/api/v1/parks?stateCode=";
    private final String url2 = "&api_key=nOaxZahJW1mqUZxcCSCAR5DRmQjzUtUUBZQfkvT6";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //TODO change to your layout
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_explore, null);
        //TODO findViewById's here
        mListView = root.findViewById(R.id.listView);
        //initializing map with state codes
        createStateCodes();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Activity a = (Activity) parent.getItemAtPosition(position);
                String url = ((Activity) parent.getItemAtPosition(position)).getDirectionsUri();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        //TODO get state code from UI(spinner)
        String stateCode = "ks";

        //api call and update adaptor with results
        fetchResults(stateCode);


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO Your logic here
    }

    public void fetchResults(String stateCode) {
        String urlFinal = url1 + stateCode + url2;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get( urlFinal, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, "onSuccess: " + statusCode);

                try {
                    List<Activity> activityList = new ArrayList<>();
                    JSONArray datas = response.getJSONArray("data");
                    for(int k=0; k< datas.length(); k++) {
                        JSONObject data = datas.getJSONObject(k);
                        Activity a = new Activity();
                        a.setFullName(data.getString("fullName"));
                        a.setDescription(data.getString("description"));
                        a.setDirectionsUri(data.getString("directionsUrl"));
                        JSONArray activities = data.getJSONArray("activities");
                        int len = activities.length();
                        String[] activity = new String[len];
                        for(int i=0; i<len; i++) {
                            activity[i] = activities.getJSONObject(i).getString("name");
                        }
                        a.setActivities(activity);
                        activityList.add(a);

                    }
                    ActivityAdapter adaptorA = new ActivityAdapter(getContext(), R.layout.explore_list_item, (ArrayList<Activity>) activityList);
                    mListView.setAdapter(adaptorA);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, "onFailure: code:" + statusCode);
                Log.d(TAG, "onFailure: " + errorResponse);
            }
        });
    }

    private void createStateCodes() {
        mStateNameToCodeMap = new HashMap<>();
        mStateNameToCodeMap.put("Alabama","AL");
        mStateNameToCodeMap.put("Alaska","AK");
        mStateNameToCodeMap.put("Arizona","AZ");
        mStateNameToCodeMap.put("Arkansas","AR");
        mStateNameToCodeMap.put("California","CA");
        mStateNameToCodeMap.put("Colorado","CO");
        mStateNameToCodeMap.put("Connecticut","CT");
        mStateNameToCodeMap.put("Delaware","DE");
        mStateNameToCodeMap.put("District Of Columbia","DC");
        mStateNameToCodeMap.put("Florida","FL");
        mStateNameToCodeMap.put("Georgia","GA");
        mStateNameToCodeMap.put("Hawaii","HI");
        mStateNameToCodeMap.put("Idaho","ID");
        mStateNameToCodeMap.put("Illinois","IL");
        mStateNameToCodeMap.put("Indiana","IN");
        mStateNameToCodeMap.put("Iowa","IA");
        mStateNameToCodeMap.put("Kansas","KS");
        mStateNameToCodeMap.put("Kentucky","KY");
        mStateNameToCodeMap.put("Louisiana","LA");
        mStateNameToCodeMap.put("Maine","ME");
        mStateNameToCodeMap.put("Maryland","MD");
        mStateNameToCodeMap.put("Massachusetts","MA");
        mStateNameToCodeMap.put("Michigan","MI");
        mStateNameToCodeMap.put("Minnesota","MN");
        mStateNameToCodeMap.put("Mississippi","MS");
        mStateNameToCodeMap.put("Missouri","MO");
        mStateNameToCodeMap.put("Montana","MT");
        mStateNameToCodeMap.put("Nebraska","NE");
        mStateNameToCodeMap.put("Nevada","NV");
        mStateNameToCodeMap.put("New Hampshire","NH");
        mStateNameToCodeMap.put("New Jersey","NJ");
        mStateNameToCodeMap.put("New Mexico","NM");
        mStateNameToCodeMap.put("New York","NY");
        mStateNameToCodeMap.put("North Carolina","NC");
        mStateNameToCodeMap.put("North Dakota","ND");
        mStateNameToCodeMap.put("Ohio","OH");
        mStateNameToCodeMap.put("Oklahoma","OK");
        mStateNameToCodeMap.put("Oregon","OR");
        mStateNameToCodeMap.put("Pennsylvania","PA");
        mStateNameToCodeMap.put("Rhode Island","RI");
        mStateNameToCodeMap.put("South Carolina","SC");
        mStateNameToCodeMap.put("South Dakota","SD");
        mStateNameToCodeMap.put("Tennessee","TN");
        mStateNameToCodeMap.put("Texas","TX");
        mStateNameToCodeMap.put("Utah","UT");
        mStateNameToCodeMap.put("Vermont","VT");
        mStateNameToCodeMap.put("Virginia","VA");
        mStateNameToCodeMap.put("Washington","WA");
        mStateNameToCodeMap.put("West Virginia","WV");
        mStateNameToCodeMap.put("Wisconsin","WI");
        mStateNameToCodeMap.put("Wyoming","WY");
    }

}
