package it.polimi.ing.sw.psp017.view.GraphicUserInterface;
import it.polimi.ing.sw.psp017.controller.client.Client;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.PowerActiveMessage;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.SelectedTileMessage;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.UndoMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.BoardMessage;
import it.polimi.ing.sw.psp017.model.Vector2d;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class BoardGUI extends JFrame {
    private final Dimension dim;
    public JButton[] playersButton;
    private JDialog popUp;
    private Client client;
    private final Color trasparentColor = new Color(.1f,.1f,.1f,.1f);
    private Timer timerThread;

    public BoardGUI(Client client) {
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.client = client;
        initComponents();

    }


    private void initComponents() {

        buttonGroup = new ButtonGroup();
        this.setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource("logo.png")).getImage());

        kGradientPanel1 = new KGradientPanel();


        kGradientPanel1.setkEndColor(new Color(151, 241, 255, 164));
        kGradientPanel1.setkStartColor(new Color(48, 36, 245));


        southPanel = new KGradientPanel();
        southPanel.setkEndColor(new Color(253, 121, 2, 164));
        southPanel.setkStartColor(new Color(245, 24, 17));
        southPanel.backgroundGradient(50);
        southPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(0, 153, 255), new Color(0, 255, 255), new Color(0, 102, 102), new Color(0, 153, 153)));


        northPanel_JPanel = new KGradientPanel();
        northPanel_JPanel.setkEndColor(new Color(253, 121, 2, 164));
        northPanel_JPanel.setkStartColor(new Color(245, 24, 17));
        northPanel_JPanel.backgroundGradient(50);
        northPanel_JPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(0, 153, 255), new Color(0, 255, 255), new Color(0, 102, 102), new Color(0, 153, 153)));



        DXPanel = new KGradientPanel();
        DXPanel.setkEndColor(new Color(136, 2, 253, 164));
        DXPanel.setkStartColor(new Color(17, 245, 101, 180));
        DXPanel.backgroundGradient(50);
        DXPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(0, 153, 255), new Color(0, 255, 255), new Color(0, 102, 102), new Color(0, 153, 153)));


        SXPanel = new KGradientPanel();
        SXPanel.setkEndColor(new Color(136, 2, 253, 164));
        SXPanel.setkStartColor(new Color(17, 245, 101, 180));
        SXPanel.backgroundGradient(50);
        SXPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(0, 153, 255), new Color(0, 255, 255), new Color(0, 102, 102), new Color(0, 153, 153)));






        tilePanel = new JPanel();
       // northPanel_JPanel = new JPanel();
        messageLabel_NorthPanel = new JLabel();
        boardGamePanel = new JPanel() {

            public void paintComponent(Graphics g) {

                super.paintComponent(g);

                Image img = new ImageIcon(getClass().getClassLoader().getResource("STUFF/BOARDgAMEbACKgROUND.png")).getImage();

                Graphics2D g2d = (Graphics2D) g;
                int x = (this.getWidth() - img.getWidth(null)) / 2;
                int y = (this.getHeight() - img.getHeight(null)) / 2;
                g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);

            }

        };

        workerPanel = new JPanel();
        buttonPanel = new JPanel();

        upperDXPanel = new JPanel();
        undo = new AbstractButton();
        powerActivated = new JToggleButton();
        lowerDXPanel = new JPanel();

        quitButton = new AbstractButton();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] option = {"Quit", "Cancel"};
                int n = JOptionPane.showOptionDialog(e.getComponent(), "Are you sure you want to quit the game ", "Quit ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);;
                if (n == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }

            }
        });
        setMinimumSize(new Dimension(1600, 900));
        this.setExtendedState(MAXIMIZED_BOTH);
        setMaximumSize(new Dimension(1600, 900));
        setPreferredSize(new Dimension(1600, 900));
        setSize(new Dimension(1600, 900));
        kGradientPanel1.setLayout(new BorderLayout());


        //northPanel_JPanel.setOpaque(false);
        FlowLayout layout = new FlowLayout();
        layout.setVgap(10);
        northPanel_JPanel.setLayout(layout);
        messageLabel_NorthPanel.setFont(new Font("Tahoma", 3, 36));
        messageLabel_NorthPanel.setForeground(new Color(255, 255, 255));
        messageLabel_NorthPanel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel_NorthPanel.setVerticalAlignment(SwingConstants.BOTTOM);
        messageLabel_NorthPanel.setText(" Loading... ");
        northPanel_JPanel.add(messageLabel_NorthPanel);
        kGradientPanel1.add(northPanel_JPanel, BorderLayout.NORTH);

        workerPanel.setOpaque(false);
        workerPanel.setLayout(new GridLayout(5, 5, 2, 2));



        tilePanel.setOpaque(false);
        tilePanel.setLayout(new GridLayout(5, 5, 2, 2));


        buttonPanel.setLayout(new GridLayout(5, 5));
        buttonGrid = new JButtonTile[5][5];
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                buttonGrid[row][col]= new JButtonTile(new Vector2d(row,col));
                buttonPanel.add(buttonGrid[row][col]);


                workerPanel.add(buttonGrid[row][col].getWorkerJLabel());
                tilePanel.add(buttonGrid[row][col].getBuildJLabel());

                ButtonListeners buttonListener = new ButtonListeners();

                buttonGrid[row][col].addMouseListener(buttonListener);
                buttonGroup.add(buttonGrid[row][col]); //si puo eliminare??
            }

        }

        buttonPanel.setOpaque(false);

        //DXPanel.setOpaque(false);
        DXPanel.setPreferredSize(new Dimension(200, 400));
        DXPanel.setMinimumSize(new Dimension(100, 400));
        DXPanel.setLayout(new GridLayout(2, 1));

        upperDXPanel.setOpaque(false);
        upperDXPanel.setLayout(new GridLayout(3, 1, 0, 2));

        undo.setEnabled(false);
        undo.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("STUFF/undo.png"))));
        //  undo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("STUFF/pointing-left.png")));
        //undo.customizeDesignButton(trasparentColor, .getImage());
        undo.setToolTipText("Undo");
        undo.setFont(new Font("Tahoma", 3, 24));
        undo.setForeground(Color.white);
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == undo)
                    clearUndo();
                client.getNetworkHandler().sendMessage(new UndoMessage());
            }
        });
        upperDXPanel.add(undo);
        powerActivated.setText("");
        //    powerActivated.setBackground(trasparentColor);
        powerActivated.setIcon(GodView.getCard(client.getCard()).getIconPower());
        // powerActivated.setBorder(BorderFactory.createLoweredBevelBorder());
        powerActivated.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //powerActivated.setEnabled(false);
                switchColor(powerActivated, Color.red, Color.green);
                client.getView().notifyIsPowerActive(new PowerActiveMessage(true));

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        upperDXPanel.add(powerActivated);
        upperDXPanel.add(new JLabel());
        DXPanel.add(upperDXPanel);

        lowerDXPanel.setOpaque(false);


        Border compound;
        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        compound = BorderFactory.createCompoundBorder(
                raisedbevel, loweredbevel);

        playersButton = new JButton[client.playersInfo.size()];
        for(int i = 0; i < client.playersInfo.size();i++)
        {

            playersButton[i] = createJButton(i);


            playersButton[i].setHorizontalTextPosition(SwingConstants.CENTER);
            playersButton[i].setOpaque(false);

            if(client.playersInfo.get(i).playerNumber == client.getPlayerNumber())
            {
                playersButton[i].setToolTipText("Your Player");
                DXPanel.add(playersButton[i]);

            }
            else
            {
                playersButton[i].setToolTipText("Opponent Player");
                SXPanel.add(playersButton[i]);
                   /* if(client.playersInfo.size() == 2)
                    {
                        JButton extensionButton = createJButton(i);
                        extensionButton.setIcon(GodView.getCard(client.playersInfo.get(i).card).getIconDescription());
                        SXPanel.add(extensionButton);

                    }*/
            }
        }


           /* JLabel name = new JLabel();
            upperDXPanel.add(name);*/


        kGradientPanel1.add(DXPanel, BorderLayout.EAST);


        //SXPanel.setOpaque(false);
        SXPanel.setPreferredSize(new Dimension(200, 400));
        SXPanel.setMinimumSize(new Dimension(100, 400));
        // SXPanel.setSize(200,400);
        SXPanel.setLayout(new GridLayout(2, 1));



        kGradientPanel1.add(SXPanel, BorderLayout.WEST);


        southPanel.setPreferredSize(new Dimension(1120, 100));
        quitButton.setToolTipText("QUIT");
        quitButton.setIcon((new ImageIcon(getClass().getClassLoader().getResource("STUFF/quit.png"))));
        quitButton.setVisible(true);
        //southPanel.setOpaque(false);
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });


        southPanel.setMinimumSize(new Dimension(this.getMinimumSize().width, 200));

        kGradientPanel1.add(southPanel, BorderLayout.SOUTH);

        Dimension dimBoard = new Dimension((int) (dim.height*0.7), (int) (dim.height*0.7));
        JLayeredPane layer = new JLayeredPane();
        layer.setPreferredSize(dimBoard);
        layer.setSize(dimBoard);
        layer.setMinimumSize(dimBoard);
        kGradientPanel1.add(layer, BorderLayout.CENTER);
        System.out.println(layer.getWidth());
        System.out.println(this.getWidth());
        System.out.println(SXPanel.getMinimumSize().width);
        boardGamePanel.setBounds(SXPanel.getWidth() + dimBoard.width / 2, 0, dimBoard.width, dimBoard.height);
        buttonPanel.setBounds(SXPanel.getWidth() + dimBoard.width / 2, 0, dimBoard.width, dimBoard.height);
        workerPanel.setBounds(SXPanel.getWidth() + dimBoard.width / 2, 0, dimBoard.width, dimBoard.height);
        tilePanel.setBounds(SXPanel.getWidth() + dimBoard.width / 2, 0, dimBoard.width, dimBoard.height);
        layer.add(boardGamePanel, Integer.valueOf(0));
        layer.add(buttonPanel, Integer.valueOf(3));
        layer.add(workerPanel, Integer.valueOf(2));
        layer.add(tilePanel, Integer.valueOf(1));


        this.setContentPane(kGradientPanel1);

        setLocationRelativeTo(null);
        setSize(dim.width / 2, dim.width / 2);
        // kGradientPanel1.backgroundGradient();
        //kGradientPanel1.backgroundTransition();
        this.revalidate();
        this.repaint();
        setVisible(true);

        kGradientPanel1.backgroundGradient();
        kGradientPanel1.backgroundTransition();
        southPanel.add(quitButton);

    }

    private JButton createJButton(int i) {

        Border compound;
        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        compound = BorderFactory.createCompoundBorder(
                raisedbevel, loweredbevel);

        JButton playersButton ;
        playersButton = new AbstractButton();
        playersButton.setFont(new Font("Tahoma", 3, 24));
        playersButton.setForeground(new Color(255, 255, 255));
        playersButton.setName(client.playersInfo.get(i).name);
        playersButton.setIcon(GodView.getCard(client.playersInfo.get(i).card).getIcon());
        // playersButton.setBackground(trasparentColor);
        playersButton.setBorderPainted(true);
        playersButton.setBorder(compound);
        System.out.println(client.getCard().toString());
        playersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                infoPlayerAction(evt);
            }
        });


        playersButton.setToolTipText("Your Player");
        playersButton.setHorizontalTextPosition(SwingConstants.CENTER);
        //  playersButton.setOpaque(false);

        return playersButton;
    }


    private void quitButtonActionPerformed(ActionEvent evt) {
        Object[] option = {"Quit", "Cancel"};
        int n = JOptionPane.showOptionDialog(this, "Are you sure you want to quit the game ", "Quit ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
        ;
        if (n == JOptionPane.YES_OPTION) {
            this.dispose();
        }

    }


    public void showAction(BoardMessage boardMessage) {
        enabledBoard(true);
        switch (boardMessage.action) {
            case PLACE_WORKERS:
                messageLabel_NorthPanel.setText("PLACE WORKER");
                break;
            case SELECT_WORKER:
                messageLabel_NorthPanel.setText("SELECT WORKER");
                break;
            case MOVE:
                messageLabel_NorthPanel.setText("MOVE ");

                break;
            case BUILD:
                messageLabel_NorthPanel.setText("BUILD ");

                break;
            case NONE:
                messageLabel_NorthPanel.setText("NONE WORKER");
                break;
        }

    }

    public void showIsYourTurn(boolean isYourTurn) {
        if (!isYourTurn) messageLabel_NorthPanel.setText("Wait player");
        else messageLabel_NorthPanel.setText("Your turn");
    }


    public void showValidTile(final BoardMessage boardMessage) {
        boolean noValidMoves = true;
        Color color = Color.red;
        switch (boardMessage.action) {
            case MOVE:
                color = Color.BLUE;
                break;
            case BUILD:
                color = Color.ORANGE;
        }
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (boardMessage.validTiles[row][col]) {
                    noValidMoves=false;
                    buttonGrid[row][col].setColorTiles(color);
                }
            }
        }

        if(noValidMoves){
            boardGamePanel.setBorder(BorderFactory.createLineBorder(Color.red,15));
            messageLabel_NorthPanel.setText("INVALID ACTION! Select another worker.");
            enabledBoard(false);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("error");
            }
            enabledBoard(true);
            boardGamePanel.setBorder(null);
        }
    }

    public void updateBoard(final BoardMessage boardMessage) {


        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {

                buttonGrid[row][col].updateTile(boardMessage.board[row][col]);



                if (client.getPlayerNumber() == boardMessage.activePlayerNumber){

                    if(boardMessage.validTiles != null) {

                        buttonGrid[row][col].updateValidTiles(boardMessage.validTiles[row][col],boardMessage.action);
                        System.out.println("mio turno");
                    }
                }else{
                    clearUndo();
                }
            }
        }


       askPowerActive(false);
        this.revalidate();
        this.repaint();
    }






    private KGradientPanel DXPanel;
    private KGradientPanel SXPanel;
    private JPanel boardGamePanel;
    private JPanel buttonPanel;
    private JLabel messageLabel_NorthPanel;   //ha le informazioni
    private KGradientPanel kGradientPanel1;
    private JPanel lowerDXPanel;
    private KGradientPanel northPanel_JPanel;
    private JToggleButton powerActivated;
    private JButton quitButton;
    private KGradientPanel southPanel;
    private JPanel tilePanel;
    private AbstractButton undo;
    private JPanel upperDXPanel;
    private JPanel workerPanel;
    private ButtonGroup buttonGroup;
    private JButtonTile[][] buttonGrid;


    class AbstractButton extends JButton {

        public void setColorTiles(Color color) {
            this.setIcon(new TranslucentButtonIcon(this, color));
            this.repaint();
        }


        @Override
        public void updateUI() {
            super.updateUI();
            setVerticalAlignment(SwingConstants.CENTER);
            setVerticalTextPosition(SwingConstants.CENTER);
            setHorizontalAlignment(SwingConstants.CENTER);
            setHorizontalTextPosition(SwingConstants.CENTER);
            setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
            setMargin(new Insets(2, 8, 2, 8));
            setBorderPainted(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setOpaque(false);
            setForeground(Color.WHITE);
            setFont(new Font("Tahoma", 1, 50));
            setIcon(new TranslucentButtonIcon(this, trasparentColor));
        }

        class TranslucentButtonIcon implements Icon {
            private final Color BR = new Color(0f, 0f, 0f, .4f);
            private final Color ST = new Color(1f, 1f, 1f, .2f);
            private Color SB ;
            private static final int R = 8;
            private int width;
            private int height;
            //   private Image image;

            protected TranslucentButtonIcon(JComponent c, Color color) {
                this.SB = color;
                Insets i = c.getBorder().getBorderInsets(c);
                Dimension d = c.getPreferredSize();
                width = d.width - i.left - i.right;
                height = d.height - i.top - i.bottom;
                // image = null;
            }


            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                if (c instanceof AbstractButton) {
                    AbstractButton b = (AbstractButton) c;

                    Insets i = b.getBorder().getBorderInsets(b);
                    int w = c.getWidth();
                    int h = c.getHeight();
                    width = w - i.left - i.right;
                    height = h - i.top - i.bottom;
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    Shape area = new RoundRectangle2D.Double(x - i.left, y - i.top, w - 1, h - 1, R, R);
                    Color ssc;
                    Color bgc;
                    ButtonModel m = b.getModel();
                    if(m.isRollover()) {
                        ssc = ST;
                        bgc = SB;
                    } else{
                        ssc = SB;
                        bgc = ST;
                        // ssc =  new Color(1f, 1f, 1f, .1f);
                        // bgc =  new Color(1f, 1f, 1f, .1f);
                    }

                    g2.setPaint(new GradientPaint(0, 0, ssc, 0, h, bgc, true));
                    // if(image != null ) g2.drawImage(image,0,0,null);
                    g2.fill(area);
                    g2.setPaint(BR);
                    g2.draw(area);
                    g2.dispose();
                }
            }

            @Override
            public int getIconWidth() {
                return Math.max(width, 100);
            }

            @Override
            public int getIconHeight() {
                return Math.max(height, 20);
            }
        }

    }
    private void infoPlayerAction( ActionEvent evt){

        for(int i = 0; i < playersButton.length;i++) {
            if (evt.getSource() == playersButton[i]) {
                JLabel playerDescription = new JLabel();
                playerDescription.setIcon(GodView.getCard(client.playersInfo.get(i).card).getIconDescription());
                if (popUp == null) {
                    popUp = new JDialog(this);

                    popUp.add(playerDescription);
                    popUp.setResizable(false);
                    popUp.setVisible(true);
                    popUp.setSize(507, 278);
                    popUp.setLocationRelativeTo(null);

                    popUp.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

                    popUp.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            popUp.dispose();
                            popUp = null;

                        }
                    });
                }
                else {
                    popUp.setContentPane(playerDescription);
                    popUp.revalidate();
                    popUp.repaint();
                }
            }
        }



    }



    private class ButtonListeners implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

            JButtonTile buttonClicked = ((JButtonTile) e.getSource());


            //System.out.println("Selected row and column: " + row + " " + col);

            if ((messageLabel_NorthPanel.getText() == "MOVE " || messageLabel_NorthPanel.getText().equals("BUILD ")) && buttonClicked.isValidTile()) {
                undo();
            }
            client.getNetworkHandler().sendMessage(new SelectedTileMessage(buttonClicked.getPosition()));


        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }


    }

    private void enabledBoard(boolean isEnabled) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                buttonGrid[row][col].setEnabled(isEnabled);
            }
        }


    }
    public void askPowerActive(boolean on) {
        powerActivated.setVisible(on);
        powerActivated.setEnabled(on);
        if (on) {
            powerActivated.setBackground(Color.green);
        } else {
            powerActivated.setSelected(false);
            powerActivated.setBackground(null);
        }
    }

    public void switchColor(JToggleButton button, Color c1, Color c2) {
        if (button.getBackground() == c1) {
            // button.setColorTiles(c2);
            button.setBackground(c2);
        } else {
            // button.setColorTiles(c1);
            button.setBackground(c1);
        }
        button.repaint();
    }


    public void clearUndo(){
        if(timerThread!=null){
            timerThread.cancel();
            timerThread.purge();
            undo.setEnabled(false);
            undo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("STUFF/undo.png")));
            undo.setText("");
        }
    }

    public void undo() {

        clearUndo();
        undo.setEnabled(true);
        undo.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("STUFF/clessidra.png")));
        timerThread = new Timer();
        timerThread.schedule(new TimerTask() {
            int second = 5;

            @Override
            public void run() {
                undo.setText("" + second);
                System.out.println(second);
                second--;
                if (second == 0) {

                    clearUndo();
                }
            }

        }, 0, 1000);

    }


}






