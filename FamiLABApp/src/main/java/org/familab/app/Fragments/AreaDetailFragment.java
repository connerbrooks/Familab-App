package org.familab.app.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.familab.app.Models.Status.Area;
import org.familab.app.R;

import java.util.List;

/**
 * Created by conner on 12/27/13.
 */
public class AreaDetailFragment extends Fragment {
    List<Area> mAreasList;
    Area area;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_detail, container, false);

        mAreasList = AreasFragment.getmAreas();
        int position = getArguments().getInt("Position");

        area = mAreasList.get(position);
        setHasOptionsMenu(true);

        TextView title = (TextView) rootView.findViewById(R.id.titleText);
        title.setText(area.name);
        /*
        TextView location = (TextView) rootView.findViewById(R.id.location);
        if(area.!=null){
            location.setText(event.location);
        }else{
            location.setVisibility(View.GONE);
        }

        TextView webSite = (TextView) rootView.findViewById(R.id.website);
        webSite.setText("View in Calendar");
        webSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.htmlLink));
                startActivity(browserIntent);
            }
        });


        TextView descriptionView = (TextView) rootView.findViewById(R.id.descriptionText);
        descriptionView.setText(event.description);
        */

        return rootView;

    }




}
