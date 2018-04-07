package com.moelive.live.demo.player.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moelive.live.demo.player.model.MyVideoThumbLoader;
import com.moelive.live.demo.player.util.Video;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubohua on 16/7/12.
 */
public class JieVideoListViewAdapter extends BaseAdapter {

    private ArrayList<Video> listVideos = new ArrayList<Video>();
    private LayoutInflater mLayoutInflater;
    private MyVideoThumbLoader mVideoThumbLoader;

    public JieVideoListViewAdapter(Context context, List<Video> listVideos){
        mLayoutInflater = LayoutInflater.from(context);
        this.listVideos.addAll(listVideos);
        mVideoThumbLoader = new MyVideoThumbLoader();// 初始化缩略图载入方法

    }


    @Override
    public int getCount() {
        return listVideos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = mLayoutInflater.inflate(com.moelive.live.demo.R.layout.list_local, null);
            holder.img = (ImageView)view.findViewById(com.moelive.live.demo.R.id.img_list_local);
            holder.title = (TextView)view.findViewById(com.moelive.live.demo.R.id.tittle_list_local);
            holder.path = (TextView)view.findViewById(com.moelive.live.demo.R.id.content_list_local);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }
        String path = listVideos.get(i).getPath();

        File file = new File(path);
        if(file.isDirectory()){
            holder.img.setImageResource(com.moelive.live.demo.R.drawable.file);
        }else{
            holder.img.setTag(path);
            mVideoThumbLoader.showThumbByAsyncTask(path, holder.img);
        }
        holder.title.setText(listVideos.get(i).getTitle());
        holder.path.setText(listVideos.get(i).getPath());
        return view;

    }

    public final class ViewHolder{
        public ImageView img;
        public TextView title;
        public TextView path;
    }
}
