package mapp.com.sg.moiepicer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mapp.com.sg.moiepicer.Model.Recipe;

public class Cooking extends AppCompatActivity {
    private static final String TAG_RECIPE = "RECIPE";
    private ArrayList<Recipe> mToCookList ;
    Spinner spToCookList;
    private  TextView tvRecipeName;
    private NavigationView navigatorView;
    private DrawerLayout drawerLayout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Set up the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set up the title
        getSupportActionBar().setTitle("Cooking");
        //find component
        tvRecipeName= (TextView) findViewById(R.id.tvRecipeName_Cooking);
        navigatorView = (NavigationView) findViewById(R.id.navigator_cooking);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_cooking);

        //Get the to cookList from the previous actitvity
        Intent inten = this.getIntent();
        Bundle b = inten.getBundleExtra("bundle");
        mToCookList=b.getParcelableArrayList(Home.TOCOOKLIST);

        //Testing get toCooklist
        for(Recipe recipe : mToCookList){
            Log.i(TAG_RECIPE,"I am cooking  :" + recipe.getName());
        }

        //Set onClickListener
        spToCookList = (Spinner) findViewById(R.id.spinnerCookRecipe);
        spToCookList.setAdapter(new RecipeSpinnerAdapter(this, (List<Recipe>) mToCookList));
        spToCookList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvRecipeName.setText(((Recipe)parent.getItemAtPosition(position)).getName());
                drawerLayout.closeDrawers();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    //Set up the settings icon
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private class RecipeSpinnerAdapter extends ArrayAdapter<Recipe>{

        public RecipeSpinnerAdapter(@NonNull Context context, @NonNull List<Recipe> objects) {
            super(context,android.R.layout.simple_spinner_item, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(Cooking.this);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }

            // android.R.id.text1 is default text view in resource of the android.
            // android.R.layout.simple_spinner_item is default layout in resources of android.

            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(this.getItem(position).getName());
            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(Cooking.this);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }

            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(getItem(position).getName());
            return convertView;
        }
    }
}
