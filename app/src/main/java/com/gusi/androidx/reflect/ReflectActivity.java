package com.gusi.androidx.reflect;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.blankj.utilcode.util.ToastUtils;
import com.gusi.androidx.R;

public class ReflectActivity extends AppCompatActivity {

  private static final String TAG = "Ylw_Reflect";
  private EditText mEtClass;
  private EditText mEtFiled;
  private EditText mEtValue;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reflect);
    mEtClass = findViewById(R.id.et_class);
    mEtFiled = findViewById(R.id.et_filed);
    mEtValue = findViewById(R.id.et_value);
  }

  public void send(View view) {
    String className = mEtClass.getText().toString().trim();
    String filedName = mEtFiled.getText().toString().trim();
    String value = mEtValue.getText().toString().trim();
    if (TextUtils.isEmpty(className)) {
      ToastUtils.showShort("className is empty.");
      return;
    }
    if (TextUtils.isEmpty(filedName)) {
      ToastUtils.showShort("filedName is empty.");
      return;
    }
    if (TextUtils.isEmpty(value)) {
      ToastUtils.showShort("filedName is empty.");
      return;
    }

    Intent intent = new Intent();
    intent.setAction("REFLECT_CHANGE_FILED");
    intent.putExtra("CLASS_NAME", className);
    intent.putExtra("FILED_NAME", filedName);
    intent.putExtra("FILED_VALUE", Integer.parseInt(value) == 1);
    Log.i(TAG, "send: start");
    sendBroadcast(intent);
  }

  public void send1(View view) {
    String className = mEtClass.getText().toString().trim();
    String filedName = mEtFiled.getText().toString().trim();
    String value = mEtValue.getText().toString().trim();
    if (TextUtils.isEmpty(className)) {
      ToastUtils.showShort("className is empty.");
      return;
    }
    if (TextUtils.isEmpty(filedName)) {
      ToastUtils.showShort("filedName is empty.");
      return;
    }
    if (TextUtils.isEmpty(value)) {
      ToastUtils.showShort("filedName is empty.");
      return;
    }

    Intent intent = new Intent();
    intent.setAction("REFLECT_CHANGE_FILED");
    intent.putExtra("CLASS_NAME", className);
    intent.putExtra("FILED_NAME", filedName);
    intent.putExtra("FILED_VALUE", Integer.parseInt(value) == 1);
    Log.i(TAG, "send: start permission");
    sendBroadcast(intent, "Ylw_broadcast_permission");
  }
}