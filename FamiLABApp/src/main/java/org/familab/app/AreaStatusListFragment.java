package org.familab.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by conner on 6/28/13.
 */
public class AreaStatusListFragment extends ListFragment {

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
    JSONArray cacheArray;

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
        FamiRestClient.get("areas.json", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(JSONArray itemArray){
                Log.v("yayz", "hopze drms");

                String text = null;

                try{
                    cacheArray = itemArray;

                   // JSONArray items = itemArray.getJSONObject();
                    names = new String[itemArray.length()];
                    fuid = new String[itemArray.length()];
                    for(int i = 0; i < itemArray.length(); i++){
                        names[i] = itemArray.getJSONObject(i).getString(TAG_NAME);
                        //fuid[i] = itemArray.getJSONObject(i).getString(TAG_FUID);
                        //  areaId[i] = items.getJSONObject(i).getString(TAG_AREA_ID);


                    }
                }catch(JSONException e){
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, names);
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

        Intent intent = new Intent(this.getActivity(), AreaActivity.class);
        intent.putExtra("json", cacheArray.toString());
        intent.putExtra(EXTRA_POSITION, position);
        startActivity(intent);


    }

}