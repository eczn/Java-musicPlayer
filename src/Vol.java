import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by eczn on 2016/12/3.
 */

public class Vol extends Canvas implements Runnable {
    public double now_vol;
    public Jplayer jplayer;
    public int width;
    public int height;
    public boolean isPressed;

    public Vol(Jplayer JPLAYER){
        super();
        jplayer = JPLAYER;
        now_vol = 1.0;
        height = 40;
        System.out.println("!@#$!: "+getSize().getWidth());
        now_at = 420;
        width = 420;
        setBackground(new Color(255,255,255));

        repaint();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                isPressed = true;
                System.out.println("isPressed: "+isPressed);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                now_at = width-e.getX();
                now_vol = (now_at / getSize().getWidth());

                try {
                    jplayer.setMediaPlayerVol(now_vol);
                } catch (Exception exce){
                    exce.printStackTrace();
                    System.out.println("bugbug!!!!");
                }
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionListener(){
            @Override
            public void mouseDragged(MouseEvent e){
                if (isPressed){
                    now_at = width-e.getX();
                    pre_at = now_at;
                    now_vol = (now_at / getSize().getWidth());

                    try {
                        jplayer.setMediaPlayerVol(now_vol);
                    } catch (Exception exce){
                        exce.printStackTrace();
                        System.out.println("bugbug!!!!");
                    }
                    repaint();
                }
            }
            @Override
            public void mouseMoved(MouseEvent e){
                if (isPressed){
                    now_at = width-e.getX();
                    pre_at = now_at;
                    now_vol = (now_at / getSize().getWidth());

                    try {
                        jplayer.setMediaPlayerVol(now_vol);
                    } catch (Exception exce){
                        exce.printStackTrace();
                        System.out.println("MediaPlayerVolSettingBUG");
                    }
                    repaint();
                }
            }
        });

        jplayer.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
//                System.out.println(e.getWheelRotation());

                now_at -= e.getWheelRotation() * 35;
                now_vol = now_at / getSize().getWidth();
                if (now_at<=0){
                    now_at = 0;
                } else if (now_at >= width){
                    now_at = width;
                }
                repaint();

                System.out.print("!!WHEEL!!    ");
                System.out.println(e.getWheelRotation());

                if (now_vol >= 1.0){
                    now_vol = 1.0;
                } else if (now_vol <= 0){
                    now_vol = 0;
                }

                try {
                    jplayer.setMediaPlayerVol(now_vol);
                } catch (Exception exce){
                    exce.printStackTrace();
                    System.out.println("bugbug!!!!");
                }

            }
        });
    }

    public int now_at=-1;
    public int pre_at;
    @Override
    public void paint(Graphics g){
        int rgb;
        if (now_at == -1){
            now_at = 160;
        }


        if (pre_at != now_at){
            System.out.println("now_at != pre_at");
            try {
                int i = 0;
                int cha = now_at - pre_at;
//                g.setColor(new Color(222,222,222));
                System.out.println("cha!!: "+cha);
                int temp = (cha>=0)?(1):(-1);

                for (i=0;(cha>=0)?(i<=cha):(i>=cha);i+=temp){
//                    rgb = (int)(now_at / getSize().getWidth());
                    rgb = (int)(((pre_at+i+0.0) / width) * 40);
                    g.setColor(new Color(250-rgb/4,250-(int)(rgb/1.5),250-(int)(rgb/1.5)));
//                    g.clearRect(0, 0, 420, height);
                    g.fillRect(0, 0, 420, height);
//                    System.out.println(pre_at+i);
//                    g.fillRect(0, 0, pre_at+i, height);
                    g.clearRect(0, 0, width-(pre_at+i), height);
                    g.setColor(new Color(0,0,0));

                    double temp_now_at = (pre_at+i)/(double)width;
                    setFont(new Font("Arial", Font.PLAIN, (int)(temp_now_at*12)+12));
                    g.drawString( Integer.toString((int)(((double)(pre_at+i)/width)*100)) + "%",
                            width-(pre_at+i)-(int)(20*(1-temp_now_at)),
                            30);

                    Thread.sleep(1);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            pre_at = now_at;
            g.fillRect(0, 0, 420, height);
            g.clearRect(0, 0, width-now_at, height);

//            repaint();
//            return;
        }

        rgb = (int)(now_vol * 40);


        g.setColor(new Color(250-rgb/4,250-(int)(rgb/1.5),250-(int)(rgb/1.5)));

//        g.fillRect(0, 0, now_at, height);
        g.fillRect(0, 0, 420, height);
        g.clearRect(0, 0, width-now_at, height);

        g.setColor(new Color(0,0,0));

//        setFont(new Font("Arial", Font.PLAIN, (int)(now_vol*16)+16));
        g.drawString(Integer.toString((int)(((double)(now_at)/width)*100)) + "%",
                width-(now_at)-(int)(20*(1-now_vol)),
                30);
    }

    @Override
    public void run(){
        while (true){
//            repaint();
//            pre_at = now_at;
            try {
                Thread.sleep(1000);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
