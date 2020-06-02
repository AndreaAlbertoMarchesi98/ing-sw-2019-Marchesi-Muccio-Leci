package it.polimi.ing.sw.psp017.view.GraphicUserInterface;

import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.BoardMessage;
import it.polimi.ing.sw.psp017.model.Vector2d;
import it.polimi.ing.sw.psp017.view.ActionNames;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 *  this class is used to managed the board game GUI which is formed by 3 layer.
 *  being able to refresh this layers is useful for a fluid game
 *
 *  ++++++++++++++++++++++++++++ --->JButtonTile layer       ------------------|
 *                                                                             |
 *  ++++++++++++++++++++++++++++ --->workerJLabel             <----------------|
 *                                                                             |
 *  ++++++++++++++++++++++++++++ --->buildJLabel              <----------------|
 *
 */
public class JButtonTile extends JButton {



    private final Color transparentColor = new Color(.1f, .1f, .1f, .1f);
    private final Vector2d position;
    private int level;
    private boolean isDome;



    private boolean isValidTile = true;
    private int playerNumber = 0;


    private JLabel workerJLabel;
    private JLabel buildJLabel;

    public JButtonTile(Vector2d position) {
        this.position = position;
        this.level = 0;
        this.isDome = false;

        //JButton initialization
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
        setIcon(new TranslucentButtonIcon(this, transparentColor));
        workerJLabel = new JLabel();
        buildJLabel = new JLabel();


        workerJLabel.setVerticalAlignment(SwingConstants.CENTER);
        workerJLabel.setHorizontalAlignment(SwingConstants.CENTER);
        buildJLabel.setVerticalAlignment(SwingConstants.CENTER);
        buildJLabel.setHorizontalAlignment(SwingConstants.CENTER);





    }
    public JButtonTile(){
        position = null;
    }


    public Vector2d getPosition() {
        return position;
    }

    public JLabel getWorkerJLabel() {
        return workerJLabel;
    }

    public void setWorkerJLabel(JLabel workerJLabel) {
        this.workerJLabel = workerJLabel;
    }

    public JLabel getBuildJLabel() {
        return buildJLabel;
    }

    public void setBuildJLabel(JLabel buildJLabel) {
        this.buildJLabel = buildJLabel;
    }

    public boolean isValidTile() {
        return isValidTile;
    }


    /**
     * update 3 layers getting info from BoardMessage
     * @param printableTile tile info
     */
    public void updateTile(BoardMessage.PrintableTile printableTile) {


        this.level = printableTile.level;
        this.isDome = printableTile.dome;
        this.playerNumber = printableTile.playerNumber;
        updateBoard();
    }


    public void updateValidTiles(boolean isValidTile, ActionNames actionNames) {
        this.isValidTile = isValidTile;
        showValidTiles(actionNames);
    }

    /**
     * showing and coloring validTile
     * @param actionNames MOVE or BUILD
     */
    public void showValidTiles(ActionNames actionNames) {
        Color color = Color.red;
        switch (actionNames) {
            case MOVE:
                color = Color.BLUE;
                break;
            case BUILD:
                color = Color.ORANGE;
        }
        if (this.isValidTile) {
            this.setColorTiles(color);
        }

        //pensare a popUp worker senza validTiles
    }

    /**
     * updating workerJLabel and buildJLabel
     */
    private void updateBoard() {

        buildJLabel.setIcon(null);
        workerJLabel.setIcon(null);
        this.setColorTiles(transparentColor);


        //buildJLabel
        if (this.isDome) {
            buildJLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("Buildings/dome.png")));
        } else if (this.level == 1) {
            buildJLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("Buildings/level1.png")));
        } else if (this.level == 2) {
            buildJLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("Buildings/level2.png")));
        } else if (this.level == 3) {
            buildJLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("Buildings/level3.png")));
        }


        //workerLabel
        if (this.playerNumber == 1) {
            workerJLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("player/player1.png")));
        } else if (this.playerNumber == 2) {
            workerJLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("player/player2.png")));
        } else if (this.playerNumber == 3) {
            workerJLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("player/player3.png")));
        }

    }



    public void setColorTiles(Color color) {
        this.setIcon(new TranslucentButtonIcon(this, color)); //colorazione tile
        this.repaint();  //serve?
    }


    /**
     * inner class used to customize JButton
     */
    protected class TranslucentButtonIcon implements Icon {
        private final Color BR = new Color(0f, 0f, 0f, .4f);
        private final Color ST = new Color(1f, 1f, 1f, .2f);
        private Color shadeButtonColor;
        private static final int R = 8;
        private int width;
        private int height;

        //   private Image image;
        protected TranslucentButtonIcon(JComponent c, Color color) {
            this.shadeButtonColor = color;
            Insets i = c.getBorder().getBorderInsets(c);
            Dimension d = c.getPreferredSize();
            width = d.width - i.left - i.right;
            height = d.height - i.top - i.bottom;
            // image = null;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
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
            if (m.isRollover()) {
                ssc = ST;
                bgc = shadeButtonColor;
            } else {
                ssc = shadeButtonColor;
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



