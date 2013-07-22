/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0import android.app.Fragment;
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.familab.app;


import android.content.Intent;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This example illustrates a common usage of the DrawerLayout widget
 * in the Android support library.
 * <p/>
 * <p>When a navigation (left) drawer is present, the host activity should detect presses of
 * the action bar's Up affordance as a signal to open and close the navigation drawer. The
 * ActionBarDrawerToggle facilitates this behavior.
 * Items within the drawer should fall into one of two categories:</p>
 * <p/>
 * <ul>
 * <li><strong>View switches</strong>. A view switch follows the same basic policies as
 * list or tab navigation in that a view switch does not create navigation history.
 * This pattern should only be used at the root activity of a task, leaving some form
 * of Up navigation active for activities further down the navigation hierarchy.</li>
 * <li><strong>Selective Up</strong>. The drawer allows the user to choose an alternate
 * parent for Up navigation. This allows a user to jump across an app's navigation
 * hierarchy at will. The application should treat this as it treats Up navigation from
 * a different task, replacing the current task stack using TaskStackBuilder or similar.
 * This is the only form of navigation drawer that should be used outside of the root
 * activity of a task.</li>
 * </ul>
 * <p/>
 * <p>Right side drawers should be used for actions, not navigation. This follows the pattern
 * established by the Action Bar that navigation should be to the left and actions to the right.
 * An action should be an operation performed on the current contents of the window,
 * for example enabling or disabling a data overlay on top of the current content.</p>
 */
public class MainActivity extends FragmentActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] titles;
    private String[] classes;

    private String mCurCheckPosition = "org.familab.app.MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getString("curChoice");
        }
        setContentView(R.layout.activity_main);


        mTitle = mDrawerTitle = getTitle();
        titles = getResources().getStringArray(R.array.selection_array);

        classes = getResources().getStringArray(R.array.nav_classes);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                 R.layout.drawer_list_item, titles));




        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action ClickListenerto toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                //getSupportFragmentManager().findFragmentByTag(currentFragmentID).setMenuVisibility(true);
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                //getSupportFragmentManager().findFragmentByTag(currentFragmentID).setMenuVisibility(false);


            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.content_frame,
                    Fragment.instantiate(MainActivity.this, classes[0]), classes[0]);
            tx.commit();
            mCurCheckPosition = classes[0];
        }

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("curChoice", mCurCheckPosition);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_settings).setVisible(true);
        //getSupportFragmentManager().findFragmentByTag(mCurCheckPosition).setMenuVisibility(drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_settings:
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment sf = getSupportFragmentManager().findFragmentByTag("org.familab.app.StatusFragment");
        Fragment changes = getSupportFragmentManager().findFragmentByTag("org.familab.app.RecentChanges");
        if((sf == null) && (changes == null)){
            super.onBackPressed();
        }
        else if(sf != null){
            if(sf.isVisible()){
                if(StatusFragment.webView.canGoBack()){
                     StatusFragment.webView.goBack();
                }
                else{
                    super.onBackPressed();
                }
            }
        }
        else if(changes != null){
            if(changes.isVisible()){
                if(RecentChanges.webView.canGoBack()){
                    RecentChanges.webView.goBack();
                }
                else{
                    super.onBackPressed();
                }
            }
        }
        else{
            super.onBackPressed();
        }
    }


    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    /**
     * Selects the item clicked in the list
     * @param position in nav drawer list
     */
    private void selectItem(int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction tx = fragmentManager.beginTransaction();
        tx.replace(R.id.content_frame,
                Fragment.instantiate(MainActivity.this, classes[position]), classes[position]);
        tx.commit();

        //currentPage = position;
        mCurCheckPosition = classes[position];

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(titles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /*
    public void setGroupData() {
        groupItem.add("Connect");
        groupItem.add("Events");
        groupItem.add("Status");
        groupItem.add("Lab Panorama");
        groupItem.add("Suggestions");

        groupClasses.add("org.familab.app.ConnectFragment");
        groupClasses.add("org.familab.app.EventsFragment");
        groupClasses.add("");
        groupClasses.add("org.familab.app.FamiDuinoFragment");
        groupClasses.add("org.familab.app.SuggestionsFragments");
    }

    ArrayList<String> groupItem = new ArrayList<String>();
    ArrayList<Object> childItem = new ArrayList<Object>();
    ArrayList<String> groupClasses = new ArrayList<String>();
    ArrayList<String> childClasses = new ArrayList<String>();

    */
    /*
    public void setChildGroupData() {


        ArrayList<String> child = new ArrayList<String>(3);
        child.add("Areas");
        child.add("Items");
        child.add("Updates");
        childItem.add(null);
        childItem.add(null);
        childItem.add(child);

        childClasses.add("org.familab.app.StatusFragment");
        childClasses.add("org.familab.app.UniqueItemListFragment");
        childClasses.add("org.familab.app.RecentChanges");


    }
    */


}