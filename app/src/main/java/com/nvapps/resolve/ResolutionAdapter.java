package com.nvapps.resolve;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResolutionAdapter extends RecyclerView.Adapter<ResolutionAdapter.ViewHolder> {

    Context context;
    ArrayList<Resolution> resolutions;

    public ResolutionAdapter(Context context, ArrayList<Resolution> resolutions) {
        this.context = context;
        this.resolutions = resolutions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resolution, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Resolution res = resolutions.get(position);
        holder.titleText.setText(res.getTitle());
        holder.categoryText.setText(res.getCategory());
    }

    @Override
    public int getItemCount() {
        return resolutions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.resolution_title)
        TextView titleText;
        @Bind(R.id.resolution_category)
        TextView categoryText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
