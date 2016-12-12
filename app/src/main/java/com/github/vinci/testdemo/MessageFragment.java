package com.github.vinci.testdemo;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vinci.testdemo.adapter.MessageGridAdapter;
import com.github.vinci.testdemo.databinding.FragmentMessageBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {
    private static final String TAG = MessageFragment.class.getSimpleName();
    private static final int OPEN_CAMERA_CODE = 0;
    private static final int OPEN_GALLERY_CODE = 1;
    private MessageGridAdapter adapter;
    private FragmentMessageBinding binding;
    private MessagePresenter presenter;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_message,container,false);
        presenter = new MessagePresenter(getContext(),this);
        binding.setPresenter(presenter);
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG,resultCode+" pp");
        switch (requestCode){
            case OPEN_CAMERA_CODE:
                if(resultCode == -1) {
                    Log.i("result","aa");
                    presenter.showCameraPic();
                }
                break;
            case OPEN_GALLERY_CODE:
                break;
        }
    }
    public void addPic(String path){
        if(adapter == null){
            List<String> list = new ArrayList<>();
            list.add(path);
            adapter = new MessageGridAdapter(getContext(),list,binding.gridImg);
            binding.gridImg.setAdapter(adapter);
        }else {
            adapter.add(path);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
