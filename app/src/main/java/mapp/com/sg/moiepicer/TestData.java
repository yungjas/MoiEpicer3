package mapp.com.sg.moiepicer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import junit.framework.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import mapp.com.sg.moiepicer.Model.Ingredient;
import mapp.com.sg.moiepicer.Model.Recipe;
import mapp.com.sg.moiepicer.Model.RequiredIngredient;
import mapp.com.sg.moiepicer.Model.Step;

public class TestData extends Fragment {
    public static final String TOCOOKLIST = "TOCOOK";
    private final FirebaseDatabase mFireBase = FirebaseDatabase.getInstance();
    private final DatabaseReference mRootRef = mFireBase.getReference();
    private final String TAG_RECIPE = "RECIPE";
    private String recipeID = "";
    private ArrayList<Recipe> finalRecipeList = new ArrayList<Recipe>();
    private ArrayList<Recipe> toViewList = new ArrayList<Recipe>();
    protected ArrayList<Recipe> toCookList = new ArrayList<Recipe>();
    RecyclerView recyclerView;

    public static TestData newInstance(int page, String title,ArrayList<Recipe> toCookList){
        TestData  fragmentFirst = new TestData();
        if (toCookList == null) {
            fragmentFirst.toViewList = new ArrayList<Recipe>();
        } else {
            fragmentFirst.toCookList = toCookList;
        }
        return fragmentFirst;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_test_data,container,false);

        final ImageButton searchButton = (ImageButton)view.findViewById(R.id.searchRecipeBtn);
        final TextView searchText = (TextView) view.findViewById(R.id.searchText);
        final Spinner levelSpinner = (Spinner) view.findViewById(R.id.levelSpinner);
        final Spinner cuisineSpinner = (Spinner) view.findViewById(R.id.cuisineSpinner);
        final Spinner styleSpinner = (Spinner) view.findViewById(R.id.styleSpinner);
        final Spinner timeSpinner = (Spinner) view.findViewById(R.id.timeSpinner);
        final ArrayAdapter<CharSequence> levelAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.levels,android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> cuisineAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.cuisine,android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> styleAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.style,android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.time,android.R.layout.simple_spinner_item);

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


        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
         recyclerView = (RecyclerView) view.findViewById(R.id.testDataRV);
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

                for (final DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    final ArrayList<RequiredIngredient> rIngredientList = new ArrayList<RequiredIngredient>();
                    final ArrayList<Step> rStepList = new ArrayList<Step>();
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


        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    public ArrayList<Recipe> getToCookList (){
        return  ((SearchAdapter)recyclerView.getAdapter()).getToCookList();
    }

    public void setTocooklist(ArrayList<Recipe> toCookList){
        ((SearchAdapter)recyclerView.getAdapter()).setToCookList(toCookList);
    }

}
