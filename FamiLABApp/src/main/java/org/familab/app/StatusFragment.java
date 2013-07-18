package org.familab.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by conner on 6/16/13.
 */
public class StatusFragment extends Fragment {
    public static WebView webView;



    private ValueCallback<Uri> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private Uri mCapturedImageURI = null;
    private static final int CAMERA_REQUEST = 1888;
    // private final static int


    WebViewClient MyWebViewClient = new WebViewClient()
    {
        // Override page so it's load on my view only
        @Override
        public boolean shouldOverrideUrlLoading(WebView  view, String  url)
        {
            view.loadUrl(url);
            return true;

        }

    };




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.status_page, container, false);

        webView = (WebView) rootView.findViewById(R.id.webView3);
        webView.setWebViewClient(MyWebViewClient);
        webView.setWebChromeClient(new WebChromeClient(){

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i,"File Chooser"), FILECHOOSER_RESULTCODE);
            }

            public void openFileChooser( ValueCallback uploadMsg, String acceptType ) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FILECHOOSER_RESULTCODE);
            }



            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult( Intent.createChooser( i, "File Chooser" ), FILECHOOSER_RESULTCODE );

            }


        });
        webView.getSettings().setJavaScriptEnabled(true);

        if(savedInstanceState != null){
            ((WebView)rootView.findViewById(R.id.webView3)).restoreState(savedInstanceState);
        }
        else{
            webView.loadUrl("http://famitracker.herokuapp.com/unique_items");
        }
        setHasOptionsMenu(true);


        return rootView;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        webView.saveState(outState);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.status, menu);
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

            case R.id.menu_back:
                webView.goBack();
                return true;

            case R.id.menu_QR:
                IntentIntegrator integrator = new IntentIntegrator(this);
                integrator.initiateScan();

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            // handle scan result
            //url famitracker.herokuapp.com/unique_items/new?fuid=809430970974
            String basedUrl = "http://famitracker.herokuapp.com/unique_items/new?fuid=";

            basedUrl += scanResult.getContents();

            webView.loadUrl(basedUrl);
        }

        if(requestCode==FILECHOOSER_RESULTCODE){
            if (null == mUploadMessage) return;
            Uri result = intent == null || resultCode != MainActivity.RESULT_OK ? null
                    : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
        // else continue with any other code you need in the method
    }



}
