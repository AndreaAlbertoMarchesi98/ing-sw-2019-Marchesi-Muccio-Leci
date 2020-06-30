package it.polimi.ing.sw.psp017.client.view;
import it.polimi.ing.sw.psp017.client.Client;
import it.polimi.ing.sw.psp017.client.PlayersInfo;
import it.polimi.ing.sw.psp017.server.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.server.messages.ServerToClient.*;
import it.polimi.ing.sw.psp017.client.view.GraphicUserInterface.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;


public class GUI implements View {
    private JFrame mainFrame;
    private Client client;
    final public Dimension dim;
    private JLabel backgroundScreen;
    private JPanel mainPanel;
    private  Image logoIm;

    private BoardGUI board;

    private boolean isFirstBoardStep = true;



    public GUI(Client client) {
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.client = client;
        client.playersInfo = new ArrayList<>();
        client.getNetworkHandler().setView(this);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createFrame();
            }
        });

    }

    /**
     * createFrame() creates show the first frame with logo-image in background and a progress bar.
     * During the load, this method prepares a new frame used by the loginPanel and try to establish a connection calling the ConnectionPanel
     */
    private void createFrame() {
        mainFrame = new JFrame();
        mainFrame.setSize(dim.width/2, dim.height/2);
        mainFrame.setResizable(false);
        mainFrame.setUndecorated(true);
        mainFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        this.logoIm = new ImageIcon(getClass().getClassLoader().getResource("logo.png")).getImage().getScaledInstance(mainFrame.getWidth(),mainFrame.getHeight(),Image.SCALE_SMOOTH);
        mainFrame.setIconImage(logoIm);
        mainFrame.setLocationRelativeTo(null);
        mainPanel = new JPanel(new BorderLayout());

        ImageIcon i = new ImageIcon(logoIm);
        backgroundScreen = new JLabel(i, JLabel.CENTER);
        final JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setVisible(true);
        mainPanel.add(backgroundScreen, BorderLayout.CENTER);
        mainPanel.add(progressBar, BorderLayout.SOUTH);
        mainFrame.setContentPane(mainPanel);
        mainFrame.setVisible(true);


        new SwingWorker<Integer, String>() {
            @Override
            protected Integer doInBackground()  {
                setProgress(1);
                progressBar.setIndeterminate(false);

                progressBar.setValue(this.getProgress());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                final JFrame tempFrame = new JFrame("Santorini");
                setDefaultOptionFrame(tempFrame);
                setProgress(55);
                progressBar.setValue(this.getProgress());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }

                setProgress(99);
                progressBar.setValue(this.getProgress());

                mainFrame.dispose();
                mainFrame = new ConnectionPanel(client);
                setDefaultOptionFrame(mainFrame);
                while(!client.getNetworkHandler().isConnected()){

                }
                mainFrame.dispose();
                mainFrame= tempFrame;
                mainFrame.setMinimumSize(new Dimension(500,700));
                mainFrame.setContentPane(mainPanel);
                mainFrame.pack();
                mainFrame.setLocationRelativeTo(null);
                updateLoginScreen(null);
                mainFrame.setVisible(true);
                return 1;
            }
        }.execute();


    }

    /**
     * @param frame all options are added to this frame
     */
    public void setDefaultOptionFrame(final JFrame frame){
        frame.setTitle("Santorini");
        frame.setIconImage(logoIm);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quitPanel(frame);
            }
        });
    }

    /**
     * Show a JOptionPane asking the user if he really wants to quit.
     * @param frame on which is showed the panel
     */
    public void quitPanel(JFrame frame){
        Object[] option = {"Quit", "Cancel"};
        int n = JOptionPane.showOptionDialog(frame, "Are you sure you want to quit the game? ", "Quit ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);;
        if (n == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * each method sends the respective message through the sendMessage() of the NetworkHandeler
     * As parameter the message to send in the correct type.
     */
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
        client.getNetworkHandler().sendMessage(selectedTileMessage);
    }

    @Override
    public void notifyIsPowerActive(PowerActiveMessage powerActiveMessage) {
        client.getNetworkHandler().sendMessage(powerActiveMessage);
    }

    @Override
    public void notifyRestart(RestartMessage restartMessage) {
            client.getNetworkHandler().sendMessage( restartMessage);
    }


    @Override
    public void notifyUndo(UndoMessage undoMessage) {
        client.getNetworkHandler().sendMessage(undoMessage);
    }

    /**
     * Update the mainFrame with the FirstPlayerFrame.
     */
    @Override
    public void updateGameCreation() {
        SwingUtilities.invokeLater(new Thread(new Runnable() {
            @Override
            public void run() {
                mainFrame.dispose();
                mainPanel.setVisible(false);
                mainFrame = new FirstPlayerFrame(client);
                setDefaultOptionFrame(mainFrame);
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);

                mainFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        quitPanel(mainFrame);
                    }
                });

            }
        }));

    }

    /**
     * Update the mainFrame with the LoginPanel.
     * If invalidNameMessage is equals 'NULL' then the user have to insert the nickname for the first time;
     * else the nickname sent to the server is not valid, so a JOptionPane is showed to the user to notify him, then
     * he can reinsert a new nickname.
     */
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
                    mainFrame.setVisible(true);
                    mainFrame.pack();
                    mainFrame.setLocationRelativeTo(null);
                }
            });

        }

    }

    /**
     *  Update the mainFrame with the LobbyMessagePanel.
     *  Add information to the 'playersInfo' structure.
     * @param lobbyMessage
     */
    @Override
    public void updateLobby(final LobbyMessage lobbyMessage) {
        board = null;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                mainPanel.setVisible(false);
                mainPanel = new LobbyMessagePanel(lobbyMessage,client);
                mainFrame.setContentPane(mainPanel);
                mainFrame.setMinimumSize(new java.awt.Dimension(1200, 800));
                mainPanel.setVisible(true);
                mainFrame.setLocationRelativeTo(null);

                if (lobbyMessage.availableCards.size() == 1) {
                    isFirstBoardStep = true;
                    lobbyMessage.chosenCards.add(lobbyMessage.availableCards.get(0));

                    client.playersInfo = new ArrayList<>();

                    for(int i = 0; i < lobbyMessage.players.size();i++)
                    {
                        PlayersInfo playersInfos = new PlayersInfo(lobbyMessage.players.get(i), i+1, lobbyMessage.chosenCards.get(lobbyMessage.chosenCards.size()-1-i));
                        client.playersInfo.add(playersInfos);
                    }
                }

            }
        });
    }

    /**
     * Set not visible the mainFrame
     * And create a new Frame BoardGui assigning it to the variable 'board' only if this variable is null.
     * When the board is create, this method update the the frame passing the boardmessage to BoardGui.
     *
     * @param boardMessage
     */
    @Override
    public void updateBoard(final BoardMessage boardMessage) {
        if (isFirstBoardStep) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    mainFrame.setVisible(false);
                    board = new BoardGUI(client);
                    board.updateBoard(boardMessage);
                    isFirstBoardStep = false;
                    JLabel tutorial = new JLabel();
                   tutorial.setIcon(new ImageIcon(GUI.class.getClassLoader().getResource("tutorial.png")));
                    JDialog tutorialPopUp = new JDialog(board);
                    tutorialPopUp.add(tutorial);
                    tutorialPopUp.setTitle("Tutorial");
                    tutorialPopUp.setResizable(false);
                    tutorialPopUp.setMinimumSize(new Dimension(1200,800));
                    tutorialPopUp.setMaximumSize(new Dimension(1200,800));
                    tutorialPopUp.setVisible(true);
                    tutorialPopUp.pack();
                    tutorialPopUp.setLocationRelativeTo(null);
                    tutorialPopUp.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                }
            });
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                board.updateBoard(boardMessage);
                if (boardMessage.activePlayerNumber == client.getPlayerNumber()) {
                    board.showAction(boardMessage);
                } else {
                    board.showIsYourTurn(false);
                }
            }
        });

    }

    /**
     * Notify to the user if a player has no move left.
     * Ask to the user defeted if he wants to quit the game
     * @param noMovesMessage
     */
    @Override
    public void updateDefeat(final NoMovesMessage noMovesMessage) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(board, "No moves left for "+client.playersInfo.get(noMovesMessage.playerNumber-1).card, "Defeat", JOptionPane.WARNING_MESSAGE);
            }});
        if( noMovesMessage.playerNumber==client.getPlayerNumber())
        {
            showRestartOption("No move possible. Do you want to start a new game?",board);
        }
    }

    /**
     * Ask to the player if he wants to restart or quit.
     * @param message is the text to show
     */
    public void showRestartOption(String message, JFrame frame){

        Object[] option = {"Quit", "Restart"};
        int n = JOptionPane.showOptionDialog(frame, message, " ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);;
        if (n == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
        else{
            notifyRestart(new RestartMessage());
            if(board != null ) board.dispose();
            mainFrame.setVisible(true);
            client.setPlayerNumber(0);
            board=null; //serve?
        }
    }

    /**
     * Show at all players who is the winner and ask them if they want quit or restart
     * @param victoryMessage
     */
    @Override
    public void updateVictory( VictoryMessage victoryMessage) {
        System.out.println("Victory");
        String message;
        if(victoryMessage.winnerNumber == client.getPlayerNumber()){
            message = "Congratulations! You are the Winner!";
        }
        else{
            message = "Game over! The winner is " + client.playersInfo.get(victoryMessage.winnerNumber-1).card;
        }


        JOptionPane.showMessageDialog(board, message, "GAME OVER", JOptionPane.WARNING_MESSAGE);

       showRestartOption("Do you want to start a new game?", board);

    }

    /**
     * Ask restart if a player is Disconnected and the game cannot continue.
     * @param disconnectionMessage
     */
    @Override
    public void updateDisconnection(ServerDisconnectionMessage disconnectionMessage) {
        JFrame tempFrame;
        if(board!=null) tempFrame = board;
        else tempFrame = mainFrame;
        showRestartOption("One player is disconnected. Do you want start a new Game? ", tempFrame);
    }

    /**
     * Show a message dialog if Server is not more reachable
     */
    public void showServerNotFound(){
        final JFrame tempFrame;
        if(board!=null) tempFrame = board;
        else tempFrame = mainFrame;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(tempFrame, " We are sorry but something seems to have gone wrong. Try again later and show everyone your divine power!", "SERVER ERROR : SERVER NOT FOUND", JOptionPane.WARNING_MESSAGE);
                tempFrame.dispose();
                System.exit(0);
            }});

    }

}
