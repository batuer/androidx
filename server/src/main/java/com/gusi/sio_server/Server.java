//package com.gusi.sio_server;
//
//import android.util.Log;
//
//import com.blankj.utilcode.util.NetworkUtils;
//import com.corundumstudio.socketio.Configuration;
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.Transport;
//import com.corundumstudio.socketio.listener.ConnectListener;
//import com.corundumstudio.socketio.listener.DisconnectListener;
//import com.corundumstudio.socketio.listener.PingListener;
//
///**
// * @author Ylw
// * @since 2019/9/29 22:25
// */
//public class Server {
//
//    public static SocketIOServer run() {
//        Configuration configuration = new Configuration();
//        String ipAddress = NetworkUtils.getIPAddress(true);
//        configuration.setHostname(ipAddress);
//        Transport[] transports = {Transport.WEBSOCKET};
//        configuration.setTransports(transports);
//        configuration.setPingInterval(60000);
//        // 注意如果开放跨域设置，需要设置为null而不是"*"
//        configuration.setOrigin(null);
//        configuration.setPort(9191);
//        SocketIOServer server = new SocketIOServer(configuration);
//        server.addConnectListener(new ConnectListener() {
//            @Override
//            public void onConnect(SocketIOClient client) {
//                int size = server.getAllClients().size();
//                Log.w("Fire", "ServerMainActivity:onConnect77:" + client + ":" + size);
//            }
//        });
//        server.addDisconnectListener(new DisconnectListener() {
//            @Override
//            public void onDisconnect(SocketIOClient client) {
//                int size = server.getAllClients().size();
//                Log.w("Fire", "ServerMainActivity:onDisconnect83:" + client + ":" + size);
//            }
//        });
//        server.addPingListener(new PingListener() {
//            @Override
//            public void onPing(SocketIOClient client) {
//
//            }
//        });
//        // server.addEventListener("Msg", SioMsg.class, new DataListener<SioMsg>() {
//        // @Override
//        // public void onData(SocketIOClient client, SioMsg data, AckRequest ackSender) throws Exception {
//        //
//        // }
//        // });
//        server.start();
//        return server;
//    }
//}
