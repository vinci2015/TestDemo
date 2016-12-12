package com.github.vinci.testdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.github.vinci.testdemo.utils.PictureUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/12/9 0009.
 */

public class MessagePresenter {
    private static final String TAG = MessagePresenter.class.getSimpleName();
    private List<String> paths = new ArrayList<>();
    private String currentImgPaths ="";
    private Context context;
    private int OPEN_CAMERA_CODE = 0;
    private int OPEN_GALLERY_CODE = 1;
    private MessageFragment fragment;
    public MessagePresenter(Context context,MessageFragment fragment){
        this.context = context;
        this.fragment = fragment;
        File file = new File(Environment.getExternalStorageDirectory()+"/testImgs");
        if(!file.exists()){
            file.mkdir();
        }
    }
    public void addPic(View view){
        final PopupMenu popupMenu = new PopupMenu(context,view, Gravity.CENTER);
        popupMenu.getMenuInflater().inflate(R.menu.choose_img_type_menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.from_camera:
                        openCamera();
                        popupMenu.dismiss();
                        break;
                    case R.id.from_album:
                        openGallery();
                        popupMenu.dismiss();
                        break;
                    case R.id.cancle:
                        popupMenu.dismiss();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
        Log.i(TAG,"addPic");
    }

    /**
     * 调用相机
     */
    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("hhmmss");
        String name = format.format(date)+".jpg";
        currentImgPaths = PictureUtil.IMG_DIR+"/"+name;
        Uri imgUri = Uri.fromFile(PictureUtil.getTempImage(name));
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imgUri);
        ((Activity)context).startActivityForResult(intent,OPEN_CAMERA_CODE);
    }

    /**
     * 打开相册
     */
    public void openGallery() {
        Intent intent = new Intent(context,AlbumActivity.class);
        ((Activity)context).startActivityForResult(intent,OPEN_GALLERY_CODE);
    }

    public void showCameraPic(){
        Log.i(TAG,"showCameraPic");
        this.fragment.addPic(currentImgPaths);
        paths.add(currentImgPaths);
    }
}
