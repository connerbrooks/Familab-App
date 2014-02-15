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


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.familab.app.ui.AreaDetailFragment;
import org.familab.app.ui.AreasFragment;
import org.familab.app.ui.ConnectFragment;
import org.familab.app.ui.EventDetailFragment;
import org.familab.app.ui.EventsFragment;
import org.familab.app.fragments.RecentChanges;
import org.familab.app.fragments.StatusFragment;
import org.familab.app.ui.UniqueItemDetailFragment;
import org.familab.app.ui.UniqueItemListFragment;

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
public class MainActivity extends ActionBarActivity
        implements UniqueItemListFragment.OnUniqueItemSelectedListener,
        EventsFragment.OnEventSelectedListener,
        AreasFragment.OnAreaSelectedListener{
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

        //getSupportActionBar().setLogo(R.drawable.ic_actionbar);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_navigation_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }
                //getSupportFragmentManager().findFragmentByTag(mCurCheckPosition).setMenuVisibility(true);
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }
                //getSupportFragmentManager().findFragmentByTag(mCurCheckPosition).setMenuVisibility(false);


            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.content_frame,
                    Fragment.instantiate(MainActivity.this, classes[0]), classes[0]);
            tx.commit();
            mCurCheckPosition = classes[0];
            mDrawerList.setItemChecked(0, true);
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem settingsItem = menu.findItem(R.id.action_settings);

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
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //turn on the Navigation Drawer image; this is called in the LowerLevelFragments
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        //getSupportFragmentManager().popBackStack();

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
        switch (position) {

            case 0:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new ConnectFragment()).addToBackStack(null)
                        .commit();
                break;

            case 1:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new EventsFragment()).addToBackStack(null)
                        .commit();
                break;

            case 2:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new StatusFragment()).addToBackStack(null)
                        .commit();
                break;

            case 3:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new RecentChanges()).addToBackStack(null)
                        .commit();
                break;

            case 4:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new AreasFragment()).addToBackStack(null)
                        .commit();

        }

        mCurCheckPosition = classes[position];
        mDrawerList.setItemChecked(position, true);
        setTitle(titles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
        mDrawerToggle.setDrawerIndicatorEnabled(true);

        /*
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
        */

    }

    public void onUniqueItemSelected(String item){
        String name = item;
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
        UniqueItemDetailFragment newFragment = new UniqueItemDetailFragment();
        Bundle args = new Bundle();
        args.putString("Name", name);
        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.content_frame, newFragment).setCustomAnimations(android.R.anim.slide_in_left, 0, 0, android.R.anim.slide_out_right);
        transaction.addToBackStack(null);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Commit the transaction
        transaction.commit();
    }

    public void onEventSelected(int p){
        EventDetailFragment newFragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putInt("Position", p);
        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.content_frame, newFragment).setCustomAnimations(android.R.anim.slide_in_left, 0, 0, android.R.anim.slide_out_right);
        transaction.addToBackStack(null);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Commit the transaction
        transaction.commit();

    }

    public void onAreaSelected(int p){
        AreaDetailFragment newFragment = new AreaDetailFragment();
        Bundle args = new Bundle();
        args.putInt("Position", p);
        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.content_frame, newFragment).setCustomAnimations(android.R.anim.slide_in_left, 0, 0, android.R.anim.slide_out_right);
        transaction.addToBackStack(null);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
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

}