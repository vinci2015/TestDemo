package com.github.vinci.testdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.vinci.testdemo.utils.MyImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/12/10 0010.
 */

public class ImageAdapter extends BaseAdapter {
    private List<ImageBean> list;
    private Point mPoint = new Point(0,0);//用来封装ImageView的宽和高的对象
    private GridView mGridView;
    protected LayoutInflater mInflater;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public ImageAdapter(Context context, List<ImageBean> list, GridView mGridView){
        this.list = list;
        this.mGridView = mGridView;
        mInflater = LayoutInflater.from(context);
        mPoint = new Point(mGridView.getWidth()/3,mGridView.getWidth()/3);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        ImageBean mImageBean = list.get(position);
        String path = mImageBean.getTopImagePath();
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_img_collection, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.first_img);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.size);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
        viewHolder.textView.setText(mImageBean.getFolderName()+" ("+Integer.toString(mImageBean.getImageCounts())+")");
        //给ImageView设置路径Tag,这是异步加载图片的小技巧
        viewHolder.imageView.setTag(path);


        //利用NativeImageLoader类加载本地图片
        Bitmap bitmap = MyImageLoader.getInstance().loadNativeImage(path, mPoint, new MyImageLoader.MyImageCallBack() {

            @Override
            public void onImageLoader(Bitmap bitmap, String path) {
                ImageView mImageView = (ImageView) mGridView.findViewWithTag(path);
                if(bitmap != null && mImageView != null){
                    mImageView.setImageBitmap(bitmap);
                }
            }
        });

        if(bitmap != null){
            viewHolder.imageView.setImageBitmap(bitmap);
        }


        return convertView;
    }



    public static class ViewHolder{
        public ImageView imageView;
        public TextView textView;
    }


}