package level2.lesson7.server.service;

import level2.lesson7.server.interfaces.AuthService;

import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {

    private List<Entry> entries;

    public BaseAuthService() {
        entries = new ArrayList<>();
        entries.add(new Entry("Viktor", "1", "Viktor"));
        entries.add(new Entry("David", "1", "David"));
        entries.add(new Entry("Lexa", "1", "Lexa"));
    }

    @Override
    public void start() {
        System.out.println("Сервис аунтефикации запущен");

    }

    @Override
    public void stop() {
        System.out.println("Сервис аунтефикации остановлен");
    }

    @Override
    public String getNickByLoginAndPassword(String login, String password) {
        for (Entry e : entries) {
            if (e.login.equals(login) && e.pass.equals(password)) {
                return e.nick;
            }
        }
        return null;
    }

    private class Entry {
        private String login;
        private String pass;
        private String nick;

        public Entry(String login, String pass, String nick) {
            this.login = login;
            this.pass = pass;
            this.nick = nick;
        }
    }
}