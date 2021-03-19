package level3.lesson3.server.service;

import level3.lesson3.server.interfaces.AuthService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseAuthService implements AuthService {

    //    private List<Entry> entries;

    PreparedStatement preparedStatement = null;

    public BaseAuthService() {

//        entries = new ArrayList<>();
//        entries.add(new Entry("Viktor", "1", "Viktor"));
//        entries.add(new Entry("David", "1", "David"));
//        entries.add(new Entry("Lexa", "1", "Lexa"));
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
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

//    private class Entry {
//        private String login;
//        private String pass;
//        private String nick;
//
//        public Entry(String login, String pass, String nick) {
//            this.login = login;
//            this.pass = pass;
//            this.nick = nick;
//        }
//    }
}