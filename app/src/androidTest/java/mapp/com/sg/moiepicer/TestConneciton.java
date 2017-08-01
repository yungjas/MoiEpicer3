package mapp.com.sg.moiepicer;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

import mapp.com.sg.moiepicer.Model.Ingredient;

/**
 * Created by EternalFlames on 7/31/2017.
 */

public class TestConneciton {
    @Test
    public void testIngredient(){
        final String TAG_INGREDIENT="INGREDIENT";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mIngredientRef = database.getReference("Ingredient");
        final ArrayList<Ingredient> ingredientList= new ArrayList<>();
        Log.i(TAG_INGREDIENT,"Testing");
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG_INGREDIENT,"Fail to load Ingredient");
            }
        });

    }
}
