package mapp.com.sg.moiepicer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
    private static final  int MILIS_SECONS =1000;
    private static final int MILIS_MINUS=60000;
    private static final String TAG_RECIPE = "RECIPE";
    private ArrayList<Recipe> mToCookList;
    private ArrayList<Step> mRequiredStep;
    Spinner spToCookList;
    private TextView tvRecipeName;
    private NavigationView navigatorView;
    private DrawerLayout drawerLayout;
    private ConstraintLayout timerViewGroup;
    private ScrollView scrollView;
    private ImageButton btn_DoneCooking, btnStepGuide, btn_NextStep, btn_PreviousStep, btn_timer;
    private ImageView imageView_Recipe;
    private RecyclerView rv_steps_drawer;
    private TextView tvStepDescription_Cooking,
            tvStepTitle,
            tvCurrentStepSeq,
            tvTotalStep;
    private ProgressBar progressBarTimer;
    private TextView chronometer_timer;

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


        //Testing
        mToCookList = DataHelper.getSampleData();
        currentRecipe = mToCookList.get(recipeIndex);
        mRequiredStep = currentRecipe.getRequiredSteps();
        currentStep = mRequiredStep.get(stepIndex);

        initialUI();

        //setupRecycleView
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_steps_drawer.setLayoutManager(llm);
        StepAdapter adapter = new StepAdapter(mRequiredStep);
        rv_steps_drawer.setAdapter(adapter);
        rv_steps_drawer.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                int index = rv.getChildAdapterPosition(child);
                if (index != -1) {
                    stepIndex = index;
                    currentStep = mRequiredStep.get(stepIndex);
                    updateUI();
                    child.getParent().requestDisallowInterceptTouchEvent(true);
                    drawerLayout.closeDrawers();
                }
                return true;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
        adapter.setDataSet(mRequiredStep);
        //UpdateUI
        updateUI();
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


    @TargetApi(Build.VERSION_CODES.N)
    private void initialUI() {
        scrollView = (ScrollView) findViewById(R.id.sv_main_Cooking);
        tvRecipeName = (TextView) findViewById(R.id.tvRecipeName_Cooking);
        tvStepDescription_Cooking = (TextView) findViewById(R.id.tvStepDescription_Cooking);
        tvStepTitle = (TextView) findViewById(R.id.tv_StepTitle_Cooking);
        tvCurrentStepSeq = (TextView) findViewById(R.id.tv_StepSeq_Cooking);
        tvTotalStep = (TextView) findViewById(R.id.tv_totalStep_Cooking);
        navigatorView = (NavigationView) findViewById(R.id.navigator_cooking);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_cooking);
        timerViewGroup = (ConstraintLayout) findViewById(R.id.component_timer_cooking);
        btn_DoneCooking = (ImageButton) findViewById(R.id.btn_DoneCooking);
        btn_NextStep = (ImageButton) findViewById(R.id.btn_Next_Cooking);
        btn_PreviousStep = (ImageButton) findViewById(R.id.btn_Previous_Cooking);
        rv_steps_drawer = (RecyclerView) findViewById(R.id.rv_Steps_drawer);
        btnStepGuide = (ImageButton) findViewById(R.id.btnStepGuide);
        btn_timer = (ImageButton) findViewById(R.id.btn_timer_cooking);
        chronometer_timer = (TextView) findViewById((R.id.chronometer_cooking));
        imageView_Recipe = (ImageView) findViewById(R.id.imageRecipeCook);
        progressBarTimer = (ProgressBar) findViewById(R.id.progressbar_remaingTime_Cooking);

        //step spinner
        spToCookList = (Spinner) findViewById(R.id.spinnerCookRecipe);
        spToCookList.setAdapter(new RecipeSpinnerAdapter(this, (List<Recipe>) mToCookList));
        spToCookList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                currentRecipe = ((Recipe) parent.getItemAtPosition(position));
                mRequiredStep = currentRecipe.getRequiredSteps();
                stepIndex=0;
                currentStep=mRequiredStep.get(0);
                tvRecipeName.setText(currentRecipe.getName());
                tvTotalStep.setText(String.valueOf(mRequiredStep.size()));
                ((StepAdapter) rv_steps_drawer.getAdapter()).setDataSet(currentRecipe.getRequiredSteps());
                updateUI();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //set rv_Step
        rv_steps_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //setActionlistner


        btn_PreviousStep.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (stepIndex == 0) {
                                                        Snackbar.make(Cooking.this.getWindow().getDecorView().findViewById(android.R.id.content), "You are at the first step", 500).show();

                                                    } else {
                                                        stepIndex--;
                                                        currentStep = mRequiredStep.get(stepIndex);
                                                        scrollView.smoothScrollTo(0, 0);
                                                        updateUI();
                                                    }
                                                }
                                            }
        );
        btn_NextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepIndex == currentRecipe.getRequiredSteps().size() - 1) {
//                    Toast.makeText(Cooking.this,"You reach the last step",Toast.LENGTH_SHORT).show();
                    Snackbar.make(Cooking.this.getWindow().getDecorView().findViewById(android.R.id.content), "Reach last step", 500).show();

                } else {
                    stepIndex++;
                    currentStep =  currentRecipe.getRequiredSteps().get(stepIndex);

                    scrollView.smoothScrollTo(0, 0);
                    updateUI();
                }
            }
        });


        //set finish button
        btn_DoneCooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Cooking.this, "Finishing Cooking", Toast.LENGTH_SHORT).show();
                final Intent intent = new Intent(Cooking.this, Summary.class);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Bundle b = new Bundle();
                        intent.putExtra("bundle", b);
                        b.putParcelableArrayList(Home.TOCOOKLIST, mToCookList);
                        startActivity(intent);
                    }
                });
