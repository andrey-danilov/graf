import javafx.scene.shape.Ellipse;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class mainClass extends JPanel {

    static JFrame frame;


    static double xMain;
    static double yMain;
    static Ellipse2D pointMain;
    static Line2D xLine;
    static Line2D yLine;

    static JButton point= new JButton("add point");
    static JTextField x = new JPasswordField(10);
    static JTextField y= new JPasswordField(10);
    static JButton line= new JButton("add line");
    static JTextField x1 = new JPasswordField(10);
    static JTextField y1= new JPasswordField(10);
    static JTextField x2 = new JPasswordField(10);
    static JTextField y2= new JPasswordField(10);


    static ArrayList points;
    static ArrayList lines;
    static Line2D newLine;
    static Ellipse2D newPoint;

    static Graphics2D g2;


    public static void main(String[] args) {

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(710, 700);
        frame.setVisible(true);
        xMain=frame.getSize().getWidth()/2;
        yMain=frame.getSize().getHeight()/2;

        pointMain= new Ellipse2D.Double(xMain-2.5,yMain-2.5,5,5);
        xLine = new Line2D.Double(0,yMain,xMain*2,yMain);
        yLine= new Line2D.Double(xMain,0,xMain,yMain*2);


        MyPanel panel = new MyPanel();
        panel.setSize((int)xMain*2,(int)yMain*2);
        panel.setLocation(0, 0);
        frame.add(panel);
    }

    static class MyPanel extends JPanel
    {

        public MyPanel()
        {   newPoint =null;
            newLine =null;
            points = new ArrayList();
            addMouseListener(new CustomListener());
            addMouseMotionListener(new MyMuve());
            lines = new ArrayList();
            point.setSize(100, 30); point.setLocation(0, 0);
            add(point);
            point.addActionListener(new Pointadd());
            x.setEnabled(true);     x.setSize(50, 30);  x.setLocation(100, 0);
            y.setEnabled(true);     y.setSize(50, 30);  y.setLocation(150, 0);
            add(x, BorderLayout.NORTH);
            add(y);
            add(point);
            line.setSize(100, 30); line.setLocation(0, 60);
            line.addActionListener(new Lineadd());
            x1.setEnabled(true);     x1.setSize(50, 30);  x1.setLocation(100, 60);
            y1.setEnabled(true);     y1.setSize(50, 30);  y1.setLocation(150, 60);
            x2.setEnabled(true);     x2.setSize(50, 30);  x2.setLocation(100, 90);
            y2.setEnabled(true);     y2.setSize(50, 30);  y2.setLocation(150, 90);
            add(x1, BorderLayout.NORTH);
            add(y1);
            add(x2, BorderLayout.NORTH);
            add(y2);
            add(line);
            repaint();
        }

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g2=(Graphics2D)g;
            g2.setColor(Color.BLACK);
            g2.draw(yLine);
            g2.draw(xLine);
            g2.fill(pointMain);



                for(int i=0; i<points.size(); i++)
                   {
                        g2.fill((Ellipse2D) points.get(i));
                    }
            }


        public void add(Point point) {
            newPoint= new Ellipse2D.Double(point.getX()-10,point.getY()-10,20,20);
            points.add(newPoint);
            repaint();
        }
        public void addLine(Point p1,Point p2) {
            newLine= new Line2D.Double(p1.getX(),p1.getY(),p2.getX(),p2.getY());
            lines.add(newPoint);
            repaint();
        }

        public Ellipse2D find(Point2D p)
        {
            for(int i=0; i<points.size(); i++)
            {
                Ellipse2D  e = (Ellipse2D) points.get(i);
                if(e.contains(p)) return e;
            }
            return null;
        }

        public void remove(Ellipse2D e)
        {
            if(e==null) return;
            if(e==newPoint) newPoint=null;
            points.remove(e);
            repaint();
        }

        public class CustomListener extends MouseAdapter {

            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()>=2)
                {
                    newPoint = find(e.getPoint());
                    if(newPoint!=null) remove(newPoint);
                }
            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {
                newPoint = find(e.getPoint());
                if(newPoint == null){ add(e.getPoint());
                }}

            public void mouseReleased(MouseEvent e) {

            }
        }
        public class MyMuve implements MouseMotionListener
        {
            public void mouseMoved(MouseEvent e)
            {

            }
            public void mouseDragged(MouseEvent e)
            {   if(xMain==e.getX() && yMain==e.getY())
                    {
                        remove(pointMain);
                    }else {
                            if(newPoint!=null)
                                {
                                newPoint.setFrame(e.getX()-10,e.getY()-10,20,20);
                                repaint();
                                }
                    }
            }


        }
        public class Pointadd implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                int xt =new Integer(x.getText()).intValue();
                int yt =new Integer(y.getText()).intValue();

                Point temp = new Point(xt,yt);

                add(temp);
            }
        }

        public class Lineadd implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                int xt1 =new Integer(x1.getText()).intValue();
                int yt1 =new Integer(y1.getText()).intValue();
                int xt2 =new Integer(x2.getText()).intValue();
                int yt2 =new Integer(y2.getText()).intValue();

                Point x = new Point(xt1,yt1);
                Point y = new Point(xt2,yt2);
                addLine(x,y);

            }
        }
    }


}
