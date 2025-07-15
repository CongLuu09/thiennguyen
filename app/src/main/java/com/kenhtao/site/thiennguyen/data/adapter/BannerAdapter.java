package com.kenhtao.site.thiennguyen.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kenhtao.site.thiennguyen.R;

import java.util.ArrayList;
import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {

    private final List<Integer> bannerImages;
    private final LayoutInflater inflater;

    public BannerAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);

        List<Integer> originalImages = new ArrayList<>();
        originalImages.add(R.drawable.banner1);
        originalImages.add(R.drawable.banner2);
        originalImages.add(R.drawable.banner3);


        bannerImages = new ArrayList<>();
        if (!originalImages.isEmpty()) {
            bannerImages.add(originalImages.get(originalImages.size() - 1));
            bannerImages.addAll(originalImages);
            bannerImages.add(originalImages.get(0));
        }
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_banner, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        holder.imageView.setImageResource(bannerImages.get(position));
    }

    @Override
    public int getItemCount() {
        return bannerImages.size();
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgBanner);
        }
    }
}
