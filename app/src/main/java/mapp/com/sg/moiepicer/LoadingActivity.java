package mapp.com.sg.moiepicer;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.RunnableFuture;

import mapp.com.sg.moiepicer.Model.Ingredient;
import mapp.com.sg.moiepicer.Model.Recipe;
import mapp.com.sg.moiepicer.Model.RequiredIngredient;
import mapp.com.sg.moiepicer.Model.Step;

public class LoadingActivity extends AppCompatActivity {
    private static final String TAG_RECIPE = "Recipe";
    private int mTotal, mCurrent;
    private ArrayList<Recipe> mToCookList;
    private Stack<Recipe> incompleteRecipe = new Stack<>();
    FirebaseDatabase firebase = FirebaseDatabase.getInstance();
    DatabaseReference mRootRef = firebase.getReference(),
            mRequiredStepRef = firebase.getReference("RequiredStep"),
            mRequiredIngredientRef = firebase.getReference("RequiredIngredient");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        mRequiredStepRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Step> steps = new ArrayList<Step>();
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    Step step;
                    step = dataSnapshot.getValue(Step.class);
                    steps.add(step);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        int count =1;
//        while(true){
//            if(count%10000==0){
//                Log.i("Testing",String.valueOf(count));
//                count++;
//            }
//        }
//        while (!incompleteRecipe.isEmpty()) {
//            for (int i = incompleteRecipe.size() - 1; i >= 0; i--) {
//                Recipe recipe = incompleteRecipe.get(i);
//                if (!recipe.getRequiredIngredient().isEmpty() && !recipe.getRequiredSteps().isEmpty()) {
//                    incompleteRecipe.remove(i);
//                    incompleteRecipe.trimToSize();
//                }
//            }
//        }

//        Intent intent = new Intent(getApplicationContext(), Cooking.class);
//        Bundle b = new Bundle();
//        b.putParcelableArrayList(Home.TOCOOKLIST, mToCookList);
//        intent.putExtra("bundle", b);
//        startActivity(intent);

//        checkProgess();
    }

    @Override
    protected void onPostResume() {
        //get tocooklist
        Bundle bundle = getIntent().getBundleExtra("bundle");
        mToCookList = bundle.getParcelableArrayList(Home.TOCOOKLIST);

        //initi
        mTotal = mToCookList.size();
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


            incompleteRecipe.add(recipe);
        }
        checkProgess();
        super.onPostResume();

    }

    private void checkProgess() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (!incompleteRecipe.isEmpty()) {
                    for (int i = incompleteRecipe.size() - 1; i >= 0; i--) {
                        Recipe recipe = incompleteRecipe.get(i);
                        if (!recipe.getRequiredIngredient().isEmpty() && !recipe.getRequiredSteps().isEmpty()) {
                            incompleteRecipe.remove(i);
                            incompleteRecipe.trimToSize();
                        }
                    }
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LoadingActivity.this.getApplicationContext(), Cooking.class);
                        Bundle b = new Bundle();
                        b.putParcelableArrayList(Home.TOCOOKLIST, mToCookList);
                        intent.putExtra("bundle", b);
                        startActivity(intent);
                    }
                },10000);

            }
        };
//        LoadingActivity.super.onResume();
        new Thread(runnable, "CheckProgress").start();

    }

}
