package mapp.com.sg.moiepicer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mapp.com.sg.moiepicer.Model.Recipe;
import mapp.com.sg.moiepicer.Model.Step;

/**
 * Created by Acer on 3/8/2017.
 */


public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
    private ArrayList<Step> mDataset;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        protected TextView tvStepName,tvStepDescription;
        public ViewHolder(final View itemView) {
            super(itemView);

            tvStepName = (TextView) itemView.findViewById(R.id.tvStepName);
            tvStepDescription =(TextView) itemView.findViewById(R.id.tvStepDescription);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public StepAdapter(ArrayList<Step> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_step_cooking, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Step step = mDataset.get(position);
        holder.tvStepName.setText(step.getName());
        holder.tvStepDescription.setText(step.getDescription());
    }


    // Replace the contents of a view (invoked by the layout manager)


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
