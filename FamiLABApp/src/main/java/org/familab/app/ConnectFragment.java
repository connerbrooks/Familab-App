package org.familab.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by conner on 6/16/13.
 */
public class ConnectFragment extends ListFragment {
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] values = new String[] { "FamiLAB.org", "Forums", "IRC",
                "Map", "Twitter", "Google+"};
        int[] flags = new int[]{
                R.drawable.ic_logo,
                R.drawable.ic_forums,
                R.drawable.ic_irc,
                R.drawable.ic_location,
                R.drawable.ic_twitter,
                R.drawable.ic_g_plus
        };


        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<6;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt", values[i]);
            hm.put("flag", Integer.toString(flags[i]) );
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "flag","text1" };

        // Ids of views in listview_layout
        int[] to = { R.id.flag,R.id.text1,};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
       // SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.icon_list, from, to);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        //        R.layout.icon_list, flags, values);
        //setListAdapter(adapter);

        //return super.onCreateView(inflater, container, savedInstanceState);
        //NewArrayAdapter adapter = new NewArrayAdapter(getActivity(), values);

        //setListAdapter(adapter);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        //setHasOptionsMenu(true);

    }
    /*
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.web, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(getActivity(), Settings.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //get item that was clicked

        Object o = this.getListAdapter().getItem(position);
        String keyword = o.toString();

        //launch intents when different list items are clicked
        if(keyword.equals("FamiLAB.org")){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.familab.org"));
            startActivity(browserIntent);
        }
        else if(keyword.equals("Forums")){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://forums.familab.org"));
            startActivity(browserIntent);
        }
        else if(keyword.equals("IRC")){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://webchat.freenode.net/?channels=#familab"));
            startActivity(browserIntent);
            //Intent intent = new Intent(getActivity(), WebViewActivity.class);
            //String message = "http://webchat.freenode.net/?channels=#familab";
            //intent.putExtra(EXTRA_LINK, message);
            //startActivity(intent);
        }
        else if(keyword.equals("Map")){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/maps?q=FamiLAB,+1355+Bennett+Dr+%23129,+Longwood,+FL&hl=en&ll=28.687802,-81.353073&spn=0.040509,0.083857&sll=28.699909,-81.35064&sspn=0.081008,0.167713&oq=famil&t=h&hq=familab+1355+bennett+dr+129&hnear=Longwood,+Seminole,+Florida&z=14&iwloc=A"));
            startActivity(browserIntent);
        }
        else if(keyword.equals("Twitter")){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/FamiLAB"));
            startActivity(browserIntent);
        }
        else if(keyword.equals("Google+")){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/u/0/100828404477153041391/posts"));
            startActivity(browserIntent);
        }


    }
}