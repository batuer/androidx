package com.gusi.androidx.module.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * @author Ylw
 * @since 2020/8/11 22:00
 */
public class MyEt extends EditText {
    private static final String TAG = "Fire_Et";

    public MyEt(Context context) {
        super(context);
    }

    public MyEt(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.w(TAG, "onTextChanged: "+ s + " : " + getLayout());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public MyEt(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setCustomSelectionActionModeCallback(ActionMode.Callback actionModeCallback) {
        super.setCustomSelectionActionModeCallback(actionModeCallback);
    }

    @Override
    public boolean hasSelection() {
        return super.hasSelection();
    }

    @Override
    public void setSelection(int index) {
        super.setSelection(index);
    }

    @Override
    public void setSelection(int start, int stop) {
        super.setSelection(start, stop);
    }
}
