package com.gusi.androidx.module;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.PhoneUtils;
import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.OnClick;
import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * @author Ylw
 * @since 2019-05-31
 */
public class ClientMainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.et_ip)
    EditText mEtIp;
    @BindView(R.id.et_port)
    EditText mEtPort;
    @BindView(R.id.btn_connect)
    Button mBtnConnect;
    @BindView(R.id.btn_send)
    Button mBtnSend;
    @BindView(R.id.rcv_msg)
    RecyclerView mRcvMsg;
    @BindView(R.id.et_input)
    EditText mEtInput;
    private volatile Socket mSocket;
    private ExecutorService mService;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        super.initView();
        EasyPermission.hasAllPermission();
        mService = Executors.newCachedThreadPool();
        initToolBar(mToolbar, false, "Main");
        mEtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mBtnSend.setEnabled(s.length() > 0);
            }
        });
    }

    @OnClick(R.id.btn_send)
    public void send(View view) {}

    @OnClick(R.id.btn_connect)
    public void connect(View view) {
        // if (mBtnConnect.getText().toString().equals(getString(R.string.server_disconnect))) {
        // mService.execute(new Runnable() {
        // @Override
        // public void run() {
        // disconnect();
        // connect();
        // }
        // });
        // mBtnConnect.setText(R.string.server_connect);
        // } else {
        // mService.execute(new Runnable() {
        // @Override
        // public void run() {
        // disconnect();
        // }
        // });
        //
        // mBtnConnect.setText(R.string.server_disconnect);
        // }
        mService.execute(new Runnable() {
            @Override
            public void run() {
                // http://192.168.0.100:9191
                String ip = mEtIp.getText().toString().trim();
                String port = mEtPort.getText().toString().trim();
                Client.connect("http://" + ip + ":" + port);
            }
        });
    }

    /**
     * 创建新的 Socket 对象
     */
    private void connect() {
        IO.Options opts = new IO.Options();
        opts.forceNew = false;
        opts.reconnection = true;
        opts.reconnectionDelay = 10000;
        opts.reconnectionDelayMax = 10000;
        opts.timeout = -1;
        // opts.transports = new String[]{WebSocket.NAME};
        opts.query = PhoneUtils.getDeviceId(); // uid=uid&token=token&device=1
        try {
            String uri = "http://" + mEtIp.getText().toString().trim() + ":" + mEtPort.getText().toString().trim();
            Log.w("Fire", "ClientMainActivity:120行:" + uri + ":");
            // "http://192.168.0.100:9191"
            mSocket = IO.socket(uri, opts);
            // 添加全局监听器
            initSocketListener();
            mSocket.connect();
        } catch (Exception e) {
            Log.e("Fire", "ClientMainActivity:102行:" + e.toString());
        }
    }

    private final void initSocketListener() {
        mSocket.on(Manager.EVENT_TRANSPORT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.w("Fire", "ClientMainActivity:133行:" + test(args));
            }
        });
        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.w("Fire", "socket 链接成功 -> " + test(args));
            }
        });
        mSocket.on(Socket.EVENT_CONNECTING, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.w("Fire", "ClientMainActivity:123行:" + test(args));
            }
        });
        mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.w("Fire", "ClientMainActivity:130行:" + test(args));
            }
        });
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.w("Fire", "ClientMainActivity:137行:" + test(args));
            }
        });
        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String result = args.length != 0 ? args[0].toString() : args.toString();
                Log.w("Fire", "socket 链接已断开 -> " + test(args));
                if (result != null) {
                    mSocket.disconnect();
                    mSocket.off();
                    mSocket = null;
                }
            }
        });

        mSocket.on("Resp", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

            }
        });
    }

    private void disconnect() {
        if (mSocket != null) {
            mSocket.close();
            mSocket.off();
            mSocket = null;
        }
    }

    private String test(Object... args) {
        StringBuilder sb = new StringBuilder();
        if (args != null) {
            for (Object arg : args) {
                sb.append(arg.toString() + "\n");
            }
        }
        return sb.toString();
    }
}
