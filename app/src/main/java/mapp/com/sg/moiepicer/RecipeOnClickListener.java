package mapp.com.sg.moiepicer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

import mapp.com.sg.moiepicer.Model.Recipe;

/**
 * Created by Acer on 8/8/2017.
 */

public class RecipeOnClickListener implements View.OnClickListener{
    private Recipe recipe;
    private  ArrayList<Recipe> toCookList;

    public RecipeOnClickListener(Recipe recipe, ArrayList<Recipe> toCookList){
        super();
        this.toCookList=toCookList;
        this.recipe = recipe;

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), RecipeDetails.class);
        intent.putExtra(MainActivity.RECIPE,recipe);
        intent.putParcelableArrayListExtra(MainActivity.TOCOOKLIST,toCookList);
        v.getContext().startActivity(intent);
    }
}
