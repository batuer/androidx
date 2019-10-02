package com.gusi.lib2;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.listener.PingListener;

/**
 * @author Ylw
 * @since 2019/9/29 22:25
 */
public class Server {
    public static void run() {
        Configuration configuration = new Configuration();
        String ipAddress = "192.168.0.102";
        configuration.setHostname(ipAddress);
        configuration.setPort(9191);
        SocketIOServer server = new SocketIOServer(configuration);

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                int size = server.getAllClients().size();
                System.out.println("ServerMainActivity:onConnect77:" + client + ":" + size);
            }
        });
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                int size = server.getAllClients().size();
                System.out.println("ServerMainActivity:onDisconnect83:" + client + ":" + size);
            }
        });
        server.addPingListener(new PingListener() {
            @Override
            public void onPing(SocketIOClient client) {

            }
        });
        // server.addEventListener("Msg", SioMsg.class, new DataListener<SioMsg>() {
        // @Override
        // public void onData(SocketIOClient client, SioMsg data, AckRequest ackSender) throws Exception {
        //
        // }
        // });
        server.start();
        String s = configuration.getHostname() + ":" + configuration.getPort();
        System.out.println("ServerMainActivity:137:" + s);
    }
}
