package level3.lesson6.server.service;

import level3.lesson6.server.interfaces.AuthService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class MyServer {
    static Logger LOGGER = LogManager.getLogger(MyServer.class);
    private final int PORT = 8190;

    private List<ClientHandler> clients;
    private AuthService authService;

    public AuthService getAuthService() {
        return authService;
    }


    public MyServer () {
        try(ServerSocket server= new ServerSocket(PORT)) {
            authService = new ClientHandler.BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            while (true) {
                LOGGER.info("Сервер ожидает подключения");
//                System.out.println("Сервер ожидает подключения");
                Socket socket = server.accept();
                LOGGER.info("Клиент подключен");
                LOGGER.info(socket.getInetAddress() + " " + socket.getLocalPort());
                System.out.println("Клиент подключен");
                new ClientHandler(this, socket);
            }
        }catch (IOException e) {
            LOGGER.log(Level.ERROR, "Ошибка в рабоет сервера", e);
//            System.out.println("Ошибка в работе сервера");
//            e.printStackTrace();
        } finally {
            if (authService != null) {
                authService.stop();
            }
        }
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler c : clients) {
            if(c.getName().equals(nick)) return true;
        }
        return false;
    }

    public synchronized void broadcastMsg (String msg) {
        for (ClientHandler c : clients) c.sendMsg(msg);
    }


    public synchronized void sendMessageToCertainClient(ClientHandler clientHandler, String to, String msg) {
        for (ClientHandler client : clients) {
            if(client.getName().equals(to)) {
                client.sendMsg(msg);
                clientHandler.sendMsg(msg);
                return;
            }
        }
        clientHandler.sendMsg("Участника с ником: " + to + " нет онлайн");
    }


    public synchronized void unsubscribe(ClientHandler c) {
        clients.remove(c);
    }

    public synchronized void subscribe(ClientHandler c) {
        clients.add(c);
    }


    public void getOnlineList(ClientHandler clientHandler) {
        System.out.println("Сейчас онлайн, нижеследующие пользователи:");
        for (ClientHandler client : clients) {
            clientHandler.sendMsg(client.getName());
        }
        System.out.println("_________________________________");
    }
}