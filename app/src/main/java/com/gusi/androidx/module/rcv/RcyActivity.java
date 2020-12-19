package com.gusi.androidx.module.rcv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gusi.androidx.R;
import com.gusi.androidx.module.aidl.AidlService;

public class RcyActivity extends AppCompatActivity {

  private static final String TAG = "Fire_Rcv";
  private CheckBox mCb;
  private RecyclerView mRcv;
  private MyAdapter mAdapter;

  public RcyActivity() {
    Log.d(TAG, Log.getStackTraceString(new Throwable()));
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(newBase);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rcy);
    mCb = findViewById(R.id.cb);
    mCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mAdapter.notifyDataSetChanged();
      }
    });

    mRcv = findViewById(R.id.rcv);
    mAdapter = new MyAdapter();
    mRcv.setAdapter(mAdapter);
    mRcv.setLayoutManager(new LinearLayoutManager(this));
    mRcv.setHasFixedSize(true);
  }

  int i = 0;

  public void start(View view) {
//        getMainExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                Log.w(TAG, "run: ");
//                SystemClock.sleep(21000);
//                Log.w(TAG, "run: 11");
//            }
//        });
    startService(new Intent(this, AidlService.class));
    for (int i1 = 0; i1 < 20; i1++) {
      int finalI = i1;
      view.post(new Runnable() {
        @Override
        public void run() {
          Log.w(TAG, "run: " + finalI);
          SystemClock.sleep(1000);
          Log.e(TAG, "run: " + finalI);
        }
      });
    }
//
//        Intent intent = new Intent(this, MyService.class);
//        intent.putExtra("key", "value");
//        MyService.enqueueWork(this, intent);
//        Log.w(TAG, "start: " );


  }

  private class MyRunnable implements Runnable {
    private int i;

    public MyRunnable(int i) {
      this.i = i;
    }

    @Override
    public void run() {
      Log.w(TAG, "run: " + Thread.currentThread().getName() + " : " + i);
      SystemClock.sleep(5000000);
    }
  }

  private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      TextView itemView = (TextView) holder.itemView;
      itemView.setText("Position: " + position);
    }

    int count = 1;

    @Override
    public int getItemCount() {
      return mCb.isChecked() ? count++ : 100;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
      public ViewHolder(View itemView) {
        super(itemView);
      }
    }
  }
}