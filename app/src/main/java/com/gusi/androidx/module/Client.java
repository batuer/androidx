//package com.gusi.androidx.module;
//
//import android.util.Log;
//
//import java.io.UnsupportedEncodingException;
//
//import io.socket.client.IO;
//import io.socket.client.Manager;
//import io.socket.client.Socket;
//import io.socket.emitter.Emitter;
//import io.socket.engineio.client.transports.WebSocket;
//
///**
// * @author Ylw
// * @since 2019/9/28 17:06
// */
//public class Client {
//    private static volatile int connectNum = 0;
//
//    public static void connect(String uri) {
//        IO.Options opts = new IO.Options();
//        opts.forceNew = false;
//        opts.reconnection = true;
//        opts.reconnectionDelay = 3000;
//        opts.reconnectionDelayMax = 5000;
//        opts.timeout = -1;
//        opts.transports = new String[] {WebSocket.NAME};
//        // opts.transports = new String[]{WebSocket.NAME};
//        opts.query = "uid=uid&token=token&device=1"; // uid=uid&token=token&device=1
//        try {
//            // String uri = "http://192.168.0.100:9191";
//            // "http://192.168.0.100:9191"
//            Log.w("Fire", "Client:32行:" + uri);
//            Socket socket = IO.socket(uri, opts);
//            // 添加全局监听器
//            initSocketListener(socket);
//            socket.connect();
//        } catch (Exception e) {
//            Log.e("Fire", "Client:34行:" + e.toString());
//        }
//    }
//
//    private static void initSocketListener(Socket socket) {
//        socket.on(Manager.EVENT_TRANSPORT, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                Log.w("Fire", "ClientMainActivity:133:" + test(args));
//            }
//        });
//        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                connectNum++;
//                Log.w("Fire", "socket connect success -> " + test(args) + " :connectNum = " + connectNum);
//            }
//        });
//        socket.on(Socket.EVENT_CONNECTING, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                Log.w("Fire", "ClientMainActivity:EVENT_CONNECTING:" + test(args));
//            }
//        });
//        socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                Log.w("Fire", "ClientMainActivity:EVENT_CONNECT_ERROR130:" + test(args));
//            }
//        });
//        socket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                Log.w("Fire", "ClientMainActivity:137:" + test(args));
//            }
//        });
//        socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                connectNum--;
//                Log.w("Fire", "ClientMainActivity:disconnect:" + test(args) + " :connectNum = " + connectNum);
//            }
//        });
//
//        socket.on("Resp", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//
//            }
//        });
//    }
//
//    private static String test(Object... args) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(args.length + " :");
//        if (args != null) {
//            for (int i = 0; i < args.length; i++) {
//                if (i > 0) {
//                    sb.append("\n");
//                }
//                sb.append(args[i].toString());
//            }
//        }
//        try {
//            return new String(sb.toString().getBytes(), "GBK");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//}
