package com.github.vinci.testdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;

import com.github.vinci.testdemo.utils.MyImageLoader;

/**
 * Created by Administrator on 2016/12/12 0012.
 */

class ImageDetailAdapter extends BaseAdapter {
    private Point point;
    private LayoutInflater inflater;
    private String[] datas;
    private GridView mGridView;
    private ChosenPathListener listener;

    public ImageDetailAdapter(Context context, String[]datas, GridView mGridView,ChosenPathListener listener) {
        this.datas = datas;
        this.mGridView = mGridView;
        this.inflater = LayoutInflater.from(context);
        point = new Point(150,150);
        this.listener = listener;
    }

    public interface ChosenPathListener{
        void chooseImg(String path);
        void cancelChoosen(String path);
    }
    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int i) {
        return datas[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder ;
        final String path = datas[i];
        if(view == null){
            viewHolder = new ViewHolder();
            view = this.inflater.inflate(R.layout.item_image,viewGroup,false);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.check);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setTag(path);
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    listener.chooseImg(path);
                }else{
                    listener.cancelChoosen(path);
                }
            }
        });
        Bitmap bitmap = MyImageLoader.getInstance().loadNativeImage(path, point, new MyImageLoader.MyImageCallBack() {
            @Override
            public void onImageLoader(Bitmap bitmap, String path) {
                ImageView imageView  = (ImageView) mGridView.findViewWithTag(path);
                if(imageView != null && bitmap != null){
                    imageView.setImageBitmap(bitmap);
                }
            }
        });
        if(bitmap != null){
            viewHolder.imageView.setImageBitmap(bitmap);
        }
        return view;
    }

    public static class ViewHolder{
        public ImageView imageView;
        public CheckBox checkBox;
    }
}