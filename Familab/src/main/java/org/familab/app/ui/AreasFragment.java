package org.familab.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.familab.app.StatusRequestClient;
import org.familab.app.models.Status.Area;
import org.familab.app.models.Status.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by conner on 6/28/13.
 */
public class AreasFragment extends ListFragment {

    private static final String TAG_NAME = "name";
    public final static String EXTRA_POSITION = "position";

    public String[] names = null;
    public String[] fuid = null;
    JSONArray cacheArray;
    OnAreaSelectedListener mCallback;
    static List<Area> mAreas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            getItemList();
        }catch(JSONException e){
            Log.v("AreasFragment", "getItemList() exception");
        }
    }

    public interface OnAreaSelectedListener {
        public void onAreaSelected(int p);
    }

    public void getItemList() throws JSONException {
        StatusRequestClient.get("areas", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(JSONObject itemObject) {
                try {
                    Gson gson = new Gson();
                    Response response = gson.fromJson(itemObject.get("response").toString(), Response.class);
                    mAreas = response.getAreas();
                    String[] names = new String[mAreas.size()];
                    for (int i = 0; i < mAreas.size(); i++) {
                        names[i] = mAreas.get(i).getName();
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1, names);
                    setListAdapter(adapter);
                } catch (Exception e) {
                    Log.v("AreasFragment", "Exception at Gson parse");
                }
            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnAreaSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //get item that was clicked
        mCallback.onAreaSelected(position);
    }

    public static List<Area> getmAreas(){
        return mAreas;
    }

}