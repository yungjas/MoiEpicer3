package mapp.com.sg.moiepicer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import mapp.com.sg.moiepicer.Model.Ingredient;
import mapp.com.sg.moiepicer.Model.RequiredIngredient;
import mapp.com.sg.moiepicer.Model.Step;

public class Testing extends AppCompatActivity {
    private final String TAG_TESTING = "TESTING";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference   mRequiredStep = database.getReference("RequiredStep"),
                        mIngredientRef = database.getReference("Ingredient"),
                        mRequiredIngredientRef = database.getReference("RequiredIngredient");
    private ArrayList<Ingredient> ingredientList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        retrieveIngredient();
        retrieveStep();
        RetrieveRequiredIngredient();

    }
    private void RetrieveRequiredIngredient() {
        final ArrayList<RequiredIngredient> requiredIngredientArrayList = new ArrayList<>();
        mRequiredIngredientRef
                .child("R1")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String amount = childSnapshot.child("Amount").getValue(String.class);

                            String unit = childSnapshot.child("Unit").getValue(String.class);
                            Ingredient ingredient = childSnapshot.child("Ingredient").getValue(Ingredient.class);
                            RequiredIngredient requiredIngredient = new RequiredIngredient(ingredient,Integer.valueOf(amount),unit);
                            requiredIngredientArrayList.add(requiredIngredient);
                            Log.i(TAG_TESTING, "Required Ingredient" +
                                                "\t" +   requiredIngredient.getIngredient().getName() +
                                                "\t" +  requiredIngredient.getAmount()+
                                                "\t" +   requiredIngredient.getUnit());
                        }

                        Log.i(TAG_TESTING, "finished Step: " + dataSnapshot.getKey());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG_TESTING, "Fail to load Ingredient");
                    }
                });
    }

    private void retrieveStep() {
        final ArrayList<Step> stepsArrayList = new ArrayList<>();
        mRequiredStep
                .child("R1")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            Step step = childSnapshot.getValue(Step.class);
                            stepsArrayList.add(step);
                            Log.i(TAG_TESTING, step.getName() + "\t" + step.getDescription());
                            Log.i(TAG_TESTING, "child: " + childSnapshot.getKey());

                        }

                        Log.i(TAG_TESTING, "finished Step: " + dataSnapshot.getKey());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG_TESTING, "Fail to load Ingredient");
                    }
                });
    }

    private void retrieveIngredient() {
        mIngredientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Ingredient ingredient = childSnapshot.getValue(Ingredient.class);
                    if (ingredient != null) {
                        ingredientList.add(ingredient);
                        Log.i(TAG_TESTING, ingredient.getName() + "\t" + ingredient.getType());
                    }
                }
                Log.i(TAG_TESTING, dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG_TESTING, "Fail to load Ingredient");
            }
        });
    }

}
