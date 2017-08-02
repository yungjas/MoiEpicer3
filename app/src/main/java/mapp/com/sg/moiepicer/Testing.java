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

public class Testing extends AppCompatActivity {
    private final String TAG_INGREDIENT="INGREDIENT";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mIngredientRef = database.getReference("Ingredient");
    private ArrayList<Ingredient> ingredientList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mIngredientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot childSnapshot:dataSnapshot.getChildren()){
                    Ingredient ingredient = childSnapshot.getValue(Ingredient.class);
                    if(ingredient!=null){
                        ingredientList.add(ingredient);
                        Log.i(TAG_INGREDIENT,ingredient.getName() +"\t"+ingredient.getType());
                    }
                }
               Log.i(TAG_INGREDIENT ,dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG_INGREDIENT,"Fail to load Ingredient");
            }
        });






    }

}
