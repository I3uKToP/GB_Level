package level2.lesson4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Window extends JFrame {

    public Window() {
        setTitle("MyChat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 400, 400);
        JMenuBar mainMenu = new JMenuBar();
        JMenu mFile = new JMenu("File");
        setLayout(new GridLayout(2, 1));
        JPanel[] jp = new JPanel[4];
        for (int i = 0; i < 2; i++) {
            jp[i] = new JPanel();
            add(jp[i]);
        }


        jp[0].setLayout(new BorderLayout());
        JTextArea jta = new JTextArea();

        JScrollPane jsp = new JScrollPane(jta);
        jp[0].add(jsp);

        jp[1].setLayout(new BorderLayout());
        JTextField field = new JFormattedTextField();
        field.addActionListener(e -> {
            jta.setText(field.getText());
            field.setText(null);

        });


        JButton send = new JButton("Отправить сообщение");
        send.addActionListener(e -> {
            jta.setText(field.getText());
            field.setText(null);
        });
        jp[1].add(field, BorderLayout.CENTER);
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
                System.out.println("BYE");
            }
        });

        setVisible(true);
    }
}
