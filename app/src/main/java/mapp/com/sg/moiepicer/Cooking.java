package mapp.com.sg.moiepicer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mapp.com.sg.moiepicer.FirebaseHelper.DataHelper;
import mapp.com.sg.moiepicer.Model.Ingredient;
import mapp.com.sg.moiepicer.Model.Recipe;
import mapp.com.sg.moiepicer.Model.RequiredIngredient;
import mapp.com.sg.moiepicer.Model.Step;

public class Cooking extends AppCompatActivity {
    private static final String TAG_RECIPE = "RECIPE";
    private ArrayList<Recipe> mToCookList;
    private ArrayList<Step> mRequiredStep;
    Spinner spToCookList;
    private TextView tvRecipeName;
    private NavigationView navigatorView;
    private DrawerLayout drawerLayout;
    private ImageButton btn_DoneCooking, btnStepGuide;
    private RecyclerView rv_steps_drawer;
    private TextView tvStepDescription_Cooking;
    private Step currentStep;
    private FirebaseDatabase mfirebase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootRef = mfirebase.getReference(),
            mRequiredStepRef = mfirebase.getReference("RequiredStep"),
            mRequiredIngredientRef = mfirebase.getReference("RequiredIngredient");
    private Recipe currentRecipe;
    private int stepIndex = 0;
    private int recipeIndex = 0;

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

        //Testing get toCooklist
//        for (Recipe recipe : mToCookList) {
//            Log.i(TAG_RECIPE, "I am cooking  :" + recipe.getName());
//        }

        initialData();
        initialUI();

        mToCookList = DataHelper.getSampleData();

        currentRecipe = mToCookList.get(recipeIndex);
        //StepRecycleView
        mRequiredStep = currentRecipe.getRequiredSteps();

        //setupRecycleView
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_steps_drawer.setLayoutManager(llm);
        StepAdapter adapter = new StepAdapter(mRequiredStep);

        rv_steps_drawer.setAdapter(adapter);
        adapter.setDataSet(mRequiredStep);



        //set finish button
        btn_DoneCooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cooking.this, Summary.class);
                Bundle b = new Bundle();
                intent.putExtra("bundle", b);
                b.putParcelableArrayList(Home.TOCOOKLIST, mToCookList);
                startActivity(intent);
            }
        });

        //set btnStepGuide
        btnStepGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        //UpdateUI
//        updateUI();
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

    private void updateUI() {
        currentRecipe = mToCookList.get(recipeIndex);
        currentStep = currentRecipe.getRequiredSteps().get(stepIndex);
        tvRecipeName.setText(currentRecipe.getName());
        //Step num
        tvStepDescription_Cooking.setText(currentStep.getDescription());
    }

    private void initialUI() {
        tvRecipeName = (TextView) findViewById(R.id.tvRecipeName_Cooking);
        tvStepDescription_Cooking = (TextView) findViewById(R.id.tvStepDescription_Cooking);
        navigatorView = (NavigationView) findViewById(R.id.navigator_cooking);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_cooking);
        btn_DoneCooking = (ImageButton) findViewById(R.id.btn_DoneCooking);
        rv_steps_drawer = (RecyclerView) findViewById(R.id.rv_Steps_drawer);
        btnStepGuide = (ImageButton) findViewById(R.id.btnStepGuide);

        //step spinner
        spToCookList = (Spinner) findViewById(R.id.spinnerCookRecipe);
        spToCookList.setAdapter(new RecipeSpinnerAdapter(this, (List<Recipe>) mToCookList));
        spToCookList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentRecipe = ((Recipe) parent.getItemAtPosition(position));
                tvRecipeName.setText(currentRecipe.getName());
                ((StepAdapter) rv_steps_drawer.getAdapter()).setDataSet(currentRecipe.getRequiredSteps());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //set rv_Step


        ;
    }

    private void initialData() {
        //Get the to cookList from the previous actitvity
        Intent inten = this.getIntent();
        Bundle b = inten.getBundleExtra("bundle");
        mToCookList = b.getParcelableArrayList(Home.TOCOOKLIST);

        readFromFirebase();

    }

    private void readFromFirebase() {
        for (final Recipe recipe : mToCookList) {
            boolean isCompleted = false;
            if (recipe.getRequiredSteps().isEmpty()) {
                isCompleted = false;
                mRequiredStepRef.child(recipe.getUID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<Step> requiredStep = new ArrayList<Step>();
                        Step step;
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            step = childSnapshot.getValue(Step.class);
                            requiredStep.add(step);
                            Log.i(TAG_RECIPE, childSnapshot.getValue(Step.class).getName());
                        }
                        Log.i(TAG_RECIPE, "For Recipe :" + recipe.getName());
                        recipe.setRequiredSteps(requiredStep);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }


            if (recipe.getRequiredIngredient() == null || recipe.getRequiredIngredient().isEmpty()) {

                final ArrayList<RequiredIngredient> requiredIngredientArrayList = new ArrayList<>();
                mRequiredIngredientRef
                        .child(recipe.getUID())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    String amount = childSnapshot.child("Amount").getValue(String.class);

                                    String unit = childSnapshot.child("Unit").getValue(String.class);
                                    Ingredient ingredient = childSnapshot.child("Ingredient").getValue(Ingredient.class);
                                    RequiredIngredient requiredIngredient = new RequiredIngredient(ingredient, Integer.valueOf(amount), unit);
                                    requiredIngredientArrayList.add(requiredIngredient);
                                    Log.i(TAG_RECIPE, "Required Ingredient" +
                                            "\t" + requiredIngredient.getIngredient().getName() +
                                            "\t" + requiredIngredient.getAmount() +
                                            "\t" + requiredIngredient.getUnit());
                                }
                                recipe.setRequiredIngredient(requiredIngredientArrayList);
                                Log.i(TAG_RECIPE, "For Recipe :" + recipe.getName());
                                Log.i(TAG_RECIPE, "finished Step: " + dataSnapshot.getKey());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w(TAG_RECIPE, "Fail to load Ingredient");
                            }
                        });
            }
        }
    }


    private class RecipeSpinnerAdapter extends ArrayAdapter<Recipe> {

        public RecipeSpinnerAdapter(@NonNull Context context, @NonNull List<Recipe> objects) {
            super(context, android.R.layout.simple_spinner_item, objects);
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

            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
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
