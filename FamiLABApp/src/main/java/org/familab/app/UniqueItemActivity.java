package org.familab.app;


import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;
import android.widget.TextView;

import org.familab.app.Fragments.UniqueItemListFragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by conner on 6/28/13.
 */
public class UniqueItemActivity extends ActionBarActivity{

    private static final String TAG_ID = "id";
    private static final String TAG_AREA_ID = "area_id";
    private static final String TAG_NAME = "name";
    private static final String TAG_FUID = "fuid";
    private static final String TAG_TICKETABLE = "ticketable";
    private static final String TAG_LOGGABLE = "loggable";

    String name;
    String fuid;
    String area_id;


    public void onCreate(Bundle savedInstanceState) {
        JSONObject obj;
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.item_unique);

        Intent intent = getIntent();
        int position = intent.getIntExtra(UniqueItemListFragment.EXTRA_POSITION, 0);
        //get json object from intent
        try{
            obj = new JSONObject(getIntent().getStringExtra("json"));
            JSONArray items = obj.getJSONArray("unique_items");
            name = items.getJSONObject(position).getString(TAG_NAME);
            fuid = items.getJSONObject(position).getString(TAG_FUID);
            area_id = items.getJSONObject(position).getString(TAG_AREA_ID);


        }catch(JSONException e){

        }
        String name = intent.getStringExtra(UniqueItemListFragment.EXTRA_NAME);
        String fuid = intent.getStringExtra(UniqueItemListFragment.EXTRA_FUID);
        getSupportActionBar().setTitle(name);
        TextView tView = (TextView)findViewById(R.id.textView);
        tView.setText(name);
        TextView tView1 = (TextView)findViewById(R.id.textView2);
        tView1.setText(fuid);


    }

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