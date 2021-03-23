package level2.lesson7.server.interfaces;


public interface AuthService {
    void start();
    void stop();
    String getNickByLoginAndPassword(String login, String password);
}

