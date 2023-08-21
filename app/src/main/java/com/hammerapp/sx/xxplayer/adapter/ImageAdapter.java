package com.hammerapp.sx.xxplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hammerapp.sx.xxplayer.R;
import com.hammerapp.sx.xxplayer.activity.ImageSliderActivity;
import com.hammerapp.sx.xxplayer.activity.MainActivity;
import com.hammerapp.sx.xxplayer.comman.Utils;
import com.hammerapp.sx.xxplayer.fragement.GalleryFragment;
import com.hammerapp.sx.xxplayer.model.PictureFace;
import com.pesonal.adsdk.AppManage;

import java.util.ArrayList;
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyHolder> {
    ArrayList<PictureFace> list;
    Context context;
    LayoutInflater inflater;

    public ImageAdapter(ArrayList<PictureFace> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.image_display,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        PictureFace image = list.get(position);
        Glide.with(context)
                .load(image.getPicturePath())
                .apply(new RequestOptions().centerCrop())
                .into(holder.image_view);
        holder.image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(context).show_INTERSTIAL(context, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Utils.picture.addAll(list);
                        Intent move = new Intent(context, ImageSliderActivity.class);
                        move.putExtra("name",position);
                        context.startActivity(move);
                    }
                });


            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView image_view;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image_view=itemView.findViewById(R.id.image_view);
        }
    }
}
