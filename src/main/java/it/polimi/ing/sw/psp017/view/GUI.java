package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.controller.client.Client;
import it.polimi.ing.sw.psp017.controller.client.PlayersInfo;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.*;

import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.view.GraphicUserInterface.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class GUI implements View {
    private JFrame mainFrame;
    private Client client;
    private Container container;
    final public Dimension dim;
    private JLabel backgroundScreen;  //contiene l'immagine di sfondo
    private JPanel mainPanel;

    private BoardGUI board;
    private boolean isFirstBoardStep = true;

    private  boolean hasAskedPowerActive;

    public GUI(Client client) {
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.client = client;
        client.playersInfo = new ArrayList<>();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createFrame();
            }
        });

    }

    private Image getImage(String path) {
        ImageIcon i = new ImageIcon(path);
        Image im = i.getImage();
        im = im.getScaledInstance(mainFrame.getWidth(), mainFrame.getHeight(), Image.SCALE_SMOOTH);
        return im;
    }

    private void createFrame() {
        mainFrame = new JFrame();
        mainFrame.setSize(dim.width/2, dim.height/2);
        mainFrame.setResizable(false);
        mainFrame.setUndecorated(true);
        mainFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        final Image im = new ImageIcon(getClass().getClassLoader().getResource("logo.png")).getImage().getScaledInstance(mainFrame.getWidth(),mainFrame.getHeight(),Image.SCALE_SMOOTH);
        //mainFrame.setIconImage(im);
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
                mainFrame= new JFrame();
                mainFrame.setIconImage(im);
                mainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                mainFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        Object[] option = {"Quit", "Cancel"};
                        int n = JOptionPane.showOptionDialog(mainFrame, "!re you sure you want to quit the game ", "Quit ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);;
                        if (n == JOptionPane.YES_OPTION) {
                            mainFrame.dispose();
                        }

                    }
                });
                mainFrame.setMinimumSize(new Dimension(500,700));
                //mainFrame.setPreferredSize(dim);
                mainFrame.pack();
                mainFrame.setLocationRelativeTo(null); //centrato
                mainFrame.setContentPane(mainPanel);
                //mainFrame.setSize(dim);
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
                mainFrame = new FirstPlayerFrame(client);
               // mainFrame.setSize(dim.width/2,dim.height/2);
              //  mainFrame.setMinimumSize(new Dimension(1400,850));
              //  mainFrame.pack();
              //  mainFrame.repaint();
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);


                mainFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        Object[] option = {"Quit", "Cancel"};
                        int n = JOptionPane.showOptionDialog(mainFrame, "!re you sure you want to quit the game ", "Quit ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);;
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
        if (invalidNameMessage != null) { //hai già creato il frame e devi solo aggiornare il testo e ricevere un nuovo nick
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
                    mainFrame.pack();
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
                mainPanel = new LobbyMessagePanel(lobbyMessage,client);
               // mainPanel = new lobbyMessagePanel();
                mainFrame.setContentPane(mainPanel);
                mainFrame.pack();
                mainPanel.setVisible(true);

                if (lobbyMessage.availableCards.size() == 1) {
                    System.out.println("dentro a size == 1");

                    //salvo informazioni players
                    PlayersInfo[] playersInfos = new PlayersInfo[lobbyMessage.players.size()];
                    playersInfos[0] = new PlayersInfo(lobbyMessage.players.get(0), 1, lobbyMessage.availableCards.get(0));
                    client.playersInfo.add(playersInfos[0]);
                    for (int i = 1; i < lobbyMessage.players.size(); i++) {
                        playersInfos[i] = new PlayersInfo(lobbyMessage.players.get(i), i + 1, lobbyMessage.chosenCards.get(i-1));

                        client.playersInfo.add(playersInfos[i]);
                    }




                }

                //si puo eliminare
                if(lobbyMessage.choosingPlayerNumber == 1)
                {
                    for (int i = 0; i < client.playersInfo.size(); i++) {
                        System.out.println("nickname :" + client.playersInfo.get(i).name +
                                "\n card name : " + client.playersInfo.get(i).card +
                                " \n playerNumber  : " + client.playersInfo.get(i).playerNumber);
                    }
                }





            }
        });
    }

    @Override
    public void updateWaitingRoom(final WaitMessage waitMessage) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {


                mainPanel.setVisible(false);
                mainPanel = new WaitingRoom(waitMessage.queueLength);
                mainFrame.setContentPane(mainPanel);
                mainFrame.setSize(dim.width/2,dim.width/2);
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);



            }
        });
    }

    @Override
    public void updateBoard(final BoardMessage boardMessage) {
        if (isFirstBoardStep) {
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
                } else {
                    board.showIsYourTurn(false);
                }
            }
        });

    }

        @Override
    public void updateVictory(VictoryMessage victoryMessage) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(board, "HAI VINTO", "wIN", JOptionPane.ERROR_MESSAGE);
                }});
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
            if(board != null) board.dispose();
        }
    }







}
