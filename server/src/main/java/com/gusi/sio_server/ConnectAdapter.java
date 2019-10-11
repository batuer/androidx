package com.gusi.sio_server;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.corundumstudio.socketio.SocketIOClient;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Ylw
 * @since 2019/9/27 20:34
 */
public class ConnectAdapter extends RecyclerView.Adapter<ConnectAdapter.ViewHolder> {
    private List<SocketIOClient> mClientList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_connect, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SocketIOClient socketIOClient = mClientList.get(position);
        UUID sessionId = socketIOClient.getSessionId();
        SocketAddress remoteAddress = socketIOClient.getRemoteAddress();
        holder.mTvSession.setText("Session: " + sessionId.toString());
        holder.mTvAddress.setText("Address: " + remoteAddress.toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketIOClient.sendEvent("Resp", "I am server!" + v.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mClientList.size();
    }

    public void addConnect(SocketIOClient socketIOClient) {
        mClientList.add(0, socketIOClient);
        notifyItemInserted(0);
    }

    public void removeConnect(SocketIOClient socketIOClient) {
        int index = mClientList.indexOf(socketIOClient);
        if (index > 0) {
            mClientList.remove(index);
            notifyItemRemoved(index);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvSession;
        private final TextView mTvAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvSession = itemView.findViewById(R.id.tv_session);
            mTvAddress = itemView.findViewById(R.id.tv_address);
        }
    }
}
