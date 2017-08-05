package mapp.com.sg.moiepicer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mapp.com.sg.moiepicer.Model.Ingredient;
import mapp.com.sg.moiepicer.Model.RequiredIngredient;

/**
 * Created by JasmineLim on 8/5/2017.
 */

public class IngredientAdaptor extends RecyclerView.Adapter<IngredientAdaptor.ViewHolder>{

    private ArrayList<RequiredIngredient> mDataset;

    public  static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        protected TextView tvIngreName,tvIngreUnit, tvIngreType;
        public ViewHolder(final View itemView) {
            super(itemView);

            tvIngreName = (TextView) itemView.findViewById(R.id.tvIngredientName);
            tvIngreUnit = (TextView) itemView.findViewById(R.id.tvIngredientUnit);
            tvIngreType = (TextView) itemView.findViewById(R.id.tvIngredientType);
        }
    }

    public IngredientAdaptor(ArrayList<RequiredIngredient> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //new view
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient_details, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RequiredIngredient ingredient = mDataset.get(position);
        holder.tvIngreName.setText(ingredient.getIngredient().getName());
        holder.tvIngreUnit.setText(ingredient.getUnit());
        holder.tvIngreType.setText(ingredient.getIngredient().getType());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setDataSet(ArrayList<RequiredIngredient> newIngreArray){
        mDataset = newIngreArray;
        notifyDataSetChanged();
    }
}
