package org.familab.app.Fragments;

import android.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.familab.app.FamiRestClient;
import org.familab.app.UniqueItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class UniqueItemListFragment extends ListFragment {

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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //get item that was clicked

        Object o = this.getListAdapter().getItem(position);
        String keyword = o.toString();

        Intent intent = new Intent(this.getActivity(), UniqueItem.class);
        intent.putExtra("json", cacheObject.toString());
        //String name = names[position];
        //String famid = fuid[position];
        //intent.putExtra(EXTRA_NAME, name);
        //intent.putExtra(EXTRA_FUID, famid);
        intent.putExtra(EXTRA_POSITION, position);
        startActivity(intent);


    }
    
}
