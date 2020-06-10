package it.polimi.ing.sw.psp017.client.view.GraphicUserInterface;
import it.polimi.ing.sw.psp017.client.Client;
import it.polimi.ing.sw.psp017.server.messages.ClientToServer.PowerActiveMessage;
import it.polimi.ing.sw.psp017.server.messages.ClientToServer.SelectedTileMessage;
import it.polimi.ing.sw.psp017.server.messages.ClientToServer.UndoMessage;
import it.polimi.ing.sw.psp017.server.messages.ServerToClient.BoardMessage;
import it.polimi.ing.sw.psp017.server.model.Vector2d;
import it.polimi.ing.sw.psp017.client.view.ActionNames;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class BoardGUI extends JFrame {
    private final Dimension dim;
    public JButton[] playersButton;
    private JDialog popUp;
    private Client client;
    private Timer timerThread;

    public BoardGUI(Client client) {
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.client = client;
        initComponents();

    }


    private void initComponents() {

        buttonGroup = new ButtonGroup();
        this.setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource("logo.png")).getImage());
        this.setTitle("Santorini");
        kGradientPanel1 = new KGradientPanel();


        kGradientPanel1.setkEndColor(new Color(151, 241, 255, 164));
        kGradientPanel1.setkStartColor(new Color(48, 36, 245));


        southPanel = new KGradientPanel();
        southPanel.setkEndColor(new Color(124, 253, 253, 223));
        southPanel.setkStartColor(new Color(83, 187, 214));
        southPanel.backgroundGradient(50);
   //     southPanel.setBorder(new LineBorder(new java.awt.Color(0, 63, 143, 193), 5, true));

        northPanel_JPanel = new KGradientPanel();
        northPanel_JPanel.setkStartColor(new Color(124, 253, 253, 164));
        northPanel_JPanel.setkEndColor(new Color(83, 187, 214));
        northPanel_JPanel.backgroundGradient(50);
    //    northPanel_JPanel.setBorder(new LineBorder(new Color(0, 63, 143, 193), 5, true));



        DXPanel = new KGradientPanel();
        DXPanel.setkStartColor(new Color(124, 253, 253, 164));
        DXPanel.setkEndColor(new Color(83, 187, 214, 184));
        DXPanel.backgroundGradient(50);
    //    DXPanel.setBorder(new LineBorder(new Color(200, 0, 0, 255), 3, true));
        //DXPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 102, 102), new Color(0, 153, 153)));


        SXPanel = new KGradientPanel();
        SXPanel.setkEndColor(new Color(83, 187, 214, 164));
        SXPanel.setkStartColor(new Color(83, 187, 214, 193));
        SXPanel.backgroundGradient(50);
     //   SXPanel.setBorder(new LineBorder(new Color(200, 2, 2, 255), 3, true));
        //SXPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(0, 153, 255), new Color(0, 255, 255), new Color(0, 102, 102), new Color(0, 153, 153)));






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
        //undo = new AbstractButton();
        undo = new JButton();
        powerActivated = new JToggleButton();
        lowerDXPanel = new JPanel();

        //quitButton = new AbstractButton();
        quitButton = new JButton();

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

        undo.setToolTipText("Undo");
        undo.setFont(new Font("Tahoma", 3, 36));
        undo.setForeground(Color.white);
        undo.setContentAreaFilled(false);
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == undo)
                    clearUndo();
                    client.getView().notifyUndo(new UndoMessage());
            }
        });
        upperDXPanel.add(undo);
        powerActivated.setText("");
        powerActivated.setIcon(GodView.getCard(client.getCard()).getIconPower());

        powerActivated.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchPowerButtonColor();
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


          infoPower = new JLabel();
         infoPower.setFont(new Font("Tahoma", 3, 25));
        upperDXPanel.add(infoPower);
        DXPanel.add(upperDXPanel);

        lowerDXPanel.setOpaque(false);




        playersButton = new JButton[client.playersInfo.size()];
        for(int i = 0; i < client.playersInfo.size();i++)
        {

            playersButton[i] = createJButton(i);


            playersButton[i].setHorizontalTextPosition(SwingConstants.CENTER);
            playersButton[i].setOpaque(false);
            setButtonMessage(playersButton[i]);
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
            switch(i){
                case 0: playersButton[i].setBackground(Color.RED.darker());
                    break;
                case 1: playersButton[i].setBackground(Color.BLUE.darker());
                    break;
                case 2 : playersButton[i].setBackground(Color.GREEN.darker());
                    break;
                default: break;
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
        quitButton.setBackground(Color.RED.darker());


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
        setLocationRelativeTo(null);
        setSize(dim.width / 2, dim.width / 2);
        this.revalidate();
        this.repaint();
        setVisible(true);


        //boardGamePanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(102, 102, 9), new Color(181, 55, 55), new Color(57, 162, 3), new Color(27, 87, 117)));
        boardGamePanel.setBorder(new LineBorder(new java.awt.Color(15, 255, 0, 164), 35, false));
        layer.setBorder(new LineBorder(new java.awt.Color(83, 255, 214, 52), 30, false));
        layer.setBorder(new LineBorder(new java.awt.Color(83, 255, 214, 52), 30, false));
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

        JButtonTile playersButton = new JButtonTile();
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
            System.exit(0);
        }



    }

    public void showAction(BoardMessage boardMessage) {

        actionNames = boardMessage.action;


        enabledBoard(true);
        switch (boardMessage.action) {
            case PLACE_WORKERS:
                messageLabel_NorthPanel.setText("  PLACE WORKER  ");
                break;
            case SELECT_WORKER:
                messageLabel_NorthPanel.setText("  SELECT WORKER  ");
                break;
            case MOVE:
                messageLabel_NorthPanel.setText("  MOVE ");

                break;
            case BUILD:
                messageLabel_NorthPanel.setText("  BUILD ");

                break;
            case NONE:
                messageLabel_NorthPanel.setText("  NONE WORKER  ");
                break;
        }

    }

    public void showIsYourTurn(boolean isYourTurn) {
        if (!isYourTurn) messageLabel_NorthPanel.setText("  Wait player  ");
        else messageLabel_NorthPanel.setText("  Your turn  ");
    }



    public void updateBoard(final BoardMessage boardMessage) {


        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {

                buttonGrid[row][col].updateTile(boardMessage.board[row][col]);



                if (client.getPlayerNumber() == boardMessage.activePlayerNumber){

                    if(boardMessage.validTiles != null) {

                        buttonGrid[row][col].updateValidTiles(boardMessage.validTiles[row][col],boardMessage.action);
                    }

                    showPowerActiveButton(boardMessage.hasChoice);

                }else{
                    clearUndo();
                    showPowerActiveButton(false);
                }
            }
        }

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
    private JButton undo;
    private JPanel upperDXPanel;
    private JPanel workerPanel;
    private ButtonGroup buttonGroup;
    private JButtonTile[][] buttonGrid;
    private ActionNames actionNames;
    private  JLabel infoPower;



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

            if (actionNames!= null && (actionNames.equals(ActionNames.MOVE)||actionNames.equals(ActionNames.BUILD)) && buttonClicked.isValidTile()) {
                undo();

            }

            client.getView().notifySelectedTile(new SelectedTileMessage(buttonClicked.getPosition()));

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

    private void setButtonMessage(JButton b){
        b.setToolTipText("Click for God's description");
    }

    private void enabledBoard(boolean isEnabled) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                buttonGrid[row][col].setEnabled(isEnabled);
            }
        }


    }
    public void showPowerActiveButton(boolean visible) {
        powerActivated.setVisible(visible);
        if(!visible){
            powerActivated.setEnabled(false);
            powerActivated.setContentAreaFilled(false);
            infoPower.setText("");
        }
    }

    public void askPowerActive(){
        powerActivated.setEnabled(true);
        powerActivated.setContentAreaFilled(true);
        powerActivated.setBackground(Color.GREEN);
        infoPower.setText("");
    }


    public void switchPowerButtonColor() {
        if (powerActivated.getBackground() == Color.GREEN) {
            powerActivated.setBackground(Color.RED);
            infoPower.setText("Power ON  ");
        } else {
            powerActivated.setBackground(Color.green);
            infoPower.setText("");
        }
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
                undo.setText("" + second + "  ");

                second--;
                if (second == 0) {
                    askPowerActive();
                    clearUndo();
                }
            }

        }, 0, 1000);

    }


}






