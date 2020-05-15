package it.polimi.ing.sw.psp017.view.GraphicUserInterface;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

    public class Board extends JFrame {
        private final Dimension dim;
        public Board() {
            dim = Toolkit.getDefaultToolkit().getScreenSize();
            initComponents();
        }

        private void initComponents() {

            kGradientPanel1 = new KGradientPanel();
            kGradientPanel1.setkEndColor(new Color(255, 255, 153));
            kGradientPanel1.setkStartColor(new Color(0, 204, 102));
            tilePanel = new JPanel();
            northPanel_JPanel = new JPanel();
            jLabel1 = new JLabel();
             boardGamePanel = new JPanel(){

                public void paintComponent(java.awt.Graphics g) {

                    super.paintComponent(g);

                    java.awt.Image img = new ImageIcon(getClass().getClassLoader().getResource("STUFF/BOARDgAMEbACKgROUND.png")).getImage();

                    java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
                    int x = (this.getWidth() - img.getWidth(null)) / 2;
                    int y = (this.getHeight() - img.getHeight(null)) / 2;
                    g2d.drawImage(img, 0, 0,this.getWidth(),this.getHeight(), null);

                }

            };

            workerPanel = new JPanel();
            buttonPanel = new JPanel();
            DXPanel = new JPanel();
            upperDXPanel = new JPanel();
            undo = new JButton(){

                @Override
                public void updateUI() {
                    super.updateUI();
                    setVerticalAlignment(SwingConstants.CENTER);
                    setHorizontalAlignment(SwingConstants.CENTER);
                    setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
                    setMargin(new Insets(2, 8, 2, 8));
                    setBorderPainted(false);
                    setContentAreaFilled(false);
                    setFocusPainted(false);
                    setOpaque(false);
                    setForeground(Color.WHITE);
                    setSize(65,65);
                    setIcon(new TranslucentButtonIcon(this));

                }
                class TranslucentButtonIcon implements Icon {
                    private final Color TL = new Color(1f, 1f, 1f, .2f);
                    private final Color BR = new Color(0f, 0f, 0f, .4f);
                    private final Color ST = new Color(1f, 1f, 1f, .2f);
                    private final Color SB = new Color(1f, 1f, 1f, .1f);
                    private static final int R = 8;
                    private int width;
                    private int height;

                    protected TranslucentButtonIcon(JComponent c) {
                        Insets i = c.getBorder().getBorderInsets(c);
                        Dimension d = c.getPreferredSize();
                        width = d.width - i.left - i.right;
                        height = d.height - i.top - i.bottom;
                    }

                    @Override
                    public void paintIcon(Component c, Graphics g, int x, int y) {
                        if (c instanceof AbstractButton) {
                            AbstractButton b = (AbstractButton) c;
                            Insets i = b.getBorder().getBorderInsets(b);
                            int w = 65;
                            int h = 65;
                            width = w - i.left - i.right;
                            height = h - i.top - i.bottom;
                            Graphics2D g2 = (Graphics2D) g.create();
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            Shape area = new RoundRectangle2D.Double(x - i.left, y - i.top, w - 1, h - 1, R, R);
                            Color ssc = TL;
                            Color bgc = BR;
                            ButtonModel m = b.getModel();
                            if (m.isPressed()) {
                                ssc = SB;
                                bgc = ST;
                            } else if (m.isRollover()) {
                                ssc = ST;
                                bgc = SB;
                            }

                            g2.setPaint(new GradientPaint(0, 0, ssc, 0, h, bgc, true));
                            g2.drawImage(new ImageIcon(getClass().getClassLoader().getResource("STUFF/pointing-left.png")).getImage(),x - i.left,y- i.top,null);
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

            };
            powerActivated = new JButton();
            lowerDXPanel = new JPanel();
            opponentPlayerInfo3 = new JButton();
            SXPanel = new JPanel();
            opponentPlayerInfo1 = new JButton();
            opponentPlayerInfo2 = new JButton();
            southPanel = new JPanel();
            quitButton = new JButton(){

                @Override
                public void updateUI() {
                    super.updateUI();
                    setVerticalAlignment(SwingConstants.CENTER);
                    setHorizontalAlignment(SwingConstants.CENTER);
                    setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
                    setMargin(new Insets(2, 8, 2, 8));
                    setBorderPainted(false);
                    setContentAreaFilled(false);
                    setFocusPainted(false);
                    setOpaque(false);
                    setForeground(Color.WHITE);
                    setSize(65,65);
                    setIcon(new TranslucentButtonIcon(this));

                }
                class TranslucentButtonIcon implements Icon {
                    private final Color TL = new Color(1f, 1f, 1f, .0f);
                    private final Color BR = new Color(0f, 0f, 0f, .0f);
                    private final Color ST = new Color(1f, 1f, 1f, .2f);
                    private final Color SB = new Color(1f, 1f, 1f, .1f);
                    private static final int R = 8;
                    private int width;
                    private int height;

                    protected TranslucentButtonIcon(JComponent c) {
                        Insets i = c.getBorder().getBorderInsets(c);
                        Dimension d = c.getPreferredSize();
                        width = d.width - i.left - i.right;
                        height = d.height - i.top - i.bottom;
                    }

                    @Override
                    public void paintIcon(Component c, Graphics g, int x, int y) {
                        if (c instanceof AbstractButton) {
                            AbstractButton b = (AbstractButton) c;
                            Insets i = b.getBorder().getBorderInsets(b);
                            int w = 65;
                            int h = 65;
                            width = w - i.left - i.right;
                            height = h - i.top - i.bottom;
                            Graphics2D g2 = (Graphics2D) g.create();
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            Shape area = new RoundRectangle2D.Double(x - i.left, y - i.top, w - 1, h - 1, R, R);
                            Color ssc = TL;
                            Color bgc = BR;
                            ButtonModel m = b.getModel();
                            if (m.isPressed()) {
                                ssc = SB;
                                bgc = ST;
                            } else if (m.isRollover()) {
                                ssc = ST;
                                bgc = SB;
                            }

                            g2.setPaint(new GradientPaint(0, 0, ssc, 0, h, bgc, true));
                            g2.drawImage(new ImageIcon(getClass().getClassLoader().getResource("STUFF\\quit.png")).getImage(),x - i.left,y- i.top,null);
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

            };

            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setMinimumSize(new Dimension(1600, 900));
            this.setExtendedState(MAXIMIZED_BOTH);
            setMaximumSize(new Dimension(1600,900));
            setPreferredSize(new Dimension(1600, 900));
            setSize(new Dimension(1600, 900));
            kGradientPanel1.setLayout(new BorderLayout());
          //  kGradientPanel1.setSize(dim);
       //     kGradientPanel1.setPreferredSize(dim);

            northPanel_JPanel.setOpaque(false);
            FlowLayout layout  = new FlowLayout();
            layout.setVgap(10);
            northPanel_JPanel.setLayout(layout);
            jLabel1.setFont(new Font("Tahoma", 3, 36));
            jLabel1.setForeground(new Color(255, 255, 255));
            jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel1.setVerticalAlignment(SwingConstants.BOTTOM);
            jLabel1.setText("Move / Build   ");
            northPanel_JPanel.add(jLabel1);
            kGradientPanel1.add(northPanel_JPanel, BorderLayout.NORTH);

            workerPanel.setOpaque(false);
            workerPanel.setLayout(new GridLayout(5, 5, 2, 2));
            final JLabel[][] labelArrayWorker = new JLabel[5][5];
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 5; col++) {
                    labelArrayWorker[row][col] = new JLabel();
                    labelArrayWorker[row][col].setHorizontalAlignment(SwingConstants.CENTER);
                    labelArrayWorker[row][col].setLabelFor(boardGamePanel);
                    workerPanel.add(labelArrayWorker[row][col]);
                }

            }

            tilePanel.setOpaque(false);
            tilePanel.setLayout(new java.awt.GridLayout(5, 5,2,2));

             final JLabel[][] labelArrayBuild = new JLabel[5][5];
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 5; col++) {
                    labelArrayBuild[row][col]= new JLabel();
                    labelArrayBuild[row][col].setHorizontalAlignment(SwingConstants.CENTER);
                    labelArrayBuild[row][col].setLabelFor(boardGamePanel);
                    tilePanel.add(labelArrayBuild[row][col]);
                }
            }
            buttonPanel.setLayout(new GridLayout(5, 5));
             final JButton[][] buttonGrid = new JButton[5][5];
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 5; col++) {
                    buttonGrid[row][col]= new JButton(){

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
                            setIcon(new TranslucentButtonIcon(this));

                        }
                        class TranslucentButtonIcon implements Icon {
                            private final Color TL = new Color(1f, 1f, 1f, .2f);
                            private final Color BR = new Color(0f, 0f, 0f, .4f);
                            private final Color ST = new Color(1f, 1f, 1f, .2f);
                            private final Color SB = new Color(1f, 1f, 1f, .1f);
                            private static final int R = 8;
                            private int width;
                            private int height;

                            protected TranslucentButtonIcon(JComponent c) {
                                Insets i = c.getBorder().getBorderInsets(c);
                                Dimension d = c.getPreferredSize();
                                width = d.width - i.left - i.right;
                                height = d.height - i.top - i.bottom;
                            }

                            @Override
                            public void paintIcon(Component c, Graphics g, int x, int y) {
                                if (c instanceof AbstractButton) {
                                    AbstractButton b = (AbstractButton) c;
                                    // XXX: Insets i = b.getMargin();

                                    Insets i = b.getBorder().getBorderInsets(b);
                                    int w = c.getWidth();
                                    int h = c.getHeight();
                                    width = w - i.left - i.right;
                                    height = h - i.top - i.bottom;
                                    Graphics2D g2 = (Graphics2D) g.create();
                                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                    Shape area = new RoundRectangle2D.Double(x - i.left, y - i.top, w - 1, h - 1, R, R);
                                    Color ssc = TL;
                                    Color bgc = BR;
                                    ButtonModel m = b.getModel();
                                    if (m.isPressed()) {
                                        ssc = SB;
                                        bgc = ST;
                                    } else if (m.isRollover()) {
                                        ssc = ST;
                                        bgc = SB;
                                    }

                                    g2.setPaint(new GradientPaint(0, 0, ssc, 0, h, bgc, true));
                                    // g2.drawImage(new ImageIcon(getClass().getClassLoader().getResource("STUFF\\peopleBIG.png")).getImage(),0,0,null);
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

                    };
                    buttonPanel.add(buttonGrid[row][col]);
                    this.addWindowStateListener(new WindowStateListener(){
                                          @Override
                                          public void windowStateChanged(WindowEvent e) {
                                              for (int row = 0; row < 5; row++) {
                                                  for (int col = 0; col <5; col++) {
                                                      buttonGrid[row][col].updateUI();
                                                      buttonGrid[row][col].revalidate();
                                                      buttonGrid[row][col].repaint();
                                                    }
                                                  }
                                          }
                                      }
                    );
                    MouseListener buttonListerner2 = new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {

                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
                            for (int row = 0; row < 5; row++) {
                                for (int col = 0; col <5; col++) {
                                    if (buttonGrid[row][col] == e.getSource()) {
                                        System.out.println("Selected row and column: "+ row + " " + col);
                                        if(e.getButton() == MouseEvent.BUTTON3){
                                            labelArrayBuild[row][col].setIcon(new ImageIcon(getClass().getClassLoader().getResource("Buildings/dome.png")));
                                        }
                                        else if(e.getButton() == MouseEvent.BUTTON1){
                                             labelArrayWorker[row][col].setIcon(new ImageIcon(getClass().getClassLoader().getResource("STUFF/peopleBIG.png")));
                                        }
                                    }
                                }
                            }
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
                    };

                    buttonGrid[row][col].addMouseListener(buttonListerner2);
                }

            }
            
            buttonPanel.setOpaque(false);

            DXPanel.setOpaque(false);
            DXPanel.setPreferredSize(new java.awt.Dimension(200, 400));
          //DXPanel.setSize(200,400);
            DXPanel.setMinimumSize(new Dimension(100,400));
            DXPanel.setLayout(new GridLayout(2, 1));

            upperDXPanel.setOpaque(false);
            upperDXPanel.setLayout(new GridLayout(2, 1, 0, 2));

            undo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("STUFF/pointing-left.png")));
            undo.setToolTipText("Undo");
            //undo.setSelected(true);
            upperDXPanel.add(undo);
            powerActivated.setText("Power Active");
          //  powerActivated.setIcon(new ImageIcon(getClass().getClassLoader().getResource("STUFF/heropower_active.png")));

            upperDXPanel.add(powerActivated);

            DXPanel.add(upperDXPanel);

            lowerDXPanel.setOpaque(false);

            opponentPlayerInfo3.setFont(new Font("Tahoma", 3, 24));
            opponentPlayerInfo3.setForeground(new Color(255, 255, 255));
         //   opponentPlayerInfo3.setIcon(new ImageIcon(getClass().getClassLoader().getResource("STUFF/podium-characters-Apolo.png"))); // NOI18N
            opponentPlayerInfo3.setText("player 1");
          //  opponentPlayerInfo3.setToolTipText("");
            opponentPlayerInfo3.setHorizontalTextPosition(SwingConstants.CENTER);
            opponentPlayerInfo3.setOpaque(false);
            opponentPlayerInfo3.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    opponentPlayerInfo3ActionPerformed(evt);
                }
            });



            DXPanel.add(opponentPlayerInfo3);

            kGradientPanel1.add(DXPanel,BorderLayout.EAST);

            SXPanel.setOpaque(false);
            SXPanel.setPreferredSize(new Dimension(200, 400));
            SXPanel.setMinimumSize(new Dimension(100,400));
           // SXPanel.setSize(200,400);
            SXPanel.setLayout(new GridLayout(2, 1));

            opponentPlayerInfo1.setFont(new Font("Tahoma", 3, 24));
            opponentPlayerInfo1.setForeground(new Color(255, 255, 255));
         //   opponentPlayerInfo1.setIcon(new ImageIcon(getClass().getClassLoader().getResource("STUFF/podium-characters-Apolo.png"))); // NOI18N
            opponentPlayerInfo1.setText("player 2");
            opponentPlayerInfo1.setToolTipText("");
            opponentPlayerInfo1.setHorizontalTextPosition(SwingConstants.CENTER);
            opponentPlayerInfo1.setOpaque(false);
            opponentPlayerInfo1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    opponentPlayerInfo1ActionPerformed(evt);
                }
            });
            SXPanel.add(opponentPlayerInfo1);

            opponentPlayerInfo2.setText("player 3");
            opponentPlayerInfo2.setFont(new Font("Tahoma", 3, 24));
            opponentPlayerInfo2.setForeground(new Color(255, 255, 255));
            opponentPlayerInfo2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    opponentPlayerInfo2ActionPerformed(evt);
                }
            });
            SXPanel.add(opponentPlayerInfo2);

            kGradientPanel1.add(SXPanel, BorderLayout.WEST);

            southPanel.setOpaque(false);
            southPanel.setPreferredSize(new Dimension(1120, 100));
            quitButton.setToolTipText("QUIT");
            quitButton.setOpaque(false);
            quitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    quitButtonActionPerformed(evt);
                }
            });
           // southPanel.setSize(1120,100);
            southPanel.setLayout(new BorderLayout());

            southPanel.setMinimumSize(new Dimension(this.getMinimumSize().width,200));
            southPanel.add(quitButton, BorderLayout.EAST);
            kGradientPanel1.add(southPanel, BorderLayout.SOUTH);

            Dimension dimBoard = new Dimension(800,650);
            JLayeredPane layer = new JLayeredPane();
            layer.setPreferredSize(dimBoard);
            layer.setSize(dimBoard);
            layer.setMinimumSize(dimBoard);
            kGradientPanel1.add(layer, BorderLayout.CENTER);
            System.out.println(layer.getWidth());
            System.out.println(this.getWidth());
            System.out.println(SXPanel.getMinimumSize().width);
            boardGamePanel.setBounds(  SXPanel.getWidth() + dimBoard.width/4,0,dimBoard.width,dimBoard.height);
            buttonPanel.setBounds(SXPanel.getWidth() + dimBoard.width/4,0,dimBoard.width,dimBoard.height);
            workerPanel.setBounds( SXPanel.getWidth() + dimBoard.width/4 ,0,dimBoard.width,dimBoard.height);
            tilePanel.setBounds(SXPanel.getWidth() + dimBoard.width/4,0,dimBoard.width,dimBoard.height);
            layer.add(boardGamePanel,new Integer(0));
            layer.add(buttonPanel,new Integer(3));
            layer.add(workerPanel,new Integer(2));
            layer.add(tilePanel,new Integer(1));


            this.setContentPane(kGradientPanel1);

            setLocationRelativeTo(null);
        }

        private void opponentPlayerInfo1ActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
        }

        private void opponentPlayerInfo2ActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
        }

        private void opponentPlayerInfo3ActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
        }

        private void quitButtonActionPerformed(ActionEvent evt) {
            Object[] option = {"Quit", "Cancel"};
            int n = JOptionPane.showOptionDialog(this, "Are you sure you want to quit the game ", "Quit ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);;
            if (n == JOptionPane.YES_OPTION) {
                this.dispose();
            }
        }


        public static void main(String args[]) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new Board().setVisible(true);
                }
            });
        }

        private JPanel DXPanel;
        private JPanel SXPanel;
        private JPanel boardGamePanel;
        private JPanel buttonPanel;
        private JLabel jLabel1;   //ha le informazioni
        private KGradientPanel kGradientPanel1;
        private JPanel lowerDXPanel;
        private JPanel northPanel_JPanel;
        private JButton opponentPlayerInfo1;
        private JButton opponentPlayerInfo2;
        private JButton opponentPlayerInfo3;
        private JButton powerActivated;
        private JButton quitButton;
        private JPanel southPanel;
        private JPanel tilePanel;
        private JButton undo;
        private JPanel upperDXPanel;
        private JPanel workerPanel;




    }



