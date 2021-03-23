package level3.lesson6.server.service;

import level3.lesson6.server.interfaces.AuthService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler {
    static Logger LOGGER = LogManager.getLogger(ClientHandler.class);
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
            LOGGER.log(Level.ERROR, "Проблемы при создании обработчика клиента");
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }

    private void closeConnection() {
        myServer.unsubscribe(this);
        if (!name.equals("")) {
            LOGGER.info(name + " вышел из чата");
            myServer.broadcastMsg(name + " вышел из чата");
        }

        try {
            dis.close();
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, e);
//            e.printStackTrace();
        }
        try {
            dos.close();
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, e);
//            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, e);
//            e.printStackTrace();
        }
    }

    private void readMessage() throws IOException {
        while (true) {
            String strFromClient = dis.readUTF();
            System.out.println(name + " написал сообщение: " + strFromClient);
            if (strFromClient.trim().startsWith("/")) {
                LOGGER.info(name + " написал сообщение: " + strFromClient);
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
                        preparedStatement.setString(1, arr[1]);
                        preparedStatement.setString(2, name);
                        preparedStatement.executeUpdate();
                        myServer.broadcastMsg(name + " change nick to: " + arr[1]);
                        name = arr[1];

                    } catch (SQLException throwables) {
                        LOGGER.log(Level.ERROR, throwables);
//                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        LOGGER.log(Level.ERROR, e);
//                        e.printStackTrace();
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
        Thread timeForAuthorized = new Thread(() -> {
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!isAuthorized) {
                closeConnection();
                LOGGER.info("Прошло время авторизации");
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
                        LOGGER.info(name+ "неверный логин или пароль" + parts.toString());
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

    public static class BaseAuthService implements AuthService {


        PreparedStatement preparedStatement = null;

        public BaseAuthService() {
        }

        @Override
        public void start() {
            LOGGER.info("Сервис аунтефикации запущен");

        }

        @Override
        public void stop() {
            LOGGER.info("Сервис аунтефикации остановлен");
        }

        @Override
        public String getNickByLoginAndPassword(String login, String password) {
            try {
                preparedStatement = Singleton.getConnection().prepareStatement("SELECT * FROM users WHERE login = ?");
                preparedStatement.setString(1, login);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String pass = resultSet.getString(3);
                    String nick = resultSet.getString(4);
                    if (pass.equals(password)) {
                        return nick;
                    }
                }

            } catch (SQLException throwables) {
                LOGGER.log(Level.ERROR, throwables);

            } catch (ClassNotFoundException e) {
                LOGGER.log(Level.ERROR, e);
            }
            return null;
        }
    }
}