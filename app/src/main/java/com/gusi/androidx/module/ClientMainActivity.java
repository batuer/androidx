package com.gusi.androidx.module;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.GsonUtils;
import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseActivity;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;

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
    @BindView(R.id.cb)
    CheckBox mCb;
    private volatile Socket mSocket;

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
    public void send(View view) {
        if (mSocket == null) {
            showInfo("未连接!");
            return;
        }
        String input = mEtInput.getText().toString().trim();
        SioMsg sioMsg = new SioMsg();
        sioMsg.setData(input);
        sioMsg.setTime(System.currentTimeMillis());
        sioMsg.setType("String");
        sioMsg.setStatus("Status");
        String json = GsonUtils.toJson(sioMsg);
        Log.w("Fire", "ClientMainActivity:97行:" + json);
        if (mCb.isChecked()) {
            mSocket.emit("YlwMsg", json, new Ack() {
                @Override
                public void call(Object... args) {
                    Log.w("Fire", "ClientMainActivity:106行:" + test(args));
                }
            });
        } else {
            mSocket.emit("YlwMsg", json);
        }
    }

    @OnClick(R.id.btn_connect)
    public void connect(View view) {
        if (mBtnConnect.getText().toString().equals(getString(R.string.server_disconnect))) {
            showLoading("Connect...", false);
            disconnect().concatMap((Function<Boolean, Flowable<Socket>>)aBoolean -> {
                String ip = mEtIp.getText().toString().trim();
                String port = mEtPort.getText().toString().trim();
                return connect("http://" + ip + ":" + port);
            }).subscribe(socket -> {
                mSocket = socket;
                mBtnConnect.setText(R.string.server_connect);
                hideLoading();
            }, throwable -> {
                hideLoading();
                mBtnConnect.setText("连接异常:" + throwable.toString());
            });

        }
        if (mBtnConnect.getText().toString().equals(getString(R.string.server_connect))) {
            showLoading("Disconnect...", false);
            disconnect().subscribe(aBoolean -> {
                mBtnConnect.setText(R.string.server_disconnect);
                hideLoading();
            }, throwable -> {
                showInfo("断开异常:" + throwable.toString());
                mBtnConnect.setText(R.string.server_disconnect);
                hideLoading();
            });
        }
    }

    private Flowable<Socket> connect(final String uri) {
        return Flowable.create((FlowableOnSubscribe<Socket>)emitter -> {
            IO.Options opts = new IO.Options();
            opts.forceNew = false;
            opts.reconnection = true;
            opts.reconnectionDelay = 3000;
            opts.reconnectionDelayMax = 5000;
            opts.timeout = -1;
            opts.transports = new String[] {WebSocket.NAME};
            // opts.transports = new String[]{WebSocket.NAME};
            opts.query = "uid=uid&token=token&device=1"; // uid=uid&token=token&device=1
            Socket socket = IO.socket(uri, opts);
            // 添加全局监听器
            initSocketListener(socket);
            socket.connect();
            emitter.onNext(socket);
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private void initSocketListener(Socket socket) {
        socket.on(Manager.EVENT_TRANSPORT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                changeStatus("EVENT_TRANSPORT:" + test(args));
                Log.w("Fire", "ClientMainActivity:133:" + test(args));
            }
        });
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                changeStatus(getString(R.string.server_connect));
                Log.w("Fire", "socket connect success -> " + test(args));
            }
        });
        socket.on(Socket.EVENT_CONNECTING, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                changeStatus("连接中...");
                Log.w("Fire", "ClientMainActivity:EVENT_CONNECTING:" + test(args));
            }
        });
        socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                showInfo(test(args));
                changeStatus("连接错误!");
                Log.w("Fire", "ClientMainActivity:EVENT_CONNECT_ERROR130:" + test(args));
            }
        });
        socket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                changeStatus("连接超时!");
                Log.w("Fire", "ClientMainActivity:137:" + test(args));
            }
        });
        socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                changeStatus(getString(R.string.server_disconnect));
                Log.w("Fire", "ClientMainActivity:disconnect:" + test(args));
            }
        });

        socket.on("Resp", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.w("Fire", "ClientMainActivity:212行:" + test(args));
            }
        });
    }

    private void changeStatus(String status) {
        runOnUiThread(() -> mBtnConnect.setText(status));
    }

    private String test(Object... args) {
        StringBuilder sb = new StringBuilder();
        sb.append(args.length + " :");
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (i > 0) {
                    sb.append("\n");
                }
                Object arg = args[i];
                sb.append(arg.getClass() + " : " + arg.toString());
            }
        }
        try {
            return new String(sb.toString().getBytes(), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Flowable<Boolean> disconnect() {
        return Flowable.create((FlowableOnSubscribe<Boolean>)emitter -> {
            if (mSocket != null) {
                mSocket.close();
                mSocket.off();
                mSocket = null;
            }
            emitter.onNext(true);
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected void onDestroy() {
        disconnect().subscribe(aBoolean -> ClientMainActivity.super.onDestroy(),
            throwable -> ClientMainActivity.super.onDestroy());

    }
}
