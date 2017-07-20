import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class Drawer extends JFrame{
  public static void main(String[] args){
    Draw p = new Draw();
    p.addMouseListener(null);
    p.addMouseMotionListener(null);

        JFrame frame = new JFrame("Lab5");
	frame.add(p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280,800);
        frame.setVisible(true);


  }

}