package mapp.com.sg.moiepicer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private ArrayList<Recipe> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        protected TextView tvRecipeName;
        protected ImageView imageView_recipe;
        protected ImageButton btn_StartCooking;
        protected Button btn_delete;

        public ViewHolder(final View itemView) {
            super(itemView);

            tvRecipeName = (TextView) itemView.findViewById(R.id.tvRecipeName);
            imageView_recipe = (ImageView) itemView.findViewById(R.id.imageView_recipe);
            btn_delete = (Button) itemView.findViewById(R.id.btn_delete_recipe);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecipeAdapter(ArrayList<Recipe> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_recipe, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Recipe recipe = mDataset.get(position);
        if (recipe.getUrl() != null) {
            Glide.with(holder.imageView_recipe.getContext()).load(recipe.getUrl())
                    .into(holder.imageView_recipe);
        }
        holder.tvRecipeName.setText(recipe.getName());
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Recipe removedRecipe = mDataset.remove(position);
                RecipeAdapter.this.notifyDataSetChanged();
                Toast.makeText(v.getContext(), "Removed Recipe: " + removedRecipe.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.imageView_recipe.setOnClickListener(new RecipeOnClickListener(recipe, mDataset));


//        holder.imageView_recipe.setImageResource(/*set something */);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setmDataset(ArrayList<Recipe> toCookList) {
        Log.i("TEsting", "DataChanged");
        mDataset = toCookList;
        notifyDataSetChanged();
    }
}