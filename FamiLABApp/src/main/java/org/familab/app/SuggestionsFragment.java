package org.familab.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class SuggestionsFragment extends Fragment {
    private WebView webView;

/*
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.web, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                webView.reload();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
*/
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_suggestion, container, false);

        webView = (WebView) rootView.findViewById(R.id.webView5);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://famitracker.herokuapp.com/suggestions/new");
        setHasOptionsMenu(true);
        return rootView;
    }
}
