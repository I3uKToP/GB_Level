package level2.lesson7.client.two;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EchoClient extends JFrame {

    private final String SERVER_ADDRESS = "localhost";
    private final int SERVER_PORT = 8189;
    private final int WIDTH =600;
    private final int HEIGHT = 400;


    private JTextField messageInputField;
    private  JTextArea chatArea;

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    boolean isAuthorized = false;


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
                        chatArea.append(messageFromServer + "\n");
                        break;
                    }
                    chatArea.append(messageFromServer + "\n");
                }

                while (isAuthorized) {
                    String messageFromServer = dis.readUTF();
                    chatArea.append(messageFromServer + "\n");

                }
            } catch (IOException ignored) {

            }
        }).start();
    }

    public void closeConnect() {
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

    public void sendMessage () {
        if (!messageInputField.getText().trim().isEmpty()) {
            try {
                dos.writeUTF(messageInputField.getText());
                if (messageInputField.getText().equals("/end")) {
                    isAuthorized = false;
                    closeConnect();
                }
                messageInputField.setText("");
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
        send.addActionListener(e -> {

        });
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
