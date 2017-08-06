package mapp.com.sg.moiepicer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mapp.com.sg.moiepicer.Model.Recipe;

/**
 * Created by Acer on 5/8/2017.
 */

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {
    private ArrayList<Recipe>mDataSet;
    public SummaryAdapter(ArrayList<Recipe> mDataSet){ this.mDataSet =mDataSet;}
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        protected TextView tvRecipeName,tvTimeTaken;
        protected ImageView iv_favourite;
        protected EditText ETxt ;

        public ViewHolder(final View itemView) {
            super(itemView);

            tvRecipeName = (TextView)itemView.findViewById(R.id.tv_RecipeName_summary_item);
            tvTimeTaken =(TextView) itemView.findViewById(R.id.tvTimeTaken);
            iv_favourite=(ImageView) itemView.findViewById(R.id.iv_favourite_summary_item);
            ETxt = (EditText) itemView.findViewById(R.id.editText_Summary);

        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_summary, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = mDataSet.get(position);
        holder.tvRecipeName.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }



}
