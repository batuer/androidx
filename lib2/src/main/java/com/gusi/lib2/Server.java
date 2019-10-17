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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ylw
 * @since 2019/9/29 22:25
 */
public class Server {
    private static ConcurrentHashMap<String, File> sMap = new ConcurrentHashMap<>();

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

        server.addEventListener("FileReq", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String fileName, AckRequest ackSender) throws Exception {

                try {
                    File file = new File("lib2/file");
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    for (String s : file.list()) {
                        if (s.equals(fileName)) {
                            fileName = fileName + "_" + System.currentTimeMillis();
                            break;
                        }
                    }
                    File file1 = new File(file, fileName);
                    file1.createNewFile();
                    String uuid = UUID.randomUUID().toString().replace("-", "");
                    sMap.put(uuid, file1);
                    ackSender.sendAckData(uuid);
                    System.out.println("FileReq:" + uuid + " : " + file1.getName());
                } catch (Exception e) {
                    System.out.println(":" + e.toString());
                }

            }
        });

        server.addEventListener("FileTransport", byte[].class, new DataListener<byte[]>() {
            @Override
            public void onData(SocketIOClient client, byte[] data, AckRequest ackSender) throws Exception {
                byte[] uuidBys = new byte[32];
                for (int i = 0; i < 32; i++) {
                    uuidBys[i] = data[i];
                }
                byte[] countBys = new byte[4];
                for (int i = 32; i < 36; i++) {
                    countBys[i - 32] = data[i];
                }
                String uuid = new String(uuidBys);
                int count = ByteUtils.byte2Int(countBys);
                File file = sMap.get(uuid);
                writeFile(file, count, data);
                ackSender.sendAckData(file.getName());
            }
        });
        server.start();
    }

    private static void writeFile(File file, int count, byte[] data) throws IOException {
        System.out.println(count + ":--:" + file.getName());
        FileOutputStream fos = new FileOutputStream(file, true);
        fos.write(data, 36, count);
        fos.close();
    }
}
