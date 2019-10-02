package com.gusi.sio_server;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.NetworkUtils;
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
        });
        server.addDisconnectListener(client -> {
            Message msg = Message.obtain();
            msg.what = SIO_DISCONNECT;
            msg.obj = client;
            mHandler.sendMessage(msg);
        });
        server.addEventListener("Msg", SioMsg.class, new DataListener<SioMsg>() {
            @Override
            public void onData(SocketIOClient client, SioMsg data, AckRequest ackSender) throws Exception {
                Message msg = Message.obtain();
                msg.what = SIO_MSG;
                msg.obj = new Pair(client, data);
                mHandler.sendMessage(msg);
            }
        });
        server.start();
        return server;
    }

    @Override
    public void onBackPressed() {
        disconnect();
        mHandler.removeCallbacksAndMessages(null);
        AppUtils.exitApp();
    }
}
