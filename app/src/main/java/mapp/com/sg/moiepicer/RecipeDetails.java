package mapp.com.sg.moiepicer;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

import mapp.com.sg.moiepicer.Model.Ingredient;
import mapp.com.sg.moiepicer.Model.Recipe;
import mapp.com.sg.moiepicer.Model.RequiredIngredient;
import mapp.com.sg.moiepicer.Model.Step;

public class RecipeDetails extends AppCompatActivity {
    private TextView tvRecipeName, tvLevel, tvTime, tvCuisine, tvStyle, tvDescription;
    private EditText editTextNotes;
    private ImageView imageViewRecipe;
    private RecyclerView rv_Step, rv_Ingredient;
    private ArrayList<Recipe> toCookList;
    private Recipe displayedRecipe;
    private ArrayList<RequiredIngredient> ingredientList;
    private ArrayList<Step> stepList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set up the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set up the title
        getSupportActionBar().setTitle("Recipe Details");

        inti();


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


    private void inti() {


        //initi Data
        Intent intent = getIntent();
        toCookList = getIntent().getParcelableArrayListExtra(MainActivity.TOCOOKLIST);
        displayedRecipe = intent.getParcelableExtra(MainActivity.RECIPE);
        ingredientList = displayedRecipe.getRequiredIngredient();
        stepList = displayedRecipe.getRequiredSteps();

        //inti ui
        tvRecipeName = (TextView) findViewById(R.id.tvRecipeName_RecipeDetail);
        imageViewRecipe =(ImageView) findViewById(R.id.iv_Recipe_RecipeDetail);
        tvLevel = (TextView) findViewById(R.id.tv_level_RecipeDetail);
        tvCuisine = (TextView) findViewById(R.id.tv_Cusine_RecipeDetail);
        tvStyle = (TextView) findViewById(R.id.tv_Style_RecipeDetail);
        tvTime = (TextView) findViewById(R.id.tv_TImer_RecipeDetail);
        tvDescription = (TextView) findViewById(R.id.tv_Description_RecipeDetail);
        rv_Ingredient = (RecyclerView) findViewById(R.id.rv_Ingredients_RecipeDetail);
        rv_Step = (RecyclerView) findViewById(R.id.rv_steps_RecipeDetail);

        intiUI();
    }

    private void intiUI() {

        //Basic Information
        tvRecipeName.setText(displayedRecipe.getName());
        tvDescription.setText(displayedRecipe.getDescription());
        int time = displayedRecipe.getDuration();
        if (time == 0) {
            tvTime.setText("None Available");
        } else {
            tvTime.setText(String.format("%02dm%02d s", time / 60000, (time % 60000) / 1000));
        }

        String url = displayedRecipe.getUrl();
        if(!(url.isEmpty()||url==null)){
            Glide.with(this.getApplicationContext()).load(url).into(imageViewRecipe);
        }

//        tvLevel.setText(displayedRecipe.getLevel());
//        tvStyle.setText(displayedRecipe.getStyle());
//        tvCuisine.setText(displayedRecipe.getCuisine());

        //Ingredients
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        IngredientAdaptor ingredientAdapter = new IngredientAdaptor(ingredientList);
        rv_Ingredient.setLayoutManager(llm);
        rv_Ingredient.setAdapter(ingredientAdapter);

        //Steps
        StepAdapter stepAdapter = new StepAdapter(stepList);
        rv_Ingredient.setLayoutManager(llm);
        rv_Step.setAdapter(stepAdapter);


    }
}
