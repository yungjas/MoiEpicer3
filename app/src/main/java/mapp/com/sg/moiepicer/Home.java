package mapp.com.sg.moiepicer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import mapp.com.sg.moiepicer.Model.Recipe;

public class Home extends AppCompatActivity {
    public static  final String TOCOOKLIST="TOCOOK";
    private final FirebaseDatabase mFireBase= FirebaseDatabase.getInstance();
    private final DatabaseReference mRootRef=mFireBase.getReference();
    private final String TAG_RECIPE="RECIPE";
    protected ArrayList<Recipe> toCookList= new ArrayList<Recipe>();
    private RecyclerView recyclerView;
    private ImageButton btnStartCooking ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //Get all view
        recyclerView = (RecyclerView) findViewById(R.id.rv_toCookList);
        btnStartCooking = (ImageButton) findViewById(R.id.btn_startCooking);



    //Set up RecycleView

        LinearLayoutManager  llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(false);
        final RecipeAdapter mAdapter = new RecipeAdapter(toCookList);
        recyclerView.setAdapter(mAdapter);


        //Testing purpose to get all recipe
        DatabaseReference mRecipeRef = mRootRef.child("Recipe");
        mRecipeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG_RECIPE,dataSnapshot.getKey());
                for(DataSnapshot childSnapshot: dataSnapshot.getChildren()){
                    Recipe recipe =childSnapshot.getValue(Recipe.class);
                    toCookList.add(recipe);
                    Log.i(TAG_RECIPE,childSnapshot.getKey());
                    mAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG_RECIPE,"onCalled : " + databaseError.getMessage());
            }
        });
            //Set btn_Start cooking
            btnStartCooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Home.this,Cooking.class);
                    Bundle b = new Bundle();
                    b.putParcelableArrayList(TOCOOKLIST,Home.this.toCookList);
                     intent.putExtra("bundle",b);
                    Home.this.startActivity(intent);
                }
            });
    }

}
