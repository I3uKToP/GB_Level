package level2.lesson6.server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Socket socket;
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            System.out.println("Сервер запущен, ожидает подключения");
            socket = serverSocket.accept();
            System.out.println("Клиент подключился");
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            Thread thread = new Thread(()->{
                while (true) {
                    try {
                        dos.writeUTF(bufferedReader.readLine());
                    } catch (IOException ignored) {
                    }
                }
            });
            thread.start();

            while (true) {
                String str = dis.readUTF();
                if (str.equalsIgnoreCase("/end")) {
                    break;
                }
                System.out.println(str);
            }
        } catch (IOException ignored) {
        }
    }
}
