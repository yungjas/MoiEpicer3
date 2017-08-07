package mapp.com.sg.moiepicer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import mapp.com.sg.moiepicer.Model.Recipe;

public class Home extends Fragment {
    public static final String TOCOOKLIST = "TOCOOK";
    private final FirebaseDatabase mFireBase = FirebaseDatabase.getInstance();
    private final DatabaseReference mRootRef = mFireBase.getReference();
    private final String TAG_RECIPE = "RECIPE";
    protected ArrayList<Recipe> toCookList = new ArrayList<Recipe>();
    public  RecyclerView recyclerView;
    private ImageButton btnStartCooking;

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.content_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_toCookList);
        btnStartCooking = (ImageButton) view.findViewById(R.id.btn_startCooking);
        //Set up RecycleView

        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(false);
        final RecipeAdapter mAdapter = new RecipeAdapter(toCookList);
        recyclerView.setAdapter(mAdapter);


        //Testing purpose to get all recipe
//        DatabaseReference mRecipeRef = mRootRef.child("Recipe");
//        mRecipeRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.i(TAG_RECIPE,dataSnapshot.getKey());
//                for(DataSnapshot childSnapshot: dataSnapshot.getChildren()){
//                    Recipe recipe =childSnapshot.getValue(Recipe.class);
//                    toCookList.add(recipe);
//                    Log.i(TAG_RECIPE,childSnapshot.getKey());
//                    mAdapter.notifyDataSetChanged();
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.e(TAG_RECIPE,"onCalled : " + databaseError.getMessage());
//            }
//        });
        //Set btn_Start cooking
        btnStartCooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toCookList!=null&&toCookList.size()!=0){
                    Intent intent = new Intent(view.getContext(), Cooking.class);
                    Bundle b = new Bundle();
                    b.putParcelableArrayList(TOCOOKLIST, Home.this.toCookList);
                    intent.putExtra("bundle", b);
                    Home.this.startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "Please procedd search to add recipe", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    public static Home newInstance(int page, String title, ArrayList<Recipe> mToCookList) {
        Home fragmentFirst = new Home();
        if (mToCookList == null) {
            fragmentFirst.toCookList = new ArrayList<Recipe>();
        } else {
            fragmentFirst.toCookList = mToCookList;
        }
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getView().findViewById(R.layout.activity_home);
//        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });`
        //Get all view


    }
    public ArrayList<Recipe> getToCookList (){
        return  toCookList ;
    }

    public void setTocooklist(ArrayList<Recipe> toCookList){
        ((RecipeAdapter) (recyclerView.getAdapter())).setmDataset(toCookList);
    }
}
