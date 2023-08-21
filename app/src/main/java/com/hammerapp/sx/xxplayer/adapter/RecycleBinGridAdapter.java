package com.hammerapp.sx.xxplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.hammerapp.sx.xxplayer.R;
import com.hammerapp.sx.xxplayer.activity.RecycleBinActivity;
import com.hammerapp.sx.xxplayer.comman.OnItemRestoreListener;
import com.hammerapp.sx.xxplayer.comman.Utils;
import com.hammerapp.sx.xxplayer.player.VideoModel;

import java.io.File;
import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class RecycleBinGridAdapter extends RecyclerView.Adapter<RecycleBinGridAdapter.MyHolder> {
    Context context;
    ArrayList<VideoModel> list;
    LayoutInflater inflater;
    public OnItemRestoreListener onItemClickListener;
    public RecycleBinGridAdapter(Context context, ArrayList<VideoModel> list, OnItemRestoreListener onItemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener=onItemClickListener;
        inflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.gridviewlist,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }
    public void filterList(ArrayList<VideoModel> filterllist) {

        list = filterllist;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        VideoModel videodata=list.get(position);
        final File file=new File(String.valueOf(videodata.getVideoUri()));
        Glide.with(context).load(file.getAbsolutePath()).apply(new RequestOptions().override(153,160).centerCrop().dontAnimate().skipMemoryCache(true)).transition(withCrossFade()).into(holder.image_view);
        holder.video_name.setText(videodata.getVideoTitle());
        holder.time_duration.setText(videodata.getVideoDuration());
        holder.next_video.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onItemClickListener.OnClick(position);
           }
       });

        holder.navi_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(position,file,holder.navi_btn);
            }
        });
        if(Utils.AllSelect==true)
        {
            holder.check_btn.setVisibility(View.VISIBLE);
            holder.navi_img.setVisibility(View.GONE);
            holder.check_btn.setChecked(true);
            Utils.list_select.addAll(list);
            RecycleBinActivity.select_count.setText(""+Utils.list_select.size()+"/Selected");
        }
        else
        {
            holder.check_btn.setVisibility(View.GONE);
            holder.navi_img.setVisibility(View.VISIBLE);
        }
        holder.check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status=holder.check_btn.isChecked();
                if(status)
                {
                    Utils.list_select.add(list.get(position));
                    RecycleBinActivity.select_count.setText(""+Utils.list_select.size()+"/Selected");

                }
                else
                {
                    Utils.list_select.remove(position);
                    RecycleBinActivity.select_count.setText(""+Utils.list_select.size()+"/Selected");
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ShapeableImageView image_view;
        RelativeLayout next_video,navi_btn;
        TextView video_name,time_duration;
        CheckBox check_btn;
        ImageView navi_img;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image_view=itemView.findViewById(R.id.image_view);
            video_name=itemView.findViewById(R.id.title_txt);
            time_duration=itemView.findViewById(R.id.duration);
            next_video=itemView.findViewById(R.id.title);
            navi_btn=itemView.findViewById(R.id.navi_btn);
            check_btn=itemView.findViewById(R.id.check_btn);
            navi_img=itemView.findViewById(R.id.navi_img);

        }
    }
}
