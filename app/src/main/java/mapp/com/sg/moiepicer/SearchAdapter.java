package mapp.com.sg.moiepicer;

import android.content.Intent;
import android.support.v4.util.ArraySet;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import mapp.com.sg.moiepicer.Model.Recipe;

/**
 * Created by Acer on 2/8/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private ArrayList<Recipe> mDataset;
    private ArrayList<Recipe> toCookList = new ArrayList<Recipe>();
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //Create my own variable
        protected TextView tvRecipeName;
        protected ImageView imageView_recipe;
        protected  Button btn_add;

        public ViewHolder(final View itemView) {
            super(itemView);
            //Link to layout
            tvRecipeName = (TextView) itemView.findViewById(R.id.tvRecipeName);
            imageView_recipe = (ImageView) itemView.findViewById(R.id.imageView_recipe);
            btn_add = (Button) itemView.findViewById(R.id.btn_add);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchAdapter(ArrayList<Recipe> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_recipe, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Recipe recipe =  mDataset.get(position);
        holder.tvRecipeName.setText(recipe.getName());
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                Recipe addRecipe = mDataset.get(position);
                toCookList.add(addRecipe);
                SearchAdapter.this.notifyDataSetChanged();
                Toast.makeText(v.getContext() ,"Added Recipe: " + addRecipe.getName() , Toast.LENGTH_SHORT).show();
            }
        });
        if(!recipe.getUrl().isEmpty())
        Glide.with(holder.imageView_recipe.getContext()).load(recipe.getUrl()).into(holder.imageView_recipe);

        holder.imageView_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe getRecipe = mDataset.get(position);
                SearchAdapter.this.notifyDataSetChanged();

                final Intent intent;
                intent = new Intent(holder.imageView_recipe.getContext(),RecipeDetails.class);

                holder.imageView_recipe.getContext().startActivity(intent);
            }
        })  ;






//        holder.imageView_recipe.setImageResource(/*set something */);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    public ArrayList<Recipe> getToCookList(){
        return  toCookList;
    }

    public void setToCookList(ArrayList<Recipe> toCookList){
        this.toCookList =toCookList;

    }



}