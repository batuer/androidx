package com.gusi.sio_server;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.TimeUtils;
import com.corundumstudio.socketio.SocketIOClient;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Ylw
 * @since 2019/9/27 20:45
 */
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private Activity mActivity;
    private List<Pair<SocketIOClient, SioMsg>> mList = new ArrayList<>();

    public MsgAdapter(Activity activity) {
        mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.item_msg, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pair<SocketIOClient, SioMsg> pair = mList.get(position);
        SocketIOClient socketIOClient = pair.first;
        UUID sessionId = socketIOClient.getSessionId();
        SocketAddress remoteAddress = socketIOClient.getRemoteAddress();
        holder.mTvSession.setText("Session: " + sessionId.toString());
        holder.mTvAddress.setText("Address: " + remoteAddress.toString());
        SioMsg sioMsg = pair.second;
        holder.mTvTime.setText(TimeUtils.millis2String(sioMsg.getTime()));
        holder.mTvType.setText(sioMsg.getType());
        holder.mTvData.setText(sioMsg.getData().toString());
    }

    public void addMsg(Pair<SocketIOClient, SioMsg> pair) {
        mList.add(0, pair);
        notifyItemInserted(0);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvSession;
        private final TextView mTvAddress;
        private final TextView mTvTime;
        private final TextView mTvType;
        private final TextView mTvData;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvSession = itemView.findViewById(R.id.tv_session);
            mTvAddress = itemView.findViewById(R.id.tv_address);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvType = itemView.findViewById(R.id.tv_type);
            mTvData = itemView.findViewById(R.id.tv_data);
        }
    }
}
