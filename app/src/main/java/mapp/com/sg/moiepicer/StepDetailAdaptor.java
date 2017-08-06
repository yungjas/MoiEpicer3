package mapp.com.sg.moiepicer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mapp.com.sg.moiepicer.Model.Step;

/**
 * Created by JasmineLim on 8/5/2017.
 */

public class StepDetailAdaptor extends RecyclerView.Adapter<StepDetailAdaptor.ViewHolder> {
    private ArrayList<Step> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        protected TextView tvStepName,tvStepDescription;
        public ViewHolder(final View itemView) {
            super(itemView);
            tvStepName = (TextView) itemView.findViewById(R.id.tvStep1);
            tvStepDescription =(TextView) itemView.findViewById(R.id.tvStep1Desc);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public StepDetailAdaptor(ArrayList<Step> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_steps_details, parent, false);
        // set the view's size, margins, paddings and layout parameters

        StepDetailAdaptor.ViewHolder vh =  new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Step step = mDataset.get(position);
        holder.tvStepName.setText(step.getName());
        holder.tvStepDescription.setText(step.getDescription());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setDataSet(ArrayList<Step> newStepArray){
        mDataset =newStepArray;
        notifyDataSetChanged();
    }
}
