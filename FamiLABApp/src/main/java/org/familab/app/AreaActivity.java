package org.familab.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SimpleExpandableListAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by conner on 6/30/13.
 */
public class AreaActivity extends Activity{


    private static final String TAG_NAME = "name";
    private static final String TAG_PHOTO_URL = "photo_url";
    private static final String TAG_TICKETS = "tickets";
    private static final String TAG_STATUS = "status";
    private static final String TAG_BODY = "body";
    private static final String TAG_TICKET_TYPE = "ticket_type";
    private static final String TAG_UNIQUE_ITEMS = "unique_items";
    private static final String TAG_FUID = "fuid";
    private static final String TAG_LOGGABLE = "loggable";
    private static final String TAG_TICKETABLE = "ticketable";

    String name;
    String photoString;
    Uri photoUri;
    String photoThing;
    String area_id;

    JSONArray ticketJArray;
    JSONArray uniqueItemJArray;
    JSONObject uniqueItemJObject;
    JSONObject ticketJObject;
    String[] bodyArray;
    String[] statusArray;
    String[] ticketTypeArray;

    String[] fuidArray;
    String[] loggableArray;
    String[] itemNameArray;
    String[] ticketableArray;
    String[] photoURLArray;

    private ExpandableListAdapter mAdapter;
    static final String IS_EVEN = "IS_EVEN";

    private Drawable mActionBarBackgroundDrawable;


    public void onCreate(Bundle savedInstanceState) {
        JSONArray jArray;
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_area_trans);


        mActionBarBackgroundDrawable = getResources().getDrawable(android.R.color.holo_blue_bright);
        mActionBarBackgroundDrawable.setAlpha(0);

        getActionBar().setBackgroundDrawable(mActionBarBackgroundDrawable);

        ((NotifyingScrollView) findViewById(R.id.scroll_view)).setOnScrollChangedListener(mOnScrollChangedListener);


        Intent intent = getIntent();
        int position = intent.getIntExtra(UniqueItemListFragment.EXTRA_POSITION, 0);
        //get json object from intent
        try{
            jArray = new JSONArray(getIntent().getStringExtra("json"));
            JSONArray items = jArray;

            name = items.getJSONObject(position).getString(TAG_NAME);
            photoString = items.getJSONObject(position).getString(TAG_PHOTO_URL);
            ticketJArray = items.getJSONObject(position).getJSONArray(TAG_TICKETS);

            bodyArray = new String[ticketJArray.length()];
            statusArray = new String[ticketJArray.length()];
            ticketTypeArray = new String[ticketJArray.length()];

            for(int i=0; i < ticketJArray.length(); i++){
                ticketJObject = ticketJArray.getJSONObject(i);
                //put into arrays
                bodyArray[i] = ticketJObject.getString(TAG_BODY);
                statusArray[i] = ticketJObject.getString(TAG_STATUS);
                ticketTypeArray[i] = ticketJObject.getString(TAG_TICKET_TYPE);

            }
            uniqueItemJArray = items.getJSONObject(position).getJSONArray(TAG_UNIQUE_ITEMS);

            fuidArray = new String[uniqueItemJArray.length()];
            loggableArray = new String[uniqueItemJArray.length()];
            itemNameArray = new String[uniqueItemJArray.length()];
            ticketableArray = new String[uniqueItemJArray.length()];
            photoURLArray = new String[uniqueItemJArray.length()];
            for(int i =0; i < uniqueItemJArray.length(); i++){
                uniqueItemJObject = uniqueItemJArray.getJSONObject(i);
                //put into arrays
                fuidArray[i] = uniqueItemJObject.getString(TAG_FUID);
                loggableArray[i] = uniqueItemJObject.getString(TAG_LOGGABLE);
                itemNameArray[i] = uniqueItemJObject.getString(TAG_NAME);
                ticketableArray[i] = uniqueItemJObject.getString(TAG_TICKETABLE);
                photoURLArray[i] = uniqueItemJObject.getString(TAG_PHOTO_URL);
            }


        }catch(JSONException e){

        }

        getActionBar().setTitle(name);




        WebView webView = (WebView) findViewById(R.id.image_header);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            //webView.getSettings().setLoadWithOverviewMode(true);
           // webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(1);

         webView.loadUrl(photoString);
         //if(!photoString.equals("/photos/original/missing.png")){

         //}else{


         //}



        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();

        Map<String, String> curGroupMap = new HashMap<String, String>();
        curGroupMap.put("Name", "Tickets");
        curGroupMap.put(IS_EVEN, (1 % 2 == 0) ? "This group is even" : "This group is odd");
        groupData.add(curGroupMap);

        List<Map<String, String>> children = new ArrayList<Map<String, String>>();
        for (int j = 0; j < bodyArray.length; j++) {
            Map<String, String> curChildMap = new HashMap<String, String>();
            children.add(curChildMap);
            //curChildMap.put("Name", "Child " + j);
            curChildMap.put("Name", bodyArray[j]);
        }
        childData.add(children);

        Map<String, String> curGroupMap2 = new HashMap<String, String>();
        curGroupMap2.put("Name", "Items");
        curGroupMap2.put(IS_EVEN, (2 % 2 == 0) ? "This group is even" : "This group is odd");
        groupData.add(curGroupMap2);

        List<Map<String, String>> children2 = new ArrayList<Map<String, String>>();
        for (int j = 0; j < itemNameArray.length; j++) {
            Map<String, String> curChildMap = new HashMap<String, String>();
            children2.add(curChildMap);
            //curChildMap.put("Name", "Child " + j);
            curChildMap.put("Name", itemNameArray[j]);
        }
        childData.add(children2);


        mAdapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { "Name", IS_EVEN },
                new int[] { android.R.id.text1, android.R.id.text2 },
                childData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] { "Name", IS_EVEN },
                new int[] { android.R.id.text1, android.R.id.text2 }
        );
        ExpandableListView lv = (ExpandableListView)findViewById(R.id.listView);
        lv.setAdapter(mAdapter);

    }

    private NotifyingScrollView.OnScrollChangedListener mOnScrollChangedListener = new NotifyingScrollView.OnScrollChangedListener() {
        public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
            final int headerHeight = findViewById(R.id.image_header).getHeight() - getActionBar().getHeight();
            final float ratio = (float) Math.min(Math.max(t, 0), headerHeight) / headerHeight;
            final int newAlpha = (int) (ratio * 255);
            mActionBarBackgroundDrawable.setAlpha(newAlpha);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed in the action bar.
                // Create a simple intent that starts the hierarchical parent activity and
                // use NavUtils in the Support Package to ensure proper handling of Up.
                Intent upIntent =  NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is not part of the application's task, so create a new task
                    // with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // If there are ancestor activities, they should be added here.
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                    //finish();
                } else {
                    // This activity is part of the application's task, so simply
                    // navigate up to the hierarchical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }





}