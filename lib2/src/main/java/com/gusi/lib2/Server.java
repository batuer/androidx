package com.gusi.lib2;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.listener.PingListener;

import java.io.File;

/**
 * @author Ylw
 * @since 2019/9/29 22:25
 */
public class Server {
    public static void main(String[] args) {
        Server.run();
    }

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
        server.addEventListener("Msg", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
                System.out.println(":" + data);
                ackSender.sendAckData("I am server.");
            }
        });

        server.addEventListener("FileReq", File.class, new DataListener<File>() {
            @Override
            public void onData(SocketIOClient client, File data, AckRequest ackSender) throws Exception {
                System.out.println(data + ":---:" + ackSender.isAckRequested());
                if (ackSender.isAckRequested()) {
                    File file = new File("lib2/server.txt");
                    ackSender.sendAckData(file);
                    client.sendEvent("FileResp", file);
                }
            }
        });

        server.start();
        String s = configuration.getHostname() + ":" + configuration.getPort();
        System.out.println("ServerMainActivity:137:" + s);
    }
}
