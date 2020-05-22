package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.controller.client.Client;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.*;
import it.polimi.ing.sw.psp017.view.GraphicUserInterface.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class GUI implements View {
    final public Dimension dim;

    private JFrame mainFrame;
    private Client client;
    private JLabel backgroundScreen;
    private JPanel mainPanel;

    private BoardGUI board;
    private boolean isFirstBoardStep = true;
    private  boolean hasAskedPowerActive;

    public GUI(Client client) {
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.client = client;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createFrame();
            }
        });

    }


    private void createFrame() {
        mainFrame = new JFrame();
        mainFrame.setSize(dim.width/2, dim.height/2);
        mainFrame.setResizable(false);
        mainFrame.setUndecorated(true);
        mainFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        final Image im = new ImageIcon(getClass().getClassLoader().getResource("logo.png")).getImage().getScaledInstance(mainFrame.getWidth(),mainFrame.getHeight(),Image.SCALE_SMOOTH);
        mainFrame.setIconImage(im);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocationRelativeTo(null);
        mainPanel = new JPanel(new BorderLayout());

        ImageIcon i = new ImageIcon(im);
        backgroundScreen = new JLabel(i, JLabel.CENTER);
        final JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setVisible(true);
        mainPanel.add(backgroundScreen, BorderLayout.CENTER);
        mainPanel.add(progressBar, BorderLayout.SOUTH);
        mainFrame.setContentPane(mainPanel);
        mainFrame.setVisible(true);
        mainFrame.repaint();
        mainFrame.setLocationRelativeTo(null);

        new SwingWorker<Integer, String>() {
            @Override
            protected Integer doInBackground() throws Exception {
                setProgress(1);
                progressBar.setIndeterminate(false);

                progressBar.setValue(this.getProgress());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                final JFrame tempFrame = new JFrame("Santorini");
                tempFrame.setIconImage(im);
                tempFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                tempFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        Object[] option = {"Quit", "Cancel"};
                        int n = JOptionPane.showOptionDialog(tempFrame, "Are you sure you want to quit the game ", "Quit ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);;
                        if (n == JOptionPane.YES_OPTION) {
                            tempFrame.dispose();
                        }

                    }
                });
                setProgress(55);
                progressBar.setValue(this.getProgress());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }

                //mainFrame.setSize(dim);

                setProgress(99);
                progressBar.setValue(this.getProgress());
                try {
                    client.getNetworkHandler().startConnection();

                } catch (IOException e) {
                    JOptionPane.showMessageDialog(mainFrame, "Error: No connection. The program will be closed.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                mainFrame.dispose();
                mainFrame= tempFrame;
                mainFrame.setMinimumSize(new Dimension(500,700));
                mainFrame.pack();
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setContentPane(mainPanel);
                mainFrame.setVisible(true);

                return 1;
            }
        }.execute();



    }


    @Override
    public void notifyNickname(AuthenticationMessage authenticationMessage) {
        client.getNetworkHandler().sendMessage(authenticationMessage);
    }

    @Override
    public void notifyGameSetUp(GameSetUpMessage gameSetUpMessage) {
        client.getNetworkHandler().sendMessage(gameSetUpMessage);
    }

    @Override
    public void notifyCard(CardMessage cardMessage) {
        client.getNetworkHandler().sendMessage(cardMessage);
    }

    @Override
    public void notifySelectedTile(SelectedTileMessage selectedTileMessage) {

    }

    @Override
    public void notifyIsPowerActive(PowerActiveMessage powerActiveMessage) {
        client.getNetworkHandler().sendMessage(powerActiveMessage);
    }



    @Override
    public void notifyDisconnection(DisconnectionMessage disconnectionMessage) {
        client.getNetworkHandler().sendMessage(disconnectionMessage);
    }

    @Override
    public void notifyUndo(UndoMessage undoMessage) {

    }

    @Override
    public void updateGameCreation() {
        SwingUtilities.invokeLater(new Thread(new Runnable() {
            @Override
            public void run() {


                mainFrame.dispose();
                mainPanel.setVisible(false);
                mainFrame = new FirstPlayer(client);
                mainFrame.setSize(dim.width/2,dim.height/2);
                mainFrame.setMinimumSize(new Dimension(1400,850));
                mainFrame.pack();
                mainFrame.repaint();
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);


                mainFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        Object[] option = {"Quit", "Cancel"};
                        int n = JOptionPane.showOptionDialog(mainFrame, "Are you sure you want to quit the game ", "Quit ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);;
                        if (n == JOptionPane.YES_OPTION) {
                            mainFrame.dispose();
                        }

                    }
                });

            }
        }));

    }

    @Override
    public void updateLoginScreen(InvalidNameMessage invalidNameMessage) {
        if (invalidNameMessage != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(mainFrame, "Invalid nickname. Please insert again", "invalid nickname", JOptionPane.ERROR_MESSAGE);
                    mainPanel.setVisible(false);
                    mainPanel = new LoginPanel(client);
                    mainFrame.setContentPane(mainPanel);
                    mainFrame.pack();
                    mainFrame.setLocationRelativeTo(null);

                }
            });
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    mainPanel = new LoginPanel(client);
                    mainFrame.setContentPane(mainPanel);
                    mainFrame.setLocationRelativeTo(null);
                }
            });

        }

    }

    @Override
    public void updateLobby(final LobbyMessage lobbyMessage) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {



                mainPanel.setVisible(false);
                mainPanel = new lobbyMessagePanel(lobbyMessage,client, mainFrame);
                mainFrame.setContentPane(mainPanel);
                mainFrame.pack();
                mainPanel.setVisible(true);


            }
        });
    }

    @Override
    public void updateWaitingRoom(final WaitMessage waitMessage) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {


                mainPanel.setVisible(false);
                mainPanel = new waitingRoom(waitMessage.queueLength);
                mainFrame.setContentPane(mainPanel);
                mainFrame.setSize(dim.width/2,dim.width/2);
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);



            }
        });
    }

    @Override
    public void updateBoard(final BoardMessage boardMessage) {

        if(isFirstBoardStep)
        {
            SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainFrame.dispose();
                board = new BoardGUI(client);
                board.updateBoard(boardMessage);
                isFirstBoardStep = false;
            }
        });

        }


            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    board.updateBoard(boardMessage);
                    if (boardMessage.activePlayerNumber == client.getPlayerNumber()) {
                        board.showAction(boardMessage);
                        if (boardMessage.hasChoice) //possibilita di attivare il potere
                        {
                            board.askPowerActive();
                            // hasAskedPowerActive = true;
                        }
                    }
                    else {
                        board.showIsYourTurn(false);
                    }
                }
            });



    }

    @Override
    public void updateVictory(VictoryMessage victoryMessage) {

    }

    @Override
    public void updateDisconnection(ServerDisconnectionMessage disconnectionMessage) {
        Object[] option = {"Quit", "Restart"};
        int n = JOptionPane.showOptionDialog(mainFrame, "One player is disconetted. Do you want start a new Game? ", "Quit ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);;
        if (n == JOptionPane.YES_OPTION) {
            mainFrame.dispose();
            System.exit(0);
        }
        else{
            notifyNickname(new AuthenticationMessage(client.getNickname()));
        }
    }



    class waitingRoom extends JPanel {
        private JLabel infoLabel;
        private JLabel queueLabel;
        private JLabel boatBigLabel;
        private JLabel boatSmallLabel;
        private JPanel northPanel;
        private JPanel westPanel;
        private JPanel centerPanel;
        private JPanel eastPanel;
        private KGradientPanel kGradientPanel1;

        waitingRoom(int numPlayers) {

            kGradientPanel1 = new KGradientPanel();
            northPanel = new JPanel();
            westPanel = new JPanel();
            centerPanel = new JPanel();
            infoLabel = new JLabel();
            queueLabel = new JLabel();
            boatBigLabel = new JLabel();
            boatSmallLabel = new JLabel();
            eastPanel = new JPanel();


            kGradientPanel1.setForeground(new Color(102, 153, 0));
            kGradientPanel1.setkEndColor(new Color(204, 204, 255));
            kGradientPanel1.setkStartColor(new Color(204, 255, 255));
            kGradientPanel1.setLayout(new BorderLayout(20, 40));
            kGradientPanel1.setSize(1000,800);

            infoLabel.setFont(new Font("Tahoma", 3, 48));
            infoLabel.setForeground(new Color(0, 153, 102));
            infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            infoLabel.setText("Please wait a new game");

            queueLabel.setFont(new Font("Tahoma", 2, 36));
            queueLabel.setForeground(new Color(0, 102, 102));
            queueLabel.setHorizontalAlignment(SwingConstants.CENTER);
            queueLabel.setText(numPlayers + " Players before you");

            boatBigLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            boatBigLabel.setIcon(new ImageIcon("C:\\Users\\user\\IdeaProjects\\ing-sw-2019-Marchesi-Muccio-Leci\\src\\main\\java\\it\\polimi\\ing\\sw\\psp017\\view\\res\\title_boat_right.png"));

            boatSmallLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            boatSmallLabel.setIcon(new ImageIcon("C:\\Users\\user\\IdeaProjects\\ing-sw-2019-Marchesi-Muccio-Leci\\src\\main\\java\\it\\polimi\\ing\\sw\\psp017\\view\\res\\title_boat_left.png"));

            GroupLayout centerPanelLayout = new GroupLayout(centerPanel);
            centerPanel.setLayout(centerPanelLayout);
            centerPanelLayout.setHorizontalGroup(
                    centerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(infoLabel, GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
                            .addGroup(GroupLayout.Alignment.CENTER, centerPanelLayout.createSequentialGroup()
                                    .addComponent(queueLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(GroupLayout.Alignment.TRAILING, centerPanelLayout.createSequentialGroup()
                                    .addComponent(boatSmallLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(113, 113, 113))
                            .addGroup(GroupLayout.Alignment.TRAILING, centerPanelLayout.createSequentialGroup()
                                    .addComponent(boatBigLabel, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE)
                                    .addGap(219, 219, 219))
            );
            centerPanelLayout.setVerticalGroup(
                    centerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(centerPanelLayout.createSequentialGroup()
                                    .addGap(0,20,40)
                                    .addComponent(infoLabel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(queueLabel, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(boatBigLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(boatSmallLabel, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
                            )
            );

            eastPanel.setOpaque(false);
            northPanel.setOpaque(false);
            westPanel.setOpaque(false);
            centerPanel.setOpaque(false);

            kGradientPanel1.add(northPanel, BorderLayout.NORTH);
            kGradientPanel1.add(westPanel, BorderLayout.WEST);
            kGradientPanel1.add(eastPanel, BorderLayout.EAST);
            kGradientPanel1.add(centerPanel, BorderLayout.CENTER);

            this.setLayout(new BorderLayout());
            this.add(kGradientPanel1, BorderLayout.CENTER);

        }
    }






}
