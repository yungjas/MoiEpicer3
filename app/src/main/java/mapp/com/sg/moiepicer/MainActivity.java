package mapp.com.sg.moiepicer;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import junit.framework.Test;

import java.util.ArrayList;

import mapp.com.sg.moiepicer.Model.Recipe;

public class MainActivity extends AppCompatActivity {
    public static final String TOCOOKLIST = "TOCOOKLIST";
    public static final String RECIPE = "RECIPE";
    public static final String TAG = "MOI_EPICER";
    public static final String TAG_TESTING = "TESTING";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<Recipe> mToCookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the 5
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "Page selected" + position);
                switch (position) {
                    case 0:
                        Log.i("TEsting", "case 1");
                        Home home = (Home) fragments.get(0);
                        if (fragments.size() == 2)
                            home.setTocooklist(((TestData) fragments.get(1)).getToCookList());
                        home.recyclerView.getAdapter().notifyDataSetChanged();
                        break;
                    case 1:
                        Log.i("TEsting", "case 2");
                        TestData data = (TestData) fragments.get(1);
                        data.setTocooklist(((Home) fragments.get(0)).getToCookList());
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.i("TEsting" ,"Page scrollState" +state);
            }
        });


//        setUpTabIcons();

        //getSupportActionBar().setTitle("Home");

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        setTitle("Home");
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //Set up icons in navigation bar
//    private void setUpTabIcons(){
//        tabLayout.getTabAt(0).setIcon(R.drawable.tab_search);
//        tabLayout.getTabAt(1).setIcon(R.drawable.tab_home);
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        View rootView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            //display current selected tab layout
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0:
                    rootView = inflater.inflate(R.layout.activity_home, container, false);
                    break;
                case 1:
                    rootView = inflater.inflate(R.layout.activity_search, container, false);
                    break;
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private static final int NUM_ITESM = 2;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    if (fragments.size() == 0) {
                        fragments.add(Home.newInstance(0, "Home", null));
                    } else {
                        Home home = (Home) fragments.get(0);

                        home.setTocooklist(((TestData) fragments.get(1)).getToCookList());
                        home.recyclerView.getAdapter().notifyDataSetChanged();
                    }
                    return fragments.get(0);
                case 1:
                    if (fragments.size() == 1) {
                        fragments.add(TestData.newInstance(0, "Search", null));
                    } else {
                        TestData data = (TestData) fragments.get(1);
                        data.setTocooklist(((TestData) fragments.get(0)).getToCookList());
                    }
                    return fragments.get(1);
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 2;
        }

        //Set up the titles for each icon
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Home";
                case 1:
                    return "Search";
                default:
                    return null;
            }
        }
    }
}
