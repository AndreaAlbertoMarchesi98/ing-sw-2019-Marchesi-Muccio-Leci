/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.ing.sw.psp017.view.GraphicUserInterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseMotionListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

/**
 *
 * @author oXCToo
 */
public class KGradientPanel extends JPanel {

    private Color kStartColor = Color.MAGENTA;
    private Color kEndColor = Color.BLUE;
    private boolean kTransparentControls = true;
    private int kGradientFocus = 500;
    private boolean backToOrigin = false;
    private  boolean flipFlopKgradient = false;


    private static final float HUE_MIN = 0;
    private static final float HUE_MAX = 1;
    private float hue = HUE_MIN;
    private final float delta = 0.01f;


    public Color getkStartColor() {
        return kStartColor;
    }

    public void setkStartColor(Color kStartColor) {
        this.kStartColor = kStartColor;
    }

    public Color getkEndColor() {
        return kEndColor;
    }

    public void setkEndColor(Color kEndColor) {
        this.kEndColor = kEndColor;
    }

    public boolean iskTransparentControls() {
        return kTransparentControls;
    }

    public void setkTransparentControls(boolean kTransparentControls) {
        this.kTransparentControls = kTransparentControls;
    }

    public int getkGradientFocus() {
        return kGradientFocus;
    }

    public void setkGradientFocus(int kGradientFocus) {
        this.kGradientFocus = kGradientFocus;
    }



  
 
    
    

    public KGradientPanel() {
        setBg(kTransparentControls);
    }

    @Override
    public synchronized void addMouseMotionListener(MouseMotionListener l) {
        super.addMouseMotionListener(l); //To change body of generated methods, choose Tools | Templates.

        System.out.println("mouse motion listeners ");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();

        GradientPaint gp = new GradientPaint(0, 0, kStartColor, kGradientFocus, 1000-kGradientFocus, kEndColor);;

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        //g2d.dispose();
    }

    private void setBg(boolean isOpaque) {
        Component[] components = this.getComponents();
        for (Component component : components) {

            ((JLabel) component).setOpaque(isOpaque);
            ((JLabel) component).setOpaque(isOpaque);
            ((JLabel) component).setOpaque(isOpaque);
            ((JLabel) component).setOpaque(isOpaque);
            ((JLabel) component).setOpaque(isOpaque);
            ((JLabel) component).setOpaque(isOpaque);
            ((JLabel) component).setOpaque(isOpaque);

        }
    }

    public void backgroundTransition() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                revalidate();
                repaint();

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        /*
                        Color color = new Color((int) (Math.random() * 255), (int) (Math.random() * 256), (int) (Math.random() * 256));
                        if (tempKGradient) {
                            kGradientPanel1.kStartColor = color;

                        } else {
                            kGradientPanel1.kEndColor = color;

                        }

                        tempKGradient = !tempKGradient;
                         */


                        hue += delta;
                        if (hue > HUE_MAX) {
                            hue = HUE_MIN;
                        }



                        if (flipFlopKgradient) {
                            kStartColor = Color.getHSBColor(hue, 1, 1).darker();

                        } else {
                            kEndColor = Color.getHSBColor(  hue + 16* delta, 1, 1);

                        }
                        //16



                        kStartColor = diffColor(kStartColor,kEndColor);


                        revalidate();
                        repaint();
                    }

                };
                Timer work = new Timer();
                work.schedule(task,0,200);



            }


        }).start();


        new Thread(new Runnable() {

            @Override
            public void run() {
                revalidate();
                repaint();

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {

                        Color color = new Color((int) (Math.random() * 150 +50), (int) (Math.random() * 150 +50), (int) (Math.random() * 150 +50));
                        if (flipFlopKgradient) {
                            kStartColor = color;

                        } else {
                            kEndColor = color;

                        }

                        flipFlopKgradient = !flipFlopKgradient;

                        revalidate();
                        repaint();
                    }

                };
                Timer work = new Timer();
                work.schedule(task,100000,20000);


            }


        }).start();

    }

    private Color diffColor(Color kStartColor, Color kEndColor) {

        int diffRed = kEndColor.getRed() - kStartColor.getRed();
        int diffGreen = kEndColor.getGreen() - kStartColor.getGreen();
        int diffBlue = kEndColor.getBlue() - kStartColor.getBlue();

        diffRed = (int) ((diffRed * 0.2) + kStartColor.getRed());
        diffGreen = (int) ((diffGreen * 0.2) + kStartColor.getGreen());
        diffBlue = (int) ((diffBlue * 0.2) + kStartColor.getBlue());

        return  new Color(diffRed,diffGreen,diffBlue);
    }


    public void backgroundGradient()
    {
        new Thread(new Runnable() {

        @Override
        public void run() {
            revalidate();
            repaint();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {

                    if(backToOrigin)
                    {
                        kGradientFocus--;
                    }
                    else
                    {
                        kGradientFocus++;
                    }

                    // if (kGradientFocus > 1000) kGradientFocus = 10 ;
                    if(kGradientFocus > 1000) backToOrigin = true;
                    if(kGradientFocus < 1) backToOrigin = false;


                    revalidate();
                    repaint();
                }

            };
            java.util.Timer work = new java.util.Timer();
            work.schedule(task,0,10);


        }


    }).start();
    }

}
