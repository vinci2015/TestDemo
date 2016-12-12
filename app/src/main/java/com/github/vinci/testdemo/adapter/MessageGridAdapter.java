package com.github.vinci.testdemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.github.vinci.testdemo.R;
import com.github.vinci.testdemo.utils.MyImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/12/12 0012.
 */

public class MessageGridAdapter extends BaseAdapter {
    private List<String> paths;
    private Point point;
    private Context context;
    private LayoutInflater inflater;
    private GridView gridView;

    public MessageGridAdapter(Context context, List<String> paths, GridView gridView) {
        this.paths = paths;
        point = new Point(gridView.getWidth()/3,gridView.getWidth()/3);
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.gridView = gridView;
    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public Object getItem(int i) {
        return paths.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void add(String path){
        if(path != null){
            paths.add(path);
            notifyDataSetChanged();
        }
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        String path = paths.get(i);
        if(view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.item_message,viewGroup,false);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setTag(path);

        Bitmap bitmap = MyImageLoader.getInstance().loadNativeImage(path, point, new MyImageLoader.MyImageCallBack() {
            @Override
            public void onImageLoader(Bitmap bitmap, String path) {
                ImageView imageView = (ImageView) gridView.findViewWithTag(path);
                if(bitmap != null && path != null){
                    imageView.setImageBitmap(bitmap);
                }
            }
        });
        return view;
    }
    public static class ViewHolder{
        private ImageView imageView;
    }
}
