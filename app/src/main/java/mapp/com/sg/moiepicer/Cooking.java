package mapp.com.sg.moiepicer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import java.util.ArrayList;

import mapp.com.sg.moiepicer.Model.Recipe;

public class Cooking extends AppCompatActivity {
    private static final String TAG_RECIPE = "RECIPE";
    private ArrayList<Recipe> mToCookList ;
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

        //Get the to cookList from the previous actitvity
        Intent inten = this.getIntent();
        Bundle b = inten.getBundleExtra("bundle");
        mToCookList=b.getParcelableArrayList(Home.TOCOOKLIST);

        for(Recipe recipe : mToCookList){
            Log.i(TAG_RECIPE,"I am cooking  :" + recipe.getName());
        }
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

}
