package com.droidverine.androidcrimereporter.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.droidverine.androidcrimereporter.Models.Complaints;
import com.droidverine.androidcrimereporter.R;

import java.util.List;

/**
 * Created by DELL on 07-03-2018.
 */

public class MyCompsAdapter extends RecyclerView.Adapter<MyCompsAdapter.CustomHolder> {
    List<Complaints> complaintsList;
    Context context;

    public MyCompsAdapter(List<Complaints> complaintsList, Context context) {
        this.complaintsList = complaintsList;
        this.context = context;
    }

    @Override
    public MyCompsAdapter.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.mycomps_item,null);

        return new CustomHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyCompsAdapter.CustomHolder holder, int position) {
        holder.name.append(complaintsList.get(position).getName());
        holder.location.append(complaintsList.get(position).getLocation());
        String url=complaintsList.get(position).getUrl();
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide.with(context).load(url)
                .listener(new RequestListener<Drawable>()
                {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource)
                    {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource)
                    {
                        Log.d("Glide", "resource ready");
                        holder.img.setBackgroundResource(0);
                        return false;
                    }

                })
                .apply(options)
                .into(holder.img);



    }

    @Override
    public int getItemCount() {
        return complaintsList.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder {
        TextView name,location,status;
        ImageView img;
        public CustomHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.victimname);
            location=(TextView)itemView.findViewById(R.id.victimlocation);
            img=(ImageView)itemView.findViewById(R.id.victimimg);
        }
    }
}
