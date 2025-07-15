package com.kenhtao.site.thiennguyen.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kenhtao.site.thiennguyen.R;
import com.kenhtao.site.thiennguyen.data.model.HelpRequest;

import java.util.List;

public class HelpRequestAdapter extends RecyclerView.Adapter<HelpRequestAdapter.ViewHolder> {

    private Context context;
    private List<HelpRequest> data;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDetailClick(HelpRequest item);
    }

    public HelpRequestAdapter(Context context, List<HelpRequest> data, OnItemClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HelpRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_help_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpRequestAdapter.ViewHolder holder, int position) {
        HelpRequest item = data.get(position);

        holder.txtTitle.setText(item.getTitle());
        holder.txtDescription.setText(item.getDescription());


        Glide.with(context)
                .load(item.getImageResId())
                .into(holder.imgHelp);

        holder.btnDetail.setOnClickListener(v -> listener.onDetailClick(item));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDescription;
        ImageView imgHelp;
        Button btnDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHelp = itemView.findViewById(R.id.imgHelp);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            btnDetail = itemView.findViewById(R.id.btnDetail);
        }
    }
}