//
            }
        });

        //set btnStepGuide
        btnStepGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        btn_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final long startTime = System.currentTimeMillis();
                CountDownTimer elapsedTimer = new CountDownTimer(currentStep.getTime(), 1000) {
                    @Override
                    public void onTick(long l) {
                        long minutes=l/MILIS_MINUS;
                        long second = (l%MILIS_MINUS) / 1000;
                        progressBarTimer.setProgress(progressBarTimer.getProgress()+1000);
                        chronometer_timer.setText(String.format("%02d m:%02d s", minutes,second));
                    }

                    @Override
                    public void onFinish() {
                        chronometer_timer.setText("Finished");
                        final Vibrator vibrator = (Vibrator) Cooking.this.getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        vibrator.vibrate(new long[]{0, 1000, 10}, 0);
                        btn_timer.setImageResource(R.drawable.pausebtn);
                        btn_timer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                vibrator.cancel();
                                btn_timer.setImageResource(R.drawable.startbtn);
                            }
                        });
                        Toast.makeText(Cooking.this.getApplicationContext(), "Finished", Toast.LENGTH_SHORT).show();
                    }
                }.start();
            }
        });


    }

    private void initialData() {
        //Get the to cookList from the previous actitvity
        Intent inten = this.getIntent();
        Bundle b = inten.getBundleExtra("bundle");
        mToCookList = b.getParcelableArrayList(Home.TOCOOKLIST);

        readFromFirebase();

    }

    private void updateUI() {

        tvRecipeName.setText(currentRecipe.getName());
        ((StepAdapter)rv_steps_drawer.getAdapter()).setDataSet(currentRecipe.getRequiredSteps());
        tvStepDescription_Cooking.setText(currentStep.getDescription());
        tvStepTitle.setText(currentStep.getName());
        tvCurrentStepSeq.setText(String.valueOf(stepIndex + 1));
        if (stepIndex == 0) {
            imageView_Recipe.setVisibility(View.VISIBLE);
            Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/first-firebase-project-c6d2e.appspot.com/o/steamed_egg_in_earthenware_bow.jpg?alt=media&token=b12165b4-43ac-48ea-a523-6b63eeb1597e")
                    .into(imageView_Recipe);
        } else {
            imageView_Recipe.setVisibility(View.GONE);
        }
        if (currentStep.getTime() <= 1000) {
            timerViewGroup.setVisibility(View.GONE);

        } else {
            timerViewGroup.setVisibility(View.VISIBLE);
            progressBarTimer.setMax(currentStep.getTime());

        }
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
                                    RequiredIngredient requiredIngredient = new RequiredIngredient(ingredient, amount, unit);
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

    @Override
    protected void onPause() {
        super.onPause();
        Vibrator vibrator = (Vibrator) Cooking.this.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.cancel();
    }
}
