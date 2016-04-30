import javafx.scene.shape.Ellipse;

import java.awt.*;
import javax.sound.sampled.Line;
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
    static Line2D scale;
    static Ellipse2D pointMain;
    static Line2D xLine;
    static Line2D yLine;

    static JButton point= new JButton("add point");
    static JTextField x = new JTextField("0",10);
    static JTextField y= new JTextField("0",10);

    static Color mycolor = (Color.black);
    static JButton line= new JButton("add line");
    static JTextField x1 = new JTextField("0",10);
    static JTextField y1= new JTextField("0",10);
    static JTextField x2 = new JTextField("0",10);
    static JTextField y2= new JTextField("0",10);

    static JButton color= new JButton("set color");

    static ArrayList points;
    static ArrayList lines;
    static Line2D newLine;
    static Ellipse2D newPoint;

    static Graphics2D g2;


    public static void main(String[] args) {

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setVisible(true);






        MyPanel panel = new MyPanel();
        panel.setLayout(null);
        panel.setLocation(0, 0);
        frame.add(panel);
    }

    static class MyPanel extends JPanel
    {

        public MyPanel()
        {   xMain=frame.getSize().getWidth()/2;
            yMain=frame.getSize().getHeight()/2;

            this.setSize((int)xMain*2,(int)yMain*2);
            newPoint =null;
            newLine =null;
            points = new ArrayList();
            addMouseListener(new CustomListener());
            addMouseMotionListener(new MyMuve());
            addMouseMotionListener(new mainMuve());
            addMouseWheelListener(new Scroll());
            lines = new ArrayList();
            point.setSize(100, 30); point.setLocation(0, 0);
            add(point);
            point.addActionListener(new Pointadd());
            add(point);
            x.setEnabled(true);
            x.setSize(50, 30);  x.setLocation(100, 0);
            add(x);
            y.setEnabled(true);
            y.setSize(50, 30);  y.setLocation(150, 0);
            add(y);

            line.setSize(100, 30); line.setLocation(0, 60);
            line.addActionListener(new Lineadd());
            add(line);
            x1.setEnabled(true);
            x1.setSize(50, 30);  x1.setLocation(100, 60);
            add(x1);
            y1.setEnabled(true);
            y1.setSize(50, 30);  y1.setLocation(150, 60);
            add(y1);
            x2.setEnabled(true);
            x2.setSize(50, 30);  x2.setLocation(100, 90);
            add(x2);
            y2.setEnabled(true);
            y2.setSize(50, 30);  y2.setLocation(150, 90);
            add(y2);

            color.setSize(90, 30); color.setLocation(200, 0);
            color.addActionListener(new setColor());
            add(color);
            repaint();
        }

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g2=(Graphics2D)g;
            pointMain= new Ellipse2D.Double(xMain-2.5,yMain-2.5,5,5);
            xLine = new Line2D.Double(0,yMain,xMain*2,yMain);
            yLine= new Line2D.Double(xMain,0,xMain,yMain*2);
            g2.setColor(Color.BLACK);
            g2.draw(yLine);
            g2.draw(xLine);
            g2.fill(pointMain);
            g2.setColor(mycolor);

            for(int i=0; i<xMain*2; i+=10){
                scale = new Line2D.Double(i,yMain+5,i,yMain-5);
                g2.draw(scale);
            }

            for(int i=0; i<yMain*2; i+=10){
                scale = new Line2D.Double(xMain-5,i,xMain+5,i);
                g2.draw(scale);
            }
            for(int i=0; i<points.size(); i++)
            {
                g2.fill((Ellipse2D) points.get(i));
            }
            for(int i=0; i<lines.size(); i++)
            {
                g2.draw((Line2D) lines.get(i));
            }


            }


        public void add(Point point) {

            newPoint= new Ellipse2D.Double(point.getX()-5,point.getY()-5,10,10);
            points.add(newPoint);
            repaint();
        }
        public void addLine(Point p1,Point p2) {
            g2.setColor(mycolor);
            newLine= new Line2D.Double(p1.getX(),p1.getY(),p2.getX(),p2.getY());
            lines.add(newLine);
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
                if (!pointMain.contains(e.getPoint())){
                if(e.getClickCount()>=2)
                {
                    newPoint = find(e.getPoint());
                    if(newPoint!=null) remove(newPoint);
                }
            }}

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {
                if (!pointMain.contains(e.getPoint())){
                    newPoint = find(e.getPoint());
                if (newPoint == null) {
                    add(e.getPoint());
                }
            }


            }
            public void mouseReleased(MouseEvent e) {

            }
        }
        public class MyMuve implements MouseMotionListener
        {
            public void mouseMoved(MouseEvent e)
            {
            }
            public void mouseDragged(MouseEvent e)
            {if (!pointMain.contains(e.getPoint())){
                if (newPoint != null) {
                    newPoint.setFrame(e.getX() - 5, e.getY() - 5, 10, 10);
                    repaint();
                }
            }


            }
        }
        public class Pointadd implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                int xt =new Integer(x.getText()).intValue();
                int yt =new Integer(y.getText()).intValue();

                Point temp = new Point((int)xMain+xt,(int)yMain-yt);

                add(temp);
            }
        }

        public class Lineadd implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                int xt1 =new Integer(x1.getText()).intValue();
                int yt1 =new Integer(y1.getText()).intValue();
                int xt2 =new Integer(x2.getText()).intValue();
                int yt2 =new Integer(y2.getText()).intValue();

                Point x = new Point((int)xMain+xt1,(int)yMain-yt1);
                Point y = new Point((int)xMain+xt2,(int)yMain-yt2);
                addLine(x,y);

            }
        }

        private class setColor implements ActionListener {
            public void actionPerformed(ActionEvent e) {
            mycolor= JColorChooser.showDialog(null,"set your color",mycolor);


            }
        }


        public class mainMuve implements MouseMotionListener {
            public void mouseMoved(MouseEvent e)
            {
            }
            public void mouseDragged(MouseEvent e)
            {
                if(pointMain.contains(e.getPoint())){
                    int Lastx = (int)xMain;
                    int Lasty = (int)yMain;
                    Ellipse2D[] tempPoints = new Ellipse2D[points.size()];
                    xMain=e.getX(); yMain=e.getY();
                    for(int i =0; i<points.size() ; i++){
                        tempPoints[i]= (Ellipse2D)points.get(i);
                        tempPoints[i].setFrame((xMain - Lastx) + (tempPoints[i].getCenterX() - 5), (yMain - Lasty) + (tempPoints[i].getCenterY() - 5), 10, 10);
                        points.set(i, tempPoints[i]);
                    }
                    Line2D[] tempLinis = new Line2D[lines.size()];
                    for(int i =0; i<lines.size() ; i++) {
                        tempLinis[i] = (Line2D) lines.get(i);
                        tempLinis[i].setLine((xMain - Lastx) + tempLinis[i].getX1(), (yMain - Lasty) + tempLinis[i].getY1(),
                                (xMain - Lastx) + tempLinis[i].getX2(), (yMain - Lasty) + tempLinis[i].getY2());
                        lines.set(i, tempLinis[i]);

                    }
                    repaint();

                }
            }
        }
        public class Scroll implements MouseWheelListener{

            public void mouseWheelMoved(MouseWheelEvent e) {
                Ellipse2D[] tempPoints = new Ellipse2D[points.size()];
                if(e.getPreciseWheelRotation()==-1.0){
                    for(int i =0; i<points.size() ; i++){
                        tempPoints[i]= (Ellipse2D)points.get(i);
                        tempPoints[i].setFrame((0.2 * (tempPoints[i].getCenterX() - xMain)) + tempPoints[i].getCenterX() - 5,
                                (0.2 * (tempPoints[i].getCenterY() - yMain)) + tempPoints[i].getCenterY() - 5, 10, 10);
                        points.set(i, tempPoints[i]);
                    }
                    Line2D[] tempLinis = new Line2D[lines.size()];
                    for(int i =0; i<lines.size() ; i++) {
                        tempLinis[i] = (Line2D) lines.get(i);
                        tempLinis[i].setLine(tempLinis[i].getX1(),tempLinis[i].getY1(),
                                tempLinis[i].getX2() + (0.2*(tempLinis[i].getX2() - xMain)), tempLinis[i].getY2() + (0.2*(tempLinis[i].getY2() - yMain)));
                        lines.set(i, tempLinis[i]);
                    }
                }
                if(e.getPreciseWheelRotation()==1.0) {
                    for (int i = 0; i < points.size(); i++) {
                        tempPoints[i] = (Ellipse2D) points.get(i);
                        tempPoints[i].setFrame(tempPoints[i].getCenterX() - (0.2 * (tempPoints[i].getCenterX() - xMain)) - 5,
                                tempPoints[i].getCenterY() - (0.2 * (tempPoints[i].getCenterY() - yMain)) - 5, 10, 10);
                        points.set(i, tempPoints[i]);
                    }
                    Line2D[] tempLinis = new Line2D[lines.size()];
                    for(int i =0; i<lines.size() ; i++) {
                        tempLinis[i] = (Line2D) lines.get(i);
                        tempLinis[i].setLine(tempLinis[i].getX1(), tempLinis[i].getY1(),
                                tempLinis[i].getX2() - (0.2*(tempLinis[i].getX2() - xMain)), tempLinis[i].getY2() - (0.2*(tempLinis[i].getY2() - yMain)));
                        lines.set(i, tempLinis[i]);
                    }
                }
                repaint();

            }
        }
    }


}
