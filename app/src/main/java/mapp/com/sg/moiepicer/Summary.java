package mapp.com.sg.moiepicer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import mapp.com.sg.moiepicer.FirebaseHelper.DataHelper;
import mapp.com.sg.moiepicer.Model.Recipe;

public class Summary extends AppCompatActivity {
    private static final String TAG_RECIPE = "RECIPE";
    private ArrayList<Recipe> mToCookList;
    private ImageButton btn_Save;


    private RecyclerView rv_summary_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set up the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set up the title
        getSupportActionBar().setTitle("Summary");
        //get toCookList
        Bundle b = getIntent().getBundleExtra("bundle");
        mToCookList = b.getParcelableArrayList(Home.TOCOOKLIST);
//        mToCookList = DataHelper.getSampleData();//Testing
        //Test to cookList
        for (Recipe recipe : mToCookList) {
            Log.i(TAG_RECIPE, "Finished Cooking : " + recipe.getName());
        }
        initUI();

    }

    private void initUI() {
        rv_summary_item = (RecyclerView) findViewById(R.id.rv_summary);
        btn_Save = (ImageButton) findViewById(R.id.btn_Save_Summary);

        //Setup Sumary Adapter
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_summary_item.setLayoutManager(llm);
        SummaryAdapter summaryAdapter = new SummaryAdapter(mToCookList);
        rv_summary_item.setAdapter(summaryAdapter);
        //

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Summary.this, "Saved Completed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Summary.this, Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //Set up the settings icon
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
