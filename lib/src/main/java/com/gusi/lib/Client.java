package com.gusi.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;

/**
 * @author Ylw
 * @since 2019/9/28 17:06
 */
public class Client {
    // private static ExecutorService sService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        new Client().connect();
        // lock();
    }

    public void connect() {
        IO.Options opts = new IO.Options();
        opts.forceNew = false;
        opts.reconnection = true;
        opts.reconnectionDelay = 3000;
        opts.reconnectionDelayMax = 5000;
        opts.timeout = -1;
        opts.transports = new String[] {WebSocket.NAME};
        // opts.transports = new String[]{WebSocket.NAME};
        opts.query = "uid=uid&token=token&device=1"; // uid=uid&token=token&device=1
        try {
            String uri = "http://192.168.0.102:9191";
            // "http://192.168.0.100:9191"
            Socket socket = IO.socket(uri, opts);

            // 添加全局监听器
            initSocketListener(socket);
            socket.connect();
        } catch (Exception e) {
            System.err.println("---:" + e.toString());
        }
    }

    private void recycleEmit(Socket socket, File file, String uuid) {
        if (file == null || !file.exists()) {
            return;
        }
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        int pageNum = 50 * 1024;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            int count = 0;
            byte[] bys = new byte[pageNum + 36];
            while ((count = is.read(bys, 36, pageNum)) != -1) {
                lock.lock();

                byte[] bytes = uuid.getBytes();
                for (int i = 0; i < bytes.length; i++) {
                    bys[i] = bytes[i];
                }
                byte[] countBys = ByteUtils.intToByte(count);
                for (int i = 32; i < 36; i++) {
                    bys[i] = countBys[i - 32];
                }
                socket.emit("FileTransport", bys, new Ack() {
                    @Override
                    public void call(Object... args) {
                        lock.lock();
                        condition.signal();
                        lock.unlock();
                    }
                });
                condition.await();
                lock.unlock();
            }
            System.out.println("FileTransport over:" + file.getName() + " : " + file.length());
        } catch (Exception e) {
            System.out.println(":" + e.toString());
        } finally {
            CloseableUtils.closeCloseables(is);
        }
    }

    private void fileReq(Socket socket, File file) {
        socket.emit("FileReq", file.getName(), new Ack() {
            @Override
            public void call(Object... args) {
                if (args.length > 0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            recycleEmit(socket, file, (String)args[0]);
                        }
                    }).start();
                }
            }
        });
    }

    private void initSocketListener(Socket socket) {
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("socket connect success -> " + test(args));
                File file = new File("lib");
                File[] files = file.listFiles();
                for (File file1 : files) {
                    if (file1.isFile()) {
                        fileReq(socket, file1);
                    }
                }
            }
        });

        socket.on(Manager.EVENT_TRANSPORT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("ClientMainActivity:133:" + test(args));
            }
        });
        socket.on("FileResp", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("FileResp:" + test(args));
            }
        });
        socket.on(Socket.EVENT_CONNECTING, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("ClientMainActivity:EVENT_CONNECTING:" + test(args));
            }
        });
        socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("ClientMainActivity:EVENT_CONNECT_ERROR130:" + test(args));
            }
        });
        socket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("ClientMainActivity:137:" + test(args));
            }
        });
        socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("ClientMainActivity:disconnect:" + test(args));
            }
        });
        socket.on(Socket.EVENT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("ClientMainActivity:EVENT_ERROR:" + test(args));
            }
        });
        // :EVENT_ERROR:1 :io.socket.engineio.client.EngineIOException:---:io.socket.engineio.client.EngineIOException:
        // websocket error
        socket.on(Manager.EVENT_TRANSPORT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("ClientMainActivity:EVENT_TRANSPORT:" + test(args));
            }
        });

        socket.on("Resp", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

            }
        });
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
                sb.append(arg.getClass().getTypeName() + ":---:" + arg.toString());
            }
        }
        try {
            return new String(sb.toString().getBytes(), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void lock() {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        long timeMillis = System.currentTimeMillis();

        System.out.println("lock.lock");
        lock.lock();
        System.out.println("lock.lock()111");

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("--lock.lock");
                lock.lock();
                System.out.println("--lock.lock()111");
                while ((System.currentTimeMillis() - timeMillis) < 5000) {
                }
                System.out.println("condition.signal()");
                condition.signal();
                System.out.println("condition.signal()111");
                System.out.println("--lock.unlock()");
                lock.unlock();
                System.out.println("--lock.unlock()111");

            }
        }).start();
        try {
            System.out.println("condition.await() !");
            condition.await();
            System.out.println("condition.await() 111!");
        } catch (InterruptedException e) {
            System.out.println("condition.await():" + e.toString());
        }
        System.out.println("over");
        lock.unlock();
        System.out.println("over11");
    }
}
