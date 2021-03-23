package level3.lesson3.client.one;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import static java.awt.Font.ITALIC;

public class EchoClient extends JFrame {

    private final String SERVER_ADDRESS = "localhost";
    private final int SERVER_PORT = 8190;
    private final int WIDTH = 600;
    private final int HEIGHT = 400;
    private final int TIME_FOR_CLOSE_CONNECTION = 400;



    private JTextField messageInputField;
    private  JTextArea chatArea;

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String login;

    boolean isAuthorized = false;

    private AtomicInteger time = new AtomicInteger(0);


    public EchoClient() {

        try {
            openConnection();
        } catch (IOException ignored) {}
        settingWindow();
    }

    private void openConnection() throws IOException{
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
        new Thread(() -> {
            try {
                while (true) {
                    String messageFromServer = dis.readUTF();
                    if (messageFromServer.startsWith("/authok")) {
                        isAuthorized = true;
                        String [] arr = messageFromServer.split(" ");
                        login = arr[1];
                        File history = new File("C://history//history_" + login +".txt");
                        if (history.exists()) {
                            try (BufferedReader reader = new BufferedReader(new FileReader(history))) {
                                String str;
                                Font fontForHistory = new Font("SansSerif", ITALIC, 14);
                                int count = 0;
                                while ((str= reader.readLine()) != null && count <100) {
                                    chatArea.setFont(fontForHistory);
                                    chatArea.append(str);
                                    chatArea.append("\n");
                                    count++;
                                }
                                chatArea.append("____________________________________________\n");
                                chatArea.append("Конец истории\n");
                                chatArea.append("____________________________________________\n");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    chatArea.append(messageFromServer + "\n");
                }

                if (isAuthorized) {
                    Thread timeToCloseConnection = new Thread(()->{
                        while (time.intValue() < TIME_FOR_CLOSE_CONNECTION && isAuthorized) {
                            try {
                                time.getAndIncrement();
                                //System.out.println(time);
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        isAuthorized = false;
                        closeConnect();
                    });
                    timeToCloseConnection.setDaemon(true);
                    timeToCloseConnection.start();
                }

                while (isAuthorized) {

                    String messageFromServer = dis.readUTF();
                    chatArea.append(ZonedDateTime.now().toLocalTime().getHour() +" : " + ZonedDateTime.now().toLocalTime().getMinute() + " " + messageFromServer + "\n");

                }
            } catch (IOException ignored) {

            }
        }).start();
    }

    public void closeConnect() {
        saveHistory();
        try {
            dis.close();
        } catch (IOException ignored) {}
        try {
            dos.close();
        }catch (IOException ignored) {}
        try {
            socket.close();
        } catch (IOException ignored) {}
    }

    private void saveHistory() {
        File dir = new File("C://history//");
        boolean createdDir = dir.mkdirs();
        if(createdDir) {
            //System.out.println("Директория создана");
        }
        //else System.out.println("директория не создана");
        File history = new File("C://history//history_" + login +".txt");
        //System.out.println(history.exists() + " проверка существует ли файл");
        if (!history.exists()) {
            try
            {
                boolean created = history.createNewFile();
                if(created)
                    System.out.println("File has been created");
            }
            catch(IOException ex){
                // System.out.println("не удалось");
            }
        }
        //System.out.println(history.exists() + " проверка существует ли файл");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(history))) {
            writer.write(chatArea.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage () {

        if (!messageInputField.getText().trim().isEmpty()) {
            try {
                dos.writeUTF(messageInputField.getText());
                if (messageInputField.getText().equals("/end")) {
                    isAuthorized = false;
                    closeConnect();
                }
                messageInputField.setText("");
                time = new AtomicInteger(0);
            } catch (IOException ignored) {}
        }
    }

    public void settingWindow() {
        setTitle("Client");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        JMenuBar mainMenu = new JMenuBar();
        JMenu mFile = new JMenu("File");
        setLayout(new GridLayout(2, 1));
        JPanel[] jp = new JPanel[4];
        for (int i = 0; i < 2; i++) {
            jp[i] = new JPanel();
            add(jp[i]);
        }


        jp[0].setLayout(new BorderLayout());
        chatArea= new JTextArea();
        chatArea.setLineWrap(true);

        JScrollPane jsp = new JScrollPane(chatArea);
        jp[0].add(jsp);

        jp[1].setLayout(new BorderLayout());
        messageInputField = new JFormattedTextField();
        messageInputField.addActionListener(e -> sendMessage());


        JButton send = new JButton("Отправить сообщение");
        send.addActionListener(e -> sendMessage());
        jp[1].add(messageInputField, BorderLayout.CENTER);
        jp[1].add(send, BorderLayout.EAST);


        JMenuItem miFileNew = new JMenuItem("New");
        JMenuItem miFileExit = new JMenuItem("Exit");
        mainMenu.add(mFile);
        setJMenuBar(mainMenu);


        mFile.add(miFileNew);
        mFile.addSeparator();
        mFile.add(miFileExit);

        miFileExit.addActionListener(e -> System.exit(0));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    dos.writeUTF("/end");
                    closeConnect();
                } catch (IOException ignored) {}
                System.out.println("BYE");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EchoClient::new);
    }

}
