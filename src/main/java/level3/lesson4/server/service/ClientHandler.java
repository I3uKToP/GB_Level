package level3.lesson4.server.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler {
    private MyServer myServer;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;

    private String name;

    private boolean isAuthorized = false;

    PreparedStatement preparedStatement = null;
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    public String getName() {
        return name;
    }

    public ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            executorService.execute(new Thread(() -> {
                try {
                    authentication();
                    readMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            }));
        } catch (IOException e) {
            throw new RuntimeException("Проблемы пр и создании обработчика клиента");
        }
    }

    private void closeConnection() {
        myServer.unsubscribe(this);
        if (!name.equals("")) {
            myServer.broadcastMsg(name + " вышел из чата");
        }

        try {
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readMessage() throws IOException {
        while (true) {
            String strFromClient = dis.readUTF();
            System.out.println(name + " написал сообщение: " + strFromClient);
            if (strFromClient.trim().startsWith("/")) {
                if (strFromClient.trim().startsWith("/w")) {
                    String[] arr = strFromClient.split(" ", 3);
                    myServer.sendMessageToCertainClient(this, arr[1], name + ": " + arr[2]);
                }
                if (strFromClient.trim().startsWith("/list")) {
                    myServer.getOnlineList(this);
                }
                if (strFromClient.trim().startsWith("/changeNick")) {
                    try {
                        preparedStatement = Singleton.getConnection().prepareStatement("UPDATE users SET nick = ? WHERE nick = ?");
                        String[] arr = strFromClient.split(" ");
                        preparedStatement.setString(1,arr[1]);
                        preparedStatement.setString(2, name);
                        preparedStatement.executeUpdate();
                        myServer.broadcastMsg(name + " change nick to: " + arr[1]);
                        name = arr[1];

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if (strFromClient.trim().equals("/end")) {
                    return;
                }
            } else {
                myServer.broadcastMsg(name + ": " + strFromClient);
            }

        }
    }

    private void authentication() throws IOException {
        Thread timeForAuthorized = new Thread(()->{
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!isAuthorized) {
                closeConnection();
            }
        });
        executorService.execute(timeForAuthorized);

        while (true) {
            String str = dis.readUTF();
            if (str.startsWith("/auth")) {
                String[] parts = str.split(" ");
                String nick = myServer.getAuthService().getNickByLoginAndPassword(parts[1], parts[2]);
                if (nick != null) {
                    if (!myServer.isNickBusy(nick)) {
                        isAuthorized = true;
                        sendMsg("/authok " + parts[1]);
                        name = nick;
                        myServer.broadcastMsg(name + " зашел в чат");
                        myServer.subscribe(this);
                        return;
                    } else {
                        sendMsg("неверный логин и/или пароль");
                    }
                }
            }
        }
    }

    public void sendMsg(String msg) {
        try {
            dos.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}