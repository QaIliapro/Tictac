package b;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {

    private static final int WIN_WIDTH = 507;
    private static final int WIN_HEIGHT = 555;
    private static final int WIN_POSX = 650;
    private static final int WIN_POSY = 250;

    private Map map;
    SettingsWindow settings;

    GameWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIN_WIDTH,WIN_HEIGHT);
        setLocation(WIN_POSX,WIN_POSY);
        setTitle("TicTacToe");
        setResizable(false);
        JButton btnStart = new JButton("Start a new game");
        JButton btnExit = new JButton("Exit");
        JPanel panelButton = new JPanel(new GridLayout(1,2));
        panelButton.add(btnStart);
        panelButton.add(btnExit);
        map = new Map();
        settings = new SettingsWindow(this);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.setVisible(true);

            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }
        });

        add(panelButton, BorderLayout.SOUTH);
        add(map,BorderLayout.CENTER);
        setVisible(true);
    }
    void applySettings(int mode,int size, int len) {
        map.startNewGame(mode,size,size,len);

    }


}
