package org.familab.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by conner on 6/16/13.
 */
public class FamiDuinoFragment extends Fragment {
    private WebView webView;

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.famiduino, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*
            case R.id.action_settings:
                Intent intent = new Intent(getActivity(), Settings.class);
                startActivity(intent);
                return true;
            */
            case R.id.menu_refresh:
                webView.reload();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.twitter_page, container, false);

        webView = (WebView) rootView.findViewById(R.id.webView2);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://randomphp.levelsetlabs.com/a.php");
        setHasOptionsMenu(true);
        return rootView;
    }


}