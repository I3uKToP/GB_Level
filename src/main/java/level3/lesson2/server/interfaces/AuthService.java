package level3.lesson2.server.interfaces;


public interface AuthService {
    void start();
    void stop();
    String getNickByLoginAndPassword(String login, String password);
}

