package org.familab.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.familab.app.models.Calendar.Items;
import org.familab.app.R;

import java.util.List;

/**
 * Created by conner on 9/29/13.
 */
public class EventDetailFragment extends Fragment {
    List<Items> mEventList;
    Items event;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_detail, container, false);

        mEventList = EventsFragment.getmCalendarItems();
        int position = getArguments().getInt("Position");

        event = mEventList.get(position);
        setHasOptionsMenu(true);

        TextView title = (TextView) rootView.findViewById(R.id.titleText);
        title.setText(event.getSummary());

        TextView location = (TextView) rootView.findViewById(R.id.location);
        if(event.getLocation()!=null){
            location.setText(event.getLocation());
        }else{
            location.setVisibility(View.GONE);
        }

        TextView webSite = (TextView) rootView.findViewById(R.id.website);
        webSite.setText("View in Calendar");
        webSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getHtmlLink()));
                    startActivity(browserIntent);
            }
        });

        TextView descriptionView = (TextView) rootView.findViewById(R.id.descriptionText);
        descriptionView.setText(event.getDescription());

        return rootView;

    }

}
