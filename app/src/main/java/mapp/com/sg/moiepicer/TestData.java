package mapp.com.sg.moiepicer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import mapp.com.sg.moiepicer.Model.Ingredient;
import mapp.com.sg.moiepicer.Model.Recipe;
import mapp.com.sg.moiepicer.Model.RequiredIngredient;
import mapp.com.sg.moiepicer.Model.Step;

public class TestData extends AppCompatActivity {
    public static final String TOCOOKLIST = "TOCOOK";
    private final FirebaseDatabase mFireBase = FirebaseDatabase.getInstance();
    private final DatabaseReference mRootRef = mFireBase.getReference();
    private final String TAG_RECIPE = "RECIPE";
    private String recipeID = "";
    private ArrayList<Recipe> finalRecipeList = new ArrayList<Recipe>();
    private ArrayList<Recipe> toViewList = new ArrayList<Recipe>();
    protected ArrayList<Recipe> toCookList = new ArrayList<Recipe>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ImageButton searchButton = (ImageButton) findViewById(R.id.searchRecipeBtn);
        final TextView searchText = (TextView) findViewById(R.id.searchText);
        final Spinner levelSpinner = (Spinner) findViewById(R.id.levelSpinner);
        final Spinner cuisineSpinner = (Spinner) findViewById(R.id.cuisineSpinner);
        final Spinner styleSpinner = (Spinner) findViewById(R.id.styleSpinner);
        final Spinner timeSpinner = (Spinner) findViewById(R.id.timeSpinner);
        final ArrayAdapter<CharSequence> levelAdapter = ArrayAdapter.createFromResource(this, R.array.levels,android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> cuisineAdapter = ArrayAdapter.createFromResource(this, R.array.cuisine,android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> styleAdapter = ArrayAdapter.createFromResource(this, R.array.style,android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(this, R.array.time,android.R.layout.simple_spinner_item);

        //Setting up adapters
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting up spinners
        levelSpinner.setAdapter(levelAdapter);
        cuisineSpinner.setAdapter(cuisineAdapter);
        styleSpinner.setAdapter(styleAdapter);
        timeSpinner.setAdapter(timeAdapter);

        //Testing purpose to get all recipe
        DatabaseReference mRecipeRef = mRootRef.child("Recipe");
        final DatabaseReference mIngredientRef = mRootRef.child("RequiredIngredient");
        final DatabaseReference mStepRef = mRootRef.child("RequiredStep");


        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.testDataRV);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(false);

        //Change from finalRecipeList to
        final SearchAdapter mAdapter = new SearchAdapter(toViewList);
        recyclerView.setAdapter(mAdapter);

        mRecipeRef.addValueEventListener(new ValueEventListener() {
            //First data snapshot is for recipe list (Recipe)
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                Log.i(TAG_RECIPE, dataSnapshot.getKey()); //Prints Recipe
                //Recipe here (R1, R2)
                final ArrayList<RequiredIngredient> rIngredientList = new ArrayList<RequiredIngredient>();
                final ArrayList<Step> rStepList = new ArrayList<Step>();
                for (final DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    final Recipe recipe = childSnapshot.getValue(Recipe.class);
                    recipeID = recipe.getUID();
                    Log.i(TAG_RECIPE, recipe.getName());
                    //For ingredients
                    mIngredientRef.child(recipeID).addListenerForSingleValueEvent(new ValueEventListener() {
                        //First data snapshot is for required ingredient list (RequiredIngredient)
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshotI) {
                            //Ingredient Lists here (R1, R2)
                            for (DataSnapshot childSnapshotI : dataSnapshotI.getChildren()) {
                            //Log.i(TAG_RECIPE,cChildSnapshotI.getValue(RequiredIngredient.class).getIngredient().getName());
                            RequiredIngredient reqI = new RequiredIngredient(childSnapshotI.child("Ingredient").getValue(Ingredient.class),
                                    childSnapshotI.child("Amount").getValue(String.class),
                                    childSnapshotI.child("Unit").getValue(String.class));
                            rIngredientList.add(reqI);
                                Log.i(TAG_RECIPE, "Recipe Ingredients: " + reqI.getIngredient().getName());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e(TAG_RECIPE, "onCalled : " + databaseError.getMessage());
                        }
                    });

                    //For Steps
                    Recipe fRecipe = new Recipe(recipe, rIngredientList, rStepList);
//                    mStepRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    mStepRef.child(recipeID).addListenerForSingleValueEvent(new ValueEventListener() {
                        //First data snapshot is for required steps list (RequiredStep)
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshotS) {
                            //Step Lists (R1, R2) for individual steps (1,2,3,4)
                            for (DataSnapshot childSnapshotS : dataSnapshotS.getChildren()) {
                                rStepList.add(childSnapshotS.getValue(Step.class));
                                Log.i(TAG_RECIPE, "Recipe Step: " + childSnapshotS.getValue(Step.class).getName());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e(TAG_RECIPE, "onCalled : " + databaseError.getMessage());
                        }
                    });
                    finalRecipeList.add(fRecipe);
                    toViewList.add(fRecipe);
                }
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG_RECIPE, "onCalled : " + databaseError.getMessage());
            }

        });

        //On clickListner here for search
        searchButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                toViewList.clear();
                String term = searchText.getText().toString();
                for(Recipe r : finalRecipeList){
                    if(r.getName().contains(term)){
                        toViewList.add(r);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        /*
        Log.i(TAG_RECIPE, "All the recipe: ");
        Log.i(TAG_RECIPE, String.valueOf(finalRecipeList.size()));
        for (Recipe r : finalRecipeList) {
            Log.i(TAG_RECIPE, r.getName());
            Log.i(TAG_RECIPE, "Required Ingredients: ");
            for (RequiredIngredient ri : r.getRequiredIngredient()) {
                Log.i(TAG_RECIPE, ri.getIngredient().getName());
            }
            Log.i(TAG_RECIPE, "Required Steps: ");
            for (Step rs : r.getRequiredSteps()) {
                Log.i(TAG_RECIPE, rs.getName());
            }
        }*/
    }

}
