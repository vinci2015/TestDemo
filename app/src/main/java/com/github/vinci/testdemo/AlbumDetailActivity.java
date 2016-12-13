package com.github.vinci.testdemo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.GridView;

import com.github.vinci.testdemo.databinding.ActivityAlbumDetailBinding;

import java.util.ArrayList;
import java.util.List;

public class AlbumDetailActivity extends AppCompatActivity {

    private ArrayList<String> choosenPaths = new ArrayList<>();
    private String[] datas;
    private ActivityAlbumDetailBinding binding;
    private ImageDetailAdapter.ChosenPathListener listener;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ImageDetailAdapter adapter = new ImageDetailAdapter(AlbumDetailActivity.this,datas,binding.grid,listener);
            binding.grid.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_album_detail);
        binding.toolbar.inflateMenu(R.menu.choose_img_menu);
        binding.toolbar.setTitle("选择图片");
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.confirm:
                        Intent intent = getIntent();
                        intent.putStringArrayListExtra("paths",choosenPaths);
                        setResult(RESULT_OK,intent);
                        finish();
                        break;
                }
                return false;
            }
        });
        datas = getIntent().getStringArrayExtra("data");
        listener = new ImageDetailAdapter.ChosenPathListener() {
            @Override
            public void chooseImg(String path) {
                if(!choosenPaths.contains(path)){
                    choosenPaths.add(path);
                    binding.choosenCount.setText("("+choosenPaths.size()+"/9)");
                }
            }

            @Override
            public void cancelChoosen(String path) {
                if(choosenPaths.contains(path)){
                    choosenPaths.remove(path);
                    binding.choosenCount.setText("("+choosenPaths.size()+"/9)");
                }
            }
        };
        binding.grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String path = datas[i];
            }
        });
        mHandler.obtainMessage().sendToTarget();
    }
}
