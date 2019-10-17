package com.gusi.sio_server;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import com.corundumstudio.socketio.listener.DataListener;
import com.gusi.sio_server.base.BaseActivity;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ServerMainActivity extends BaseActivity {
    private static final int SIO_CONNECT = 0;
    private static final int SIO_DISCONNECT = 1;
    private static final int SIO_MSG = 2;

    private Button mBtnServer;
    private RecyclerView mRcvConnect;
    private RecyclerView mRcvMsg;
    private TextView mTvConnect;
    private TextView mTvMsg;

    private volatile SocketIOServer mServer;

    private ConnectAdapter mConnectAdapter;
    private MsgAdapter mMsgAdapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SIO_CONNECT:
                    mConnectAdapter.addConnect((SocketIOClient)msg.obj);
                    mTvConnect.setText("已连接: " + (mServer == null ? 0 : mServer.getAllClients().size()));
                    break;
                case SIO_DISCONNECT:
                    mConnectAdapter.removeConnect((SocketIOClient)msg.obj);
                    mTvConnect.setText("已连接: " + (mServer == null ? 0 : mServer.getAllClients().size()));
                    break;
                case SIO_MSG:
                    mMsgAdapter.addMsg((Pair<SocketIOClient, SioMsg>)msg.obj);
                    mTvMsg.setText("消息: " + mMsgAdapter.getItemCount());
                    break;
            }

        }
    };
    private CheckBox mCheckBoxb;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mBtnServer = findViewById(R.id.btn_server);
        mRcvConnect = findViewById(R.id.rcv_connect);
        mRcvMsg = findViewById(R.id.rcv_msg);
        mTvConnect = findViewById(R.id.tv_connect);
        mTvMsg = findViewById(R.id.tv_msg);
        mCheckBoxb = findViewById(R.id.cb);

        mRcvConnect.setLayoutManager(new LinearLayoutManager(this));
        mRcvConnect.setHasFixedSize(true);
        mConnectAdapter = new ConnectAdapter();
        mRcvConnect.setAdapter(mConnectAdapter);

        mRcvMsg.setLayoutManager(new LinearLayoutManager(this));
        mRcvMsg.setHasFixedSize(true);
        mMsgAdapter = new MsgAdapter(this);
        mRcvMsg.setAdapter(mMsgAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        initToolBar(toolbar, false, "服务");
        EasyPermission.hasAllPermission();
    }

    public void server(View view) {
        if (!mBtnServer.getText().toString().trim().equals(getString(R.string.server_stop))) {
            if (mServer != null) {
                disconnect();
            }
        } else {
            connect();
        }
    }

    private void disconnect() {
        if (mServer == null) {
            mBtnServer.setText(R.string.server_stop);
            return;
        }
        showLoading("Disconnect", false);
        Flowable.create((FlowableOnSubscribe<Boolean>)emitter -> {
            mServer.stop();
            mServer = null;
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(aBoolean -> {
                mBtnServer.setText(R.string.server_stop);
                hideLoading();
            });

    }

    private void connect() {
        showLoading("connect", false);
        Flowable.create((FlowableOnSubscribe<SocketIOServer>)emitter -> {
            SocketIOServer server = runSever();
            emitter.onNext(server);
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(server -> {
                Configuration configuration = server.getConfiguration();
                mBtnServer.setText(configuration.getHostname() + ":" + configuration.getPort());
                mServer = server;
                hideLoading();
            }, throwable -> {
                hideLoading();
                Log.e("Fire", "ServerMainActivity:73行:" + throwable.toString());
            });
    }

    private SocketIOServer runSever() {
        Configuration configuration = new Configuration();
        String ipAddress = NetworkUtils.getIPAddress(true);
        configuration.setHostname(ipAddress);
        Transport[] transports = {Transport.WEBSOCKET};
        configuration.setTransports(transports);
        // 注意如果开放跨域设置，需要设置为null而不是"*"
        configuration.setOrigin(null);
        configuration.setPort(9191);
        SocketIOServer server = new SocketIOServer(configuration);
        server.addConnectListener(client -> {
            Message msg = Message.obtain();
            msg.what = SIO_CONNECT;
            msg.obj = client;
            mHandler.sendMessage(msg);

            AckCallback<SioMsg> ackCallback = new AckCallback<SioMsg>(SioMsg.class) {
                @Override
                public void onSuccess(SioMsg result) {

                }
            };
            String string = client.getSessionId().toString();
            client.sendEvent("Resp", ackCallback, string);
            Log.w("Fire", "ServerMainActivity:164行:" + string);
        });
        server.addDisconnectListener(client -> {
            Message msg = Message.obtain();
            msg.what = SIO_DISCONNECT;
            msg.obj = client;
            mHandler.sendMessage(msg);
        });
        server.addEventListener("YlwMsg", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
                Log.w("Fire", "ServerMainActivity:169行:" + data + " : " + ackSender.isAckRequested());

                SioMsg sioMsg = GsonUtils.fromJson(data, SioMsg.class);
                Message msg = Message.obtain();
                msg.what = SIO_MSG;
                msg.obj = new Pair(client, sioMsg);
                mHandler.sendMessage(msg);

                if (mCheckBoxb.isChecked()) {
                    ackSender.sendAckData("I am server:" + System.currentTimeMillis());
                }
            }
        });
        server.start();
        return server;
    }

    @Override
    protected void onDestroy() {
        disconnect();
        mHandler.removeCallbacksAndMessages(null);
        AppUtils.exitApp();
        super.onDestroy();
    }

    public void test(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long begin = System.currentTimeMillis();
                long num = 0;
                while ((System.currentTimeMillis() - begin) < 10 * 60 * 1000) {
                    SioMsg sioMsg = new SioMsg();
                    num++;
                }
                Log.w("Fire", "ServerMainActivity:212行:" + num);
            }
        }).start();
    }
}
