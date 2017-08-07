package mapp.com.sg.moiepicer.FirebaseHelper;

import java.util.ArrayList;

import mapp.com.sg.moiepicer.Model.Ingredient;
import mapp.com.sg.moiepicer.Model.Recipe;
import mapp.com.sg.moiepicer.Model.RequiredIngredient;
import mapp.com.sg.moiepicer.Model.Step;

/**
 * Created by Acer on 1/8/2017.
 */

public  class DataHelper {
    public static ArrayList<Recipe> getSampleData() {
        ArrayList<Recipe> toCooklistSample = new ArrayList<>();
        ArrayList<RequiredIngredient> requiredIngredient1 = new ArrayList<>();
        ArrayList<Step> requiredStep1 = new ArrayList<>();


        Recipe recipe1 = new Recipe("R1","Granny's Steamed Egg","D1","Recipe descriptino.....",100000);
        requiredIngredient1.add(new RequiredIngredient(new Ingredient("I1","Egg","Meat"),1,"unit"));
        requiredIngredient1.add(new RequiredIngredient(new Ingredient("I1","Egg","Meat"),1,"unit"));
        requiredIngredient1.add(new RequiredIngredient(new Ingredient("I1","Egg","Meat"),1,"unit"));
        requiredStep1.add(new Step(1,"Step1","Descript ,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n1",0));
        requiredStep1.add(new Step(2,"Step2","Descript 2",0));
        requiredStep1.add(new Step(3,"Step3","Descript 3",3000));
        requiredStep1.add(new Step(4,"Step4","Descript 4",0));
        recipe1.setRequiredIngredient(requiredIngredient1);
        recipe1.setRequiredSteps(requiredStep1);


        ArrayList<RequiredIngredient> requiredIngredient2 = new ArrayList<>();
        ArrayList<Step> requiredStep2 = new ArrayList<>();


        Recipe recipe2 = new Recipe("R2","Fried Egg","D2","Recipe descriptino2.....",100000);
        requiredIngredient2.add(new RequiredIngredient(new Ingredient("I1","Egg","Meat"),1,"unit"));

        requiredStep2.add(new Step(1,"Fried Egg","Fried egg Descript 1",0));
        recipe2.setRequiredIngredient(requiredIngredient2);
        recipe2.setRequiredSteps(requiredStep2);

        toCooklistSample.add(recipe1);
        toCooklistSample.add(recipe2);



        return  toCooklistSample;
    }
}
