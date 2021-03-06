package org.familab.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.familab.app.CalendarRequestClient;
import org.familab.app.models.Calendar.Calendar;
import org.familab.app.models.Calendar.Items;
import org.familab.app.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by conner on 6/16/13.
 */
public class EventsFragment extends ListFragment {
    private WebView webView;
    String mCacheJSONString;
    static List<Items> mCalendarItems;
    OnEventSelectedListener mCallback;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        //Get calendar Events Asyncronously
        try{
            getItemList();
        }catch(JSONException e){
            Log.v("EventsFragment", "Exception at JSON parse");
        }
    }

    public void getItemList() throws JSONException {

        CalendarRequestClient.get("AIzaSyA-gmdGWLc1hoe-kNO2_3rC20GVlWw2eY4", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(JSONObject itemObject) {
                mCacheJSONString = itemObject.toString();
                //Parses JSON String with GSON
                parseItemList(mCacheJSONString);
            }
        });
    }

    public void parseItemList(String jsonString){
        try {
            Gson gson = new Gson();
            Calendar calendar = gson.fromJson(jsonString, Calendar.class);
            mCalendarItems = calendar.getItems();
            /*
            Collections.sort(mCalendarItems, new Comparator() {

                public int compare(Object o1, Object o2) {
                    Items p1 = (Items) o1;
                    Items p2 = (Items) o2;
                    return p1.start.dateTime.compareToIgnoreCase(p2.start.dateTime);
                }
            });
            */
            CalendarListAdapter customAdapter = new CalendarListAdapter(getActivity(), mCalendarItems);
            setListAdapter(customAdapter);
        } catch (Exception e) {
            Log.wtf("com.makerfaireorlando.app.MainActivity", "Exception at GSON parse");
        }
    }

    public interface OnEventSelectedListener {
        public void onEventSelected(int p);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnEventSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        mCallback.onEventSelected(position);
    }

    public static List<Items> getmCalendarItems(){
        return mCalendarItems;
    }


    private class CalendarListAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private List<Items> items;

        private class ViewHolder {
            TextView startTime;
            TextView endTime;
            TextView textTitle;
            TextView textSubTitle;
            View primaryTouchTargetView;
        }

        public CalendarListAdapter(Context context, List<Items> items) {
            inflater = LayoutInflater.from(context);
            this.items = items;
        }

        public int getCount() {
            return items.size();
        }

        public Items getItem(int position) {
            return items.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            final int mPosition = position;


            if(convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.list_item_schedule_block, null);
                holder.startTime = (TextView) convertView.findViewById(R.id.block_time);
                holder.endTime = (TextView) convertView.findViewById(R.id.block_endtime);
                holder.textTitle = (TextView) convertView.findViewById(R.id.block_title);
                holder.textSubTitle = (TextView) convertView.findViewById(R.id.block_subtitle);
                holder.primaryTouchTargetView =  convertView.findViewById(R.id.list_item_middle_container);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //"2013-10-05T16:00:00-04:00"
            //2011-10-15T13:00:00-04:00
            //parse times
            long mStartDate = 0;
            long mEndDate = 0;
            String startTime = null;
            String endTime = null;
            //myString != null && !myString.isEmpty()
            if(items.get(position).getStart().getDateTime()!= null && !(items.get(position).getStart().getDateTime().isEmpty())){
                startTime = items.get(position).getStart().getDateTime();
            }
            if(items.get(position).getEnd().getDateTime() != null && !((items.get(position).getEnd().getDateTime()).isEmpty())){
                endTime = items.get(position).getEnd().getDateTime();
            }



            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            try{
                if(startTime!=null){
                    if (!(startTime.matches("\\d{4}-\\d{2}-\\d{2}"))) {
                        mStartDate = sdf.parse(startTime).getTime();
                    }
                }
                if(endTime!=null){
                    if(!(endTime.matches("\\d{4}-\\d{2}-\\d{2}"))){
                        mEndDate = sdf.parse(endTime).getTime();
                    }
                }

                if(endTime!=null)
                    mEndDate= sdf.parse(endTime).getTime();
            }catch(ParseException e){
                Log.wtf("Calendar List Adapter", "parse exectption for date time");
            }
            DateFormat df = new SimpleDateFormat("h:mm a");
            startTime = df.format(mStartDate);
            endTime = df.format(mEndDate);

            if(startTime!=null)
                holder.startTime.setText(startTime);
            if(endTime!=null)
                holder.endTime.setText(endTime);

            holder.textTitle.setText(items.get(position).getSummary());
            holder.textSubTitle.setText(items.get(position).getLocation());
            holder.primaryTouchTargetView.setEnabled(true);
            holder.primaryTouchTargetView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String calUrl = items.get(mPosition).getHtmlLink();
                    //String eId = calUrl.substring(calUrl.indexOf("=")+1, calUrl.length());
                    //Intent calendarIntent = new Intent(Intent.ACTION_VIEW).setData( Uri.parse("content://com.android.calendar/events/" + eId));
                    Intent calendarIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(calUrl));

                    mCallback.onEventSelected(mPosition);

                    //startActivity(calendarIntent);
                }
            });

            return convertView;
        }
    }

}