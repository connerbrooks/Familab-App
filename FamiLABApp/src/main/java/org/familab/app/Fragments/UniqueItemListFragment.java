package org.familab.app.Fragments;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.familab.app.FamiRestClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class UniqueItemListFragment extends ListFragment {

    OnUniqueItemSelectedListener mCallback;


    private static final String TAG_ID = "id";
    private static final String TAG_AREA_ID = "area_id";
    private static final String TAG_NAME = "name";
    private static final String TAG_FUID = "fuid";
    private static final String TAG_TICKETABLE = "ticketable";
    private static final String TAG_LOGGABLE = "loggable";

    public final static String EXTRA_NAME = "org.familab.app.NAME";
    public final static String EXTRA_FUID = "org.familab.app.FUID";
    public final static String EXTRA_POSITION = "position";

    public String[] names = null;
    public String[] fuid = null;
    public String[] areaId = null;
    JSONObject cacheObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("oh god", "please");
        try{
            getItemList();
        }catch(JSONException e){
            Log.v("oh no", "it excepted");
        }


    }

    public void getItemList() throws JSONException {
        Log.v("jesus chirst", "omagrafds");
        FamiRestClient.get("unique_items.json", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(JSONObject itemObject) {
                Log.v("yayz", "hopze drms");

                String text = null;

                try {
                    cacheObject = itemObject;

                    JSONArray items = itemObject.getJSONArray("unique_items");
                    names = new String[items.length()];
                    fuid = new String[items.length()];
                    for (int i = 0; i < items.length(); i++) {
                        names[i] = items.getJSONObject(i).getString(TAG_NAME);
                        fuid[i] = items.getJSONObject(i).getString(TAG_FUID);
                        //  areaId[i] = items.getJSONObject(i).getString(TAG_AREA_ID);


                    }


                } catch (JSONException e) {
                }

                Log.v("fuck me omg", "dammmit");

                //Log.v("output:  ", tweetText);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        R.layout.simple_list_item_1, names);
                setListAdapter(adapter);


            }
        });

    }

    // Container Activity must implement this interface
    public interface OnUniqueItemSelectedListener {
        public void onUniqueItemSelected(String p);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnUniqueItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    
}
