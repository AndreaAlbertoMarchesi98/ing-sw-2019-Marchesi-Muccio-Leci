package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.controller.client.Client;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.*;
import it.polimi.ing.sw.psp017.view.GraphicUserInterface.FirstPlayer;
import it.polimi.ing.sw.psp017.view.GraphicUserInterface.lobbyMessagePanel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class GUI implements View {
    private JFrame mainFrame;
    private Client client;
    private Container container;
    final public Dimension dim;
    private JLabel backgroundScreen;  //contiene l'immagine di sfondo
    private JPanel mainPanel;

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

    private Image getImage(String path) {
        ImageIcon i = new ImageIcon(path);
        Image im = i.getImage();
        im = im.getScaledInstance(mainFrame.getWidth(), mainFrame.getHeight(), Image.SCALE_SMOOTH);
        return im;
    }

    private void createFrame() {
        mainFrame = new JFrame();
        mainFrame.setSize(1000, 600);
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

                final JFrame tempFrame = new JFrame();
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
                tempFrame.setMinimumSize(new Dimension(500,700));
                tempFrame.setPreferredSize(dim);
                tempFrame.pack();
                tempFrame.setLocationRelativeTo(null);
                setProgress(99);
                progressBar.setValue(this.getProgress());
                try {
                    mainFrame.dispose();
                    tempFrame.setContentPane(mainPanel);
                    mainFrame=tempFrame;
                    client.getNetworkHandler().startConnection();

                } catch (IOException e) {
                    JOptionPane.showMessageDialog(mainFrame, "Error: Server down. The program will be closed.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
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

    }

    @Override
    public void notifyCard(CardMessage cardMessage) {

    }

    @Override
    public void notifyPlacement(PlacementMessage placementMessage) {

    }

    @Override
    public void notifySelection(SelectionMessage selectionMessage) {

    }

    @Override
    public void notifyIsPowerActive(PowerActiveMessage powerActiveMessage) {

    }

    @Override
    public void notifyAction(ActionMessage actionMessage) {

    }

    @Override
    public void notifyDisconnection(DisconnectionMessage disconnectionMessage) {

    }

    @Override
    public void updateGameCreation() {
        SwingUtilities.invokeLater(new Thread(new Runnable() {
            @Override
            public void run() {
                mainPanel.setVisible(false);
                mainPanel = new FirstPlayer();
                mainPanel.revalidate();
                mainPanel.repaint();
                mainFrame.setContentPane(mainPanel);
                mainFrame.pack();
                mainFrame.repaint();

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
                    mainPanel = new LoginPanel();
                    mainFrame.setContentPane(mainPanel);
                }
            });
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    mainPanel = new LoginPanel();
                    mainFrame.setContentPane(mainPanel);
                    mainFrame.pack();

                }
            });

        }

    }

    @Override
    public void updateLobby(LobbyMessage lobbyMessage) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainPanel.setVisible(false);
                mainPanel = new lobbyMessagePanel();
                mainFrame.setContentPane(mainPanel);

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

            }
        });
    }

    @Override
    public void updateBoard(BoardMessage boardMessage) {

    }

    @Override
    public void updateVictory(VictoryMessage victoryMessage) {

    }

    @Override
    public void updateDisconnection(SDisconnectionMessage disconnectionMessage) {

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


    class LoginPanel extends JPanel {
        private JPanel bottomPanel;
        private JButton cliButton;
        private JLabel infoLabel;
        private JPanel inputPanel;
        private KGradientPanel kGradientPanel;
        private JLabel logoLabel;
        private JButton nickButton;
        private JTextField nicknameField;

        public LoginPanel(){
            initComponents();
        }

        private void initComponents() {
            this.setMinimumSize(new Dimension(800,1000));
            mainPanel.setMinimumSize(this.getSize());
            mainFrame.pack();

            kGradientPanel = new KGradientPanel();
            logoLabel = new JLabel();
            infoLabel = new JLabel();
            inputPanel = new JPanel();
            nicknameField = new JTextField();
            nickButton = new JButton();
            bottomPanel = new JPanel();
            cliButton = new JButton();


            kGradientPanel.setkEndColor(new Color(255, 255, 153));
            kGradientPanel.setkStartColor(new Color(0, 204, 102));
            kGradientPanel.setLayout(new GridLayout(4, 1,100,10));

            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

            logoLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("logonome.png")));
            logoLabel.setVerticalAlignment(SwingConstants.BOTTOM);
            kGradientPanel.add(logoLabel);

            infoLabel.setFont(new Font("Tahoma", 1, 48));
            infoLabel.setForeground(Color.white);
            infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            infoLabel.setText("Insert your nickname");
            kGradientPanel.add(infoLabel);

            inputPanel.setOpaque(false);

            nicknameField.setFont(new Font("Tahoma", 0, 36));
            nicknameField.setForeground(Color.DARK_GRAY);
            nicknameField.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
            nicknameField.setPreferredSize(new Dimension(300, 70));
            nicknameField.setText("");
            nicknameField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER && !nicknameField.getText().equals("")) {
                        nicknameField.setEnabled(false);
                        nickButton.setEnabled(false);
                        cliButton.setEnabled(false);

                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                invalidNick();
                            }
                        });
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });

            inputPanel.add(nicknameField);

            nickButton.setBackground(new Color(0, 153, 0));
            nickButton.setFont(new Font("Tahoma", 1, 24));
            nickButton.setForeground(new Color(0, 102, 51));
            nickButton.setText("Ok");
            nickButton.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
            nickButton.setOpaque(false);
            nickButton.setPreferredSize(new Dimension(70, 70));


            nickButton.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if(!nicknameField.getText().equals("")){
                        nicknameField.setEnabled(false);
                        nickButton.setEnabled(false);
                        cliButton.setEnabled(false);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                invalidNick();
                            }
                        });
                    }

                }
            });

            inputPanel.add(nickButton);

            kGradientPanel.add(inputPanel);

            bottomPanel.setOpaque(false);
            bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 200, 0));

            cliButton.setBackground(new Color(204, 255, 204));
            cliButton.setFont(new Font("Tahoma", 0, 14));
            cliButton.setForeground(new Color(204, 102, 0));
            cliButton.setText("Continue with CLI");
            cliButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            cliButton.setOpaque(false);
            cliButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Object[] option = {"Confirm", "Cancel"};
                    int n = JOptionPane.showOptionDialog(mainFrame, "Are you sure you want to switch to the command line? Gui will be closed.", "Close Graphic User Interface", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
                    ;
                    if (n == JOptionPane.YES_OPTION) {
                        mainFrame.setVisible(false);
                        System.out.println("Continue here...");
                        client.setView(new CLI(client));
                        client.getView().updateLoginScreen(null);
                    }

                }
            });

            bottomPanel.add(cliButton);

            kGradientPanel.add(bottomPanel);
            this.setLayout(new BorderLayout());
            this.add(kGradientPanel, BorderLayout.CENTER);

        }

        private void invalidNick(){
            nickButton.setText("");
            infoLabel.setText("Waiting for Server response...");
            nickButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ajax-loader.gif")));
            client.getNetworkHandler().sendMessage(new AuthenticationMessage(nicknameField.getText()));
            mainPanel.revalidate();
            mainPanel.repaint();
            mainFrame.repaint();
        }


    }



}
