package com.github.vinci.testdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class AlbumDetailActivity extends AppCompatActivity {

    private String[] datas;
    private GridView gridView;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ImageDetailAdapter adapter = new ImageDetailAdapter(AlbumDetailActivity.this,datas,gridView);
            gridView.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        gridView = (GridView) findViewById(R.id.grid);
        datas = getIntent().getStringArrayExtra("data");

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String path = datas[i];
            }
        });
        mHandler.obtainMessage().sendToTarget();
    }
}
