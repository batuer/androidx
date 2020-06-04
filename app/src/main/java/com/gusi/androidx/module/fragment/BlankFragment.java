package com.gusi.androidx.module.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gusi.androidx.R;
import com.gusi.androidx.module.lv.ListViewActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {


    public BlankFragment() {
        // Required empty public constructor
//        isAdded();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.w("Fire", "onAttach:32行:" + isAdded());
        super.onAttach(context);
        Log.w("Fire", "onAttach:34行:" + isAdded());
    }

    public static BlankFragment newInstance(String from) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString("From", from);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.w("Fire", "onCreate:47行:" + isAdded());
        super.onCreate(savedInstanceState);
        Log.w("Fire", "onCreate:49行:" + isAdded());
        if (savedInstanceState != null) {

            for (String key : savedInstanceState.keySet()) {
                Log.w("Fire",
                        "BlankFragment:38行:onCreate:key = " + key + " , value = " + savedInstanceState.get(key));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.w("Fire", "onCreateView:62行:" + isAdded());
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        TextView textView = view.findViewById(R.id.tv);
        textView.setText(getArguments().getString("From") + "_" + this);
        textView.setOnClickListener(v -> startActivity(new Intent(getContext(), ListViewActivity.class)));
        return view;
    }

    @Override
    public void onDestroyView() {
        Log.w("Fire", "onDestroyView:71行:" + isAdded());
        super.onDestroyView();
        Log.w("Fire", "onDestroyView:73行:" + isAdded());
    }

    @Override
    public void onDestroy() {
        Log.w("Fire", "onDestroy:78行:" + isAdded());
        super.onDestroy();
        Log.w("Fire", "onDestroy:80行:" + isAdded());
    }

    @Override
    public void onDetach() {
        Log.w("Fire", "onDetach:85行:" + isAdded());
        super.onDetach();
        Log.w("Fire", "onDetach:87行:" + isAdded());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.w("Fire", "BlankFragment:93行:onViewStateRestored");
        if (savedInstanceState != null) {

            for (String key : savedInstanceState.keySet()) {
                Log.w("Fire",
                        "BlankFragment:48行:onViewStateRestored:key = " + key + " , value = " + savedInstanceState.get(key));
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putString("onSaveInstanceState", getClass().getName() + "__onSaveInstanceState");
//        outState.putByteArray("bys", new byte[1024 * 1024 * 100]);
        Log.w("Fire", "BlankFragment:55行:onSaveInstanceState");
        Log.w("Fire", Log.getStackTraceString(new Throwable()));
    }
}